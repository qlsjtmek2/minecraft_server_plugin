// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public abstract class GenLayer
{
    private long b;
    protected GenLayer a;
    private long c;
    private long d;
    
    public static GenLayer[] a(final long n, final WorldType worldType) {
        final GenLayerMushroomIsland genLayerMushroomIsland = new GenLayerMushroomIsland(5L, new GenLayerIsland(4L, new GenLayerZoom(2003L, new GenLayerIsland(3L, new GenLayerZoom(2002L, new GenLayerIcePlains(2L, new GenLayerIsland(2L, new GenLayerZoom(2001L, new GenLayerIsland(1L, new GenLayerZoomFuzzy(2000L, new LayerIsland(1L)))))))))));
        int n2 = 4;
        if (worldType == WorldType.LARGE_BIOMES) {
            n2 = 6;
        }
        final GenLayerSmooth genLayerSmooth = new GenLayerSmooth(1000L, new GenLayerRiver(1L, GenLayerZoom.a(1000L, new GenLayerRiverInit(100L, GenLayerZoom.a(1000L, genLayerMushroomIsland, 0)), n2 + 2)));
        GenLayer genLayer = new GenLayerRegionHills(1000L, GenLayerZoom.a(1000L, new GenLayerBiome(200L, GenLayerZoom.a(1000L, genLayerMushroomIsland, 0), worldType), 2));
        for (int i = 0; i < n2; ++i) {
            genLayer = new GenLayerZoom(1000 + i, genLayer);
            if (i == 0) {
                genLayer = new GenLayerIsland(3L, genLayer);
            }
            if (i == 1) {
                genLayer = new GenLayerMushroomShore(1000L, genLayer);
            }
            if (i == 1) {
                genLayer = new GenLayerSwampRivers(1000L, genLayer);
            }
        }
        final GenLayerRiverMix genLayerRiverMix2;
        final GenLayerRiverMix genLayerRiverMix = genLayerRiverMix2 = new GenLayerRiverMix(100L, new GenLayerSmooth(1000L, genLayer), genLayerSmooth);
        final GenLayerZoomVoronoi genLayerZoomVoronoi = new GenLayerZoomVoronoi(10L, genLayerRiverMix);
        genLayerRiverMix.a(n);
        genLayerZoomVoronoi.a(n);
        return new GenLayer[] { genLayerRiverMix, genLayerZoomVoronoi, genLayerRiverMix2 };
    }
    
    public GenLayer(final long d) {
        this.d = d;
        this.d *= this.d * 6364136223846793005L + 1442695040888963407L;
        this.d += d;
        this.d *= this.d * 6364136223846793005L + 1442695040888963407L;
        this.d += d;
        this.d *= this.d * 6364136223846793005L + 1442695040888963407L;
        this.d += d;
    }
    
    public void a(final long b) {
        this.b = b;
        if (this.a != null) {
            this.a.a(b);
        }
        this.b *= this.b * 6364136223846793005L + 1442695040888963407L;
        this.b += this.d;
        this.b *= this.b * 6364136223846793005L + 1442695040888963407L;
        this.b += this.d;
        this.b *= this.b * 6364136223846793005L + 1442695040888963407L;
        this.b += this.d;
    }
    
    public void a(final long n, final long n2) {
        this.c = this.b;
        this.c *= this.c * 6364136223846793005L + 1442695040888963407L;
        this.c += n;
        this.c *= this.c * 6364136223846793005L + 1442695040888963407L;
        this.c += n2;
        this.c *= this.c * 6364136223846793005L + 1442695040888963407L;
        this.c += n;
        this.c *= this.c * 6364136223846793005L + 1442695040888963407L;
        this.c += n2;
    }
    
    protected int a(final int n) {
        int n2 = (int)((this.c >> 24) % n);
        if (n2 < 0) {
            n2 += n;
        }
        this.c *= this.c * 6364136223846793005L + 1442695040888963407L;
        this.c += this.b;
        return n2;
    }
    
    public abstract int[] a(final int p0, final int p1, final int p2, final int p3);
}
