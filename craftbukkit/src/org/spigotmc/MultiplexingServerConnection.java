// 
// Decompiled by Procyon v0.5.30
// 

package org.spigotmc;

import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.spigotmc.netty.NettyServerConnection;
import net.minecraft.server.v1_5_R3.DedicatedServerConnection;
import java.util.Iterator;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashSet;
import net.minecraft.server.v1_5_R3.MinecraftServer;
import java.net.InetAddress;
import java.util.HashMap;
import net.minecraft.server.v1_5_R3.PendingConnection;
import java.util.List;
import java.util.Collection;
import net.minecraft.server.v1_5_R3.ServerConnection;

public class MultiplexingServerConnection extends ServerConnection
{
    private static final boolean NETTY_DISABLED;
    private final Collection<ServerConnection> children;
    private final List<PendingConnection> pending;
    private final HashMap<InetAddress, Long> throttle;
    
    public MultiplexingServerConnection(final MinecraftServer ms) {
        super(ms);
        this.children = new HashSet<ServerConnection>();
        this.pending = Collections.synchronizedList(new ArrayList<PendingConnection>());
        this.throttle = new HashMap<InetAddress, Long>();
        this.start(ms.server.getIp(), ms.server.getPort());
        for (final InetSocketAddress address : ms.server.getSecondaryHosts()) {
            this.start(address.getAddress().getHostAddress(), address.getPort());
        }
    }
    
    private void start(final String ipAddress, final int port) {
        try {
            final InetAddress socketAddress = (ipAddress.length() == 0) ? null : InetAddress.getByName(ipAddress);
            this.d().getLogger().info("Starting listener #" + this.children.size() + " on " + ((socketAddress == null) ? "*" : ipAddress) + ":" + port);
            final ServerConnection listener = MultiplexingServerConnection.NETTY_DISABLED ? new DedicatedServerConnection(this.d(), socketAddress, port) : new NettyServerConnection(this.d(), socketAddress, port);
            this.children.add(listener);
        }
        catch (Throwable t) {
            t.printStackTrace();
            this.d().getLogger().warning("**** FAILED TO BIND TO PORT!");
            this.d().getLogger().warning("The exception was: {0}", t);
            this.d().getLogger().warning("Perhaps a server is already running on that port?");
        }
    }
    
    public void a() {
        for (final ServerConnection child : this.children) {
            child.a();
        }
    }
    
    public void b() {
        super.b();
        for (int i = 0; i < this.pending.size(); ++i) {
            final PendingConnection connection = this.pending.get(i);
            try {
                connection.c();
            }
            catch (Exception ex) {
                connection.disconnect("Internal server error");
                Bukkit.getServer().getLogger().log(Level.WARNING, "Failed to handle packet: " + ex, ex);
            }
            if (connection.b) {
                this.pending.remove(i--);
            }
        }
    }
    
    public void unThrottle(final InetAddress address) {
        if (address != null) {
            synchronized (this.throttle) {
                this.throttle.remove(address);
            }
        }
    }
    
    public boolean throttle(final InetAddress address) {
        final long currentTime = System.currentTimeMillis();
        synchronized (this.throttle) {
            final Long value = this.throttle.get(address);
            if (value != null && !address.isLoopbackAddress() && currentTime - value < this.d().server.getConnectionThrottle()) {
                this.throttle.put(address, currentTime);
                return true;
            }
            this.throttle.put(address, currentTime);
        }
        return false;
    }
    
    public void register(final PendingConnection conn) {
        this.pending.add(conn);
    }
    
    static {
        NETTY_DISABLED = Boolean.getBoolean("org.spigotmc.netty.disabled");
    }
}
