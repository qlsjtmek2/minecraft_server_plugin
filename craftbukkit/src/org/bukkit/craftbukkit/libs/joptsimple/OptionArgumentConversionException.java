// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

import java.util.Collection;

class OptionArgumentConversionException extends OptionException
{
    private static final long serialVersionUID = -1L;
    private final String argument;
    private final Class<?> valueType;
    
    OptionArgumentConversionException(final Collection<String> options, final String argument, final Class<?> valueType, final Throwable cause) {
        super(options, cause);
        this.argument = argument;
        this.valueType = valueType;
    }
    
    public String getMessage() {
        return "Cannot convert argument '" + this.argument + "' of option " + this.multipleOptionMessage() + " to " + this.valueType;
    }
}
