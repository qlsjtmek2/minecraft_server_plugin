// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public abstract class BlockMinecartTrackAbstract extends Block
{
    protected final boolean a;
    
    public static final boolean d_(final World world, final int i, final int j, final int k) {
        return d_(world.getTypeId(i, j, k));
    }
    
    public static final boolean d_(final int n) {
        return n == Block.RAILS.id || n == Block.GOLDEN_RAIL.id || n == Block.DETECTOR_RAIL.id || n == Block.ACTIVATOR_RAIL.id;
    }
    
    protected BlockMinecartTrackAbstract(final int i, final boolean a) {
        super(i, Material.ORIENTABLE);
        this.a = a;
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        this.a(CreativeModeTab.e);
    }
    
    public boolean e() {
        return this.a;
    }
    
    public AxisAlignedBB b(final World world, final int n, final int n2, final int n3) {
        return null;
    }
    
    public boolean c() {
        return false;
    }
    
    public MovingObjectPosition a(final World world, final int i, final int j, final int k, final Vec3D vec3d, final Vec3D vec3d2) {
        this.updateShape(world, i, j, k);
        return super.a(world, i, j, k, vec3d, vec3d2);
    }
    
    public void updateShape(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        final int data = blockAccess.getData(n, n2, n3);
        if (data >= 2 && data <= 5) {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
        }
        else {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        }
    }
    
    public boolean b() {
        return false;
    }
    
    public int d() {
        return 9;
    }
    
    public int a(final Random random) {
        return 1;
    }
    
    public boolean canPlace(final World world, final int i, final int n, final int k) {
        return world.w(i, n - 1, k);
    }
    
    public void onPlace(final World world, final int n, final int n2, final int n3) {
        if (!world.isStatic) {
            this.a(world, n, n2, n3, true);
            if (this.a) {
                this.doPhysics(world, n, n2, n3, this.id);
            }
        }
    }
    
    public void doPhysics(final World world, final int i, final int n, final int k, final int n2) {
        if (world.isStatic) {
            return;
        }
        int data;
        final int n3 = data = world.getData(i, n, k);
        if (this.a) {
            data &= 0x7;
        }
        boolean b = false;
        if (!world.w(i, n - 1, k)) {
            b = true;
        }
        if (data == 2 && !world.w(i + 1, n, k)) {
            b = true;
        }
        if (data == 3 && !world.w(i - 1, n, k)) {
            b = true;
        }
        if (data == 4 && !world.w(i, n, k - 1)) {
            b = true;
        }
        if (data == 5 && !world.w(i, n, k + 1)) {
            b = true;
        }
        if (b) {
            this.c(world, i, n, k, world.getData(i, n, k), 0);
            world.setAir(i, n, k);
        }
        else {
            this.a(world, i, n, k, n3, data, n2);
        }
    }
    
    protected void a(final World world, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
    }
    
    protected void a(final World world, final int i, final int j, final int k, final boolean b) {
        if (world.isStatic) {
            return;
        }
        new MinecartTrackLogic(this, world, i, j, k).a(world.isBlockIndirectlyPowered(i, j, k), b);
    }
    
    public int h() {
        return 0;
    }
    
    public void remove(final World world, final int n, final int n2, final int n3, final int n4, final int i1) {
        int n5 = i1;
        if (this.a) {
            n5 &= 0x7;
        }
        super.remove(world, n, n2, n3, n4, i1);
        if (n5 == 2 || n5 == 3 || n5 == 4 || n5 == 5) {
            world.applyPhysics(n, n2 + 1, n3, n4);
        }
        if (this.a) {
            world.applyPhysics(n, n2, n3, n4);
            world.applyPhysics(n, n2 - 1, n3, n4);
        }
    }
}
