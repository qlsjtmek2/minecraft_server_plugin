// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import java.util.List;

public class TileEntityBeacon extends TileEntity implements IInventory
{
    public static final MobEffectList[][] a;
    private boolean d;
    private int e;
    private int f;
    private int g;
    private ItemStack inventorySlot;
    private String i;
    public List<HumanEntity> transaction;
    private int maxStack;
    
    public ItemStack[] getContents() {
        return new ItemStack[] { this.inventorySlot };
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
    
    public TileEntityBeacon() {
        this.e = -1;
        this.transaction = new ArrayList<HumanEntity>();
        this.maxStack = 64;
    }
    
    public void h() {
        if (this.world.getTime() % 80L == 0L) {
            this.v();
            this.u();
        }
    }
    
    private void u() {
        if (this.d && this.e > 0 && !this.world.isStatic && this.f > 0) {
            final double d0 = this.e * 10 + 10;
            byte b0 = 0;
            if (this.e >= 4 && this.f == this.g) {
                b0 = 1;
            }
            final AxisAlignedBB axisalignedbb = AxisAlignedBB.a().a(this.x, this.y, this.z, this.x + 1, this.y + 1, this.z + 1).grow(d0, d0, d0);
            axisalignedbb.e = this.world.getHeight();
            final List list = this.world.a(EntityHuman.class, axisalignedbb);
            for (final EntityHuman entityhuman : list) {
                entityhuman.addEffect(new MobEffect(this.f, 180, b0, true));
            }
            if (this.e >= 4 && this.f != this.g && this.g > 0) {
                for (final EntityHuman entityhuman : list) {
                    entityhuman.addEffect(new MobEffect(this.g, 180, 0, true));
                }
            }
        }
    }
    
    private void v() {
        if (!this.world.l(this.x, this.y + 1, this.z)) {
            this.d = false;
            this.e = 0;
        }
        else {
            this.d = true;
            this.e = 0;
            for (int i = 1; i <= 4; this.e = i++) {
                final int j = this.y - i;
                if (j < 1) {
                    break;
                }
                boolean flag = true;
                for (int k = this.x - i; k <= this.x + i && flag; ++k) {
                    for (int l = this.z - i; l <= this.z + i; ++l) {
                        final int i2 = this.world.getTypeId(k, j, l);
                        if (i2 != Block.EMERALD_BLOCK.id && i2 != Block.GOLD_BLOCK.id && i2 != Block.DIAMOND_BLOCK.id && i2 != Block.IRON_BLOCK.id) {
                            flag = false;
                            break;
                        }
                    }
                }
                if (!flag) {
                    break;
                }
            }
            if (this.e == 0) {
                this.d = false;
            }
        }
    }
    
    public int j() {
        return this.f;
    }
    
    public int k() {
        return this.g;
    }
    
    public int l() {
        return this.e;
    }
    
    public void d(final int i) {
        this.f = 0;
        for (int j = 0; j < this.e && j < 3; ++j) {
            for (final MobEffectList mobeffectlist : TileEntityBeacon.a[j]) {
                if (mobeffectlist.id == i) {
                    this.f = i;
                    return;
                }
            }
        }
    }
    
    public void e(final int i) {
        this.g = 0;
        if (this.e >= 4) {
            for (int j = 0; j < 4; ++j) {
                for (final MobEffectList mobeffectlist : TileEntityBeacon.a[j]) {
                    if (mobeffectlist.id == i) {
                        this.g = i;
                        return;
                    }
                }
            }
        }
    }
    
    public Packet getUpdatePacket() {
        final NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.b(nbttagcompound);
        return new Packet132TileEntityData(this.x, this.y, this.z, 3, nbttagcompound);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.f = nbttagcompound.getInt("Primary");
        this.g = nbttagcompound.getInt("Secondary");
        this.e = nbttagcompound.getInt("Levels");
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Primary", this.f);
        nbttagcompound.setInt("Secondary", this.g);
        nbttagcompound.setInt("Levels", this.e);
    }
    
    public int getSize() {
        return 1;
    }
    
    public ItemStack getItem(final int i) {
        return (i == 0) ? this.inventorySlot : null;
    }
    
    public ItemStack splitStack(final int i, final int j) {
        if (i != 0 || this.inventorySlot == null) {
            return null;
        }
        if (j >= this.inventorySlot.count) {
            final ItemStack itemstack = this.inventorySlot;
            this.inventorySlot = null;
            return itemstack;
        }
        final ItemStack inventorySlot = this.inventorySlot;
        inventorySlot.count -= j;
        return new ItemStack(this.inventorySlot.id, j, this.inventorySlot.getData());
    }
    
    public ItemStack splitWithoutUpdate(final int i) {
        if (i == 0 && this.inventorySlot != null) {
            final ItemStack itemstack = this.inventorySlot;
            this.inventorySlot = null;
            return itemstack;
        }
        return null;
    }
    
    public void setItem(final int i, final ItemStack itemstack) {
        if (i == 0) {
            this.inventorySlot = itemstack;
        }
    }
    
    public String getName() {
        return this.c() ? this.i : "container.beacon";
    }
    
    public boolean c() {
        return this.i != null && this.i.length() > 0;
    }
    
    public void a(final String s) {
        this.i = s;
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
        return itemstack.id == Item.EMERALD.id || itemstack.id == Item.DIAMOND.id || itemstack.id == Item.GOLD_INGOT.id || itemstack.id == Item.IRON_INGOT.id;
    }
    
    static {
        a = new MobEffectList[][] { { MobEffectList.FASTER_MOVEMENT, MobEffectList.FASTER_DIG }, { MobEffectList.RESISTANCE, MobEffectList.JUMP }, { MobEffectList.INCREASE_DAMAGE }, { MobEffectList.REGENERATION } };
    }
}
