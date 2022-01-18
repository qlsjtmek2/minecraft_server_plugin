// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

import java.util.Collection;
import java.util.List;

public interface OptionSpec<V>
{
    List<V> values(final OptionSet p0);
    
    V value(final OptionSet p0);
    
    Collection<String> options();
}
