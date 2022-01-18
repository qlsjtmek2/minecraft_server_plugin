// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet101CloseWindow extends Packet
{
    public int a;
    
    public Packet101CloseWindow() {
    }
    
    public Packet101CloseWindow(final int a) {
        this.a = a;
    }
    
    public void handle(final Connection connection) {
        connection.handleContainerClose(this);
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readByte();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeByte(this.a);
    }
    
    public int a() {
        return 1;
    }
}
