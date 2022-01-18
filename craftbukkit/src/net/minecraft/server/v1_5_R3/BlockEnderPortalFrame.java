// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import java.util.List;

public class BlockEnderPortalFrame extends Block
{
    public BlockEnderPortalFrame(final int i) {
        super(i, Material.STONE);
    }
    
    public boolean c() {
        return false;
    }
    
    public int d() {
        return 26;
    }
    
    public void g() {
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
    }
    
    public void a(final World world, final int i, final int j, final int k, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
        super.a(world, i, j, k, axisAlignedBB, list, entity);
        if (d(world.getData(i, j, k))) {
            this.a(0.3125f, 0.8125f, 0.3125f, 0.6875f, 1.0f, 0.6875f);
            super.a(world, i, j, k, axisAlignedBB, list, entity);
        }
        this.g();
    }
    
    public static boolean d(final int n) {
        return (n & 0x4) != 0x0;
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return 0;
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final EntityLiving entityLiving, final ItemStack itemStack) {
        world.setData(i, j, k, ((MathHelper.floor(entityLiving.yaw * 4.0f / 360.0f + 0.5) & 0x3) + 2) % 4, 2);
    }
}
