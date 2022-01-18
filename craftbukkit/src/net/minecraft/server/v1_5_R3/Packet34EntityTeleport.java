// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet34EntityTeleport extends Packet
{
    public int a;
    public int b;
    public int c;
    public int d;
    public byte e;
    public byte f;
    
    public Packet34EntityTeleport() {
    }
    
    public Packet34EntityTeleport(final Entity entity) {
        this.a = entity.id;
        this.b = MathHelper.floor(entity.locX * 32.0);
        this.c = MathHelper.floor(entity.locY * 32.0);
        this.d = MathHelper.floor(entity.locZ * 32.0);
        this.e = (byte)(entity.yaw * 256.0f / 360.0f);
        this.f = (byte)(entity.pitch * 256.0f / 360.0f);
    }
    
    public Packet34EntityTeleport(final int a, final int b, final int c, final int d, final byte e, final byte f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.b = dataInputStream.readInt();
        this.c = dataInputStream.readInt();
        this.d = dataInputStream.readInt();
        this.e = (byte)dataInputStream.read();
        this.f = (byte)dataInputStream.read();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeInt(this.b);
        dataOutputStream.writeInt(this.c);
        dataOutputStream.writeInt(this.d);
        dataOutputStream.write(this.e);
        dataOutputStream.write(this.f);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 34;
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        return ((Packet34EntityTeleport)packet).a == this.a;
    }
}
