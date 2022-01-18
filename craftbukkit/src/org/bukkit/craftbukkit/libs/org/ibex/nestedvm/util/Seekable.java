// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;

public abstract class Seekable
{
    public abstract int read(final byte[] p0, final int p1, final int p2) throws IOException;
    
    public abstract int write(final byte[] p0, final int p1, final int p2) throws IOException;
    
    public abstract int length() throws IOException;
    
    public abstract void seek(final int p0) throws IOException;
    
    public abstract void close() throws IOException;
    
    public abstract int pos() throws IOException;
    
    public void sync() throws IOException {
        throw new IOException("sync not implemented for " + this.getClass());
    }
    
    public void resize(final long n) throws IOException {
        throw new IOException("resize not implemented for " + this.getClass());
    }
    
    public Lock lock(final long n, final long n2, final boolean b) throws IOException {
        throw new IOException("lock not implemented for " + this.getClass());
    }
    
    public int read() throws IOException {
        final byte[] array = { 0 };
        return (this.read(array, 0, 1) == -1) ? -1 : (array[0] & 0xFF);
    }
    
    public int tryReadFully(final byte[] array, int n, int i) throws IOException {
        int n2;
        int read;
        for (n2 = 0; i > 0; i -= read, n2 += read) {
            read = this.read(array, n, i);
            if (read == -1) {
                break;
            }
            n += read;
        }
        return (n2 == 0) ? -1 : n2;
    }
    
    public static class ByteArray extends Seekable
    {
        protected byte[] data;
        protected int pos;
        private final boolean writable;
        
        public ByteArray(final byte[] data, final boolean writable) {
            this.data = data;
            this.pos = 0;
            this.writable = writable;
        }
        
        public int read(final byte[] array, final int n, int min) {
            min = Math.min(min, this.data.length - this.pos);
            if (min <= 0) {
                return -1;
            }
            System.arraycopy(this.data, this.pos, array, n, min);
            this.pos += min;
            return min;
        }
        
        public int write(final byte[] array, final int n, int min) throws IOException {
            if (!this.writable) {
                throw new IOException("read-only data");
            }
            min = Math.min(min, this.data.length - this.pos);
            if (min <= 0) {
                throw new IOException("no space");
            }
            System.arraycopy(array, n, this.data, this.pos, min);
            this.pos += min;
            return min;
        }
        
        public int length() {
            return this.data.length;
        }
        
        public int pos() {
            return this.pos;
        }
        
        public void seek(final int pos) {
            this.pos = pos;
        }
        
        public void close() {
        }
    }
    
    public static class File extends Seekable
    {
        private final java.io.File file;
        private final RandomAccessFile raf;
        
        public File(final String s) throws IOException {
            this(s, false);
        }
        
        public File(final String s, final boolean b) throws IOException {
            this(new java.io.File(s), b, false);
        }
        
        public File(final java.io.File file, final boolean b, final boolean b2) throws IOException {
            this.file = file;
            this.raf = new RandomAccessFile(file, b ? "rw" : "r");
            if (b2) {
                Platform.setFileLength(this.raf, 0);
            }
        }
        
        public int read(final byte[] array, final int n, final int n2) throws IOException {
            return this.raf.read(array, n, n2);
        }
        
        public int write(final byte[] array, final int n, final int n2) throws IOException {
            this.raf.write(array, n, n2);
            return n2;
        }
        
        public void sync() throws IOException {
            this.raf.getFD().sync();
        }
        
        public void seek(final int n) throws IOException {
            this.raf.seek(n);
        }
        
        public int pos() throws IOException {
            return (int)this.raf.getFilePointer();
        }
        
        public int length() throws IOException {
            return (int)this.raf.length();
        }
        
        public void close() throws IOException {
            this.raf.close();
        }
        
        public void resize(final long n) throws IOException {
            Platform.setFileLength(this.raf, (int)n);
        }
        
        public boolean equals(final Object o) {
            return o != null && o instanceof File && this.file.equals(((File)o).file);
        }
        
        public Lock lock(final long n, final long n2, final boolean b) throws IOException {
            return Platform.lockFile(this, this.raf, n, n2, b);
        }
    }
    
    public static class InputStream extends Seekable
    {
        private byte[] buffer;
        private int bytesRead;
        private boolean eof;
        private int pos;
        private java.io.InputStream is;
        
        public InputStream(final java.io.InputStream is) {
            this.buffer = new byte[4096];
            this.bytesRead = 0;
            this.eof = false;
            this.is = is;
        }
        
        public int read(final byte[] array, final int n, int min) throws IOException {
            if (this.pos >= this.bytesRead && !this.eof) {
                this.readTo(this.pos + 1);
            }
            min = Math.min(min, this.bytesRead - this.pos);
            if (min <= 0) {
                return -1;
            }
            System.arraycopy(this.buffer, this.pos, array, n, min);
            this.pos += min;
            return min;
        }
        
        private void readTo(final int n) throws IOException {
            if (n >= this.buffer.length) {
                final byte[] buffer = new byte[Math.max(this.buffer.length + Math.min(this.buffer.length, 65536), n)];
                System.arraycopy(this.buffer, 0, buffer, 0, this.bytesRead);
                this.buffer = buffer;
            }
            while (this.bytesRead < n) {
                final int read = this.is.read(this.buffer, this.bytesRead, this.buffer.length - this.bytesRead);
                if (read == -1) {
                    this.eof = true;
                    break;
                }
                this.bytesRead += read;
            }
        }
        
        public int length() throws IOException {
            while (!this.eof) {
                this.readTo(this.bytesRead + 4096);
            }
            return this.bytesRead;
        }
        
        public int write(final byte[] array, final int n, final int n2) throws IOException {
            throw new IOException("read-only");
        }
        
        public void seek(final int pos) {
            this.pos = pos;
        }
        
        public int pos() {
            return this.pos;
        }
        
        public void close() throws IOException {
            this.is.close();
        }
    }
    
    public abstract static class Lock
    {
        private Object owner;
        
        public Lock() {
            this.owner = null;
        }
        
        public abstract Seekable seekable();
        
        public abstract boolean isShared();
        
        public abstract boolean isValid();
        
        public abstract void release() throws IOException;
        
        public abstract long position();
        
        public abstract long size();
        
        public void setOwner(final Object owner) {
            this.owner = owner;
        }
        
        public Object getOwner() {
            return this.owner;
        }
        
        public final boolean contains(final int n, final int n2) {
            return n >= this.position() && this.position() + this.size() >= n + n2;
        }
        
        public final boolean contained(final int n, final int n2) {
            return n < this.position() && this.position() + this.size() < n + n2;
        }
        
        public final boolean overlaps(final int n, final int n2) {
            return this.contains(n, n2) || this.contained(n, n2);
        }
    }
}
