// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet61WorldEvent extends Packet
{
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    private boolean f;
    
    public Packet61WorldEvent() {
    }
    
    public Packet61WorldEvent(final int a, final int c, final int d, final int e, final int b, final boolean f) {
        this.a = a;
        this.c = c;
        this.d = d;
        this.e = e;
        this.b = b;
        this.f = f;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.c = dataInputStream.readInt();
        this.d = (dataInputStream.readByte() & 0xFF);
        this.e = dataInputStream.readInt();
        this.b = dataInputStream.readInt();
        this.f = dataInputStream.readBoolean();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeInt(this.c);
        dataOutputStream.writeByte(this.d & 0xFF);
        dataOutputStream.writeInt(this.e);
        dataOutputStream.writeInt(this.b);
        dataOutputStream.writeBoolean(this.f);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 21;
    }
}
