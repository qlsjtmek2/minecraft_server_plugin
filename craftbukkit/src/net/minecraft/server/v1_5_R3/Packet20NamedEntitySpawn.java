// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.util.List;

public class Packet20NamedEntitySpawn extends Packet
{
    public int a;
    public String b;
    public int c;
    public int d;
    public int e;
    public byte f;
    public byte g;
    public int h;
    private DataWatcher i;
    private List j;
    
    public Packet20NamedEntitySpawn() {
    }
    
    public Packet20NamedEntitySpawn(final EntityHuman entityhuman) {
        this.a = entityhuman.id;
        if (entityhuman.name.length() > 16) {
            this.b = entityhuman.name.substring(0, 16);
        }
        else {
            this.b = entityhuman.name;
        }
        this.c = MathHelper.floor(entityhuman.locX * 32.0);
        this.d = MathHelper.floor(entityhuman.locY * 32.0);
        this.e = MathHelper.floor(entityhuman.locZ * 32.0);
        this.f = (byte)(entityhuman.yaw * 256.0f / 360.0f);
        this.g = (byte)(entityhuman.pitch * 256.0f / 360.0f);
        final ItemStack itemstack = entityhuman.inventory.getItemInHand();
        this.h = ((itemstack == null) ? 0 : itemstack.id);
        this.i = entityhuman.getDataWatcher();
    }
    
    public void a(final DataInputStream datainputstream) throws IOException {
        this.a = datainputstream.readInt();
        this.b = Packet.a(datainputstream, 16);
        this.c = datainputstream.readInt();
        this.d = datainputstream.readInt();
        this.e = datainputstream.readInt();
        this.f = datainputstream.readByte();
        this.g = datainputstream.readByte();
        this.h = datainputstream.readShort();
        this.j = DataWatcher.a(datainputstream);
    }
    
    public void a(final DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(this.a);
        Packet.a(this.b, dataoutputstream);
        dataoutputstream.writeInt(this.c);
        dataoutputstream.writeInt(this.d);
        dataoutputstream.writeInt(this.e);
        dataoutputstream.writeByte(this.f);
        dataoutputstream.writeByte(this.g);
        dataoutputstream.writeShort(this.h);
        this.i.a(dataoutputstream);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 28;
    }
}
