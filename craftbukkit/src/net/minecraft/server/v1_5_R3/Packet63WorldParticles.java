// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataInputStream;

public class Packet63WorldParticles extends Packet
{
    private String a;
    private float b;
    private float c;
    private float d;
    private float e;
    private float f;
    private float g;
    private float h;
    private int i;
    
    public Packet63WorldParticles() {
    }
    
    public Packet63WorldParticles(final String particleName, final float x, final float y, final float z, final float offsetX, final float offsetY, final float offsetZ, final float speed, final int count) {
        this.a = particleName;
        this.b = x;
        this.c = y;
        this.d = z;
        this.e = offsetX;
        this.f = offsetY;
        this.g = offsetZ;
        this.h = speed;
        this.i = count;
    }
    
    public void a(final DataInputStream datainputstream) throws IOException {
        this.a = Packet.a(datainputstream, 64);
        this.b = datainputstream.readFloat();
        this.c = datainputstream.readFloat();
        this.d = datainputstream.readFloat();
        this.e = datainputstream.readFloat();
        this.f = datainputstream.readFloat();
        this.g = datainputstream.readFloat();
        this.h = datainputstream.readFloat();
        this.i = datainputstream.readInt();
    }
    
    public void a(final DataOutputStream dataoutputstream) throws IOException {
        Packet.a(this.a, dataoutputstream);
        dataoutputstream.writeFloat(this.b);
        dataoutputstream.writeFloat(this.c);
        dataoutputstream.writeFloat(this.d);
        dataoutputstream.writeFloat(this.e);
        dataoutputstream.writeFloat(this.f);
        dataoutputstream.writeFloat(this.g);
        dataoutputstream.writeFloat(this.h);
        dataoutputstream.writeInt(this.i);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 64;
    }
}
