// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet23VehicleSpawn extends Packet
{
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;
    public int j;
    public int k;
    
    public Packet23VehicleSpawn() {
    }
    
    public Packet23VehicleSpawn(final Entity entity, final int n) {
        this(entity, n, 0);
    }
    
    public Packet23VehicleSpawn(final Entity entity, final int j, final int k) {
        this.a = entity.id;
        this.b = MathHelper.floor(entity.locX * 32.0);
        this.c = MathHelper.floor(entity.locY * 32.0);
        this.d = MathHelper.floor(entity.locZ * 32.0);
        this.h = MathHelper.d(entity.pitch * 256.0f / 360.0f);
        this.i = MathHelper.d(entity.yaw * 256.0f / 360.0f);
        this.j = j;
        this.k = k;
        if (k > 0) {
            double motX = entity.motX;
            double motY = entity.motY;
            double motZ = entity.motZ;
            final double n = 3.9;
            if (motX < -n) {
                motX = -n;
            }
            if (motY < -n) {
                motY = -n;
            }
            if (motZ < -n) {
                motZ = -n;
            }
            if (motX > n) {
                motX = n;
            }
            if (motY > n) {
                motY = n;
            }
            if (motZ > n) {
                motZ = n;
            }
            this.e = (int)(motX * 8000.0);
            this.f = (int)(motY * 8000.0);
            this.g = (int)(motZ * 8000.0);
        }
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.j = dataInputStream.readByte();
        this.b = dataInputStream.readInt();
        this.c = dataInputStream.readInt();
        this.d = dataInputStream.readInt();
        this.h = dataInputStream.readByte();
        this.i = dataInputStream.readByte();
        this.k = dataInputStream.readInt();
        if (this.k > 0) {
            this.e = dataInputStream.readShort();
            this.f = dataInputStream.readShort();
            this.g = dataInputStream.readShort();
        }
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeByte(this.j);
        dataOutputStream.writeInt(this.b);
        dataOutputStream.writeInt(this.c);
        dataOutputStream.writeInt(this.d);
        dataOutputStream.writeByte(this.h);
        dataOutputStream.writeByte(this.i);
        dataOutputStream.writeInt(this.k);
        if (this.k > 0) {
            dataOutputStream.writeShort(this.e);
            dataOutputStream.writeShort(this.f);
            dataOutputStream.writeShort(this.g);
        }
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return (21 + this.k > 0) ? 6 : 0;
    }
}
