package com.hlysine.create_connected.mixin.copycat.fencegate;

import com.hlysine.create_connected.content.copycat.ICopycatWithWrappedBlock;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "me.jellysquid.mods.lithium.common.ai.pathing.PathNodeDefaults")
@Pseudo // Would normally fail if Radium is not installed
public class COMPATPathNodeDefaultsMixin {
    @WrapOperation(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;"),
            method = "getNodeType", // Fabric: name only, the descriptor is not remapped for a @Pseudo target
            require = 0
    )
    private static Block getWrappedBlock(BlockState instance, Operation<Block> original) {
        return ICopycatWithWrappedBlock.unwrap(original.call(instance));
    }
}
