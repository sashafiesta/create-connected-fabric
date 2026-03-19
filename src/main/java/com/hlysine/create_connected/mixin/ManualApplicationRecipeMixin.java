package com.hlysine.create_connected.mixin;

import com.hlysine.create_connected.config.CServer;
import com.simibubi.create.content.kinetics.deployer.ManualApplicationRecipe;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ManualApplicationRecipe.class, remap = false)
public class ManualApplicationRecipeMixin {
    @Inject(
            method = "manualApplicationRecipesApplyInWorld(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"),
            remap = true
    )
    private static void craftingRemainingItemOnApplication(Player player, Level level, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<?> info) {
        if (!CServer.ApplicationRemainingItemFix.get()) return;

        ItemStack heldItem = player.getItemInHand(hand);
        ItemStack leftover = heldItem.getItem().hasCraftingRemainingItem() ? new ItemStack(heldItem.getItem().getCraftingRemainingItem()) : ItemStack.EMPTY;

        heldItem.shrink(1);

        if (heldItem.isEmpty()) {
            player.setItemInHand(hand, leftover);
        } else {
            heldItem.grow(1); // Create shrinks the stack again after this inject
            if (!player.getInventory().add(leftover)) {
                player.drop(leftover, false);
            }
        }
    }
}
