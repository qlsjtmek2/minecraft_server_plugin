// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockPistonMoving extends BlockContainer
{
    public BlockPistonMoving(final int n) {
        super(n, Material.PISTON);
        this.c(-1.0f);
    }
    
    public TileEntity b(final World world) {
        return null;
    }
    
    public void onPlace(final World world, final int n, final int n2, final int n3) {
    }
    
    public void remove(final World world, final int i, final int j, final int k, final int n, final int n2) {
        final TileEntity tileEntity = world.getTileEntity(i, j, k);
        if (tileEntity instanceof TileEntityPiston) {
            ((TileEntityPiston)tileEntity).f();
        }
        else {
            super.remove(world, i, j, k, n, n2);
        }
    }
    
    public boolean canPlace(final World world, final int n, final int n2, final int n3) {
        return false;
    }
    
    public boolean canPlace(final World world, final int n, final int n2, final int n3, final int n4) {
        return false;
    }
    
    public int d() {
        return -1;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean interact(final World world, final int n, final int n2, final int n3, final EntityHuman entityHuman, final int n4, final float n5, final float n6, final float n7) {
        if (!world.isStatic && world.getTileEntity(n, n2, n3) == null) {
            world.setAir(n, n2, n3);
            return true;
        }
        return false;
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return 0;
    }
    
    public void dropNaturally(final World world, final int i, final int j, final int k, final int n, final float n2, final int n3) {
        if (world.isStatic) {
            return;
        }
        final TileEntityPiston d = this.d(world, i, j, k);
        if (d == null) {
            return;
        }
        Block.byId[d.a()].c(world, i, j, k, d.p(), 0);
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int n) {
        if (world.isStatic || world.getTileEntity(i, j, k) == null) {}
    }
    
    public static TileEntity a(final int i, final int j, final int k, final boolean flag, final boolean flag2) {
        return new TileEntityPiston(i, j, k, flag, flag2);
    }
    
    public AxisAlignedBB b(final World world, final int n, final int n2, final int n3) {
        final TileEntityPiston d = this.d(world, n, n2, n3);
        if (d == null) {
            return null;
        }
        float a = d.a(0.0f);
        if (d.b()) {
            a = 1.0f - a;
        }
        return this.b(world, n, n2, n3, d.a(), a, d.c());
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        final TileEntityPiston d = this.d(iblockaccess, i, j, k);
        if (d != null) {
            final Block block = Block.byId[d.a()];
            if (block == null || block == this) {
                return;
            }
            block.updateShape(iblockaccess, i, j, k);
            float a = d.a(0.0f);
            if (d.b()) {
                a = 1.0f - a;
            }
            final int c = d.c();
            this.minX = block.u() - Facing.b[c] * a;
            this.minY = block.w() - Facing.c[c] * a;
            this.minZ = block.y() - Facing.d[c] * a;
            this.maxX = block.v() - Facing.b[c] * a;
            this.maxY = block.x() - Facing.c[c] * a;
            this.maxZ = block.z() - Facing.d[c] * a;
        }
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k, final int n, final float n2, final int n3) {
        if (n == 0 || n == this.id) {
            return null;
        }
        final AxisAlignedBB b = Block.byId[n].b(world, i, j, k);
        if (b == null) {
            return null;
        }
        if (Facing.b[n3] < 0) {
            final AxisAlignedBB axisAlignedBB = b;
            axisAlignedBB.a -= Facing.b[n3] * n2;
        }
        else {
            final AxisAlignedBB axisAlignedBB2 = b;
            axisAlignedBB2.d -= Facing.b[n3] * n2;
        }
        if (Facing.c[n3] < 0) {
            final AxisAlignedBB axisAlignedBB3 = b;
            axisAlignedBB3.b -= Facing.c[n3] * n2;
        }
        else {
            final AxisAlignedBB axisAlignedBB4 = b;
            axisAlignedBB4.e -= Facing.c[n3] * n2;
        }
        if (Facing.d[n3] < 0) {
            final AxisAlignedBB axisAlignedBB5 = b;
            axisAlignedBB5.c -= Facing.d[n3] * n2;
        }
        else {
            final AxisAlignedBB axisAlignedBB6 = b;
            axisAlignedBB6.f -= Facing.d[n3] * n2;
        }
        return b;
    }
    
    private TileEntityPiston d(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        final TileEntity tileEntity = blockAccess.getTileEntity(n, n2, n3);
        if (tileEntity instanceof TileEntityPiston) {
            return (TileEntityPiston)tileEntity;
        }
        return null;
    }
}
