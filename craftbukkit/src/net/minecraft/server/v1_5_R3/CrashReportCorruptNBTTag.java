// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Collection;
import java.io.DataInput;
import java.util.Iterator;
import java.io.DataOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

class CrashReportCorruptNBTTag implements Callable
{
    final /* synthetic */ String a;
    final /* synthetic */ NBTTagCompound b;
    
    CrashReportCorruptNBTTag(final NBTTagCompound b, final String a) {
        this.b = b;
        this.a = a;
    }
    
    public String a() {
        return NBTBase.b[this.b.map.get(this.a).getTypeId()];
    }
}
