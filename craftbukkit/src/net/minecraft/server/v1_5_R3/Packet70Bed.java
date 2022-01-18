// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet70Bed extends Packet
{
    public static final String[] a;
    public int b;
    public int c;
    
    public Packet70Bed() {
    }
    
    public Packet70Bed(final int b, final int c) {
        this.b = b;
        this.c = c;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.b = dataInputStream.readByte();
        this.c = dataInputStream.readByte();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeByte(this.b);
        dataOutputStream.writeByte(this.c);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 2;
    }
    
    static {
        a = new String[] { "tile.bed.notValid", null, null, "gameMode.changed" };
    }
}
