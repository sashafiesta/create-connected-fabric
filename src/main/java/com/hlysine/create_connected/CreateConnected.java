package com.hlysine.create_connected;

import com.simibubi.create.foundation.data.CreateRegistrate;

import com.hlysine.create_connected.CCBlocks;
import com.hlysine.create_connected.CCBlockEntityTypes;
import com.hlysine.create_connected.CCItems;
import com.hlysine.create_connected.CCPackets;
import com.hlysine.create_connected.CCItemAttributes;
import com.hlysine.create_connected.CCCreativeTabs;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import team.reborn.energy.api.EnergyStorage;

public class CreateConnected implements ModInitializer {

	public static final String ID = "create_connected";
	public static final String NAME = "Create Connected";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);

	@Override
	public void onInitialize() {
		CCItems.register();
		CCBlockEntityTypes.register();
		REGISTRATE.register();

		CCPackets.registerPackets();
		CCPackets.channel.initServerListener();
		
		CCItemAttributes.register();
		CCCreativeTabs.register();

	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(ID, path);
	}
}
