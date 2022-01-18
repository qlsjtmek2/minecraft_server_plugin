// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockTripwireHook extends Block
{
    public BlockTripwireHook(final int i) {
        super(i, Material.ORIENTABLE);
        this.a(CreativeModeTab.d);
        this.b(true);
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
        return 29;
    }
    
    public int a(final World world) {
        return 10;
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k, final int l) {
        return (l == 2 && world.u(i, j, k + 1)) || (l == 3 && world.u(i, j, k - 1)) || (l == 4 && world.u(i + 1, j, k)) || (l == 5 && world.u(i - 1, j, k));
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k) {
        return world.u(i - 1, j, k) || world.u(i + 1, j, k) || world.u(i, j, k - 1) || world.u(i, j, k + 1);
    }
    
    public int getPlacedData(final World world, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2, final int i1) {
        byte b0 = 0;
        if (l == 2 && world.c(i, j, k + 1, true)) {
            b0 = 2;
        }
        if (l == 3 && world.c(i, j, k - 1, true)) {
            b0 = 0;
        }
        if (l == 4 && world.c(i + 1, j, k, true)) {
            b0 = 1;
        }
        if (l == 5 && world.c(i - 1, j, k, true)) {
            b0 = 3;
        }
        return b0;
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final int l) {
        this.a(world, i, j, k, this.id, l, false, -1, 0);
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        if (l != this.id && this.k(world, i, j, k)) {
            final int i2 = world.getData(i, j, k);
            final int j2 = i2 & 0x3;
            boolean flag = false;
            if (!world.u(i - 1, j, k) && j2 == 3) {
                flag = true;
            }
            if (!world.u(i + 1, j, k) && j2 == 1) {
                flag = true;
            }
            if (!world.u(i, j, k - 1) && j2 == 0) {
                flag = true;
            }
            if (!world.u(i, j, k + 1) && j2 == 2) {
                flag = true;
            }
            if (flag) {
                this.c(world, i, j, k, i2, 0);
                world.setAir(i, j, k);
            }
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, final int l, int i1, final boolean flag, final int j1, final int k1) {
        final int l2 = i1 & 0x3;
        final boolean flag2 = (i1 & 0x4) == 0x4;
        final boolean flag3 = (i1 & 0x8) == 0x8;
        boolean flag4 = l == Block.TRIPWIRE_SOURCE.id;
        boolean flag5 = false;
        final boolean flag6 = !world.w(i, j - 1, k);
        final int i2 = Direction.a[l2];
        final int j2 = Direction.b[l2];
        int k2 = 0;
        final int[] aint = new int[42];
        int i3 = 1;
        while (i3 < 42) {
            final int l3 = i + i2 * i3;
            final int k3 = k + j2 * i3;
            final int j3 = world.getTypeId(l3, j, k3);
            if (j3 == Block.TRIPWIRE_SOURCE.id) {
                final int l4 = world.getData(l3, j, k3);
                if ((l4 & 0x3) == Direction.f[l2]) {
                    k2 = i3;
                    break;
                }
                break;
            }
            else {
                if (j3 != Block.TRIPWIRE.id && i3 != j1) {
                    aint[i3] = -1;
                    flag4 = false;
                }
                else {
                    final int l4 = (i3 == j1) ? k1 : world.getData(l3, j, k3);
                    final boolean flag7 = (l4 & 0x8) != 0x8;
                    final boolean flag8 = (l4 & 0x1) == 0x1;
                    final boolean flag9 = (l4 & 0x2) == 0x2;
                    flag4 &= (flag9 == flag6);
                    flag5 |= (flag7 && flag8);
                    aint[i3] = l4;
                    if (i3 == j1) {
                        world.a(i, j, k, l, this.a(world));
                        flag4 &= flag7;
                    }
                }
                ++i3;
            }
        }
        flag4 &= (k2 > 1);
        flag5 &= flag4;
        i3 = ((flag4 ? 4 : 0) | (flag5 ? 8 : 0));
        i1 = (l2 | i3);
        if (k2 > 0) {
            final int l3 = i + i2 * k2;
            final int k3 = k + j2 * k2;
            final int j3 = Direction.f[l2];
            world.setData(l3, j, k3, j3 | i3, 3);
            this.d(world, l3, j, k3, j3);
            this.a(world, l3, j, k3, flag4, flag5, flag2, flag3);
        }
        final org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
        final BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 15, 0);
        world.getServer().getPluginManager().callEvent(eventRedstone);
        if (eventRedstone.getNewCurrent() > 0) {
            return;
        }
        this.a(world, i, j, k, flag4, flag5, flag2, flag3);
        if (l > 0) {
            world.setData(i, j, k, i1, 3);
            if (flag) {
                this.d(world, i, j, k, l2);
            }
        }
        if (flag2 != flag4) {
            for (int l3 = 1; l3 < k2; ++l3) {
                final int k3 = i + i2 * l3;
                final int j3 = k + j2 * l3;
                int l4 = aint[l3];
                if (l4 >= 0) {
                    if (flag4) {
                        l4 |= 0x4;
                    }
                    else {
                        l4 &= 0xFFFFFFFB;
                    }
                    world.setData(k3, j, j3, l4, 3);
                }
            }
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        this.a(world, i, j, k, this.id, world.getData(i, j, k), true, -1, 0);
    }
    
    private void a(final World world, final int i, final int j, final int k, final boolean flag, final boolean flag1, final boolean flag2, final boolean flag3) {
        if (flag1 && !flag3) {
            world.makeSound(i + 0.5, j + 0.1, k + 0.5, "random.click", 0.4f, 0.6f);
        }
        else if (!flag1 && flag3) {
            world.makeSound(i + 0.5, j + 0.1, k + 0.5, "random.click", 0.4f, 0.5f);
        }
        else if (flag && !flag2) {
            world.makeSound(i + 0.5, j + 0.1, k + 0.5, "random.click", 0.4f, 0.7f);
        }
        else if (!flag && flag2) {
            world.makeSound(i + 0.5, j + 0.1, k + 0.5, "random.bowhit", 0.4f, 1.2f / (world.random.nextFloat() * 0.2f + 0.9f));
        }
    }
    
    private void d(final World world, final int i, final int j, final int k, final int l) {
        world.applyPhysics(i, j, k, this.id);
        if (l == 3) {
            world.applyPhysics(i - 1, j, k, this.id);
        }
        else if (l == 1) {
            world.applyPhysics(i + 1, j, k, this.id);
        }
        else if (l == 0) {
            world.applyPhysics(i, j, k - 1, this.id);
        }
        else if (l == 2) {
            world.applyPhysics(i, j, k + 1, this.id);
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
        final int l = iblockaccess.getData(i, j, k) & 0x3;
        final float f = 0.1875f;
        if (l == 3) {
            this.a(0.0f, 0.2f, 0.5f - f, f * 2.0f, 0.8f, 0.5f + f);
        }
        else if (l == 1) {
            this.a(1.0f - f * 2.0f, 0.2f, 0.5f - f, 1.0f, 0.8f, 0.5f + f);
        }
        else if (l == 0) {
            this.a(0.5f - f, 0.2f, 0.0f, 0.5f + f, 0.8f, f * 2.0f);
        }
        else if (l == 2) {
            this.a(0.5f - f, 0.2f, 1.0f - f * 2.0f, 0.5f + f, 0.8f, 1.0f);
        }
    }
    
    public void remove(final World world, final int i, final int j, final int k, final int l, final int i1) {
        final boolean flag = (i1 & 0x4) == 0x4;
        final boolean flag2 = (i1 & 0x8) == 0x8;
        if (flag || flag2) {
            this.a(world, i, j, k, 0, i1, false, -1, 0);
        }
        if (flag2) {
            world.applyPhysics(i, j, k, this.id);
            final int j2 = i1 & 0x3;
            if (j2 == 3) {
                world.applyPhysics(i - 1, j, k, this.id);
            }
            else if (j2 == 1) {
                world.applyPhysics(i + 1, j, k, this.id);
            }
            else if (j2 == 0) {
                world.applyPhysics(i, j, k - 1, this.id);
            }
            else if (j2 == 2) {
                world.applyPhysics(i, j, k + 1, this.id);
            }
        }
        super.remove(world, i, j, k, l, i1);
    }
    
    public int b(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        return ((iblockaccess.getData(i, j, k) & 0x8) == 0x8) ? 15 : 0;
    }
    
    public int c(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        final int i2 = iblockaccess.getData(i, j, k);
        if ((i2 & 0x8) != 0x8) {
            return 0;
        }
        final int j2 = i2 & 0x3;
        return (j2 == 2 && l == 2) ? 15 : ((j2 == 0 && l == 3) ? 15 : ((j2 == 1 && l == 4) ? 15 : ((j2 == 3 && l == 5) ? 15 : 0)));
    }
    
    public boolean isPowerSource() {
        return true;
    }
}
