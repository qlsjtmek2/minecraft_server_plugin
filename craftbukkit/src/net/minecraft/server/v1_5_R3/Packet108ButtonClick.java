// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet108ButtonClick extends Packet
{
    public int a;
    public int b;
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readByte();
        this.b = dataInputStream.readByte();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeByte(this.a);
        dataOutputStream.writeByte(this.b);
    }
    
    public int a() {
        return 2;
    }
}
