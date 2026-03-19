package com.hlysine.create_connected;

import com.hlysine.create_connected.datagen.recipes.FeatureEnabledCondition;
import com.hlysine.create_connected.datagen.recipes.FeatureEnabledInCopycatsCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

@SuppressWarnings("unused")
public class CCCraftingConditions {
    public static void register() {
        ResourceConditions.register(FeatureEnabledCondition.getId(), FeatureEnabledCondition::test);
        ResourceConditions.register(FeatureEnabledInCopycatsCondition.getId(), FeatureEnabledInCopycatsCondition::test);
    }
}
