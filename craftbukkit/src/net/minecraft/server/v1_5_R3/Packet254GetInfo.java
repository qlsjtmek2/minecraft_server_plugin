// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet254GetInfo extends Packet
{
    public int a;
    
    public Packet254GetInfo() {
        this.a = 0;
    }
    
    public void a(final DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readByte();
        }
        catch (Throwable t) {
            this.a = 0;
        }
    }
    
    public void a(final DataOutputStream dataOutputStream) {
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 0;
    }
}
