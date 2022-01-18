// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.util.Map;

public class WorldGenStronghold extends StructureGenerator
{
    private BiomeBase[] e;
    private boolean f;
    private ChunkCoordIntPair[] g;
    private double h;
    private int i;
    
    public WorldGenStronghold() {
        this.e = new BiomeBase[] { BiomeBase.DESERT, BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.SWAMPLAND, BiomeBase.TAIGA, BiomeBase.ICE_PLAINS, BiomeBase.ICE_MOUNTAINS, BiomeBase.DESERT_HILLS, BiomeBase.FOREST_HILLS, BiomeBase.SMALL_MOUNTAINS, BiomeBase.JUNGLE, BiomeBase.JUNGLE_HILLS };
        this.g = new ChunkCoordIntPair[3];
        this.h = 32.0;
        this.i = 3;
    }
    
    public WorldGenStronghold(final Map map) {
        this.e = new BiomeBase[] { BiomeBase.DESERT, BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.SWAMPLAND, BiomeBase.TAIGA, BiomeBase.ICE_PLAINS, BiomeBase.ICE_MOUNTAINS, BiomeBase.DESERT_HILLS, BiomeBase.FOREST_HILLS, BiomeBase.SMALL_MOUNTAINS, BiomeBase.JUNGLE, BiomeBase.JUNGLE_HILLS };
        this.g = new ChunkCoordIntPair[3];
        this.h = 32.0;
        this.i = 3;
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            if (entry.getKey().equals("distance")) {
                this.h = MathHelper.a((String)entry.getValue(), this.h, 1.0);
            }
            else if (entry.getKey().equals("count")) {
                this.g = new ChunkCoordIntPair[MathHelper.a((String)entry.getValue(), this.g.length, 1)];
            }
            else {
                if (!entry.getKey().equals("spread")) {
                    continue;
                }
                this.i = MathHelper.a((String)entry.getValue(), this.i, 1);
            }
        }
    }
    
    protected boolean a(final int n, final int n2) {
        if (!this.f) {
            final Random random = new Random();
            random.setSeed(this.c.getSeed());
            double n3 = random.nextDouble() * 3.141592653589793 * 2.0;
            int n4 = 1;
            for (int i = 0; i < this.g.length; ++i) {
                final double n5 = (1.25 * n4 + random.nextDouble()) * (this.h * n4);
                int n6 = (int)Math.round(Math.cos(n3) * n5);
                int n7 = (int)Math.round(Math.sin(n3) * n5);
                final ArrayList<Object> list = new ArrayList<Object>();
                Collections.addAll(list, this.e);
                final ChunkPosition a = this.c.getWorldChunkManager().a((n6 << 4) + 8, (n7 << 4) + 8, 112, list, random);
                if (a != null) {
                    n6 = a.x >> 4;
                    n7 = a.z >> 4;
                }
                this.g[i] = new ChunkCoordIntPair(n6, n7);
                n3 += 6.283185307179586 * n4 / this.i;
                if (i == this.i) {
                    n4 += 2 + random.nextInt(5);
                    this.i += 1 + random.nextInt(2);
                }
            }
            this.f = true;
        }
        for (final ChunkCoordIntPair chunkCoordIntPair : this.g) {
            if (n == chunkCoordIntPair.x && n2 == chunkCoordIntPair.z) {
                return true;
            }
        }
        return false;
    }
    
    protected List p_() {
        final ArrayList<ChunkPosition> list = new ArrayList<ChunkPosition>();
        for (final ChunkCoordIntPair chunkCoordIntPair : this.g) {
            if (chunkCoordIntPair != null) {
                list.add(chunkCoordIntPair.a(64));
            }
        }
        return list;
    }
    
    protected StructureStart b(final int n, final int n2) {
        WorldGenStronghold2Start worldGenStronghold2Start;
        for (worldGenStronghold2Start = new WorldGenStronghold2Start(this.c, this.b, n, n2); worldGenStronghold2Start.b().isEmpty() || ((WorldGenStrongholdStart)worldGenStronghold2Start.b().get(0)).b == null; worldGenStronghold2Start = new WorldGenStronghold2Start(this.c, this.b, n, n2)) {}
        return worldGenStronghold2Start;
    }
}
