// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline.console.history;

import java.io.IOException;

public interface PersistentHistory extends History
{
    void flush() throws IOException;
    
    void purge() throws IOException;
}
