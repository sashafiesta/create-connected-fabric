package com.hlysine.create_connected;

import com.hlysine.create_connected.config.FeatureToggle;
import com.simibubi.create.AllCreativeModeTabs;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class CCCreativeTabs {

    public static final List<ItemProviderEntry<?>> ITEMS = List.of(
            CCBlocks.ENCASED_CHAIN_COGWHEEL,
            CCBlocks.CRANK_WHEEL,
            CCBlocks.LARGE_CRANK_WHEEL,
            CCBlocks.INVERTED_CLUTCH,
            CCBlocks.INVERTED_GEARSHIFT,
            CCBlocks.PARALLEL_GEARBOX,
            CCItems.VERTICAL_PARALLEL_GEARBOX,
            CCBlocks.SIX_WAY_GEARBOX,
            CCItems.VERTICAL_SIX_WAY_GEARBOX,
            CCBlocks.BRASS_GEARBOX,
            CCItems.VERTICAL_BRASS_GEARBOX,
            CCBlocks.CROSS_CONNECTOR,
            CCBlocks.SHEAR_PIN,
            CCBlocks.OVERSTRESS_CLUTCH,
            CCBlocks.CENTRIFUGAL_CLUTCH,
            CCBlocks.FREEWHEEL_CLUTCH,
            CCBlocks.BRAKE,
            CCBlocks.KINETIC_BRIDGE,
            CCBlocks.KINETIC_BATTERY,
            CCItems.CHARGED_KINETIC_BATTERY,
            CCBlocks.ITEM_SILO,
            CCBlocks.FLUID_VESSEL,
            CCBlocks.CREATIVE_FLUID_VESSEL,
            CCBlocks.INVENTORY_ACCESS_PORT,
            CCBlocks.INVENTORY_BRIDGE,
            CCBlocks.SEQUENCED_PULSE_GENERATOR,
            CCItems.LINKED_TRANSMITTER,
            CCItems.REDSTONE_LINK_WILDCARD,
            CCBlocks.EMPTY_FAN_CATALYST,
            CCBlocks.FAN_BLASTING_CATALYST,
            CCBlocks.FAN_SMOKING_CATALYST,
            CCBlocks.FAN_SPLASHING_CATALYST,
            CCBlocks.FAN_HAUNTING_CATALYST,
            CCBlocks.FAN_SEETHING_CATALYST,
            CCBlocks.FAN_FREEZING_CATALYST,
            CCBlocks.FAN_SANDING_CATALYST,
            CCBlocks.FAN_ENRICHED_CATALYST,
            CCBlocks.FAN_ENDING_CATALYST_DRAGONS_BREATH,
            CCBlocks.FAN_ENDING_CATALYST_DRAGON_HEAD,
            CCBlocks.FAN_WITHERING_CATALYST,
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
            CCItems.MUSIC_DISC_INTERLUDE
    );

    public static final ResourceKey<CreativeModeTab> MAIN_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, CreateConnected.asResource("main"));

    public static void register() {
        CreativeModeTab tab = FabricItemGroup.builder()
                .title(Component.translatable("itemGroup.create_connected.main"))
                .icon(CCBlocks.PARALLEL_GEARBOX::asStack)
                .displayItems(new DisplayItemsGenerator(ITEMS))
                .build();

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MAIN_KEY, tab);

        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) -> {
            ResourceKey<CreativeModeTab> key = BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(group).orElse(null);
            if (Objects.equals(key, MAIN_KEY) || Objects.equals(key, CreativeModeTabs.SEARCH)) {
                Set<Item> hiddenItems = ITEMS.stream()
                        .filter(x -> !FeatureToggle.isEnabled(x.getId()))
                        .map(ItemProviderEntry::asItem)
                        .collect(Collectors.toSet());
                entries.getDisplayStacks().removeIf(stack -> hiddenItems.contains(stack.getItem()));
                entries.getSearchTabStacks().removeIf(stack -> hiddenItems.contains(stack.getItem()));
            }
        });
    }

    private record DisplayItemsGenerator(
            List<ItemProviderEntry<?>> items) implements CreativeModeTab.DisplayItemsGenerator {
        @Override
        public void accept(@NotNull CreativeModeTab.ItemDisplayParameters params, @NotNull CreativeModeTab.Output output) {
            for (ItemProviderEntry<?> item : items) {
                if (FeatureToggle.isEnabled(item.getId())) {
                    output.accept(item);
                }
            }
        }
    }
}
