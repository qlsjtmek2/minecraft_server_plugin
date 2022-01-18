// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet62NamedSoundEffect extends Packet
{
    private String a;
    private int b;
    private int c;
    private int d;
    private float e;
    private int f;
    
    public Packet62NamedSoundEffect() {
        this.c = Integer.MAX_VALUE;
    }
    
    public Packet62NamedSoundEffect(final String a, final double n, final double n2, final double n3, final float e, final float n4) {
        this.c = Integer.MAX_VALUE;
        this.a = a;
        this.b = (int)(n * 8.0);
        this.c = (int)(n2 * 8.0);
        this.d = (int)(n3 * 8.0);
        this.e = e;
        this.f = (int)(n4 * 63.0f);
        if (this.f < 0) {
            this.f = 0;
        }
        if (this.f > 255) {
            this.f = 255;
        }
    }
    
    public void a(final DataInputStream datainputstream) {
        this.a = Packet.a(datainputstream, 32);
        this.b = datainputstream.readInt();
        this.c = datainputstream.readInt();
        this.d = datainputstream.readInt();
        this.e = datainputstream.readFloat();
        this.f = datainputstream.readUnsignedByte();
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        Packet.a(this.a, dataoutputstream);
        dataoutputstream.writeInt(this.b);
        dataoutputstream.writeInt(this.c);
        dataoutputstream.writeInt(this.d);
        dataoutputstream.writeFloat(this.e);
        dataoutputstream.writeByte(this.f);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 24;
    }
}
