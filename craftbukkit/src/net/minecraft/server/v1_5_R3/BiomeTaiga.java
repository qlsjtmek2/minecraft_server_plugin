// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BiomeTaiga extends BiomeBase
{
    public BiomeTaiga(final int n) {
        super(n);
        this.K.add(new BiomeMeta(EntityWolf.class, 8, 4, 4));
        this.I.z = 10;
        this.I.B = 1;
    }
    
    public WorldGenerator a(final Random random) {
        if (random.nextInt(3) == 0) {
            return new WorldGenTaiga1();
        }
        return new WorldGenTaiga2(false);
    }
}
