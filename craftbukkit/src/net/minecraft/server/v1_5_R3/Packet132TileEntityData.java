// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet132TileEntityData extends Packet
{
    public int a;
    public int b;
    public int c;
    public int d;
    public NBTTagCompound e;
    
    public Packet132TileEntityData() {
        this.lowPriority = true;
    }
    
    public Packet132TileEntityData(final int a, final int b, final int c, final int d, final NBTTagCompound e) {
        this.lowPriority = true;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }
    
    public void a(final DataInputStream datainputstream) {
        this.a = datainputstream.readInt();
        this.b = datainputstream.readShort();
        this.c = datainputstream.readInt();
        this.d = datainputstream.readByte();
        this.e = Packet.d(datainputstream);
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        dataoutputstream.writeInt(this.a);
        dataoutputstream.writeShort(this.b);
        dataoutputstream.writeInt(this.c);
        dataoutputstream.writeByte((byte)this.d);
        Packet.a(this.e, dataoutputstream);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 25;
    }
}
