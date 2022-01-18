// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet42RemoveMobEffect extends Packet
{
    public int a;
    public byte b;
    
    public Packet42RemoveMobEffect() {
    }
    
    public Packet42RemoveMobEffect(final int a, final MobEffect mobEffect) {
        this.a = a;
        this.b = (byte)(mobEffect.getEffectId() & 0xFF);
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.b = dataInputStream.readByte();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeByte(this.b);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 5;
    }
}
