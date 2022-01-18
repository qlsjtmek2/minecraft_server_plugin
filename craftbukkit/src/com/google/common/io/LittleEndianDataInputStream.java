// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.io;

import java.io.DataInputStream;
import com.google.common.primitives.Longs;
import com.google.common.primitives.Ints;
import java.io.EOFException;
import java.io.IOException;
import com.google.common.base.Preconditions;
import java.io.InputStream;
import com.google.common.annotations.Beta;
import java.io.DataInput;
import java.io.FilterInputStream;

@Beta
public final class LittleEndianDataInputStream extends FilterInputStream implements DataInput
{
    public LittleEndianDataInputStream(final InputStream in) {
        super(Preconditions.checkNotNull(in));
    }
    
    public String readLine() {
        throw new UnsupportedOperationException("readLine is not supported");
    }
    
    public void readFully(final byte[] b) throws IOException {
        ByteStreams.readFully(this, b);
    }
    
    public void readFully(final byte[] b, final int off, final int len) throws IOException {
        ByteStreams.readFully(this, b, off, len);
    }
    
    public int skipBytes(final int n) throws IOException {
        return (int)this.in.skip(n);
    }
    
    public int readUnsignedByte() throws IOException {
        final int b1 = this.in.read();
        if (0 > b1) {
            throw new EOFException();
        }
        return b1;
    }
    
    public int readUnsignedShort() throws IOException {
        final byte b1 = this.readAndCheckByte();
        final byte b2 = this.readAndCheckByte();
        return Ints.fromBytes((byte)0, (byte)0, b2, b1);
    }
    
    public int readInt() throws IOException {
        final byte b1 = this.readAndCheckByte();
        final byte b2 = this.readAndCheckByte();
        final byte b3 = this.readAndCheckByte();
        final byte b4 = this.readAndCheckByte();
        return Ints.fromBytes(b4, b3, b2, b1);
    }
    
    public long readLong() throws IOException {
        final byte b1 = this.readAndCheckByte();
        final byte b2 = this.readAndCheckByte();
        final byte b3 = this.readAndCheckByte();
        final byte b4 = this.readAndCheckByte();
        final byte b5 = this.readAndCheckByte();
        final byte b6 = this.readAndCheckByte();
        final byte b7 = this.readAndCheckByte();
        final byte b8 = this.readAndCheckByte();
        return Longs.fromBytes(b8, b7, b6, b5, b4, b3, b2, b1);
    }
    
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readInt());
    }
    
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }
    
    public String readUTF() throws IOException {
        return new DataInputStream(this.in).readUTF();
    }
    
    public short readShort() throws IOException {
        return (short)this.readUnsignedShort();
    }
    
    public char readChar() throws IOException {
        return (char)this.readUnsignedShort();
    }
    
    public byte readByte() throws IOException {
        return (byte)this.readUnsignedByte();
    }
    
    public boolean readBoolean() throws IOException {
        return this.readUnsignedByte() != 0;
    }
    
    private byte readAndCheckByte() throws IOException, EOFException {
        final int b1 = this.in.read();
        if (-1 == b1) {
            throw new EOFException();
        }
        return (byte)b1;
    }
}
