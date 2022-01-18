// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.ItemMeta;
import com.google.common.collect.ImmutableMap;
import com.google.common.base.Strings;
import org.bukkit.Material;
import java.util.Map;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.SkullMeta;

@DelegateDeserialization(SerializableMeta.class)
class CraftMetaSkull extends CraftMetaItem implements SkullMeta
{
    static final ItemMetaKey SKULL_OWNER;
    static final int MAX_OWNER_LENGTH = 16;
    private String player;
    
    CraftMetaSkull(final CraftMetaItem meta) {
        super(meta);
        if (!(meta instanceof CraftMetaSkull)) {
            return;
        }
        final CraftMetaSkull skullMeta = (CraftMetaSkull)meta;
        this.player = skullMeta.player;
    }
    
    CraftMetaSkull(final NBTTagCompound tag) {
        super(tag);
        if (tag.hasKey(CraftMetaSkull.SKULL_OWNER.NBT)) {
            this.player = tag.getString(CraftMetaSkull.SKULL_OWNER.NBT);
        }
    }
    
    CraftMetaSkull(final Map<String, Object> map) {
        super(map);
        this.setOwner(SerializableMeta.getString(map, CraftMetaSkull.SKULL_OWNER.BUKKIT, true));
    }
    
    void applyToItem(final NBTTagCompound tag) {
        super.applyToItem(tag);
        if (this.hasOwner()) {
            tag.setString(CraftMetaSkull.SKULL_OWNER.NBT, this.player);
        }
    }
    
    boolean isEmpty() {
        return super.isEmpty() && this.isSkullEmpty();
    }
    
    boolean isSkullEmpty() {
        return !this.hasOwner();
    }
    
    boolean applicableTo(final Material type) {
        switch (type) {
            case SKULL_ITEM: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public CraftMetaSkull clone() {
        return (CraftMetaSkull)super.clone();
    }
    
    public boolean hasOwner() {
        return !Strings.isNullOrEmpty(this.player);
    }
    
    public String getOwner() {
        return this.player;
    }
    
    public boolean setOwner(final String name) {
        if (name != null && name.length() > 16) {
            return false;
        }
        this.player = name;
        return true;
    }
    
    int applyHash() {
        int hash;
        final int original = hash = super.applyHash();
        if (this.hasOwner()) {
            hash = 61 * hash + this.player.hashCode();
        }
        return (original != hash) ? (CraftMetaSkull.class.hashCode() ^ hash) : hash;
    }
    
    boolean equalsCommon(final CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        }
        if (meta instanceof CraftMetaSkull) {
            final CraftMetaSkull that = (CraftMetaSkull)meta;
            return this.hasOwner() ? (that.hasOwner() && this.player.equals(that.player)) : (!that.hasOwner());
        }
        return true;
    }
    
    boolean notUncommon(final CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaSkull || this.isSkullEmpty());
    }
    
    ImmutableMap.Builder<String, Object> serialize(final ImmutableMap.Builder<String, Object> builder) {
        super.serialize(builder);
        if (this.hasOwner()) {
            return (ImmutableMap.Builder<String, Object>)builder.put(CraftMetaSkull.SKULL_OWNER.BUKKIT, this.player);
        }
        return builder;
    }
    
    static {
        SKULL_OWNER = new ItemMetaKey("SkullOwner", "skull-owner");
    }
}
