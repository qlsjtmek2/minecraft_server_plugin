// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import java.util.List;

public class InventoryLargeChest implements IInventory
{
    private String a;
    public IInventory left;
    public IInventory right;
    public List<HumanEntity> transaction;
    
    public ItemStack[] getContents() {
        final ItemStack[] result = new ItemStack[this.getSize()];
        for (int i = 0; i < result.length; ++i) {
            result[i] = this.getItem(i);
        }
        return result;
    }
    
    public void onOpen(final CraftHumanEntity who) {
        this.left.onOpen(who);
        this.right.onOpen(who);
        this.transaction.add(who);
    }
    
    public void onClose(final CraftHumanEntity who) {
        this.left.onClose(who);
        this.right.onClose(who);
        this.transaction.remove(who);
    }
    
    public List<HumanEntity> getViewers() {
        return this.transaction;
    }
    
    public InventoryHolder getOwner() {
        return null;
    }
    
    public void setMaxStackSize(final int size) {
        this.left.setMaxStackSize(size);
        this.right.setMaxStackSize(size);
    }
    
    public InventoryLargeChest(final String s, IInventory iinventory, IInventory iinventory1) {
        this.transaction = new ArrayList<HumanEntity>();
        this.a = s;
        if (iinventory == null) {
            iinventory = iinventory1;
        }
        if (iinventory1 == null) {
            iinventory1 = iinventory;
        }
        this.left = iinventory;
        this.right = iinventory1;
    }
    
    public int getSize() {
        return this.left.getSize() + this.right.getSize();
    }
    
    public boolean a(final IInventory iinventory) {
        return this.left == iinventory || this.right == iinventory;
    }
    
    public String getName() {
        return this.left.c() ? this.left.getName() : (this.right.c() ? this.right.getName() : this.a);
    }
    
    public boolean c() {
        return this.left.c() || this.right.c();
    }
    
    public ItemStack getItem(final int i) {
        return (i >= this.left.getSize()) ? this.right.getItem(i - this.left.getSize()) : this.left.getItem(i);
    }
    
    public ItemStack splitStack(final int i, final int j) {
        return (i >= this.left.getSize()) ? this.right.splitStack(i - this.left.getSize(), j) : this.left.splitStack(i, j);
    }
    
    public ItemStack splitWithoutUpdate(final int i) {
        return (i >= this.left.getSize()) ? this.right.splitWithoutUpdate(i - this.left.getSize()) : this.left.splitWithoutUpdate(i);
    }
    
    public void setItem(final int i, final ItemStack itemstack) {
        if (i >= this.left.getSize()) {
            this.right.setItem(i - this.left.getSize(), itemstack);
        }
        else {
            this.left.setItem(i, itemstack);
        }
    }
    
    public int getMaxStackSize() {
        return Math.min(this.left.getMaxStackSize(), this.right.getMaxStackSize());
    }
    
    public void update() {
        this.left.update();
        this.right.update();
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return this.left.a(entityhuman) && this.right.a(entityhuman);
    }
    
    public void startOpen() {
        this.left.startOpen();
        this.right.startOpen();
    }
    
    public void g() {
        this.left.g();
        this.right.g();
    }
    
    public boolean b(final int i, final ItemStack itemstack) {
        return true;
    }
}
