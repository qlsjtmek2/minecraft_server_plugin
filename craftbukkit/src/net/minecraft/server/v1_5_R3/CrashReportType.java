// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportType implements Callable
{
    final /* synthetic */ DedicatedServer a;
    
    CrashReportType(final DedicatedServer a) {
        this.a = a;
    }
    
    public String a() {
        return "Dedicated Server (map_server.txt)";
    }
}
