// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet8UpdateHealth extends Packet
{
    public int a;
    public int b;
    public float c;
    
    public Packet8UpdateHealth() {
    }
    
    public Packet8UpdateHealth(final int a, final int b, final float c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readShort();
        this.b = dataInputStream.readShort();
        this.c = dataInputStream.readFloat();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeShort(this.a);
        dataOutputStream.writeShort(this.b);
        dataOutputStream.writeFloat(this.c);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 8;
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        return true;
    }
}
