// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet35EntityHeadRotation extends Packet
{
    public int a;
    public byte b;
    
    public Packet35EntityHeadRotation() {
    }
    
    public Packet35EntityHeadRotation(final int a, final byte b) {
        this.a = a;
        this.b = b;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.b = dataInputStream.readByte();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeByte(this.b);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 5;
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        return ((Packet35EntityHeadRotation)packet).a == this.a;
    }
    
    public boolean a_() {
        return true;
    }
}
