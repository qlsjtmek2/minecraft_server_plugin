// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class RecipeArmorDye extends ShapelessRecipes implements IRecipe
{
    public RecipeArmorDye() {
        super(new ItemStack(Item.LEATHER_HELMET, 0, 0), Arrays.asList(new ItemStack(Item.INK_SACK, 0, 5)));
    }
    
    public boolean a(final InventoryCrafting inventorycrafting, final World world) {
        ItemStack itemstack = null;
        final ArrayList arraylist = new ArrayList();
        for (int i = 0; i < inventorycrafting.getSize(); ++i) {
            final ItemStack itemstack2 = inventorycrafting.getItem(i);
            if (itemstack2 != null) {
                if (itemstack2.getItem() instanceof ItemArmor) {
                    final ItemArmor itemarmor = (ItemArmor)itemstack2.getItem();
                    if (itemarmor.d() != EnumArmorMaterial.CLOTH || itemstack != null) {
                        return false;
                    }
                    itemstack = itemstack2;
                }
                else {
                    if (itemstack2.id != Item.INK_SACK.id) {
                        return false;
                    }
                    arraylist.add(itemstack2);
                }
            }
        }
        return itemstack != null && !arraylist.isEmpty();
    }
    
    public ItemStack a(final InventoryCrafting inventorycrafting) {
        ItemStack itemstack = null;
        final int[] aint = new int[3];
        int i = 0;
        int j = 0;
        ItemArmor itemarmor = null;
        for (int k = 0; k < inventorycrafting.getSize(); ++k) {
            final ItemStack itemstack2 = inventorycrafting.getItem(k);
            if (itemstack2 != null) {
                if (itemstack2.getItem() instanceof ItemArmor) {
                    itemarmor = (ItemArmor)itemstack2.getItem();
                    if (itemarmor.d() != EnumArmorMaterial.CLOTH || itemstack != null) {
                        return null;
                    }
                    itemstack = itemstack2.cloneItemStack();
                    itemstack.count = 1;
                    if (itemarmor.a(itemstack2)) {
                        final int l = itemarmor.b(itemstack);
                        final float f = (l >> 16 & 0xFF) / 255.0f;
                        final float f2 = (l >> 8 & 0xFF) / 255.0f;
                        final float f3 = (l & 0xFF) / 255.0f;
                        i += (int)(Math.max(f, Math.max(f2, f3)) * 255.0f);
                        aint[0] += (int)(f * 255.0f);
                        aint[1] += (int)(f2 * 255.0f);
                        aint[2] += (int)(f3 * 255.0f);
                        ++j;
                    }
                }
                else {
                    if (itemstack2.id != Item.INK_SACK.id) {
                        return null;
                    }
                    final float[] afloat = EntitySheep.d[BlockCloth.g_(itemstack2.getData())];
                    final int j2 = (int)(afloat[0] * 255.0f);
                    final int k2 = (int)(afloat[1] * 255.0f);
                    final int i2 = (int)(afloat[2] * 255.0f);
                    i += Math.max(j2, Math.max(k2, i2));
                    final int[] array = aint;
                    final int n = 0;
                    array[n] += j2;
                    final int[] array2 = aint;
                    final int n2 = 1;
                    array2[n2] += k2;
                    final int[] array3 = aint;
                    final int n3 = 2;
                    array3[n3] += i2;
                    ++j;
                }
            }
        }
        if (itemarmor == null) {
            return null;
        }
        int k = aint[0] / j;
        int l2 = aint[1] / j;
        int l = aint[2] / j;
        final float f = i / j;
        final float f2 = Math.max(k, Math.max(l2, l));
        k = (int)(k * f / f2);
        l2 = (int)(l2 * f / f2);
        l = (int)(l * f / f2);
        int i2 = (k << 8) + l2;
        i2 = (i2 << 8) + l;
        itemarmor.b(itemstack, i2);
        return itemstack;
    }
    
    public int a() {
        return 10;
    }
    
    public ItemStack b() {
        return null;
    }
}
