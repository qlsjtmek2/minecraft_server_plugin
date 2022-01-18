// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet39AttachEntity extends Packet
{
    public int a;
    public int b;
    
    public Packet39AttachEntity() {
    }
    
    public Packet39AttachEntity(final Entity entity, final Entity entity2) {
        this.a = entity.id;
        this.b = ((entity2 != null) ? entity2.id : -1);
    }
    
    public int a() {
        return 8;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.b = dataInputStream.readInt();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeInt(this.b);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        return ((Packet39AttachEntity)packet).a == this.a;
    }
}
