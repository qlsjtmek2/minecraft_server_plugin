// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.Random;

public class BlockChest extends BlockContainer
{
    private final Random b;
    public final int a;
    
    protected BlockChest(final int n, final int a) {
        super(n, Material.WOOD);
        this.b = new Random();
        this.a = a;
        this.a(CreativeModeTab.c);
        this.a(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public int d() {
        return 22;
    }
    
    public void updateShape(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        if (blockAccess.getTypeId(n, n2, n3 - 1) == this.id) {
            this.a(0.0625f, 0.0f, 0.0f, 0.9375f, 0.875f, 0.9375f);
        }
        else if (blockAccess.getTypeId(n, n2, n3 + 1) == this.id) {
            this.a(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 1.0f);
        }
        else if (blockAccess.getTypeId(n - 1, n2, n3) == this.id) {
            this.a(0.0f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
        }
        else if (blockAccess.getTypeId(n + 1, n2, n3) == this.id) {
            this.a(0.0625f, 0.0f, 0.0625f, 1.0f, 0.875f, 0.9375f);
        }
        else {
            this.a(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
        }
    }
    
    public void onPlace(final World world, final int n, final int n2, final int n3) {
        super.onPlace(world, n, n2, n3);
        this.f_(world, n, n2, n3);
        final int typeId = world.getTypeId(n, n2, n3 - 1);
        final int typeId2 = world.getTypeId(n, n2, n3 + 1);
        final int typeId3 = world.getTypeId(n - 1, n2, n3);
        final int typeId4 = world.getTypeId(n + 1, n2, n3);
        if (typeId == this.id) {
            this.f_(world, n, n2, n3 - 1);
        }
        if (typeId2 == this.id) {
            this.f_(world, n, n2, n3 + 1);
        }
        if (typeId3 == this.id) {
            this.f_(world, n - 1, n2, n3);
        }
        if (typeId4 == this.id) {
            this.f_(world, n + 1, n2, n3);
        }
    }
    
    public void postPlace(final World world, final int n, final int n2, final int n3, final EntityLiving entityLiving, final ItemStack itemStack) {
        final int typeId = world.getTypeId(n, n2, n3 - 1);
        final int typeId2 = world.getTypeId(n, n2, n3 + 1);
        final int typeId3 = world.getTypeId(n - 1, n2, n3);
        final int typeId4 = world.getTypeId(n + 1, n2, n3);
        int l = 0;
        final int n4 = MathHelper.floor(entityLiving.yaw * 4.0f / 360.0f + 0.5) & 0x3;
        if (n4 == 0) {
            l = 2;
        }
        if (n4 == 1) {
            l = 5;
        }
        if (n4 == 2) {
            l = 3;
        }
        if (n4 == 3) {
            l = 4;
        }
        if (typeId != this.id && typeId2 != this.id && typeId3 != this.id && typeId4 != this.id) {
            world.setData(n, n2, n3, l, 3);
        }
        else {
            if ((typeId == this.id || typeId2 == this.id) && (l == 4 || l == 5)) {
                if (typeId == this.id) {
                    world.setData(n, n2, n3 - 1, l, 3);
                }
                else {
                    world.setData(n, n2, n3 + 1, l, 3);
                }
                world.setData(n, n2, n3, l, 3);
            }
            if ((typeId3 == this.id || typeId4 == this.id) && (l == 2 || l == 3)) {
                if (typeId3 == this.id) {
                    world.setData(n - 1, n2, n3, l, 3);
                }
                else {
                    world.setData(n + 1, n2, n3, l, 3);
                }
                world.setData(n, n2, n3, l, 3);
            }
        }
        if (itemStack.hasName()) {
            ((TileEntityChest)world.getTileEntity(n, n2, n3)).a(itemStack.getName());
        }
    }
    
    public void f_(final World world, final int i, final int j, final int k) {
        if (world.isStatic) {
            return;
        }
        final int typeId = world.getTypeId(i, j, k - 1);
        final int typeId2 = world.getTypeId(i, j, k + 1);
        final int typeId3 = world.getTypeId(i - 1, j, k);
        final int typeId4 = world.getTypeId(i + 1, j, k);
        int l;
        if (typeId == this.id || typeId2 == this.id) {
            final int typeId5 = world.getTypeId(i - 1, j, (typeId == this.id) ? (k - 1) : (k + 1));
            final int typeId6 = world.getTypeId(i + 1, j, (typeId == this.id) ? (k - 1) : (k + 1));
            l = 5;
            int n;
            if (typeId == this.id) {
                n = world.getData(i, j, k - 1);
            }
            else {
                n = world.getData(i, j, k + 1);
            }
            if (n == 4) {
                l = 4;
            }
            if ((Block.s[typeId3] || Block.s[typeId5]) && !Block.s[typeId4] && !Block.s[typeId6]) {
                l = 5;
            }
            if ((Block.s[typeId4] || Block.s[typeId6]) && !Block.s[typeId3] && !Block.s[typeId5]) {
                l = 4;
            }
        }
        else if (typeId3 == this.id || typeId4 == this.id) {
            final int typeId7 = world.getTypeId((typeId3 == this.id) ? (i - 1) : (i + 1), j, k - 1);
            final int typeId8 = world.getTypeId((typeId3 == this.id) ? (i - 1) : (i + 1), j, k + 1);
            l = 3;
            int n2;
            if (typeId3 == this.id) {
                n2 = world.getData(i - 1, j, k);
            }
            else {
                n2 = world.getData(i + 1, j, k);
            }
            if (n2 == 2) {
                l = 2;
            }
            if ((Block.s[typeId] || Block.s[typeId7]) && !Block.s[typeId2] && !Block.s[typeId8]) {
                l = 3;
            }
            if ((Block.s[typeId2] || Block.s[typeId8]) && !Block.s[typeId] && !Block.s[typeId7]) {
                l = 2;
            }
        }
        else {
            l = 3;
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
        }
        world.setData(i, j, k, l, 3);
    }
    
    public boolean canPlace(final World world, final int n, final int n2, final int n3) {
        int n4 = 0;
        if (world.getTypeId(n - 1, n2, n3) == this.id) {
            ++n4;
        }
        if (world.getTypeId(n + 1, n2, n3) == this.id) {
            ++n4;
        }
        if (world.getTypeId(n, n2, n3 - 1) == this.id) {
            ++n4;
        }
        if (world.getTypeId(n, n2, n3 + 1) == this.id) {
            ++n4;
        }
        return n4 <= 1 && !this.k(world, n - 1, n2, n3) && !this.k(world, n + 1, n2, n3) && !this.k(world, n, n2, n3 - 1) && !this.k(world, n, n2, n3 + 1);
    }
    
    private boolean k(final World world, final int i, final int j, final int k) {
        return world.getTypeId(i, j, k) == this.id && (world.getTypeId(i - 1, j, k) == this.id || world.getTypeId(i + 1, j, k) == this.id || world.getTypeId(i, j, k - 1) == this.id || world.getTypeId(i, j, k + 1) == this.id);
    }
    
    public void doPhysics(final World world, final int n, final int n2, final int n3, final int l) {
        super.doPhysics(world, n, n2, n3, l);
        final TileEntityChest tileEntityChest = (TileEntityChest)world.getTileEntity(n, n2, n3);
        if (tileEntityChest != null) {
            tileEntityChest.i();
        }
    }
    
    public void remove(final World world, final int n, final int n2, final int n3, final int l, final int n4) {
        final TileEntityChest tileEntityChest = (TileEntityChest)world.getTileEntity(n, n2, n3);
        if (tileEntityChest != null) {
            for (int i = 0; i < tileEntityChest.getSize(); ++i) {
                final ItemStack item = tileEntityChest.getItem(i);
                if (item != null) {
                    final float n5 = this.b.nextFloat() * 0.8f + 0.1f;
                    final float n6 = this.b.nextFloat() * 0.8f + 0.1f;
                    final float n7 = this.b.nextFloat() * 0.8f + 0.1f;
                    while (item.count > 0) {
                        int count = this.b.nextInt(21) + 10;
                        if (count > item.count) {
                            count = item.count;
                        }
                        final ItemStack itemStack = item;
                        itemStack.count -= count;
                        final EntityItem entity = new EntityItem(world, n + n5, n2 + n6, n3 + n7, new ItemStack(item.id, count, item.getData()));
                        final float n8 = 0.05f;
                        entity.motX = (float)this.b.nextGaussian() * n8;
                        entity.motY = (float)this.b.nextGaussian() * n8 + 0.2f;
                        entity.motZ = (float)this.b.nextGaussian() * n8;
                        if (item.hasTag()) {
                            entity.getItemStack().setTag((NBTTagCompound)item.getTag().clone());
                        }
                        world.addEntity(entity);
                    }
                }
            }
            world.m(n, n2, n3, l);
        }
        super.remove(world, n, n2, n3, l, n4);
    }
    
    public boolean interact(final World world, final int n, final int n2, final int n3, final EntityHuman entityHuman, final int n4, final float n5, final float n6, final float n7) {
        if (world.isStatic) {
            return true;
        }
        final IInventory g_ = this.g_(world, n, n2, n3);
        if (g_ != null) {
            entityHuman.openContainer(g_);
        }
        return true;
    }
    
    public IInventory g_(final World world, final int n, final int j, final int n2) {
        IInventory inventory = (TileEntityChest)world.getTileEntity(n, j, n2);
        if (inventory == null) {
            return null;
        }
        if (world.u(n, j + 1, n2)) {
            return null;
        }
        if (m(world, n, j, n2)) {
            return null;
        }
        if (world.getTypeId(n - 1, j, n2) == this.id && (world.u(n - 1, j + 1, n2) || m(world, n - 1, j, n2))) {
            return null;
        }
        if (world.getTypeId(n + 1, j, n2) == this.id && (world.u(n + 1, j + 1, n2) || m(world, n + 1, j, n2))) {
            return null;
        }
        if (world.getTypeId(n, j, n2 - 1) == this.id && (world.u(n, j + 1, n2 - 1) || m(world, n, j, n2 - 1))) {
            return null;
        }
        if (world.getTypeId(n, j, n2 + 1) == this.id && (world.u(n, j + 1, n2 + 1) || m(world, n, j, n2 + 1))) {
            return null;
        }
        if (world.getTypeId(n - 1, j, n2) == this.id) {
            inventory = new InventoryLargeChest("container.chestDouble", (IInventory)world.getTileEntity(n - 1, j, n2), inventory);
        }
        if (world.getTypeId(n + 1, j, n2) == this.id) {
            inventory = new InventoryLargeChest("container.chestDouble", inventory, (IInventory)world.getTileEntity(n + 1, j, n2));
        }
        if (world.getTypeId(n, j, n2 - 1) == this.id) {
            inventory = new InventoryLargeChest("container.chestDouble", (IInventory)world.getTileEntity(n, j, n2 - 1), inventory);
        }
        if (world.getTypeId(n, j, n2 + 1) == this.id) {
            inventory = new InventoryLargeChest("container.chestDouble", inventory, (IInventory)world.getTileEntity(n, j, n2 + 1));
        }
        return inventory;
    }
    
    public TileEntity b(final World world) {
        return new TileEntityChest();
    }
    
    public boolean isPowerSource() {
        return this.a == 1;
    }
    
    public int b(final IBlockAccess blockAccess, final int n, final int n2, final int n3, final int n4) {
        if (!this.isPowerSource()) {
            return 0;
        }
        return MathHelper.a(((TileEntityChest)blockAccess.getTileEntity(n, n2, n3)).h, 0, 15);
    }
    
    public int c(final IBlockAccess blockAccess, final int n, final int n2, final int n3, final int n4) {
        if (n4 == 1) {
            return this.b(blockAccess, n, n2, n3, n4);
        }
        return 0;
    }
    
    private static boolean m(final World world, final int n, final int n2, final int n3) {
        final Iterator<EntityOcelot> iterator = world.a(EntityOcelot.class, AxisAlignedBB.a().a(n, n2 + 1, n3, n + 1, n2 + 2, n3 + 1)).iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isSitting()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean q_() {
        return true;
    }
    
    public int b_(final World world, final int n, final int n2, final int n3, final int n4) {
        return Container.b(this.g_(world, n, n2, n3));
    }
}
