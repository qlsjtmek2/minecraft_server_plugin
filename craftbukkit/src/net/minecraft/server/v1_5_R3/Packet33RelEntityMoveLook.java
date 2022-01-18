// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet33RelEntityMoveLook extends Packet30Entity
{
    public Packet33RelEntityMoveLook() {
        this.g = true;
    }
    
    public Packet33RelEntityMoveLook(final int n, final byte b, final byte c, final byte d, final byte e, final byte f) {
        super(n);
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = true;
    }
    
    public void a(final DataInputStream dataInputStream) {
        super.a(dataInputStream);
        this.b = dataInputStream.readByte();
        this.c = dataInputStream.readByte();
        this.d = dataInputStream.readByte();
        this.e = dataInputStream.readByte();
        this.f = dataInputStream.readByte();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        super.a(dataOutputStream);
        dataOutputStream.writeByte(this.b);
        dataOutputStream.writeByte(this.c);
        dataOutputStream.writeByte(this.d);
        dataOutputStream.writeByte(this.e);
        dataOutputStream.writeByte(this.f);
    }
    
    public int a() {
        return 9;
    }
}
