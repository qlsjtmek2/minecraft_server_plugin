// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.ItemMeta;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;
import net.minecraft.server.v1_5_R3.NBTBase;
import net.minecraft.server.v1_5_R3.NBTTagInt;
import java.util.Map;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import org.bukkit.Color;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.LeatherArmorMeta;

@DelegateDeserialization(SerializableMeta.class)
class CraftMetaLeatherArmor extends CraftMetaItem implements LeatherArmorMeta
{
    static final ItemMetaKey COLOR;
    private Color color;
    
    CraftMetaLeatherArmor(final CraftMetaItem meta) {
        super(meta);
        this.color = CraftItemFactory.DEFAULT_LEATHER_COLOR;
        if (!(meta instanceof CraftMetaLeatherArmor)) {
            return;
        }
        final CraftMetaLeatherArmor armorMeta = (CraftMetaLeatherArmor)meta;
        this.color = armorMeta.color;
    }
    
    CraftMetaLeatherArmor(final NBTTagCompound tag) {
        super(tag);
        this.color = CraftItemFactory.DEFAULT_LEATHER_COLOR;
        if (tag.hasKey(CraftMetaLeatherArmor.DISPLAY.NBT)) {
            final NBTTagCompound display = tag.getCompound(CraftMetaLeatherArmor.DISPLAY.NBT);
            if (display.hasKey(CraftMetaLeatherArmor.COLOR.NBT)) {
                this.color = Color.fromRGB(display.getInt(CraftMetaLeatherArmor.COLOR.NBT));
            }
        }
    }
    
    CraftMetaLeatherArmor(final Map<String, Object> map) {
        super(map);
        this.color = CraftItemFactory.DEFAULT_LEATHER_COLOR;
        this.setColor(SerializableMeta.getObject(Color.class, map, CraftMetaLeatherArmor.COLOR.BUKKIT, true));
    }
    
    void applyToItem(final NBTTagCompound itemTag) {
        super.applyToItem(itemTag);
        if (this.hasColor()) {
            this.setDisplayTag(itemTag, CraftMetaLeatherArmor.COLOR.NBT, new NBTTagInt(CraftMetaLeatherArmor.COLOR.NBT, this.color.asRGB()));
        }
    }
    
    boolean isEmpty() {
        return super.isEmpty() && this.isLeatherArmorEmpty();
    }
    
    boolean isLeatherArmorEmpty() {
        return !this.hasColor();
    }
    
    boolean applicableTo(final Material type) {
        switch (type) {
            case LEATHER_HELMET:
            case LEATHER_CHESTPLATE:
            case LEATHER_LEGGINGS:
            case LEATHER_BOOTS: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public CraftMetaLeatherArmor clone() {
        return (CraftMetaLeatherArmor)super.clone();
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void setColor(final Color color) {
        this.color = ((color == null) ? CraftItemFactory.DEFAULT_LEATHER_COLOR : color);
    }
    
    boolean hasColor() {
        return !CraftItemFactory.DEFAULT_LEATHER_COLOR.equals(this.color);
    }
    
    ImmutableMap.Builder<String, Object> serialize(final ImmutableMap.Builder<String, Object> builder) {
        super.serialize(builder);
        if (this.hasColor()) {
            builder.put(CraftMetaLeatherArmor.COLOR.BUKKIT, this.color);
        }
        return builder;
    }
    
    boolean equalsCommon(final CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        }
        if (meta instanceof CraftMetaLeatherArmor) {
            final CraftMetaLeatherArmor that = (CraftMetaLeatherArmor)meta;
            return this.color.equals(that.color);
        }
        return true;
    }
    
    boolean notUncommon(final CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaLeatherArmor || this.isLeatherArmorEmpty());
    }
    
    int applyHash() {
        int hash;
        final int original = hash = super.applyHash();
        if (this.hasColor()) {
            hash ^= this.color.hashCode();
        }
        return (original != hash) ? (CraftMetaSkull.class.hashCode() ^ hash) : hash;
    }
    
    static {
        COLOR = new ItemMetaKey("color");
    }
}
