// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet200Statistic extends Packet
{
    public int a;
    public int b;
    
    public Packet200Statistic() {
    }
    
    public Packet200Statistic(final int a, final int b) {
        this.a = a;
        this.b = b;
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.b = dataInputStream.readByte();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeByte(this.b);
    }
    
    public int a() {
        return 6;
    }
    
    public boolean a_() {
        return true;
    }
}
