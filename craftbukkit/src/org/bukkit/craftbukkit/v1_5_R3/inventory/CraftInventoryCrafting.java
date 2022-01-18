// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import net.minecraft.server.v1_5_R3.IRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.util.Java15Compat;
import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_5_R3.InventoryCrafting;
import net.minecraft.server.v1_5_R3.IInventory;
import org.bukkit.inventory.CraftingInventory;

public class CraftInventoryCrafting extends CraftInventory implements CraftingInventory
{
    private final IInventory resultInventory;
    
    public CraftInventoryCrafting(final InventoryCrafting inventory, final IInventory resultInventory) {
        super(inventory);
        this.resultInventory = resultInventory;
    }
    
    public IInventory getResultInventory() {
        return this.resultInventory;
    }
    
    public IInventory getMatrixInventory() {
        return this.inventory;
    }
    
    public int getSize() {
        return this.getResultInventory().getSize() + this.getMatrixInventory().getSize();
    }
    
    public void setContents(final ItemStack[] items) {
        final int resultLen = this.getResultInventory().getContents().length;
        final int len = this.getMatrixInventory().getContents().length + resultLen;
        if (len > items.length) {
            throw new IllegalArgumentException("Invalid inventory size; expected " + len + " or less");
        }
        this.setContents(items[0], Java15Compat.Arrays_copyOfRange(items, 1, items.length));
    }
    
    public ItemStack[] getContents() {
        final ItemStack[] items = new ItemStack[this.getSize()];
        net.minecraft.server.v1_5_R3.ItemStack[] mcResultItems;
        int i;
        for (mcResultItems = this.getResultInventory().getContents(), i = 0, i = 0; i < mcResultItems.length; ++i) {
            items[i] = CraftItemStack.asCraftMirror(mcResultItems[i]);
        }
        final net.minecraft.server.v1_5_R3.ItemStack[] mcItems = this.getMatrixInventory().getContents();
        for (int j = 0; j < mcItems.length; ++j) {
            items[i + j] = CraftItemStack.asCraftMirror(mcItems[j]);
        }
        return items;
    }
    
    public void setContents(final ItemStack result, final ItemStack[] contents) {
        this.setResult(result);
        this.setMatrix(contents);
    }
    
    public CraftItemStack getItem(final int index) {
        if (index < this.getResultInventory().getSize()) {
            final net.minecraft.server.v1_5_R3.ItemStack item = this.getResultInventory().getItem(index);
            return (item == null) ? null : CraftItemStack.asCraftMirror(item);
        }
        final net.minecraft.server.v1_5_R3.ItemStack item = this.getMatrixInventory().getItem(index - this.getResultInventory().getSize());
        return (item == null) ? null : CraftItemStack.asCraftMirror(item);
    }
    
    public void setItem(final int index, final ItemStack item) {
        if (index < this.getResultInventory().getSize()) {
            this.getResultInventory().setItem(index, (item == null) ? null : CraftItemStack.asNMSCopy(item));
        }
        else {
            this.getMatrixInventory().setItem(index - this.getResultInventory().getSize(), (item == null) ? null : CraftItemStack.asNMSCopy(item));
        }
    }
    
    public ItemStack[] getMatrix() {
        final ItemStack[] items = new ItemStack[this.getSize()];
        final net.minecraft.server.v1_5_R3.ItemStack[] matrix = this.getMatrixInventory().getContents();
        for (int i = 0; i < matrix.length; ++i) {
            items[i] = CraftItemStack.asCraftMirror(matrix[i]);
        }
        return items;
    }
    
    public ItemStack getResult() {
        final net.minecraft.server.v1_5_R3.ItemStack item = this.getResultInventory().getItem(0);
        if (item != null) {
            return CraftItemStack.asCraftMirror(item);
        }
        return null;
    }
    
    public void setMatrix(final ItemStack[] contents) {
        if (this.getMatrixInventory().getContents().length > contents.length) {
            throw new IllegalArgumentException("Invalid inventory size; expected " + this.getMatrixInventory().getContents().length + " or less");
        }
        final net.minecraft.server.v1_5_R3.ItemStack[] mcItems = this.getMatrixInventory().getContents();
        for (int i = 0; i < mcItems.length; ++i) {
            if (i < contents.length) {
                final ItemStack item = contents[i];
                if (item == null || item.getTypeId() <= 0) {
                    mcItems[i] = null;
                }
                else {
                    mcItems[i] = CraftItemStack.asNMSCopy(item);
                }
            }
            else {
                mcItems[i] = null;
            }
        }
    }
    
    public void setResult(final ItemStack item) {
        final net.minecraft.server.v1_5_R3.ItemStack[] contents = this.getResultInventory().getContents();
        if (item == null || item.getTypeId() <= 0) {
            contents[0] = null;
        }
        else {
            contents[0] = CraftItemStack.asNMSCopy(item);
        }
    }
    
    public Recipe getRecipe() {
        final IRecipe recipe = ((InventoryCrafting)this.getInventory()).currentRecipe;
        return (recipe == null) ? null : recipe.toBukkitRecipe();
    }
}
