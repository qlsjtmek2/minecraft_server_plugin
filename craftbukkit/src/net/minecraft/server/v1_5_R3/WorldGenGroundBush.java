// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.BlockChangeDelegate;
import java.util.Random;

public class WorldGenGroundBush extends WorldGenerator implements BlockSapling.TreeGenerator
{
    private int a;
    private int b;
    
    public WorldGenGroundBush(final int i, final int j) {
        this.b = i;
        this.a = j;
    }
    
    public boolean a(final World world, final Random random, final int i, final int j, final int k) {
        return this.generate((BlockChangeDelegate)world, random, i, j, k);
    }
    
    public boolean generate(final BlockChangeDelegate world, final Random random, final int i, int j, final int k) {
        final boolean flag = false;
        int l;
        while (((l = world.getTypeId(i, j, k)) == 0 || l == Block.LEAVES.id) && j > 0) {
            --j;
        }
        final int i2 = world.getTypeId(i, j, k);
        if (i2 == Block.DIRT.id || i2 == Block.GRASS.id) {
            ++j;
            this.setTypeAndData(world, i, j, k, Block.LOG.id, this.b);
            for (int j2 = j; j2 <= j + 2; ++j2) {
                final int k2 = j2 - j;
                for (int l2 = 2 - k2, i3 = i - l2; i3 <= i + l2; ++i3) {
                    final int j3 = i3 - i;
                    for (int k3 = k - l2; k3 <= k + l2; ++k3) {
                        final int l3 = k3 - k;
                        if ((Math.abs(j3) != l2 || Math.abs(l3) != l2 || random.nextInt(2) != 0) && !Block.s[world.getTypeId(i3, j2, k3)]) {
                            this.setTypeAndData(world, i3, j2, k3, Block.LEAVES.id, this.a);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
