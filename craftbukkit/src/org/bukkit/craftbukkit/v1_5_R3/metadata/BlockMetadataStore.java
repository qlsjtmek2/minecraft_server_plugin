// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.metadata;

import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.MetadataValue;
import java.util.List;
import org.bukkit.World;
import org.bukkit.metadata.MetadataStore;
import org.bukkit.block.Block;
import org.bukkit.metadata.MetadataStoreBase;

public class BlockMetadataStore extends MetadataStoreBase<Block> implements MetadataStore<Block>
{
    private final World owningWorld;
    
    public BlockMetadataStore(final World owningWorld) {
        this.owningWorld = owningWorld;
    }
    
    protected String disambiguate(final Block block, final String metadataKey) {
        return Integer.toString(block.getX()) + ":" + Integer.toString(block.getY()) + ":" + Integer.toString(block.getZ()) + ":" + metadataKey;
    }
    
    public List<MetadataValue> getMetadata(final Block block, final String metadataKey) {
        if (block.getWorld() == this.owningWorld) {
            return super.getMetadata(block, metadataKey);
        }
        throw new IllegalArgumentException("Block does not belong to world " + this.owningWorld.getName());
    }
    
    public boolean hasMetadata(final Block block, final String metadataKey) {
        if (block.getWorld() == this.owningWorld) {
            return super.hasMetadata(block, metadataKey);
        }
        throw new IllegalArgumentException("Block does not belong to world " + this.owningWorld.getName());
    }
    
    public void removeMetadata(final Block block, final String metadataKey, final Plugin owningPlugin) {
        if (block.getWorld() == this.owningWorld) {
            super.removeMetadata(block, metadataKey, owningPlugin);
            return;
        }
        throw new IllegalArgumentException("Block does not belong to world " + this.owningWorld.getName());
    }
    
    public void setMetadata(final Block block, final String metadataKey, final MetadataValue newMetadataValue) {
        if (block.getWorld() == this.owningWorld) {
            super.setMetadata(block, metadataKey, newMetadataValue);
            return;
        }
        throw new IllegalArgumentException("Block does not belong to world " + this.owningWorld.getName());
    }
}
