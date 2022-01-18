// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.List;

public class BiomeCache
{
    private final WorldChunkManager a;
    private long b;
    private LongHashMap c;
    private List d;
    
    public BiomeCache(final WorldChunkManager a) {
        this.b = 0L;
        this.c = new LongHashMap();
        this.d = new ArrayList();
        this.a = a;
    }
    
    public BiomeCacheBlock a(int n, int n2) {
        n >>= 4;
        n2 >>= 4;
        final long n3 = (n & 0xFFFFFFFFL) | (n2 & 0xFFFFFFFFL) << 32;
        BiomeCacheBlock biomeCacheBlock = (BiomeCacheBlock)this.c.getEntry(n3);
        if (biomeCacheBlock == null) {
            biomeCacheBlock = new BiomeCacheBlock(this, n, n2);
            this.c.put(n3, biomeCacheBlock);
            this.d.add(biomeCacheBlock);
        }
        biomeCacheBlock.f = System.currentTimeMillis();
        return biomeCacheBlock;
    }
    
    public BiomeBase b(final int n, final int n2) {
        return this.a(n, n2).a(n, n2);
    }
    
    public void a() {
        final long currentTimeMillis = System.currentTimeMillis();
        final long n = currentTimeMillis - this.b;
        if (n > 7500L || n < 0L) {
            this.b = currentTimeMillis;
            for (int i = 0; i < this.d.size(); ++i) {
                final BiomeCacheBlock biomeCacheBlock = this.d.get(i);
                final long n2 = currentTimeMillis - biomeCacheBlock.f;
                if (n2 > 30000L || n2 < 0L) {
                    this.d.remove(i--);
                    this.c.remove((biomeCacheBlock.d & 0xFFFFFFFFL) | (biomeCacheBlock.e & 0xFFFFFFFFL) << 32);
                }
            }
        }
    }
    
    public BiomeBase[] e(final int n, final int n2) {
        return this.a(n, n2).c;
    }
}
