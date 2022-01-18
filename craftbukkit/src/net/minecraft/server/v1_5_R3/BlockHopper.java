// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class BlockHopper extends BlockContainer
{
    private final Random a;
    
    public BlockHopper(final int i) {
        super(i, Material.ORE);
        this.a = new Random();
        this.a(CreativeModeTab.d);
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void a(final World world, final int i, final int j, final int k, final AxisAlignedBB axisalignedbb, final List list, final Entity entity) {
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
        super.a(world, i, j, k, axisalignedbb, list, entity);
        final float f = 0.125f;
        this.a(0.0f, 0.0f, 0.0f, f, 1.0f, 1.0f);
        super.a(world, i, j, k, axisalignedbb, list, entity);
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f);
        super.a(world, i, j, k, axisalignedbb, list, entity);
        this.a(1.0f - f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.a(world, i, j, k, axisalignedbb, list, entity);
        this.a(0.0f, 0.0f, 1.0f - f, 1.0f, 1.0f, 1.0f);
        super.a(world, i, j, k, axisalignedbb, list, entity);
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public int getPlacedData(final World world, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2, final int i1) {
        int j2 = Facing.OPPOSITE_FACING[l];
        if (j2 == 1) {
            j2 = 0;
        }
        return j2;
    }
    
    public TileEntity b(final World world) {
        return new TileEntityHopper();
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final EntityLiving entityliving, final ItemStack itemstack) {
        super.postPlace(world, i, j, k, entityliving, itemstack);
        if (itemstack.hasName()) {
            final TileEntityHopper tileentityhopper = d(world, i, j, k);
            tileentityhopper.a(itemstack.getName());
        }
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        super.onPlace(world, i, j, k);
        this.k(world, i, j, k);
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityhuman, final int l, final float f, final float f1, final float f2) {
        if (world.isStatic) {
            return true;
        }
        final TileEntityHopper tileentityhopper = d(world, i, j, k);
        if (tileentityhopper != null) {
            entityhuman.openHopper(tileentityhopper);
        }
        return true;
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        this.k(world, i, j, k);
    }
    
    private void k(final World world, final int i, final int j, final int k) {
        final int l = world.getData(i, j, k);
        final int i2 = c(l);
        final boolean flag = !world.isBlockIndirectlyPowered(i, j, k);
        final boolean flag2 = d(l);
        if (flag != flag2) {
            world.setData(i, j, k, i2 | (flag ? 0 : 8), 4);
        }
    }
    
    public void remove(final World world, final int i, final int j, final int k, final int l, final int i1) {
        final TileEntityHopper tileentityhopper = (TileEntityHopper)world.getTileEntity(i, j, k);
        if (tileentityhopper != null) {
            for (int j2 = 0; j2 < tileentityhopper.getSize(); ++j2) {
                final ItemStack itemstack = tileentityhopper.getItem(j2);
                if (itemstack != null) {
                    final float f = this.a.nextFloat() * 0.8f + 0.1f;
                    final float f2 = this.a.nextFloat() * 0.8f + 0.1f;
                    final float f3 = this.a.nextFloat() * 0.8f + 0.1f;
                    while (itemstack.count > 0) {
                        int k2 = this.a.nextInt(21) + 10;
                        if (k2 > itemstack.count) {
                            k2 = itemstack.count;
                        }
                        final ItemStack itemStack = itemstack;
                        itemStack.count -= k2;
                        final EntityItem entityitem = new EntityItem(world, i + f, j + f2, k + f3, new ItemStack(itemstack.id, k2, itemstack.getData()));
                        if (itemstack.hasTag()) {
                            entityitem.getItemStack().setTag((NBTTagCompound)itemstack.getTag().clone());
                        }
                        final float f4 = 0.05f;
                        entityitem.motX = (float)this.a.nextGaussian() * f4;
                        entityitem.motY = (float)this.a.nextGaussian() * f4 + 0.2f;
                        entityitem.motZ = (float)this.a.nextGaussian() * f4;
                        world.addEntity(entityitem);
                    }
                }
            }
            world.m(i, j, k, l);
        }
        super.remove(world, i, j, k, l, i1);
    }
    
    public int d() {
        return 38;
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean c() {
        return false;
    }
    
    public static int c(final int i) {
        return Math.min(i & 0x7, 5);
    }
    
    public static boolean d(final int i) {
        return (i & 0x8) != 0x8;
    }
    
    public boolean q_() {
        return true;
    }
    
    public int b_(final World world, final int i, final int j, final int k, final int l) {
        return Container.b(d(world, i, j, k));
    }
    
    public static TileEntityHopper d(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        return (TileEntityHopper)iblockaccess.getTileEntity(i, j, k);
    }
}
