// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportEffectAmplifier implements Callable
{
    final /* synthetic */ MobEffect a;
    final /* synthetic */ EntityLiving b;
    
    CrashReportEffectAmplifier(final EntityLiving b, final MobEffect a) {
        this.b = b;
        this.a = a;
    }
    
    public String a() {
        return this.a.getAmplifier() + "";
    }
}
