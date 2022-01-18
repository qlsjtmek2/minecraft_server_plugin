// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.net.Socket;
import java.net.SocketAddress;

public interface INetworkManager
{
    void a(final Connection p0);
    
    void queue(final Packet p0);
    
    void a();
    
    void b();
    
    SocketAddress getSocketAddress();
    
    void d();
    
    int e();
    
    void a(final String p0, final Object... p1);
    
    Socket getSocket();
    
    void setSocketAddress(final SocketAddress p0);
}
