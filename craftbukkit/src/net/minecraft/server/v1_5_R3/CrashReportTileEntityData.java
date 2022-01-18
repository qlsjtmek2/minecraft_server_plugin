// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportTileEntityData implements Callable
{
    final /* synthetic */ TileEntity a;
    
    CrashReportTileEntityData(final TileEntity a) {
        this.a = a;
    }
    
    public String a() {
        final int data = this.a.world.getData(this.a.x, this.a.y, this.a.z);
        if (data < 0) {
            return "Unknown? (Got " + data + ")";
        }
        return String.format("%1$d / 0x%1$X / 0b%2$s", data, String.format("%4s", Integer.toBinaryString(data)).replace(" ", "0"));
    }
}
