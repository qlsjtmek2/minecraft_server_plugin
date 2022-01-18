// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenFire extends WorldGenerator
{
    public boolean a(final World world, final Random random, final int n, final int n2, final int n3) {
        for (int i = 0; i < 64; ++i) {
            final int j = n + random.nextInt(8) - random.nextInt(8);
            final int n4 = n2 + random.nextInt(4) - random.nextInt(4);
            final int k = n3 + random.nextInt(8) - random.nextInt(8);
            if (world.isEmpty(j, n4, k)) {
                if (world.getTypeId(j, n4 - 1, k) == Block.NETHERRACK.id) {
                    world.setTypeIdAndData(j, n4, k, Block.FIRE.id, 0, 2);
                }
            }
        }
        return true;
    }
}
