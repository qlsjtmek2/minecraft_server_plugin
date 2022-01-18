// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.util.List;

public class Packet24MobSpawn extends Packet
{
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public byte i;
    public byte j;
    public byte k;
    private DataWatcher t;
    private List u;
    
    public Packet24MobSpawn() {
    }
    
    public Packet24MobSpawn(final EntityLiving entityLiving) {
        this.a = entityLiving.id;
        this.b = (byte)EntityTypes.a(entityLiving);
        this.c = entityLiving.at.a(entityLiving.locX);
        this.d = MathHelper.floor(entityLiving.locY * 32.0);
        this.e = entityLiving.at.a(entityLiving.locZ);
        this.i = (byte)(entityLiving.yaw * 256.0f / 360.0f);
        this.j = (byte)(entityLiving.pitch * 256.0f / 360.0f);
        this.k = (byte)(entityLiving.aA * 256.0f / 360.0f);
        final double n = 3.9;
        double motX = entityLiving.motX;
        double motY = entityLiving.motY;
        double motZ = entityLiving.motZ;
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
        this.f = (int)(motX * 8000.0);
        this.g = (int)(motY * 8000.0);
        this.h = (int)(motZ * 8000.0);
        this.t = entityLiving.getDataWatcher();
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.b = (dataInputStream.readByte() & 0xFF);
        this.c = dataInputStream.readInt();
        this.d = dataInputStream.readInt();
        this.e = dataInputStream.readInt();
        this.i = dataInputStream.readByte();
        this.j = dataInputStream.readByte();
        this.k = dataInputStream.readByte();
        this.f = dataInputStream.readShort();
        this.g = dataInputStream.readShort();
        this.h = dataInputStream.readShort();
        this.u = DataWatcher.a(dataInputStream);
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeByte(this.b & 0xFF);
        dataOutputStream.writeInt(this.c);
        dataOutputStream.writeInt(this.d);
        dataOutputStream.writeInt(this.e);
        dataOutputStream.writeByte(this.i);
        dataOutputStream.writeByte(this.j);
        dataOutputStream.writeByte(this.k);
        dataOutputStream.writeShort(this.f);
        dataOutputStream.writeShort(this.g);
        dataOutputStream.writeShort(this.h);
        this.t.a(dataOutputStream);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 26;
    }
}
