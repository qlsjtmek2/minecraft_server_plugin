// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet107SetCreativeSlot extends Packet
{
    public int slot;
    public ItemStack b;
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public void a(final DataInputStream datainputstream) {
        this.slot = datainputstream.readShort();
        this.b = Packet.c(datainputstream);
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        dataoutputstream.writeShort(this.slot);
        Packet.a(this.b, dataoutputstream);
    }
    
    public int a() {
        return 8;
    }
}
