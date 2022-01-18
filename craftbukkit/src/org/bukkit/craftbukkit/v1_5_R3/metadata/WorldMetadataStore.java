// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.metadata;

import org.bukkit.metadata.MetadataStore;
import org.bukkit.World;
import org.bukkit.metadata.MetadataStoreBase;

public class WorldMetadataStore extends MetadataStoreBase<World> implements MetadataStore<World>
{
    protected String disambiguate(final World world, final String metadataKey) {
        return world.getUID().toString() + ":" + metadataKey;
    }
}
