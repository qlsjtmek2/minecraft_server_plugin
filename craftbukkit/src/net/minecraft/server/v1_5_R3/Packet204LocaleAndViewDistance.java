// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet204LocaleAndViewDistance extends Packet
{
    private String a;
    private int b;
    private int c;
    private boolean d;
    private int e;
    private boolean f;
    
    public void a(final DataInputStream datainputstream) {
        this.a = Packet.a(datainputstream, 7);
        this.b = datainputstream.readByte();
        final byte byte1 = datainputstream.readByte();
        this.c = (byte1 & 0x7);
        this.d = ((byte1 & 0x8) == 0x8);
        this.e = datainputstream.readByte();
        this.f = datainputstream.readBoolean();
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        Packet.a(this.a, dataoutputstream);
        dataoutputstream.writeByte(this.b);
        dataoutputstream.writeByte(this.c | (this.d ? 1 : 0) << 3);
        dataoutputstream.writeByte(this.e);
        dataoutputstream.writeBoolean(this.f);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 7;
    }
    
    public String d() {
        return this.a;
    }
    
    public int f() {
        return this.b;
    }
    
    public int g() {
        return this.c;
    }
    
    public boolean h() {
        return this.d;
    }
    
    public int i() {
        return this.e;
    }
    
    public boolean j() {
        return this.f;
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        return true;
    }
}
