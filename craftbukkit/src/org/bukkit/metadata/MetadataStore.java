// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.metadata;

import org.bukkit.plugin.Plugin;
import java.util.List;

public interface MetadataStore<T>
{
    void setMetadata(final T p0, final String p1, final MetadataValue p2);
    
    List<MetadataValue> getMetadata(final T p0, final String p1);
    
    boolean hasMetadata(final T p0, final String p1);
    
    void removeMetadata(final T p0, final String p1, final Plugin p2);
    
    void invalidateAll(final Plugin p0);
}
