// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockTrapdoor extends Block
{
    protected BlockTrapdoor(final int i, final Material material) {
        super(i, material);
        final float f = 0.5f;
        final float f2 = 1.0f;
        this.a(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, f2, 0.5f + f);
        this.a(CreativeModeTab.d);
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean b(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        return !f(iblockaccess.getData(i, j, k));
    }
    
    public int d() {
        return 0;
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        this.updateShape(world, i, j, k);
        return super.b(world, i, j, k);
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        this.d(iblockaccess.getData(i, j, k));
    }
    
    public void g() {
        final float f = 0.1875f;
        this.a(0.0f, 0.5f - f / 2.0f, 0.0f, 1.0f, 0.5f + f / 2.0f, 1.0f);
    }
    
    public void d(final int i) {
        final float f = 0.1875f;
        if ((i & 0x8) != 0x0) {
            this.a(0.0f, 1.0f - f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, f, 1.0f);
        }
        if (f(i)) {
            if ((i & 0x3) == 0x0) {
                this.a(0.0f, 0.0f, 1.0f - f, 1.0f, 1.0f, 1.0f);
            }
            if ((i & 0x3) == 0x1) {
                this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f);
            }
            if ((i & 0x3) == 0x2) {
                this.a(1.0f - f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
            if ((i & 0x3) == 0x3) {
                this.a(0.0f, 0.0f, 0.0f, f, 1.0f, 1.0f);
            }
        }
    }
    
    public void attack(final World world, final int i, final int j, final int k, final EntityHuman entityhuman) {
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityhuman, final int l, final float f, final float f1, final float f2) {
        if (this.material == Material.ORE) {
            return true;
        }
        final int i2 = world.getData(i, j, k);
        world.setData(i, j, k, i2 ^ 0x4, 2);
        world.a(entityhuman, 1003, i, j, k, 0);
        return true;
    }
    
    public void setOpen(final World world, final int i, final int j, final int k, final boolean flag) {
        final int l = world.getData(i, j, k);
        final boolean flag2 = (l & 0x4) > 0;
        if (flag2 != flag) {
            world.setData(i, j, k, l ^ 0x4, 2);
            world.a(null, 1003, i, j, k, 0);
        }
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        if (!world.isStatic) {
            final int i2 = world.getData(i, j, k);
            int j2 = i;
            int k2 = k;
            if ((i2 & 0x3) == 0x0) {
                k2 = k + 1;
            }
            if ((i2 & 0x3) == 0x1) {
                --k2;
            }
            if ((i2 & 0x3) == 0x2) {
                j2 = i + 1;
            }
            if ((i2 & 0x3) == 0x3) {
                --j2;
            }
            if (!g(world.getTypeId(j2, j, k2))) {
                world.setAir(i, j, k);
                this.c(world, i, j, k, i2, 0);
            }
            if (l == 0 || (l > 0 && Block.byId[l] != null && Block.byId[l].isPowerSource())) {
                final org.bukkit.World bworld = world.getWorld();
                final org.bukkit.block.Block block = bworld.getBlockAt(i, j, k);
                final int power = block.getBlockPower();
                final int oldPower = ((world.getData(i, j, k) & 0x4) > 0) ? 15 : 0;
                if ((oldPower == 0 ^ power == 0) || (Block.byId[l] != null && Block.byId[l].isPowerSource())) {
                    final BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, oldPower, power);
                    world.getServer().getPluginManager().callEvent(eventRedstone);
                    this.setOpen(world, i, j, k, eventRedstone.getNewCurrent() > 0);
                }
            }
        }
    }
    
    public MovingObjectPosition a(final World world, final int i, final int j, final int k, final Vec3D vec3d, final Vec3D vec3d1) {
        this.updateShape(world, i, j, k);
        return super.a(world, i, j, k, vec3d, vec3d1);
    }
    
    public int getPlacedData(final World world, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2, final int i1) {
        int j2 = 0;
        if (l == 2) {
            j2 = 0;
        }
        if (l == 3) {
            j2 = 1;
        }
        if (l == 4) {
            j2 = 2;
        }
        if (l == 5) {
            j2 = 3;
        }
        if (l != 1 && l != 0 && f1 > 0.5f) {
            j2 |= 0x8;
        }
        return j2;
    }
    
    public boolean canPlace(final World world, int i, final int j, int k, final int l) {
        if (l == 0) {
            return false;
        }
        if (l == 1) {
            return false;
        }
        if (l == 2) {
            ++k;
        }
        if (l == 3) {
            --k;
        }
        if (l == 4) {
            ++i;
        }
        if (l == 5) {
            --i;
        }
        return g(world.getTypeId(i, j, k));
    }
    
    public static boolean f(final int i) {
        return (i & 0x4) != 0x0;
    }
    
    private static boolean g(final int i) {
        if (i <= 0) {
            return false;
        }
        final Block block = Block.byId[i];
        return (block != null && block.material.k() && block.b()) || block == Block.GLOWSTONE || block instanceof BlockStepAbstract || block instanceof BlockStairs;
    }
}
