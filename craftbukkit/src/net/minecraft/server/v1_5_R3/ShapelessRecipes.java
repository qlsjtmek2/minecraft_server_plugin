// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.inventory.Recipe;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftShapelessRecipe;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import java.util.List;

public class ShapelessRecipes implements IRecipe
{
    public final ItemStack result;
    private final List ingredients;
    
    public ShapelessRecipes(final ItemStack itemstack, final List list) {
        this.result = itemstack;
        this.ingredients = list;
    }
    
    public ShapelessRecipe toBukkitRecipe() {
        final CraftItemStack result = CraftItemStack.asCraftMirror(this.result);
        final CraftShapelessRecipe recipe = new CraftShapelessRecipe(result, this);
        for (final ItemStack stack : this.ingredients) {
            if (stack != null) {
                recipe.addIngredient(Material.getMaterial(stack.id), stack.getData());
            }
        }
        return recipe;
    }
    
    public ItemStack b() {
        return this.result;
    }
    
    public boolean a(final InventoryCrafting inventorycrafting, final World world) {
        final ArrayList arraylist = new ArrayList(this.ingredients);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                final ItemStack itemstack = inventorycrafting.b(j, i);
                if (itemstack != null) {
                    boolean flag = false;
                    for (final ItemStack itemstack2 : arraylist) {
                        if (itemstack.id == itemstack2.id && (itemstack2.getData() == 32767 || itemstack.getData() == itemstack2.getData())) {
                            flag = true;
                            arraylist.remove(itemstack2);
                            break;
                        }
                    }
                    if (!flag) {
                        return false;
                    }
                }
            }
        }
        return arraylist.isEmpty();
    }
    
    public ItemStack a(final InventoryCrafting inventorycrafting) {
        return this.result.cloneItemStack();
    }
    
    public int a() {
        return this.ingredients.size();
    }
    
    public List<ItemStack> getIngredients() {
        return Collections.unmodifiableList((List<? extends ItemStack>)this.ingredients);
    }
}
