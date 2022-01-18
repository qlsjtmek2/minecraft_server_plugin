// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public enum EnchantmentSlotType
{
    ALL("all", 0), 
    ARMOR("armor", 1), 
    ARMOR_FEET("armor_feet", 2), 
    ARMOR_LEGS("armor_legs", 3), 
    ARMOR_TORSO("armor_torso", 4), 
    ARMOR_HEAD("armor_head", 5), 
    WEAPON("weapon", 6), 
    DIGGER("digger", 7), 
    BOW("bow", 8);
    
    private EnchantmentSlotType(final String s, final int n) {
    }
    
    public boolean canEnchant(final Item item) {
        if (this == EnchantmentSlotType.ALL) {
            return true;
        }
        if (item instanceof ItemArmor) {
            if (this == EnchantmentSlotType.ARMOR) {
                return true;
            }
            final ItemArmor itemArmor = (ItemArmor)item;
            if (itemArmor.b == 0) {
                return this == EnchantmentSlotType.ARMOR_HEAD;
            }
            if (itemArmor.b == 2) {
                return this == EnchantmentSlotType.ARMOR_LEGS;
            }
            if (itemArmor.b == 1) {
                return this == EnchantmentSlotType.ARMOR_TORSO;
            }
            return itemArmor.b == 3 && this == EnchantmentSlotType.ARMOR_FEET;
        }
        else {
            if (item instanceof ItemSword) {
                return this == EnchantmentSlotType.WEAPON;
            }
            if (item instanceof ItemTool) {
                return this == EnchantmentSlotType.DIGGER;
            }
            return item instanceof ItemBow && this == EnchantmentSlotType.BOW;
        }
    }
}
