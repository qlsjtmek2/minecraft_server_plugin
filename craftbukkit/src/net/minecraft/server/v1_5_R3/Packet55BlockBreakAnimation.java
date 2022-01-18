// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet55BlockBreakAnimation extends Packet
{
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    
    public Packet55BlockBreakAnimation() {
    }
    
    public Packet55BlockBreakAnimation(final int a, final int b, final int c, final int d, final int e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.b = dataInputStream.readInt();
        this.c = dataInputStream.readInt();
        this.d = dataInputStream.readInt();
        this.e = dataInputStream.read();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeInt(this.b);
        dataOutputStream.writeInt(this.c);
        dataOutputStream.writeInt(this.d);
        dataOutputStream.write(this.e);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 13;
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        return ((Packet55BlockBreakAnimation)packet).a == this.a;
    }
}
