// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

public class CrashReportVec3DPoolSize implements Callable
{
    final /* synthetic */ MinecraftServer a;
    
    public CrashReportVec3DPoolSize(final MinecraftServer a) {
        this.a = a;
    }
    
    public String a() {
        final int c = this.a.worldServer[0].getVec3DPool().c();
        final int n = 56 * c;
        final int n2 = n / 1024 / 1024;
        final int d = this.a.worldServer[0].getVec3DPool().d();
        final int n3 = 56 * d;
        return c + " (" + n + " bytes; " + n2 + " MB) allocated, " + d + " (" + n3 + " bytes; " + n3 / 1024 / 1024 + " MB) used";
    }
}
