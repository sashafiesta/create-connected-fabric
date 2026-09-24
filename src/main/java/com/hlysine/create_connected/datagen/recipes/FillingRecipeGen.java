package com.hlysine.create_connected.datagen.recipes;

import com.hlysine.create_connected.CreateConnected;
import com.hlysine.create_connected.compat.Mods;
import com.hlysine.create_connected.registries.CCBlocks;
import com.hlysine.create_connected.registries.CCTags;
import com.simibubi.create.AllFluids;
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

    GeneratedRecipe FAN_CHOCOLATE_COATING_CATALYST = create("fan_chocolate_coating_catalyst", b -> b.require(AllFluids.CHOCOLATE.get(), FluidConstants.BUCKET)
            .require(CCBlocks.EMPTY_FAN_CATALYST)
            .withCondition(new FeatureEnabledCondition(CCBlocks.EMPTY_FAN_CATALYST.getId()))
            .withCondition(DefaultResourceConditions.allModsLoaded(Mods.MORE_CATALYSTS.id()))
            .output(CCBlocks.FAN_CHOCOLATE_COATING_CATALYST));

    GeneratedRecipe FAN_HONEY_COATING_CATALYST = create("fan_honey_coating_catalyst", b -> b.require(AllFluids.HONEY.get(), FluidConstants.BUCKET)
            .require(CCBlocks.EMPTY_FAN_CATALYST)
            .withCondition(new FeatureEnabledCondition(CCBlocks.EMPTY_FAN_CATALYST.getId()))
            .withCondition(DefaultResourceConditions.allModsLoaded(Mods.MORE_CATALYSTS.id()))
            .output(CCBlocks.FAN_HONEY_COATING_CATALYST));

    GeneratedRecipe FAN_DYEING_CATALYSTS = fillFanDyeingCatalysts();

    private GeneratedRecipe fillFanDyeingCatalysts() {
        CCBlocks.FAN_DYEING_CATALYSTS.forEach((color, block) -> {
            create(color.getName() + "_fan_dyeing_catalyst_dragons_plus", b -> b.require(new SimpleFluidIngredient(Mods.DRAGONS_PLUS, color.getName() + "_dye", FluidConstants.BUCKET))
                    .require(CCBlocks.EMPTY_FAN_CATALYST)
                    .withCondition(new FeatureEnabledCondition(CCBlocks.EMPTY_FAN_CATALYST.getId()))
                    .withCondition(DefaultResourceConditions.allModsLoaded(Mods.DRAGONS_PLUS.id()))
                    .output(CCBlocks.FAN_DYEING_CATALYSTS.get(color)));
            create(color.getName() + "_fan_dyeing_catalyst_garnished", b -> b.require(new SimpleFluidIngredient(Mods.GARNISHED, color.getName() + "_mastic_resin", FluidConstants.BUCKET))
                    .require(CCBlocks.EMPTY_FAN_CATALYST)
                    .withCondition(new FeatureEnabledCondition(CCBlocks.EMPTY_FAN_CATALYST.getId()))
                    .withCondition(DefaultResourceConditions.allModsLoaded(Mods.GARNISHED.id()))
                    .output(CCBlocks.FAN_DYEING_CATALYSTS.get(color)));
        });
        return null;
    }

    /*
    GeneratedRecipe FAN_TRANSMUTATION_CATALYST = create("fan_transmutation_catalyst", b -> b.require(new SizedFluidIngredient(new SimpleFluidIngredient(Mods.SHIMMER, "shimmer"), 1000))
            .require(CCBlocks.EMPTY_FAN_CATALYST)
            .withCondition(new FeatureEnabledCondition(CCBlocks.EMPTY_FAN_CATALYST.getId()))
            .withCondition(new ModLoadedCondition(Mods.SHIMMER.id()))
            .output(CCBlocks.FAN_TRANSMUTATION_CATALYST));
     */

    public FillingRecipeGen(PackOutput output) {
        super(output, CreateConnected.MODID);
    }
}
