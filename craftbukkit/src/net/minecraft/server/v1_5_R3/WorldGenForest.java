// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.BlockChangeDelegate;
import java.util.Random;

public class WorldGenForest extends WorldGenerator implements BlockSapling.TreeGenerator
{
    public WorldGenForest(final boolean flag) {
        super(flag);
    }
    
    public boolean a(final World world, final Random random, final int i, final int j, final int k) {
        return this.generate((BlockChangeDelegate)world, random, i, j, k);
    }
    
    public boolean generate(final BlockChangeDelegate world, final Random random, final int i, final int j, final int k) {
        final int l = random.nextInt(3) + 5;
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
            for (int j2 = i - b0; j2 <= i + b0 && flag; ++j2) {
                for (int k2 = k - b0; k2 <= k + b0 && flag; ++k2) {
                    if (i2 >= 0 && i2 < 256) {
                        final int l2 = world.getTypeId(j2, i2, k2);
                        if (l2 != 0 && l2 != Block.LEAVES.id) {
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
            for (int i3 = j - 3 + l; i3 <= j + l; ++i3) {
                final int j2 = i3 - (j + l);
                for (int k2 = 1 - j2 / 2, l2 = i - k2; l2 <= i + k2; ++l2) {
                    final int j3 = l2 - i;
                    for (int k3 = k - k2; k3 <= k + k2; ++k3) {
                        final int l3 = k3 - k;
                        if (Math.abs(j3) != k2 || Math.abs(l3) != k2 || (random.nextInt(2) != 0 && j2 != 0)) {
                            final int i4 = world.getTypeId(l2, i3, k3);
                            if (i4 == 0 || i4 == Block.LEAVES.id) {
                                this.setTypeAndData(world, l2, i3, k3, Block.LEAVES.id, 2);
                            }
                        }
                    }
                }
            }
            for (int i3 = 0; i3 < l; ++i3) {
                final int j2 = world.getTypeId(i, j + i3, k);
                if (j2 == 0 || j2 == Block.LEAVES.id) {
                    this.setTypeAndData(world, i, j + i3, k, Block.LOG.id, 2);
                }
            }
            return true;
        }
        return false;
    }
}
