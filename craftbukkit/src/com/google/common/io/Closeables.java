// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.io;

import java.io.IOException;
import java.util.logging.Level;
import javax.annotation.Nullable;
import java.io.Closeable;
import java.util.logging.Logger;
import com.google.common.annotations.Beta;

@Beta
public final class Closeables
{
    private static final Logger logger;
    
    public static void close(@Nullable final Closeable closeable, final boolean swallowIOException) throws IOException {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        }
        catch (IOException e) {
            if (!swallowIOException) {
                throw e;
            }
            Closeables.logger.log(Level.WARNING, "IOException thrown while closing Closeable.", e);
        }
    }
    
    public static void closeQuietly(@Nullable final Closeable closeable) {
        try {
            close(closeable, true);
        }
        catch (IOException e) {
            Closeables.logger.log(Level.SEVERE, "IOException should not have been thrown.", e);
        }
    }
    
    static {
        logger = Logger.getLogger(Closeables.class.getName());
    }
}
