// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.metadata;

import java.util.Iterator;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import org.apache.commons.lang.Validate;
import java.util.HashMap;
import org.bukkit.plugin.Plugin;
import java.util.Map;

public abstract class MetadataStoreBase<T>
{
    private Map<String, Map<Plugin, MetadataValue>> metadataMap;
    
    public MetadataStoreBase() {
        this.metadataMap = new HashMap<String, Map<Plugin, MetadataValue>>();
    }
    
    public synchronized void setMetadata(final T subject, final String metadataKey, final MetadataValue newMetadataValue) {
        final Plugin owningPlugin = newMetadataValue.getOwningPlugin();
        Validate.notNull(newMetadataValue, "Value cannot be null");
        Validate.notNull(owningPlugin, "Plugin cannot be null");
        final String key = this.disambiguate(subject, metadataKey);
        Map<Plugin, MetadataValue> entry = this.metadataMap.get(key);
        if (entry == null) {
            entry = new WeakHashMap<Plugin, MetadataValue>(1);
            this.metadataMap.put(key, entry);
        }
        entry.put(owningPlugin, newMetadataValue);
    }
    
    public synchronized List<MetadataValue> getMetadata(final T subject, final String metadataKey) {
        final String key = this.disambiguate(subject, metadataKey);
        if (this.metadataMap.containsKey(key)) {
            final Collection<MetadataValue> values = this.metadataMap.get(key).values();
            return Collections.unmodifiableList((List<? extends MetadataValue>)new ArrayList<MetadataValue>(values));
        }
        return Collections.emptyList();
    }
    
    public synchronized boolean hasMetadata(final T subject, final String metadataKey) {
        final String key = this.disambiguate(subject, metadataKey);
        return this.metadataMap.containsKey(key);
    }
    
    public synchronized void removeMetadata(final T subject, final String metadataKey, final Plugin owningPlugin) {
        Validate.notNull(owningPlugin, "Plugin cannot be null");
        final String key = this.disambiguate(subject, metadataKey);
        final Map<Plugin, MetadataValue> entry = this.metadataMap.get(key);
        if (entry == null) {
            return;
        }
        entry.remove(owningPlugin);
        if (entry.isEmpty()) {
            this.metadataMap.remove(key);
        }
    }
    
    public synchronized void invalidateAll(final Plugin owningPlugin) {
        Validate.notNull(owningPlugin, "Plugin cannot be null");
        for (final Map<Plugin, MetadataValue> values : this.metadataMap.values()) {
            if (values.containsKey(owningPlugin)) {
                values.get(owningPlugin).invalidate();
            }
        }
    }
    
    protected abstract String disambiguate(final T p0, final String p1);
}
