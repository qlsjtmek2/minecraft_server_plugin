// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BiomeForest extends BiomeBase
{
    public BiomeForest(final int n) {
        super(n);
        this.K.add(new BiomeMeta(EntityWolf.class, 5, 4, 4));
        this.I.z = 10;
        this.I.B = 2;
    }
    
    public WorldGenerator a(final Random random) {
        if (random.nextInt(5) == 0) {
            return this.Q;
        }
        if (random.nextInt(10) == 0) {
            return this.P;
        }
        return this.O;
    }
}
