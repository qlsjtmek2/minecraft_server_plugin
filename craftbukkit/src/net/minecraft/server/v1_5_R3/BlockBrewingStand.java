// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class BlockBrewingStand extends BlockContainer
{
    private Random a;
    
    public BlockBrewingStand(final int n) {
        super(n, Material.ORE);
        this.a = new Random();
    }
    
    public boolean c() {
        return false;
    }
    
    public int d() {
        return 25;
    }
    
    public TileEntity b(final World world) {
        return new TileEntityBrewingStand();
    }
    
    public boolean b() {
        return false;
    }
    
    public void a(final World world, final int n, final int n2, final int n3, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        this.a(0.4375f, 0.0f, 0.4375f, 0.5625f, 0.875f, 0.5625f);
        super.a(world, n, n2, n3, axisAlignedBB, list, entity);
        this.g();
        super.a(world, n, n2, n3, axisAlignedBB, list, entity);
    }
    
    public void g() {
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityHuman, final int n, final float n2, final float n3, final float n4) {
        if (world.isStatic) {
            return true;
        }
        final TileEntityBrewingStand tileentitybrewingstand = (TileEntityBrewingStand)world.getTileEntity(i, j, k);
        if (tileentitybrewingstand != null) {
            entityHuman.openBrewingStand(tileentitybrewingstand);
        }
        return true;
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final EntityLiving entityLiving, final ItemStack itemStack) {
        if (itemStack.hasName()) {
            ((TileEntityBrewingStand)world.getTileEntity(i, j, k)).a(itemStack.getName());
        }
    }
    
    public void remove(final World world, final int i, final int j, final int k, final int n, final int n2) {
        final TileEntity tileEntity = world.getTileEntity(i, j, k);
        if (tileEntity instanceof TileEntityBrewingStand) {
            final TileEntityBrewingStand tileEntityBrewingStand = (TileEntityBrewingStand)tileEntity;
            for (int l = 0; l < tileEntityBrewingStand.getSize(); ++l) {
                final ItemStack item = tileEntityBrewingStand.getItem(l);
                if (item != null) {
                    final float n3 = this.a.nextFloat() * 0.8f + 0.1f;
                    final float n4 = this.a.nextFloat() * 0.8f + 0.1f;
                    final float n5 = this.a.nextFloat() * 0.8f + 0.1f;
                    while (item.count > 0) {
                        int count = this.a.nextInt(21) + 10;
                        if (count > item.count) {
                            count = item.count;
                        }
                        final ItemStack itemStack = item;
                        itemStack.count -= count;
                        final EntityItem entity = new EntityItem(world, i + n3, j + n4, k + n5, new ItemStack(item.id, count, item.getData()));
                        final float n6 = 0.05f;
                        entity.motX = (float)this.a.nextGaussian() * n6;
                        entity.motY = (float)this.a.nextGaussian() * n6 + 0.2f;
                        entity.motZ = (float)this.a.nextGaussian() * n6;
                        world.addEntity(entity);
                    }
                }
            }
        }
        super.remove(world, i, j, k, n, n2);
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Item.BREWING_STAND.id;
    }
    
    public boolean q_() {
        return true;
    }
    
    public int b_(final World world, final int i, final int j, final int k, final int n) {
        return Container.b((IInventory)world.getTileEntity(i, j, k));
    }
}
