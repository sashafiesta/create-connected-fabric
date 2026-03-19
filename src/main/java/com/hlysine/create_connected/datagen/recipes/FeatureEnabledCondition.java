package com.hlysine.create_connected.datagen.recipes;

import com.google.gson.JsonObject;
import com.hlysine.create_connected.CreateConnected;
import com.hlysine.create_connected.config.FeatureToggle;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class FeatureEnabledCondition implements ConditionJsonProvider {
    private static final ResourceLocation NAME = CreateConnected.asResource("feature_enabled");
    private final ResourceLocation feature;

    public FeatureEnabledCondition(ResourceLocation feature) {
        this.feature = feature;
    }

    @Override
    public ResourceLocation getConditionId() {
        return NAME;
    }

    @Override
    public void writeParameters(JsonObject json) {
        json.addProperty("feature", feature.toString());
    }

    public static boolean test(JsonObject json) {
        ResourceLocation feature = new ResourceLocation(GsonHelper.getAsString(json, "feature"));
        return FeatureToggle.isEnabled(feature);
    }

    public static ResourceLocation getId() {
        return NAME;
    }
}
