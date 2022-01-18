// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet41MobEffect extends Packet
{
    public int a;
    public byte b;
    public byte c;
    public short d;
    
    public Packet41MobEffect() {
    }
    
    public Packet41MobEffect(final int a, final MobEffect mobEffect) {
        this.a = a;
        this.b = (byte)(mobEffect.getEffectId() & 0xFF);
        this.c = (byte)(mobEffect.getAmplifier() & 0xFF);
        if (mobEffect.getDuration() > 32767) {
            this.d = 32767;
        }
        else {
            this.d = (short)mobEffect.getDuration();
        }
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.b = dataInputStream.readByte();
        this.c = dataInputStream.readByte();
        this.d = dataInputStream.readShort();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeByte(this.b);
        dataOutputStream.writeByte(this.c);
        dataOutputStream.writeShort(this.d);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 8;
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        final Packet41MobEffect packet41MobEffect = (Packet41MobEffect)packet;
        return packet41MobEffect.a == this.a && packet41MobEffect.b == this.b;
    }
}
