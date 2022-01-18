// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportLevelTime implements Callable
{
    final /* synthetic */ WorldData a;
    
    CrashReportLevelTime(final WorldData a) {
        this.a = a;
    }
    
    public String a() {
        return String.format("%d game time, %d day time", this.a.time, this.a.dayTime);
    }
}
