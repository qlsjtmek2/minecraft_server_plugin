// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.logging.Level;
import java.io.IOException;
import org.bukkit.craftbukkit.v1_5_R3.util.Waitable;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import java.net.InetSocketAddress;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.math.BigInteger;
import org.bukkit.craftbukkit.v1_5_R3.Spigot;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;

class ThreadLoginVerifier extends Thread
{
    final PendingConnection pendingConnection;
    CraftServer server;
    
    ThreadLoginVerifier(final PendingConnection pendingconnection, final CraftServer server) {
        super("Login Verifier - " + pendingconnection.getName());
        this.server = server;
        this.pendingConnection = pendingconnection;
    }
    
    public void run() {
        try {
            if (Spigot.filterIp(this.pendingConnection)) {
                return;
            }
            final String s = new BigInteger(MinecraftEncryption.a(PendingConnection.a(this.pendingConnection), PendingConnection.b(this.pendingConnection).F().getPublic(), PendingConnection.c(this.pendingConnection))).toString(16);
            final URL url = new URL("http://session.minecraft.net/game/checkserver.jsp?user=" + URLEncoder.encode(PendingConnection.d(this.pendingConnection), "UTF-8") + "&serverId=" + URLEncoder.encode(s, "UTF-8"));
            final BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(url.openStream()));
            final String s2 = bufferedreader.readLine();
            bufferedreader.close();
            if (!"YES".equals(s2)) {
                this.pendingConnection.disconnect("Failed to verify username!");
                return;
            }
            if (this.pendingConnection.getSocket() == null) {
                return;
            }
            final AsyncPlayerPreLoginEvent asyncEvent = new AsyncPlayerPreLoginEvent(PendingConnection.d(this.pendingConnection), ((InetSocketAddress)this.pendingConnection.networkManager.getSocketAddress()).getAddress());
            this.server.getPluginManager().callEvent(asyncEvent);
            if (PlayerPreLoginEvent.getHandlerList().getRegisteredListeners().length != 0) {
                final PlayerPreLoginEvent event = new PlayerPreLoginEvent(PendingConnection.d(this.pendingConnection), ((InetSocketAddress)this.pendingConnection.networkManager.getSocketAddress()).getAddress());
                if (asyncEvent.getResult() != PlayerPreLoginEvent.Result.ALLOWED) {
                    event.disallow(asyncEvent.getResult(), asyncEvent.getKickMessage());
                }
                final Waitable<PlayerPreLoginEvent.Result> waitable = new Waitable<PlayerPreLoginEvent.Result>() {
                    protected PlayerPreLoginEvent.Result evaluate() {
                        ThreadLoginVerifier.this.server.getPluginManager().callEvent(event);
                        return event.getResult();
                    }
                };
                PendingConnection.b(this.pendingConnection).processQueue.add(waitable);
                if (waitable.get() != PlayerPreLoginEvent.Result.ALLOWED) {
                    this.pendingConnection.disconnect(event.getKickMessage());
                    return;
                }
            }
            else if (asyncEvent.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
                this.pendingConnection.disconnect(asyncEvent.getKickMessage());
                return;
            }
            PendingConnection.a(this.pendingConnection, true);
        }
        catch (IOException exception2) {
            this.pendingConnection.disconnect("Failed to verify username, session authentication server unavailable!");
        }
        catch (Exception exception) {
            this.pendingConnection.disconnect("Failed to verify username!");
            this.server.getLogger().log(Level.WARNING, "Exception verifying " + PendingConnection.d(this.pendingConnection), exception);
        }
    }
}
