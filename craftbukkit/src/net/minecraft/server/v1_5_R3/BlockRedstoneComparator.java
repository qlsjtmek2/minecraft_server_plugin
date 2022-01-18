// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockRedstoneComparator extends BlockDiodeAbstract implements IContainer
{
    public BlockRedstoneComparator(final int i, final boolean flag) {
        super(i, flag);
        this.isTileEntity = true;
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Item.REDSTONE_COMPARATOR.id;
    }
    
    protected int i_(final int n) {
        return 2;
    }
    
    protected BlockDiodeAbstract i() {
        return Block.REDSTONE_COMPARATOR_ON;
    }
    
    protected BlockDiodeAbstract j() {
        return Block.REDSTONE_COMPARATOR_OFF;
    }
    
    public int d() {
        return 37;
    }
    
    protected boolean c(final int n) {
        return this.a || (n & 0x8) != 0x0;
    }
    
    protected int d(final IBlockAccess blockAccess, final int n, final int n2, final int n3, final int n4) {
        return this.a_(blockAccess, n, n2, n3).a();
    }
    
    private int m(final World iblockaccess, final int i, final int j, final int k, final int l) {
        if (!this.d(l)) {
            return this.e(iblockaccess, i, j, k, l);
        }
        return Math.max(this.e(iblockaccess, i, j, k, l) - this.f((IBlockAccess)iblockaccess, i, j, k, l), 0);
    }
    
    public boolean d(final int n) {
        return (n & 0x4) == 0x4;
    }
    
    protected boolean d(final World iblockaccess, final int i, final int j, final int k, final int l) {
        final int e = this.e(iblockaccess, i, j, k, l);
        if (e >= 15) {
            return true;
        }
        if (e == 0) {
            return false;
        }
        final int f = this.f((IBlockAccess)iblockaccess, i, j, k, l);
        return f == 0 || e >= f;
    }
    
    protected int e(final World world, final int i, final int j, final int k, final int l) {
        int n = super.e(world, i, j, k, l);
        final int m = BlockDirectional.j(l);
        final int n2 = i + Direction.a[m];
        final int n3 = k + Direction.b[m];
        final int typeId = world.getTypeId(n2, j, n3);
        if (typeId > 0) {
            if (Block.byId[typeId].q_()) {
                n = Block.byId[typeId].b_(world, n2, j, n3, Direction.f[m]);
            }
            else if (n < 15 && Block.l(typeId)) {
                final int n4 = n2 + Direction.a[m];
                final int n5 = n3 + Direction.b[m];
                final int typeId2 = world.getTypeId(n4, j, n5);
                if (typeId2 > 0 && Block.byId[typeId2].q_()) {
                    n = Block.byId[typeId2].b_(world, n4, j, n5, Direction.f[m]);
                }
            }
        }
        return n;
    }
    
    public TileEntityComparator a_(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        return (TileEntityComparator)blockAccess.getTileEntity(n, n2, n3);
    }
    
    public boolean interact(final World world, final int n, final int n2, final int n3, final EntityHuman entityHuman, final int n4, final float n5, final float n6, final float n7) {
        final int data = world.getData(n, n2, n3);
        final boolean b = this.a | (data & 0x8) != 0x0;
        final boolean b2 = !this.d(data);
        final int n8 = (b2 ? 4 : 0) | (b ? 8 : 0);
        world.makeSound(n + 0.5, n2 + 0.5, n3 + 0.5, "random.click", 0.3f, b2 ? 0.55f : 0.5f);
        world.setData(n, n2, n3, n8 | (data & 0x3), 2);
        this.c(world, n, n2, n3, world.random);
        return true;
    }
    
    protected void f(final World world, final int i, final int j, final int k, final int n) {
        if (!world.a(i, j, k, this.id)) {
            final int data = world.getData(i, j, k);
            if (this.m(world, i, j, k, data) != this.a_(world, i, j, k).a() || this.c(data) != this.d(world, i, j, k, data)) {
                if (this.h(world, i, j, k, data)) {
                    world.a(i, j, k, this.id, this.i_(0), -1);
                }
                else {
                    world.a(i, j, k, this.id, this.i_(0), 0);
                }
            }
        }
    }
    
    private void c(final World world, final int n, final int n2, final int n3, final Random random) {
        final int data = world.getData(n, n2, n3);
        final int m = this.m(world, n, n2, n3, data);
        final int a = this.a_(world, n, n2, n3).a();
        this.a_(world, n, n2, n3).a(m);
        if (a != m || !this.d(data)) {
            final boolean d = this.d(world, n, n2, n3, data);
            final boolean b = this.a || (data & 0x8) != 0x0;
            if (b && !d) {
                world.setData(n, n2, n3, data & 0xFFFFFFF7, 2);
            }
            else if (!b && d) {
                world.setData(n, n2, n3, data | 0x8, 2);
            }
            this.h_(world, n, n2, n3);
        }
    }
    
    public void a(final World world, final int n, final int n2, final int n3, final Random random) {
        if (this.a) {
            world.setTypeIdAndData(n, n2, n3, this.j().id, world.getData(n, n2, n3) | 0x8, 4);
        }
        this.c(world, n, n2, n3, random);
    }
    
    public void onPlace(final World world, final int n, final int n2, final int n3) {
        super.onPlace(world, n, n2, n3);
        world.setTileEntity(n, n2, n3, this.b(world));
    }
    
    public void remove(final World world, final int i, final int j, final int k, final int l, final int i2) {
        super.remove(world, i, j, k, l, i2);
        world.s(i, j, k);
        this.h_(world, i, j, k);
    }
    
    public boolean b(final World world, final int n, final int n2, final int n3, final int n4, final int n5) {
        super.b(world, n, n2, n3, n4, n5);
        final TileEntity tileEntity = world.getTileEntity(n, n2, n3);
        return tileEntity != null && tileEntity.b(n4, n5);
    }
    
    public TileEntity b(final World world) {
        return new TileEntityComparator();
    }
}
