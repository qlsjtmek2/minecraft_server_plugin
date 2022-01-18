// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline.console.completer;

import java.io.IOException;
import java.util.List;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;

public interface CompletionHandler
{
    boolean complete(final ConsoleReader p0, final List<CharSequence> p1, final int p2) throws IOException;
}
