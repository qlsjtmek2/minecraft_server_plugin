// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import org.bukkit.entity.HumanEntity;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import org.bukkit.inventory.InventoryHolder;

public class InventoryCraftResult implements IInventory
{
    private ItemStack[] items;
    private int maxStack;
    
    public ItemStack[] getContents() {
        return this.items;
    }
    
    public InventoryHolder getOwner() {
        return null;
    }
    
    public void onOpen(final CraftHumanEntity who) {
    }
    
    public void onClose(final CraftHumanEntity who) {
    }
    
    public List<HumanEntity> getViewers() {
        return new ArrayList<HumanEntity>();
    }
    
    public void setMaxStackSize(final int size) {
        this.maxStack = size;
    }
    
    public InventoryCraftResult() {
        this.items = new ItemStack[1];
        this.maxStack = 64;
    }
    
    public int getSize() {
        return 1;
    }
    
    public ItemStack getItem(final int i) {
        return this.items[0];
    }
    
    public String getName() {
        return "Result";
    }
    
    public boolean c() {
        return false;
    }
    
    public ItemStack splitStack(final int i, final int j) {
        if (this.items[0] != null) {
            final ItemStack itemstack = this.items[0];
            this.items[0] = null;
            return itemstack;
        }
        return null;
    }
    
    public ItemStack splitWithoutUpdate(final int i) {
        if (this.items[0] != null) {
            final ItemStack itemstack = this.items[0];
            this.items[0] = null;
            return itemstack;
        }
        return null;
    }
    
    public void setItem(final int i, final ItemStack itemstack) {
        this.items[0] = itemstack;
    }
    
    public int getMaxStackSize() {
        return this.maxStack;
    }
    
    public void update() {
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return true;
    }
    
    public void startOpen() {
    }
    
    public void g() {
    }
    
    public boolean b(final int i, final ItemStack itemstack) {
        return true;
    }
}
