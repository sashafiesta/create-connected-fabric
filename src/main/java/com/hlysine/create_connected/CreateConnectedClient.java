package com.hlysine.create_connected;

import com.simibubi.create.foundation.utility.animation.LerpedFloat;

import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import com.hlysine.create_connected.CCPackets;
import com.hlysine.create_connected.CCPonders;
import net.fabricmc.api.ClientModInitializer;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class CreateConnectedClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CCPonders.register();
		CCPackets.channel.initClientListener();
	}

}
