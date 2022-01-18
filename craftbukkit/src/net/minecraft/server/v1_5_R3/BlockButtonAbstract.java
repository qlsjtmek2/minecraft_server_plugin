// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.List;
import org.bukkit.event.entity.EntityInteractEvent;
import java.util.Random;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;

public abstract class BlockButtonAbstract extends Block
{
    private final boolean a;
    
    protected BlockButtonAbstract(final int i, final boolean flag) {
        super(i, Material.ORIENTABLE);
        this.b(true);
        this.a(CreativeModeTab.d);
        this.a = flag;
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        return null;
    }
    
    public int a(final World world) {
        return this.a ? 30 : 20;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k, final int l) {
        return (l == 2 && world.u(i, j, k + 1)) || (l == 3 && world.u(i, j, k - 1)) || (l == 4 && world.u(i + 1, j, k)) || (l == 5 && world.u(i - 1, j, k));
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k) {
        return world.u(i - 1, j, k) || world.u(i + 1, j, k) || world.u(i, j, k - 1) || world.u(i, j, k + 1);
    }
    
    public int getPlacedData(final World world, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2, final int i1) {
        int j2 = world.getData(i, j, k);
        final int k2 = j2 & 0x8;
        j2 &= 0x7;
        if (l == 2 && world.u(i, j, k + 1)) {
            j2 = 4;
        }
        else if (l == 3 && world.u(i, j, k - 1)) {
            j2 = 3;
        }
        else if (l == 4 && world.u(i + 1, j, k)) {
            j2 = 2;
        }
        else if (l == 5 && world.u(i - 1, j, k)) {
            j2 = 1;
        }
        else {
            j2 = this.k(world, i, j, k);
        }
        return j2 + k2;
    }
    
    private int k(final World world, final int i, final int j, final int k) {
        return world.u(i - 1, j, k) ? 1 : (world.u(i + 1, j, k) ? 2 : (world.u(i, j, k - 1) ? 3 : (world.u(i, j, k + 1) ? 4 : 1)));
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        if (this.m(world, i, j, k)) {
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
            if (flag) {
                this.c(world, i, j, k, world.getData(i, j, k), 0);
                world.setAir(i, j, k);
            }
        }
    }
    
    private boolean m(final World world, final int i, final int j, final int k) {
        if (!this.canPlace(world, i, j, k)) {
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setAir(i, j, k);
            return false;
        }
        return true;
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        final int l = iblockaccess.getData(i, j, k);
        this.d(l);
    }
    
    private void d(final int i) {
        final int j = i & 0x7;
        final boolean flag = (i & 0x8) > 0;
        final float f = 0.375f;
        final float f2 = 0.625f;
        final float f3 = 0.1875f;
        float f4 = 0.125f;
        if (flag) {
            f4 = 0.0625f;
        }
        if (j == 1) {
            this.a(0.0f, f, 0.5f - f3, f4, f2, 0.5f + f3);
        }
        else if (j == 2) {
            this.a(1.0f - f4, f, 0.5f - f3, 1.0f, f2, 0.5f + f3);
        }
        else if (j == 3) {
            this.a(0.5f - f3, f, 0.0f, 0.5f + f3, f2, f4);
        }
        else if (j == 4) {
            this.a(0.5f - f3, f, 1.0f - f4, 0.5f + f3, f2, 1.0f);
        }
    }
    
    public void attack(final World world, final int i, final int j, final int k, final EntityHuman entityhuman) {
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityhuman, final int l, final float f, final float f1, final float f2) {
        final int i2 = world.getData(i, j, k);
        final int j2 = i2 & 0x7;
        final int k2 = 8 - (i2 & 0x8);
        if (k2 == 0) {
            return true;
        }
        final org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
        final int old = (k2 != 8) ? 15 : 0;
        final int current = (k2 == 8) ? 15 : 0;
        final BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
        world.getServer().getPluginManager().callEvent(eventRedstone);
        if (eventRedstone.getNewCurrent() > 0 != (k2 == 8)) {
            return true;
        }
        world.setData(i, j, k, j2 + k2, 3);
        world.g(i, j, k, i, j, k);
        world.makeSound(i + 0.5, j + 0.5, k + 0.5, "random.click", 0.3f, 0.6f);
        this.d(world, i, j, k, j2);
        world.a(i, j, k, this.id, this.a(world));
        return true;
    }
    
    public void remove(final World world, final int i, final int j, final int k, final int l, final int i1) {
        if ((i1 & 0x8) > 0) {
            final int j2 = i1 & 0x7;
            this.d(world, i, j, k, j2);
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
        return (j2 == 5 && l == 1) ? 15 : ((j2 == 4 && l == 2) ? 15 : ((j2 == 3 && l == 3) ? 15 : ((j2 == 2 && l == 4) ? 15 : ((j2 == 1 && l == 5) ? 15 : 0))));
    }
    
    public boolean isPowerSource() {
        return true;
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (!world.isStatic) {
            final int l = world.getData(i, j, k);
            if ((l & 0x8) != 0x0) {
                final org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
                final BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 15, 0);
                world.getServer().getPluginManager().callEvent(eventRedstone);
                if (eventRedstone.getNewCurrent() > 0) {
                    return;
                }
                if (this.a) {
                    this.n(world, i, j, k);
                }
                else {
                    world.setData(i, j, k, l & 0x7, 3);
                    final int i2 = l & 0x7;
                    this.d(world, i, j, k, i2);
                    world.makeSound(i + 0.5, j + 0.5, k + 0.5, "random.click", 0.3f, 0.5f);
                    world.g(i, j, k, i, j, k);
                }
            }
        }
    }
    
    public void g() {
        final float f = 0.1875f;
        final float f2 = 0.125f;
        final float f3 = 0.125f;
        this.a(0.5f - f, 0.5f - f2, 0.5f - f3, 0.5f + f, 0.5f + f2, 0.5f + f3);
    }
    
    public void a(final World world, final int i, final int j, final int k, final Entity entity) {
        if (!world.isStatic && this.a && (world.getData(i, j, k) & 0x8) == 0x0) {
            this.n(world, i, j, k);
        }
    }
    
    private void n(final World world, final int i, final int j, final int k) {
        final int l = world.getData(i, j, k);
        final int i2 = l & 0x7;
        final boolean flag = (l & 0x8) != 0x0;
        this.d(l);
        final List list = world.a(EntityArrow.class, AxisAlignedBB.a().a(i + this.minX, j + this.minY, k + this.minZ, i + this.maxX, j + this.maxY, k + this.maxZ));
        final boolean flag2 = !list.isEmpty();
        if (flag != flag2 && flag2) {
            final org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            boolean allowed = false;
            for (final Object object : list) {
                if (object != null) {
                    final EntityInteractEvent event = new EntityInteractEvent(((Entity)object).getBukkitEntity(), block);
                    world.getServer().getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        allowed = true;
                        break;
                    }
                    continue;
                }
            }
            if (!allowed) {
                return;
            }
        }
        if (flag2 && !flag) {
            world.setData(i, j, k, i2 | 0x8, 3);
            this.d(world, i, j, k, i2);
            world.g(i, j, k, i, j, k);
            world.makeSound(i + 0.5, j + 0.5, k + 0.5, "random.click", 0.3f, 0.6f);
        }
        if (!flag2 && flag) {
            world.setData(i, j, k, i2, 3);
            this.d(world, i, j, k, i2);
            world.g(i, j, k, i, j, k);
            world.makeSound(i + 0.5, j + 0.5, k + 0.5, "random.click", 0.3f, 0.5f);
        }
        if (flag2) {
            world.a(i, j, k, this.id, this.a(world));
        }
    }
    
    private void d(final World world, final int i, final int j, final int k, final int l) {
        world.applyPhysics(i, j, k, this.id);
        if (l == 1) {
            world.applyPhysics(i - 1, j, k, this.id);
        }
        else if (l == 2) {
            world.applyPhysics(i + 1, j, k, this.id);
        }
        else if (l == 3) {
            world.applyPhysics(i, j, k - 1, this.id);
        }
        else if (l == 4) {
            world.applyPhysics(i, j, k + 1, this.id);
        }
        else {
            world.applyPhysics(i, j - 1, k, this.id);
        }
    }
}
