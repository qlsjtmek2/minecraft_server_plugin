// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportConnectionPacketClass implements Callable
{
    final /* synthetic */ Packet a;
    final /* synthetic */ PlayerConnection b;
    
    CrashReportConnectionPacketClass(final PlayerConnection b, final Packet a) {
        this.b = b;
        this.a = a;
    }
    
    public String a() {
        return this.a.getClass().getCanonicalName();
    }
}
