// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline.console.completer;

import java.util.List;

public final class NullCompleter implements Completer
{
    public static final NullCompleter INSTANCE;
    
    public int complete(final String buffer, final int cursor, final List<CharSequence> candidates) {
        return -1;
    }
    
    static {
        INSTANCE = new NullCompleter();
    }
}
