// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockFenceGate extends BlockDirectional
{
    public BlockFenceGate(final int n) {
        super(n, Material.WOOD);
        this.a(CreativeModeTab.d);
    }
    
    public boolean canPlace(final World world, final int n, final int j, final int n2) {
        return world.getMaterial(n, j - 1, n2).isBuildable() && super.canPlace(world, n, j, n2);
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        final int data = world.getData(i, j, k);
        if (k_(data)) {
            return null;
        }
        if (data == 2 || data == 0) {
            return AxisAlignedBB.a().a(i, j, k + 0.375f, i + 1, j + 1.5f, k + 0.625f);
        }
        return AxisAlignedBB.a().a(i + 0.375f, j, k, i + 0.625f, j + 1.5f, k + 1);
    }
    
    public void updateShape(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        final int j = BlockDirectional.j(blockAccess.getData(n, n2, n3));
        if (j == 2 || j == 0) {
            this.a(0.0f, 0.0f, 0.375f, 1.0f, 1.0f, 0.625f);
        }
        else {
            this.a(0.375f, 0.0f, 0.0f, 0.625f, 1.0f, 1.0f);
        }
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean b(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        return k_(blockAccess.getData(n, n2, n3));
    }
    
    public int d() {
        return 21;
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final EntityLiving entityLiving, final ItemStack itemStack) {
        world.setData(i, j, k, (MathHelper.floor(entityLiving.yaw * 4.0f / 360.0f + 0.5) & 0x3) % 4, 2);
    }
    
    public boolean interact(final World world, final int n, final int n2, final int n3, final EntityHuman entityhuman, final int n4, final float n5, final float n6, final float n7) {
        int data = world.getData(n, n2, n3);
        if (k_(data)) {
            world.setData(n, n2, n3, data & 0xFFFFFFFB, 2);
        }
        else {
            final int n8 = (MathHelper.floor(entityhuman.yaw * 4.0f / 360.0f + 0.5) & 0x3) % 4;
            if (BlockDirectional.j(data) == (n8 + 2) % 4) {
                data = n8;
            }
            world.setData(n, n2, n3, data | 0x4, 2);
        }
        world.a(entityhuman, 1003, n, n2, n3, 0);
        return true;
    }
    
    public void doPhysics(final World world, final int n, final int n2, final int n3, final int n4) {
        if (world.isStatic) {
            return;
        }
        final int data = world.getData(n, n2, n3);
        final boolean blockIndirectlyPowered = world.isBlockIndirectlyPowered(n, n2, n3);
        if (blockIndirectlyPowered || (n4 > 0 && Block.byId[n4].isPowerSource())) {
            if (blockIndirectlyPowered && !k_(data)) {
                world.setData(n, n2, n3, data | 0x4, 2);
                world.a(null, 1003, n, n2, n3, 0);
            }
            else if (!blockIndirectlyPowered && k_(data)) {
                world.setData(n, n2, n3, data & 0xFFFFFFFB, 2);
                world.a(null, 1003, n, n2, n3, 0);
            }
        }
    }
    
    public static boolean k_(final int n) {
        return (n & 0x4) != 0x0;
    }
}
