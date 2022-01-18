// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet71Weather extends Packet
{
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    
    public Packet71Weather() {
    }
    
    public Packet71Weather(final Entity entity) {
        this.a = entity.id;
        this.b = MathHelper.floor(entity.locX * 32.0);
        this.c = MathHelper.floor(entity.locY * 32.0);
        this.d = MathHelper.floor(entity.locZ * 32.0);
        if (entity instanceof EntityLightning) {
            this.e = 1;
        }
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.e = dataInputStream.readByte();
        this.b = dataInputStream.readInt();
        this.c = dataInputStream.readInt();
        this.d = dataInputStream.readInt();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeByte(this.e);
        dataOutputStream.writeInt(this.b);
        dataOutputStream.writeInt(this.c);
        dataOutputStream.writeInt(this.d);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 17;
    }
}
