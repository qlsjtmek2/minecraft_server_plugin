// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenReed extends WorldGenerator
{
    public boolean a(final World world, final Random random, final int n, final int j, final int n2) {
        for (int i = 0; i < 20; ++i) {
            final int k = n + random.nextInt(4) - random.nextInt(4);
            final int l = n2 + random.nextInt(4) - random.nextInt(4);
            if (world.isEmpty(k, j, l) && (world.getMaterial(k - 1, j - 1, l) == Material.WATER || world.getMaterial(k + 1, j - 1, l) == Material.WATER || world.getMaterial(k, j - 1, l - 1) == Material.WATER || world.getMaterial(k, j - 1, l + 1) == Material.WATER)) {
                for (int n3 = 2 + random.nextInt(random.nextInt(3) + 1), n4 = 0; n4 < n3; ++n4) {
                    if (Block.SUGAR_CANE_BLOCK.f(world, k, j + n4, l)) {
                        world.setTypeIdAndData(k, j + n4, l, Block.SUGAR_CANE_BLOCK.id, 0, 2);
                    }
                }
            }
        }
        return true;
    }
}
