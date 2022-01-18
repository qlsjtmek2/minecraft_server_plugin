// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet0KeepAlive extends Packet
{
    public int a;
    
    public Packet0KeepAlive() {
    }
    
    public Packet0KeepAlive(final int a) {
        this.a = a;
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
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
    
    public boolean a_() {
        return true;
    }
}
