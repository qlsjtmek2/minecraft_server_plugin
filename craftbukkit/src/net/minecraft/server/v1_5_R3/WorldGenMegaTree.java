// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.BlockChangeDelegate;
import java.util.Random;

public class WorldGenMegaTree extends WorldGenerator implements BlockSapling.TreeGenerator
{
    private final int a;
    private final int b;
    private final int c;
    
    public WorldGenMegaTree(final boolean flag, final int i, final int j, final int k) {
        super(flag);
        this.a = i;
        this.b = j;
        this.c = k;
    }
    
    public boolean a(final World world, final Random random, final int i, final int j, final int k) {
        return this.generate((BlockChangeDelegate)world, random, i, j, k);
    }
    
    public boolean generate(final BlockChangeDelegate world, final Random random, final int i, final int j, final int k) {
        final int l = random.nextInt(3) + this.a;
        boolean flag = true;
        if (j < 1 || j + l + 1 > 256) {
            return false;
        }
        for (int i2 = j; i2 <= j + 1 + l; ++i2) {
            byte b0 = 2;
            if (i2 == j) {
                b0 = 1;
            }
            if (i2 >= j + 1 + l - 2) {
                b0 = 2;
            }
            for (int j2 = i - b0; j2 <= i + b0 && flag; ++j2) {
                for (int k2 = k - b0; k2 <= k + b0 && flag; ++k2) {
                    if (i2 >= 0 && i2 < 256) {
                        final int l2 = world.getTypeId(j2, i2, k2);
                        if (l2 != 0 && l2 != Block.LEAVES.id && l2 != Block.GRASS.id && l2 != Block.DIRT.id && l2 != Block.LOG.id && l2 != Block.SAPLING.id) {
                            flag = false;
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
        if ((i2 == Block.GRASS.id || i2 == Block.DIRT.id) && j < 256 - l - 1) {
            world.setTypeIdAndData(i, j - 1, k, Block.DIRT.id, 0);
            world.setTypeIdAndData(i + 1, j - 1, k, Block.DIRT.id, 0);
            world.setTypeIdAndData(i, j - 1, k + 1, Block.DIRT.id, 0);
            world.setTypeIdAndData(i + 1, j - 1, k + 1, Block.DIRT.id, 0);
            this.a(world, i, k, j + l, 2, random);
            for (int i3 = j + l - 2 - random.nextInt(4); i3 > j + l / 2; i3 -= 2 + random.nextInt(4)) {
                final float f = random.nextFloat() * 3.1415927f * 2.0f;
                int k2 = i + (int)(0.5f + MathHelper.cos(f) * 4.0f);
                int l2 = k + (int)(0.5f + MathHelper.sin(f) * 4.0f);
                this.a(world, k2, l2, i3, 0, random);
                for (int j3 = 0; j3 < 5; ++j3) {
                    k2 = i + (int)(1.5f + MathHelper.cos(f) * j3);
                    l2 = k + (int)(1.5f + MathHelper.sin(f) * j3);
                    this.setTypeAndData(world, k2, i3 - 3 + j3 / 2, l2, Block.LOG.id, this.b);
                }
            }
            for (int j2 = 0; j2 < l; ++j2) {
                int k2 = world.getTypeId(i, j + j2, k);
                if (k2 == 0 || k2 == Block.LEAVES.id) {
                    this.setTypeAndData(world, i, j + j2, k, Block.LOG.id, this.b);
                    if (j2 > 0) {
                        if (random.nextInt(3) > 0 && world.isEmpty(i - 1, j + j2, k)) {
                            this.setTypeAndData(world, i - 1, j + j2, k, Block.VINE.id, 8);
                        }
                        if (random.nextInt(3) > 0 && world.isEmpty(i, j + j2, k - 1)) {
                            this.setTypeAndData(world, i, j + j2, k - 1, Block.VINE.id, 1);
                        }
                    }
                }
                if (j2 < l - 1) {
                    k2 = world.getTypeId(i + 1, j + j2, k);
                    if (k2 == 0 || k2 == Block.LEAVES.id) {
                        this.setTypeAndData(world, i + 1, j + j2, k, Block.LOG.id, this.b);
                        if (j2 > 0) {
                            if (random.nextInt(3) > 0 && world.isEmpty(i + 2, j + j2, k)) {
                                this.setTypeAndData(world, i + 2, j + j2, k, Block.VINE.id, 2);
                            }
                            if (random.nextInt(3) > 0 && world.isEmpty(i + 1, j + j2, k - 1)) {
                                this.setTypeAndData(world, i + 1, j + j2, k - 1, Block.VINE.id, 1);
                            }
                        }
                    }
                    k2 = world.getTypeId(i + 1, j + j2, k + 1);
                    if (k2 == 0 || k2 == Block.LEAVES.id) {
                        this.setTypeAndData(world, i + 1, j + j2, k + 1, Block.LOG.id, this.b);
                        if (j2 > 0) {
                            if (random.nextInt(3) > 0 && world.isEmpty(i + 2, j + j2, k + 1)) {
                                this.setTypeAndData(world, i + 2, j + j2, k + 1, Block.VINE.id, 2);
                            }
                            if (random.nextInt(3) > 0 && world.isEmpty(i + 1, j + j2, k + 2)) {
                                this.setTypeAndData(world, i + 1, j + j2, k + 2, Block.VINE.id, 4);
                            }
                        }
                    }
                    k2 = world.getTypeId(i, j + j2, k + 1);
                    if (k2 == 0 || k2 == Block.LEAVES.id) {
                        this.setTypeAndData(world, i, j + j2, k + 1, Block.LOG.id, this.b);
                        if (j2 > 0) {
                            if (random.nextInt(3) > 0 && world.isEmpty(i - 1, j + j2, k + 1)) {
                                this.setTypeAndData(world, i - 1, j + j2, k + 1, Block.VINE.id, 8);
                            }
                            if (random.nextInt(3) > 0 && world.isEmpty(i, j + j2, k + 2)) {
                                this.setTypeAndData(world, i, j + j2, k + 2, Block.VINE.id, 4);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private void a(final BlockChangeDelegate world, final int i, final int j, final int k, final int l, final Random random) {
        final byte b0 = 2;
        for (int i2 = k - b0; i2 <= k; ++i2) {
            final int j2 = i2 - k;
            for (int k2 = l + 1 - j2, l2 = i - k2; l2 <= i + k2 + 1; ++l2) {
                final int i3 = l2 - i;
                for (int j3 = j - k2; j3 <= j + k2 + 1; ++j3) {
                    final int k3 = j3 - j;
                    if ((i3 >= 0 || k3 >= 0 || i3 * i3 + k3 * k3 <= k2 * k2) && ((i3 <= 0 && k3 <= 0) || i3 * i3 + k3 * k3 <= (k2 + 1) * (k2 + 1)) && (random.nextInt(4) != 0 || i3 * i3 + k3 * k3 <= (k2 - 1) * (k2 - 1))) {
                        final int l3 = world.getTypeId(l2, i2, j3);
                        if (l3 == 0 || l3 == Block.LEAVES.id) {
                            this.setTypeAndData(world, l2, i2, j3, Block.LEAVES.id, this.c);
                        }
                    }
                }
            }
        }
    }
}
