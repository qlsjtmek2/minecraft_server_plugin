// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockBeacon extends BlockContainer
{
    public BlockBeacon(final int n) {
        super(n, Material.SHATTERABLE);
        this.c(3.0f);
        this.a(CreativeModeTab.f);
    }
    
    public TileEntity b(final World world) {
        return new TileEntityBeacon();
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityHuman, final int n, final float n2, final float n3, final float n4) {
        if (world.isStatic) {
            return true;
        }
        final TileEntityBeacon tileentitybeacon = (TileEntityBeacon)world.getTileEntity(i, j, k);
        if (tileentitybeacon != null) {
            entityHuman.openBeacon(tileentitybeacon);
        }
        return true;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public int d() {
        return 34;
    }
    
    public void postPlace(final World world, final int n, final int n2, final int n3, final EntityLiving entityliving, final ItemStack itemstack) {
        super.postPlace(world, n, n2, n3, entityliving, itemstack);
        if (itemstack.hasName()) {
            ((TileEntityBeacon)world.getTileEntity(n, n2, n3)).a(itemstack.getName());
        }
    }
}
