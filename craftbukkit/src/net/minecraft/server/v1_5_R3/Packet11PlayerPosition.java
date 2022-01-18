// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet11PlayerPosition extends Packet10Flying
{
    public Packet11PlayerPosition() {
        this.hasPos = true;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.x = dataInputStream.readDouble();
        this.y = dataInputStream.readDouble();
        this.stance = dataInputStream.readDouble();
        this.z = dataInputStream.readDouble();
        super.a(dataInputStream);
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeDouble(this.x);
        dataOutputStream.writeDouble(this.y);
        dataOutputStream.writeDouble(this.stance);
        dataOutputStream.writeDouble(this.z);
        super.a(dataOutputStream);
    }
    
    public int a() {
        return 33;
    }
}
