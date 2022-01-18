// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet16BlockItemSwitch extends Packet
{
    public int itemInHandIndex;
    
    public Packet16BlockItemSwitch() {
    }
    
    public Packet16BlockItemSwitch(final int itemInHandIndex) {
        this.itemInHandIndex = itemInHandIndex;
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.itemInHandIndex = dataInputStream.readShort();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeShort(this.itemInHandIndex);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 2;
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        return true;
    }
}
