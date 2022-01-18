// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.io;

import com.google.common.annotations.Beta;
import java.io.OutputStream;

@Beta
public final class NullOutputStream extends OutputStream
{
    public void write(final int b) {
    }
    
    public void write(final byte[] b, final int off, final int len) {
    }
}
