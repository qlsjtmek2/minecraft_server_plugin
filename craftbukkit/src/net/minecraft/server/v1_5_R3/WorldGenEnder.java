// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenEnder extends WorldGenerator
{
    private int a;
    
    public WorldGenEnder(final int a) {
        this.a = a;
    }
    
    public boolean a(final World world, final Random random, final int i, final int j, final int k) {
        if (!world.isEmpty(i, j, k) || world.getTypeId(i, j - 1, k) != this.a) {
            return false;
        }
        final int n = random.nextInt(32) + 6;
        final int n2 = random.nextInt(4) + 1;
        for (int l = i - n2; l <= i + n2; ++l) {
            for (int m = k - n2; m <= k + n2; ++m) {
                final int n3 = l - i;
                final int n4 = m - k;
                if (n3 * n3 + n4 * n4 <= n2 * n2 + 1 && world.getTypeId(l, j - 1, m) != this.a) {
                    return false;
                }
            }
        }
        for (int j2 = j; j2 < j + n && j2 < 128; ++j2) {
            for (int i2 = i - n2; i2 <= i + n2; ++i2) {
                for (int k2 = k - n2; k2 <= k + n2; ++k2) {
                    final int n5 = i2 - i;
                    final int n6 = k2 - k;
                    if (n5 * n5 + n6 * n6 <= n2 * n2 + 1) {
                        world.setTypeIdAndData(i2, j2, k2, Block.OBSIDIAN.id, 0, 2);
                    }
                }
            }
        }
        final EntityEnderCrystal entity = new EntityEnderCrystal(world);
        entity.setPositionRotation(i + 0.5f, j + n, k + 0.5f, random.nextFloat() * 360.0f, 0.0f);
        world.addEntity(entity);
        world.setTypeIdAndData(i, j + n, k, Block.BEDROCK.id, 0, 2);
        return true;
    }
}
