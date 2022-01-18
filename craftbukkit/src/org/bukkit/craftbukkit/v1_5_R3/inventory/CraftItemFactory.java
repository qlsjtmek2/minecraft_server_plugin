// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Color;
import org.bukkit.inventory.ItemFactory;

public final class CraftItemFactory implements ItemFactory
{
    static final Color DEFAULT_LEATHER_COLOR;
    private static final CraftItemFactory instance;
    
    public boolean isApplicable(final ItemMeta meta, final ItemStack itemstack) {
        return itemstack != null && this.isApplicable(meta, itemstack.getType());
    }
    
    public boolean isApplicable(final ItemMeta meta, final Material type) {
        if (type == null || meta == null) {
            return false;
        }
        if (!(meta instanceof CraftMetaItem)) {
            throw new IllegalArgumentException("Meta of " + meta.getClass().toString() + " not created by " + CraftItemFactory.class.getName());
        }
        return ((CraftMetaItem)meta).applicableTo(type);
    }
    
    public ItemMeta getItemMeta(final Material material) {
        Validate.notNull(material, "Material cannot be null");
        return this.getItemMeta(material, null);
    }
    
    private ItemMeta getItemMeta(final Material material, final CraftMetaItem meta) {
        switch (material) {
            case AIR: {
                return null;
            }
            case WRITTEN_BOOK:
            case BOOK_AND_QUILL: {
                return (meta instanceof CraftMetaBook) ? meta : new CraftMetaBook(meta);
            }
            case SKULL_ITEM: {
                return (meta instanceof CraftMetaSkull) ? meta : new CraftMetaSkull(meta);
            }
            case LEATHER_HELMET:
            case LEATHER_CHESTPLATE:
            case LEATHER_LEGGINGS:
            case LEATHER_BOOTS: {
                return (meta instanceof CraftMetaLeatherArmor) ? meta : new CraftMetaLeatherArmor(meta);
            }
            case POTION: {
                return (meta instanceof CraftMetaPotion) ? meta : new CraftMetaPotion(meta);
            }
            case MAP: {
                return (meta instanceof CraftMetaMap) ? meta : new CraftMetaMap(meta);
            }
            case FIREWORK: {
                return (meta instanceof CraftMetaFirework) ? meta : new CraftMetaFirework(meta);
            }
            case FIREWORK_CHARGE: {
                return (meta instanceof CraftMetaCharge) ? meta : new CraftMetaCharge(meta);
            }
            case ENCHANTED_BOOK: {
                return (meta instanceof CraftMetaEnchantedBook) ? meta : new CraftMetaEnchantedBook(meta);
            }
            default: {
                return new CraftMetaItem(meta);
            }
        }
    }
    
    public boolean equals(final ItemMeta meta1, final ItemMeta meta2) {
        if (meta1 == meta2) {
            return true;
        }
        if (meta1 != null && !(meta1 instanceof CraftMetaItem)) {
            throw new IllegalArgumentException("First meta of " + meta1.getClass().getName() + " does not belong to " + CraftItemFactory.class.getName());
        }
        if (meta2 != null && !(meta2 instanceof CraftMetaItem)) {
            throw new IllegalArgumentException("Second meta " + meta2.getClass().getName() + " does not belong to " + CraftItemFactory.class.getName());
        }
        if (meta1 == null) {
            return ((CraftMetaItem)meta2).isEmpty();
        }
        if (meta2 == null) {
            return ((CraftMetaItem)meta1).isEmpty();
        }
        return this.equals((CraftMetaItem)meta1, (CraftMetaItem)meta2);
    }
    
    boolean equals(final CraftMetaItem meta1, final CraftMetaItem meta2) {
        return meta1.equalsCommon(meta2) && meta1.notUncommon(meta2) && meta2.notUncommon(meta1);
    }
    
    public static CraftItemFactory instance() {
        return CraftItemFactory.instance;
    }
    
    public ItemMeta asMetaFor(final ItemMeta meta, final ItemStack stack) {
        Validate.notNull(stack, "Stack cannot be null");
        return this.asMetaFor(meta, stack.getType());
    }
    
    public ItemMeta asMetaFor(final ItemMeta meta, final Material material) {
        Validate.notNull(material, "Material cannot be null");
        if (!(meta instanceof CraftMetaItem)) {
            throw new IllegalArgumentException("Meta of " + ((meta != null) ? meta.getClass().toString() : "null") + " not created by " + CraftItemFactory.class.getName());
        }
        return this.getItemMeta(material, (CraftMetaItem)meta);
    }
    
    public Color getDefaultLeatherColor() {
        return CraftItemFactory.DEFAULT_LEATHER_COLOR;
    }
    
    static {
        DEFAULT_LEATHER_COLOR = Color.fromRGB(10511680);
        instance = new CraftItemFactory();
        ConfigurationSerialization.registerClass(CraftMetaItem.SerializableMeta.class);
    }
}
