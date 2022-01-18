// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Terminal
{
    void init() throws Exception;
    
    void restore() throws Exception;
    
    void reset() throws Exception;
    
    boolean isSupported();
    
    int getWidth();
    
    int getHeight();
    
    boolean isAnsiSupported();
    
    OutputStream wrapOutIfNeeded(final OutputStream p0);
    
    InputStream wrapInIfNeeded(final InputStream p0) throws IOException;
    
    boolean hasWeirdWrap();
    
    boolean isEchoEnabled();
    
    void setEchoEnabled(final boolean p0);
}
