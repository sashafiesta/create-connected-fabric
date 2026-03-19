package com.hlysine.create_connected.config;

import com.hlysine.create_connected.CreateConnected;
import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.createmod.catnip.config.ConfigBase;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Function;

public abstract class SyncConfigBase extends ConfigBase {

    private SimpleChannel syncChannel;
    private Function<CompoundTag, ? extends SyncConfig> messageSupplier;

    private static MinecraftServer currentServer;
    private static boolean lifecycleRegistered = false;

    public final CompoundTag getSyncConfig() {
        CompoundTag nbt = new CompoundTag();
        writeSyncConfig(nbt);
        if (children != null)
            for (ConfigBase child : children) {
                if (child instanceof SyncConfigBase syncChild) {
                    if (nbt.contains(child.getName()))
                        throw new RuntimeException("A sync config key starts with " + child.getName() + " but does not belong to the child");
                    nbt.put(child.getName(), syncChild.getSyncConfig());
                }
            }
        return nbt;
    }

    protected void writeSyncConfig(CompoundTag nbt) {
    }

    public final void setSyncConfig(CompoundTag config) {
        if (children != null)
            for (ConfigBase child : children) {
                if (child instanceof SyncConfigBase syncChild) {
                    CompoundTag nbt = config.getCompound(child.getName());
                    syncChild.readSyncConfig(nbt);
                }
            }
        readSyncConfig(config);
    }

    protected void readSyncConfig(CompoundTag nbt) {
    }

    public <T extends SyncConfig> void registerAsSyncRoot(
            Class<T> messageType,
            Function<FriendlyByteBuf, T> decoder,
            Function<CompoundTag, T> messageSupplier
    ) {
        syncChannel = new SimpleChannel(CreateConnected.asResource("config_" + getName()));
        syncChannel.registerS2CPacket(messageType, 0, decoder);
        this.messageSupplier = messageSupplier;

        if (!lifecycleRegistered) {
            ServerLifecycleEvents.SERVER_STARTED.register(server -> currentServer = server);
            ServerLifecycleEvents.SERVER_STOPPED.register(server -> currentServer = null);
            lifecycleRegistered = true;
        }

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            syncToPlayer(handler.getPlayer());
        });
    }

    @Override
    public void onLoad() {
        super.onLoad();
        syncToAllPlayers();
    }

    @Override
    public void onReload() {
        super.onReload();
        syncToAllPlayers();
    }

    public void syncToAllPlayers() {
        if (this.syncChannel == null) {
            return; // not sync root
        }
        if (currentServer == null) {
            CreateConnected.LOGGER.debug("Sync Config: Config sync skipped due to null server");
            return;
        }
            CreateConnected.LOGGER.debug("Sync Config: Sending server config to all players on reload");
        syncChannel.sendToClientsInServer(this.messageSupplier.apply(getSyncConfig()), currentServer);
    }

    public void syncToPlayer(ServerPlayer player) {
        if (player == null) return;
        if (this.syncChannel == null) return;
        CreateConnected.LOGGER.debug("Sync Config: Sending server config to " + player.getScoreboardName());
        syncChannel.sendToClient(this.messageSupplier.apply(getSyncConfig()), player);
    }

    public abstract static class SyncConfig implements S2CPacket {

        private final CompoundTag nbt;

        protected SyncConfig(CompoundTag nbt) {
            this.nbt = nbt;
        }

        protected abstract SyncConfigBase configInstance();

        @Override
        public void encode(FriendlyByteBuf buf) {
            buf.writeNbt(nbt);
        }

        static CompoundTag decode(FriendlyByteBuf buf) {
            return buf.readAnySizeNbt();
        }

        @Override
        @Environment(EnvType.CLIENT)
        public void handle(Minecraft client, ClientPacketListener listener, PacketSender responseSender, SimpleChannel channel) {
            client.execute(() -> {
                configInstance().setSyncConfig(nbt);
                CreateConnected.LOGGER.debug("Sync Config: Received and applied server config " + nbt.toString());
            });
        }
    }

}
