// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.logging.Logger;

public interface IConsoleLogManager
{
    Logger getLogger();
    
    void info(final String p0);
    
    void warning(final String p0);
    
    void warning(final String p0, final Object... p1);
    
    void warning(final String p0, final Throwable p1);
    
    void severe(final String p0);
    
    void severe(final String p0, final Throwable p1);
}
