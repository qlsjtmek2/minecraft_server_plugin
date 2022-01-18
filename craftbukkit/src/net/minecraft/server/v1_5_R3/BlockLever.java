// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockLever extends Block
{
    protected BlockLever(final int i) {
        super(i, Material.ORIENTABLE);
        this.a(CreativeModeTab.d);
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        return null;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public int d() {
        return 12;
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k, final int l) {
        return (l == 0 && world.u(i, j + 1, k)) || (l == 1 && world.w(i, j - 1, k)) || (l == 2 && world.u(i, j, k + 1)) || (l == 3 && world.u(i, j, k - 1)) || (l == 4 && world.u(i + 1, j, k)) || (l == 5 && world.u(i - 1, j, k));
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k) {
        return world.u(i - 1, j, k) || world.u(i + 1, j, k) || world.u(i, j, k - 1) || world.u(i, j, k + 1) || world.w(i, j - 1, k) || world.u(i, j + 1, k);
    }
    
    public int getPlacedData(final World world, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2, final int i1) {
        final int j2 = i1 & 0x8;
        final int k2 = i1 & 0x7;
        byte b0 = -1;
        if (l == 0 && world.u(i, j + 1, k)) {
            b0 = 0;
        }
        if (l == 1 && world.w(i, j - 1, k)) {
            b0 = 5;
        }
        if (l == 2 && world.u(i, j, k + 1)) {
            b0 = 4;
        }
        if (l == 3 && world.u(i, j, k - 1)) {
            b0 = 3;
        }
        if (l == 4 && world.u(i + 1, j, k)) {
            b0 = 2;
        }
        if (l == 5 && world.u(i - 1, j, k)) {
            b0 = 1;
        }
        return b0 + j2;
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final EntityLiving entityliving, final ItemStack itemstack) {
        final int l = world.getData(i, j, k);
        final int i2 = l & 0x7;
        final int j2 = l & 0x8;
        if (i2 == d(1)) {
            if ((MathHelper.floor(entityliving.yaw * 4.0f / 360.0f + 0.5) & 0x1) == 0x0) {
                world.setData(i, j, k, 0x5 | j2, 2);
            }
            else {
                world.setData(i, j, k, 0x6 | j2, 2);
            }
        }
        else if (i2 == d(0)) {
            if ((MathHelper.floor(entityliving.yaw * 4.0f / 360.0f + 0.5) & 0x1) == 0x0) {
                world.setData(i, j, k, 0x7 | j2, 2);
            }
            else {
                world.setData(i, j, k, 0x0 | j2, 2);
            }
        }
    }
    
    public static int d(final int i) {
        switch (i) {
            case 0: {
                return 0;
            }
            case 1: {
                return 5;
            }
            case 2: {
                return 4;
            }
            case 3: {
                return 3;
            }
            case 4: {
                return 2;
            }
            case 5: {
                return 1;
            }
            default: {
                return -1;
            }
        }
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        if (this.k(world, i, j, k)) {
            final int i2 = world.getData(i, j, k) & 0x7;
            boolean flag = false;
            if (!world.u(i - 1, j, k) && i2 == 1) {
                flag = true;
            }
            if (!world.u(i + 1, j, k) && i2 == 2) {
                flag = true;
            }
            if (!world.u(i, j, k - 1) && i2 == 3) {
                flag = true;
            }
            if (!world.u(i, j, k + 1) && i2 == 4) {
                flag = true;
            }
            if (!world.w(i, j - 1, k) && i2 == 5) {
                flag = true;
            }
            if (!world.w(i, j - 1, k) && i2 == 6) {
                flag = true;
            }
            if (!world.u(i, j + 1, k) && i2 == 0) {
                flag = true;
            }
            if (!world.u(i, j + 1, k) && i2 == 7) {
                flag = true;
            }
            if (flag) {
                this.c(world, i, j, k, world.getData(i, j, k), 0);
                world.setAir(i, j, k);
            }
        }
    }
    
    private boolean k(final World world, final int i, final int j, final int k) {
        if (!this.canPlace(world, i, j, k)) {
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setAir(i, j, k);
            return false;
        }
        return true;
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        final int l = iblockaccess.getData(i, j, k) & 0x7;
        float f = 0.1875f;
        if (l == 1) {
            this.a(0.0f, 0.2f, 0.5f - f, f * 2.0f, 0.8f, 0.5f + f);
        }
        else if (l == 2) {
            this.a(1.0f - f * 2.0f, 0.2f, 0.5f - f, 1.0f, 0.8f, 0.5f + f);
        }
        else if (l == 3) {
            this.a(0.5f - f, 0.2f, 0.0f, 0.5f + f, 0.8f, f * 2.0f);
        }
        else if (l == 4) {
            this.a(0.5f - f, 0.2f, 1.0f - f * 2.0f, 0.5f + f, 0.8f, 1.0f);
        }
        else if (l != 5 && l != 6) {
            if (l == 0 || l == 7) {
                f = 0.25f;
                this.a(0.5f - f, 0.4f, 0.5f - f, 0.5f + f, 1.0f, 0.5f + f);
            }
        }
        else {
            f = 0.25f;
            this.a(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 0.6f, 0.5f + f);
        }
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityhuman, final int l, final float f, final float f1, final float f2) {
        if (world.isStatic) {
            return true;
        }
        final int i2 = world.getData(i, j, k);
        final int j2 = i2 & 0x7;
        final int k2 = 8 - (i2 & 0x8);
        final org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
        final int old = (k2 != 8) ? 15 : 0;
        final int current = (k2 == 8) ? 15 : 0;
        final BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
        world.getServer().getPluginManager().callEvent(eventRedstone);
        if (eventRedstone.getNewCurrent() > 0 != (k2 == 8)) {
            return true;
        }
        world.setData(i, j, k, j2 + k2, 3);
        world.makeSound(i + 0.5, j + 0.5, k + 0.5, "random.click", 0.3f, (k2 > 0) ? 0.6f : 0.5f);
        world.applyPhysics(i, j, k, this.id);
        if (j2 == 1) {
            world.applyPhysics(i - 1, j, k, this.id);
        }
        else if (j2 == 2) {
            world.applyPhysics(i + 1, j, k, this.id);
        }
        else if (j2 == 3) {
            world.applyPhysics(i, j, k - 1, this.id);
        }
        else if (j2 == 4) {
            world.applyPhysics(i, j, k + 1, this.id);
        }
        else if (j2 != 5 && j2 != 6) {
            if (j2 == 0 || j2 == 7) {
                world.applyPhysics(i, j + 1, k, this.id);
            }
        }
        else {
            world.applyPhysics(i, j - 1, k, this.id);
        }
        return true;
    }
    
    public void remove(final World world, final int i, final int j, final int k, final int l, final int i1) {
        if ((i1 & 0x8) > 0) {
            world.applyPhysics(i, j, k, this.id);
            final int j2 = i1 & 0x7;
            if (j2 == 1) {
                world.applyPhysics(i - 1, j, k, this.id);
            }
            else if (j2 == 2) {
                world.applyPhysics(i + 1, j, k, this.id);
            }
            else if (j2 == 3) {
                world.applyPhysics(i, j, k - 1, this.id);
            }
            else if (j2 == 4) {
                world.applyPhysics(i, j, k + 1, this.id);
            }
            else if (j2 != 5 && j2 != 6) {
                if (j2 == 0 || j2 == 7) {
                    world.applyPhysics(i, j + 1, k, this.id);
                }
            }
            else {
                world.applyPhysics(i, j - 1, k, this.id);
            }
        }
        super.remove(world, i, j, k, l, i1);
    }
    
    public int b(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        return ((iblockaccess.getData(i, j, k) & 0x8) > 0) ? 15 : 0;
    }
    
    public int c(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        final int i2 = iblockaccess.getData(i, j, k);
        if ((i2 & 0x8) == 0x0) {
            return 0;
        }
        final int j2 = i2 & 0x7;
        return (j2 == 0 && l == 0) ? 15 : ((j2 == 7 && l == 0) ? 15 : ((j2 == 6 && l == 1) ? 15 : ((j2 == 5 && l == 1) ? 15 : ((j2 == 4 && l == 2) ? 15 : ((j2 == 3 && l == 3) ? 15 : ((j2 == 2 && l == 4) ? 15 : ((j2 == 1 && l == 5) ? 15 : 0)))))));
    }
    
    public boolean isPowerSource() {
        return true;
    }
}
