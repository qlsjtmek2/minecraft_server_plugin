// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockTorch extends Block
{
    protected BlockTorch(final int i) {
        super(i, Material.ORIENTABLE);
        this.b(true);
        this.a(CreativeModeTab.c);
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
        return 2;
    }
    
    private boolean m(final World world, final int n, final int n2, final int n3) {
        if (world.w(n, n2, n3)) {
            return true;
        }
        final int typeId = world.getTypeId(n, n2, n3);
        return typeId == Block.FENCE.id || typeId == Block.NETHER_FENCE.id || typeId == Block.GLASS.id || typeId == Block.COBBLE_WALL.id;
    }
    
    public boolean canPlace(final World world, final int n, final int n2, final int n3) {
        return world.c(n - 1, n2, n3, true) || world.c(n + 1, n2, n3, true) || world.c(n, n2, n3 - 1, true) || world.c(n, n2, n3 + 1, true) || this.m(world, n, n2 - 1, n3);
    }
    
    public int getPlacedData(final World world, final int n, final int n2, final int n3, final int n4, final float n5, final float n6, final float n7, final int n8) {
        int n9 = n8;
        if (n4 == 1 && this.m(world, n, n2 - 1, n3)) {
            n9 = 5;
        }
        if (n4 == 2 && world.c(n, n2, n3 + 1, true)) {
            n9 = 4;
        }
        if (n4 == 3 && world.c(n, n2, n3 - 1, true)) {
            n9 = 3;
        }
        if (n4 == 4 && world.c(n + 1, n2, n3, true)) {
            n9 = 2;
        }
        if (n4 == 5 && world.c(n - 1, n2, n3, true)) {
            n9 = 1;
        }
        return n9;
    }
    
    public void a(final World world, final int n, final int n2, final int n3, final Random random) {
        super.a(world, n, n2, n3, random);
        if (world.getData(n, n2, n3) == 0) {
            this.onPlace(world, n, n2, n3);
        }
    }
    
    public void onPlace(final World world, final int n, final int n2, final int n3) {
        if (world.getData(n, n2, n3) == 0) {
            if (world.c(n - 1, n2, n3, true)) {
                world.setData(n, n2, n3, 1, 2);
            }
            else if (world.c(n + 1, n2, n3, true)) {
                world.setData(n, n2, n3, 2, 2);
            }
            else if (world.c(n, n2, n3 - 1, true)) {
                world.setData(n, n2, n3, 3, 2);
            }
            else if (world.c(n, n2, n3 + 1, true)) {
                world.setData(n, n2, n3, 4, 2);
            }
            else if (this.m(world, n, n2 - 1, n3)) {
                world.setData(n, n2, n3, 5, 2);
            }
        }
        this.k(world, n, n2, n3);
    }
    
    public void doPhysics(final World world, final int n, final int n2, final int n3, final int n4) {
        this.d(world, n, n2, n3, n4);
    }
    
    protected boolean d(final World world, final int n, final int n2, final int n3, final int n4) {
        if (!this.k(world, n, n2, n3)) {
            return true;
        }
        final int data = world.getData(n, n2, n3);
        boolean b = false;
        if (!world.c(n - 1, n2, n3, true) && data == 1) {
            b = true;
        }
        if (!world.c(n + 1, n2, n3, true) && data == 2) {
            b = true;
        }
        if (!world.c(n, n2, n3 - 1, true) && data == 3) {
            b = true;
        }
        if (!world.c(n, n2, n3 + 1, true) && data == 4) {
            b = true;
        }
        if (!this.m(world, n, n2 - 1, n3) && data == 5) {
            b = true;
        }
        if (b) {
            this.c(world, n, n2, n3, world.getData(n, n2, n3), 0);
            world.setAir(n, n2, n3);
            return true;
        }
        return false;
    }
    
    protected boolean k(final World world, final int n, final int n2, final int n3) {
        if (!this.canPlace(world, n, n2, n3)) {
            if (world.getTypeId(n, n2, n3) == this.id) {
                this.c(world, n, n2, n3, world.getData(n, n2, n3), 0);
                world.setAir(n, n2, n3);
            }
            return false;
        }
        return true;
    }
    
    public MovingObjectPosition a(final World world, final int n, final int n2, final int n3, final Vec3D vec3d, final Vec3D vec3d2) {
        final int n4 = world.getData(n, n2, n3) & 0x7;
        final float n5 = 0.15f;
        if (n4 == 1) {
            this.a(0.0f, 0.2f, 0.5f - n5, n5 * 2.0f, 0.8f, 0.5f + n5);
        }
        else if (n4 == 2) {
            this.a(1.0f - n5 * 2.0f, 0.2f, 0.5f - n5, 1.0f, 0.8f, 0.5f + n5);
        }
        else if (n4 == 3) {
            this.a(0.5f - n5, 0.2f, 0.0f, 0.5f + n5, 0.8f, n5 * 2.0f);
        }
        else if (n4 == 4) {
            this.a(0.5f - n5, 0.2f, 1.0f - n5 * 2.0f, 0.5f + n5, 0.8f, 1.0f);
        }
        else {
            final float n6 = 0.1f;
            this.a(0.5f - n6, 0.0f, 0.5f - n6, 0.5f + n6, 0.6f, 0.5f + n6);
        }
        return super.a(world, n, n2, n3, vec3d, vec3d2);
    }
}
