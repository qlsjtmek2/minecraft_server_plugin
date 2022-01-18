// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.Iterator;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import java.util.ArrayList;
import org.bukkit.entity.HumanEntity;
import java.util.List;

public class TileEntityChest extends TileEntity implements IInventory
{
    private ItemStack[] items;
    public boolean a;
    public TileEntityChest b;
    public TileEntityChest c;
    public TileEntityChest d;
    public TileEntityChest e;
    public float f;
    public float g;
    public int h;
    private int ticks;
    private int r;
    private String s;
    public List<HumanEntity> transaction;
    private int maxStack;
    
    public TileEntityChest() {
        this.items = new ItemStack[27];
        this.a = false;
        this.r = -1;
        this.transaction = new ArrayList<HumanEntity>();
        this.maxStack = 64;
    }
    
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
    
    public int getSize() {
        return 27;
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
    
    public String getName() {
        return this.c() ? this.s : "container.chest";
    }
    
    public boolean c() {
        return this.s != null && this.s.length() > 0;
    }
    
    public void a(final String s) {
        this.s = s;
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        final NBTTagList nbttaglist = nbttagcompound.getList("Items");
        this.items = new ItemStack[this.getSize()];
        if (nbttagcompound.hasKey("CustomName")) {
            this.s = nbttagcompound.getString("CustomName");
        }
        for (int i = 0; i < nbttaglist.size(); ++i) {
            final NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbttaglist.get(i);
            final int j = nbttagcompound2.getByte("Slot") & 0xFF;
            if (j >= 0 && j < this.items.length) {
                this.items[j] = ItemStack.createStack(nbttagcompound2);
            }
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
            nbttagcompound.setString("CustomName", this.s);
        }
    }
    
    public int getMaxStackSize() {
        return this.maxStack;
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return this.world == null || (this.world.getTileEntity(this.x, this.y, this.z) == this && entityhuman.e(this.x + 0.5, this.y + 0.5, this.z + 0.5) <= 64.0);
    }
    
    public void i() {
        super.i();
        this.a = false;
    }
    
    private void a(final TileEntityChest tileentitychest, final int i) {
        if (tileentitychest.r()) {
            this.a = false;
        }
        else if (this.a) {
            switch (i) {
                case 0: {
                    if (this.e != tileentitychest) {
                        this.a = false;
                        break;
                    }
                    break;
                }
                case 1: {
                    if (this.d != tileentitychest) {
                        this.a = false;
                        break;
                    }
                    break;
                }
                case 2: {
                    if (this.b != tileentitychest) {
                        this.a = false;
                        break;
                    }
                    break;
                }
                case 3: {
                    if (this.c != tileentitychest) {
                        this.a = false;
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public void j() {
        if (!this.a) {
            this.a = true;
            this.b = null;
            this.c = null;
            this.d = null;
            this.e = null;
            if (this.a(this.x - 1, this.y, this.z)) {
                this.d = (TileEntityChest)this.world.getTileEntity(this.x - 1, this.y, this.z);
            }
            if (this.a(this.x + 1, this.y, this.z)) {
                this.c = (TileEntityChest)this.world.getTileEntity(this.x + 1, this.y, this.z);
            }
            if (this.a(this.x, this.y, this.z - 1)) {
                this.b = (TileEntityChest)this.world.getTileEntity(this.x, this.y, this.z - 1);
            }
            if (this.a(this.x, this.y, this.z + 1)) {
                this.e = (TileEntityChest)this.world.getTileEntity(this.x, this.y, this.z + 1);
            }
            if (this.b != null) {
                this.b.a(this, 0);
            }
            if (this.e != null) {
                this.e.a(this, 2);
            }
            if (this.c != null) {
                this.c.a(this, 1);
            }
            if (this.d != null) {
                this.d.a(this, 3);
            }
        }
    }
    
    private boolean a(final int i, final int j, final int k) {
        final Block block = Block.byId[this.world.getTypeId(i, j, k)];
        return block != null && block instanceof BlockChest && ((BlockChest)block).a == this.l();
    }
    
    public void h() {
        super.h();
        if (this.world == null) {
            return;
        }
        this.j();
        ++this.ticks;
        if (!this.world.isStatic && this.h != 0 && (this.ticks + this.x + this.y + this.z) % 200 == 0) {
            this.h = 0;
            final float f = 5.0f;
            final List list = this.world.a(EntityHuman.class, AxisAlignedBB.a().a(this.x - f, this.y - f, this.z - f, this.x + 1 + f, this.y + 1 + f, this.z + 1 + f));
            for (final EntityHuman entityhuman : list) {
                if (entityhuman.activeContainer instanceof ContainerChest) {
                    final IInventory iinventory = ((ContainerChest)entityhuman.activeContainer).e();
                    if (iinventory != this && (!(iinventory instanceof InventoryLargeChest) || !((InventoryLargeChest)iinventory).a(this))) {
                        continue;
                    }
                    ++this.h;
                }
            }
        }
        this.g = this.f;
        final float f = 0.1f;
        if (this.h > 0 && this.f == 0.0f && this.b == null && this.d == null) {
            double d1 = this.x + 0.5;
            double d2 = this.z + 0.5;
            if (this.e != null) {
                d2 += 0.5;
            }
            if (this.c != null) {
                d1 += 0.5;
            }
            this.world.makeSound(d1, this.y + 0.5, d2, "random.chestopen", 0.5f, this.world.random.nextFloat() * 0.1f + 0.9f);
        }
        if ((this.h == 0 && this.f > 0.0f) || (this.h > 0 && this.f < 1.0f)) {
            final float f2 = this.f;
            if (this.h > 0) {
                this.f += f;
            }
            else {
                this.f -= f;
            }
            if (this.f > 1.0f) {
                this.f = 1.0f;
            }
            final float f3 = 0.5f;
            if (this.f < f3 && f2 >= f3 && this.b == null && this.d == null) {
                double d2 = this.x + 0.5;
                double d3 = this.z + 0.5;
                if (this.e != null) {
                    d3 += 0.5;
                }
                if (this.c != null) {
                    d2 += 0.5;
                }
                this.world.makeSound(d2, this.y + 0.5, d3, "random.chestclosed", 0.5f, this.world.random.nextFloat() * 0.1f + 0.9f);
            }
            if (this.f < 0.0f) {
                this.f = 0.0f;
            }
        }
    }
    
    public boolean b(final int i, final int j) {
        if (i == 1) {
            this.h = j;
            return true;
        }
        return super.b(i, j);
    }
    
    public void startOpen() {
        if (this.h < 0) {
            this.h = 0;
        }
        final int oldPower = Math.max(0, Math.min(15, this.h));
        ++this.h;
        if (this.world == null) {
            return;
        }
        this.world.playNote(this.x, this.y, this.z, this.q().id, 1, this.h);
        if (this.q().id == Block.TRAPPED_CHEST.id) {
            final int newPower = Math.max(0, Math.min(15, this.h));
            if (oldPower != newPower) {
                CraftEventFactory.callRedstoneChange(this.world, this.x, this.y, this.z, oldPower, newPower);
            }
        }
        this.world.applyPhysics(this.x, this.y, this.z, this.q().id);
        this.world.applyPhysics(this.x, this.y - 1, this.z, this.q().id);
    }
    
    public void g() {
        if (this.q() != null && this.q() instanceof BlockChest) {
            final int oldPower = Math.max(0, Math.min(15, this.h));
            --this.h;
            if (this.world == null) {
                return;
            }
            this.world.playNote(this.x, this.y, this.z, this.q().id, 1, this.h);
            if (this.q().id == Block.TRAPPED_CHEST.id) {
                final int newPower = Math.max(0, Math.min(15, this.h));
                if (oldPower != newPower) {
                    CraftEventFactory.callRedstoneChange(this.world, this.x, this.y, this.z, oldPower, newPower);
                }
            }
            this.world.applyPhysics(this.x, this.y, this.z, this.q().id);
            this.world.applyPhysics(this.x, this.y - 1, this.z, this.q().id);
        }
    }
    
    public boolean b(final int i, final ItemStack itemstack) {
        return true;
    }
    
    public void w_() {
        super.w_();
        this.i();
        this.j();
    }
    
    public int l() {
        if (this.r == -1) {
            if (this.world == null || !(this.q() instanceof BlockChest)) {
                return 0;
            }
            this.r = ((BlockChest)this.q()).a;
        }
        return this.r;
    }
}
