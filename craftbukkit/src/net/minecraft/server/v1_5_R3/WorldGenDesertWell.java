// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenDesertWell extends WorldGenerator
{
    public boolean a(final World world, final Random random, final int i, int j, final int k) {
        while (world.isEmpty(i, j, k) && j > 2) {
            --j;
        }
        if (world.getTypeId(i, j, k) != Block.SAND.id) {
            return false;
        }
        for (int l = -2; l <= 2; ++l) {
            for (int n = -2; n <= 2; ++n) {
                if (world.isEmpty(i + l, j - 1, k + n) && world.isEmpty(i + l, j - 2, k + n)) {
                    return false;
                }
            }
        }
        for (int n2 = -1; n2 <= 0; ++n2) {
            for (int n3 = -2; n3 <= 2; ++n3) {
                for (int n4 = -2; n4 <= 2; ++n4) {
                    world.setTypeIdAndData(i + n3, j + n2, k + n4, Block.SANDSTONE.id, 0, 2);
                }
            }
        }
        world.setTypeIdAndData(i, j, k, Block.WATER.id, 0, 2);
        world.setTypeIdAndData(i - 1, j, k, Block.WATER.id, 0, 2);
        world.setTypeIdAndData(i + 1, j, k, Block.WATER.id, 0, 2);
        world.setTypeIdAndData(i, j, k - 1, Block.WATER.id, 0, 2);
        world.setTypeIdAndData(i, j, k + 1, Block.WATER.id, 0, 2);
        for (int n5 = -2; n5 <= 2; ++n5) {
            for (int n6 = -2; n6 <= 2; ++n6) {
                if (n5 == -2 || n5 == 2 || n6 == -2 || n6 == 2) {
                    world.setTypeIdAndData(i + n5, j + 1, k + n6, Block.SANDSTONE.id, 0, 2);
                }
            }
        }
        world.setTypeIdAndData(i + 2, j + 1, k, Block.STEP.id, 1, 2);
        world.setTypeIdAndData(i - 2, j + 1, k, Block.STEP.id, 1, 2);
        world.setTypeIdAndData(i, j + 1, k + 2, Block.STEP.id, 1, 2);
        world.setTypeIdAndData(i, j + 1, k - 2, Block.STEP.id, 1, 2);
        for (int n7 = -1; n7 <= 1; ++n7) {
            for (int n8 = -1; n8 <= 1; ++n8) {
                if (n7 == 0 && n8 == 0) {
                    world.setTypeIdAndData(i + n7, j + 4, k + n8, Block.SANDSTONE.id, 0, 2);
                }
                else {
                    world.setTypeIdAndData(i + n7, j + 4, k + n8, Block.STEP.id, 1, 2);
                }
            }
        }
        for (int n9 = 1; n9 <= 3; ++n9) {
            world.setTypeIdAndData(i - 1, j + n9, k - 1, Block.SANDSTONE.id, 0, 2);
            world.setTypeIdAndData(i - 1, j + n9, k + 1, Block.SANDSTONE.id, 0, 2);
            world.setTypeIdAndData(i + 1, j + n9, k - 1, Block.SANDSTONE.id, 0, 2);
            world.setTypeIdAndData(i + 1, j + n9, k + 1, Block.SANDSTONE.id, 0, 2);
        }
        return true;
    }
}
