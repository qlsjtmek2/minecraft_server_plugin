// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet201PlayerInfo extends Packet
{
    public String a;
    public boolean b;
    public int c;
    
    public Packet201PlayerInfo() {
    }
    
    public Packet201PlayerInfo(final String a, final boolean b, final int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public void a(final DataInputStream datainputstream) {
        this.a = Packet.a(datainputstream, 16);
        this.b = (datainputstream.readByte() != 0);
        this.c = datainputstream.readShort();
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        Packet.a(this.a, dataoutputstream);
        dataoutputstream.writeByte(this.b ? 1 : 0);
        dataoutputstream.writeShort(this.c);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return this.a.length() + 2 + 1 + 2;
    }
}
