// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet17EntityLocationAction extends Packet
{
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    
    public Packet17EntityLocationAction() {
    }
    
    public Packet17EntityLocationAction(final Entity entity, final int e, final int b, final int c, final int d) {
        this.e = e;
        this.b = b;
        this.c = c;
        this.d = d;
        this.a = entity.id;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.e = dataInputStream.readByte();
        this.b = dataInputStream.readInt();
        this.c = dataInputStream.readByte();
        this.d = dataInputStream.readInt();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeByte(this.e);
        dataOutputStream.writeInt(this.b);
        dataOutputStream.writeByte(this.c);
        dataOutputStream.writeInt(this.d);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 14;
    }
}
