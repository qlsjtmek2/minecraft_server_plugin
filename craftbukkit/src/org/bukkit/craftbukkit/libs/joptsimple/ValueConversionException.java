// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

public class ValueConversionException extends RuntimeException
{
    private static final long serialVersionUID = -1L;
    
    public ValueConversionException(final String message) {
        this(message, null);
    }
    
    public ValueConversionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
