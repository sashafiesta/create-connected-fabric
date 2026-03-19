package com.hlysine.create_connected.mixin.featuretoggle;

import com.hlysine.create_connected.CreateConnected;
import com.hlysine.create_connected.config.CCConfigs;
import net.createmod.catnip.config.ui.ConfigScreen;
import net.createmod.catnip.config.ui.SubMenuConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SubMenuConfigScreen.class, remap = false)
public class SubMenuConfigScreenMixin {
    @Inject(
            method = "saveChanges()V",
            at = @At("TAIL")
    )
    private void saveChangesAndRefresh(CallbackInfo ci) {
        if (ConfigScreen.modID != null && ConfigScreen.modID.equals(CreateConnected.MODID)) {
            MinecraftServer server = Minecraft.getInstance().getSingleplayerServer();
            if (server != null)
                server.submit(() -> CCConfigs.common().syncToAllPlayers());
        }
    }
}
