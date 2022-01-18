// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.server.v1_5_R3.EnchantmentManager;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import net.minecraft.server.v1_5_R3.NBTBase;
import net.minecraft.server.v1_5_R3.NBTTagList;
import org.apache.commons.lang.Validate;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.material.MaterialData;
import org.bukkit.inventory.meta.ItemMeta;
import net.minecraft.server.v1_5_R3.Item;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.ItemStack;

@DelegateDeserialization(ItemStack.class)
public final class CraftItemStack extends ItemStack
{
    net.minecraft.server.v1_5_R3.ItemStack handle;
    
    public static net.minecraft.server.v1_5_R3.ItemStack asNMSCopy(final ItemStack original) {
        if (original instanceof CraftItemStack) {
            final CraftItemStack stack = (CraftItemStack)original;
            return (stack.handle == null) ? null : stack.handle.cloneItemStack();
        }
        if (original == null || original.getTypeId() <= 0) {
            return null;
        }
        final net.minecraft.server.v1_5_R3.ItemStack stack2 = new net.minecraft.server.v1_5_R3.ItemStack(original.getTypeId(), original.getAmount(), original.getDurability());
        if (original.hasItemMeta()) {
            setItemMeta(stack2, original.getItemMeta());
        }
        return stack2;
    }
    
    public static net.minecraft.server.v1_5_R3.ItemStack copyNMSStack(final net.minecraft.server.v1_5_R3.ItemStack original, final int amount) {
        final net.minecraft.server.v1_5_R3.ItemStack stack = original.cloneItemStack();
        stack.count = amount;
        return stack;
    }
    
    public static ItemStack asBukkitCopy(final net.minecraft.server.v1_5_R3.ItemStack original) {
        if (original == null) {
            return new ItemStack(Material.AIR);
        }
        final ItemStack stack = new ItemStack(original.id, original.count, (short)original.getData());
        if (hasItemMeta(original)) {
            stack.setItemMeta(getItemMeta(original));
        }
        return stack;
    }
    
    public static CraftItemStack asCraftMirror(final net.minecraft.server.v1_5_R3.ItemStack original) {
        return new CraftItemStack(original);
    }
    
    public static CraftItemStack asCraftCopy(final ItemStack original) {
        if (original instanceof CraftItemStack) {
            final CraftItemStack stack = (CraftItemStack)original;
            return new CraftItemStack((stack.handle == null) ? null : stack.handle.cloneItemStack());
        }
        return new CraftItemStack(original);
    }
    
    public static CraftItemStack asNewCraftStack(final Item item) {
        return asNewCraftStack(item, 1);
    }
    
    public static CraftItemStack asNewCraftStack(final Item item, final int amount) {
        return new CraftItemStack(item.id, amount, (short)0, (ItemMeta)null);
    }
    
    private CraftItemStack(final net.minecraft.server.v1_5_R3.ItemStack item) {
        this.handle = item;
    }
    
    private CraftItemStack(final ItemStack item) {
        this(item.getTypeId(), item.getAmount(), item.getDurability(), item.hasItemMeta() ? item.getItemMeta() : null);
    }
    
    private CraftItemStack(final int typeId, final int amount, final short durability, final ItemMeta itemMeta) {
        this.setTypeId(typeId);
        this.setAmount(amount);
        this.setDurability(durability);
        this.setItemMeta(itemMeta);
    }
    
    public int getTypeId() {
        return (this.handle != null) ? this.handle.id : 0;
    }
    
    public void setTypeId(final int type) {
        if (this.getTypeId() == type) {
            return;
        }
        if (type == 0) {
            this.handle = null;
        }
        else if (this.handle == null) {
            this.handle = new net.minecraft.server.v1_5_R3.ItemStack(type, 1, 0);
        }
        else {
            this.handle.id = type;
            if (this.hasItemMeta()) {
                setItemMeta(this.handle, getItemMeta(this.handle));
            }
        }
        this.setData(null);
    }
    
    public int getAmount() {
        return (this.handle != null) ? this.handle.count : 0;
    }
    
    public void setAmount(final int amount) {
        if (this.handle == null) {
            return;
        }
        if (amount == 0) {
            this.handle = null;
        }
        else {
            this.handle.count = amount;
        }
    }
    
    public void setDurability(final short durability) {
        if (this.handle != null) {
            this.handle.setData(durability);
        }
    }
    
    public short getDurability() {
        if (this.handle != null) {
            return (short)this.handle.getData();
        }
        return -1;
    }
    
    public int getMaxStackSize() {
        return (this.handle == null) ? Material.AIR.getMaxStackSize() : this.handle.getItem().getMaxStackSize();
    }
    
    public void addUnsafeEnchantment(final Enchantment ench, final int level) {
        Validate.notNull(ench, "Cannot add null enchantment");
        if (!makeTag(this.handle)) {
            return;
        }
        NBTTagList list = getEnchantmentList(this.handle);
        if (list == null) {
            list = new NBTTagList(CraftMetaItem.ENCHANTMENTS.NBT);
            this.handle.tag.set(CraftMetaItem.ENCHANTMENTS.NBT, list);
        }
        for (int size = list.size(), i = 0; i < size; ++i) {
            final NBTTagCompound tag = (NBTTagCompound)list.get(i);
            final short id = tag.getShort(CraftMetaItem.ENCHANTMENTS_ID.NBT);
            if (id == ench.getId()) {
                tag.setShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT, (short)level);
                return;
            }
        }
        final NBTTagCompound tag2 = new NBTTagCompound();
        tag2.setShort(CraftMetaItem.ENCHANTMENTS_ID.NBT, (short)ench.getId());
        tag2.setShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT, (short)level);
        list.add(tag2);
    }
    
    static boolean makeTag(final net.minecraft.server.v1_5_R3.ItemStack item) {
        if (item == null) {
            return false;
        }
        if (item.tag != null) {
            return true;
        }
        item.tag = new NBTTagCompound();
        return true;
    }
    
    public boolean containsEnchantment(final Enchantment ench) {
        return this.getEnchantmentLevel(ench) > 0;
    }
    
    public int getEnchantmentLevel(final Enchantment ench) {
        Validate.notNull(ench, "Cannot find null enchantment");
        if (this.handle == null) {
            return 0;
        }
        return EnchantmentManager.getEnchantmentLevel(ench.getId(), this.handle);
    }
    
    public int removeEnchantment(final Enchantment ench) {
        Validate.notNull(ench, "Cannot remove null enchantment");
        final NBTTagList list = getEnchantmentList(this.handle);
        if (list == null) {
            return 0;
        }
        int index = Integer.MIN_VALUE;
        int level = Integer.MIN_VALUE;
        final int size = list.size();
        for (int i = 0; i < size; ++i) {
            final NBTTagCompound enchantment = (NBTTagCompound)list.get(i);
            final int id = 0xFFFF & enchantment.getShort(CraftMetaItem.ENCHANTMENTS_ID.NBT);
            if (id == ench.getId()) {
                index = i;
                level = (0xFFFF & enchantment.getShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT));
                break;
            }
        }
        if (index == Integer.MIN_VALUE) {
            return 0;
        }
        if (size == 1) {
            this.handle.tag.remove(CraftMetaItem.ENCHANTMENTS.NBT);
            if (this.handle.tag.isEmpty()) {
                this.handle.tag = null;
            }
            return level;
        }
        final NBTTagList listCopy = new NBTTagList(CraftMetaItem.ENCHANTMENTS.NBT);
        for (int i = 0; i < size; ++i) {
            if (i != index) {
                listCopy.add(list.get(i));
            }
        }
        this.handle.tag.set(CraftMetaItem.ENCHANTMENTS.NBT, listCopy);
        return level;
    }
    
    public Map<Enchantment, Integer> getEnchantments() {
        return getEnchantments(this.handle);
    }
    
    static Map<Enchantment, Integer> getEnchantments(final net.minecraft.server.v1_5_R3.ItemStack item) {
        final ImmutableMap.Builder<Enchantment, Integer> result = ImmutableMap.builder();
        final NBTTagList list = (item == null) ? null : item.getEnchantments();
        if (list == null) {
            return result.build();
        }
        for (int i = 0; i < list.size(); ++i) {
            final int id = 0xFFFF & ((NBTTagCompound)list.get(i)).getShort(CraftMetaItem.ENCHANTMENTS_ID.NBT);
            final int level = 0xFFFF & ((NBTTagCompound)list.get(i)).getShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT);
            result.put(Enchantment.getById(id), level);
        }
        return result.build();
    }
    
    static NBTTagList getEnchantmentList(final net.minecraft.server.v1_5_R3.ItemStack item) {
        return (item == null) ? null : item.getEnchantments();
    }
    
    public CraftItemStack clone() {
        final CraftItemStack itemStack = (CraftItemStack)super.clone();
        if (this.handle != null) {
            itemStack.handle = this.handle.cloneItemStack();
        }
        return itemStack;
    }
    
    public ItemMeta getItemMeta() {
        return getItemMeta(this.handle);
    }
    
    public static ItemMeta getItemMeta(final net.minecraft.server.v1_5_R3.ItemStack item) {
        if (!hasItemMeta(item)) {
            return CraftItemFactory.instance().getItemMeta(getType(item));
        }
        switch (getType(item)) {
            case WRITTEN_BOOK:
            case BOOK_AND_QUILL: {
                return new CraftMetaBook(item.tag);
            }
            case SKULL_ITEM: {
                return new CraftMetaSkull(item.tag);
            }
            case LEATHER_HELMET:
            case LEATHER_CHESTPLATE:
            case LEATHER_LEGGINGS:
            case LEATHER_BOOTS: {
                return new CraftMetaLeatherArmor(item.tag);
            }
            case POTION: {
                return new CraftMetaPotion(item.tag);
            }
            case MAP: {
                return new CraftMetaMap(item.tag);
            }
            case FIREWORK: {
                return new CraftMetaFirework(item.tag);
            }
            case FIREWORK_CHARGE: {
                return new CraftMetaCharge(item.tag);
            }
            case ENCHANTED_BOOK: {
                return new CraftMetaEnchantedBook(item.tag);
            }
            default: {
                return new CraftMetaItem(item.tag);
            }
        }
    }
    
    static Material getType(final net.minecraft.server.v1_5_R3.ItemStack item) {
        final Material material = Material.getMaterial((item == null) ? 0 : item.id);
        return (material == null) ? Material.AIR : material;
    }
    
    public boolean setItemMeta(final ItemMeta itemMeta) {
        return setItemMeta(this.handle, itemMeta);
    }
    
    public static boolean setItemMeta(final net.minecraft.server.v1_5_R3.ItemStack item, final ItemMeta itemMeta) {
        if (item == null) {
            return false;
        }
        if (itemMeta == null) {
            item.tag = null;
            return true;
        }
        if (!CraftItemFactory.instance().isApplicable(itemMeta, getType(item))) {
            return false;
        }
        final NBTTagCompound tag = new NBTTagCompound();
        item.setTag(tag);
        ((CraftMetaItem)itemMeta).applyToItem(tag);
        return true;
    }
    
    public boolean isSimilar(final ItemStack stack) {
        if (stack == null) {
            return false;
        }
        if (stack == this) {
            return true;
        }
        if (!(stack instanceof CraftItemStack)) {
            return stack.getClass() == ItemStack.class && stack.isSimilar(this);
        }
        final CraftItemStack that = (CraftItemStack)stack;
        return this.handle == that.handle || (this.handle != null && that.handle != null && that.getTypeId() == this.getTypeId() && this.getDurability() == that.getDurability() && (this.hasItemMeta() ? (that.hasItemMeta() && this.handle.tag.equals(that.handle.tag)) : (!that.hasItemMeta())));
    }
    
    public boolean hasItemMeta() {
        return hasItemMeta(this.handle);
    }
    
    static boolean hasItemMeta(final net.minecraft.server.v1_5_R3.ItemStack item) {
        return item != null && item.tag != null && !item.tag.isEmpty();
    }
}
