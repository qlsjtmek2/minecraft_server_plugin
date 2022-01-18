// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenCactus extends WorldGenerator
{
    public boolean a(final World world, final Random random, final int n, final int n2, final int n3) {
        for (int i = 0; i < 10; ++i) {
            final int j = n + random.nextInt(8) - random.nextInt(8);
            final int k = n2 + random.nextInt(4) - random.nextInt(4);
            final int l = n3 + random.nextInt(8) - random.nextInt(8);
            if (world.isEmpty(j, k, l)) {
                for (int n4 = 1 + random.nextInt(random.nextInt(3) + 1), n5 = 0; n5 < n4; ++n5) {
                    if (Block.CACTUS.f(world, j, k + n5, l)) {
                        world.setTypeIdAndData(j, k + n5, l, Block.CACTUS.id, 0, 2);
                    }
                }
            }
        }
        return true;
    }
}
