// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet15Place extends Packet
{
    private int a;
    private int b;
    private int c;
    private int d;
    private ItemStack e;
    private float f;
    private float g;
    private float h;
    
    public void a(final DataInputStream datainputstream) {
        this.a = datainputstream.readInt();
        this.b = datainputstream.read();
        this.c = datainputstream.readInt();
        this.d = datainputstream.read();
        this.e = Packet.c(datainputstream);
        this.f = datainputstream.read() / 16.0f;
        this.g = datainputstream.read() / 16.0f;
        this.h = datainputstream.read() / 16.0f;
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        dataoutputstream.writeInt(this.a);
        dataoutputstream.write(this.b);
        dataoutputstream.writeInt(this.c);
        dataoutputstream.write(this.d);
        Packet.a(this.e, dataoutputstream);
        dataoutputstream.write((int)(this.f * 16.0f));
        dataoutputstream.write((int)(this.g * 16.0f));
        dataoutputstream.write((int)(this.h * 16.0f));
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 19;
    }
    
    public int d() {
        return this.a;
    }
    
    public int f() {
        return this.b;
    }
    
    public int g() {
        return this.c;
    }
    
    public int getFace() {
        return this.d;
    }
    
    public ItemStack getItemStack() {
        return this.e;
    }
    
    public float j() {
        return this.f;
    }
    
    public float k() {
        return this.g;
    }
    
    public float l() {
        return this.h;
    }
}
