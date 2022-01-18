// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockLadder extends Block
{
    protected BlockLadder(final int i) {
        super(i, Material.ORIENTABLE);
        this.a(CreativeModeTab.c);
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        this.updateShape(world, i, j, k);
        return super.b(world, i, j, k);
    }
    
    public void updateShape(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        this.c(blockAccess.getData(n, n2, n3));
    }
    
    public void c(final int n) {
        final float n2 = 0.125f;
        if (n == 2) {
            this.a(0.0f, 0.0f, 1.0f - n2, 1.0f, 1.0f, 1.0f);
        }
        if (n == 3) {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n2);
        }
        if (n == 4) {
            this.a(1.0f - n2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (n == 5) {
            this.a(0.0f, 0.0f, 0.0f, n2, 1.0f, 1.0f);
        }
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public int d() {
        return 8;
    }
    
    public boolean canPlace(final World world, final int n, final int n2, final int n3) {
        return world.u(n - 1, n2, n3) || world.u(n + 1, n2, n3) || world.u(n, n2, n3 - 1) || world.u(n, n2, n3 + 1);
    }
    
    public int getPlacedData(final World world, final int n, final int n2, final int n3, final int n4, final float n5, final float n6, final float n7, final int n8) {
        int n9 = n8;
        if ((n9 == 0 || n4 == 2) && world.u(n, n2, n3 + 1)) {
            n9 = 2;
        }
        if ((n9 == 0 || n4 == 3) && world.u(n, n2, n3 - 1)) {
            n9 = 3;
        }
        if ((n9 == 0 || n4 == 4) && world.u(n + 1, n2, n3)) {
            n9 = 4;
        }
        if ((n9 == 0 || n4 == 5) && world.u(n - 1, n2, n3)) {
            n9 = 5;
        }
        return n9;
    }
    
    public void doPhysics(final World world, final int n, final int n2, final int n3, final int l) {
        final int data = world.getData(n, n2, n3);
        boolean b = false;
        if (data == 2 && world.u(n, n2, n3 + 1)) {
            b = true;
        }
        if (data == 3 && world.u(n, n2, n3 - 1)) {
            b = true;
        }
        if (data == 4 && world.u(n + 1, n2, n3)) {
            b = true;
        }
        if (data == 5 && world.u(n - 1, n2, n3)) {
            b = true;
        }
        if (!b) {
            this.c(world, n, n2, n3, data, 0);
            world.setAir(n, n2, n3);
        }
        super.doPhysics(world, n, n2, n3, l);
    }
    
    public int a(final Random random) {
        return 1;
    }
}
