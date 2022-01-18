// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet9Respawn extends Packet
{
    public int a;
    public int b;
    public int c;
    public EnumGamemode d;
    public WorldType e;
    
    public Packet9Respawn() {
    }
    
    public Packet9Respawn(final int a, final byte b, final WorldType e, final int c, final EnumGamemode d) {
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
        this.a = datainputstream.readInt();
        this.b = datainputstream.readByte();
        this.d = EnumGamemode.a(datainputstream.readByte());
        this.c = datainputstream.readShort();
        this.e = WorldType.getType(Packet.a(datainputstream, 16));
        if (this.e == null) {
            this.e = WorldType.NORMAL;
        }
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        dataoutputstream.writeInt(this.a);
        dataoutputstream.writeByte(this.b);
        dataoutputstream.writeByte(this.d.a());
        dataoutputstream.writeShort(this.c);
        Packet.a(this.e.name(), dataoutputstream);
    }
    
    public int a() {
        return 8 + ((this.e == null) ? 0 : this.e.name().length());
    }
}
