// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet7UseEntity extends Packet
{
    public int a;
    public int target;
    public int action;
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.target = dataInputStream.readInt();
        this.action = dataInputStream.readByte();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeInt(this.target);
        dataOutputStream.writeByte(this.action);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 9;
    }
}
