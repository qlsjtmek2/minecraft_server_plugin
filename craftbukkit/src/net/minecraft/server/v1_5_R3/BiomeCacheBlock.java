// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.List;

public class BiomeCacheBlock
{
    public float[] a;
    public float[] b;
    public BiomeBase[] c;
    public int d;
    public int e;
    public long f;
    final /* synthetic */ BiomeCache g;
    
    public BiomeCacheBlock(final BiomeCache g, final int d, final int e) {
        this.g = g;
        this.a = new float[256];
        this.b = new float[256];
        this.c = new BiomeBase[256];
        this.d = d;
        this.e = e;
        g.a.getTemperatures(this.a, d << 4, e << 4, 16, 16);
        g.a.getWetness(this.b, d << 4, e << 4, 16, 16);
        g.a.a(this.c, d << 4, e << 4, 16, 16, false);
    }
    
    public BiomeBase a(final int n, final int n2) {
        return this.c[(n & 0xF) | (n2 & 0xF) << 4];
    }
}
