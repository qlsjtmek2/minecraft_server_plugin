// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

final class CrashReportBlockDataValue implements Callable
{
    final /* synthetic */ int a;
    
    CrashReportBlockDataValue(final int a) {
        this.a = a;
    }
    
    public String a() {
        if (this.a < 0) {
            return "Unknown? (Got " + this.a + ")";
        }
        return String.format("%1$d / 0x%1$X / 0b%2$s", this.a, String.format("%4s", Integer.toBinaryString(this.a)).replace(" ", "0"));
    }
}
