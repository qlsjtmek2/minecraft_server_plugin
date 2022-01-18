// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockSlowSand extends Block
{
    public BlockSlowSand(final int i) {
        super(i, Material.SAND);
        this.a(CreativeModeTab.b);
    }
    
    public AxisAlignedBB b(final World world, final int n, final int n2, final int n3) {
        return AxisAlignedBB.a().a(n, n2, n3, n + 1, n2 + 1 - 0.125f, n3 + 1);
    }
    
    public void a(final World world, final int n, final int n2, final int n3, final Entity entity) {
        entity.motX *= 0.4;
        entity.motZ *= 0.4;
    }
}
