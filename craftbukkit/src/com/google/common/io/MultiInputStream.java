// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.io;

import java.io.IOException;
import java.util.Iterator;
import java.io.InputStream;

final class MultiInputStream extends InputStream
{
    private Iterator<? extends InputSupplier<? extends InputStream>> it;
    private InputStream in;
    
    public MultiInputStream(final Iterator<? extends InputSupplier<? extends InputStream>> it) throws IOException {
        this.it = it;
        this.advance();
    }
    
    public void close() throws IOException {
        if (this.in != null) {
            try {
                this.in.close();
            }
            finally {
                this.in = null;
            }
        }
    }
    
    private void advance() throws IOException {
        this.close();
        if (this.it.hasNext()) {
            this.in = ((InputSupplier)this.it.next()).getInput();
        }
    }
    
    public int available() throws IOException {
        if (this.in == null) {
            return 0;
        }
        return this.in.available();
    }
    
    public boolean markSupported() {
        return false;
    }
    
    public int read() throws IOException {
        if (this.in == null) {
            return -1;
        }
        final int result = this.in.read();
        if (result == -1) {
            this.advance();
            return this.read();
        }
        return result;
    }
    
    public int read(final byte[] b, final int off, final int len) throws IOException {
        if (this.in == null) {
            return -1;
        }
        final int result = this.in.read(b, off, len);
        if (result == -1) {
            this.advance();
            return this.read(b, off, len);
        }
        return result;
    }
    
    public long skip(final long n) throws IOException {
        if (this.in == null || n <= 0L) {
            return 0L;
        }
        final long result = this.in.skip(n);
        if (result != 0L) {
            return result;
        }
        if (this.read() == -1) {
            return 0L;
        }
        return 1L + this.in.skip(n - 1L);
    }
}
