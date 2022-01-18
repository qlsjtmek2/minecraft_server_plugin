// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import java.util.List;

public class InventoryCrafting implements IInventory
{
    private ItemStack[] items;
    private int b;
    private Container c;
    public List<HumanEntity> transaction;
    public IRecipe currentRecipe;
    public IInventory resultInventory;
    private EntityHuman owner;
    private int maxStack;
    
    public ItemStack[] getContents() {
        return this.items;
    }
    
    public void onOpen(final CraftHumanEntity who) {
        this.transaction.add(who);
    }
    
    public InventoryType getInvType() {
        return (this.items.length == 4) ? InventoryType.CRAFTING : InventoryType.WORKBENCH;
    }
    
    public void onClose(final CraftHumanEntity who) {
        this.transaction.remove(who);
    }
    
    public List<HumanEntity> getViewers() {
        return this.transaction;
    }
    
    public InventoryHolder getOwner() {
        return this.owner.getBukkitEntity();
    }
    
    public void setMaxStackSize(final int size) {
        this.maxStack = size;
        this.resultInventory.setMaxStackSize(size);
    }
    
    public InventoryCrafting(final Container container, final int i, final int j, final EntityHuman player) {
        this(container, i, j);
        this.owner = player;
    }
    
    public InventoryCrafting(final Container container, final int i, final int j) {
        this.transaction = new ArrayList<HumanEntity>();
        this.maxStack = 64;
        final int k = i * j;
        this.items = new ItemStack[k];
        this.c = container;
        this.b = i;
    }
    
    public int getSize() {
        return this.items.length;
    }
    
    public ItemStack getItem(final int i) {
        return (i >= this.getSize()) ? null : this.items[i];
    }
    
    public ItemStack b(final int i, final int j) {
        if (i >= 0 && i < this.b) {
            final int k = i + j * this.b;
            return this.getItem(k);
        }
        return null;
    }
    
    public String getName() {
        return "container.crafting";
    }
    
    public boolean c() {
        return false;
    }
    
    public ItemStack splitWithoutUpdate(final int i) {
        if (this.items[i] != null) {
            final ItemStack itemstack = this.items[i];
            this.items[i] = null;
            return itemstack;
        }
        return null;
    }
    
    public ItemStack splitStack(final int i, final int j) {
        if (this.items[i] == null) {
            return null;
        }
        if (this.items[i].count <= j) {
            final ItemStack itemstack = this.items[i];
            this.items[i] = null;
            this.c.a(this);
            return itemstack;
        }
        final ItemStack itemstack = this.items[i].a(j);
        if (this.items[i].count == 0) {
            this.items[i] = null;
        }
        this.c.a(this);
        return itemstack;
    }
    
    public void setItem(final int i, final ItemStack itemstack) {
        this.items[i] = itemstack;
        this.c.a(this);
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
