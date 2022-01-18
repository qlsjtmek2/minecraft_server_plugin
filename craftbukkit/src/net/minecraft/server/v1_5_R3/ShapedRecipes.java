// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.inventory.Recipe;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftShapedRecipe;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class ShapedRecipes implements IRecipe
{
    private int width;
    private int height;
    private ItemStack[] items;
    public ItemStack result;
    public final int a;
    private boolean f;
    
    public ShapedRecipes(final int i, final int j, final ItemStack[] aitemstack, final ItemStack itemstack) {
        this.f = false;
        this.a = itemstack.id;
        this.width = i;
        this.height = j;
        this.items = aitemstack;
        this.result = itemstack;
    }
    
    public ShapedRecipe toBukkitRecipe() {
        final CraftItemStack result = CraftItemStack.asCraftMirror(this.result);
        final CraftShapedRecipe recipe = new CraftShapedRecipe(result, this);
        Label_0334: {
            switch (this.height) {
                case 1: {
                    switch (this.width) {
                        case 1: {
                            recipe.shape("a");
                            break;
                        }
                        case 2: {
                            recipe.shape("ab");
                            break;
                        }
                        case 3: {
                            recipe.shape("abc");
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    switch (this.width) {
                        case 1: {
                            recipe.shape("a", "b");
                            break;
                        }
                        case 2: {
                            recipe.shape("ab", "cd");
                            break;
                        }
                        case 3: {
                            recipe.shape("abc", "def");
                            break;
                        }
                    }
                    break;
                }
                case 3: {
                    switch (this.width) {
                        case 1: {
                            recipe.shape("a", "b", "c");
                            break Label_0334;
                        }
                        case 2: {
                            recipe.shape("ab", "cd", "ef");
                            break Label_0334;
                        }
                        case 3: {
                            recipe.shape("abc", "def", "ghi");
                            break Label_0334;
                        }
                    }
                    break;
                }
            }
        }
        char c = 'a';
        for (final ItemStack stack : this.items) {
            if (stack != null) {
                recipe.setIngredient(c, Material.getMaterial(stack.id), stack.getData());
            }
            ++c;
        }
        return recipe;
    }
    
    public ItemStack b() {
        return this.result;
    }
    
    public boolean a(final InventoryCrafting inventorycrafting, final World world) {
        for (int i = 0; i <= 3 - this.width; ++i) {
            for (int j = 0; j <= 3 - this.height; ++j) {
                if (this.a(inventorycrafting, i, j, true)) {
                    return true;
                }
                if (this.a(inventorycrafting, i, j, false)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean a(final InventoryCrafting inventorycrafting, final int i, final int j, final boolean flag) {
        for (int k = 0; k < 3; ++k) {
            for (int l = 0; l < 3; ++l) {
                final int i2 = k - i;
                final int j2 = l - j;
                ItemStack itemstack = null;
                if (i2 >= 0 && j2 >= 0 && i2 < this.width && j2 < this.height) {
                    if (flag) {
                        itemstack = this.items[this.width - i2 - 1 + j2 * this.width];
                    }
                    else {
                        itemstack = this.items[i2 + j2 * this.width];
                    }
                }
                final ItemStack itemstack2 = inventorycrafting.b(k, l);
                if (itemstack2 != null || itemstack != null) {
                    if ((itemstack2 == null && itemstack != null) || (itemstack2 != null && itemstack == null)) {
                        return false;
                    }
                    if (itemstack.id != itemstack2.id) {
                        return false;
                    }
                    if (itemstack.getData() != 32767 && itemstack.getData() != itemstack2.getData()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public ItemStack a(final InventoryCrafting inventorycrafting) {
        final ItemStack itemstack = this.b().cloneItemStack();
        if (this.f) {
            for (int i = 0; i < inventorycrafting.getSize(); ++i) {
                final ItemStack itemstack2 = inventorycrafting.getItem(i);
                if (itemstack2 != null && itemstack2.hasTag()) {
                    itemstack.setTag((NBTTagCompound)itemstack2.tag.clone());
                }
            }
        }
        return itemstack;
    }
    
    public int a() {
        return this.width * this.height;
    }
    
    public ShapedRecipes c() {
        this.f = true;
        return this;
    }
    
    public List<ItemStack> getIngredients() {
        return Arrays.asList(this.items);
    }
}
