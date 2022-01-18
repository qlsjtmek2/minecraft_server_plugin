// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet25EntityPainting extends Packet
{
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public String f;
    
    public Packet25EntityPainting() {
    }
    
    public Packet25EntityPainting(final EntityPainting entityPainting) {
        this.a = entityPainting.id;
        this.b = entityPainting.x;
        this.c = entityPainting.y;
        this.d = entityPainting.z;
        this.e = entityPainting.direction;
        this.f = entityPainting.art.B;
    }
    
    public void a(final DataInputStream datainputstream) {
        this.a = datainputstream.readInt();
        this.f = Packet.a(datainputstream, EnumArt.A);
        this.b = datainputstream.readInt();
        this.c = datainputstream.readInt();
        this.d = datainputstream.readInt();
        this.e = datainputstream.readInt();
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        dataoutputstream.writeInt(this.a);
        Packet.a(this.f, dataoutputstream);
        dataoutputstream.writeInt(this.b);
        dataoutputstream.writeInt(this.c);
        dataoutputstream.writeInt(this.d);
        dataoutputstream.writeInt(this.e);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 24;
    }
}
