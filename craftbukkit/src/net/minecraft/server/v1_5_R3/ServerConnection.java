// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public abstract class ServerConnection
{
    private final MinecraftServer b;
    private final List c;
    public volatile boolean a;
    
    public ServerConnection(final MinecraftServer b) {
        this.c = Collections.synchronizedList(new ArrayList<Object>());
        this.a = false;
        this.b = b;
        this.a = true;
    }
    
    public void a(final PlayerConnection playerConnection) {
        this.c.add(playerConnection);
    }
    
    public void a() {
        this.a = false;
    }
    
    public void b() {
        for (int i = 0; i < this.c.size(); ++i) {
            final PlayerConnection playerConnection = this.c.get(i);
            try {
                playerConnection.d();
            }
            catch (Exception throwable) {
                if (playerConnection.networkManager instanceof MemoryNetworkManager) {
                    throw new ReportedException(CrashReport.a(throwable, "Ticking memory connection"));
                }
                this.b.getLogger().warning("Failed to handle packet for " + playerConnection.player.getLocalizedName() + "/" + playerConnection.player.p() + ": " + throwable, throwable);
                playerConnection.disconnect("Internal server error");
            }
            if (playerConnection.disconnected) {
                this.c.remove(i--);
            }
            playerConnection.networkManager.a();
        }
    }
    
    public MinecraftServer d() {
        return this.b;
    }
}
