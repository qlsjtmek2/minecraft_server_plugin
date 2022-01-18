// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet32EntityLook extends Packet30Entity
{
    public Packet32EntityLook() {
        this.g = true;
    }
    
    public Packet32EntityLook(final int n, final byte e, final byte f) {
        super(n);
        this.e = e;
        this.f = f;
        this.g = true;
    }
    
    public void a(final DataInputStream dataInputStream) {
        super.a(dataInputStream);
        this.e = dataInputStream.readByte();
        this.f = dataInputStream.readByte();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        super.a(dataOutputStream);
        dataOutputStream.writeByte(this.e);
        dataOutputStream.writeByte(this.f);
    }
    
    public int a() {
        return 6;
    }
}
