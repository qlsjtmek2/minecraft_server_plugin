// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import java.util.List;

public abstract class BlockStepAbstract extends Block
{
    protected final boolean a;
    
    public BlockStepAbstract(final int i, final boolean a, final Material material) {
        super(i, material);
        this.a = a;
        if (a) {
            BlockStepAbstract.s[i] = true;
        }
        else {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
        this.k(255);
    }
    
    public void updateShape(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        if (this.a) {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else if ((blockAccess.getData(n, n2, n3) & 0x8) != 0x0) {
            this.a(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }
    
    public void g() {
        if (this.a) {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, final AxisAlignedBB axisalignedbb, final List list, final Entity entity) {
        this.updateShape(world, i, j, k);
        super.a(world, i, j, k, axisalignedbb, list, entity);
    }
    
    public boolean c() {
        return this.a;
    }
    
    public int getPlacedData(final World world, final int n, final int n2, final int n3, final int n4, final float n5, final float n6, final float n7, final int n8) {
        if (this.a) {
            return n8;
        }
        if (n4 == 0 || (n4 != 1 && n6 > 0.5)) {
            return n8 | 0x8;
        }
        return n8;
    }
    
    public int a(final Random random) {
        if (this.a) {
            return 2;
        }
        return 1;
    }
    
    public int getDropData(final int n) {
        return n & 0x7;
    }
    
    public boolean b() {
        return this.a;
    }
    
    public abstract String c(final int p0);
    
    public int getDropData(final World world, final int i, final int j, final int k) {
        return super.getDropData(world, i, j, k) & 0x7;
    }
}
