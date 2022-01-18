// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportPlayers implements Callable
{
    final /* synthetic */ World a;
    
    CrashReportPlayers(final World a) {
        this.a = a;
    }
    
    public String a() {
        return this.a.players.size() + " total; " + this.a.players.toString();
    }
}
