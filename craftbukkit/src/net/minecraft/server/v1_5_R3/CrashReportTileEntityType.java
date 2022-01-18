// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportTileEntityType implements Callable
{
    final /* synthetic */ TileEntity a;
    
    CrashReportTileEntityType(final TileEntity a) {
        this.a = a;
    }
    
    public String a() {
        final int typeId = this.a.world.getTypeId(this.a.x, this.a.y, this.a.z);
        try {
            return String.format("ID #%d (%s // %s)", typeId, Block.byId[typeId].a(), Block.byId[typeId].getClass().getCanonicalName());
        }
        catch (Throwable t) {
            return "ID #" + typeId;
        }
    }
}
