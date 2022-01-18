// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Arrays;

public class RecipeMapClone extends ShapelessRecipes implements IRecipe
{
    public RecipeMapClone() {
        super(new ItemStack(Item.MAP, 0, -1), Arrays.asList(new ItemStack(Item.MAP_EMPTY, 0, 0)));
    }
    
    public boolean a(final InventoryCrafting inventorycrafting, final World world) {
        int i = 0;
        ItemStack itemstack = null;
        for (int j = 0; j < inventorycrafting.getSize(); ++j) {
            final ItemStack itemstack2 = inventorycrafting.getItem(j);
            if (itemstack2 != null) {
                if (itemstack2.id == Item.MAP.id) {
                    if (itemstack != null) {
                        return false;
                    }
                    itemstack = itemstack2;
                }
                else {
                    if (itemstack2.id != Item.MAP_EMPTY.id) {
                        return false;
                    }
                    ++i;
                }
            }
        }
        return itemstack != null && i > 0;
    }
    
    public ItemStack a(final InventoryCrafting inventorycrafting) {
        int i = 0;
        ItemStack itemstack = null;
        for (int j = 0; j < inventorycrafting.getSize(); ++j) {
            final ItemStack itemstack2 = inventorycrafting.getItem(j);
            if (itemstack2 != null) {
                if (itemstack2.id == Item.MAP.id) {
                    if (itemstack != null) {
                        return null;
                    }
                    itemstack = itemstack2;
                }
                else {
                    if (itemstack2.id != Item.MAP_EMPTY.id) {
                        return null;
                    }
                    ++i;
                }
            }
        }
        if (itemstack != null && i >= 1) {
            final ItemStack itemstack3 = new ItemStack(Item.MAP, i + 1, itemstack.getData());
            if (itemstack.hasName()) {
                itemstack3.c(itemstack.getName());
            }
            return itemstack3;
        }
        return null;
    }
    
    public int a() {
        return 9;
    }
    
    public ItemStack b() {
        return null;
    }
}
