// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class BlockPistonExtension extends Block
{
    private IIcon a;
    
    public BlockPistonExtension(final int i) {
        super(i, Material.PISTON);
        this.a = null;
        this.a(BlockPistonExtension.j);
        this.c(0.5f);
    }
    
    public void remove(final World world, int i, int j, int k, final int l, int i1) {
        super.remove(world, i, j, k, l, i1);
        if ((i1 & 0x7) >= Facing.OPPOSITE_FACING.length) {
            return;
        }
        final int j2 = Facing.OPPOSITE_FACING[d(i1)];
        i += Facing.b[j2];
        j += Facing.c[j2];
        k += Facing.d[j2];
        final int k2 = world.getTypeId(i, j, k);
        if (k2 == Block.PISTON.id || k2 == Block.PISTON_STICKY.id) {
            i1 = world.getData(i, j, k);
            if (BlockPiston.e(i1)) {
                Block.byId[k2].c(world, i, j, k, i1, 0);
                world.setAir(i, j, k);
            }
        }
    }
    
    public int d() {
        return 17;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k) {
        return false;
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k, final int l) {
        return false;
    }
    
    public int a(final Random random) {
        return 0;
    }
    
    public void a(final World world, final int i, final int j, final int k, final AxisAlignedBB axisalignedbb, final List list, final Entity entity) {
        final int l = world.getData(i, j, k);
        switch (d(l)) {
            case 0: {
                this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
                super.a(world, i, j, k, axisalignedbb, list, entity);
                this.a(0.375f, 0.25f, 0.375f, 0.625f, 1.0f, 0.625f);
                super.a(world, i, j, k, axisalignedbb, list, entity);
                break;
            }
            case 1: {
                this.a(0.0f, 0.75f, 0.0f, 1.0f, 1.0f, 1.0f);
                super.a(world, i, j, k, axisalignedbb, list, entity);
                this.a(0.375f, 0.0f, 0.375f, 0.625f, 0.75f, 0.625f);
                super.a(world, i, j, k, axisalignedbb, list, entity);
                break;
            }
            case 2: {
                this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.25f);
                super.a(world, i, j, k, axisalignedbb, list, entity);
                this.a(0.25f, 0.375f, 0.25f, 0.75f, 0.625f, 1.0f);
                super.a(world, i, j, k, axisalignedbb, list, entity);
                break;
            }
            case 3: {
                this.a(0.0f, 0.0f, 0.75f, 1.0f, 1.0f, 1.0f);
                super.a(world, i, j, k, axisalignedbb, list, entity);
                this.a(0.25f, 0.375f, 0.0f, 0.75f, 0.625f, 0.75f);
                super.a(world, i, j, k, axisalignedbb, list, entity);
                break;
            }
            case 4: {
                this.a(0.0f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f);
                super.a(world, i, j, k, axisalignedbb, list, entity);
                this.a(0.375f, 0.25f, 0.25f, 0.625f, 0.75f, 1.0f);
                super.a(world, i, j, k, axisalignedbb, list, entity);
                break;
            }
            case 5: {
                this.a(0.75f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                super.a(world, i, j, k, axisalignedbb, list, entity);
                this.a(0.0f, 0.375f, 0.25f, 0.75f, 0.625f, 0.75f);
                super.a(world, i, j, k, axisalignedbb, list, entity);
                break;
            }
        }
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        final int l = iblockaccess.getData(i, j, k);
        switch (d(l)) {
            case 0: {
                this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
                break;
            }
            case 1: {
                this.a(0.0f, 0.75f, 0.0f, 1.0f, 1.0f, 1.0f);
                break;
            }
            case 2: {
                this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.25f);
                break;
            }
            case 3: {
                this.a(0.0f, 0.0f, 0.75f, 1.0f, 1.0f, 1.0f);
                break;
            }
            case 4: {
                this.a(0.0f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f);
                break;
            }
            case 5: {
                this.a(0.75f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                break;
            }
        }
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        final int i2 = d(world.getData(i, j, k));
        if ((i2 & 0x7) >= Facing.OPPOSITE_FACING.length) {
            return;
        }
        final int j2 = world.getTypeId(i - Facing.b[i2], j - Facing.c[i2], k - Facing.d[i2]);
        if (j2 != Block.PISTON.id && j2 != Block.PISTON_STICKY.id) {
            world.setAir(i, j, k);
        }
        else {
            Block.byId[j2].doPhysics(world, i - Facing.b[i2], j - Facing.c[i2], k - Facing.d[i2], l);
        }
    }
    
    public static int d(final int i) {
        return i & 0x7;
    }
}
