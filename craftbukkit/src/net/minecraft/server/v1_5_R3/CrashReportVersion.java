// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportVersion implements Callable
{
    final /* synthetic */ CrashReport a;
    
    CrashReportVersion(final CrashReport a) {
        this.a = a;
    }
    
    public String a() {
        return "1.5.2";
    }
}
