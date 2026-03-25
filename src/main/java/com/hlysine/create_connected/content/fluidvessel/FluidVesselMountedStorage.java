package com.hlysine.create_connected.content.fluidvessel;


import com.hlysine.create_connected.CCMountedStorageTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.api.contraption.storage.SyncedMountedStorage;
import com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType;
import com.simibubi.create.api.contraption.storage.fluid.WrapperMountedFluidStorage;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.foundation.utility.CreateCodecs;
import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class FluidVesselMountedStorage extends WrapperMountedFluidStorage<FluidVesselMountedStorage.Handler> implements SyncedMountedStorage {
    public static final Codec<FluidVesselMountedStorage> CODEC = RecordCodecBuilder.create(i -> i.group(
            CreateCodecs.NON_NEGATIVE_LONG.fieldOf("capacity").forGetter(FluidVesselMountedStorage::getCapacity),
            CreateCodecs.FLUID_STACK_CODEC.fieldOf("fluid").forGetter(FluidVesselMountedStorage::getFluid)
    ).apply(i, FluidVesselMountedStorage::new));

    private boolean dirty;

    protected FluidVesselMountedStorage(MountedFluidStorageType<?> type, long capacity, FluidStack stack) {
        super(type, new FluidVesselMountedStorage.Handler(capacity, stack));
        this.wrapped.onChange = () -> this.dirty = true;
    }

    protected FluidVesselMountedStorage(long capacity, FluidStack stack) {
        this(CCMountedStorageTypes.FLUID_VESSEL.get(), capacity, stack);
    }

    @Override
    public void unmount(Level level, BlockState state, BlockPos pos, @Nullable BlockEntity be) {
        if (be instanceof FluidTankBlockEntity tank && tank.isController()) {
            FluidTank inventory = tank.getTankInventory();
            // capacity shouldn't change, leave it
            inventory.setFluid(this.wrapped.getFluid());
        }
    }

    public FluidStack getFluid() {
        return Objects.requireNonNull(this.wrapped.getFluid());
    }

    public long getCapacity() {
        return this.wrapped.getCapacity();
    }

    @Override
    public boolean isDirty() {
        return this.dirty;
    }

    @Override
    public void markClean() {
        this.dirty = false;
    }

    @Override
    public void afterSync(Contraption contraption, BlockPos localPos) {
        BlockEntity be = contraption.getBlockEntityClientSide(localPos);
        if (!(be instanceof FluidTankBlockEntity tank))
            return;

        FluidTank inv = tank.getTankInventory();
        inv.setFluid(this.getFluid());
        float fillLevel = inv.getFluidAmount() / (float) inv.getCapacity();
        if (tank.getFluidLevel() == null) {
            tank.setFluidLevel(LerpedFloat.linear().startWithValue(fillLevel));
        }
        tank.getFluidLevel().chase(fillLevel, 0.5, LerpedFloat.Chaser.EXP);
    }

    public static FluidVesselMountedStorage fromTank(FluidTankBlockEntity tank) {
        // tank has update callbacks, make an isolated copy
        FluidTank inventory = tank.getTankInventory();
        return new FluidVesselMountedStorage(inventory.getCapacity(), inventory.getFluid().copy());
    }

    public static FluidVesselMountedStorage fromLegacy(CompoundTag nbt) {
        int capacity = nbt.getInt("Capacity");
        FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt);
        return new FluidVesselMountedStorage(capacity, fluid);
    }

    public static final class Handler extends FluidTank {
        private Runnable onChange = () -> {};

        public Handler(long capacity, FluidStack stack) {
            super(capacity);
            Objects.requireNonNull(stack);
            this.setFluid(stack);
        }

        @Override
        protected void onContentsChanged() {
            this.onChange.run();
        }
    }
}
