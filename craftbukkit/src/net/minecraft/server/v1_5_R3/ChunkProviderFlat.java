// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChunkProviderFlat implements IChunkProvider
{
    private World a;
    private Random b;
    private final byte[] c;
    private final byte[] d;
    private final WorldGenFlatInfo e;
    private final List f;
    private final boolean g;
    private final boolean h;
    private WorldGenLakes i;
    private WorldGenLakes j;
    
    public ChunkProviderFlat(final World a, final long n, final boolean b, final String s) {
        this.c = new byte[256];
        this.d = new byte[256];
        this.f = new ArrayList();
        this.a = a;
        this.b = new Random(n);
        this.e = WorldGenFlatInfo.a(s);
        if (b) {
            final Map b2 = this.e.b();
            if (b2.containsKey("village")) {
                final Map<String, String> map = b2.get("village");
                if (!map.containsKey("size")) {
                    map.put("size", "1");
                }
                this.f.add(new WorldGenVillage(map));
            }
            if (b2.containsKey("biome_1")) {
                this.f.add(new WorldGenLargeFeature(b2.get("biome_1")));
            }
            if (b2.containsKey("mineshaft")) {
                this.f.add(new WorldGenMineshaft(b2.get("mineshaft")));
            }
            if (b2.containsKey("stronghold")) {
                this.f.add(new WorldGenStronghold(b2.get("stronghold")));
            }
        }
        this.g = this.e.b().containsKey("decoration");
        if (this.e.b().containsKey("lake")) {
            this.i = new WorldGenLakes(Block.STATIONARY_WATER.id);
        }
        if (this.e.b().containsKey("lava_lake")) {
            this.j = new WorldGenLakes(Block.STATIONARY_LAVA.id);
        }
        this.h = this.e.b().containsKey("dungeon");
        for (final WorldGenFlatLayerInfo worldGenFlatLayerInfo : this.e.c()) {
            for (int i = worldGenFlatLayerInfo.d(); i < worldGenFlatLayerInfo.d() + worldGenFlatLayerInfo.a(); ++i) {
                this.c[i] = (byte)(worldGenFlatLayerInfo.b() & 0xFF);
                this.d[i] = (byte)worldGenFlatLayerInfo.c();
            }
        }
    }
    
    public Chunk getChunkAt(final int n, final int n2) {
        return this.getOrCreateChunk(n, n2);
    }
    
    public Chunk getOrCreateChunk(final int i, final int j) {
        final Chunk chunk = new Chunk(this.a, i, j);
        for (int k = 0; k < this.c.length; ++k) {
            final int n = k >> 4;
            ChunkSection chunkSection = chunk.i()[n];
            if (chunkSection == null) {
                chunkSection = new ChunkSection(k, !this.a.worldProvider.f);
                chunk.i()[n] = chunkSection;
            }
            for (int l = 0; l < 16; ++l) {
                for (int n2 = 0; n2 < 16; ++n2) {
                    chunkSection.setTypeId(l, k & 0xF, n2, this.c[k] & 0xFF);
                    chunkSection.setData(l, k & 0xF, n2, this.d[k]);
                }
            }
        }
        chunk.initLighting();
        final BiomeBase[] biomeBlock = this.a.getWorldChunkManager().getBiomeBlock(null, i * 16, j * 16, 16, 16);
        final byte[] m = chunk.m();
        for (int n3 = 0; n3 < m.length; ++n3) {
            m[n3] = (byte)biomeBlock[n3].id;
        }
        final Iterator<StructureGenerator> iterator = this.f.iterator();
        while (iterator.hasNext()) {
            iterator.next().a(this, this.a, i, j, null);
        }
        chunk.initLighting();
        return chunk;
    }
    
    public boolean isChunkLoaded(final int n, final int n2) {
        return true;
    }
    
    public void getChunkAt(final IChunkProvider chunkProvider, final int n, final int n2) {
        final int n3 = n * 16;
        final int n4 = n2 * 16;
        final BiomeBase biome = this.a.getBiome(n3 + 16, n4 + 16);
        boolean b = false;
        this.b.setSeed(this.a.getSeed());
        this.b.setSeed(n * (this.b.nextLong() / 2L * 2L + 1L) + n2 * (this.b.nextLong() / 2L * 2L + 1L) ^ this.a.getSeed());
        for (final StructureGenerator structureGenerator : this.f) {
            final boolean a = structureGenerator.a(this.a, this.b, n, n2);
            if (structureGenerator instanceof WorldGenVillage) {
                b |= a;
            }
        }
        if (this.i != null && !b && this.b.nextInt(4) == 0) {
            this.i.a(this.a, this.b, n3 + this.b.nextInt(16) + 8, this.b.nextInt(128), n4 + this.b.nextInt(16) + 8);
        }
        if (this.j != null && !b && this.b.nextInt(8) == 0) {
            final int n5 = n3 + this.b.nextInt(16) + 8;
            final int nextInt = this.b.nextInt(this.b.nextInt(120) + 8);
            final int n6 = n4 + this.b.nextInt(16) + 8;
            if (nextInt < 63 || this.b.nextInt(10) == 0) {
                this.j.a(this.a, this.b, n5, nextInt, n6);
            }
        }
        if (this.h) {
            for (int i = 0; i < 8; ++i) {
                new WorldGenDungeons().a(this.a, this.b, n3 + this.b.nextInt(16) + 8, this.b.nextInt(128), n4 + this.b.nextInt(16) + 8);
            }
        }
        if (this.g) {
            biome.a(this.a, this.b, n3, n4);
        }
    }
    
    public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
        return true;
    }
    
    public void b() {
    }
    
    public boolean unloadChunks() {
        return false;
    }
    
    public boolean canSave() {
        return true;
    }
    
    public String getName() {
        return "FlatLevelSource";
    }
    
    public List getMobsFor(final EnumCreatureType enumCreatureType, final int i, final int n, final int j) {
        final BiomeBase biome = this.a.getBiome(i, j);
        if (biome == null) {
            return null;
        }
        return biome.getMobs(enumCreatureType);
    }
    
    public ChunkPosition findNearestMapFeature(final World world, final String s, final int n, final int n2, final int n3) {
        if ("Stronghold".equals(s)) {
            for (final StructureGenerator structureGenerator : this.f) {
                if (structureGenerator instanceof WorldGenStronghold) {
                    return structureGenerator.getNearestGeneratedFeature(world, n, n2, n3);
                }
            }
        }
        return null;
    }
    
    public int getLoadedChunks() {
        return 0;
    }
    
    public void recreateStructures(final int n, final int n2) {
        final Iterator<StructureGenerator> iterator = this.f.iterator();
        while (iterator.hasNext()) {
            iterator.next().a(this, this.a, n, n2, null);
        }
    }
}
