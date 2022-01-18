// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.metadata;

import org.bukkit.plugin.Plugin;
import java.util.List;

public interface Metadatable
{
    void setMetadata(final String p0, final MetadataValue p1);
    
    List<MetadataValue> getMetadata(final String p0);
    
    boolean hasMetadata(final String p0);
    
    void removeMetadata(final String p0, final Plugin p1);
}
