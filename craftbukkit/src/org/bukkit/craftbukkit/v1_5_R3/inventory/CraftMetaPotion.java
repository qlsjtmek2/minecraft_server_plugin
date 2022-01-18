// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.ItemMeta;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.Validate;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import net.minecraft.server.v1_5_R3.NBTBase;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.server.v1_5_R3.NBTTagList;
import org.bukkit.potion.PotionEffectType;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import java.util.Collection;
import java.util.ArrayList;
import org.bukkit.potion.PotionEffect;
import java.util.List;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.PotionMeta;

@DelegateDeserialization(SerializableMeta.class)
class CraftMetaPotion extends CraftMetaItem implements PotionMeta
{
    static final ItemMetaKey AMPLIFIER;
    static final ItemMetaKey AMBIENT;
    static final ItemMetaKey DURATION;
    static final ItemMetaKey POTION_EFFECTS;
    static final ItemMetaKey ID;
    private List<PotionEffect> customEffects;
    
    CraftMetaPotion(final CraftMetaItem meta) {
        super(meta);
        if (!(meta instanceof CraftMetaPotion)) {
            return;
        }
        final CraftMetaPotion potionMeta = (CraftMetaPotion)meta;
        if (potionMeta.hasCustomEffects()) {
            this.customEffects = new ArrayList<PotionEffect>(potionMeta.customEffects);
        }
    }
    
    CraftMetaPotion(final NBTTagCompound tag) {
        super(tag);
        if (tag.hasKey(CraftMetaPotion.POTION_EFFECTS.NBT)) {
            final NBTTagList list = tag.getList(CraftMetaPotion.POTION_EFFECTS.NBT);
            final int length = list.size();
            if (length > 0) {
                this.customEffects = new ArrayList<PotionEffect>(length);
                for (int i = 0; i < length; ++i) {
                    final NBTTagCompound effect = (NBTTagCompound)list.get(i);
                    final PotionEffectType type = PotionEffectType.getById(effect.getByte(CraftMetaPotion.ID.NBT));
                    final int amp = effect.getByte(CraftMetaPotion.AMPLIFIER.NBT);
                    final int duration = effect.getInt(CraftMetaPotion.DURATION.NBT);
                    final boolean ambient = effect.getBoolean(CraftMetaPotion.AMBIENT.NBT);
                    this.customEffects.add(new PotionEffect(type, duration, amp, ambient));
                }
            }
        }
    }
    
    CraftMetaPotion(final Map<String, Object> map) {
        super(map);
        final Iterable<?> rawEffectList = SerializableMeta.getObject((Class<Iterable<?>>)Iterable.class, map, CraftMetaPotion.POTION_EFFECTS.BUKKIT, true);
        if (rawEffectList == null) {
            return;
        }
        for (final Object obj : rawEffectList) {
            if (!(obj instanceof PotionEffect)) {
                throw new IllegalArgumentException("Object in effect list is not valid. " + obj.getClass());
            }
            this.addCustomEffect((PotionEffect)obj, true);
        }
    }
    
    void applyToItem(final NBTTagCompound tag) {
        super.applyToItem(tag);
        if (this.hasCustomEffects()) {
            final NBTTagList effectList = new NBTTagList();
            tag.set(CraftMetaPotion.POTION_EFFECTS.NBT, effectList);
            for (final PotionEffect effect : this.customEffects) {
                final NBTTagCompound effectData = new NBTTagCompound();
                effectData.setByte(CraftMetaPotion.ID.NBT, (byte)effect.getType().getId());
                effectData.setByte(CraftMetaPotion.AMPLIFIER.NBT, (byte)effect.getAmplifier());
                effectData.setInt(CraftMetaPotion.DURATION.NBT, effect.getDuration());
                effectData.setBoolean(CraftMetaPotion.AMBIENT.NBT, effect.isAmbient());
                effectList.add(effectData);
            }
        }
    }
    
    boolean isEmpty() {
        return super.isEmpty() && this.isPotionEmpty();
    }
    
    boolean isPotionEmpty() {
        return !this.hasCustomEffects();
    }
    
    boolean applicableTo(final Material type) {
        switch (type) {
            case POTION: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public CraftMetaPotion clone() {
        final CraftMetaPotion clone = (CraftMetaPotion)super.clone();
        if (this.customEffects != null) {
            clone.customEffects = new ArrayList<PotionEffect>(this.customEffects);
        }
        return clone;
    }
    
    public boolean hasCustomEffects() {
        return this.customEffects != null && !this.customEffects.isEmpty();
    }
    
    public List<PotionEffect> getCustomEffects() {
        if (this.hasCustomEffects()) {
            return (List<PotionEffect>)ImmutableList.copyOf((Collection<?>)this.customEffects);
        }
        return (List<PotionEffect>)ImmutableList.of();
    }
    
    public boolean addCustomEffect(final PotionEffect effect, final boolean overwrite) {
        Validate.notNull(effect, "Potion effect must not be null");
        final int index = this.indexOfEffect(effect.getType());
        if (index == -1) {
            if (this.customEffects == null) {
                this.customEffects = new ArrayList<PotionEffect>();
            }
            this.customEffects.add(effect);
            return true;
        }
        if (!overwrite) {
            return false;
        }
        final PotionEffect old = this.customEffects.get(index);
        if (old.getAmplifier() == effect.getAmplifier() && old.getDuration() == effect.getDuration() && old.isAmbient() == effect.isAmbient()) {
            return false;
        }
        this.customEffects.set(index, effect);
        return true;
    }
    
    public boolean removeCustomEffect(final PotionEffectType type) {
        Validate.notNull(type, "Potion effect type must not be null");
        if (!this.hasCustomEffects()) {
            return false;
        }
        boolean changed = false;
        final Iterator<PotionEffect> iterator = this.customEffects.iterator();
        while (iterator.hasNext()) {
            final PotionEffect effect = iterator.next();
            if (effect.getType() == type) {
                iterator.remove();
                changed = true;
            }
        }
        return changed;
    }
    
    public boolean hasCustomEffect(final PotionEffectType type) {
        Validate.notNull(type, "Potion effect type must not be null");
        return this.indexOfEffect(type) != -1;
    }
    
    public boolean setMainEffect(final PotionEffectType type) {
        Validate.notNull(type, "Potion effect type must not be null");
        final int index = this.indexOfEffect(type);
        if (index == -1 || index == 0) {
            return false;
        }
        final PotionEffect old = this.customEffects.get(0);
        this.customEffects.set(0, this.customEffects.get(index));
        this.customEffects.set(index, old);
        return true;
    }
    
    private int indexOfEffect(final PotionEffectType type) {
        if (!this.hasCustomEffects()) {
            return -1;
        }
        for (int i = 0; i < this.customEffects.size(); ++i) {
            if (this.customEffects.get(i).getType().equals(type)) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean clearCustomEffects() {
        final boolean changed = this.hasCustomEffects();
        this.customEffects = null;
        return changed;
    }
    
    int applyHash() {
        int hash;
        final int original = hash = super.applyHash();
        if (this.hasCustomEffects()) {
            hash = 73 * hash + this.customEffects.hashCode();
        }
        return (original != hash) ? (CraftMetaPotion.class.hashCode() ^ hash) : hash;
    }
    
    public boolean equalsCommon(final CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        }
        if (meta instanceof CraftMetaPotion) {
            final CraftMetaPotion that = (CraftMetaPotion)meta;
            return this.hasCustomEffects() ? (that.hasCustomEffects() && this.customEffects.equals(that.customEffects)) : (!that.hasCustomEffects());
        }
        return true;
    }
    
    boolean notUncommon(final CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaPotion || this.isPotionEmpty());
    }
    
    ImmutableMap.Builder<String, Object> serialize(final ImmutableMap.Builder<String, Object> builder) {
        super.serialize(builder);
        if (this.hasCustomEffects()) {
            builder.put(CraftMetaPotion.POTION_EFFECTS.BUKKIT, ImmutableList.copyOf((Collection<?>)this.customEffects));
        }
        return builder;
    }
    
    static {
        AMPLIFIER = new ItemMetaKey("Amplifier", "amplifier");
        AMBIENT = new ItemMetaKey("Ambient", "ambient");
        DURATION = new ItemMetaKey("Duration", "duration");
        POTION_EFFECTS = new ItemMetaKey("CustomPotionEffects", "custom-effects");
        ID = new ItemMetaKey("Id", "potion-id");
    }
}
