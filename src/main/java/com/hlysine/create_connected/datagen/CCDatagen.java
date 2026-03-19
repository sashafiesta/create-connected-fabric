package com.hlysine.create_connected.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hlysine.create_connected.CCPonderPlugin;
import com.hlysine.create_connected.CCSoundEvents;
import com.hlysine.create_connected.CreateConnected;
import com.hlysine.create_connected.datagen.advancements.CCAdvancements;
import com.hlysine.create_connected.datagen.recipes.CCStandardRecipes;
import com.hlysine.create_connected.datagen.recipes.CreateConnectedProcessingRecipeGen;
import com.hlysine.create_connected.datagen.recipes.SequencedAssemblyGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.createmod.ponder.foundation.PonderIndex;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import java.util.Collections;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;

public class CCDatagen {

    private static final CreateRegistrate REGISTRATE = CreateConnected.getRegistrate();

    public static void gatherData(FabricDataGenerator generator) {
        addExtraRegistrateData();

        ExistingFileHelper helper = new ExistingFileHelper(Collections.emptyList(), Set.of(), false, null, null);

        FabricDataGenerator.Pack pack = generator.createPack();
        REGISTRATE.setupDatagen(pack, helper);

        pack.addProvider((FabricDataOutput output) -> CCSoundEvents.provider(generator));
        pack.addProvider((FabricDataOutput output) -> new CCAdvancements(output));
        pack.addProvider(CCStandardRecipes::new);
        pack.addProvider((FabricDataOutput output) -> new SequencedAssemblyGen(output));
        pack.addProvider(CreateConnectedProcessingRecipeGen::create);
    }

    private static void addExtraRegistrateData() {
        CCTagGen.addGenerators();

        REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;

            provideDefaultLang("interface", langConsumer);
            provideDefaultLang("tooltips", langConsumer);
            CCAdvancements.provideLang(langConsumer);
            CCSoundEvents.provideLang(langConsumer);
            providePonderLang(langConsumer);
        });
    }

    private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
        String path = "assets/create_connected/lang/default/" + fileName + ".json";
        JsonElement jsonElement = FilesHelper.loadJsonResource(path);
        if (jsonElement == null) {
            throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            consumer.accept(key, value);
        }
    }

    private static void providePonderLang(BiConsumer<String, String> consumer) {
        // Register this since client init does not run during datagen
        PonderIndex.addPlugin(new CCPonderPlugin());

        PonderIndex.getLangAccess().provideLang(CreateConnected.MODID, consumer);
    }
}
