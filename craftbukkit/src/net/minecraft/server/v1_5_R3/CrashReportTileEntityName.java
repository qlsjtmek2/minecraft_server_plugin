// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportTileEntityName implements Callable
{
    final /* synthetic */ TileEntity a;
    
    CrashReportTileEntityName(final TileEntity a) {
        this.a = a;
    }
    
    public String a() {
        return TileEntity.t().get(this.a.getClass()) + " // " + this.a.getClass().getCanonicalName();
    }
}
