// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet26AddExpOrb extends Packet
{
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    
    public Packet26AddExpOrb() {
    }
    
    public Packet26AddExpOrb(final EntityExperienceOrb entityExperienceOrb) {
        this.a = entityExperienceOrb.id;
        this.b = MathHelper.floor(entityExperienceOrb.locX * 32.0);
        this.c = MathHelper.floor(entityExperienceOrb.locY * 32.0);
        this.d = MathHelper.floor(entityExperienceOrb.locZ * 32.0);
        this.e = entityExperienceOrb.c();
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.b = dataInputStream.readInt();
        this.c = dataInputStream.readInt();
        this.d = dataInputStream.readInt();
        this.e = dataInputStream.readShort();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeInt(this.b);
        dataOutputStream.writeInt(this.c);
        dataOutputStream.writeInt(this.d);
        dataOutputStream.writeShort(this.e);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 18;
    }
}
