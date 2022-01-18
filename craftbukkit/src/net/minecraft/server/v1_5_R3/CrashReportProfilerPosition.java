// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

public class CrashReportProfilerPosition implements Callable
{
    final /* synthetic */ MinecraftServer a;
    
    public CrashReportProfilerPosition(final MinecraftServer a) {
        this.a = a;
    }
    
    public String a() {
        return this.a.methodProfiler.a ? this.a.methodProfiler.c() : "N/A (disabled)";
    }
}
