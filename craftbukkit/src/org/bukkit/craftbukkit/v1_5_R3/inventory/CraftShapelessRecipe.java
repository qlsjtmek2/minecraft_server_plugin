// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import java.util.List;
import net.minecraft.server.v1_5_R3.CraftingManager;
import java.util.Iterator;
import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_5_R3.ShapelessRecipes;
import org.bukkit.inventory.ShapelessRecipe;

public class CraftShapelessRecipe extends ShapelessRecipe implements CraftRecipe
{
    private ShapelessRecipes recipe;
    
    public CraftShapelessRecipe(final ItemStack result) {
        super(result);
    }
    
    public CraftShapelessRecipe(final ItemStack result, final ShapelessRecipes recipe) {
        this(result);
        this.recipe = recipe;
    }
    
    public static CraftShapelessRecipe fromBukkitRecipe(final ShapelessRecipe recipe) {
        if (recipe instanceof CraftShapelessRecipe) {
            return (CraftShapelessRecipe)recipe;
        }
        final CraftShapelessRecipe ret = new CraftShapelessRecipe(recipe.getResult());
        for (final ItemStack ingred : recipe.getIngredientList()) {
            ret.addIngredient(ingred.getType(), ingred.getDurability());
        }
        return ret;
    }
    
    public void addToCraftingManager() {
        final List<ItemStack> ingred = this.getIngredientList();
        final Object[] data = new Object[ingred.size()];
        int i = 0;
        for (final ItemStack mdata : ingred) {
            final int id = mdata.getTypeId();
            final short dmg = mdata.getDurability();
            data[i] = new net.minecraft.server.v1_5_R3.ItemStack(id, 1, dmg);
            ++i;
        }
        CraftingManager.getInstance().registerShapelessRecipe(CraftItemStack.asNMSCopy(this.getResult()), data);
    }
}
