// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockCobbleWall extends Block
{
    public static final String[] a;
    
    public BlockCobbleWall(final int i, final Block block) {
        super(i, block.material);
        this.c(block.strength);
        this.b(block.durability / 3.0f);
        this.a(block.stepSound);
        this.a(CreativeModeTab.b);
    }
    
    public int d() {
        return 32;
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean b(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        return false;
    }
    
    public boolean c() {
        return false;
    }
    
    public void updateShape(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        final boolean d = this.d(blockAccess, n, n2, n3 - 1);
        final boolean d2 = this.d(blockAccess, n, n2, n3 + 1);
        final boolean d3 = this.d(blockAccess, n - 1, n2, n3);
        final boolean d4 = this.d(blockAccess, n + 1, n2, n3);
        float f = 0.25f;
        float f2 = 0.75f;
        float f3 = 0.25f;
        float f4 = 0.75f;
        float f5 = 1.0f;
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
        if (d && d2 && !d3 && !d4) {
            f5 = 0.8125f;
            f = 0.3125f;
            f2 = 0.6875f;
        }
        else if (!d && !d2 && d3 && d4) {
            f5 = 0.8125f;
            f3 = 0.3125f;
            f4 = 0.6875f;
        }
        this.a(f, 0.0f, f3, f2, f5, f4);
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        this.updateShape(world, i, j, k);
        this.maxY = 1.5;
        return super.b(world, i, j, k);
    }
    
    public boolean d(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        final int typeId = blockAccess.getTypeId(n, n2, n3);
        if (typeId == this.id || typeId == Block.FENCE_GATE.id) {
            return true;
        }
        final Block block = Block.byId[typeId];
        return block != null && block.material.k() && block.b() && block.material != Material.PUMPKIN;
    }
    
    public int getDropData(final int n) {
        return n;
    }
    
    static {
        a = new String[] { "normal", "mossy" };
    }
}
