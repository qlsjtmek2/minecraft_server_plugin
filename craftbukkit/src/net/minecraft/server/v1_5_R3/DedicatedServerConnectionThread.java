// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.net.Socket;
import org.spigotmc.MultiplexingServerConnection;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.List;

public class DedicatedServerConnectionThread extends Thread
{
    private final List a;
    private final HashMap b;
    private int c;
    private final ServerSocket d;
    private ServerConnection e;
    private final InetAddress f;
    private final int g;
    long connectionThrottle;
    
    public DedicatedServerConnectionThread(final ServerConnection serverconnection, final InetAddress inetaddress, final int i) throws IOException {
        super("Listen thread");
        this.a = Collections.synchronizedList(new ArrayList<Object>());
        this.b = new HashMap();
        this.c = 0;
        this.e = serverconnection;
        this.g = i;
        this.d = new ServerSocket(i, 0, inetaddress);
        this.f = ((inetaddress == null) ? this.d.getInetAddress() : inetaddress);
        this.d.setPerformancePreferences(0, 2, 1);
    }
    
    public void a() {
        final List list = this.a;
        synchronized (this.a) {
            for (int i = 0; i < this.a.size(); ++i) {
                final PendingConnection pendingconnection = this.a.get(i);
                try {
                    pendingconnection.c();
                }
                catch (Exception exception) {
                    pendingconnection.disconnect("Internal server error");
                    this.e.d().getLogger().warning("Failed to handle packet for " + pendingconnection.getName() + ": " + exception, exception);
                }
                if (pendingconnection.b) {
                    this.a.remove(i--);
                }
                pendingconnection.networkManager.a();
            }
        }
    }
    
    public void run() {
        while (this.e.a) {
            try {
                final Socket socket = this.d.accept();
                final InetAddress address = socket.getInetAddress();
                final long currentTime = System.currentTimeMillis();
                if (this.e.d().server == null) {
                    socket.close();
                }
                else {
                    this.connectionThrottle = this.e.d().server.getConnectionThrottle();
                    synchronized (this.b) {
                        if (this.b.containsKey(address) && !"127.0.0.1".equals(address.getHostAddress()) && currentTime - this.b.get(address) < this.connectionThrottle) {
                            this.b.put(address, currentTime);
                            socket.close();
                            continue;
                        }
                        this.b.put(address, currentTime);
                    }
                    final PendingConnection pendingconnection = new PendingConnection(this.e.d(), socket, "Connection #" + this.c++);
                    ((MultiplexingServerConnection)this.e.d().ae()).register(pendingconnection);
                }
            }
            catch (IOException ioexception) {
                this.e.d().getLogger().warning("DSCT: " + ioexception.getMessage());
            }
        }
        this.e.d().getLogger().info("Closing listening thread");
    }
    
    private void a(final PendingConnection pendingconnection) {
        if (pendingconnection == null) {
            throw new IllegalArgumentException("Got null pendingconnection!");
        }
        final List list = this.a;
        synchronized (this.a) {
            this.a.add(pendingconnection);
        }
    }
    
    public void a(final InetAddress inetaddress) {
        if (inetaddress != null) {
            final HashMap hashmap = this.b;
            synchronized (this.b) {
                this.b.remove(inetaddress);
            }
        }
    }
    
    public void b() {
        try {
            this.d.close();
        }
        catch (Throwable t) {}
    }
}
