// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.net.ServerSocket;

public class RemoteControlListener extends RemoteConnectionThread
{
    private int g;
    private int h;
    private String i;
    private ServerSocket j;
    private String k;
    private Map l;
    
    public RemoteControlListener(final IMinecraftServer minecraftServer) {
        super(minecraftServer);
        this.j = null;
        this.g = minecraftServer.a("rcon.port", 0);
        this.k = minecraftServer.a("rcon.password", "");
        this.i = minecraftServer.u();
        this.h = minecraftServer.v();
        if (0 == this.g) {
            this.g = this.h + 10;
            this.info("Setting default rcon port to " + this.g);
            minecraftServer.a("rcon.port", (Object)this.g);
            if (0 == this.k.length()) {
                minecraftServer.a("rcon.password", (Object)"");
            }
            minecraftServer.a();
        }
        if (0 == this.i.length()) {
            this.i = "0.0.0.0";
        }
        this.f();
        this.j = null;
    }
    
    private void f() {
        this.l = new HashMap();
    }
    
    private void g() {
        final Iterator<Map.Entry<K, RemoteControlSession>> iterator = this.l.entrySet().iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getValue().c()) {
                iterator.remove();
            }
        }
    }
    
    public void run() {
        this.info("RCON running on " + this.i + ":" + this.g);
        try {
            while (this.running) {
                try {
                    final Socket accept = this.j.accept();
                    accept.setSoTimeout(500);
                    final RemoteControlSession remoteControlSession = new RemoteControlSession(this.server, accept);
                    remoteControlSession.a();
                    this.l.put(accept.getRemoteSocketAddress(), remoteControlSession);
                    this.g();
                }
                catch (SocketTimeoutException ex2) {
                    this.g();
                }
                catch (IOException ex) {
                    if (!this.running) {
                        continue;
                    }
                    this.info("IO: " + ex.getMessage());
                }
            }
        }
        finally {
            this.b(this.j);
        }
    }
    
    public void a() {
        if (0 == this.k.length()) {
            this.warning("No rcon password set in '" + this.server.b_() + "', rcon disabled!");
            return;
        }
        if (0 >= this.g || 65535 < this.g) {
            this.warning("Invalid rcon port " + this.g + " found in '" + this.server.b_() + "', rcon disabled!");
            return;
        }
        if (this.running) {
            return;
        }
        try {
            (this.j = new ServerSocket(this.g, 0, InetAddress.getByName(this.i))).setSoTimeout(500);
            super.a();
        }
        catch (IOException ex) {
            this.warning("Unable to initialise rcon on " + this.i + ":" + this.g + " : " + ex.getMessage());
        }
    }
}
