// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet203TabComplete extends Packet
{
    private String a;
    
    public Packet203TabComplete() {
    }
    
    public Packet203TabComplete(final String a) {
        this.a = a;
    }
    
    public void a(final DataInputStream datainputstream) {
        this.a = Packet.a(datainputstream, Packet3Chat.a);
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        Packet.a(this.a, dataoutputstream);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 2 + this.a.length() * 2;
    }
    
    public String d() {
        return this.a;
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        return true;
    }
}
