// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet255KickDisconnect extends Packet
{
    public String a;
    
    public Packet255KickDisconnect() {
    }
    
    public Packet255KickDisconnect(final String a) {
        this.a = a;
    }
    
    public void a(final DataInputStream datainputstream) {
        this.a = Packet.a(datainputstream, 256);
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        Packet.a(this.a, dataoutputstream);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return this.a.length();
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        return true;
    }
}
