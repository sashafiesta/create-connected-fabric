package com.hlysine.create_connected.content.kineticbattery;

import com.hlysine.create_connected.registries.CCBlocks;
import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import io.github.fabricators_of_create.porting_lib.transfer.callbacks.TransactionCallback;
import net.minecraft.core.BlockPos;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class KineticBatteryInteractionPoint extends AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint {
    public KineticBatteryInteractionPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    public ItemStack insert(ItemStack stack, TransactionContext ctx) {
        ItemStack input = stack.copy();
        InteractionResultHolder<ItemStack> res =
                KineticBatteryBlock.tryInsert(cachedState, level, pos, input, false, false, true);
        // Fabric: only apply the insertion once the arm commits the transaction
        ItemStack toApply = stack.copy();
        TransactionCallback.onSuccess(ctx, () ->
                KineticBatteryBlock.tryInsert(cachedState, level, pos, toApply, false, false, false));
        ItemStack remainder = res.getObject();
        if (input.isEmpty()) {
            return remainder;
        } else {
            TransactionCallback.onSuccess(ctx, () ->
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), remainder));
            return input;
        }
    }

    public static class Type extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return CCBlocks.KINETIC_BATTERY.has(state);
        }

        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new KineticBatteryInteractionPoint(this, level, pos, state);
        }
    }
}
