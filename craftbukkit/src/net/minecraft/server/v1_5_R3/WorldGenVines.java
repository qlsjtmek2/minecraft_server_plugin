// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenVines extends WorldGenerator
{
    public boolean a(final World world, final Random random, int i, int j, int k) {
        final int n = i;
        final int n2 = k;
        while (j < 128) {
            if (world.isEmpty(i, j, k)) {
                for (int l = 2; l <= 5; ++l) {
                    if (Block.VINE.canPlace(world, i, j, k, l)) {
                        world.setTypeIdAndData(i, j, k, Block.VINE.id, 1 << Direction.e[Facing.OPPOSITE_FACING[l]], 2);
                        break;
                    }
                }
            }
            else {
                i = n + random.nextInt(4) - random.nextInt(4);
                k = n2 + random.nextInt(4) - random.nextInt(4);
            }
            ++j;
        }
        return true;
    }
}
