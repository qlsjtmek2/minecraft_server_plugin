// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BiomeDesert extends BiomeBase
{
    public BiomeDesert(final int n) {
        super(n);
        this.K.clear();
        this.A = (byte)Block.SAND.id;
        this.B = (byte)Block.SAND.id;
        this.I.z = -999;
        this.I.C = 2;
        this.I.E = 50;
        this.I.F = 10;
    }
    
    public void a(final World world, final Random random, final int n, final int n2) {
        super.a(world, random, n, n2);
        if (random.nextInt(1000) == 0) {
            final int i = n + random.nextInt(16) + 8;
            final int j = n2 + random.nextInt(16) + 8;
            new WorldGenDesertWell().a(world, random, i, world.getHighestBlockYAt(i, j) + 1, j);
        }
    }
}
