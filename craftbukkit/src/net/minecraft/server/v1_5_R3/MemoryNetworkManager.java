// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.net.InetSocketAddress;
import java.util.List;
import java.net.SocketAddress;

public class MemoryNetworkManager implements INetworkManager
{
    private static final SocketAddress a;
    private final List b;
    private final IConsoleLogManager c;
    private MemoryNetworkManager d;
    private Connection e;
    private boolean f;
    private String g;
    private Object[] h;
    
    public void a(final Connection e) {
        this.e = e;
    }
    
    public void queue(final Packet packet) {
        if (this.f) {
            return;
        }
        this.d.b(packet);
    }
    
    public void a() {
    }
    
    public void b() {
        int n = 2500;
        while (n-- >= 0 && !this.b.isEmpty()) {
            this.b.remove(0).handle(this.e);
        }
        if (this.b.size() > n) {
            this.c.warning("Memory connection overburdened; after processing 2500 packets, we still have " + this.b.size() + " to go!");
        }
        if (this.f && this.b.isEmpty()) {
            this.e.a(this.g, this.h);
        }
    }
    
    public SocketAddress getSocketAddress() {
        return MemoryNetworkManager.a;
    }
    
    public void d() {
        this.f = true;
    }
    
    public void a(final String g, final Object... h) {
        this.f = true;
        this.g = g;
        this.h = h;
    }
    
    public int e() {
        return 0;
    }
    
    public void b(final Packet packet) {
        if (packet.a_() && this.e.b()) {
            packet.handle(this.e);
        }
        else {
            this.b.add(packet);
        }
    }
    
    static {
        a = new InetSocketAddress("127.0.0.1", 0);
    }
}
