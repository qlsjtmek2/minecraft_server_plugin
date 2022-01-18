// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Arrays;
import java.util.Random;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

public class WorldGenVillage extends StructureGenerator
{
    public static final List e;
    private int f;
    private int g;
    private int h;
    
    public WorldGenVillage() {
        this.f = 0;
        this.g = 32;
        this.h = 8;
    }
    
    public WorldGenVillage(final Map map) {
        this();
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            if (entry.getKey().equals("size")) {
                this.f = MathHelper.a((String)entry.getValue(), this.f, 0);
            }
            else {
                if (!entry.getKey().equals("distance")) {
                    continue;
                }
                this.g = MathHelper.a((String)entry.getValue(), this.g, this.h + 1);
            }
        }
    }
    
    protected boolean a(int n, int n2) {
        final int n3 = n;
        final int n4 = n2;
        if (n < 0) {
            n -= this.g - 1;
        }
        if (n2 < 0) {
            n2 -= this.g - 1;
        }
        final int i = n / this.g;
        final int j = n2 / this.g;
        final Random h = this.c.H(i, j, 10387312);
        final int n5 = i * this.g;
        final int n6 = j * this.g;
        final int n7 = n5 + h.nextInt(this.g - this.h);
        final int n8 = n6 + h.nextInt(this.g - this.h);
        n = n3;
        n2 = n4;
        return n == n7 && n2 == n8 && this.c.getWorldChunkManager().a(n * 16 + 8, n2 * 16 + 8, 0, WorldGenVillage.e);
    }
    
    protected StructureStart b(final int n, final int n2) {
        return new WorldGenVillageStart(this.c, this.b, n, n2, this.f);
    }
    
    static {
        e = Arrays.asList(BiomeBase.PLAINS, BiomeBase.DESERT);
    }
}
