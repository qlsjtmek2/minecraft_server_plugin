// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text;

import com.avaje.ebean.Query;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.Map;

public class PathProperties
{
    private final Map<String, Props> pathMap;
    private final Props rootProps;
    
    public static PathProperties parse(final String source) {
        return PathPropertiesParser.parse(source);
    }
    
    public PathProperties() {
        this.rootProps = new Props(this, (String)null, (String)null);
        (this.pathMap = new LinkedHashMap<String, Props>()).put(null, this.rootProps);
    }
    
    private PathProperties(final PathProperties orig) {
        this.rootProps = orig.rootProps.copy(this);
        this.pathMap = new LinkedHashMap<String, Props>(orig.pathMap.size());
        final Set<Map.Entry<String, Props>> entrySet = orig.pathMap.entrySet();
        for (final Map.Entry<String, Props> e : entrySet) {
            this.pathMap.put(e.getKey(), e.getValue().copy(this));
        }
    }
    
    public PathProperties copy() {
        return new PathProperties(this);
    }
    
    public boolean isEmpty() {
        return this.pathMap.isEmpty();
    }
    
    public String toString() {
        return this.pathMap.toString();
    }
    
    public boolean hasPath(final String path) {
        final Props props = this.pathMap.get(path);
        return props != null && !props.isEmpty();
    }
    
    public Set<String> get(final String path) {
        final Props props = this.pathMap.get(path);
        return (props == null) ? null : props.getProperties();
    }
    
    public void addToPath(final String path, final String property) {
        Props props = this.pathMap.get(path);
        if (props == null) {
            props = new Props(this, (String)null, path);
            this.pathMap.put(path, props);
        }
        props.getProperties().add(property);
    }
    
    public void put(final String path, final Set<String> properties) {
        this.pathMap.put(path, new Props(this, (String)null, path, (Set)properties));
    }
    
    public Set<String> remove(final String path) {
        final Props props = this.pathMap.remove(path);
        return (props == null) ? null : props.getProperties();
    }
    
    public Set<String> getPaths() {
        return new LinkedHashSet<String>(this.pathMap.keySet());
    }
    
    public Collection<Props> getPathProps() {
        return this.pathMap.values();
    }
    
    public void apply(final Query<?> query) {
        for (final Map.Entry<String, Props> entry : this.pathMap.entrySet()) {
            final String path = entry.getKey();
            final String props = entry.getValue().getPropertiesAsString();
            if (path == null || path.length() == 0) {
                query.select(props);
            }
            else {
                query.fetch(path, props);
            }
        }
    }
    
    protected Props getRootProperties() {
        return this.rootProps;
    }
    
    public static class Props
    {
        private final PathProperties owner;
        private final String parentPath;
        private final String path;
        private final Set<String> propSet;
        
        private Props(final PathProperties owner, final String parentPath, final String path, final Set<String> propSet) {
            this.owner = owner;
            this.path = path;
            this.parentPath = parentPath;
            this.propSet = propSet;
        }
        
        private Props(final PathProperties owner, final String parentPath, final String path) {
            this(owner, parentPath, path, new LinkedHashSet<String>());
        }
        
        public Props copy(final PathProperties newOwner) {
            return new Props(newOwner, this.parentPath, this.path, new LinkedHashSet<String>(this.propSet));
        }
        
        public String getPath() {
            return this.path;
        }
        
        public String toString() {
            return this.propSet.toString();
        }
        
        public boolean isEmpty() {
            return this.propSet.isEmpty();
        }
        
        public Set<String> getProperties() {
            return this.propSet;
        }
        
        public String getPropertiesAsString() {
            final StringBuilder sb = new StringBuilder();
            final Iterator<String> it = this.propSet.iterator();
            boolean hasNext = it.hasNext();
            while (hasNext) {
                sb.append(it.next());
                hasNext = it.hasNext();
                if (hasNext) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }
        
        protected Props getParent() {
            return this.owner.pathMap.get(this.parentPath);
        }
        
        protected Props addChild(String subpath) {
            subpath = subpath.trim();
            this.addProperty(subpath);
            final String p = (this.path == null) ? subpath : (this.path + "." + subpath);
            final Props nested = new Props(this.owner, this.path, p);
            this.owner.pathMap.put(p, nested);
            return nested;
        }
        
        protected void addProperty(final String property) {
            this.propSet.add(property.trim());
        }
    }
}
