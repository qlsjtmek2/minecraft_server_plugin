// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenWaterLily extends WorldGenerator
{
    public boolean a(final World world, final Random random, final int n, final int n2, final int n3) {
        for (int i = 0; i < 10; ++i) {
            final int j = n + random.nextInt(8) - random.nextInt(8);
            final int k = n2 + random.nextInt(4) - random.nextInt(4);
            final int l = n3 + random.nextInt(8) - random.nextInt(8);
            if (world.isEmpty(j, k, l) && Block.WATER_LILY.canPlace(world, j, k, l)) {
                world.setTypeIdAndData(j, k, l, Block.WATER_LILY.id, 0, 2);
            }
        }
        return true;
    }
}
