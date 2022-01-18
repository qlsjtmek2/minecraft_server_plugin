// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportCorruptNBTTag2 implements Callable
{
    final /* synthetic */ int a;
    final /* synthetic */ NBTTagCompound b;
    
    CrashReportCorruptNBTTag2(final NBTTagCompound b, final int a) {
        this.b = b;
        this.a = a;
    }
    
    public String a() {
        return NBTBase.b[this.a];
    }
}
