// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportEntityType implements Callable
{
    final /* synthetic */ Entity a;
    
    CrashReportEntityType(final Entity a) {
        this.a = a;
    }
    
    public String a() {
        return EntityTypes.b(this.a) + " (" + this.a.getClass().getCanonicalName() + ")";
    }
}
