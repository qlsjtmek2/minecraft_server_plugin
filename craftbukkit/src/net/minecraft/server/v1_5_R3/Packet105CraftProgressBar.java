// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet105CraftProgressBar extends Packet
{
    public int a;
    public int b;
    public int c;
    
    public Packet105CraftProgressBar() {
    }
    
    public Packet105CraftProgressBar(final int a, final int b, final int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readByte();
        this.b = dataInputStream.readShort();
        this.c = dataInputStream.readShort();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeByte(this.a);
        dataOutputStream.writeShort(this.b);
        dataOutputStream.writeShort(this.c);
    }
    
    public int a() {
        return 5;
    }
}
