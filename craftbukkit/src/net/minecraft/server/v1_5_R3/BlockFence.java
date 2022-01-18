// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class BlockFence extends Block
{
    private final String a;
    
    public BlockFence(final int i, final String a, final Material material) {
        super(i, material);
        this.a = a;
        this.a(CreativeModeTab.c);
    }
    
    public void a(final World world, final int n, final int n2, final int n3, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        final boolean d = this.d(world, n, n2, n3 - 1);
        final boolean d2 = this.d(world, n, n2, n3 + 1);
        final boolean d3 = this.d(world, n - 1, n2, n3);
        final boolean d4 = this.d(world, n + 1, n2, n3);
        float f = 0.375f;
        float f2 = 0.625f;
        float f3 = 0.375f;
        float f4 = 0.625f;
        if (d) {
            f3 = 0.0f;
        }
        if (d2) {
            f4 = 1.0f;
        }
        if (d || d2) {
            this.a(f, 0.0f, f3, f2, 1.5f, f4);
            super.a(world, n, n2, n3, axisAlignedBB, list, entity);
        }
        float n4 = 0.375f;
        float n5 = 0.625f;
        if (d3) {
            f = 0.0f;
        }
        if (d4) {
            f2 = 1.0f;
        }
        if (d3 || d4 || (!d && !d2)) {
            this.a(f, 0.0f, n4, f2, 1.5f, n5);
            super.a(world, n, n2, n3, axisAlignedBB, list, entity);
        }
        if (d) {
            n4 = 0.0f;
        }
        if (d2) {
            n5 = 1.0f;
        }
        this.a(f, 0.0f, n4, f2, 1.0f, n5);
    }
    
    public void updateShape(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        final boolean d = this.d(blockAccess, n, n2, n3 - 1);
        final boolean d2 = this.d(blockAccess, n, n2, n3 + 1);
        final boolean d3 = this.d(blockAccess, n - 1, n2, n3);
        final boolean d4 = this.d(blockAccess, n + 1, n2, n3);
        float f = 0.375f;
        float f2 = 0.625f;
        float f3 = 0.375f;
        float f4 = 0.625f;
        if (d) {
            f3 = 0.0f;
        }
        if (d2) {
            f4 = 1.0f;
        }
        if (d3) {
            f = 0.0f;
        }
        if (d4) {
            f2 = 1.0f;
        }
        this.a(f, 0.0f, f3, f2, 1.0f, f4);
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean b(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        return false;
    }
    
    public int d() {
        return 11;
    }
    
    public boolean d(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        final int typeId = blockAccess.getTypeId(n, n2, n3);
        if (typeId == this.id || typeId == Block.FENCE_GATE.id) {
            return true;
        }
        final Block block = Block.byId[typeId];
        return block != null && block.material.k() && block.b() && block.material != Material.PUMPKIN;
    }
    
    public static boolean l_(final int n) {
        return n == Block.FENCE.id || n == Block.NETHER_FENCE.id;
    }
}
