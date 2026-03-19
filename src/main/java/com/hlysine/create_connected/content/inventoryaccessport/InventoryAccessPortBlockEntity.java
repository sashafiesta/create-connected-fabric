package com.hlysine.create_connected.content.inventoryaccessport;
import com.simibubi.create.content.redstone.DirectedDirectionalBlock;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.inventory.CapManipulationBehaviourBase;
import com.simibubi.create.foundation.blockEntity.behaviour.inventory.InvManipulationBehaviour;
import net.createmod.catnip.math.BlockFace;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.hlysine.create_connected.content.inventoryaccessport.InventoryAccessPortBlock.ATTACHED;

public class InventoryAccessPortBlockEntity extends SmartBlockEntity implements SidedStorageBlockEntity {
    private InventoryAccessHandler capability;
    private InvManipulationBehaviour observedInventory;
    private boolean powered;

    public InventoryAccessPortBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        powered = false;
    }

    @Override
    public void initialize() {
        super.initialize();
        updateConnectedInventory();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        CapManipulationBehaviourBase.InterfaceProvider towardBlockFacing =
                (w, p, s) -> new BlockFace(p, DirectedDirectionalBlock.getTargetDirection(s));
        behaviours.add(observedInventory = new InvManipulationBehaviour(this, towardBlockFacing));
    }

    public boolean isAttached() {
        return !powered && observedInventory.hasInventory() && !(observedInventory.getInventory() instanceof WrappedItemHandler);
    }

    public void updateConnectedInventory() {
        observedInventory.findNewCapability();
        boolean previouslyPowered = powered;
        assert level != null;
        powered = level.hasNeighborSignal(worldPosition);
        if (powered != previouslyPowered) {
            notifyUpdate();
        }
        if (isAttached() != getBlockState().getValue(ATTACHED)) {
            BlockState state = getBlockState().cycle(ATTACHED);
            level.setBlockAndUpdate(worldPosition, state);
        }
    }

    @Override
    public @Nullable Storage<ItemVariant> getItemStorage(@Nullable Direction face) {
        if (capability == null) capability = new InventoryAccessHandler();
        return capability;
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        powered = compound.getBoolean("Powered");
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putBoolean("Powered", powered);
    }

    private Storage<ItemVariant> getConnectedStorage() {
        if (powered) return null;
        Storage<ItemVariant> storage = observedInventory.getInventory();
        if (storage instanceof WrappedItemHandler) return null;
        return storage;
    }

    private class InventoryAccessHandler implements WrappedItemHandler {

        private final ThreadLocal<Boolean> recursionGuard = ThreadLocal.withInitial(() -> false);

        @Override
        public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
            if (recursionGuard.get()) return 0;
            recursionGuard.set(true);
            try {
                Storage<ItemVariant> storage = getConnectedStorage();
                return storage == null ? 0 : storage.insert(resource, maxAmount, transaction);
            } finally {
                recursionGuard.set(false);
            }
        }

        @Override
        public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
            if (recursionGuard.get()) return 0;
            recursionGuard.set(true);
            try {
                Storage<ItemVariant> storage = getConnectedStorage();
                return storage == null ? 0 : storage.extract(resource, maxAmount, transaction);
            } finally {
                recursionGuard.set(false);
            }
        }

        @Override
        public Iterator<StorageView<ItemVariant>> iterator() {
            if (recursionGuard.get()) return Collections.emptyIterator();
            recursionGuard.set(true);
            try {
                Storage<ItemVariant> storage = getConnectedStorage();
                if (storage == null) return Collections.emptyIterator();
                List<StorageView<ItemVariant>> views = new ArrayList<>();
                storage.iterator().forEachRemaining(views::add);
                return views.iterator();
            } finally {
                recursionGuard.set(false);
            }
        }
    }
}
