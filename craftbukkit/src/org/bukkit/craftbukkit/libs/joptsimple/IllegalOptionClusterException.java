// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

import java.util.Collection;
import java.util.Collections;

class IllegalOptionClusterException extends OptionException
{
    private static final long serialVersionUID = -1L;
    
    IllegalOptionClusterException(final String option) {
        super(Collections.singletonList(option));
    }
    
    public String getMessage() {
        return "Option cluster containing " + this.singleOptionMessage() + " is illegal";
    }
}
