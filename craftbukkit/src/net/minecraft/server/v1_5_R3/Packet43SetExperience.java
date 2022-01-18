// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet43SetExperience extends Packet
{
    public float a;
    public int b;
    public int c;
    
    public Packet43SetExperience() {
    }
    
    public Packet43SetExperience(final float a, final int b, final int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readFloat();
        this.c = dataInputStream.readShort();
        this.b = dataInputStream.readShort();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeFloat(this.a);
        dataOutputStream.writeShort(this.c);
        dataOutputStream.writeShort(this.b);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 4;
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        return true;
    }
}
