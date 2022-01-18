// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockFurnace extends BlockContainer
{
    private final Random a;
    private final boolean b;
    private static boolean c;
    
    protected BlockFurnace(final int n, final boolean b) {
        super(n, Material.STONE);
        this.a = new Random();
        this.b = b;
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Block.FURNACE.id;
    }
    
    public void onPlace(final World world, final int n, final int n2, final int n3) {
        super.onPlace(world, n, n2, n3);
        this.k(world, n, n2, n3);
    }
    
    private void k(final World world, final int i, final int j, final int k) {
        if (world.isStatic) {
            return;
        }
        final int typeId = world.getTypeId(i, j, k - 1);
        final int typeId2 = world.getTypeId(i, j, k + 1);
        final int typeId3 = world.getTypeId(i - 1, j, k);
        final int typeId4 = world.getTypeId(i + 1, j, k);
        int l = 3;
        if (Block.s[typeId] && !Block.s[typeId2]) {
            l = 3;
        }
        if (Block.s[typeId2] && !Block.s[typeId]) {
            l = 2;
        }
        if (Block.s[typeId3] && !Block.s[typeId4]) {
            l = 5;
        }
        if (Block.s[typeId4] && !Block.s[typeId3]) {
            l = 4;
        }
        world.setData(i, j, k, l, 2);
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityHuman, final int n, final float n2, final float n3, final float n4) {
        if (world.isStatic) {
            return true;
        }
        final TileEntityFurnace tileentityfurnace = (TileEntityFurnace)world.getTileEntity(i, j, k);
        if (tileentityfurnace != null) {
            entityHuman.openFurnace(tileentityfurnace);
        }
        return true;
    }
    
    public static void a(final boolean b, final World world, final int n, final int n2, final int n3) {
        final int data = world.getData(n, n2, n3);
        final TileEntity tileEntity = world.getTileEntity(n, n2, n3);
        BlockFurnace.c = true;
        if (b) {
            world.setTypeIdUpdate(n, n2, n3, Block.BURNING_FURNACE.id);
        }
        else {
            world.setTypeIdUpdate(n, n2, n3, Block.FURNACE.id);
        }
        BlockFurnace.c = false;
        world.setData(n, n2, n3, data, 2);
        if (tileEntity != null) {
            tileEntity.s();
            world.setTileEntity(n, n2, n3, tileEntity);
        }
    }
    
    public TileEntity b(final World world) {
        return new TileEntityFurnace();
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final EntityLiving entityLiving, final ItemStack itemStack) {
        final int n = MathHelper.floor(entityLiving.yaw * 4.0f / 360.0f + 0.5) & 0x3;
        if (n == 0) {
            world.setData(i, j, k, 2, 2);
        }
        if (n == 1) {
            world.setData(i, j, k, 5, 2);
        }
        if (n == 2) {
            world.setData(i, j, k, 3, 2);
        }
        if (n == 3) {
            world.setData(i, j, k, 4, 2);
        }
        if (itemStack.hasName()) {
            ((TileEntityFurnace)world.getTileEntity(i, j, k)).a(itemStack.getName());
        }
    }
    
    public void remove(final World world, final int n, final int n2, final int n3, final int l, final int n4) {
        if (!BlockFurnace.c) {
            final TileEntityFurnace tileEntityFurnace = (TileEntityFurnace)world.getTileEntity(n, n2, n3);
            if (tileEntityFurnace != null) {
                for (int i = 0; i < tileEntityFurnace.getSize(); ++i) {
                    final ItemStack item = tileEntityFurnace.getItem(i);
                    if (item != null) {
                        final float n5 = this.a.nextFloat() * 0.8f + 0.1f;
                        final float n6 = this.a.nextFloat() * 0.8f + 0.1f;
                        final float n7 = this.a.nextFloat() * 0.8f + 0.1f;
                        while (item.count > 0) {
                            int count = this.a.nextInt(21) + 10;
                            if (count > item.count) {
                                count = item.count;
                            }
                            final ItemStack itemStack = item;
                            itemStack.count -= count;
                            final EntityItem entity = new EntityItem(world, n + n5, n2 + n6, n3 + n7, new ItemStack(item.id, count, item.getData()));
                            if (item.hasTag()) {
                                entity.getItemStack().setTag((NBTTagCompound)item.getTag().clone());
                            }
                            final float n8 = 0.05f;
                            entity.motX = (float)this.a.nextGaussian() * n8;
                            entity.motY = (float)this.a.nextGaussian() * n8 + 0.2f;
                            entity.motZ = (float)this.a.nextGaussian() * n8;
                            world.addEntity(entity);
                        }
                    }
                }
                world.m(n, n2, n3, l);
            }
        }
        super.remove(world, n, n2, n3, l, n4);
    }
    
    public boolean q_() {
        return true;
    }
    
    public int b_(final World world, final int i, final int j, final int k, final int n) {
        return Container.b((IInventory)world.getTileEntity(i, j, k));
    }
    
    static {
        BlockFurnace.c = false;
    }
}
