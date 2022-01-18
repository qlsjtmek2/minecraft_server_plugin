// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public abstract class BlockContainer extends Block implements IContainer
{
    protected BlockContainer(final int i, final Material material) {
        super(i, material);
        this.isTileEntity = true;
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        super.onPlace(world, i, j, k);
    }
    
    public void remove(final World world, final int n, final int n2, final int n3, final int l, final int i1) {
        super.remove(world, n, n2, n3, l, i1);
        world.s(n, n2, n3);
    }
    
    public boolean b(final World world, final int n, final int n2, final int n3, final int n4, final int n5) {
        super.b(world, n, n2, n3, n4, n5);
        final TileEntity tileEntity = world.getTileEntity(n, n2, n3);
        return tileEntity != null && tileEntity.b(n4, n5);
    }
}
