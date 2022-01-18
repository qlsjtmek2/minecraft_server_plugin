// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text.json;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.HashMap;
import com.avaje.ebean.text.PathProperties;
import java.util.Map;

public class JsonWriteOptions
{
    protected String callback;
    protected JsonValueAdapter valueAdapter;
    protected Map<String, JsonWriteBeanVisitor<?>> visitorMap;
    protected PathProperties pathProperties;
    
    public JsonWriteOptions copy() {
        final JsonWriteOptions copy = new JsonWriteOptions();
        copy.callback = this.callback;
        copy.valueAdapter = this.valueAdapter;
        copy.pathProperties = this.pathProperties;
        if (this.visitorMap != null) {
            copy.visitorMap = new HashMap<String, JsonWriteBeanVisitor<?>>(this.visitorMap);
        }
        return copy;
    }
    
    public String getCallback() {
        return this.callback;
    }
    
    public JsonWriteOptions setCallback(final String callback) {
        this.callback = callback;
        return this;
    }
    
    public JsonValueAdapter getValueAdapter() {
        return this.valueAdapter;
    }
    
    public JsonWriteOptions setValueAdapter(final JsonValueAdapter valueAdapter) {
        this.valueAdapter = valueAdapter;
        return this;
    }
    
    public JsonWriteOptions setRootPathVisitor(final JsonWriteBeanVisitor<?> visitor) {
        return this.setPathVisitor(null, visitor);
    }
    
    public JsonWriteOptions setPathVisitor(final String path, final JsonWriteBeanVisitor<?> visitor) {
        if (this.visitorMap == null) {
            this.visitorMap = new HashMap<String, JsonWriteBeanVisitor<?>>();
        }
        this.visitorMap.put(path, visitor);
        return this;
    }
    
    public JsonWriteOptions setPathProperties(final String path, final Set<String> propertiesToInclude) {
        if (this.pathProperties == null) {
            this.pathProperties = new PathProperties();
        }
        this.pathProperties.put(path, propertiesToInclude);
        return this;
    }
    
    public JsonWriteOptions setPathProperties(final String path, final String propertiesToInclude) {
        return this.setPathProperties(path, this.parseProps(propertiesToInclude));
    }
    
    public JsonWriteOptions setRootPathProperties(final String propertiesToInclude) {
        return this.setPathProperties(null, this.parseProps(propertiesToInclude));
    }
    
    public JsonWriteOptions setRootPathProperties(final Set<String> propertiesToInclude) {
        return this.setPathProperties(null, propertiesToInclude);
    }
    
    private Set<String> parseProps(final String propertiesToInclude) {
        final LinkedHashSet<String> props = new LinkedHashSet<String>();
        final String[] split = propertiesToInclude.split(",");
        for (int i = 0; i < split.length; ++i) {
            final String s = split[i].trim();
            if (s.length() > 0) {
                props.add(s);
            }
        }
        return props;
    }
    
    public Map<String, JsonWriteBeanVisitor<?>> getVisitorMap() {
        return this.visitorMap;
    }
    
    public void setPathProperties(final PathProperties pathProperties) {
        this.pathProperties = pathProperties;
    }
    
    public PathProperties getPathProperties() {
        return this.pathProperties;
    }
}
