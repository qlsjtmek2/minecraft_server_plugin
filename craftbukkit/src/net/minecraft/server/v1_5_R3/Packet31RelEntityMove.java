// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet31RelEntityMove extends Packet30Entity
{
    public Packet31RelEntityMove() {
    }
    
    public Packet31RelEntityMove(final int n, final byte b, final byte c, final byte d) {
        super(n);
        this.b = b;
        this.c = c;
        this.d = d;
    }
    
    public void a(final DataInputStream dataInputStream) {
        super.a(dataInputStream);
        this.b = dataInputStream.readByte();
        this.c = dataInputStream.readByte();
        this.d = dataInputStream.readByte();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        super.a(dataOutputStream);
        dataOutputStream.writeByte(this.b);
        dataOutputStream.writeByte(this.c);
        dataOutputStream.writeByte(this.d);
    }
    
    public int a() {
        return 7;
    }
}
