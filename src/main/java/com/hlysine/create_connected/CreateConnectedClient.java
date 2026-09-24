package com.hlysine.create_connected;

import com.hlysine.create_connected.config.CCConfigs;
import com.hlysine.create_connected.registries.CCPackets;
import com.hlysine.create_connected.registries.CCPartialModels;
import com.hlysine.create_connected.registries.CCPonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;

public class CreateConnectedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CCPartialModels.register();
        CCPackets.getChannel().initClientListener();
        CCConfigs.common().initClientListener();
        PonderIndex.addPlugin(new CCPonderPlugin());
        // Fabric: item-only composite models must be top-level, or porting_lib never resolves their children
        ModelLoadingPlugin.register(context -> context.addModels(
                CreateConnected.asResource("block/fan_ending_catalyst_dragon_head/block"),
                CreateConnected.asResource("block/fan_exploding_catalyst/block")
        ));
    }
}