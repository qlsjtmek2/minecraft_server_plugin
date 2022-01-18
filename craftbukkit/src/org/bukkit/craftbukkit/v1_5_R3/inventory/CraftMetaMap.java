// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.ItemMeta;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;
import java.util.Map;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.MapMeta;

@DelegateDeserialization(SerializableMeta.class)
class CraftMetaMap extends CraftMetaItem implements MapMeta
{
    static final ItemMetaKey MAP_SCALING;
    static final byte SCALING_EMPTY = 0;
    static final byte SCALING_TRUE = 1;
    static final byte SCALING_FALSE = 2;
    private byte scaling;
    
    CraftMetaMap(final CraftMetaItem meta) {
        super(meta);
        this.scaling = 0;
        if (!(meta instanceof CraftMetaMap)) {
            return;
        }
        final CraftMetaMap map = (CraftMetaMap)meta;
        this.scaling = map.scaling;
    }
    
    CraftMetaMap(final NBTTagCompound tag) {
        super(tag);
        this.scaling = 0;
        if (tag.hasKey(CraftMetaMap.MAP_SCALING.NBT)) {
            this.scaling = (byte)(tag.getBoolean(CraftMetaMap.MAP_SCALING.NBT) ? 1 : 2);
        }
    }
    
    CraftMetaMap(final Map<String, Object> map) {
        super(map);
        this.scaling = 0;
        final Boolean scaling = SerializableMeta.getObject(Boolean.class, map, CraftMetaMap.MAP_SCALING.BUKKIT, true);
        if (scaling != null) {
            this.setScaling(scaling);
        }
    }
    
    void applyToItem(final NBTTagCompound tag) {
        super.applyToItem(tag);
        if (this.hasScaling()) {
            tag.setBoolean(CraftMetaMap.MAP_SCALING.NBT, this.isScaling());
        }
    }
    
    boolean applicableTo(final Material type) {
        switch (type) {
            case MAP: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    boolean isEmpty() {
        return super.isEmpty() && this.isMapEmpty();
    }
    
    boolean isMapEmpty() {
        return !this.hasScaling();
    }
    
    boolean hasScaling() {
        return this.scaling != 0;
    }
    
    public boolean isScaling() {
        return this.scaling == 1;
    }
    
    public void setScaling(final boolean scaling) {
        this.scaling = (byte)(scaling ? 1 : 2);
    }
    
    boolean equalsCommon(final CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        }
        if (meta instanceof CraftMetaMap) {
            final CraftMetaMap that = (CraftMetaMap)meta;
            return this.scaling == that.scaling;
        }
        return true;
    }
    
    boolean notUncommon(final CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaMap || this.isMapEmpty());
    }
    
    int applyHash() {
        int hash;
        final int original = hash = super.applyHash();
        if (this.hasScaling()) {
            hash ^= 572662306 << (this.isScaling() ? 1 : -1);
        }
        return (original != hash) ? (CraftMetaMap.class.hashCode() ^ hash) : hash;
    }
    
    public CraftMetaMap clone() {
        return (CraftMetaMap)super.clone();
    }
    
    ImmutableMap.Builder<String, Object> serialize(final ImmutableMap.Builder<String, Object> builder) {
        super.serialize(builder);
        if (this.hasScaling()) {
            builder.put(CraftMetaMap.MAP_SCALING.BUKKIT, this.isScaling());
        }
        return builder;
    }
    
    static {
        MAP_SCALING = new ItemMetaKey("map_is_scaling", "scaling");
    }
}
