// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet102WindowClick extends Packet
{
    public int a;
    public int slot;
    public int button;
    public short d;
    public ItemStack item;
    public int shift;
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public void a(final DataInputStream datainputstream) {
        this.a = datainputstream.readByte();
        this.slot = datainputstream.readShort();
        this.button = datainputstream.readByte();
        this.d = datainputstream.readShort();
        this.shift = datainputstream.readByte();
        this.item = Packet.c(datainputstream);
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        dataoutputstream.writeByte(this.a);
        dataoutputstream.writeShort(this.slot);
        dataoutputstream.writeByte(this.button);
        dataoutputstream.writeShort(this.d);
        dataoutputstream.writeByte(this.shift);
        Packet.a(this.item, dataoutputstream);
    }
    
    public int a() {
        return 11;
    }
}
