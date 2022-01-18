// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin;

public class InvalidDescriptionException extends Exception
{
    private static final long serialVersionUID = 5721389122281775896L;
    
    public InvalidDescriptionException(final Throwable cause, final String message) {
        super(message, cause);
    }
    
    public InvalidDescriptionException(final Throwable cause) {
        super("Invalid plugin.yml", cause);
    }
    
    public InvalidDescriptionException(final String message) {
        super(message);
    }
    
    public InvalidDescriptionException() {
        super("Invalid plugin.yml");
    }
}
