// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Collection;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class InsensitiveStringMap implements Map
{
    private final Map a;
    
    public InsensitiveStringMap() {
        this.a = new LinkedHashMap();
    }
    
    public int size() {
        return this.a.size();
    }
    
    public boolean isEmpty() {
        return this.a.isEmpty();
    }
    
    public boolean containsKey(final Object o) {
        return this.a.containsKey(o.toString().toLowerCase());
    }
    
    public boolean containsValue(final Object o) {
        return this.a.containsKey(o);
    }
    
    public Object get(final Object o) {
        return this.a.get(o.toString().toLowerCase());
    }
    
    public Object put(final String s, final Object o) {
        return this.a.put(s.toLowerCase(), o);
    }
    
    public Object remove(final Object o) {
        return this.a.remove(o.toString().toLowerCase());
    }
    
    public void putAll(final Map map) {
        for (final Entry<String, V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }
    
    public void clear() {
        this.a.clear();
    }
    
    public Set keySet() {
        return this.a.keySet();
    }
    
    public Collection values() {
        return this.a.values();
    }
    
    public Set entrySet() {
        return this.a.entrySet();
    }
}
