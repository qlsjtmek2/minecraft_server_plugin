// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportChunkStats implements Callable
{
    final /* synthetic */ World a;
    
    CrashReportChunkStats(final World a) {
        this.a = a;
    }
    
    public String a() {
        return this.a.chunkProvider.getName();
    }
}
