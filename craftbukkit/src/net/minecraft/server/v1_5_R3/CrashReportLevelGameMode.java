// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

class CrashReportLevelGameMode implements Callable
{
    final /* synthetic */ WorldData a;
    
    CrashReportLevelGameMode(final WorldData a) {
        this.a = a;
    }
    
    public String a() {
        return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", this.a.gameType.b(), this.a.gameType.a(), this.a.hardcore, this.a.allowCommands);
    }
}
