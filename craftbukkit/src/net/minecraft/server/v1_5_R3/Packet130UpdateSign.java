// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet130UpdateSign extends Packet
{
    public int x;
    public int y;
    public int z;
    public String[] lines;
    
    public Packet130UpdateSign() {
        this.lowPriority = true;
    }
    
    public Packet130UpdateSign(final int x, final int y, final int z, final String[] array) {
        this.lowPriority = true;
        this.x = x;
        this.y = y;
        this.z = z;
        this.lines = new String[] { array[0], array[1], array[2], array[3] };
    }
    
    public void a(final DataInputStream datainputstream) {
        this.x = datainputstream.readInt();
        this.y = datainputstream.readShort();
        this.z = datainputstream.readInt();
        this.lines = new String[4];
        for (int i = 0; i < 4; ++i) {
            this.lines[i] = Packet.a(datainputstream, 15);
        }
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        dataoutputstream.writeInt(this.x);
        dataoutputstream.writeShort(this.y);
        dataoutputstream.writeInt(this.z);
        for (int i = 0; i < 4; ++i) {
            Packet.a(this.lines[i], dataoutputstream);
        }
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        int n = 0;
        for (int i = 0; i < 4; ++i) {
            n += this.lines[i].length();
        }
        return n;
    }
}
