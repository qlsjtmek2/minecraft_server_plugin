// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BiomeMeta extends WeightedRandomChoice
{
    public Class b;
    public int c;
    public int d;
    
    public BiomeMeta(final Class b, final int n, final int c, final int d) {
        super(n);
        this.b = b;
        this.c = c;
        this.d = d;
    }
}
