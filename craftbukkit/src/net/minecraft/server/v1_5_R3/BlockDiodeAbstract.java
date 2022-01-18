// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.Random;

public abstract class BlockDiodeAbstract extends BlockDirectional
{
    protected final boolean a;
    
    protected BlockDiodeAbstract(final int i, final boolean flag) {
        super(i, Material.ORIENTABLE);
        this.a = flag;
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k) {
        return world.w(i, j - 1, k) && super.canPlace(world, i, j, k);
    }
    
    public boolean f(final World world, final int i, final int j, final int k) {
        return world.w(i, j - 1, k) && super.f(world, i, j, k);
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        final int l = world.getData(i, j, k);
        if (!this.e((IBlockAccess)world, i, j, k, l)) {
            final boolean flag = this.d(world, i, j, k, l);
            if (this.a && !flag) {
                if (CraftEventFactory.callRedstoneChange(world, i, j, k, 15, 0).getNewCurrent() != 0) {
                    return;
                }
                world.setTypeIdAndData(i, j, k, this.j().id, l, 2);
            }
            else if (!this.a) {
                if (CraftEventFactory.callRedstoneChange(world, i, j, k, 0, 15).getNewCurrent() != 15) {
                    return;
                }
                world.setTypeIdAndData(i, j, k, this.i().id, l, 2);
                if (!flag) {
                    world.a(i, j, k, this.i().id, this.h(l), -1);
                }
            }
        }
    }
    
    public int d() {
        return 36;
    }
    
    protected boolean c(final int i) {
        return this.a;
    }
    
    public int c(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        return this.b(iblockaccess, i, j, k, l);
    }
    
    public int b(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        final int i2 = iblockaccess.getData(i, j, k);
        if (!this.c(i2)) {
            return 0;
        }
        final int j2 = BlockDirectional.j(i2);
        return (j2 == 0 && l == 3) ? this.d(iblockaccess, i, j, k, i2) : ((j2 == 1 && l == 4) ? this.d(iblockaccess, i, j, k, i2) : ((j2 == 2 && l == 2) ? this.d(iblockaccess, i, j, k, i2) : ((j2 == 3 && l == 5) ? this.d(iblockaccess, i, j, k, i2) : 0)));
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        if (!this.f(world, i, j, k)) {
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setAir(i, j, k);
            world.applyPhysics(i + 1, j, k, this.id);
            world.applyPhysics(i - 1, j, k, this.id);
            world.applyPhysics(i, j, k + 1, this.id);
            world.applyPhysics(i, j, k - 1, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            world.applyPhysics(i, j + 1, k, this.id);
        }
        else {
            this.f(world, i, j, k, l);
        }
    }
    
    protected void f(final World world, final int i, final int j, final int k, final int l) {
        final int i2 = world.getData(i, j, k);
        if (!this.e((IBlockAccess)world, i, j, k, i2)) {
            final boolean flag = this.d(world, i, j, k, i2);
            if (((this.a && !flag) || (!this.a && flag)) && !world.a(i, j, k, this.id)) {
                byte b0 = -1;
                if (this.h(world, i, j, k, i2)) {
                    b0 = -3;
                }
                else if (this.a) {
                    b0 = -2;
                }
                world.a(i, j, k, this.id, this.i_(i2), b0);
            }
        }
    }
    
    public boolean e(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        return false;
    }
    
    protected boolean d(final World world, final int i, final int j, final int k, final int l) {
        return this.e(world, i, j, k, l) > 0;
    }
    
    protected int e(final World world, final int i, final int j, final int k, final int l) {
        final int i2 = BlockDirectional.j(l);
        final int j2 = i + Direction.a[i2];
        final int k2 = k + Direction.b[i2];
        final int l2 = world.getBlockFacePower(j2, j, k2, Direction.d[i2]);
        return (l2 >= 15) ? l2 : Math.max(l2, (world.getTypeId(j2, j, k2) == Block.REDSTONE_WIRE.id) ? world.getData(j2, j, k2) : 0);
    }
    
    protected int f(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        final int i2 = BlockDirectional.j(l);
        switch (i2) {
            case 0:
            case 2: {
                return Math.max(this.g(iblockaccess, i - 1, j, k, 4), this.g(iblockaccess, i + 1, j, k, 5));
            }
            case 1:
            case 3: {
                return Math.max(this.g(iblockaccess, i, j, k + 1, 3), this.g(iblockaccess, i, j, k - 1, 2));
            }
            default: {
                return 0;
            }
        }
    }
    
    protected int g(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        final int i2 = iblockaccess.getTypeId(i, j, k);
        return this.e(i2) ? ((i2 == Block.REDSTONE_WIRE.id) ? iblockaccess.getData(i, j, k) : iblockaccess.getBlockPower(i, j, k, l)) : 0;
    }
    
    public boolean isPowerSource() {
        return true;
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final EntityLiving entityliving, final ItemStack itemstack) {
        final int l = ((MathHelper.floor(entityliving.yaw * 4.0f / 360.0f + 0.5) & 0x3) + 2) % 4;
        world.setData(i, j, k, l, 3);
        final boolean flag = this.d(world, i, j, k, l);
        if (flag) {
            world.a(i, j, k, this.id, 1);
        }
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        this.h_(world, i, j, k);
    }
    
    protected void h_(final World world, final int i, final int j, final int k) {
        final int l = BlockDirectional.j(world.getData(i, j, k));
        if (l == 1) {
            world.g(i + 1, j, k, this.id);
            world.c(i + 1, j, k, this.id, 4);
        }
        if (l == 3) {
            world.g(i - 1, j, k, this.id);
            world.c(i - 1, j, k, this.id, 5);
        }
        if (l == 2) {
            world.g(i, j, k + 1, this.id);
            world.c(i, j, k + 1, this.id, 2);
        }
        if (l == 0) {
            world.g(i, j, k - 1, this.id);
            world.c(i, j, k - 1, this.id, 3);
        }
    }
    
    public void postBreak(final World world, final int i, final int j, final int k, final int l) {
        if (this.a) {
            world.applyPhysics(i + 1, j, k, this.id);
            world.applyPhysics(i - 1, j, k, this.id);
            world.applyPhysics(i, j, k + 1, this.id);
            world.applyPhysics(i, j, k - 1, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            world.applyPhysics(i, j + 1, k, this.id);
        }
        super.postBreak(world, i, j, k, l);
    }
    
    public boolean c() {
        return false;
    }
    
    protected boolean e(final int i) {
        final Block block = Block.byId[i];
        return block != null && block.isPowerSource();
    }
    
    protected int d(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        return 15;
    }
    
    public static boolean f(final int i) {
        return Block.DIODE_OFF.g(i) || Block.REDSTONE_COMPARATOR_OFF.g(i);
    }
    
    public boolean g(final int i) {
        return i == this.i().id || i == this.j().id;
    }
    
    public boolean h(final World world, final int i, final int j, final int k, final int l) {
        final int i2 = BlockDirectional.j(l);
        if (f(world.getTypeId(i - Direction.a[i2], j, k - Direction.b[i2]))) {
            final int j2 = world.getData(i - Direction.a[i2], j, k - Direction.b[i2]);
            final int k2 = BlockDirectional.j(j2);
            return k2 != i2;
        }
        return false;
    }
    
    protected int h(final int i) {
        return this.i_(i);
    }
    
    protected abstract int i_(final int p0);
    
    protected abstract BlockDiodeAbstract i();
    
    protected abstract BlockDiodeAbstract j();
    
    public boolean i(final int i) {
        return this.g(i);
    }
}
