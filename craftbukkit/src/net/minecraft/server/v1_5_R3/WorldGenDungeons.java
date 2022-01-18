// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenDungeons extends WorldGenerator
{
    public boolean a(final World world, final Random random, final int n, final int j, final int n2) {
        final int n3 = 3;
        final int n4 = random.nextInt(2) + 2;
        final int n5 = random.nextInt(2) + 2;
        int n6 = 0;
        for (int i = n - n4 - 1; i <= n + n4 + 1; ++i) {
            for (int k = j - 1; k <= j + n3 + 1; ++k) {
                for (int l = n2 - n5 - 1; l <= n2 + n5 + 1; ++l) {
                    final Material material = world.getMaterial(i, k, l);
                    if (k == j - 1 && !material.isBuildable()) {
                        return false;
                    }
                    if (k == j + n3 + 1 && !material.isBuildable()) {
                        return false;
                    }
                    if ((i == n - n4 - 1 || i == n + n4 + 1 || l == n2 - n5 - 1 || l == n2 + n5 + 1) && k == j && world.isEmpty(i, k, l) && world.isEmpty(i, k + 1, l)) {
                        ++n6;
                    }
                }
            }
        }
        if (n6 < 1 || n6 > 5) {
            return false;
        }
        for (int n7 = n - n4 - 1; n7 <= n + n4 + 1; ++n7) {
            for (int m = j + n3; m >= j - 1; --m) {
                for (int n8 = n2 - n5 - 1; n8 <= n2 + n5 + 1; ++n8) {
                    if (n7 == n - n4 - 1 || m == j - 1 || n8 == n2 - n5 - 1 || n7 == n + n4 + 1 || m == j + n3 + 1 || n8 == n2 + n5 + 1) {
                        if (m >= 0 && !world.getMaterial(n7, m - 1, n8).isBuildable()) {
                            world.setAir(n7, m, n8);
                        }
                        else if (world.getMaterial(n7, m, n8).isBuildable()) {
                            if (m == j - 1 && random.nextInt(4) != 0) {
                                world.setTypeIdAndData(n7, m, n8, Block.MOSSY_COBBLESTONE.id, 0, 2);
                            }
                            else {
                                world.setTypeIdAndData(n7, m, n8, Block.COBBLESTONE.id, 0, 2);
                            }
                        }
                    }
                    else {
                        world.setAir(n7, m, n8);
                    }
                }
            }
        }
        for (int n9 = 0; n9 < 2; ++n9) {
            for (int n10 = 0; n10 < 3; ++n10) {
                final int i2 = n + random.nextInt(n4 * 2 + 1) - n4;
                final int k2 = n2 + random.nextInt(n5 * 2 + 1) - n5;
                if (world.isEmpty(i2, j, k2)) {
                    int n11 = 0;
                    if (world.getMaterial(i2 - 1, j, k2).isBuildable()) {
                        ++n11;
                    }
                    if (world.getMaterial(i2 + 1, j, k2).isBuildable()) {
                        ++n11;
                    }
                    if (world.getMaterial(i2, j, k2 - 1).isBuildable()) {
                        ++n11;
                    }
                    if (world.getMaterial(i2, j, k2 + 1).isBuildable()) {
                        ++n11;
                    }
                    if (n11 == 1) {
                        world.setTypeIdAndData(i2, j, k2, Block.CHEST.id, 0, 2);
                        final TileEntityChest tileEntityChest = (TileEntityChest)world.getTileEntity(i2, j, k2);
                        if (tileEntityChest != null) {
                            for (int n12 = 0; n12 < 8; ++n12) {
                                final ItemStack a = this.a(random);
                                if (a != null) {
                                    tileEntityChest.setItem(random.nextInt(tileEntityChest.getSize()), a);
                                }
                            }
                            break;
                        }
                        break;
                    }
                }
            }
        }
        world.setTypeIdAndData(n, j, n2, Block.MOB_SPAWNER.id, 0, 2);
        final TileEntityMobSpawner tileEntityMobSpawner = (TileEntityMobSpawner)world.getTileEntity(n, j, n2);
        if (tileEntityMobSpawner != null) {
            tileEntityMobSpawner.a().a(this.b(random));
        }
        else {
            System.err.println("Failed to fetch mob spawner entity at (" + n + ", " + j + ", " + n2 + ")");
        }
        return true;
    }
    
    private ItemStack a(final Random random) {
        final int nextInt = random.nextInt(12);
        if (nextInt == 0) {
            return new ItemStack(Item.SADDLE);
        }
        if (nextInt == 1) {
            return new ItemStack(Item.IRON_INGOT, random.nextInt(4) + 1);
        }
        if (nextInt == 2) {
            return new ItemStack(Item.BREAD);
        }
        if (nextInt == 3) {
            return new ItemStack(Item.WHEAT, random.nextInt(4) + 1);
        }
        if (nextInt == 4) {
            return new ItemStack(Item.SULPHUR, random.nextInt(4) + 1);
        }
        if (nextInt == 5) {
            return new ItemStack(Item.STRING, random.nextInt(4) + 1);
        }
        if (nextInt == 6) {
            return new ItemStack(Item.BUCKET);
        }
        if (nextInt == 7 && random.nextInt(100) == 0) {
            return new ItemStack(Item.GOLDEN_APPLE);
        }
        if (nextInt == 8 && random.nextInt(2) == 0) {
            return new ItemStack(Item.REDSTONE, random.nextInt(4) + 1);
        }
        if (nextInt == 9 && random.nextInt(10) == 0) {
            return new ItemStack(Item.byId[Item.RECORD_1.id + random.nextInt(2)]);
        }
        if (nextInt == 10) {
            return new ItemStack(Item.INK_SACK, 1, 3);
        }
        if (nextInt == 11) {
            return Item.ENCHANTED_BOOK.a(random);
        }
        return null;
    }
    
    private String b(final Random random) {
        final int nextInt = random.nextInt(4);
        if (nextInt == 0) {
            return "Skeleton";
        }
        if (nextInt == 1) {
            return "Zombie";
        }
        if (nextInt == 2) {
            return "Zombie";
        }
        if (nextInt == 3) {
            return "Spider";
        }
        return "";
    }
}
