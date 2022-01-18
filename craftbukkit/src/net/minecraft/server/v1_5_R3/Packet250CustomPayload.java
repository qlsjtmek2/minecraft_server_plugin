// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet250CustomPayload extends Packet
{
    public String tag;
    public int length;
    public byte[] data;
    
    public Packet250CustomPayload() {
    }
    
    public Packet250CustomPayload(final String tag, final byte[] data) {
        this.tag = tag;
        this.data = data;
        if (data != null) {
            this.length = data.length;
            if (this.length > 32767) {
                throw new IllegalArgumentException("Payload may not be larger than 32k");
            }
        }
    }
    
    public void a(final DataInputStream datainputstream) {
        this.tag = Packet.a(datainputstream, 20);
        this.length = datainputstream.readShort();
        if (this.length > 0 && this.length < 32767) {
            datainputstream.readFully(this.data = new byte[this.length]);
        }
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        Packet.a(this.tag, dataoutputstream);
        dataoutputstream.writeShort((short)this.length);
        if (this.data != null) {
            dataoutputstream.write(this.data);
        }
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 2 + this.tag.length() * 2 + 2 + this.length;
    }
}
