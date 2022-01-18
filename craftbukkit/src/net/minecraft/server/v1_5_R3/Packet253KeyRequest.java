// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.security.PublicKey;

public class Packet253KeyRequest extends Packet
{
    private String a;
    private PublicKey b;
    private byte[] c;
    
    public Packet253KeyRequest() {
        this.c = new byte[0];
    }
    
    public Packet253KeyRequest(final String a, final PublicKey b, final byte[] c) {
        this.c = new byte[0];
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public void a(final DataInputStream datainputstream) {
        this.a = Packet.a(datainputstream, 20);
        this.b = MinecraftEncryption.a(Packet.b(datainputstream));
        this.c = Packet.b(datainputstream);
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        Packet.a(this.a, dataoutputstream);
        Packet.a(dataoutputstream, this.b.getEncoded());
        Packet.a(dataoutputstream, this.c);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 2 + this.a.length() * 2 + 2 + this.b.getEncoded().length + 2 + this.c.length;
    }
}
