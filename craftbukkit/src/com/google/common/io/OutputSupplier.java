// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.io;

import java.io.IOException;

public interface OutputSupplier<T>
{
    T getOutput() throws IOException;
}
