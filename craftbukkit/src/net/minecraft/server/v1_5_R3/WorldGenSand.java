// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenSand extends WorldGenerator
{
    private int a;
    private int b;
    
    public WorldGenSand(final int b, final int a) {
        this.a = a;
        this.b = b;
    }
    
    public boolean a(final World world, final Random random, final int i, final int j, final int k) {
        if (world.getMaterial(i, j, k) != Material.WATER) {
            return false;
        }
        final int n = random.nextInt(this.b - 2) + 2;
        final int n2 = 2;
        for (int l = i - n; l <= i + n; ++l) {
            for (int n3 = k - n; n3 <= k + n; ++n3) {
                final int n4 = l - i;
                final int n5 = n3 - k;
                if (n4 * n4 + n5 * n5 <= n * n) {
                    for (int n6 = j - n2; n6 <= j + n2; ++n6) {
                        final int typeId = world.getTypeId(l, n6, n3);
                        if (typeId == Block.DIRT.id || typeId == Block.GRASS.id) {
                            world.setTypeIdAndData(l, n6, n3, this.a, 0, 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
