// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportLevelGenerator implements Callable
{
    final /* synthetic */ WorldData a;
    
    CrashReportLevelGenerator(final WorldData a) {
        this.a = a;
    }
    
    public String a() {
        return String.format("ID %02d - %s, ver %d. Features enabled: %b", this.a.type.f(), this.a.type.name(), this.a.type.getVersion(), this.a.useMapFeatures);
    }
}
