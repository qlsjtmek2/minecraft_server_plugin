// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportEffectDuration implements Callable
{
    final /* synthetic */ MobEffect a;
    final /* synthetic */ EntityLiving b;
    
    CrashReportEffectDuration(final EntityLiving b, final MobEffect a) {
        this.b = b;
        this.a = a;
    }
    
    public String a() {
        return this.a.getDuration() + "";
    }
}
