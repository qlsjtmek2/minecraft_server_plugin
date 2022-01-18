// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class BlockWaterLily extends BlockFlower
{
    protected BlockWaterLily(final int n) {
        super(n);
        final float n2 = 0.5f;
        this.a(0.5f - n2, 0.0f, 0.5f - n2, 0.5f + n2, 0.015625f, 0.5f + n2);
        this.a(CreativeModeTab.c);
    }
    
    public int d() {
        return 23;
    }
    
    public void a(final World world, final int i, final int j, final int k, final AxisAlignedBB axisalignedbb, final List list, final Entity entity) {
        if (entity == null || !(entity instanceof EntityBoat)) {
            super.a(world, i, j, k, axisalignedbb, list, entity);
        }
    }
    
    public AxisAlignedBB b(final World world, final int n, final int n2, final int n3) {
        return AxisAlignedBB.a().a(n + this.minX, n2 + this.minY, n3 + this.minZ, n + this.maxX, n2 + this.maxY, n3 + this.maxZ);
    }
    
    protected boolean f_(final int n) {
        return n == Block.STATIONARY_WATER.id;
    }
    
    public boolean f(final World world, final int n, final int n2, final int n3) {
        return n2 >= 0 && n2 < 256 && world.getMaterial(n, n2 - 1, n3) == Material.WATER && world.getData(n, n2 - 1, n3) == 0;
    }
}
