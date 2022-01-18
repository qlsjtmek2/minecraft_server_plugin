// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet54PlayNoteBlock extends Packet
{
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    
    public Packet54PlayNoteBlock() {
    }
    
    public Packet54PlayNoteBlock(final int a, final int b, final int c, final int f, final int d, final int e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.b = dataInputStream.readShort();
        this.c = dataInputStream.readInt();
        this.d = dataInputStream.read();
        this.e = dataInputStream.read();
        this.f = (dataInputStream.readShort() & 0xFFF);
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeShort(this.b);
        dataOutputStream.writeInt(this.c);
        dataOutputStream.write(this.d);
        dataOutputStream.write(this.e);
        dataOutputStream.writeShort(this.f & 0xFFF);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 14;
    }
}
