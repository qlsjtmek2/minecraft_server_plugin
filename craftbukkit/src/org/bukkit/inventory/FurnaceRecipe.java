// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory;

import org.bukkit.material.MaterialData;
import org.bukkit.Material;

public class FurnaceRecipe implements Recipe
{
    private ItemStack output;
    private ItemStack ingredient;
    
    public FurnaceRecipe(final ItemStack result, final Material source) {
        this(result, source, 0);
    }
    
    public FurnaceRecipe(final ItemStack result, final MaterialData source) {
        this(result, source.getItemType(), source.getData());
    }
    
    public FurnaceRecipe(final ItemStack result, final Material source, final int data) {
        this.output = new ItemStack(result);
        this.ingredient = new ItemStack(source, 1, (short)data);
    }
    
    public FurnaceRecipe setInput(final MaterialData input) {
        return this.setInput(input.getItemType(), input.getData());
    }
    
    public FurnaceRecipe setInput(final Material input) {
        return this.setInput(input, 0);
    }
    
    public FurnaceRecipe setInput(final Material input, final int data) {
        this.ingredient = new ItemStack(input, 1, (short)data);
        return this;
    }
    
    public ItemStack getInput() {
        return this.ingredient.clone();
    }
    
    public ItemStack getResult() {
        return this.output.clone();
    }
}
