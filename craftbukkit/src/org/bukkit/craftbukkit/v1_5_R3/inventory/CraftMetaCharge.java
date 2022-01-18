// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.ItemMeta;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;
import net.minecraft.server.v1_5_R3.NBTBase;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import java.util.Map;
import org.bukkit.FireworkEffect;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.FireworkEffectMeta;

@DelegateDeserialization(SerializableMeta.class)
class CraftMetaCharge extends CraftMetaItem implements FireworkEffectMeta
{
    static final ItemMetaKey EXPLOSION;
    private FireworkEffect effect;
    
    CraftMetaCharge(final CraftMetaItem meta) {
        super(meta);
        if (meta instanceof CraftMetaCharge) {
            this.effect = ((CraftMetaCharge)meta).effect;
        }
    }
    
    CraftMetaCharge(final Map<String, Object> map) {
        super(map);
        this.setEffect(SerializableMeta.getObject(FireworkEffect.class, map, CraftMetaCharge.EXPLOSION.BUKKIT, true));
    }
    
    CraftMetaCharge(final NBTTagCompound tag) {
        super(tag);
        if (tag.hasKey(CraftMetaCharge.EXPLOSION.NBT)) {
            this.effect = CraftMetaFirework.getEffect(tag.getCompound(CraftMetaCharge.EXPLOSION.NBT));
        }
    }
    
    public void setEffect(final FireworkEffect effect) {
        this.effect = effect;
    }
    
    public boolean hasEffect() {
        return this.effect != null;
    }
    
    public FireworkEffect getEffect() {
        return this.effect;
    }
    
    void applyToItem(final NBTTagCompound itemTag) {
        super.applyToItem(itemTag);
        if (this.hasEffect()) {
            itemTag.set(CraftMetaCharge.EXPLOSION.NBT, CraftMetaFirework.getExplosion(this.effect));
        }
    }
    
    boolean applicableTo(final Material type) {
        switch (type) {
            case FIREWORK_CHARGE: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    boolean isEmpty() {
        return super.isEmpty() && !this.hasChargeMeta();
    }
    
    boolean hasChargeMeta() {
        return this.hasEffect();
    }
    
    boolean equalsCommon(final CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        }
        if (meta instanceof CraftMetaCharge) {
            final CraftMetaCharge that = (CraftMetaCharge)meta;
            return this.hasEffect() ? (that.hasEffect() && this.effect.equals(that.effect)) : (!that.hasEffect());
        }
        return true;
    }
    
    boolean notUncommon(final CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaCharge || !this.hasChargeMeta());
    }
    
    int applyHash() {
        int hash;
        final int original = hash = super.applyHash();
        if (this.hasEffect()) {
            hash = 61 * hash + this.effect.hashCode();
        }
        return (hash != original) ? (CraftMetaCharge.class.hashCode() ^ hash) : hash;
    }
    
    public CraftMetaCharge clone() {
        return (CraftMetaCharge)super.clone();
    }
    
    ImmutableMap.Builder<String, Object> serialize(final ImmutableMap.Builder<String, Object> builder) {
        super.serialize(builder);
        if (this.hasEffect()) {
            builder.put(CraftMetaCharge.EXPLOSION.BUKKIT, this.effect);
        }
        return builder;
    }
    
    static {
        EXPLOSION = new ItemMetaKey("Explosion", "firework-effect");
    }
}
