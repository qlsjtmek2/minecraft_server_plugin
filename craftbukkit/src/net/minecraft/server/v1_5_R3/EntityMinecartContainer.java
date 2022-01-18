// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import java.util.List;

public abstract class EntityMinecartContainer extends EntityMinecartAbstract implements IInventory
{
    private ItemStack[] items;
    private boolean b;
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
    
    public InventoryHolder getOwner() {
        final org.bukkit.entity.Entity cart = this.getBukkitEntity();
        if (cart instanceof InventoryHolder) {
            return (InventoryHolder)cart;
        }
        return null;
    }
    
    public void setMaxStackSize(final int size) {
        this.maxStack = size;
    }
    
    public EntityMinecartContainer(final World world) {
        super(world);
        this.items = new ItemStack[27];
        this.b = true;
        this.transaction = new ArrayList<HumanEntity>();
        this.maxStack = 64;
    }
    
    public EntityMinecartContainer(final World world, final double d0, final double d1, final double d2) {
        super(world, d0, d1, d2);
        this.items = new ItemStack[27];
        this.b = true;
        this.transaction = new ArrayList<HumanEntity>();
        this.maxStack = 64;
    }
    
    public void a(final DamageSource damagesource) {
        super.a(damagesource);
        for (int i = 0; i < this.getSize(); ++i) {
            final ItemStack itemstack = this.getItem(i);
            if (itemstack != null) {
                final float f = this.random.nextFloat() * 0.8f + 0.1f;
                final float f2 = this.random.nextFloat() * 0.8f + 0.1f;
                final float f3 = this.random.nextFloat() * 0.8f + 0.1f;
                while (itemstack.count > 0) {
                    int j = this.random.nextInt(21) + 10;
                    if (j > itemstack.count) {
                        j = itemstack.count;
                    }
                    final ItemStack itemStack = itemstack;
                    itemStack.count -= j;
                    final EntityItem entityitem = new EntityItem(this.world, this.locX + f, this.locY + f2, this.locZ + f3, new ItemStack(itemstack.id, j, itemstack.getData()));
                    final float f4 = 0.05f;
                    entityitem.motX = (float)this.random.nextGaussian() * f4;
                    entityitem.motY = (float)this.random.nextGaussian() * f4 + 0.2f;
                    entityitem.motZ = (float)this.random.nextGaussian() * f4;
                    this.world.addEntity(entityitem);
                }
            }
        }
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
            return itemstack;
        }
        final ItemStack itemstack = this.items[i].a(j);
        if (this.items[i].count == 0) {
            this.items[i] = null;
        }
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
    }
    
    public void update() {
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return !this.dead && entityhuman.e(this) <= 64.0;
    }
    
    public void startOpen() {
    }
    
    public void g() {
    }
    
    public boolean b(final int i, final ItemStack itemstack) {
        return true;
    }
    
    public String getName() {
        return this.c() ? this.t() : "container.minecart";
    }
    
    public int getMaxStackSize() {
        return 64;
    }
    
    public void c(final int i) {
        this.b = false;
        super.c(i);
    }
    
    public void die() {
        if (this.b) {
            for (int i = 0; i < this.getSize(); ++i) {
                final ItemStack itemstack = this.getItem(i);
                if (itemstack != null) {
                    final float f = this.random.nextFloat() * 0.8f + 0.1f;
                    final float f2 = this.random.nextFloat() * 0.8f + 0.1f;
                    final float f3 = this.random.nextFloat() * 0.8f + 0.1f;
                    while (itemstack.count > 0) {
                        int j = this.random.nextInt(21) + 10;
                        if (j > itemstack.count) {
                            j = itemstack.count;
                        }
                        final ItemStack itemStack = itemstack;
                        itemStack.count -= j;
                        final EntityItem entityitem = new EntityItem(this.world, this.locX + f, this.locY + f2, this.locZ + f3, new ItemStack(itemstack.id, j, itemstack.getData()));
                        if (itemstack.hasTag()) {
                            entityitem.getItemStack().setTag((NBTTagCompound)itemstack.getTag().clone());
                        }
                        final float f4 = 0.05f;
                        entityitem.motX = (float)this.random.nextGaussian() * f4;
                        entityitem.motY = (float)this.random.nextGaussian() * f4 + 0.2f;
                        entityitem.motZ = (float)this.random.nextGaussian() * f4;
                        this.world.addEntity(entityitem);
                    }
                }
            }
        }
        super.die();
    }
    
    protected void b(final NBTTagCompound nbttagcompound) {
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
    }
    
    protected void a(final NBTTagCompound nbttagcompound) {
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
    }
    
    public boolean a_(final EntityHuman entityhuman) {
        if (!this.world.isStatic) {
            entityhuman.openContainer(this);
        }
        return true;
    }
    
    protected void h() {
        final int i = 15 - Container.b(this);
        final float f = 0.98f + i * 0.001f;
        this.motX *= f;
        this.motY *= 0.0;
        this.motZ *= f;
    }
}
