// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet103SetSlot extends Packet
{
    public int a;
    public int b;
    public ItemStack c;
    
    public Packet103SetSlot() {
    }
    
    public Packet103SetSlot(final int a, final int b, final ItemStack itemStack) {
        this.a = a;
        this.b = b;
        this.c = ((itemStack == null) ? itemStack : itemStack.cloneItemStack());
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public void a(final DataInputStream datainputstream) {
        this.a = datainputstream.readByte();
        this.b = datainputstream.readShort();
        this.c = Packet.c(datainputstream);
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        dataoutputstream.writeByte(this.a);
        dataoutputstream.writeShort(this.b);
        Packet.a(this.c, dataoutputstream);
    }
    
    public int a() {
        return 8;
    }
}
