// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportAABBPoolSize implements Callable
{
    final /* synthetic */ CrashReport a;
    
    CrashReportAABBPoolSize(final CrashReport a) {
        this.a = a;
    }
    
    public String a() {
        final int c = AxisAlignedBB.a().c();
        final int n = 56 * c;
        final int n2 = n / 1024 / 1024;
        final int d = AxisAlignedBB.a().d();
        final int n3 = 56 * d;
        return c + " (" + n + " bytes; " + n2 + " MB) allocated, " + d + " (" + n3 + " bytes; " + n3 / 1024 / 1024 + " MB) used";
    }
}
