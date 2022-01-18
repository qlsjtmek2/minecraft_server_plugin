// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.BlockChangeDelegate;
import java.util.Random;

public class WorldGenSwampTree extends WorldGenerator implements BlockSapling.TreeGenerator
{
    public boolean a(final World world, final Random random, final int i, final int j, final int k) {
        return this.generate((BlockChangeDelegate)world, random, i, j, k);
    }
    
    public boolean generate(final BlockChangeDelegate world, final Random random, final int i, int j, final int k) {
        final int l = random.nextInt(4) + 5;
        while (world.getTypeId(i, j - 1, k) != 0 && Block.byId[world.getTypeId(i, j - 1, k)].material == Material.WATER) {
            --j;
        }
        boolean flag = true;
        if (j < 1 || j + l + 1 > 128) {
            return false;
        }
        for (int i2 = j; i2 <= j + 1 + l; ++i2) {
            byte b0 = 1;
            if (i2 == j) {
                b0 = 0;
            }
            if (i2 >= j + 1 + l - 2) {
                b0 = 3;
            }
            for (int j2 = i - b0; j2 <= i + b0 && flag; ++j2) {
                for (int k2 = k - b0; k2 <= k + b0 && flag; ++k2) {
                    if (i2 >= 0 && i2 < 128) {
                        final int l2 = world.getTypeId(j2, i2, k2);
                        if (l2 != 0 && l2 != Block.LEAVES.id) {
                            if (l2 != Block.STATIONARY_WATER.id && l2 != Block.WATER.id) {
                                flag = false;
                            }
                            else if (i2 > j) {
                                flag = false;
                            }
                        }
                    }
                    else {
                        flag = false;
                    }
                }
            }
        }
        if (!flag) {
            return false;
        }
        int i2 = world.getTypeId(i, j - 1, k);
        if ((i2 == Block.GRASS.id || i2 == Block.DIRT.id) && j < 128 - l - 1) {
            this.setType(world, i, j - 1, k, Block.DIRT.id);
            for (int j3 = j - 3 + l; j3 <= j + l; ++j3) {
                final int j2 = j3 - (j + l);
                for (int k2 = 2 - j2 / 2, l2 = i - k2; l2 <= i + k2; ++l2) {
                    final int i3 = l2 - i;
                    for (int k3 = k - k2; k3 <= k + k2; ++k3) {
                        final int l3 = k3 - k;
                        if ((Math.abs(i3) != k2 || Math.abs(l3) != k2 || (random.nextInt(2) != 0 && j2 != 0)) && !Block.s[world.getTypeId(l2, j3, k3)]) {
                            this.setType(world, l2, j3, k3, Block.LEAVES.id);
                        }
                    }
                }
            }
            for (int j3 = 0; j3 < l; ++j3) {
                final int j2 = world.getTypeId(i, j + j3, k);
                if (j2 == 0 || j2 == Block.LEAVES.id || j2 == Block.WATER.id || j2 == Block.STATIONARY_WATER.id) {
                    this.setType(world, i, j + j3, k, Block.LOG.id);
                }
            }
            for (int j3 = j - 3 + l; j3 <= j + l; ++j3) {
                final int j2 = j3 - (j + l);
                for (int k2 = 2 - j2 / 2, l2 = i - k2; l2 <= i + k2; ++l2) {
                    for (int i3 = k - k2; i3 <= k + k2; ++i3) {
                        if (world.getTypeId(l2, j3, i3) == Block.LEAVES.id) {
                            if (random.nextInt(4) == 0 && world.getTypeId(l2 - 1, j3, i3) == 0) {
                                this.b(world, l2 - 1, j3, i3, 8);
                            }
                            if (random.nextInt(4) == 0 && world.getTypeId(l2 + 1, j3, i3) == 0) {
                                this.b(world, l2 + 1, j3, i3, 2);
                            }
                            if (random.nextInt(4) == 0 && world.getTypeId(l2, j3, i3 - 1) == 0) {
                                this.b(world, l2, j3, i3 - 1, 1);
                            }
                            if (random.nextInt(4) == 0 && world.getTypeId(l2, j3, i3 + 1) == 0) {
                                this.b(world, l2, j3, i3 + 1, 4);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private void b(final BlockChangeDelegate world, final int i, int j, final int k, final int l) {
        this.setTypeAndData(world, i, j, k, Block.VINE.id, l);
        int i2 = 4;
        while (true) {
            --j;
            if (world.getTypeId(i, j, k) != 0 || i2 <= 0) {
                break;
            }
            this.setTypeAndData(world, i, j, k, Block.VINE.id, l);
            --i2;
        }
    }
}
