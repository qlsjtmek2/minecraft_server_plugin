// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import java.util.List;

public class BlockCauldron extends Block
{
    public BlockCauldron(final int i) {
        super(i, Material.ORE);
    }
    
    public void a(final World world, final int i, final int j, final int k, final AxisAlignedBB axisalignedbb, final List list, final Entity entity) {
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.3125f, 1.0f);
        super.a(world, i, j, k, axisalignedbb, list, entity);
        final float n = 0.125f;
        this.a(0.0f, 0.0f, 0.0f, n, 1.0f, 1.0f);
        super.a(world, i, j, k, axisalignedbb, list, entity);
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n);
        super.a(world, i, j, k, axisalignedbb, list, entity);
        this.a(1.0f - n, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.a(world, i, j, k, axisalignedbb, list, entity);
        this.a(0.0f, 0.0f, 1.0f - n, 1.0f, 1.0f, 1.0f);
        super.a(world, i, j, k, axisalignedbb, list, entity);
        this.g();
    }
    
    public void g() {
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public boolean c() {
        return false;
    }
    
    public int d() {
        return 24;
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean interact(final World world, final int n, final int n2, final int n3, final EntityHuman entityHuman, final int n4, final float n5, final float n6, final float n7) {
        if (world.isStatic) {
            return true;
        }
        final ItemStack itemInHand = entityHuman.inventory.getItemInHand();
        if (itemInHand == null) {
            return true;
        }
        final int data = world.getData(n, n2, n3);
        if (itemInHand.id == Item.WATER_BUCKET.id) {
            if (data < 3) {
                if (!entityHuman.abilities.canInstantlyBuild) {
                    entityHuman.inventory.setItem(entityHuman.inventory.itemInHandIndex, new ItemStack(Item.BUCKET));
                }
                world.setData(n, n2, n3, 3, 2);
            }
            return true;
        }
        if (itemInHand.id == Item.GLASS_BOTTLE.id) {
            if (data > 0) {
                final ItemStack itemStack = new ItemStack(Item.POTION, 1, 0);
                if (!entityHuman.inventory.pickup(itemStack)) {
                    world.addEntity(new EntityItem(world, n + 0.5, n2 + 1.5, n3 + 0.5, itemStack));
                }
                else if (entityHuman instanceof EntityPlayer) {
                    ((EntityPlayer)entityHuman).updateInventory(entityHuman.defaultContainer);
                }
                final ItemStack itemStack2 = itemInHand;
                --itemStack2.count;
                if (itemInHand.count <= 0) {
                    entityHuman.inventory.setItem(entityHuman.inventory.itemInHandIndex, null);
                }
                world.setData(n, n2, n3, data - 1, 2);
            }
        }
        else if (data > 0 && itemInHand.getItem() instanceof ItemArmor && ((ItemArmor)itemInHand.getItem()).d() == EnumArmorMaterial.CLOTH) {
            ((ItemArmor)itemInHand.getItem()).c(itemInHand);
            world.setData(n, n2, n3, data - 1, 2);
            return true;
        }
        return true;
    }
    
    public void g(final World world, final int n, final int n2, final int n3) {
        if (world.random.nextInt(20) != 1) {
            return;
        }
        final int data = world.getData(n, n2, n3);
        if (data < 3) {
            world.setData(n, n2, n3, data + 1, 2);
        }
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Item.CAULDRON.id;
    }
}
