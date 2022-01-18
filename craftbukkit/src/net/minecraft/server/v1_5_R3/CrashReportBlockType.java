// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

final class CrashReportBlockType implements Callable
{
    final /* synthetic */ int a;
    
    CrashReportBlockType(final int a) {
        this.a = a;
    }
    
    public String a() {
        try {
            return String.format("ID #%d (%s // %s)", this.a, Block.byId[this.a].a(), Block.byId[this.a].getClass().getCanonicalName());
        }
        catch (Throwable t) {
            return "ID #" + this.a;
        }
    }
}
