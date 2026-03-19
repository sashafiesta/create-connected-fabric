package com.hlysine.create_connected.config;

import com.hlysine.create_connected.CreateConnected;
import com.simibubi.create.api.stress.BlockStressValues;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.createmod.catnip.config.ConfigBase;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class CCConfigs {

    private static final Map<ModConfig.Type, ConfigBase> CONFIGS = new EnumMap<>(ModConfig.Type.class);

    private static CCommon common;
    private static CServer server;

    public static CCommon common() {
        return common;
    }

    public static CServer server() {
        return server;
    }

    public static <T> Supplier<T> safeGetter(Supplier<T> getter, T defaultValue) {
        return () -> {
            try {
                return getter.get();
            } catch (IllegalStateException | NullPointerException ex) {
                // the config is accessed too early (before registration or before config load)
                return defaultValue;
            }
        };
    }

    public static ConfigBase byType(ModConfig.Type type) {
        return CONFIGS.get(type);
    }

    private static <T extends ConfigBase> T register(Supplier<T> factory, ModConfig.Type side) {
        Pair<T, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(builder -> {
            T config = factory.get();
            config.registerAll(builder);
            return config;
        });

        T config = specPair.getLeft();
        config.specification = specPair.getRight();
        CONFIGS.put(side, config);
        return config;
    }

    public static void register() {
        common = register(CCommon::new, ModConfig.Type.COMMON);
        server = register(CServer::new, ModConfig.Type.SERVER);

        for (Map.Entry<ModConfig.Type, ConfigBase> pair : CONFIGS.entrySet())
            ForgeConfigRegistry.INSTANCE.register(CreateConnected.MODID, pair.getKey(), pair.getValue().specification);

        ModConfigEvents.loading(CreateConnected.MODID).register(CCConfigs::onLoad);
        ModConfigEvents.reloading(CreateConnected.MODID).register(CCConfigs::onReload);

        CStress stress = server().stressValues;
        BlockStressValues.IMPACTS.registerProvider(stress::getImpact);
        BlockStressValues.CAPACITIES.registerProvider(stress::getCapacity);
    }

    public static void onLoad(ModConfig modConfig) {
        for (ConfigBase config : CONFIGS.values())
            if (config.specification == modConfig.getSpec())
                config.onLoad();
    }

    public static void onReload(ModConfig modConfig) {
        for (ConfigBase config : CONFIGS.values())
            if (config.specification == modConfig.getSpec())
                config.onReload();
    }

}
