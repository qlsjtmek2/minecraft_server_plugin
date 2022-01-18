// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet106Transaction extends Packet
{
    public int a;
    public short b;
    public boolean c;
    
    public Packet106Transaction() {
    }
    
    public Packet106Transaction(final int a, final short b, final boolean c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readByte();
        this.b = dataInputStream.readShort();
        this.c = (dataInputStream.readByte() != 0);
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeByte(this.a);
        dataOutputStream.writeShort(this.b);
        dataOutputStream.writeByte(this.c ? 1 : 0);
    }
    
    public int a() {
        return 4;
    }
}
