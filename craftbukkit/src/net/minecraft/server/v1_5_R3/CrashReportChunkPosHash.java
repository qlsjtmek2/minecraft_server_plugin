// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportChunkPosHash implements Callable
{
    final /* synthetic */ int a;
    final /* synthetic */ int b;
    final /* synthetic */ StructureGenerator c;
    
    CrashReportChunkPosHash(final StructureGenerator c, final int a, final int b) {
        this.c = c;
        this.a = a;
        this.b = b;
    }
    
    public String a() {
        return String.valueOf(ChunkCoordIntPair.a(this.a, this.b));
    }
}
