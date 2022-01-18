// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import java.util.ArrayList;

public class WorldGenVillageStartPiece extends WorldGenVillageWell
{
    public final WorldChunkManager a;
    public final boolean b;
    public final int c;
    public WorldGenVillagePieceWeight d;
    public ArrayList h;
    public ArrayList i;
    public ArrayList j;
    
    public WorldGenVillageStartPiece(final WorldChunkManager a, final int n, final Random random, final int n2, final int n3, final ArrayList h, final int c) {
        super(null, 0, random, n2, n3);
        this.i = new ArrayList();
        this.j = new ArrayList();
        this.a = a;
        this.h = h;
        this.c = c;
        final BiomeBase biome = a.getBiome(n2, n3);
        this.b = (biome == BiomeBase.DESERT || biome == BiomeBase.DESERT_HILLS);
        this.k = this;
    }
    
    public WorldChunkManager d() {
        return this.a;
    }
}
