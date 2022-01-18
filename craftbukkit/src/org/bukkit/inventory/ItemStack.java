// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory;

import java.util.LinkedHashMap;
import java.util.Iterator;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.Bukkit;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class ItemStack implements Cloneable, ConfigurationSerializable
{
    private int type;
    private int amount;
    private MaterialData data;
    private short durability;
    private ItemMeta meta;
    
    protected ItemStack() {
        this.type = 0;
        this.amount = 0;
        this.data = null;
        this.durability = 0;
    }
    
    public ItemStack(final int type) {
        this(type, 1);
    }
    
    public ItemStack(final Material type) {
        this(type, 1);
    }
    
    public ItemStack(final int type, final int amount) {
        this(type, amount, (short)0);
    }
    
    public ItemStack(final Material type, final int amount) {
        this(type.getId(), amount);
    }
    
    public ItemStack(final int type, final int amount, final short damage) {
        this.type = 0;
        this.amount = 0;
        this.data = null;
        this.durability = 0;
        this.type = type;
        this.amount = amount;
        this.durability = damage;
    }
    
    public ItemStack(final Material type, final int amount, final short damage) {
        this(type.getId(), amount, damage);
    }
    
    public ItemStack(final int type, final int amount, final short damage, final Byte data) {
        this.type = 0;
        this.amount = 0;
        this.data = null;
        this.durability = 0;
        this.type = type;
        this.amount = amount;
        this.durability = damage;
        if (data != null) {
            this.createData(data);
            this.durability = data;
        }
    }
    
    public ItemStack(final Material type, final int amount, final short damage, final Byte data) {
        this(type.getId(), amount, damage, data);
    }
    
    public ItemStack(final ItemStack stack) throws IllegalArgumentException {
        this.type = 0;
        this.amount = 0;
        this.data = null;
        this.durability = 0;
        Validate.notNull(stack, "Cannot copy null stack");
        this.type = stack.getTypeId();
        this.amount = stack.getAmount();
        this.durability = stack.getDurability();
        this.data = stack.getData();
        if (stack.hasItemMeta()) {
            this.setItemMeta0(stack.getItemMeta(), this.getType0());
        }
    }
    
    public Material getType() {
        return getType0(this.getTypeId());
    }
    
    private Material getType0() {
        return getType0(this.type);
    }
    
    private static Material getType0(final int id) {
        final Material material = Material.getMaterial(id);
        return (material == null) ? Material.AIR : material;
    }
    
    public void setType(final Material type) {
        Validate.notNull(type, "Material cannot be null");
        this.setTypeId(type.getId());
    }
    
    public int getTypeId() {
        return this.type;
    }
    
    public void setTypeId(final int type) {
        this.type = type;
        if (this.meta != null) {
            this.meta = Bukkit.getItemFactory().asMetaFor(this.meta, this.getType0());
        }
        this.createData((byte)0);
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    public void setAmount(final int amount) {
        this.amount = amount;
    }
    
    public MaterialData getData() {
        final Material mat = this.getType();
        if (this.data == null && mat != null && mat.getData() != null) {
            this.data = mat.getNewData((byte)this.getDurability());
        }
        return this.data;
    }
    
    public void setData(final MaterialData data) {
        final Material mat = this.getType();
        if (data == null || mat == null || mat.getData() == null) {
            this.data = data;
        }
        else {
            if (data.getClass() != mat.getData() && data.getClass() != MaterialData.class) {
                throw new IllegalArgumentException("Provided data is not of type " + mat.getData().getName() + ", found " + data.getClass().getName());
            }
            this.data = data;
        }
    }
    
    public void setDurability(final short durability) {
        this.durability = durability;
    }
    
    public short getDurability() {
        return this.durability;
    }
    
    public int getMaxStackSize() {
        final Material material = this.getType();
        if (material != null) {
            return material.getMaxStackSize();
        }
        return -1;
    }
    
    private void createData(final byte data) {
        final Material mat = Material.getMaterial(this.type);
        if (mat == null) {
            this.data = new MaterialData(this.type, data);
        }
        else {
            this.data = mat.getNewData(data);
        }
    }
    
    public String toString() {
        final StringBuilder toString = new StringBuilder("ItemStack{").append(this.getType().name()).append(" x ").append(this.getAmount());
        if (this.hasItemMeta()) {
            toString.append(", ").append(this.getItemMeta());
        }
        return toString.append('}').toString();
    }
    
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ItemStack)) {
            return false;
        }
        final ItemStack stack = (ItemStack)obj;
        return this.getAmount() == stack.getAmount() && this.isSimilar(stack);
    }
    
    public boolean isSimilar(final ItemStack stack) {
        return stack != null && (stack == this || (this.getTypeId() == stack.getTypeId() && this.getDurability() == stack.getDurability() && this.hasItemMeta() == stack.hasItemMeta() && (!this.hasItemMeta() || Bukkit.getItemFactory().equals(this.getItemMeta(), stack.getItemMeta()))));
    }
    
    public ItemStack clone() {
        try {
            final ItemStack itemStack = (ItemStack)super.clone();
            if (this.meta != null) {
                itemStack.meta = this.meta.clone();
            }
            if (this.data != null) {
                itemStack.data = this.data.clone();
            }
            return itemStack;
        }
        catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
    
    public final int hashCode() {
        int hash = 1;
        hash = hash * 31 + this.getTypeId();
        hash = hash * 31 + this.getAmount();
        hash = hash * 31 + (this.getDurability() & 0xFFFF);
        hash = hash * 31 + (this.hasItemMeta() ? ((this.meta == null) ? this.getItemMeta().hashCode() : this.meta.hashCode()) : 0);
        return hash;
    }
    
    public boolean containsEnchantment(final Enchantment ench) {
        return this.meta != null && this.meta.hasEnchant(ench);
    }
    
    public int getEnchantmentLevel(final Enchantment ench) {
        return (this.meta == null) ? 0 : this.meta.getEnchantLevel(ench);
    }
    
    public Map<Enchantment, Integer> getEnchantments() {
        return (Map<Enchantment, Integer>)((this.meta == null) ? ImmutableMap.of() : this.meta.getEnchants());
    }
    
    public void addEnchantments(final Map<Enchantment, Integer> enchantments) {
        Validate.notNull(enchantments, "Enchantments cannot be null");
        for (final Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            this.addEnchantment(entry.getKey(), entry.getValue());
        }
    }
    
    public void addEnchantment(final Enchantment ench, final int level) {
        Validate.notNull(ench, "Enchantment cannot be null");
        if (level < ench.getStartLevel() || level > ench.getMaxLevel()) {
            throw new IllegalArgumentException("Enchantment level is either too low or too high (given " + level + ", bounds are " + ench.getStartLevel() + " to " + ench.getMaxLevel());
        }
        if (!ench.canEnchantItem(this)) {
            throw new IllegalArgumentException("Specified enchantment cannot be applied to this itemstack");
        }
        this.addUnsafeEnchantment(ench, level);
    }
    
    public void addUnsafeEnchantments(final Map<Enchantment, Integer> enchantments) {
        for (final Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            this.addUnsafeEnchantment(entry.getKey(), entry.getValue());
        }
    }
    
    public void addUnsafeEnchantment(final Enchantment ench, final int level) {
        ((this.meta == null) ? (this.meta = Bukkit.getItemFactory().getItemMeta(this.getType0())) : this.meta).addEnchant(ench, level, true);
    }
    
    public int removeEnchantment(final Enchantment ench) {
        final int level = this.getEnchantmentLevel(ench);
        if (level == 0 || this.meta == null) {
            return level;
        }
        this.meta.removeEnchant(ench);
        return level;
    }
    
    public Map<String, Object> serialize() {
        final Map<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("type", this.getType().name());
        if (this.getDurability() != 0) {
            result.put("damage", this.getDurability());
        }
        if (this.getAmount() != 1) {
            result.put("amount", this.getAmount());
        }
        final ItemMeta meta = this.getItemMeta();
        if (!Bukkit.getItemFactory().equals(meta, null)) {
            result.put("meta", meta);
        }
        return result;
    }
    
    public static ItemStack deserialize(final Map<String, Object> args) {
        final Material type = Material.getMaterial(args.get("type"));
        short damage = 0;
        int amount = 1;
        if (args.containsKey("damage")) {
            damage = args.get("damage").shortValue();
        }
        if (args.containsKey("amount")) {
            amount = args.get("amount");
        }
        final ItemStack result = new ItemStack(type, amount, damage);
        if (args.containsKey("enchantments")) {
            final Object raw = args.get("enchantments");
            if (raw instanceof Map) {
                final Map<?, ?> map = (Map<?, ?>)raw;
                for (final Map.Entry<?, ?> entry : map.entrySet()) {
                    final Enchantment enchantment = Enchantment.getByName(entry.getKey().toString());
                    if (enchantment != null && entry.getValue() instanceof Integer) {
                        result.addUnsafeEnchantment(enchantment, (int)entry.getValue());
                    }
                }
            }
        }
        else if (args.containsKey("meta")) {
            final Object raw = args.get("meta");
            if (raw instanceof ItemMeta) {
                result.setItemMeta((ItemMeta)raw);
            }
        }
        return result;
    }
    
    public ItemMeta getItemMeta() {
        return (this.meta == null) ? Bukkit.getItemFactory().getItemMeta(this.getType0()) : this.meta.clone();
    }
    
    public boolean hasItemMeta() {
        return !Bukkit.getItemFactory().equals(this.meta, null);
    }
    
    public boolean setItemMeta(final ItemMeta itemMeta) {
        return this.setItemMeta0(itemMeta, this.getType0());
    }
    
    private boolean setItemMeta0(final ItemMeta itemMeta, final Material material) {
        if (itemMeta == null) {
            this.meta = null;
            return true;
        }
        if (!Bukkit.getItemFactory().isApplicable(itemMeta, material)) {
            return false;
        }
        this.meta = Bukkit.getItemFactory().asMetaFor(itemMeta, material);
        if (this.meta == itemMeta) {
            this.meta = itemMeta.clone();
        }
        return true;
    }
}
