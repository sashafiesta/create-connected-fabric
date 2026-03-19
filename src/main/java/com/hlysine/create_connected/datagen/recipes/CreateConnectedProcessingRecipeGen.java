package com.hlysine.create_connected.datagen.recipes;

import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class CreateConnectedProcessingRecipeGen {
    public static DataProvider create(FabricDataOutput output) {
        final List<ProcessingRecipeGen> GENERATORS = new ArrayList<>();

        GENERATORS.add(new CuttingRecipeGen(output));
        GENERATORS.add(new FillingRecipeGen(output));
        GENERATORS.add(new ItemApplicationRecipeGen(output));

        return new DataProvider() {

            @Override
            public String getName() {
                return "Create: Connected's Processing Recipes";
            }

            @Override
            public CompletableFuture<?> run(CachedOutput dc) {
                return CompletableFuture.allOf(GENERATORS.stream()
                        .map(gen -> gen.run(dc))
                        .toArray(CompletableFuture[]::new));
            }
        };
    }
}
