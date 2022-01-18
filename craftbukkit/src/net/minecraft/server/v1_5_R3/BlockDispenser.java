// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockDispenser extends BlockContainer
{
    public static final IRegistry a;
    protected Random b;
    public static boolean eventFired;
    
    protected BlockDispenser(final int i) {
        super(i, Material.STONE);
        this.b = new Random();
        this.a(CreativeModeTab.d);
    }
    
    public int a(final World world) {
        return 4;
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        super.onPlace(world, i, j, k);
        this.k(world, i, j, k);
    }
    
    private void k(final World world, final int i, final int j, final int k) {
        if (!world.isStatic) {
            final int l = world.getTypeId(i, j, k - 1);
            final int i2 = world.getTypeId(i, j, k + 1);
            final int j2 = world.getTypeId(i - 1, j, k);
            final int k2 = world.getTypeId(i + 1, j, k);
            byte b0 = 3;
            if (Block.s[l] && !Block.s[i2]) {
                b0 = 3;
            }
            if (Block.s[i2] && !Block.s[l]) {
                b0 = 2;
            }
            if (Block.s[j2] && !Block.s[k2]) {
                b0 = 5;
            }
            if (Block.s[k2] && !Block.s[j2]) {
                b0 = 4;
            }
            world.setData(i, j, k, b0, 2);
        }
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityhuman, final int l, final float f, final float f1, final float f2) {
        if (world.isStatic) {
            return true;
        }
        final TileEntityDispenser tileentitydispenser = (TileEntityDispenser)world.getTileEntity(i, j, k);
        if (tileentitydispenser != null) {
            entityhuman.openDispenser(tileentitydispenser);
        }
        return true;
    }
    
    public void dispense(final World world, final int i, final int j, final int k) {
        final SourceBlock sourceblock = new SourceBlock(world, i, j, k);
        final TileEntityDispenser tileentitydispenser = (TileEntityDispenser)sourceblock.getTileEntity();
        if (tileentitydispenser != null) {
            final int l = tileentitydispenser.j();
            if (l < 0) {
                world.triggerEffect(1001, i, j, k, 0);
            }
            else {
                final ItemStack itemstack = tileentitydispenser.getItem(l);
                final IDispenseBehavior idispensebehavior = this.a(itemstack);
                if (idispensebehavior != IDispenseBehavior.a) {
                    final ItemStack itemstack2 = idispensebehavior.a(sourceblock, itemstack);
                    BlockDispenser.eventFired = false;
                    tileentitydispenser.setItem(l, (itemstack2.count == 0) ? null : itemstack2);
                }
            }
        }
    }
    
    protected IDispenseBehavior a(final ItemStack itemstack) {
        return (IDispenseBehavior)BlockDispenser.a.a(itemstack.getItem());
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        final boolean flag = world.isBlockIndirectlyPowered(i, j, k) || world.isBlockIndirectlyPowered(i, j + 1, k);
        final int i2 = world.getData(i, j, k);
        final boolean flag2 = (i2 & 0x8) != 0x0;
        if (flag && !flag2) {
            world.a(i, j, k, this.id, this.a(world));
            world.setData(i, j, k, i2 | 0x8, 4);
        }
        else if (!flag && flag2) {
            world.setData(i, j, k, i2 & 0xFFFFFFF7, 4);
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (!world.isStatic) {
            this.dispense(world, i, j, k);
        }
    }
    
    public TileEntity b(final World world) {
        return new TileEntityDispenser();
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final EntityLiving entityliving, final ItemStack itemstack) {
        final int l = BlockPiston.a(world, i, j, k, entityliving);
        world.setData(i, j, k, l, 2);
        if (itemstack.hasName()) {
            ((TileEntityDispenser)world.getTileEntity(i, j, k)).a(itemstack.getName());
        }
    }
    
    public void remove(final World world, final int i, final int j, final int k, final int l, final int i1) {
        final TileEntityDispenser tileentitydispenser = (TileEntityDispenser)world.getTileEntity(i, j, k);
        if (tileentitydispenser != null) {
            for (int j2 = 0; j2 < tileentitydispenser.getSize(); ++j2) {
                final ItemStack itemstack = tileentitydispenser.getItem(j2);
                if (itemstack != null) {
                    final float f = this.b.nextFloat() * 0.8f + 0.1f;
                    final float f2 = this.b.nextFloat() * 0.8f + 0.1f;
                    final float f3 = this.b.nextFloat() * 0.8f + 0.1f;
                    while (itemstack.count > 0) {
                        int k2 = this.b.nextInt(21) + 10;
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
                        entityitem.motX = (float)this.b.nextGaussian() * f4;
                        entityitem.motY = (float)this.b.nextGaussian() * f4 + 0.2f;
                        entityitem.motZ = (float)this.b.nextGaussian() * f4;
                        world.addEntity(entityitem);
                    }
                }
            }
            world.m(i, j, k, l);
        }
        super.remove(world, i, j, k, l, i1);
    }
    
    public static IPosition a(final ISourceBlock isourceblock) {
        final EnumFacing enumfacing = j_(isourceblock.h());
        final double d0 = isourceblock.getX() + 0.7 * enumfacing.c();
        final double d2 = isourceblock.getY() + 0.7 * enumfacing.d();
        final double d3 = isourceblock.getZ() + 0.7 * enumfacing.e();
        return new Position(d0, d2, d3);
    }
    
    public static EnumFacing j_(final int i) {
        return EnumFacing.a(i & 0x7);
    }
    
    public boolean q_() {
        return true;
    }
    
    public int b_(final World world, final int i, final int j, final int k, final int l) {
        return Container.b((IInventory)world.getTileEntity(i, j, k));
    }
    
    static {
        a = new RegistryDefault(new DispenseBehaviorItem());
        BlockDispenser.eventFired = false;
    }
}
