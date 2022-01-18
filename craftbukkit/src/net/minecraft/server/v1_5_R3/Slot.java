// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class Slot
{
    public final int index;
    public final IInventory inventory;
    public int g;
    public int h;
    public int i;
    
    public Slot(final IInventory iinventory, final int i, final int j, final int k) {
        this.inventory = iinventory;
        this.index = i;
        this.h = j;
        this.i = k;
    }
    
    public void a(final ItemStack itemstack, final ItemStack itemstack1) {
        if (itemstack != null && itemstack1 != null && itemstack.id == itemstack1.id) {
            final int i = itemstack1.count - itemstack.count;
            if (i > 0) {
                this.a(itemstack, i);
            }
        }
    }
    
    protected void a(final ItemStack itemstack, final int i) {
    }
    
    protected void b(final ItemStack itemstack) {
    }
    
    public void a(final EntityHuman entityhuman, final ItemStack itemstack) {
        this.e();
    }
    
    public boolean isAllowed(final ItemStack itemstack) {
        return true;
    }
    
    public ItemStack getItem() {
        return this.inventory.getItem(this.index);
    }
    
    public boolean d() {
        return this.getItem() != null;
    }
    
    public void set(final ItemStack itemstack) {
        this.inventory.setItem(this.index, itemstack);
        this.e();
    }
    
    public void e() {
        this.inventory.update();
    }
    
    public int a() {
        return this.inventory.getMaxStackSize();
    }
    
    public ItemStack a(final int i) {
        return this.inventory.splitStack(this.index, i);
    }
    
    public boolean a(final IInventory iinventory, final int i) {
        return iinventory == this.inventory && i == this.index;
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return true;
    }
}
