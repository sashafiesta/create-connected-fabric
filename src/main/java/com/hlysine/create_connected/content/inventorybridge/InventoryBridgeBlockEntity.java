package com.hlysine.create_connected.content.inventorybridge;
import com.hlysine.create_connected.content.inventoryaccessport.WrappedItemHandler;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.SidedFilteringBehaviour;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.hlysine.create_connected.content.inventorybridge.InventoryBridgeBlock.ATTACHED_NEGATIVE;
import static com.hlysine.create_connected.content.inventorybridge.InventoryBridgeBlock.ATTACHED_POSITIVE;

public class InventoryBridgeBlockEntity extends SmartBlockEntity implements SidedStorageBlockEntity {
    private InventoryBridgeHandler capability;
    private InvManipulationBehaviour negativeInventory;
    private InvManipulationBehaviour positiveInventory;

    SidedFilteringBehaviour filters;
    public FilteringBehaviour negativeFilter;
    public FilteringBehaviour positiveFilter;

    private boolean powered;

    public InventoryBridgeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
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
        CapManipulationBehaviourBase.InterfaceProvider towardBlockFacing1 =
                (w, p, s) -> new BlockFace(p, InventoryBridgeBlock.getNegativeTarget(s));
        CapManipulationBehaviourBase.InterfaceProvider towardBlockFacing2 =
                (w, p, s) -> new BlockFace(p, InventoryBridgeBlock.getPositiveTarget(s));
        behaviours.add(negativeInventory = new InvManipulationBehaviour(this, towardBlockFacing1));
        behaviours.add(positiveInventory = new InvManipulationBehaviour(this, towardBlockFacing2));
        behaviours.add(filters = new SidedFilteringBehaviour(
                this,
                new InventoryBridgeFilterSlot(),
                (facing, filter) -> {
                    if (facing.getAxisDirection() == Direction.AxisDirection.NEGATIVE) {
                        negativeFilter = filter;
                    } else {
                        positiveFilter = filter;
                    }
                    return filter;
                },
                facing -> facing.getAxis() == getBlockState().getValue(InventoryBridgeBlock.AXIS)
        ));
    }

    public boolean isAttachedNegative() {
        return !powered && negativeInventory.hasInventory() && !(negativeInventory.getInventory() instanceof WrappedItemHandler);
    }

    public boolean isAttachedPositive() {
        return !powered && positiveInventory.hasInventory() && !(positiveInventory.getInventory() instanceof WrappedItemHandler);
    }

    public void updateConnectedInventory() {
        negativeInventory.findNewCapability();
        positiveInventory.findNewCapability();
        boolean previouslyPowered = powered;
        powered = level.hasNeighborSignal(worldPosition);
        if (powered != previouslyPowered) {
            notifyUpdate();
        }
        boolean attachedNegative = isAttachedNegative();
        boolean attachedPositive = isAttachedPositive();
        if (attachedNegative != getBlockState().getValue(ATTACHED_NEGATIVE) || attachedPositive != getBlockState().getValue(ATTACHED_POSITIVE)) {
            BlockState state = getBlockState()
                    .setValue(ATTACHED_NEGATIVE, attachedNegative)
                    .setValue(ATTACHED_POSITIVE, attachedPositive);
            level.setBlockAndUpdate(worldPosition, state);
        }
    }

    @Override
    public @Nullable Storage<ItemVariant> getItemStorage(@Nullable Direction face) {
        if (capability == null) capability = new InventoryBridgeHandler();
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

    private Storage<ItemVariant> getNegativeStorage() {
        if (powered) return null;
        Storage<ItemVariant> storage = negativeInventory.getInventory();
        if (storage instanceof WrappedItemHandler) return null;
        return storage;
    }

    private Storage<ItemVariant> getPositiveStorage() {
        if (powered) return null;
        Storage<ItemVariant> storage = positiveInventory.getInventory();
        if (storage instanceof WrappedItemHandler) return null;
        return storage;
    }

    private class InventoryBridgeHandler implements WrappedItemHandler {

        private final ThreadLocal<Boolean> recursionGuard = ThreadLocal.withInitial(() -> false);

        @Override
        public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
            if (recursionGuard.get()) return 0;
            recursionGuard.set(true);
            try {
                Storage<ItemVariant> handler1 = getNegativeStorage();
                Storage<ItemVariant> handler2 = getPositiveStorage();
                if (handler1 == null && handler2 == null) return 0;

                ItemStack stack = resource.toStack((int) Math.min(maxAmount, Integer.MAX_VALUE));
                boolean negative = negativeFilter.test(stack);
                boolean positive = positiveFilter.test(stack);
                if (!negative && !positive) return 0;

                boolean negativeFilterEmpty = negativeFilter.getFilter().isEmpty();
                boolean positiveFilterEmpty = positiveFilter.getFilter().isEmpty();

                if (handler1 == null) {
                    if (!positive) return 0;
                    if (negative && !negativeFilterEmpty && positiveFilterEmpty) return 0;
                    return handler2.insert(resource, maxAmount, transaction);
                } else if (handler2 == null) {
                    if (!negative) return 0;
                    if (positive && !positiveFilterEmpty && negativeFilterEmpty) return 0;
                    return handler1.insert(resource, maxAmount, transaction);
                } else {
                    boolean canNegative = negative;
                    boolean canPositive = positive;
                    if (!negativeFilterEmpty || !positiveFilterEmpty) {
                        if (canNegative && positiveFilterEmpty) canPositive = false;
                        if (canPositive && negativeFilterEmpty) canNegative = false;
                    }

                    long inserted = 0;
                    if (canNegative) {
                        inserted = handler1.insert(resource, maxAmount, transaction);
                    }
                    if (canPositive && inserted < maxAmount) {
                        inserted += handler2.insert(resource, maxAmount - inserted, transaction);
                    }
                    return inserted;
                }
            } finally {
                recursionGuard.set(false);
            }
        }

        @Override
        public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
            if (recursionGuard.get()) return 0;
            recursionGuard.set(true);
            try {
                Storage<ItemVariant> handler1 = getNegativeStorage();
                Storage<ItemVariant> handler2 = getPositiveStorage();
                if (handler1 == null && handler2 == null) return 0;

                long extracted = 0;
                if (handler1 != null) {
                    extracted = handler1.extract(resource, maxAmount, transaction);
                }
                if (handler2 != null && extracted < maxAmount) {
                    extracted += handler2.extract(resource, maxAmount - extracted, transaction);
                }
                return extracted;
            } finally {
                recursionGuard.set(false);
            }
        }

        @Override
        public Iterator<StorageView<ItemVariant>> iterator() {
            if (recursionGuard.get()) return Collections.emptyIterator();
            recursionGuard.set(true);
            try {
                Storage<ItemVariant> handler1 = getNegativeStorage();
                Storage<ItemVariant> handler2 = getPositiveStorage();
                if (handler1 == null && handler2 == null) return Collections.emptyIterator();

                List<StorageView<ItemVariant>> views = new ArrayList<>();
                if (handler1 != null) {
                    handler1.iterator().forEachRemaining(views::add);
                }
                if (handler2 != null) {
                    handler2.iterator().forEachRemaining(views::add);
                }
                return views.iterator();
            } finally {
                recursionGuard.set(false);
            }
        }
    }
}
