// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_5_R3.RecipesFurnace;
import net.minecraft.server.v1_5_R3.CraftingManager;
import net.minecraft.server.v1_5_R3.IRecipe;
import org.bukkit.inventory.Recipe;
import java.util.Iterator;

public class RecipeIterator implements Iterator<Recipe>
{
    private final Iterator<IRecipe> recipes;
    private final Iterator<Integer> smelting;
    private Iterator<?> removeFrom;
    
    public RecipeIterator() {
        this.removeFrom = null;
        this.recipes = CraftingManager.getInstance().getRecipes().iterator();
        this.smelting = RecipesFurnace.getInstance().getRecipes().keySet().iterator();
    }
    
    public boolean hasNext() {
        return this.recipes.hasNext() || this.smelting.hasNext();
    }
    
    public Recipe next() {
        if (this.recipes.hasNext()) {
            this.removeFrom = this.recipes;
            return this.recipes.next().toBukkitRecipe();
        }
        this.removeFrom = this.smelting;
        final int id = this.smelting.next();
        final CraftItemStack stack = CraftItemStack.asCraftMirror(RecipesFurnace.getInstance().getResult(id));
        return new CraftFurnaceRecipe(stack, new ItemStack(id, 1, (short)(-1)));
    }
    
    public void remove() {
        if (this.removeFrom == null) {
            throw new IllegalStateException();
        }
        this.removeFrom.remove();
    }
}
