// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import java.util.ArrayList;
import org.bukkit.entity.HumanEntity;
import java.util.List;

public class TileEntityBrewingStand extends TileEntity implements IWorldInventory
{
    private static final int[] a;
    private static final int[] b;
    public ItemStack[] items;
    public int brewTime;
    private int e;
    private int f;
    private String g;
    private int lastTick;
    public List<HumanEntity> transaction;
    private int maxStack;
    
    public TileEntityBrewingStand() {
        this.items = new ItemStack[4];
        this.lastTick = MinecraftServer.currentTick;
        this.transaction = new ArrayList<HumanEntity>();
        this.maxStack = 64;
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
    
    public ItemStack[] getContents() {
        return this.items;
    }
    
    public void setMaxStackSize(final int size) {
        this.maxStack = size;
    }
    
    public String getName() {
        return this.c() ? this.g : "container.brewing";
    }
    
    public boolean c() {
        return this.g != null && this.g.length() > 0;
    }
    
    public void a(final String s) {
        this.g = s;
    }
    
    public int getSize() {
        return this.items.length;
    }
    
    public void h() {
        final int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
        this.lastTick = MinecraftServer.currentTick;
        if (this.brewTime > 0) {
            this.brewTime -= elapsedTicks;
            if (this.brewTime <= 0) {
                this.u();
                this.update();
            }
            else if (!this.l()) {
                this.brewTime = 0;
                this.update();
            }
            else if (this.f != this.items[3].id) {
                this.brewTime = 0;
                this.update();
            }
        }
        else if (this.l()) {
            this.brewTime = 400;
            this.f = this.items[3].id;
        }
        final int i = this.j();
        if (i != this.e) {
            this.e = i;
            this.world.setData(this.x, this.y, this.z, i, 2);
        }
        super.h();
    }
    
    public int x_() {
        return this.brewTime;
    }
    
    private boolean l() {
        if (this.items[3] == null || this.items[3].count <= 0) {
            return false;
        }
        final ItemStack itemstack = this.items[3];
        if (!Item.byId[itemstack.id].w()) {
            return false;
        }
        boolean flag = false;
        for (int i = 0; i < 3; ++i) {
            if (this.items[i] != null && this.items[i].id == Item.POTION.id) {
                final int j = this.items[i].getData();
                final int k = this.c(j, itemstack);
                if (!ItemPotion.f(j) && ItemPotion.f(k)) {
                    flag = true;
                    break;
                }
                final List list = Item.POTION.c(j);
                final List list2 = Item.POTION.c(k);
                if ((j <= 0 || list != list2) && (list == null || (!list.equals(list2) && list2 != null)) && j != k) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
    
    private void u() {
        if (this.l()) {
            final ItemStack itemstack = this.items[3];
            if (this.getOwner() != null) {
                final BrewEvent event = new BrewEvent(this.world.getWorld().getBlockAt(this.x, this.y, this.z), (BrewerInventory)this.getOwner().getInventory());
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    return;
                }
            }
            for (int i = 0; i < 3; ++i) {
                if (this.items[i] != null && this.items[i].id == Item.POTION.id) {
                    final int j = this.items[i].getData();
                    final int k = this.c(j, itemstack);
                    final List list = Item.POTION.c(j);
                    final List list2 = Item.POTION.c(k);
                    if ((j <= 0 || list != list2) && (list == null || (!list.equals(list2) && list2 != null))) {
                        if (j != k) {
                            this.items[i].setData(k);
                        }
                    }
                    else if (!ItemPotion.f(j) && ItemPotion.f(k)) {
                        this.items[i].setData(k);
                    }
                }
            }
            if (Item.byId[itemstack.id].t()) {
                this.items[3] = new ItemStack(Item.byId[itemstack.id].s());
            }
            else {
                final ItemStack itemStack = this.items[3];
                --itemStack.count;
                if (this.items[3].count <= 0) {
                    this.items[3] = null;
                }
            }
        }
    }
    
    private int c(final int i, final ItemStack itemstack) {
        return (itemstack == null) ? i : (Item.byId[itemstack.id].w() ? PotionBrewer.a(i, Item.byId[itemstack.id].v()) : i);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        final NBTTagList nbttaglist = nbttagcompound.getList("Items");
        this.items = new ItemStack[this.getSize()];
        for (int i = 0; i < nbttaglist.size(); ++i) {
            final NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbttaglist.get(i);
            final byte b0 = nbttagcompound2.getByte("Slot");
            if (b0 >= 0 && b0 < this.items.length) {
                this.items[b0] = ItemStack.createStack(nbttagcompound2);
            }
        }
        this.brewTime = nbttagcompound.getShort("BrewTime");
        if (nbttagcompound.hasKey("CustomName")) {
            this.g = nbttagcompound.getString("CustomName");
        }
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setShort("BrewTime", (short)this.brewTime);
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
            nbttagcompound.setString("CustomName", this.g);
        }
    }
    
    public ItemStack getItem(final int i) {
        return (i >= 0 && i < this.items.length) ? this.items[i] : null;
    }
    
    public ItemStack splitStack(final int i, final int j) {
        if (i >= 0 && i < this.items.length) {
            final ItemStack itemstack = this.items[i];
            this.items[i] = null;
            return itemstack;
        }
        return null;
    }
    
    public ItemStack splitWithoutUpdate(final int i) {
        if (i >= 0 && i < this.items.length) {
            final ItemStack itemstack = this.items[i];
            this.items[i] = null;
            return itemstack;
        }
        return null;
    }
    
    public void setItem(final int i, final ItemStack itemstack) {
        if (i >= 0 && i < this.items.length) {
            this.items[i] = itemstack;
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
        return (i == 3) ? Item.byId[itemstack.id].w() : (itemstack.id == Item.POTION.id || itemstack.id == Item.GLASS_BOTTLE.id);
    }
    
    public int j() {
        int i = 0;
        for (int j = 0; j < 3; ++j) {
            if (this.items[j] != null) {
                i |= 1 << j;
            }
        }
        return i;
    }
    
    public int[] getSlotsForFace(final int i) {
        return (i == 1) ? TileEntityBrewingStand.a : TileEntityBrewingStand.b;
    }
    
    public boolean canPlaceItemThroughFace(final int i, final ItemStack itemstack, final int j) {
        return this.b(i, itemstack);
    }
    
    public boolean canTakeItemThroughFace(final int i, final ItemStack itemstack, final int j) {
        return true;
    }
    
    static {
        a = new int[] { 3 };
        b = new int[] { 0, 1, 2 };
    }
}
