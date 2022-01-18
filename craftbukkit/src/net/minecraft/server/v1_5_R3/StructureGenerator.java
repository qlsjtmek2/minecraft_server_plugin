// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.HashMap;
import java.util.Map;

public abstract class StructureGenerator extends WorldGenBase
{
    protected Map d;
    
    public StructureGenerator() {
        this.d = new HashMap();
    }
    
    protected void a(final World world, final int n, final int n2, final int n3, final int n4, final byte[] array) {
        if (this.d.containsKey(ChunkCoordIntPair.a(n, n2))) {
            return;
        }
        this.b.nextInt();
        try {
            if (this.a(n, n2)) {
                this.d.put(ChunkCoordIntPair.a(n, n2), this.b(n, n2));
            }
        }
        catch (Throwable throwable) {
            final CrashReport a = CrashReport.a(throwable, "Exception preparing structure feature");
            final CrashReportSystemDetails a2 = a.a("Feature being prepared");
            a2.a("Is feature chunk", new CrashReportIsFeatureChunk(this, n, n2));
            a2.a("Chunk location", String.format("%d,%d", n, n2));
            a2.a("Chunk pos hash", new CrashReportChunkPosHash(this, n, n2));
            a2.a("Structure type", new CrashReportStructureType(this));
            throw new ReportedException(a);
        }
    }
    
    public boolean a(final World world, final Random random, final int n, final int n2) {
        final int n3 = (n << 4) + 8;
        final int n4 = (n2 << 4) + 8;
        boolean b = false;
        for (final StructureStart structureStart : this.d.values()) {
            if (structureStart.d() && structureStart.a().a(n3, n4, n3 + 15, n4 + 15)) {
                structureStart.a(world, random, new StructureBoundingBox(n3, n4, n3 + 15, n4 + 15));
                b = true;
            }
        }
        return b;
    }
    
    public boolean a(final int n, final int n2, final int n3) {
        for (final StructureStart structureStart : this.d.values()) {
            if (structureStart.d() && structureStart.a().a(n, n3, n, n3)) {
                final Iterator iterator2 = structureStart.b().iterator();
                while (iterator2.hasNext()) {
                    if (iterator2.next().b().b(n, n2, n3)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public ChunkPosition getNearestGeneratedFeature(final World c, final int n, final int n2, final int n3) {
        this.c = c;
        this.b.setSeed(c.getSeed());
        this.b.setSeed((n >> 4) * this.b.nextLong() ^ (n3 >> 4) * this.b.nextLong() ^ c.getSeed());
        this.a(c, n >> 4, n3 >> 4, 0, 0, null);
        double n4 = Double.MAX_VALUE;
        ChunkPosition chunkPosition = null;
        for (final StructureStart structureStart : this.d.values()) {
            if (structureStart.d()) {
                final ChunkPosition a = structureStart.b().get(0).a();
                final int n5 = a.x - n;
                final int n6 = a.y - n2;
                final int n7 = a.z - n3;
                final double n8 = n5 + n5 * n6 * n6 + n7 * n7;
                if (n8 >= n4) {
                    continue;
                }
                n4 = n8;
                chunkPosition = a;
            }
        }
        if (chunkPosition != null) {
            return chunkPosition;
        }
        final List p_ = this.p_();
        if (p_ != null) {
            ChunkPosition chunkPosition2 = null;
            for (final ChunkPosition chunkPosition3 : p_) {
                final int n9 = chunkPosition3.x - n;
                final int n10 = chunkPosition3.y - n2;
                final int n11 = chunkPosition3.z - n3;
                final double n12 = n9 + n9 * n10 * n10 + n11 * n11;
                if (n12 < n4) {
                    n4 = n12;
                    chunkPosition2 = chunkPosition3;
                }
            }
            return chunkPosition2;
        }
        return null;
    }
    
    protected List p_() {
        return null;
    }
    
    protected abstract boolean a(final int p0, final int p1);
    
    protected abstract StructureStart b(final int p0, final int p1);
}
