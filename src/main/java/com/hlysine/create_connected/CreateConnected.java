package com.hlysine.create_connected;

import com.hlysine.create_connected.compat.CopycatsManager;
import com.hlysine.create_connected.compat.Mods;
import com.hlysine.create_connected.config.CCConfigs;
import com.hlysine.create_connected.content.redstonelinkwildcard.LinkWildcardNetworkHandler;
import com.hlysine.create_connected.datagen.advancements.CCAdvancements;
import com.hlysine.create_connected.datagen.advancements.CCTriggers;
import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class CreateConnected implements ModInitializer {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "create_connected";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

    static {
        REGISTRATE.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                .andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    }

    @Override
    public void onInitialize() {
        CCCraftingConditions.register();

        REGISTRATE.setCreativeTab(CCCreativeTabs.MAIN_KEY);
        CCSoundEvents.prepare();
        CCBlocks.register();
        CCItems.register();
        CCBlockEntityTypes.register();
        CCCreativeTabs.register();
        CCPackets.registerPackets();
        CCPackets.getChannel().initServerListener();
        CCArmInteractionPointTypes.register();

        CCConfigs.register();
        CCConfigs.common().register();

        if (Mods.COPYCATS.isLoaded())
            ServerTickEvents.END_WORLD_TICK.register(CopycatsManager::onLevelTick);

        CCSoundEvents.register();

        CCInteractionBehaviours.register();
        CCMovementBehaviours.register();
        CCMountedStorageTypes.register();
        CCDisplaySources.register();
        CCItemAttributes.register();

        LinkWildcardNetworkHandler.register();

        REGISTRATE.register();

        // Must be after REGISTRATE.register() so items are registered and asStack() works
        CCAdvancements.register();
        CCTriggers.register();
    }

    public static CreateRegistrate getRegistrate() {
        return REGISTRATE;
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }
}