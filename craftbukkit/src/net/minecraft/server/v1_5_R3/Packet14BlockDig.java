// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet14BlockDig extends Packet
{
    public int a;
    public int b;
    public int c;
    public int face;
    public int e;
    
    public void a(final DataInputStream dataInputStream) {
        this.e = dataInputStream.read();
        this.a = dataInputStream.readInt();
        this.b = dataInputStream.read();
        this.c = dataInputStream.readInt();
        this.face = dataInputStream.read();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.write(this.e);
        dataOutputStream.writeInt(this.a);
        dataOutputStream.write(this.b);
        dataOutputStream.writeInt(this.c);
        dataOutputStream.write(this.face);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 11;
    }
}
