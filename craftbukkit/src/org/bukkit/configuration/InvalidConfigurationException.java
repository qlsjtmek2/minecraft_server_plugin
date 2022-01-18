// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.configuration;

public class InvalidConfigurationException extends Exception
{
    public InvalidConfigurationException() {
    }
    
    public InvalidConfigurationException(final String msg) {
        super(msg);
    }
    
    public InvalidConfigurationException(final Throwable cause) {
        super(cause);
    }
    
    public InvalidConfigurationException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
