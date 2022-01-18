// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public abstract class InventorySubcontainer implements IInventory
{
    private String a;
    private int b;
    protected ItemStack[] items;
    private List d;
    private boolean e;
    
    public InventorySubcontainer(final String s, final boolean flag, final int i) {
        this.a = s;
        this.e = flag;
        this.b = i;
        this.items = new ItemStack[i];
    }
    
    public ItemStack getItem(final int i) {
        return this.items[i];
    }
    
    public ItemStack splitStack(final int i, final int j) {
        if (this.items[i] == null) {
            return null;
        }
        if (this.items[i].count <= j) {
            final ItemStack itemstack = this.items[i];
            this.items[i] = null;
            this.update();
            return itemstack;
        }
        final ItemStack itemstack = this.items[i].a(j);
        if (this.items[i].count == 0) {
            this.items[i] = null;
        }
        this.update();
        return itemstack;
    }
    
    public ItemStack splitWithoutUpdate(final int i) {
        if (this.items[i] != null) {
            final ItemStack itemstack = this.items[i];
            this.items[i] = null;
            return itemstack;
        }
        return null;
    }
    
    public void setItem(final int i, final ItemStack itemstack) {
        this.items[i] = itemstack;
        if (itemstack != null && itemstack.count > this.getMaxStackSize()) {
            itemstack.count = this.getMaxStackSize();
        }
        this.update();
    }
    
    public int getSize() {
        return this.b;
    }
    
    public String getName() {
        return this.a;
    }
    
    public boolean c() {
        return this.e;
    }
    
    public int getMaxStackSize() {
        return 64;
    }
    
    public void update() {
        if (this.d != null) {
            for (int i = 0; i < this.d.size(); ++i) {
                this.d.get(i).a(this);
            }
        }
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
