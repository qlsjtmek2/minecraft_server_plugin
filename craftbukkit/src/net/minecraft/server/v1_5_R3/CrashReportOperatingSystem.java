// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportOperatingSystem implements Callable
{
    final /* synthetic */ CrashReport a;
    
    CrashReportOperatingSystem(final CrashReport a) {
        this.a = a;
    }
    
    public String a() {
        return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
    }
}
