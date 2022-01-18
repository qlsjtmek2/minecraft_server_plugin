// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportEntityTrackerUpdateInterval implements Callable
{
    final /* synthetic */ int a;
    final /* synthetic */ EntityTracker b;
    
    CrashReportEntityTrackerUpdateInterval(final EntityTracker b, final int a) {
        this.b = b;
        this.a = a;
    }
    
    public String a() {
        String s = "Once per " + this.a + " ticks";
        if (this.a == Integer.MAX_VALUE) {
            s = "Maximum (" + s + ")";
        }
        return s;
    }
}
