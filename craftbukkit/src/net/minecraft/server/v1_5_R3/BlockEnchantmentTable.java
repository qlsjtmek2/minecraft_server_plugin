// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockEnchantmentTable extends BlockContainer
{
    protected BlockEnchantmentTable(final int n) {
        super(n, Material.STONE);
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
        this.k(0);
        this.a(CreativeModeTab.c);
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean c() {
        return false;
    }
    
    public TileEntity b(final World world) {
        return new TileEntityEnchantTable();
    }
    
    public boolean interact(final World world, final int n, final int n2, final int n3, final EntityHuman entityHuman, final int n4, final float n5, final float n6, final float n7) {
        if (world.isStatic) {
            return true;
        }
        final TileEntityEnchantTable tileEntityEnchantTable = (TileEntityEnchantTable)world.getTileEntity(n, n2, n3);
        entityHuman.startEnchanting(n, n2, n3, tileEntityEnchantTable.b() ? tileEntityEnchantTable.a() : null);
        return true;
    }
    
    public void postPlace(final World world, final int n, final int n2, final int n3, final EntityLiving entityliving, final ItemStack itemstack) {
        super.postPlace(world, n, n2, n3, entityliving, itemstack);
        if (itemstack.hasName()) {
            ((TileEntityEnchantTable)world.getTileEntity(n, n2, n3)).a(itemstack.getName());
        }
    }
}
