// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.net.InetAddress;

public class DedicatedServerConnection extends ServerConnection
{
    private final DedicatedServerConnectionThread b;
    
    public DedicatedServerConnection(final MinecraftServer minecraftServer, final InetAddress inetaddress, final int i) {
        super(minecraftServer);
        (this.b = new DedicatedServerConnectionThread(this, inetaddress, i)).start();
    }
    
    public void a() {
        super.a();
        this.b.b();
        this.b.interrupt();
    }
    
    public void b() {
        this.b.a();
        super.b();
    }
    
    public DedicatedServer c() {
        return (DedicatedServer)super.d();
    }
    
    public void a(final InetAddress inetaddress) {
        this.b.a(inetaddress);
    }
}
