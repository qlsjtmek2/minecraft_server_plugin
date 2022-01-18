// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import java.util.ArrayList;
import java.util.HashMap;

public class AnnotationInfo
{
    final HashMap<String, Object> valueMap;
    AnnotationInfo parent;
    
    public AnnotationInfo(final AnnotationInfo parent) {
        this.valueMap = new HashMap<String, Object>();
        this.parent = parent;
    }
    
    public String toString() {
        return this.valueMap.toString();
    }
    
    public AnnotationInfo getParent() {
        return this.parent;
    }
    
    public void setParent(final AnnotationInfo parent) {
        this.parent = parent;
    }
    
    public void add(final String prefix, final String name, final Object value) {
        if (name == null) {
            ArrayList<Object> list = this.valueMap.get(prefix);
            if (list == null) {
                list = new ArrayList<Object>();
                this.valueMap.put(prefix, list);
            }
            list.add(value);
        }
        else {
            final String key = this.getKey(prefix, name);
            this.valueMap.put(key, value);
        }
    }
    
    public void addEnum(final String prefix, final String name, final String desc, final String value) {
        this.add(prefix, name, value);
    }
    
    private String getKey(final String prefix, final String name) {
        if (prefix == null) {
            return name;
        }
        return prefix + "." + name;
    }
    
    public Object getValue(final String key) {
        Object o = this.valueMap.get(key);
        if (o == null && this.parent != null) {
            o = this.parent.getValue(key);
        }
        return o;
    }
}
