// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportStructureType implements Callable
{
    final /* synthetic */ StructureGenerator a;
    
    CrashReportStructureType(final StructureGenerator a) {
        this.a = a;
    }
    
    public String a() {
        return this.a.getClass().getCanonicalName();
    }
}