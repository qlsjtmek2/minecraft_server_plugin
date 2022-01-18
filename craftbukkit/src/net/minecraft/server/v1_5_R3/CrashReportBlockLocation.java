// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

final class CrashReportBlockLocation implements Callable
{
    final /* synthetic */ int a;
    final /* synthetic */ int b;
    final /* synthetic */ int c;
    
    CrashReportBlockLocation(final int a, final int b, final int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public String a() {
        return CrashReportSystemDetails.a(this.a, this.b, this.c);
    }
}
