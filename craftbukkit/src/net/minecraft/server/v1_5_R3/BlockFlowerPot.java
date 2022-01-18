// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockFlowerPot extends Block
{
    public BlockFlowerPot(final int i) {
        super(i, Material.ORIENTABLE);
        this.g();
    }
    
    public void g() {
        final float f4 = 0.375f;
        final float n = f4 / 2.0f;
        this.a(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, f4, 0.5f + n);
    }
    
    public boolean c() {
        return false;
    }
    
    public int d() {
        return 33;
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean interact(final World world, final int n, final int n2, final int n3, final EntityHuman entityHuman, final int n4, final float n5, final float n6, final float n7) {
        final ItemStack itemInHand = entityHuman.inventory.getItemInHand();
        if (itemInHand == null) {
            return false;
        }
        if (world.getData(n, n2, n3) != 0) {
            return false;
        }
        final int a = a(itemInHand);
        if (a > 0) {
            world.setData(n, n2, n3, a, 2);
            if (!entityHuman.abilities.canInstantlyBuild) {
                final ItemStack itemStack = itemInHand;
                if (--itemStack.count <= 0) {
                    entityHuman.inventory.setItem(entityHuman.inventory.itemInHandIndex, null);
                }
            }
            return true;
        }
        return false;
    }
    
    public int getDropData(final World world, final int i, final int j, final int k) {
        final ItemStack n_ = n_(world.getData(i, j, k));
        if (n_ == null) {
            return Item.FLOWER_POT.id;
        }
        return n_.getData();
    }
    
    public boolean canPlace(final World world, final int n, final int j, final int n2) {
        return super.canPlace(world, n, j, n2) && world.w(n, j - 1, n2);
    }
    
    public void doPhysics(final World world, final int n, final int j, final int n2, final int n3) {
        if (!world.w(n, j - 1, n2)) {
            this.c(world, n, j, n2, world.getData(n, j, n2), 0);
            world.setAir(n, j, n2);
        }
    }
    
    public void dropNaturally(final World world, final int n, final int n2, final int n3, final int l, final float f, final int i1) {
        super.dropNaturally(world, n, n2, n3, l, f, i1);
        if (l > 0) {
            final ItemStack n_ = n_(l);
            if (n_ != null) {
                this.b(world, n, n2, n3, n_);
            }
        }
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Item.FLOWER_POT.id;
    }
    
    public static ItemStack n_(final int n) {
        switch (n) {
            case 1: {
                return new ItemStack(Block.RED_ROSE);
            }
            case 2: {
                return new ItemStack(Block.YELLOW_FLOWER);
            }
            case 9: {
                return new ItemStack(Block.CACTUS);
            }
            case 8: {
                return new ItemStack(Block.BROWN_MUSHROOM);
            }
            case 7: {
                return new ItemStack(Block.RED_MUSHROOM);
            }
            case 10: {
                return new ItemStack(Block.DEAD_BUSH);
            }
            case 3: {
                return new ItemStack(Block.SAPLING, 1, 0);
            }
            case 5: {
                return new ItemStack(Block.SAPLING, 1, 2);
            }
            case 4: {
                return new ItemStack(Block.SAPLING, 1, 1);
            }
            case 6: {
                return new ItemStack(Block.SAPLING, 1, 3);
            }
            case 11: {
                return new ItemStack(Block.LONG_GRASS, 1, 2);
            }
            default: {
                return null;
            }
        }
    }
    
    public static int a(final ItemStack itemStack) {
        final int id = itemStack.getItem().id;
        if (id == Block.RED_ROSE.id) {
            return 1;
        }
        if (id == Block.YELLOW_FLOWER.id) {
            return 2;
        }
        if (id == Block.CACTUS.id) {
            return 9;
        }
        if (id == Block.BROWN_MUSHROOM.id) {
            return 8;
        }
        if (id == Block.RED_MUSHROOM.id) {
            return 7;
        }
        if (id == Block.DEAD_BUSH.id) {
            return 10;
        }
        if (id == Block.SAPLING.id) {
            switch (itemStack.getData()) {
                case 0: {
                    return 3;
                }
                case 2: {
                    return 5;
                }
                case 1: {
                    return 4;
                }
                case 3: {
                    return 6;
                }
            }
        }
        if (id == Block.LONG_GRASS.id) {
            switch (itemStack.getData()) {
                case 2: {
                    return 11;
                }
            }
        }
        return 0;
    }
}
