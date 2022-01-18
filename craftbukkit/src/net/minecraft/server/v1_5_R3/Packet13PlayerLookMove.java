// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet13PlayerLookMove extends Packet10Flying
{
    public Packet13PlayerLookMove() {
        this.hasLook = true;
        this.hasPos = true;
    }
    
    public Packet13PlayerLookMove(final double x, final double y, final double stance, final double z, final float yaw, final float pitch, final boolean g) {
        this.x = x;
        this.y = y;
        this.stance = stance;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.g = g;
        this.hasLook = true;
        this.hasPos = true;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.x = dataInputStream.readDouble();
        this.y = dataInputStream.readDouble();
        this.stance = dataInputStream.readDouble();
        this.z = dataInputStream.readDouble();
        this.yaw = dataInputStream.readFloat();
        this.pitch = dataInputStream.readFloat();
        super.a(dataInputStream);
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeDouble(this.x);
        dataOutputStream.writeDouble(this.y);
        dataOutputStream.writeDouble(this.stance);
        dataOutputStream.writeDouble(this.z);
        dataOutputStream.writeFloat(this.yaw);
        dataOutputStream.writeFloat(this.pitch);
        super.a(dataOutputStream);
    }
    
    public int a() {
        return 41;
    }
}
