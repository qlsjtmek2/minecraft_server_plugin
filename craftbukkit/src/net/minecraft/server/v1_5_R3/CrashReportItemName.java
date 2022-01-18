// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportItemName implements Callable
{
    final /* synthetic */ ItemStack a;
    final /* synthetic */ PlayerInventory b;
    
    CrashReportItemName(final PlayerInventory b, final ItemStack a) {
        this.b = b;
        this.a = a;
    }
    
    public String a() {
        return this.a.getName();
    }
}
