// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.IOException;
import org.bukkit.craftbukkit.v1_5_R3.Spigot;
import java.io.DataInputStream;

public class Packet2Handshake extends Packet
{
    private int a;
    private String b;
    public String c;
    public int d;
    
    public void a(final DataInputStream datainputstream) throws IOException {
        this.a = datainputstream.readByte();
        this.b = Packet.a(datainputstream, 16);
        this.c = Packet.a(datainputstream, 255);
        this.d = datainputstream.readInt();
        if (!Spigot.validName.matcher(this.b).matches()) {
            throw new IOException("Invalid name!");
        }
    }
    
    public void a(final DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeByte(this.a);
        Packet.a(this.b, dataoutputstream);
        Packet.a(this.c, dataoutputstream);
        dataoutputstream.writeInt(this.d);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 3 + 2 * this.b.length();
    }
    
    public int d() {
        return this.a;
    }
    
    public String f() {
        return this.b;
    }
}
