// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenPumpkin extends WorldGenerator
{
    public boolean a(final World world, final Random random, final int n, final int n2, final int n3) {
        for (int i = 0; i < 64; ++i) {
            final int n4 = n + random.nextInt(8) - random.nextInt(8);
            final int j = n2 + random.nextInt(4) - random.nextInt(4);
            final int n5 = n3 + random.nextInt(8) - random.nextInt(8);
            if (world.isEmpty(n4, j, n5) && world.getTypeId(n4, j - 1, n5) == Block.GRASS.id && Block.PUMPKIN.canPlace(world, n4, j, n5)) {
                world.setTypeIdAndData(n4, j, n5, Block.PUMPKIN.id, random.nextInt(4), 2);
            }
        }
        return true;
    }
}
