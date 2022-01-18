// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.io;

import java.io.DataOutputStream;
import java.io.DataOutput;
import java.io.DataInputStream;
import java.io.DataInput;
import java.security.MessageDigest;
import java.util.zip.Checksum;
import java.io.EOFException;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.ReadableByteChannel;
import java.io.InputStream;
import java.io.Closeable;
import com.google.common.base.Preconditions;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import com.google.common.annotations.Beta;

@Beta
public final class ByteStreams
{
    private static final int BUF_SIZE = 4096;
    
    public static InputSupplier<ByteArrayInputStream> newInputStreamSupplier(final byte[] b) {
        return newInputStreamSupplier(b, 0, b.length);
    }
    
    public static InputSupplier<ByteArrayInputStream> newInputStreamSupplier(final byte[] b, final int off, final int len) {
        return new InputSupplier<ByteArrayInputStream>() {
            public ByteArrayInputStream getInput() {
                return new ByteArrayInputStream(b, off, len);
            }
        };
    }
    
    public static void write(final byte[] from, final OutputSupplier<? extends OutputStream> to) throws IOException {
        Preconditions.checkNotNull(from);
        boolean threw = true;
        final OutputStream out = (OutputStream)to.getOutput();
        try {
            out.write(from);
            threw = false;
        }
        finally {
            Closeables.close(out, threw);
        }
    }
    
    public static long copy(final InputSupplier<? extends InputStream> from, final OutputSupplier<? extends OutputStream> to) throws IOException {
        boolean threw = true;
        final InputStream in = (InputStream)from.getInput();
        try {
            final OutputStream out = (OutputStream)to.getOutput();
            try {
                final long count = copy(in, out);
                threw = false;
                return count;
            }
            finally {
                Closeables.close(out, threw);
            }
        }
        finally {
            Closeables.close(in, threw);
        }
    }
    
    public static long copy(final InputSupplier<? extends InputStream> from, final OutputStream to) throws IOException {
        boolean threw = true;
        final InputStream in = (InputStream)from.getInput();
        try {
            final long count = copy(in, to);
            threw = false;
            return count;
        }
        finally {
            Closeables.close(in, threw);
        }
    }
    
    public static long copy(final InputStream from, final OutputSupplier<? extends OutputStream> to) throws IOException {
        boolean threw = true;
        final OutputStream out = (OutputStream)to.getOutput();
        try {
            final long count = copy(from, out);
            threw = false;
            return count;
        }
        finally {
            Closeables.close(out, threw);
        }
    }
    
    public static long copy(final InputStream from, final OutputStream to) throws IOException {
        final byte[] buf = new byte[4096];
        long total = 0L;
        while (true) {
            final int r = from.read(buf);
            if (r == -1) {
                break;
            }
            to.write(buf, 0, r);
            total += r;
        }
        return total;
    }
    
    public static long copy(final ReadableByteChannel from, final WritableByteChannel to) throws IOException {
        final ByteBuffer buf = ByteBuffer.allocate(4096);
        long total = 0L;
        while (from.read(buf) != -1) {
            buf.flip();
            while (buf.hasRemaining()) {
                total += to.write(buf);
            }
            buf.clear();
        }
        return total;
    }
    
    public static byte[] toByteArray(final InputStream in) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(in, out);
        return out.toByteArray();
    }
    
    public static byte[] toByteArray(final InputSupplier<? extends InputStream> supplier) throws IOException {
        boolean threw = true;
        final InputStream in = (InputStream)supplier.getInput();
        try {
            final byte[] result = toByteArray(in);
            threw = false;
            return result;
        }
        finally {
            Closeables.close(in, threw);
        }
    }
    
    public static ByteArrayDataInput newDataInput(final byte[] bytes) {
        return new ByteArrayDataInputStream(bytes);
    }
    
    public static ByteArrayDataInput newDataInput(final byte[] bytes, final int start) {
        Preconditions.checkPositionIndex(start, bytes.length);
        return new ByteArrayDataInputStream(bytes, start);
    }
    
    public static ByteArrayDataOutput newDataOutput() {
        return new ByteArrayDataOutputStream();
    }
    
    public static ByteArrayDataOutput newDataOutput(final int size) {
        Preconditions.checkArgument(size >= 0, "Invalid size: %s", size);
        return new ByteArrayDataOutputStream(size);
    }
    
    public static long length(final InputSupplier<? extends InputStream> supplier) throws IOException {
        long count = 0L;
        boolean threw = true;
        final InputStream in = (InputStream)supplier.getInput();
        try {
            while (true) {
                final long amt = in.skip(2147483647L);
                if (amt == 0L) {
                    if (in.read() == -1) {
                        break;
                    }
                    ++count;
                }
                else {
                    count += amt;
                }
            }
            threw = false;
            return count;
        }
        finally {
            Closeables.close(in, threw);
        }
    }
    
    public static boolean equal(final InputSupplier<? extends InputStream> supplier1, final InputSupplier<? extends InputStream> supplier2) throws IOException {
        final byte[] buf1 = new byte[4096];
        final byte[] buf2 = new byte[4096];
        boolean threw = true;
        final InputStream in1 = (InputStream)supplier1.getInput();
        try {
            final InputStream in2 = (InputStream)supplier2.getInput();
            try {
                while (true) {
                    final int read1 = read(in1, buf1, 0, 4096);
                    final int read2 = read(in2, buf2, 0, 4096);
                    if (read1 != read2 || !Arrays.equals(buf1, buf2)) {
                        threw = false;
                        return false;
                    }
                    if (read1 != 4096) {
                        threw = false;
                        return true;
                    }
                }
            }
            finally {
                Closeables.close(in2, threw);
            }
        }
        finally {
            Closeables.close(in1, threw);
        }
    }
    
    public static void readFully(final InputStream in, final byte[] b) throws IOException {
        readFully(in, b, 0, b.length);
    }
    
    public static void readFully(final InputStream in, final byte[] b, final int off, final int len) throws IOException {
        if (read(in, b, off, len) != len) {
            throw new EOFException();
        }
    }
    
    public static void skipFully(final InputStream in, long n) throws IOException {
        while (n > 0L) {
            final long amt = in.skip(n);
            if (amt == 0L) {
                if (in.read() == -1) {
                    throw new EOFException();
                }
                --n;
            }
            else {
                n -= amt;
            }
        }
    }
    
    public static <T> T readBytes(final InputSupplier<? extends InputStream> supplier, final ByteProcessor<T> processor) throws IOException {
        final byte[] buf = new byte[4096];
        boolean threw = true;
        final InputStream in = (InputStream)supplier.getInput();
        try {
            int amt;
            do {
                amt = in.read(buf);
                if (amt == -1) {
                    threw = false;
                    break;
                }
            } while (processor.processBytes(buf, 0, amt));
            return processor.getResult();
        }
        finally {
            Closeables.close(in, threw);
        }
    }
    
    public static long getChecksum(final InputSupplier<? extends InputStream> supplier, final Checksum checksum) throws IOException {
        return readBytes(supplier, (ByteProcessor<Long>)new ByteProcessor<Long>() {
            public boolean processBytes(final byte[] buf, final int off, final int len) {
                checksum.update(buf, off, len);
                return true;
            }
            
            public Long getResult() {
                final long result = checksum.getValue();
                checksum.reset();
                return result;
            }
        });
    }
    
    public static byte[] getDigest(final InputSupplier<? extends InputStream> supplier, final MessageDigest md) throws IOException {
        return readBytes(supplier, (ByteProcessor<byte[]>)new ByteProcessor<byte[]>() {
            public boolean processBytes(final byte[] buf, final int off, final int len) {
                md.update(buf, off, len);
                return true;
            }
            
            public byte[] getResult() {
                return md.digest();
            }
        });
    }
    
    public static int read(final InputStream in, final byte[] b, final int off, final int len) throws IOException {
        if (len < 0) {
            throw new IndexOutOfBoundsException("len is negative");
        }
        int total;
        int result;
        for (total = 0; total < len; total += result) {
            result = in.read(b, off + total, len - total);
            if (result == -1) {
                break;
            }
        }
        return total;
    }
    
    public static InputSupplier<InputStream> slice(final InputSupplier<? extends InputStream> supplier, final long offset, final long length) {
        Preconditions.checkNotNull(supplier);
        Preconditions.checkArgument(offset >= 0L, (Object)"offset is negative");
        Preconditions.checkArgument(length >= 0L, (Object)"length is negative");
        return new InputSupplier<InputStream>() {
            public InputStream getInput() throws IOException {
                final InputStream in = supplier.getInput();
                if (offset > 0L) {
                    try {
                        ByteStreams.skipFully(in, offset);
                    }
                    catch (IOException e) {
                        Closeables.closeQuietly(in);
                        throw e;
                    }
                }
                return new LimitInputStream(in, length);
            }
        };
    }
    
    public static InputSupplier<InputStream> join(final Iterable<? extends InputSupplier<? extends InputStream>> suppliers) {
        return new InputSupplier<InputStream>() {
            public InputStream getInput() throws IOException {
                return new MultiInputStream(suppliers.iterator());
            }
        };
    }
    
    public static InputSupplier<InputStream> join(final InputSupplier<? extends InputStream>... suppliers) {
        return join(Arrays.asList(suppliers));
    }
    
    private static class ByteArrayDataInputStream implements ByteArrayDataInput
    {
        final DataInput input;
        
        ByteArrayDataInputStream(final byte[] bytes) {
            this.input = new DataInputStream(new ByteArrayInputStream(bytes));
        }
        
        ByteArrayDataInputStream(final byte[] bytes, final int start) {
            this.input = new DataInputStream(new ByteArrayInputStream(bytes, start, bytes.length - start));
        }
        
        public void readFully(final byte[] b) {
            try {
                this.input.readFully(b);
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public void readFully(final byte[] b, final int off, final int len) {
            try {
                this.input.readFully(b, off, len);
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public int skipBytes(final int n) {
            try {
                return this.input.skipBytes(n);
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public boolean readBoolean() {
            try {
                return this.input.readBoolean();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public byte readByte() {
            try {
                return this.input.readByte();
            }
            catch (EOFException e) {
                throw new IllegalStateException(e);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }
        
        public int readUnsignedByte() {
            try {
                return this.input.readUnsignedByte();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public short readShort() {
            try {
                return this.input.readShort();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public int readUnsignedShort() {
            try {
                return this.input.readUnsignedShort();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public char readChar() {
            try {
                return this.input.readChar();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public int readInt() {
            try {
                return this.input.readInt();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public long readLong() {
            try {
                return this.input.readLong();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public float readFloat() {
            try {
                return this.input.readFloat();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public double readDouble() {
            try {
                return this.input.readDouble();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public String readLine() {
            try {
                return this.input.readLine();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public String readUTF() {
            try {
                return this.input.readUTF();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
    
    private static class ByteArrayDataOutputStream implements ByteArrayDataOutput
    {
        final DataOutput output;
        final ByteArrayOutputStream byteArrayOutputSteam;
        
        ByteArrayDataOutputStream() {
            this(new ByteArrayOutputStream());
        }
        
        ByteArrayDataOutputStream(final int size) {
            this(new ByteArrayOutputStream(size));
        }
        
        ByteArrayDataOutputStream(final ByteArrayOutputStream byteArrayOutputSteam) {
            this.byteArrayOutputSteam = byteArrayOutputSteam;
            this.output = new DataOutputStream(byteArrayOutputSteam);
        }
        
        public void write(final int b) {
            try {
                this.output.write(b);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }
        
        public void write(final byte[] b) {
            try {
                this.output.write(b);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }
        
        public void write(final byte[] b, final int off, final int len) {
            try {
                this.output.write(b, off, len);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }
        
        public void writeBoolean(final boolean v) {
            try {
                this.output.writeBoolean(v);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }
        
        public void writeByte(final int v) {
            try {
                this.output.writeByte(v);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }
        
        public void writeBytes(final String s) {
            try {
                this.output.writeBytes(s);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }
        
        public void writeChar(final int v) {
            try {
                this.output.writeChar(v);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }
        
        public void writeChars(final String s) {
            try {
                this.output.writeChars(s);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }
        
        public void writeDouble(final double v) {
            try {
                this.output.writeDouble(v);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }
        
        public void writeFloat(final float v) {
            try {
                this.output.writeFloat(v);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }
        
        public void writeInt(final int v) {
            try {
                this.output.writeInt(v);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }
        
        public void writeLong(final long v) {
            try {
                this.output.writeLong(v);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }
        
        public void writeShort(final int v) {
            try {
                this.output.writeShort(v);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }
        
        public void writeUTF(final String s) {
            try {
                this.output.writeUTF(s);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }
        
        public byte[] toByteArray() {
            return this.byteArrayOutputSteam.toByteArray();
        }
    }
}
