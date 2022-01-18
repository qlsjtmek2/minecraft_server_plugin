// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BiomeSwamp extends BiomeBase
{
    protected BiomeSwamp(final int n) {
        super(n);
        this.I.z = 2;
        this.I.A = -999;
        this.I.C = 1;
        this.I.D = 8;
        this.I.E = 10;
        this.I.I = 1;
        this.I.y = 4;
        this.H = 14745518;
        this.J.add(new BiomeMeta(EntitySlime.class, 1, 1, 1));
    }
    
    public WorldGenerator a(final Random random) {
        return this.R;
    }
}
