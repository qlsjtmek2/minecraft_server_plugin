// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class BlockThinFence extends Block
{
    private final String a;
    private final boolean b;
    private final String c;
    
    protected BlockThinFence(final int i, final String c, final String a, final Material material, final boolean b) {
        super(i, material);
        this.a = a;
        this.b = b;
        this.c = c;
        this.a(CreativeModeTab.c);
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        if (!this.b) {
            return 0;
        }
        return super.getDropType(i, random, j);
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public int d() {
        return 18;
    }
    
    public void a(final World world, final int n, final int n2, final int n3, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        final boolean d = this.d(world.getTypeId(n, n2, n3 - 1));
        final boolean d2 = this.d(world.getTypeId(n, n2, n3 + 1));
        final boolean d3 = this.d(world.getTypeId(n - 1, n2, n3));
        final boolean d4 = this.d(world.getTypeId(n + 1, n2, n3));
        if ((d3 && d4) || (!d3 && !d4 && !d && !d2)) {
            this.a(0.0f, 0.0f, 0.4375f, 1.0f, 1.0f, 0.5625f);
            super.a(world, n, n2, n3, axisAlignedBB, list, entity);
        }
        else if (d3 && !d4) {
            this.a(0.0f, 0.0f, 0.4375f, 0.5f, 1.0f, 0.5625f);
            super.a(world, n, n2, n3, axisAlignedBB, list, entity);
        }
        else if (!d3 && d4) {
            this.a(0.5f, 0.0f, 0.4375f, 1.0f, 1.0f, 0.5625f);
            super.a(world, n, n2, n3, axisAlignedBB, list, entity);
        }
        if ((d && d2) || (!d3 && !d4 && !d && !d2)) {
            this.a(0.4375f, 0.0f, 0.0f, 0.5625f, 1.0f, 1.0f);
            super.a(world, n, n2, n3, axisAlignedBB, list, entity);
        }
        else if (d && !d2) {
            this.a(0.4375f, 0.0f, 0.0f, 0.5625f, 1.0f, 0.5f);
            super.a(world, n, n2, n3, axisAlignedBB, list, entity);
        }
        else if (!d && d2) {
            this.a(0.4375f, 0.0f, 0.5f, 0.5625f, 1.0f, 1.0f);
            super.a(world, n, n2, n3, axisAlignedBB, list, entity);
        }
    }
    
    public void g() {
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void updateShape(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        float f = 0.4375f;
        float f2 = 0.5625f;
        float f3 = 0.4375f;
        float f4 = 0.5625f;
        final boolean d = this.d(blockAccess.getTypeId(n, n2, n3 - 1));
        final boolean d2 = this.d(blockAccess.getTypeId(n, n2, n3 + 1));
        final boolean d3 = this.d(blockAccess.getTypeId(n - 1, n2, n3));
        final boolean d4 = this.d(blockAccess.getTypeId(n + 1, n2, n3));
        if ((d3 && d4) || (!d3 && !d4 && !d && !d2)) {
            f = 0.0f;
            f2 = 1.0f;
        }
        else if (d3 && !d4) {
            f = 0.0f;
        }
        else if (!d3 && d4) {
            f2 = 1.0f;
        }
        if ((d && d2) || (!d3 && !d4 && !d && !d2)) {
            f3 = 0.0f;
            f4 = 1.0f;
        }
        else if (d && !d2) {
            f3 = 0.0f;
        }
        else if (!d && d2) {
            f4 = 1.0f;
        }
        this.a(f, 0.0f, f3, f2, 1.0f, f4);
    }
    
    public final boolean d(final int n) {
        return Block.s[n] || n == this.id || n == Block.GLASS.id;
    }
    
    protected boolean r_() {
        return true;
    }
    
    protected ItemStack c_(final int k) {
        return new ItemStack(this.id, 1, k);
    }
}
