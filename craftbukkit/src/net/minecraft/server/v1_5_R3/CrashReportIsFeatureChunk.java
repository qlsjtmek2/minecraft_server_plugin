// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportIsFeatureChunk implements Callable
{
    final /* synthetic */ int a;
    final /* synthetic */ int b;
    final /* synthetic */ StructureGenerator c;
    
    CrashReportIsFeatureChunk(final StructureGenerator c, final int a, final int b) {
        this.c = c;
        this.a = a;
        this.b = b;
    }
    
    public String a() {
        return this.c.a(this.a, this.b) ? "True" : "False";
    }
}
