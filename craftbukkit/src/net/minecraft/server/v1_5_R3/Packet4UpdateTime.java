// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet4UpdateTime extends Packet
{
    public long a;
    public long b;
    
    public Packet4UpdateTime() {
    }
    
    public Packet4UpdateTime(final long a, final long b) {
        this.a = a;
        this.b = b;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readLong();
        this.b = dataInputStream.readLong();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeLong(this.a);
        dataOutputStream.writeLong(this.b);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 16;
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        return true;
    }
    
    public boolean a_() {
        return true;
    }
}
