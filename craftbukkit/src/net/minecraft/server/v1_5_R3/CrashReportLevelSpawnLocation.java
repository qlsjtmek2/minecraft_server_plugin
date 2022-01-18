// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportLevelSpawnLocation implements Callable
{
    final /* synthetic */ WorldData a;
    
    CrashReportLevelSpawnLocation(final WorldData a) {
        this.a = a;
    }
    
    public String a() {
        return CrashReportSystemDetails.a(this.a.spawnX, this.a.spawnY, this.a.spawnZ);
    }
}
