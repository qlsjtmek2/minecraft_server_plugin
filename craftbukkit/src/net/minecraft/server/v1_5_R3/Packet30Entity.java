// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet30Entity extends Packet
{
    public int a;
    public byte b;
    public byte c;
    public byte d;
    public byte e;
    public byte f;
    public boolean g;
    
    public Packet30Entity() {
        this.g = false;
    }
    
    public Packet30Entity(final int a) {
        this.g = false;
        this.a = a;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 4;
    }
    
    public String toString() {
        return "Entity_" + super.toString();
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        return ((Packet30Entity)packet).a == this.a;
    }
}
