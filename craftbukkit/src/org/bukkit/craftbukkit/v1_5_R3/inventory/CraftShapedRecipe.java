// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import net.minecraft.server.v1_5_R3.CraftingManager;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_5_R3.ShapedRecipes;
import org.bukkit.inventory.ShapedRecipe;

public class CraftShapedRecipe extends ShapedRecipe implements CraftRecipe
{
    private ShapedRecipes recipe;
    
    public CraftShapedRecipe(final ItemStack result) {
        super(result);
    }
    
    public CraftShapedRecipe(final ItemStack result, final ShapedRecipes recipe) {
        this(result);
        this.recipe = recipe;
    }
    
    public static CraftShapedRecipe fromBukkitRecipe(final ShapedRecipe recipe) {
        if (recipe instanceof CraftShapedRecipe) {
            return (CraftShapedRecipe)recipe;
        }
        final CraftShapedRecipe ret = new CraftShapedRecipe(recipe.getResult());
        final String[] shape = recipe.getShape();
        ret.shape(shape);
        final Map<Character, ItemStack> ingredientMap = recipe.getIngredientMap();
        for (final char c : ingredientMap.keySet()) {
            final ItemStack stack = ingredientMap.get(c);
            if (stack != null) {
                ret.setIngredient(c, stack.getType(), stack.getDurability());
            }
        }
        return ret;
    }
    
    public void addToCraftingManager() {
        final String[] shape = this.getShape();
        final Map<Character, ItemStack> ingred = this.getIngredientMap();
        int datalen = shape.length;
        datalen += ingred.size() * 2;
        int i = 0;
        final Object[] data = new Object[datalen];
        while (i < shape.length) {
            data[i] = shape[i];
            ++i;
        }
        for (final char c : ingred.keySet()) {
            final ItemStack mdata = ingred.get(c);
            if (mdata == null) {
                continue;
            }
            data[i] = c;
            ++i;
            final int id = mdata.getTypeId();
            final short dmg = mdata.getDurability();
            data[i] = new net.minecraft.server.v1_5_R3.ItemStack(id, 1, dmg);
            ++i;
        }
        CraftingManager.getInstance().registerShapedRecipe(CraftItemStack.asNMSCopy(this.getResult()), data);
    }
}
