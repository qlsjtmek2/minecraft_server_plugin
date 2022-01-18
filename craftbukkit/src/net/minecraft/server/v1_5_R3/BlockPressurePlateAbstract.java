// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.plugin.PluginManager;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;
import java.util.Random;

public abstract class BlockPressurePlateAbstract extends Block
{
    private String a;
    
    protected BlockPressurePlateAbstract(final int i, final String s, final Material material) {
        super(i, material);
        this.a = s;
        this.a(CreativeModeTab.d);
        this.b(true);
        this.b_(this.d(15));
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        this.b_(iblockaccess.getData(i, j, k));
    }
    
    protected void b_(final int i) {
        final boolean flag = this.c(i) > 0;
        final float f = 0.0625f;
        if (flag) {
            this.a(f, 0.0f, f, 1.0f - f, 0.03125f, 1.0f - f);
        }
        else {
            this.a(f, 0.0f, f, 1.0f - f, 0.0625f, 1.0f - f);
        }
    }
    
    public int a(final World world) {
        return 20;
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
    
    public boolean b(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        return true;
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k) {
        return world.w(i, j - 1, k) || BlockFence.l_(world.getTypeId(i, j - 1, k));
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        boolean flag = false;
        if (!world.w(i, j - 1, k) && !BlockFence.l_(world.getTypeId(i, j - 1, k))) {
            flag = true;
        }
        if (flag) {
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setAir(i, j, k);
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (!world.isStatic) {
            final int l = this.c(world.getData(i, j, k));
            if (l > 0) {
                this.b(world, i, j, k, l);
            }
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, final Entity entity) {
        if (!world.isStatic) {
            final int l = this.c(world.getData(i, j, k));
            if (l == 0) {
                this.b(world, i, j, k, l);
            }
        }
    }
    
    protected void b(final World world, final int i, final int j, final int k, final int l) {
        int i2 = this.e(world, i, j, k);
        final boolean flag = l > 0;
        boolean flag2 = i2 > 0;
        final org.bukkit.World bworld = world.getWorld();
        final PluginManager manager = world.getServer().getPluginManager();
        if (flag != flag2) {
            final BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bworld.getBlockAt(i, j, k), l, i2);
            manager.callEvent(eventRedstone);
            flag2 = (eventRedstone.getNewCurrent() > 0);
            i2 = eventRedstone.getNewCurrent();
        }
        if (l != i2) {
            world.setData(i, j, k, this.d(i2), 2);
            this.b_(world, i, j, k);
            world.g(i, j, k, i, j, k);
        }
        if (!flag2 && flag) {
            world.makeSound(i + 0.5, j + 0.1, k + 0.5, "random.click", 0.3f, 0.5f);
        }
        else if (flag2 && !flag) {
            world.makeSound(i + 0.5, j + 0.1, k + 0.5, "random.click", 0.3f, 0.6f);
        }
        if (flag2) {
            world.a(i, j, k, this.id, this.a(world));
        }
    }
    
    protected AxisAlignedBB a(final int i, final int j, final int k) {
        final float f = 0.125f;
        return AxisAlignedBB.a().a(i + f, j, k + f, i + 1 - f, j + 0.25, k + 1 - f);
    }
    
    public void remove(final World world, final int i, final int j, final int k, final int l, final int i1) {
        if (this.c(i1) > 0) {
            this.b_(world, i, j, k);
        }
        super.remove(world, i, j, k, l, i1);
    }
    
    protected void b_(final World world, final int i, final int j, final int k) {
        world.applyPhysics(i, j, k, this.id);
        world.applyPhysics(i, j - 1, k, this.id);
    }
    
    public int b(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        return this.c(iblockaccess.getData(i, j, k));
    }
    
    public int c(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        return (l == 1) ? this.c(iblockaccess.getData(i, j, k)) : 0;
    }
    
    public boolean isPowerSource() {
        return true;
    }
    
    public void g() {
        final float f = 0.5f;
        final float f2 = 0.125f;
        final float f3 = 0.5f;
        this.a(0.5f - f, 0.5f - f2, 0.5f - f3, 0.5f + f, 0.5f + f2, 0.5f + f3);
    }
    
    public int h() {
        return 1;
    }
    
    protected abstract int e(final World p0, final int p1, final int p2, final int p3);
    
    protected abstract int c(final int p0);
    
    protected abstract int d(final int p0);
}
