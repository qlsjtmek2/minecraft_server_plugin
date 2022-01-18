// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenDeadBush extends WorldGenerator
{
    private int a;
    
    public WorldGenDeadBush(final int a) {
        this.a = a;
    }
    
    public boolean a(final World world, final Random random, final int i, int j, final int k) {
        int typeId;
        while (((typeId = world.getTypeId(i, j, k)) == 0 || typeId == Block.LEAVES.id) && j > 0) {
            --j;
        }
        for (int l = 0; l < 4; ++l) {
            final int m = i + random.nextInt(8) - random.nextInt(8);
            final int j2 = j + random.nextInt(4) - random.nextInt(4);
            final int k2 = k + random.nextInt(8) - random.nextInt(8);
            if (world.isEmpty(m, j2, k2) && Block.byId[this.a].f(world, m, j2, k2)) {
                world.setTypeIdAndData(m, j2, k2, this.a, 0, 2);
            }
        }
        return true;
    }
}
