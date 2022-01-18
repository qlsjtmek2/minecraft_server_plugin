// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockCake extends Block
{
    protected BlockCake(final int i) {
        super(i, Material.CAKE);
        this.b(true);
    }
    
    public void updateShape(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        final int data = blockAccess.getData(n, n2, n3);
        final float f2 = 0.0625f;
        this.a((1 + data * 2) / 16.0f, 0.0f, f2, 1.0f - f2, 0.5f, 1.0f - f2);
    }
    
    public void g() {
        final float n = 0.0625f;
        this.a(n, 0.0f, n, 1.0f - n, 0.5f, 1.0f - n);
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        final int data = world.getData(i, j, k);
        final float n = 0.0625f;
        return AxisAlignedBB.a().a(i + (1 + data * 2) / 16.0f, j, k + n, i + 1 - n, j + 0.5f - n, k + 1 - n);
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean interact(final World world, final int n, final int n2, final int n3, final EntityHuman entityHuman, final int n4, final float n5, final float n6, final float n7) {
        this.b(world, n, n2, n3, entityHuman);
        return true;
    }
    
    public void attack(final World world, final int n, final int n2, final int n3, final EntityHuman entityHuman) {
        this.b(world, n, n2, n3, entityHuman);
    }
    
    private void b(final World world, final int i, final int j, final int k, final EntityHuman entityHuman) {
        if (entityHuman.i(false)) {
            entityHuman.getFoodData().eat(2, 0.1f);
            final int l = world.getData(i, j, k) + 1;
            if (l >= 6) {
                world.setAir(i, j, k);
            }
            else {
                world.setData(i, j, k, l, 2);
            }
        }
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k) {
        return super.canPlace(world, i, j, k) && this.f(world, i, j, k);
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int n) {
        if (!this.f(world, i, j, k)) {
            world.setAir(i, j, k);
        }
    }
    
    public boolean f(final World world, final int i, final int n, final int k) {
        return world.getMaterial(i, n - 1, k).isBuildable();
    }
    
    public int a(final Random random) {
        return 0;
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return 0;
    }
}
