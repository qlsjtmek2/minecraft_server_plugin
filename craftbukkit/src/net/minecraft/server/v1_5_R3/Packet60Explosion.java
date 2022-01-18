// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class Packet60Explosion extends Packet
{
    public double a;
    public double b;
    public double c;
    public float d;
    public List e;
    private float f;
    private float g;
    private float h;
    
    public Packet60Explosion() {
    }
    
    public Packet60Explosion(final double a, final double b, final double c, final float d, final List list, final Vec3D vec3D) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = new ArrayList(list);
        if (vec3D != null) {
            this.f = (float)vec3D.c;
            this.g = (float)vec3D.d;
            this.h = (float)vec3D.e;
        }
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readDouble();
        this.b = dataInputStream.readDouble();
        this.c = dataInputStream.readDouble();
        this.d = dataInputStream.readFloat();
        final int int1 = dataInputStream.readInt();
        this.e = new ArrayList(int1);
        final int n = (int)this.a;
        final int n2 = (int)this.b;
        final int n3 = (int)this.c;
        for (int i = 0; i < int1; ++i) {
            this.e.add(new ChunkPosition(dataInputStream.readByte() + n, dataInputStream.readByte() + n2, dataInputStream.readByte() + n3));
        }
        this.f = dataInputStream.readFloat();
        this.g = dataInputStream.readFloat();
        this.h = dataInputStream.readFloat();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeDouble(this.a);
        dataOutputStream.writeDouble(this.b);
        dataOutputStream.writeDouble(this.c);
        dataOutputStream.writeFloat(this.d);
        dataOutputStream.writeInt(this.e.size());
        final int n = (int)this.a;
        final int n2 = (int)this.b;
        final int n3 = (int)this.c;
        for (final ChunkPosition chunkPosition : this.e) {
            final int n4 = chunkPosition.x - n;
            final int n5 = chunkPosition.y - n2;
            final int n6 = chunkPosition.z - n3;
            dataOutputStream.writeByte(n4);
            dataOutputStream.writeByte(n5);
            dataOutputStream.writeByte(n6);
        }
        dataOutputStream.writeFloat(this.f);
        dataOutputStream.writeFloat(this.g);
        dataOutputStream.writeFloat(this.h);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 32 + this.e.size() * 3 + 3;
    }
}
