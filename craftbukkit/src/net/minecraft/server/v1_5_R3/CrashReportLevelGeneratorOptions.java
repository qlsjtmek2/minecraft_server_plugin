// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportLevelGeneratorOptions implements Callable
{
    final /* synthetic */ WorldData a;
    
    CrashReportLevelGeneratorOptions(final WorldData a) {
        this.a = a;
    }
    
    public String a() {
        return this.a.generatorOptions;
    }
}
