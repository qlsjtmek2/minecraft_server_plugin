// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class ItemEnchantedBook extends Item
{
    public ItemEnchantedBook(final int n) {
        super(n);
    }
    
    public boolean d_(final ItemStack itemStack) {
        return false;
    }
    
    public NBTTagList g(final ItemStack itemStack) {
        if (itemStack.tag == null || !itemStack.tag.hasKey("StoredEnchantments")) {
            return new NBTTagList();
        }
        return (NBTTagList)itemStack.tag.get("StoredEnchantments");
    }
    
    public void a(final ItemStack itemStack, final EnchantmentInstance enchantmentInstance) {
        final NBTTagList g = this.g(itemStack);
        boolean b = true;
        for (int i = 0; i < g.size(); ++i) {
            final NBTTagCompound nbtTagCompound = (NBTTagCompound)g.get(i);
            if (nbtTagCompound.getShort("id") == enchantmentInstance.enchantment.id) {
                if (nbtTagCompound.getShort("lvl") < enchantmentInstance.level) {
                    nbtTagCompound.setShort("lvl", (short)enchantmentInstance.level);
                }
                b = false;
                break;
            }
        }
        if (b) {
            final NBTTagCompound paramNBTBase = new NBTTagCompound();
            paramNBTBase.setShort("id", (short)enchantmentInstance.enchantment.id);
            paramNBTBase.setShort("lvl", (short)enchantmentInstance.level);
            g.add(paramNBTBase);
        }
        if (!itemStack.hasTag()) {
            itemStack.setTag(new NBTTagCompound());
        }
        itemStack.getTag().set("StoredEnchantments", g);
    }
    
    public ItemStack a(final EnchantmentInstance enchantmentInstance) {
        final ItemStack itemStack = new ItemStack(this);
        this.a(itemStack, enchantmentInstance);
        return itemStack;
    }
    
    public ItemStack a(final Random random) {
        final Enchantment enchantment = Enchantment.c[random.nextInt(Enchantment.c.length)];
        final ItemStack itemStack = new ItemStack(this.id, 1, 0);
        this.a(itemStack, new EnchantmentInstance(enchantment, MathHelper.nextInt(random, enchantment.getStartLevel(), enchantment.getMaxLevel())));
        return itemStack;
    }
    
    public StructurePieceTreasure b(final Random random) {
        return this.a(random, 1, 1, 1);
    }
    
    public StructurePieceTreasure a(final Random random, final int n, final int n2, final int n3) {
        final Enchantment enchantment = Enchantment.c[random.nextInt(Enchantment.c.length)];
        final ItemStack itemStack = new ItemStack(this.id, 1, 0);
        this.a(itemStack, new EnchantmentInstance(enchantment, MathHelper.nextInt(random, enchantment.getStartLevel(), enchantment.getMaxLevel())));
        return new StructurePieceTreasure(itemStack, n, n2, n3);
    }
}
