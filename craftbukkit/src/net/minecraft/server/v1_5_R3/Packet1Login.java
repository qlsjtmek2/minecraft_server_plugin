// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet1Login extends Packet
{
    public int a;
    public WorldType b;
    public boolean c;
    public EnumGamemode d;
    public int e;
    public byte f;
    public byte g;
    public byte h;
    
    public Packet1Login() {
        this.a = 0;
    }
    
    public Packet1Login(final int a, final WorldType b, final EnumGamemode d, final boolean c, final int e, final int n, final int n2, final int n3) {
        this.a = 0;
        this.a = a;
        this.b = b;
        this.e = e;
        this.f = (byte)n;
        this.d = d;
        this.g = (byte)n2;
        this.h = (byte)n3;
        this.c = c;
    }
    
    public void a(final DataInputStream datainputstream) {
        this.a = datainputstream.readInt();
        this.b = WorldType.getType(Packet.a(datainputstream, 16));
        if (this.b == null) {
            this.b = WorldType.NORMAL;
        }
        final byte byte1 = datainputstream.readByte();
        this.c = ((byte1 & 0x8) == 0x8);
        this.d = EnumGamemode.a(byte1 & 0xFFFFFFF7);
        this.e = datainputstream.readByte();
        this.f = datainputstream.readByte();
        this.g = datainputstream.readByte();
        this.h = datainputstream.readByte();
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        dataoutputstream.writeInt(this.a);
        Packet.a((this.b == null) ? "" : this.b.name(), dataoutputstream);
        int a = this.d.a();
        if (this.c) {
            a |= 0x8;
        }
        dataoutputstream.writeByte(a);
        dataoutputstream.writeByte(this.e);
        dataoutputstream.writeByte(this.f);
        dataoutputstream.writeByte(this.g);
        dataoutputstream.writeByte(this.h);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        int length = 0;
        if (this.b != null) {
            length = this.b.name().length();
        }
        return 6 + 2 * length + 4 + 4 + 1 + 1 + 1;
    }
}
