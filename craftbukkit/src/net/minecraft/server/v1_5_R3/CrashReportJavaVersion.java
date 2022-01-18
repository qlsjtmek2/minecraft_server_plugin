// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportJavaVersion implements Callable
{
    final /* synthetic */ CrashReport a;
    
    CrashReportJavaVersion(final CrashReport a) {
        this.a = a;
    }
    
    public String a() {
        return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
    }
}
