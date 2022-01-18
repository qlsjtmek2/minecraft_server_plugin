// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import java.util.List;
import java.util.Random;

public class TileEntityDispenser extends TileEntity implements IInventory
{
    private ItemStack[] items;
    private Random c;
    protected String a;
    public List<HumanEntity> transaction;
    private int maxStack;
    
    public ItemStack[] getContents() {
        return this.items;
    }
    
    public void onOpen(final CraftHumanEntity who) {
        this.transaction.add(who);
    }
    
    public void onClose(final CraftHumanEntity who) {
        this.transaction.remove(who);
    }
    
    public List<HumanEntity> getViewers() {
        return this.transaction;
    }
    
    public void setMaxStackSize(final int size) {
        this.maxStack = size;
    }
    
    public TileEntityDispenser() {
        this.items = new ItemStack[9];
        this.c = new Random();
        this.transaction = new ArrayList<HumanEntity>();
        this.maxStack = 64;
    }
    
    public int getSize() {
        return 9;
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
    
    public int j() {
        int i = -1;
        int j = 1;
        for (int k = 0; k < this.items.length; ++k) {
            if (this.items[k] != null && this.c.nextInt(j++) == 0) {
                if (this.items[k].count != 0) {
                    i = k;
                }
            }
        }
        return i;
    }
    
    public void setItem(final int i, final ItemStack itemstack) {
        this.items[i] = itemstack;
        if (itemstack != null && itemstack.count > this.getMaxStackSize()) {
            itemstack.count = this.getMaxStackSize();
        }
        this.update();
    }
    
    public int addItem(final ItemStack itemstack) {
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] == null || this.items[i].id == 0) {
                this.setItem(i, itemstack);
                return i;
            }
        }
        return -1;
    }
    
    public String getName() {
        return this.c() ? this.a : "container.dispenser";
    }
    
    public void a(final String s) {
        this.a = s;
    }
    
    public boolean c() {
        return this.a != null;
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        final NBTTagList nbttaglist = nbttagcompound.getList("Items");
        this.items = new ItemStack[this.getSize()];
        for (int i = 0; i < nbttaglist.size(); ++i) {
            final NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbttaglist.get(i);
            final int j = nbttagcompound2.getByte("Slot") & 0xFF;
            if (j >= 0 && j < this.items.length) {
                this.items[j] = ItemStack.createStack(nbttagcompound2);
            }
        }
        if (nbttagcompound.hasKey("CustomName")) {
            this.a = nbttagcompound.getString("CustomName");
        }
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        final NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] != null) {
                final NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                nbttagcompound2.setByte("Slot", (byte)i);
                this.items[i].save(nbttagcompound2);
                nbttaglist.add(nbttagcompound2);
            }
        }
        nbttagcompound.set("Items", nbttaglist);
        if (this.c()) {
            nbttagcompound.setString("CustomName", this.a);
        }
    }
    
    public int getMaxStackSize() {
        return this.maxStack;
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return this.world.getTileEntity(this.x, this.y, this.z) == this && entityhuman.e(this.x + 0.5, this.y + 0.5, this.z + 0.5) <= 64.0;
    }
    
    public void startOpen() {
    }
    
    public void g() {
    }
    
    public boolean b(final int i, final ItemStack itemstack) {
        return true;
    }
}
