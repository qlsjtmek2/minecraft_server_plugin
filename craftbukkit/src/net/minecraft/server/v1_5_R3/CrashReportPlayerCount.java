// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

public class CrashReportPlayerCount implements Callable
{
    final /* synthetic */ MinecraftServer a;
    
    public CrashReportPlayerCount(final MinecraftServer a) {
        this.a = a;
    }
    
    public String a() {
        return MinecraftServer.a(this.a).getPlayerCount() + " / " + MinecraftServer.a(this.a).getMaxPlayers() + "; " + MinecraftServer.a(this.a).players;
    }
}
