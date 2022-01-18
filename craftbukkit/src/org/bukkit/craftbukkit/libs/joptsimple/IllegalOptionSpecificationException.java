// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

import java.util.Collection;
import java.util.Collections;

class IllegalOptionSpecificationException extends OptionException
{
    private static final long serialVersionUID = -1L;
    
    IllegalOptionSpecificationException(final String option) {
        super(Collections.singletonList(option));
    }
    
    public String getMessage() {
        return this.singleOptionMessage() + " is not a legal option character";
    }
}
