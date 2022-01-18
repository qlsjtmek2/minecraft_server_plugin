// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportJavaVMVersion implements Callable
{
    final /* synthetic */ CrashReport a;
    
    CrashReportJavaVMVersion(final CrashReport a) {
        this.a = a;
    }
    
    public String a() {
        return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
    }
}
