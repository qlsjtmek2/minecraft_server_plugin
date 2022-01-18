// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import java.util.NoSuchElementException;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.lang.Validate;
import java.lang.reflect.Constructor;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Annotation;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.base.Strings;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R3.Overridden;
import net.minecraft.server.v1_5_R3.NBTBase;
import java.util.Iterator;
import net.minecraft.server.v1_5_R3.NBTTagList;
import net.minecraft.server.v1_5_R3.NBTTagString;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import org.bukkit.enchantments.Enchantment;
import java.util.Map;
import java.util.List;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.ItemMeta;

@DelegateDeserialization(SerializableMeta.class)
class CraftMetaItem implements ItemMeta, Repairable
{
    static final ItemMetaKey NAME;
    static final ItemMetaKey DISPLAY;
    static final ItemMetaKey LORE;
    static final ItemMetaKey ENCHANTMENTS;
    static final ItemMetaKey ENCHANTMENTS_ID;
    static final ItemMetaKey ENCHANTMENTS_LVL;
    static final ItemMetaKey REPAIR;
    private String displayName;
    private List<String> lore;
    private Map<Enchantment, Integer> enchantments;
    private int repairCost;
    
    CraftMetaItem(final CraftMetaItem meta) {
        if (meta == null) {
            return;
        }
        this.displayName = meta.displayName;
        if (meta.hasLore()) {
            this.lore = new ArrayList<String>(meta.lore);
        }
        if (meta.hasEnchants()) {
            this.enchantments = new HashMap<Enchantment, Integer>(meta.enchantments);
        }
        this.repairCost = meta.repairCost;
    }
    
    CraftMetaItem(final NBTTagCompound tag) {
        if (tag.hasKey(CraftMetaItem.DISPLAY.NBT)) {
            final NBTTagCompound display = tag.getCompound(CraftMetaItem.DISPLAY.NBT);
            if (display.hasKey(CraftMetaItem.NAME.NBT)) {
                this.displayName = display.getString(CraftMetaItem.NAME.NBT);
            }
            if (display.hasKey(CraftMetaItem.LORE.NBT)) {
                final NBTTagList list = display.getList(CraftMetaItem.LORE.NBT);
                this.lore = new ArrayList<String>(list.size());
                for (int index = 0; index < list.size(); ++index) {
                    final String line = ((NBTTagString)list.get(index)).data;
                    this.lore.add(line);
                }
            }
        }
        this.enchantments = buildEnchantments(tag, CraftMetaItem.ENCHANTMENTS);
        if (tag.hasKey(CraftMetaItem.REPAIR.NBT)) {
            this.repairCost = tag.getInt(CraftMetaItem.REPAIR.NBT);
        }
    }
    
    static Map<Enchantment, Integer> buildEnchantments(final NBTTagCompound tag, final ItemMetaKey key) {
        if (!tag.hasKey(key.NBT)) {
            return null;
        }
        final NBTTagList ench = tag.getList(key.NBT);
        final Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>(ench.size());
        for (int i = 0; i < ench.size(); ++i) {
            final int id = 0xFFFF & ((NBTTagCompound)ench.get(i)).getShort(CraftMetaItem.ENCHANTMENTS_ID.NBT);
            final int level = 0xFFFF & ((NBTTagCompound)ench.get(i)).getShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT);
            enchantments.put(Enchantment.getById(id), level);
        }
        return enchantments;
    }
    
    CraftMetaItem(final Map<String, Object> map) {
        this.setDisplayName(SerializableMeta.getString(map, CraftMetaItem.NAME.BUKKIT, true));
        final Iterable<?> lore = SerializableMeta.getObject((Class<Iterable<?>>)Iterable.class, map, CraftMetaItem.LORE.BUKKIT, true);
        if (lore != null) {
            safelyAdd(lore, this.lore = new ArrayList<String>(), Integer.MAX_VALUE);
        }
        this.enchantments = buildEnchantments(map, CraftMetaItem.ENCHANTMENTS);
        final Integer repairCost = SerializableMeta.getObject(Integer.class, map, CraftMetaItem.REPAIR.BUKKIT, true);
        if (repairCost != null) {
            this.setRepairCost(repairCost);
        }
    }
    
    static Map<Enchantment, Integer> buildEnchantments(final Map<String, Object> map, final ItemMetaKey key) {
        final Map<?, ?> ench = SerializableMeta.getObject((Class<Map<?, ?>>)Map.class, map, key.BUKKIT, true);
        if (ench == null) {
            return null;
        }
        final Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>(ench.size());
        for (final Map.Entry<?, ?> entry : ench.entrySet()) {
            final Enchantment enchantment = Enchantment.getByName(entry.getKey().toString());
            if (enchantment != null && entry.getValue() instanceof Integer) {
                enchantments.put(enchantment, (Integer)entry.getValue());
            }
        }
        return enchantments;
    }
    
    @Overridden
    void applyToItem(final NBTTagCompound itemTag) {
        if (this.hasDisplayName()) {
            this.setDisplayTag(itemTag, CraftMetaItem.NAME.NBT, new NBTTagString(CraftMetaItem.NAME.NBT, this.displayName));
        }
        if (this.hasLore()) {
            this.setDisplayTag(itemTag, CraftMetaItem.LORE.NBT, createStringList(this.lore, CraftMetaItem.LORE));
        }
        applyEnchantments(this.enchantments, itemTag, CraftMetaItem.ENCHANTMENTS);
        if (this.hasRepairCost()) {
            itemTag.setInt(CraftMetaItem.REPAIR.NBT, this.repairCost);
        }
    }
    
    static NBTTagList createStringList(final List<String> list, final ItemMetaKey key) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        final NBTTagList tagList = new NBTTagList(key.NBT);
        for (final String value : list) {
            tagList.add(new NBTTagString("", value));
        }
        return tagList;
    }
    
    static void applyEnchantments(final Map<Enchantment, Integer> enchantments, final NBTTagCompound tag, final ItemMetaKey key) {
        if (enchantments == null || enchantments.size() == 0) {
            return;
        }
        final NBTTagList list = new NBTTagList(key.NBT);
        for (final Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            final NBTTagCompound subtag = new NBTTagCompound();
            subtag.setShort(CraftMetaItem.ENCHANTMENTS_ID.NBT, (short)entry.getKey().getId());
            subtag.setShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT, (short)(Object)entry.getValue());
            list.add(subtag);
        }
        tag.set(key.NBT, list);
    }
    
    void setDisplayTag(final NBTTagCompound tag, final String key, final NBTBase value) {
        final NBTTagCompound display = tag.getCompound(CraftMetaItem.DISPLAY.NBT);
        if (!tag.hasKey(CraftMetaItem.DISPLAY.NBT)) {
            tag.setCompound(CraftMetaItem.DISPLAY.NBT, display);
        }
        display.set(key, value);
    }
    
    @Overridden
    boolean applicableTo(final Material type) {
        return type != Material.AIR;
    }
    
    @Overridden
    boolean isEmpty() {
        return !this.hasDisplayName() && !this.hasEnchants() && !this.hasLore();
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public final void setDisplayName(final String name) {
        this.displayName = name;
    }
    
    public boolean hasDisplayName() {
        return !Strings.isNullOrEmpty(this.displayName);
    }
    
    public boolean hasLore() {
        return this.lore != null && !this.lore.isEmpty();
    }
    
    public boolean hasRepairCost() {
        return this.repairCost > 0;
    }
    
    public boolean hasEnchant(final Enchantment ench) {
        return this.hasEnchants() && this.enchantments.containsKey(ench);
    }
    
    public int getEnchantLevel(final Enchantment ench) {
        final Integer level = this.hasEnchants() ? this.enchantments.get(ench) : null;
        if (level == null) {
            return 0;
        }
        return level;
    }
    
    public Map<Enchantment, Integer> getEnchants() {
        return (Map<Enchantment, Integer>)(this.hasEnchants() ? ImmutableMap.copyOf((Map<?, ?>)this.enchantments) : ImmutableMap.of());
    }
    
    public boolean addEnchant(final Enchantment ench, final int level, final boolean ignoreRestrictions) {
        if (this.enchantments == null) {
            this.enchantments = new HashMap<Enchantment, Integer>(4);
        }
        if (ignoreRestrictions || (level >= ench.getStartLevel() && level <= ench.getMaxLevel())) {
            final Integer old = this.enchantments.put(ench, level);
            return old == null || old != level;
        }
        return false;
    }
    
    public boolean removeEnchant(final Enchantment ench) {
        return this.hasEnchants() && this.enchantments.remove(ench) != null;
    }
    
    public boolean hasEnchants() {
        return this.enchantments != null && !this.enchantments.isEmpty();
    }
    
    public boolean hasConflictingEnchant(final Enchantment ench) {
        return checkConflictingEnchants(this.enchantments, ench);
    }
    
    public List<String> getLore() {
        return (this.lore == null) ? null : new ArrayList<String>(this.lore);
    }
    
    public void setLore(final List<String> lore) {
        if (lore == null) {
            this.lore = null;
        }
        else if (this.lore == null) {
            safelyAdd(lore, this.lore = new ArrayList<String>(lore.size()), Integer.MAX_VALUE);
        }
        else {
            this.lore.clear();
            safelyAdd(lore, this.lore, Integer.MAX_VALUE);
        }
    }
    
    public int getRepairCost() {
        return this.repairCost;
    }
    
    public void setRepairCost(final int cost) {
        this.repairCost = cost;
    }
    
    public final boolean equals(final Object object) {
        return object != null && (object == this || (object instanceof CraftMetaItem && CraftItemFactory.instance().equals(this, (ItemMeta)object)));
    }
    
    @Overridden
    boolean equalsCommon(final CraftMetaItem that) {
        if (this.hasDisplayName()) {
            if (!that.hasDisplayName() || !this.displayName.equals(that.displayName)) {
                return false;
            }
        }
        else if (that.hasDisplayName()) {
            return false;
        }
        if (this.hasEnchants()) {
            if (!that.hasEnchants() || !this.enchantments.equals(that.enchantments)) {
                return false;
            }
        }
        else if (that.hasEnchants()) {
            return false;
        }
        if (this.hasLore()) {
            if (!that.hasLore() || !this.lore.equals(that.lore)) {
                return false;
            }
        }
        else if (that.hasLore()) {
            return false;
        }
        if (!(this.hasRepairCost() ? (!that.hasRepairCost() || this.repairCost != that.repairCost) : that.hasRepairCost())) {
            return true;
        }
        return false;
    }
    
    @Overridden
    boolean notUncommon(final CraftMetaItem meta) {
        return true;
    }
    
    public final int hashCode() {
        return this.applyHash();
    }
    
    @Overridden
    int applyHash() {
        int hash = 3;
        hash = 61 * hash + (this.hasDisplayName() ? this.displayName.hashCode() : 0);
        hash = 61 * hash + (this.hasLore() ? this.lore.hashCode() : 0);
        hash = 61 * hash + (this.hasEnchants() ? this.enchantments.hashCode() : 0);
        hash = 61 * hash + (this.hasRepairCost() ? this.repairCost : 0);
        return hash;
    }
    
    @Overridden
    public CraftMetaItem clone() {
        try {
            final CraftMetaItem clone = (CraftMetaItem)super.clone();
            if (this.lore != null) {
                clone.lore = new ArrayList<String>(this.lore);
            }
            if (this.enchantments != null) {
                clone.enchantments = new HashMap<Enchantment, Integer>(this.enchantments);
            }
            return clone;
        }
        catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
    
    public final Map<String, Object> serialize() {
        final ImmutableMap.Builder<String, Object> map = ImmutableMap.builder();
        map.put("meta-type", SerializableMeta.classMap.get(this.getClass()));
        this.serialize(map);
        return map.build();
    }
    
    @Overridden
    ImmutableMap.Builder<String, Object> serialize(final ImmutableMap.Builder<String, Object> builder) {
        if (this.hasDisplayName()) {
            builder.put(CraftMetaItem.NAME.BUKKIT, this.displayName);
        }
        if (this.hasLore()) {
            builder.put(CraftMetaItem.LORE.BUKKIT, ImmutableList.copyOf((Collection<?>)this.lore));
        }
        serializeEnchantments(this.enchantments, builder, CraftMetaItem.ENCHANTMENTS);
        if (this.hasRepairCost()) {
            builder.put(CraftMetaItem.REPAIR.BUKKIT, this.repairCost);
        }
        return builder;
    }
    
    static void serializeEnchantments(final Map<Enchantment, Integer> enchantments, final ImmutableMap.Builder<String, Object> builder, final ItemMetaKey key) {
        if (enchantments == null || enchantments.isEmpty()) {
            return;
        }
        final ImmutableMap.Builder<String, Integer> enchants = ImmutableMap.builder();
        for (final Map.Entry<? extends Enchantment, Integer> enchant : enchantments.entrySet()) {
            enchants.put(((Enchantment)enchant.getKey()).getName(), enchant.getValue());
        }
        builder.put(key.BUKKIT, enchants.build());
    }
    
    static void safelyAdd(final Iterable<?> addFrom, final Collection<String> addTo, final int maxItemLength) {
        if (addFrom == null) {
            return;
        }
        for (final Object object : addFrom) {
            if (!(object instanceof String)) {
                if (object != null) {
                    throw new IllegalArgumentException(addFrom + " cannot contain non-string " + object.getClass().getName());
                }
                addTo.add("");
            }
            else {
                String page = object.toString();
                if (page.length() > maxItemLength) {
                    page = page.substring(0, maxItemLength);
                }
                addTo.add(page);
            }
        }
    }
    
    static boolean checkConflictingEnchants(final Map<Enchantment, Integer> enchantments, final Enchantment ench) {
        if (enchantments == null || enchantments.isEmpty()) {
            return false;
        }
        for (final Enchantment enchant : enchantments.keySet()) {
            if (enchant.conflictsWith(ench)) {
                return true;
            }
        }
        return false;
    }
    
    public final String toString() {
        return SerializableMeta.classMap.get(this.getClass()) + "_META:" + this.serialize();
    }
    
    static {
        NAME = new ItemMetaKey("Name", "display-name");
        DISPLAY = new ItemMetaKey("display");
        LORE = new ItemMetaKey("Lore", "lore");
        ENCHANTMENTS = new ItemMetaKey("ench", "enchants");
        ENCHANTMENTS_ID = new ItemMetaKey("id");
        ENCHANTMENTS_LVL = new ItemMetaKey("lvl");
        REPAIR = new ItemMetaKey("RepairCost", "repair-cost");
    }
    
    static class ItemMetaKey
    {
        final String BUKKIT;
        final String NBT;
        
        ItemMetaKey(final String both) {
            this(both, both);
        }
        
        ItemMetaKey(final String nbt, final String bukkit) {
            this.NBT = nbt;
            this.BUKKIT = bukkit;
        }
        
        @Retention(RetentionPolicy.SOURCE)
        @Target({ ElementType.FIELD })
        @interface Specific {
            To value();
            
            public enum To
            {
                BUKKIT, 
                NBT;
            }
        }
    }
    
    @SerializableAs("ItemMeta")
    public static class SerializableMeta implements ConfigurationSerializable
    {
        static final String TYPE_FIELD = "meta-type";
        static final ImmutableMap<Class<? extends CraftMetaItem>, String> classMap;
        static final ImmutableMap<String, Constructor<? extends CraftMetaItem>> constructorMap;
        
        public static ItemMeta deserialize(final Map<String, Object> map) throws Throwable {
            Validate.notNull(map, "Cannot deserialize null map");
            final String type = getString(map, "meta-type", false);
            final Constructor<? extends CraftMetaItem> constructor = SerializableMeta.constructorMap.get(type);
            if (constructor == null) {
                throw new IllegalArgumentException(type + " is not a valid " + "meta-type");
            }
            try {
                return (ItemMeta)constructor.newInstance(map);
            }
            catch (InstantiationException e) {
                throw new AssertionError((Object)e);
            }
            catch (IllegalAccessException e2) {
                throw new AssertionError((Object)e2);
            }
            catch (InvocationTargetException e3) {
                throw e3.getCause();
            }
        }
        
        public Map<String, Object> serialize() {
            throw new AssertionError();
        }
        
        static String getString(final Map<?, ?> map, final Object field, final boolean nullable) {
            return getObject(String.class, map, field, nullable);
        }
        
        static boolean getBoolean(final Map<?, ?> map, final Object field) {
            final Boolean value = getObject(Boolean.class, map, field, true);
            return value != null && value;
        }
        
        static <T> T getObject(final Class<T> clazz, final Map<?, ?> map, final Object field, final boolean nullable) {
            final Object object = map.get(field);
            if (clazz.isInstance(object)) {
                return clazz.cast(object);
            }
            if (object != null) {
                throw new IllegalArgumentException(field + "(" + object + ") is not a valid " + clazz);
            }
            if (!nullable) {
                throw new NoSuchElementException(map + " does not contain " + field);
            }
            return null;
        }
        
        static {
            classMap = ImmutableMap.builder().put(CraftMetaBook.class, "BOOK").put((Class<CraftMetaBook>)CraftMetaSkull.class, "SKULL").put((Class<CraftMetaBook>)CraftMetaLeatherArmor.class, "LEATHER_ARMOR").put((Class<CraftMetaBook>)CraftMetaMap.class, "MAP").put((Class<CraftMetaBook>)CraftMetaPotion.class, "POTION").put((Class<CraftMetaBook>)CraftMetaEnchantedBook.class, "ENCHANTED").put((Class<CraftMetaBook>)CraftMetaFirework.class, "FIREWORK").put((Class<CraftMetaBook>)CraftMetaCharge.class, "FIREWORK_EFFECT").put((Class<CraftMetaBook>)CraftMetaItem.class, "UNSPECIFIC").build();
            final ImmutableMap.Builder<String, Constructor<? extends CraftMetaItem>> classConstructorBuilder = ImmutableMap.builder();
            for (final Map.Entry<Class<? extends CraftMetaItem>, String> mapping : SerializableMeta.classMap.entrySet()) {
                try {
                    classConstructorBuilder.put(mapping.getValue(), mapping.getKey().getDeclaredConstructor(Map.class));
                }
                catch (NoSuchMethodException e) {
                    throw new AssertionError((Object)e);
                }
            }
            constructorMap = classConstructorBuilder.build();
        }
    }
}
