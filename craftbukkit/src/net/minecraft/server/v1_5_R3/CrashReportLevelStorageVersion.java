// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportLevelStorageVersion implements Callable
{
    final /* synthetic */ WorldData a;
    
    CrashReportLevelStorageVersion(final WorldData a) {
        this.a = a;
    }
    
    public String a() {
        String s = "Unknown?";
        try {
            switch (this.a.version) {
                case 19133: {
                    s = "Anvil";
                    break;
                }
                case 19132: {
                    s = "McRegion";
                    break;
                }
            }
        }
        catch (Throwable t) {}
        return String.format("0x%05X - %s", this.a.version, s);
    }
}
