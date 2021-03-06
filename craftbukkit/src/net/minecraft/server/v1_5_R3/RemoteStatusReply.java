// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;

public class RemoteStatusReply
{
    private ByteArrayOutputStream buffer;
    private DataOutputStream stream;
    
    public RemoteStatusReply(final int n) {
        this.buffer = new ByteArrayOutputStream(n);
        this.stream = new DataOutputStream(this.buffer);
    }
    
    public void write(final byte[] array) {
        this.stream.write(array, 0, array.length);
    }
    
    public void write(final String s) {
        this.stream.writeBytes(s);
        this.stream.write(0);
    }
    
    public void write(final int n) {
        this.stream.write(n);
    }
    
    public void write(final short n) {
        this.stream.writeShort(Short.reverseBytes(n));
    }
    
    public byte[] getBytes() {
        return this.buffer.toByteArray();
    }
    
    public void reset() {
        this.buffer.reset();
    }
}
