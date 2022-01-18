// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

import java.util.Collection;

class OptionMissingRequiredArgumentException extends OptionException
{
    private static final long serialVersionUID = -1L;
    
    OptionMissingRequiredArgumentException(final Collection<String> options) {
        super(options);
    }
    
    public String getMessage() {
        return "Option " + this.multipleOptionMessage() + " requires an argument";
    }
}
