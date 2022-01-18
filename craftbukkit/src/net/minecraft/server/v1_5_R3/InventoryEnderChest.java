// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.HumanEntity;
import java.util.List;

public class InventoryEnderChest extends InventorySubcontainer
{
    private TileEntityEnderChest a;
    public List<HumanEntity> transaction;
    public Player player;
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
    
    public InventoryHolder getOwner() {
        return this.player;
    }
    
    public void setMaxStackSize(final int size) {
        this.maxStack = size;
    }
    
    public int getMaxStackSize() {
        return this.maxStack;
    }
    
    public InventoryEnderChest() {
        super("container.enderchest", false, 27);
        this.transaction = new ArrayList<HumanEntity>();
        this.maxStack = 64;
    }
    
    public void a(final TileEntityEnderChest tileentityenderchest) {
        this.a = tileentityenderchest;
    }
    
    public void a(final NBTTagList nbttaglist) {
        for (int i = 0; i < this.getSize(); ++i) {
            this.setItem(i, null);
        }
        for (int i = 0; i < nbttaglist.size(); ++i) {
            final NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.get(i);
            final int j = nbttagcompound.getByte("Slot") & 0xFF;
            if (j >= 0 && j < this.getSize()) {
                this.setItem(j, ItemStack.createStack(nbttagcompound));
            }
        }
    }
    
    public NBTTagList h() {
        final NBTTagList nbttaglist = new NBTTagList("EnderItems");
        for (int i = 0; i < this.getSize(); ++i) {
            final ItemStack itemstack = this.getItem(i);
            if (itemstack != null) {
                final NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                itemstack.save(nbttagcompound);
                nbttaglist.add(nbttagcompound);
            }
        }
        return nbttaglist;
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return (this.a == null || this.a.a(entityhuman)) && super.a(entityhuman);
    }
    
    public void startOpen() {
        if (this.a != null) {
            this.a.a();
        }
        super.startOpen();
    }
    
    public void g() {
        if (this.a != null) {
            this.a.b();
        }
        super.g();
        this.a = null;
    }
    
    public boolean b(final int i, final ItemStack itemstack) {
        return true;
    }
}
