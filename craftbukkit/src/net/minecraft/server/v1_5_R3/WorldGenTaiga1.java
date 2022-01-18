// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.BlockChangeDelegate;
import java.util.Random;

public class WorldGenTaiga1 extends WorldGenerator implements BlockSapling.TreeGenerator
{
    public boolean a(final World world, final Random random, final int i, final int j, final int k) {
        return this.generate((BlockChangeDelegate)world, random, i, j, k);
    }
    
    public boolean generate(final BlockChangeDelegate world, final Random random, final int i, final int j, final int k) {
        final int l = random.nextInt(5) + 7;
        final int i2 = l - random.nextInt(2) - 3;
        final int j2 = l - i2;
        final int k2 = 1 + random.nextInt(j2 + 1);
        boolean flag = true;
        if (j < 1 || j + l + 1 > 128) {
            return false;
        }
        for (int l2 = j; l2 <= j + 1 + l && flag; ++l2) {
            final boolean flag2 = true;
            int l3;
            if (l2 - j < i2) {
                l3 = 0;
            }
            else {
                l3 = k2;
            }
            for (int i3 = i - l3; i3 <= i + l3 && flag; ++i3) {
                for (int j3 = k - l3; j3 <= k + l3 && flag; ++j3) {
                    if (l2 >= 0 && l2 < 128) {
                        final int k3 = world.getTypeId(i3, l2, j3);
                        if (k3 != 0 && k3 != Block.LEAVES.id) {
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
        if ((l2 == Block.GRASS.id || l2 == Block.DIRT.id) && j < 128 - l - 1) {
            this.setType(world, i, j - 1, k, Block.DIRT.id);
            int l3 = 0;
            for (int i3 = j + l; i3 >= j + i2; --i3) {
                for (int j3 = i - l3; j3 <= i + l3; ++j3) {
                    final int k3 = j3 - i;
                    for (int i4 = k - l3; i4 <= k + l3; ++i4) {
                        final int j4 = i4 - k;
                        if ((Math.abs(k3) != l3 || Math.abs(j4) != l3 || l3 <= 0) && !Block.s[world.getTypeId(j3, i3, i4)]) {
                            this.setTypeAndData(world, j3, i3, i4, Block.LEAVES.id, 1);
                        }
                    }
                }
                if (l3 >= 1 && i3 == j + i2 + 1) {
                    --l3;
                }
                else if (l3 < k2) {
                    ++l3;
                }
            }
            for (int i3 = 0; i3 < l - 1; ++i3) {
                final int j3 = world.getTypeId(i, j + i3, k);
                if (j3 == 0 || j3 == Block.LEAVES.id) {
                    this.setTypeAndData(world, i, j + i3, k, Block.LOG.id, 1);
                }
            }
            return true;
        }
        return false;
    }
}
