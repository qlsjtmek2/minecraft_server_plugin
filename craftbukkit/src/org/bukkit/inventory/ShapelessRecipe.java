// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory;

import java.util.Iterator;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import java.util.ArrayList;
import java.util.List;

public class ShapelessRecipe implements Recipe
{
    private ItemStack output;
    private List<ItemStack> ingredients;
    
    public ShapelessRecipe(final ItemStack result) {
        this.ingredients = new ArrayList<ItemStack>();
        this.output = new ItemStack(result);
    }
    
    public ShapelessRecipe addIngredient(final MaterialData ingredient) {
        return this.addIngredient(1, ingredient);
    }
    
    public ShapelessRecipe addIngredient(final Material ingredient) {
        return this.addIngredient(1, ingredient, 0);
    }
    
    public ShapelessRecipe addIngredient(final Material ingredient, final int rawdata) {
        return this.addIngredient(1, ingredient, rawdata);
    }
    
    public ShapelessRecipe addIngredient(final int count, final MaterialData ingredient) {
        return this.addIngredient(count, ingredient.getItemType(), ingredient.getData());
    }
    
    public ShapelessRecipe addIngredient(final int count, final Material ingredient) {
        return this.addIngredient(count, ingredient, 0);
    }
    
    public ShapelessRecipe addIngredient(int count, final Material ingredient, int rawdata) {
        Validate.isTrue(this.ingredients.size() + count <= 9, "Shapeless recipes cannot have more than 9 ingredients");
        if (rawdata == -1) {
            rawdata = 32767;
        }
        while (count-- > 0) {
            this.ingredients.add(new ItemStack(ingredient, 1, (short)rawdata));
        }
        return this;
    }
    
    public ShapelessRecipe removeIngredient(final Material ingredient) {
        return this.removeIngredient(ingredient, 0);
    }
    
    public ShapelessRecipe removeIngredient(final MaterialData ingredient) {
        return this.removeIngredient(ingredient.getItemType(), ingredient.getData());
    }
    
    public ShapelessRecipe removeIngredient(final int count, final Material ingredient) {
        return this.removeIngredient(count, ingredient, 0);
    }
    
    public ShapelessRecipe removeIngredient(final int count, final MaterialData ingredient) {
        return this.removeIngredient(count, ingredient.getItemType(), ingredient.getData());
    }
    
    public ShapelessRecipe removeIngredient(final Material ingredient, final int rawdata) {
        return this.removeIngredient(1, ingredient, rawdata);
    }
    
    public ShapelessRecipe removeIngredient(int count, final Material ingredient, final int rawdata) {
        for (Iterator<ItemStack> iterator = this.ingredients.iterator(); count > 0 && iterator.hasNext(); --count) {
            final ItemStack stack = iterator.next();
            if (stack.getType() == ingredient && stack.getDurability() == rawdata) {
                iterator.remove();
            }
        }
        return this;
    }
    
    public ItemStack getResult() {
        return this.output.clone();
    }
    
    public List<ItemStack> getIngredientList() {
        final ArrayList<ItemStack> result = new ArrayList<ItemStack>(this.ingredients.size());
        for (final ItemStack ingredient : this.ingredients) {
            result.add(ingredient.clone());
        }
        return result;
    }
}
