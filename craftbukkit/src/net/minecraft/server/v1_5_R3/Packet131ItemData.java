// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet131ItemData extends Packet
{
    public short a;
    public short b;
    public byte[] c;
    
    public Packet131ItemData() {
        this.lowPriority = true;
    }
    
    public Packet131ItemData(final short a, final short b, final byte[] c) {
        this.lowPriority = true;
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readShort();
        this.b = dataInputStream.readShort();
        dataInputStream.readFully(this.c = new byte[dataInputStream.readUnsignedShort()]);
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeShort(this.a);
        dataOutputStream.writeShort(this.b);
        dataOutputStream.writeShort(this.c.length);
        dataOutputStream.write(this.c);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 4 + this.c.length;
    }
}
