// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.metadata;

import org.bukkit.metadata.MetadataStore;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataStoreBase;

public class EntityMetadataStore extends MetadataStoreBase<Entity> implements MetadataStore<Entity>
{
    protected String disambiguate(final Entity entity, final String metadataKey) {
        return entity.getUniqueId().toString() + ":" + metadataKey;
    }
}
