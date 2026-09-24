package com.hlysine.create_connected.content.dashboard;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

@Environment(EnvType.CLIENT)
public class ClientPlayerAccess {
    public static Player getPlayer() {
        return Minecraft.getInstance().player;
    }
}
