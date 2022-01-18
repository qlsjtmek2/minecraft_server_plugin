// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockJukeBox extends BlockContainer
{
    protected BlockJukeBox(final int n) {
        super(n, Material.WOOD);
        this.a(CreativeModeTab.c);
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityHuman, final int n, final float n2, final float n3, final float n4) {
        if (world.getData(i, j, k) == 0) {
            return false;
        }
        this.dropRecord(world, i, j, k);
        return true;
    }
    
    public void a(final World world, final int n, final int n2, final int n3, final ItemStack itemStack) {
        if (world.isStatic) {
            return;
        }
        final TileEntityRecordPlayer tileEntityRecordPlayer = (TileEntityRecordPlayer)world.getTileEntity(n, n2, n3);
        if (tileEntityRecordPlayer == null) {
            return;
        }
        tileEntityRecordPlayer.setRecord(itemStack.cloneItemStack());
        world.setData(n, n2, n3, 1, 2);
    }
    
    public void dropRecord(final World world, final int n, final int n2, final int n3) {
        if (world.isStatic) {
            return;
        }
        final TileEntityRecordPlayer tileEntityRecordPlayer = (TileEntityRecordPlayer)world.getTileEntity(n, n2, n3);
        if (tileEntityRecordPlayer == null) {
            return;
        }
        final ItemStack record = tileEntityRecordPlayer.getRecord();
        if (record == null) {
            return;
        }
        world.triggerEffect(1005, n, n2, n3, 0);
        world.a((String)null, n, n2, n3);
        tileEntityRecordPlayer.setRecord(null);
        world.setData(n, n2, n3, 0, 2);
        final float n4 = 0.7f;
        final EntityItem entity = new EntityItem(world, n + (world.random.nextFloat() * n4 + (1.0f - n4) * 0.5), n2 + (world.random.nextFloat() * n4 + (1.0f - n4) * 0.2 + 0.6), n3 + (world.random.nextFloat() * n4 + (1.0f - n4) * 0.5), record.cloneItemStack());
        entity.pickupDelay = 10;
        world.addEntity(entity);
    }
    
    public void remove(final World world, final int n, final int n2, final int n3, final int n4, final int n5) {
        this.dropRecord(world, n, n2, n3);
        super.remove(world, n, n2, n3, n4, n5);
    }
    
    public void dropNaturally(final World world, final int i, final int j, final int k, final int l, final float f, final int n) {
        if (world.isStatic) {
            return;
        }
        super.dropNaturally(world, i, j, k, l, f, 0);
    }
    
    public TileEntity b(final World world) {
        return new TileEntityRecordPlayer();
    }
    
    public boolean q_() {
        return true;
    }
    
    public int b_(final World world, final int i, final int j, final int k, final int n) {
        final ItemStack record = ((TileEntityRecordPlayer)world.getTileEntity(i, j, k)).getRecord();
        return (record == null) ? 0 : (record.id + 1 - Item.RECORD_1.id);
    }
}
