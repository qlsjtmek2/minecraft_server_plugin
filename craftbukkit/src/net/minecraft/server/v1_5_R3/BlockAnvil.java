// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockAnvil extends BlockSand
{
    public static final String[] a;
    private static final String[] d;
    public int b;
    
    protected BlockAnvil(final int n) {
        super(n, Material.HEAVY);
        this.k(this.b = 0);
        this.a(CreativeModeTab.c);
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean c() {
        return false;
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final EntityLiving entityLiving, final ItemStack itemStack) {
        int n = MathHelper.floor(entityLiving.yaw * 4.0f / 360.0f + 0.5) & 0x3;
        final int n2 = world.getData(i, j, k) >> 2;
        final int n3 = ++n % 4;
        if (n3 == 0) {
            world.setData(i, j, k, 0x2 | n2 << 2, 2);
        }
        if (n3 == 1) {
            world.setData(i, j, k, 0x3 | n2 << 2, 2);
        }
        if (n3 == 2) {
            world.setData(i, j, k, 0x0 | n2 << 2, 2);
        }
        if (n3 == 3) {
            world.setData(i, j, k, 0x1 | n2 << 2, 2);
        }
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityHuman, final int n, final float n2, final float n3, final float n4) {
        if (world.isStatic) {
            return true;
        }
        entityHuman.openAnvil(i, j, k);
        return true;
    }
    
    public int d() {
        return 35;
    }
    
    public int getDropData(final int n) {
        return n >> 2;
    }
    
    public void updateShape(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        final int n4 = blockAccess.getData(n, n2, n3) & 0x3;
        if (n4 == 3 || n4 == 1) {
            this.a(0.0f, 0.0f, 0.125f, 1.0f, 1.0f, 0.875f);
        }
        else {
            this.a(0.125f, 0.0f, 0.0f, 0.875f, 1.0f, 1.0f);
        }
    }
    
    protected void a(final EntityFallingBlock entityFallingBlock) {
        entityFallingBlock.a(true);
    }
    
    public void a_(final World world, final int j, final int k, final int l, final int n) {
        world.triggerEffect(1022, j, k, l, 0);
    }
    
    static {
        a = new String[] { "intact", "slightlyDamaged", "veryDamaged" };
        d = new String[] { "anvil_top", "anvil_top_damaged_1", "anvil_top_damaged_2" };
    }
}
