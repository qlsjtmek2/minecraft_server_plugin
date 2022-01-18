// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenLightStone2 extends WorldGenerator
{
    public boolean a(final World world, final Random random, final int i, final int n, final int k) {
        if (!world.isEmpty(i, n, k)) {
            return false;
        }
        if (world.getTypeId(i, n + 1, k) != Block.NETHERRACK.id) {
            return false;
        }
        world.setTypeIdAndData(i, n, k, Block.GLOWSTONE.id, 0, 2);
        for (int j = 0; j < 1500; ++j) {
            final int n2 = i + random.nextInt(8) - random.nextInt(8);
            final int n3 = n - random.nextInt(12);
            final int n4 = k + random.nextInt(8) - random.nextInt(8);
            if (world.getTypeId(n2, n3, n4) == 0) {
                int n5 = 0;
                for (int l = 0; l < 6; ++l) {
                    int n6 = 0;
                    if (l == 0) {
                        n6 = world.getTypeId(n2 - 1, n3, n4);
                    }
                    if (l == 1) {
                        n6 = world.getTypeId(n2 + 1, n3, n4);
                    }
                    if (l == 2) {
                        n6 = world.getTypeId(n2, n3 - 1, n4);
                    }
                    if (l == 3) {
                        n6 = world.getTypeId(n2, n3 + 1, n4);
                    }
                    if (l == 4) {
                        n6 = world.getTypeId(n2, n3, n4 - 1);
                    }
                    if (l == 5) {
                        n6 = world.getTypeId(n2, n3, n4 + 1);
                    }
                    if (n6 == Block.GLOWSTONE.id) {
                        ++n5;
                    }
                }
                if (n5 == 1) {
                    world.setTypeIdAndData(n2, n3, n4, Block.GLOWSTONE.id, 0, 2);
                }
            }
        }
        return true;
    }
}
