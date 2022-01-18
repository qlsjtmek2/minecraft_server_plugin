// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet5EntityEquipment extends Packet
{
    public int a;
    public int b;
    private ItemStack c;
    
    public Packet5EntityEquipment() {
    }
    
    public Packet5EntityEquipment(final int a, final int b, final ItemStack itemStack) {
        this.a = a;
        this.b = b;
        this.c = ((itemStack == null) ? null : itemStack.cloneItemStack());
    }
    
    public void a(final DataInputStream datainputstream) {
        this.a = datainputstream.readInt();
        this.b = datainputstream.readShort();
        this.c = Packet.c(datainputstream);
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        dataoutputstream.writeInt(this.a);
        dataoutputstream.writeShort(this.b);
        Packet.a(this.c, dataoutputstream);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 8;
    }
    
    public boolean e() {
        return true;
    }
    
    public boolean a(final Packet packet) {
        final Packet5EntityEquipment packet5EntityEquipment = (Packet5EntityEquipment)packet;
        return packet5EntityEquipment.a == this.a && packet5EntityEquipment.b == this.b;
    }
}
