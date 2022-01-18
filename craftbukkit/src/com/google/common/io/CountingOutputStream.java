// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.io;

import java.io.IOException;
import java.io.OutputStream;
import com.google.common.annotations.Beta;
import java.io.FilterOutputStream;

@Beta
public final class CountingOutputStream extends FilterOutputStream
{
    private long count;
    
    public CountingOutputStream(final OutputStream out) {
        super(out);
    }
    
    public long getCount() {
        return this.count;
    }
    
    public void write(final byte[] b, final int off, final int len) throws IOException {
        this.out.write(b, off, len);
        this.count += len;
    }
    
    public void write(final int b) throws IOException {
        this.out.write(b);
        ++this.count;
    }
}
