// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.BlockChangeDelegate;
import java.util.Random;

public class WorldGenTrees extends WorldGenerator implements BlockSapling.TreeGenerator
{
    private final int a;
    private final boolean b;
    private final int c;
    private final int d;
    
    public WorldGenTrees(final boolean flag) {
        this(flag, 4, 0, 0, false);
    }
    
    public WorldGenTrees(final boolean flag, final int i, final int j, final int k, final boolean flag1) {
        super(flag);
        this.a = i;
        this.c = j;
        this.d = k;
        this.b = flag1;
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
            byte b0 = 1;
            if (i2 == j) {
                b0 = 0;
            }
            if (i2 >= j + 1 + l - 2) {
                b0 = 2;
            }
            for (int l2 = i - b0; l2 <= i + b0 && flag; ++l2) {
                for (int j2 = k - b0; j2 <= k + b0 && flag; ++j2) {
                    if (i2 >= 0 && i2 < 256) {
                        final int k2 = world.getTypeId(l2, i2, j2);
                        if (k2 != 0 && k2 != Block.LEAVES.id && k2 != Block.GRASS.id && k2 != Block.DIRT.id && k2 != Block.LOG.id) {
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
            this.setType(world, i, j - 1, k, Block.DIRT.id);
            final byte b0 = 3;
            final byte b2 = 0;
            for (int j2 = j - b0 + l; j2 <= j + l; ++j2) {
                final int k2 = j2 - (j + l);
                for (int i3 = b2 + 1 - k2 / 2, j3 = i - i3; j3 <= i + i3; ++j3) {
                    final int k3 = j3 - i;
                    for (int l3 = k - i3; l3 <= k + i3; ++l3) {
                        final int i4 = l3 - k;
                        if (Math.abs(k3) != i3 || Math.abs(i4) != i3 || (random.nextInt(2) != 0 && k2 != 0)) {
                            final int j4 = world.getTypeId(j3, j2, l3);
                            if (j4 == 0 || j4 == Block.LEAVES.id) {
                                this.setTypeAndData(world, j3, j2, l3, Block.LEAVES.id, this.d);
                            }
                        }
                    }
                }
            }
            for (int j2 = 0; j2 < l; ++j2) {
                final int k2 = world.getTypeId(i, j + j2, k);
                if (k2 == 0 || k2 == Block.LEAVES.id) {
                    this.setTypeAndData(world, i, j + j2, k, Block.LOG.id, this.c);
                    if (this.b && j2 > 0) {
                        if (random.nextInt(3) > 0 && world.isEmpty(i - 1, j + j2, k)) {
                            this.setTypeAndData(world, i - 1, j + j2, k, Block.VINE.id, 8);
                        }
                        if (random.nextInt(3) > 0 && world.isEmpty(i + 1, j + j2, k)) {
                            this.setTypeAndData(world, i + 1, j + j2, k, Block.VINE.id, 2);
                        }
                        if (random.nextInt(3) > 0 && world.isEmpty(i, j + j2, k - 1)) {
                            this.setTypeAndData(world, i, j + j2, k - 1, Block.VINE.id, 1);
                        }
                        if (random.nextInt(3) > 0 && world.isEmpty(i, j + j2, k + 1)) {
                            this.setTypeAndData(world, i, j + j2, k + 1, Block.VINE.id, 4);
                        }
                    }
                }
            }
            if (this.b) {
                for (int j2 = j - 3 + l; j2 <= j + l; ++j2) {
                    final int k2 = j2 - (j + l);
                    for (int i3 = 2 - k2 / 2, j3 = i - i3; j3 <= i + i3; ++j3) {
                        for (int k3 = k - i3; k3 <= k + i3; ++k3) {
                            if (world.getTypeId(j3, j2, k3) == Block.LEAVES.id) {
                                if (random.nextInt(4) == 0 && world.getTypeId(j3 - 1, j2, k3) == 0) {
                                    this.b(world, j3 - 1, j2, k3, 8);
                                }
                                if (random.nextInt(4) == 0 && world.getTypeId(j3 + 1, j2, k3) == 0) {
                                    this.b(world, j3 + 1, j2, k3, 2);
                                }
                                if (random.nextInt(4) == 0 && world.getTypeId(j3, j2, k3 - 1) == 0) {
                                    this.b(world, j3, j2, k3 - 1, 1);
                                }
                                if (random.nextInt(4) == 0 && world.getTypeId(j3, j2, k3 + 1) == 0) {
                                    this.b(world, j3, j2, k3 + 1, 4);
                                }
                            }
                        }
                    }
                }
                if (random.nextInt(5) == 0 && l > 5) {
                    for (int j2 = 0; j2 < 2; ++j2) {
                        for (int k2 = 0; k2 < 4; ++k2) {
                            if (random.nextInt(4 - j2) == 0) {
                                final int i3 = random.nextInt(3);
                                this.setTypeAndData(world, i + Direction.a[Direction.f[k2]], j + l - 5 + j2, k + Direction.b[Direction.f[k2]], Block.COCOA.id, i3 << 2 | k2);
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
