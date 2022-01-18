// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.security.Key;
import java.security.PrivateKey;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import javax.crypto.SecretKey;

public class Packet252KeyResponse extends Packet
{
    private byte[] a;
    private byte[] b;
    private SecretKey c;
    
    public Packet252KeyResponse() {
        this.a = new byte[0];
        this.b = new byte[0];
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = Packet.b(dataInputStream);
        this.b = Packet.b(dataInputStream);
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        Packet.a(dataOutputStream, this.a);
        Packet.a(dataOutputStream, this.b);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 2 + this.a.length + 2 + this.b.length;
    }
    
    public SecretKey a(final PrivateKey privateKey) {
        if (privateKey == null) {
            return this.c;
        }
        return this.c = MinecraftEncryption.a(privateKey, this.a);
    }
    
    public SecretKey d() {
        return this.a((PrivateKey)null);
    }
    
    public byte[] b(final PrivateKey privateKey) {
        if (privateKey == null) {
            return this.b;
        }
        return MinecraftEncryption.b(privateKey, this.b);
    }
}
