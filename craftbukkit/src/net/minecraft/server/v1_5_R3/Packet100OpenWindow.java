// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet100OpenWindow extends Packet
{
    public int a;
    public int b;
    public String c;
    public int d;
    public boolean e;
    
    public Packet100OpenWindow() {
    }
    
    public Packet100OpenWindow(final int a, final int b, final String c, final int d, final boolean e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public void a(final DataInputStream datainputstream) {
        this.a = (datainputstream.readByte() & 0xFF);
        this.b = (datainputstream.readByte() & 0xFF);
        this.c = Packet.a(datainputstream, 32);
        this.d = (datainputstream.readByte() & 0xFF);
        this.e = datainputstream.readBoolean();
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        dataoutputstream.writeByte(this.a & 0xFF);
        dataoutputstream.writeByte(this.b & 0xFF);
        Packet.a(this.c, dataoutputstream);
        dataoutputstream.writeByte(this.d & 0xFF);
        dataoutputstream.writeBoolean(this.e);
    }
    
    public int a() {
        return 4 + this.c.length();
    }
}
