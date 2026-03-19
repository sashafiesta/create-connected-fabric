package com.hlysine.create_connected;

import net.createmod.ponder.foundation.PonderIndex;
import net.fabricmc.api.ClientModInitializer;

public class CreateConnectedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CCPartialModels.register();
        CCPackets.getChannel().initClientListener();
        PonderIndex.addPlugin(new CCPonderPlugin());
    }
}