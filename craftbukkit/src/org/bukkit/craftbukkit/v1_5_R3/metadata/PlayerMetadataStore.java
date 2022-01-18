// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.metadata;

import org.bukkit.metadata.MetadataStore;
import org.bukkit.OfflinePlayer;
import org.bukkit.metadata.MetadataStoreBase;

public class PlayerMetadataStore extends MetadataStoreBase<OfflinePlayer> implements MetadataStore<OfflinePlayer>
{
    protected String disambiguate(final OfflinePlayer player, final String metadataKey) {
        return player.getName().toLowerCase() + ":" + metadataKey;
    }
}
