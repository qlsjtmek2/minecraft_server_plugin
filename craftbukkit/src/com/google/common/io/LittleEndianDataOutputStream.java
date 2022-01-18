// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.io;

import com.google.common.primitives.Longs;
import java.io.IOException;
import java.io.DataOutputStream;
import com.google.common.base.Preconditions;
import java.io.OutputStream;
import com.google.common.annotations.Beta;
import java.io.DataOutput;
import java.io.FilterOutputStream;

@Beta
public class LittleEndianDataOutputStream extends FilterOutputStream implements DataOutput
{
    public LittleEndianDataOutputStream(final OutputStream out) {
        super(new DataOutputStream(Preconditions.checkNotNull(out)));
    }
    
    public void write(final byte[] b, final int off, final int len) throws IOException {
        this.out.write(b, off, len);
    }
    
    public void writeBoolean(final boolean v) throws IOException {
        ((DataOutputStream)this.out).writeBoolean(v);
    }
    
    public void writeByte(final int v) throws IOException {
        ((DataOutputStream)this.out).writeByte(v);
    }
    
    @Deprecated
    public void writeBytes(final String s) throws IOException {
        ((DataOutputStream)this.out).writeBytes(s);
    }
    
    public void writeChar(final int v) throws IOException {
        this.writeShort(v);
    }
    
    public void writeChars(final String s) throws IOException {
        for (int i = 0; i < s.length(); ++i) {
            this.writeChar(s.charAt(i));
        }
    }
    
    public void writeDouble(final double v) throws IOException {
        this.writeLong(Double.doubleToLongBits(v));
    }
    
    public void writeFloat(final float v) throws IOException {
        this.writeInt(Float.floatToIntBits(v));
    }
    
    public void writeInt(final int v) throws IOException {
        this.out.write(0xFF & v);
        this.out.write(0xFF & v >> 8);
        this.out.write(0xFF & v >> 16);
        this.out.write(0xFF & v >> 24);
    }
    
    public void writeLong(final long v) throws IOException {
        final byte[] bytes = Longs.toByteArray(Long.reverseBytes(v));
        this.write(bytes, 0, bytes.length);
    }
    
    public void writeShort(final int v) throws IOException {
        this.out.write(0xFF & v);
        this.out.write(0xFF & v >> 8);
    }
    
    public void writeUTF(final String str) throws IOException {
        ((DataOutputStream)this.out).writeUTF(str);
    }
}
