// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.BlockChangeDelegate;
import java.util.Random;

public class WorldGenTaiga2 extends WorldGenerator implements BlockSapling.TreeGenerator
{
    public WorldGenTaiga2(final boolean flag) {
        super(flag);
    }
    
    public boolean a(final World world, final Random random, final int i, final int j, final int k) {
        return this.generate((BlockChangeDelegate)world, random, i, j, k);
    }
    
    public boolean generate(final BlockChangeDelegate world, final Random random, final int i, final int j, final int k) {
        final int l = random.nextInt(4) + 6;
        final int i2 = 1 + random.nextInt(2);
        final int j2 = l - i2;
        final int k2 = 2 + random.nextInt(2);
        boolean flag = true;
        if (j < 1 || j + l + 1 > 256) {
            return false;
        }
        for (int l2 = j; l2 <= j + 1 + l && flag; ++l2) {
            final boolean flag2 = true;
            int k3;
            if (l2 - j < i2) {
                k3 = 0;
            }
            else {
                k3 = k2;
            }
            for (int i3 = i - k3; i3 <= i + k3 && flag; ++i3) {
                for (int l3 = k - k3; l3 <= k + k3 && flag; ++l3) {
                    if (l2 >= 0 && l2 < 256) {
                        final int j3 = world.getTypeId(i3, l2, l3);
                        if (j3 != 0 && j3 != Block.LEAVES.id) {
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
        int l2 = world.getTypeId(i, j - 1, k);
        if ((l2 == Block.GRASS.id || l2 == Block.DIRT.id) && j < 256 - l - 1) {
            this.setType(world, i, j - 1, k, Block.DIRT.id);
            int k3 = random.nextInt(2);
            int i3 = 1;
            byte b0 = 0;
            for (int j3 = 0; j3 <= j2; ++j3) {
                final int j4 = j + l - j3;
                for (int i4 = i - k3; i4 <= i + k3; ++i4) {
                    final int k4 = i4 - i;
                    for (int l4 = k - k3; l4 <= k + k3; ++l4) {
                        final int i5 = l4 - k;
                        if ((Math.abs(k4) != k3 || Math.abs(i5) != k3 || k3 <= 0) && !Block.s[world.getTypeId(i4, j4, l4)]) {
                            this.setTypeAndData(world, i4, j4, l4, Block.LEAVES.id, 1);
                        }
                    }
                }
                if (k3 >= i3) {
                    k3 = b0;
                    b0 = 1;
                    if (++i3 > k2) {
                        i3 = k2;
                    }
                }
                else {
                    ++k3;
                }
            }
            int j3;
            for (j3 = random.nextInt(3), int j4 = 0; j4 < l - j3; ++j4) {
                final int i4 = world.getTypeId(i, j + j4, k);
                if (i4 == 0 || i4 == Block.LEAVES.id) {
                    this.setTypeAndData(world, i, j + j4, k, Block.LOG.id, 1);
                }
            }
            return true;
        }
        return false;
    }
}
