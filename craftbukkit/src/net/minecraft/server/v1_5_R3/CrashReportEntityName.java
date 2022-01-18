// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportEntityName implements Callable
{
    final /* synthetic */ Entity a;
    
    CrashReportEntityName(final Entity a) {
        this.a = a;
    }
    
    public String a() {
        return this.a.getLocalizedName();
    }
}
