// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BiomeBigHills extends BiomeBase
{
    private WorldGenerator S;
    
    protected BiomeBigHills(final int n) {
        super(n);
        this.S = new WorldGenMinable(Block.MONSTER_EGGS.id, 8);
    }
    
    public void a(final World world, final Random random, final int n, final int n2) {
        super.a(world, random, n, n2);
        for (int n3 = 3 + random.nextInt(6), i = 0; i < n3; ++i) {
            final int n4 = n + random.nextInt(16);
            final int n5 = random.nextInt(28) + 4;
            final int n6 = n2 + random.nextInt(16);
            if (world.getTypeId(n4, n5, n6) == Block.STONE.id) {
                world.setTypeIdAndData(n4, n5, n6, Block.EMERALD_ORE.id, 0, 2);
            }
        }
        for (int j = 0; j < 7; ++j) {
            this.S.a(world, random, n + random.nextInt(16), random.nextInt(64), n2 + random.nextInt(16));
        }
    }
}
