// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BiomeJungle extends BiomeBase
{
    public BiomeJungle(final int n) {
        super(n);
        this.I.z = 50;
        this.I.B = 25;
        this.I.A = 4;
        this.J.add(new BiomeMeta(EntityOcelot.class, 2, 1, 1));
        this.K.add(new BiomeMeta(EntityChicken.class, 10, 4, 4));
    }
    
    public WorldGenerator a(final Random random) {
        if (random.nextInt(10) == 0) {
            return this.P;
        }
        if (random.nextInt(2) == 0) {
            return new WorldGenGroundBush(3, 0);
        }
        if (random.nextInt(3) == 0) {
            return new WorldGenMegaTree(false, 10 + random.nextInt(20), 3, 3);
        }
        return new WorldGenTrees(false, 4 + random.nextInt(7), 3, 3, true);
    }
    
    public WorldGenerator b(final Random random) {
        if (random.nextInt(4) == 0) {
            return new WorldGenGrass(Block.LONG_GRASS.id, 2);
        }
        return new WorldGenGrass(Block.LONG_GRASS.id, 1);
    }
    
    public void a(final World world, final Random random, final int n, final int n2) {
        super.a(world, random, n, n2);
        final WorldGenVines worldGenVines = new WorldGenVines();
        for (int i = 0; i < 50; ++i) {
            worldGenVines.a(world, random, n + random.nextInt(16) + 8, 64, n2 + random.nextInt(16) + 8);
        }
    }
}
