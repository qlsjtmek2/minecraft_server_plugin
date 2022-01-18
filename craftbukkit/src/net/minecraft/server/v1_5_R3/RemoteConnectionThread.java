// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

public abstract class RemoteConnectionThread implements Runnable
{
    protected boolean running;
    protected IMinecraftServer server;
    protected Thread thread;
    protected int d;
    protected List e;
    protected List f;
    
    RemoteConnectionThread(final IMinecraftServer server) {
        this.running = false;
        this.d = 5;
        this.e = new ArrayList();
        this.f = new ArrayList();
        this.server = server;
        if (this.server.isDebugging()) {
            this.warning("Debugging is enabled, performance maybe reduced!");
        }
    }
    
    public synchronized void a() {
        (this.thread = new Thread(this)).start();
        this.running = true;
    }
    
    public boolean c() {
        return this.running;
    }
    
    protected void debug(final String s) {
        this.server.j(s);
    }
    
    protected void info(final String s) {
        this.server.info(s);
    }
    
    protected void warning(final String s) {
        this.server.warning(s);
    }
    
    protected void error(final String s) {
        this.server.i(s);
    }
    
    protected int d() {
        return this.server.y();
    }
    
    protected void a(final DatagramSocket datagramSocket) {
        this.debug("registerSocket: " + datagramSocket);
        this.e.add(datagramSocket);
    }
    
    protected boolean a(final DatagramSocket datagramSocket, final boolean b) {
        this.debug("closeSocket: " + datagramSocket);
        if (null == datagramSocket) {
            return false;
        }
        boolean b2 = false;
        if (!datagramSocket.isClosed()) {
            datagramSocket.close();
            b2 = true;
        }
        if (b) {
            this.e.remove(datagramSocket);
        }
        return b2;
    }
    
    protected boolean b(final ServerSocket serverSocket) {
        return this.a(serverSocket, true);
    }
    
    protected boolean a(final ServerSocket serverSocket, final boolean b) {
        this.debug("closeSocket: " + serverSocket);
        if (null == serverSocket) {
            return false;
        }
        boolean b2 = false;
        try {
            if (!serverSocket.isClosed()) {
                serverSocket.close();
                b2 = true;
            }
        }
        catch (IOException ex) {
            this.warning("IO: " + ex.getMessage());
        }
        if (b) {
            this.f.remove(serverSocket);
        }
        return b2;
    }
    
    protected void e() {
        this.a(false);
    }
    
    protected void a(final boolean b) {
        int n = 0;
        final Iterator<DatagramSocket> iterator = this.e.iterator();
        while (iterator.hasNext()) {
            if (this.a(iterator.next(), false)) {
                ++n;
            }
        }
        this.e.clear();
        final Iterator<ServerSocket> iterator2 = this.f.iterator();
        while (iterator2.hasNext()) {
            if (this.a(iterator2.next(), false)) {
                ++n;
            }
        }
        this.f.clear();
        if (b && 0 < n) {
            this.warning("Force closed " + n + " sockets");
        }
    }
}
