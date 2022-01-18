// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.util.List;

public class Packet104WindowItems extends Packet
{
    public int a;
    public ItemStack[] b;
    
    public Packet104WindowItems() {
    }
    
    public Packet104WindowItems(final int a, final List list) {
        this.a = a;
        this.b = new ItemStack[list.size()];
        for (int i = 0; i < this.b.length; ++i) {
            final ItemStack itemStack = list.get(i);
            this.b[i] = ((itemStack == null) ? null : itemStack.cloneItemStack());
        }
    }
    
    public void a(final DataInputStream datainputstream) {
        this.a = datainputstream.readByte();
        final short short1 = datainputstream.readShort();
        this.b = new ItemStack[short1];
        for (short n = 0; n < short1; ++n) {
            this.b[n] = Packet.c(datainputstream);
        }
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        dataoutputstream.writeByte(this.a);
        dataoutputstream.writeShort(this.b.length);
        for (int i = 0; i < this.b.length; ++i) {
            Packet.a(this.b[i], dataoutputstream);
        }
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 3 + this.b.length * 5;
    }
}
