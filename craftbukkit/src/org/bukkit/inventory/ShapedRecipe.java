// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory;

import java.util.Iterator;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.apache.commons.lang.Validate;
import java.util.HashMap;
import java.util.Map;

public class ShapedRecipe implements Recipe
{
    private ItemStack output;
    private String[] rows;
    private Map<Character, ItemStack> ingredients;
    
    public ShapedRecipe(final ItemStack result) {
        this.ingredients = new HashMap<Character, ItemStack>();
        this.output = new ItemStack(result);
    }
    
    public ShapedRecipe shape(final String... shape) {
        Validate.notNull(shape, "Must provide a shape");
        Validate.isTrue(shape.length > 0 && shape.length < 4, "Crafting recipes should be 1, 2, 3 rows, not ", shape.length);
        for (final String row : shape) {
            Validate.notNull(row, "Shape cannot have null rows");
            Validate.isTrue(row.length() > 0 && row.length() < 4, "Crafting rows should be 1, 2, or 3 characters, not ", row.length());
        }
        this.rows = new String[shape.length];
        for (int i = 0; i < shape.length; ++i) {
            this.rows[i] = shape[i];
        }
        final HashMap<Character, ItemStack> newIngredients = new HashMap<Character, ItemStack>();
        for (final String row2 : shape) {
            for (final Character c : row2.toCharArray()) {
                newIngredients.put(c, this.ingredients.get(c));
            }
        }
        this.ingredients = newIngredients;
        return this;
    }
    
    public ShapedRecipe setIngredient(final char key, final MaterialData ingredient) {
        return this.setIngredient(key, ingredient.getItemType(), ingredient.getData());
    }
    
    public ShapedRecipe setIngredient(final char key, final Material ingredient) {
        return this.setIngredient(key, ingredient, 0);
    }
    
    public ShapedRecipe setIngredient(final char key, final Material ingredient, int raw) {
        Validate.isTrue(this.ingredients.containsKey(key), "Symbol does not appear in the shape:", key);
        if (raw == -1) {
            raw = 32767;
        }
        this.ingredients.put(key, new ItemStack(ingredient, 1, (short)raw));
        return this;
    }
    
    public Map<Character, ItemStack> getIngredientMap() {
        final HashMap<Character, ItemStack> result = new HashMap<Character, ItemStack>();
        for (final Map.Entry<Character, ItemStack> ingredient : this.ingredients.entrySet()) {
            if (ingredient.getValue() == null) {
                result.put(ingredient.getKey(), null);
            }
            else {
                result.put(ingredient.getKey(), ingredient.getValue().clone());
            }
        }
        return result;
    }
    
    public String[] getShape() {
        return this.rows.clone();
    }
    
    public ItemStack getResult() {
        return this.output.clone();
    }
}
