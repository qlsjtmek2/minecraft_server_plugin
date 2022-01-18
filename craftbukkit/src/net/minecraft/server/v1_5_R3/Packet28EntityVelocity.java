// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet28EntityVelocity extends Packet
{
    public int a;
    public int b;
    public int c;
    public int d;
    
    public Packet28EntityVelocity() {
    }
    
    public Packet28EntityVelocity(final Entity entity) {
        this(entity.id, entity.motX, entity.motY, entity.motZ);
    }
    
    public Packet28EntityVelocity(final int a, double n, double n2, double n3) {
        this.a = a;
        final double n4 = 3.9;
        if (n < -n4) {
            n = -n4;
        }
        if (n2 < -n4) {
            n2 = -n4;
        }
        if (n3 < -n4) {
            n3 = -n4;
        }
        if (n > n4) {
            n = n4;
        }
        if (n2 > n4) {
            n2 = n4;
        }
        if (n3 > n4) {
            n3 = n4;
        }
        this.b = (int)(n * 8000.0);
        this.c = (int)(n2 * 8000.0);
        this.d = (int)(n3 * 8000.0);
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.b = dataInputStream.readShort();
        this.c = dataInputStream.readShort();
        this.d = dataInputStream.readShort();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeShort(this.b);
        dataOutputStream.writeShort(this.c);
        dataOutputStream.writeShort(this.d);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 10;
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        return ((Packet28EntityVelocity)packet).a == this.a;
    }
}
