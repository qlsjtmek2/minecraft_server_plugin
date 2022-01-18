// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet12PlayerLook extends Packet10Flying
{
    public Packet12PlayerLook() {
        this.hasLook = true;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.yaw = dataInputStream.readFloat();
        this.pitch = dataInputStream.readFloat();
        super.a(dataInputStream);
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeFloat(this.yaw);
        dataOutputStream.writeFloat(this.pitch);
        super.a(dataOutputStream);
    }
    
    public int a() {
        return 9;
    }
}
