package com.hlysine.create_connected.content.inventoryaccessport;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;

/**
 * This is a marker interface for all item handlers that redirect calls to other handlers.
 */
public interface WrappedItemHandler extends Storage<ItemVariant> {
}
