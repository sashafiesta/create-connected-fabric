package com.hlysine.create_connected.compat;

import mezz.jei.api.runtime.IIngredientManager;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resources.ResourceLocation;

/*
 * Base class of the two feature refresh events.
 *
 * @see FeatureRefreshEvent.Pre
 * @see FeatureRefreshEvent.Post
 */
public class FeatureRefreshEvent {
    public static final Event<PreCallback> PRE = EventFactory.createArrayBacked(PreCallback.class, callbacks -> event -> {
        for (PreCallback callback : callbacks)
            callback.onPre(event);
    });

    public static final Event<PostCallback> POST = EventFactory.createArrayBacked(PostCallback.class, callbacks -> event -> {
        for (PostCallback callback : callbacks)
            callback.onPost(event);
    });

    private final ResourceLocation jeiPluginId;
    private final IIngredientManager ingredientManager;

    protected FeatureRefreshEvent(ResourceLocation jeiPluginId, IIngredientManager ingredientManager) {
        this.jeiPluginId = jeiPluginId;
        this.ingredientManager = ingredientManager;
    }

    public ResourceLocation getJeiPluginId() {
        return jeiPluginId;
    }

    public IIngredientManager getIngredientManager() {
        return ingredientManager;
    }

    /**
     * Fired before Create: Connected updates the JEI item list according to enabled features.
     */
    public static class Pre extends FeatureRefreshEvent {
        public Pre(ResourceLocation jeiPluginId, IIngredientManager ingredientManager) {
            super(jeiPluginId, ingredientManager);
        }
    }

    /**
     * Fired after Create: Connected updates the JEI item list according to enabled features.
     */
    public static class Post extends FeatureRefreshEvent {
        public Post(ResourceLocation jeiPluginId, IIngredientManager ingredientManager) {
            super(jeiPluginId, ingredientManager);
        }
    }

    @FunctionalInterface
    public interface PreCallback {
        void onPre(Pre event);
    }

    @FunctionalInterface
    public interface PostCallback {
        void onPost(Post event);
    }
}
