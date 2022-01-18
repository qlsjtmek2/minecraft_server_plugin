// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import net.minecraft.server.v1_5_R3.RecipesFurnace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.FurnaceRecipe;

public class CraftFurnaceRecipe extends FurnaceRecipe implements CraftRecipe
{
    public CraftFurnaceRecipe(final ItemStack result, final ItemStack source) {
        super(result, source.getType(), source.getDurability());
    }
    
    public static CraftFurnaceRecipe fromBukkitRecipe(final FurnaceRecipe recipe) {
        if (recipe instanceof CraftFurnaceRecipe) {
            return (CraftFurnaceRecipe)recipe;
        }
        return new CraftFurnaceRecipe(recipe.getResult(), recipe.getInput());
    }
    
    public void addToCraftingManager() {
        final ItemStack result = this.getResult();
        final ItemStack input = this.getInput();
        RecipesFurnace.getInstance().registerRecipe(input.getTypeId(), CraftItemStack.asNMSCopy(result), 0.1f);
    }
}
