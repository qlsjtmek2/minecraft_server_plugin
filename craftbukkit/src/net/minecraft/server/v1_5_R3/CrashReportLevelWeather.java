// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportLevelWeather implements Callable
{
    final /* synthetic */ WorldData a;
    
    CrashReportLevelWeather(final WorldData a) {
        this.a = a;
    }
    
    public String a() {
        return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", this.a.rainTicks, this.a.isRaining, this.a.thunderTicks, this.a.isThundering);
    }
}
