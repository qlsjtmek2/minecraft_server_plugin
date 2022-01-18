// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockFlower extends Block
{
    protected BlockFlower(final int i, final Material material) {
        super(i, material);
        this.b(true);
        final float n = 0.2f;
        this.a(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, n * 3.0f, 0.5f + n);
        this.a(CreativeModeTab.c);
    }
    
    protected BlockFlower(final int n) {
        this(n, Material.PLANT);
    }
    
    public boolean canPlace(final World world, final int n, final int j, final int n2) {
        return super.canPlace(world, n, j, n2) && this.f_(world.getTypeId(n, j - 1, n2));
    }
    
    protected boolean f_(final int n) {
        return n == Block.GRASS.id || n == Block.DIRT.id || n == Block.SOIL.id;
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        super.doPhysics(world, i, j, k, l);
        this.e(world, i, j, k);
    }
    
    public void a(final World world, final int n, final int n2, final int n3, final Random random) {
        this.e(world, n, n2, n3);
    }
    
    protected final void e(final World world, final int i, final int j, final int k) {
        if (!this.f(world, i, j, k)) {
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setAir(i, j, k);
        }
    }
    
    public boolean f(final World world, final int i, final int n, final int k) {
        return (world.m(i, n, k) >= 8 || world.l(i, n, k)) && this.f_(world.getTypeId(i, n - 1, k));
    }
    
    public AxisAlignedBB b(final World world, final int n, final int n2, final int n3) {
        return null;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public int d() {
        return 1;
    }
}
