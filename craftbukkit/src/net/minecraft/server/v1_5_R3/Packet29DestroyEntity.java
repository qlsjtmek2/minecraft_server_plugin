// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet29DestroyEntity extends Packet
{
    public int[] a;
    
    public Packet29DestroyEntity() {
    }
    
    public Packet29DestroyEntity(final int... a) {
        this.a = a;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = new int[dataInputStream.readByte()];
        for (int i = 0; i < this.a.length; ++i) {
            this.a[i] = dataInputStream.readInt();
        }
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeByte(this.a.length);
        for (int i = 0; i < this.a.length; ++i) {
            dataOutputStream.writeInt(this.a[i]);
        }
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 1 + this.a.length * 4;
    }
}
