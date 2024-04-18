package com.hlysine.create_connected;

import com.simibubi.create.AllCreativeModeTabs;

import com.simibubi.create.Create;

import com.hlysine.create_connected.CreateConnected;
import com.hlysine.create_connected.CCBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Supplier;

public class CCCreativeTabs {

	public static final AllCreativeModeTabs.TabInfo MAIN = register("create_connected",
			() -> FabricItemGroup.builder()
					.title(Component.translatable("itemGroup.create_connected.main"))
					.icon(() -> CCBlocks.PARALLEL_GEARBOX.asStack())
					.build());

	private static AllCreativeModeTabs.TabInfo register(String name, Supplier<CreativeModeTab> supplier) {
		ResourceLocation id = CreateConnected.asResource(name);
		ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
		CreativeModeTab tab = supplier.get();
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, tab);
		return new AllCreativeModeTabs.TabInfo(key, tab);
	}
	public static void register() {}

}


/*
 public static final List<ItemProviderEntry<?>> ITEMS = List.of(
            CCBlocks.ENCASED_CHAIN_COGWHEEL,
            CCBlocks.INVERTED_CLUTCH,
            CCBlocks.INVERTED_GEARSHIFT,
            CCBlocks.PARALLEL_GEARBOX,
            CCItems.VERTICAL_PARALLEL_GEARBOX,
            CCBlocks.SIX_WAY_GEARBOX,
            CCItems.VERTICAL_SIX_WAY_GEARBOX,
            CCBlocks.BRASS_GEARBOX,
            CCItems.VERTICAL_BRASS_GEARBOX,
            CCBlocks.SHEAR_PIN,
            CCBlocks.OVERSTRESS_CLUTCH,
            CCBlocks.CENTRIFUGAL_CLUTCH,
            CCBlocks.FREEWHEEL_CLUTCH,
            CCBlocks.BRAKE,
            CCBlocks.ITEM_SILO,
            CCBlocks.SEQUENCED_PULSE_GENERATOR,
            CCItems.LINKED_TRANSMITTER,
            CCBlocks.EMPTY_FAN_CATALYST,
            CCBlocks.FAN_BLASTING_CATALYST,
            CCBlocks.FAN_SMOKING_CATALYST,
            CCBlocks.FAN_SPLASHING_CATALYST,
            CCBlocks.FAN_HAUNTING_CATALYST,
            CCBlocks.COPYCAT_BLOCK,
            CCBlocks.COPYCAT_SLAB,
            CCBlocks.COPYCAT_BEAM,
            CCBlocks.COPYCAT_VERTICAL_STEP,
            CCBlocks.COPYCAT_STAIRS,
            CCBlocks.COPYCAT_FENCE,
            CCBlocks.COPYCAT_FENCE_GATE,
            CCBlocks.COPYCAT_WALL,
            CCBlocks.COPYCAT_BOARD,
            CCItems.COPYCAT_BOX,
            CCItems.COPYCAT_CATWALK,
            CCItems.CONTROL_CHIP,
            CCItems.MUSIC_DISC_ELEVATOR,
            CCItems.MUSIC_DISC_INTERLUDE,
            CCBlocks.CHERRY_WINDOW,
            CCBlocks.BAMBOO_WINDOW,
            CCBlocks.CHERRY_WINDOW_PANE,
            CCBlocks.BAMBOO_WINDOW_PANE
    );
*/
/*
		ItemGroupEvents.modifyEntriesEvent(CCCreativeTabs.MAIN.key()).register(content -> {
			content.accept(VOID_STEEL_BLOCK);
			content.accept(VOID_STEEL_SCAFFOLD);
			content.accept(VOID_STEEL_LADDER);
			content.accept(VOID_STEEL_BARS);
			content.accept(VOID_CASING);
			content.accept(VOID_MOTOR);
			content.accept(VOID_CHEST);
			content.accept(VOID_TANK);
			content.accept(VOID_BATTERY);
			content.accept(GEARCUBE);
			content.accept(LSHAPED_GEARBOX);
			content.accept(AMETHYST_TILES);
			content.accept(SMALL_AMETHYST_TILES);
		});
*/
/*
	static {
		ItemGroupEvents.modifyEntriesEvent(CCCreativeTabs.MAIN.key()).register(content -> {
			content.accept(VOID_STEEL_INGOT);
			content.accept(VOID_STEEL_SHEET);
			content.accept(POLISHED_AMETHYST);
			content.accept(GRAVITON_TUBE);
		});
	}
*/