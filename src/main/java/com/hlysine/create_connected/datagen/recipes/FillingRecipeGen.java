package com.hlysine.create_connected.datagen.recipes;

import com.hlysine.create_connected.CCBlocks;
import com.hlysine.create_connected.CCTags;
import com.hlysine.create_connected.CreateConnected;
import com.hlysine.create_connected.compat.Mods;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.material.Fluids;

@SuppressWarnings("unused")
public class FillingRecipeGen extends com.simibubi.create.api.data.recipe.FillingRecipeGen {

    GeneratedRecipe FAN_BLASTING_CATALYST = create("fan_blasting_catalyst", b -> b.require(Fluids.LAVA, FluidConstants.BUCKET)
            .require(CCBlocks.EMPTY_FAN_CATALYST)
            .withCondition(new FeatureEnabledCondition(CCBlocks.EMPTY_FAN_CATALYST.getId()))
            .output(CCBlocks.FAN_BLASTING_CATALYST));

    GeneratedRecipe FAN_SPLASHING_CATALYST = create("fan_splashing_catalyst", b -> b.require(Fluids.WATER, FluidConstants.BUCKET)
            .require(CCBlocks.EMPTY_FAN_CATALYST)
            .withCondition(new FeatureEnabledCondition(CCBlocks.EMPTY_FAN_CATALYST.getId()))
            .output(CCBlocks.FAN_SPLASHING_CATALYST));

    GeneratedRecipe FAN_ENDING_CATALYST_DRAGONS_BREATH = create("fan_ending_catalyst_dragons_breath", b -> b.require(FluidIngredient.fromTag(CCTags.Fluids.FAN_PROCESSING_CATALYSTS_ENDING.tag, FluidConstants.BUCKET))
            .require(CCBlocks.EMPTY_FAN_CATALYST)
            .withCondition(new FeatureEnabledCondition(CCBlocks.EMPTY_FAN_CATALYST.getId()))
            .withCondition(DefaultResourceConditions.allModsLoaded(Mods.DRAGONS_PLUS.id()))
            .output(CCBlocks.FAN_ENDING_CATALYST_DRAGONS_BREATH));

    public FillingRecipeGen(PackOutput output) {
        super(output, CreateConnected.MODID);
    }
}
