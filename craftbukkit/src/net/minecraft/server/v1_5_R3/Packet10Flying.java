// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet10Flying extends Packet
{
    public double x;
    public double y;
    public double z;
    public double stance;
    public float yaw;
    public float pitch;
    public boolean g;
    public boolean hasPos;
    public boolean hasLook;
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.g = (dataInputStream.read() != 0);
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.write(this.g ? 1 : 0);
    }
    
    public int a() {
        return 1;
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        return true;
    }
}
