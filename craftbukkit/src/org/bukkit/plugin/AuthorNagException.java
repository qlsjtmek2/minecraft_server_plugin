// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin;

public class AuthorNagException extends RuntimeException
{
    private final String message;
    
    public AuthorNagException(final String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
}
