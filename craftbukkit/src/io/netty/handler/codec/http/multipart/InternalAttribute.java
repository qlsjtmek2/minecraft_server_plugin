// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.multipart;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

final class InternalAttribute implements InterfaceHttpData
{
    private final List<String> value;
    
    InternalAttribute() {
        this.value = new ArrayList<String>();
    }
    
    @Override
    public HttpDataType getHttpDataType() {
        return HttpDataType.InternalAttribute;
    }
    
    public List<String> getValue() {
        return this.value;
    }
    
    public void addValue(final String value) {
        if (value == null) {
            throw new NullPointerException("value");
        }
        this.value.add(value);
    }
    
    public void addValue(final String value, final int rank) {
        if (value == null) {
            throw new NullPointerException("value");
        }
        this.value.add(rank, value);
    }
    
    public void setValue(final String value, final int rank) {
        if (value == null) {
            throw new NullPointerException("value");
        }
        this.value.set(rank, value);
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Attribute)) {
            return false;
        }
        final Attribute attribute = (Attribute)o;
        return this.getName().equalsIgnoreCase(attribute.getName());
    }
    
    @Override
    public int compareTo(final InterfaceHttpData o) {
        if (!(o instanceof InternalAttribute)) {
            throw new ClassCastException("Cannot compare " + this.getHttpDataType() + " with " + o.getHttpDataType());
        }
        return this.compareTo((InternalAttribute)o);
    }
    
    public int compareTo(final InternalAttribute o) {
        return this.getName().compareToIgnoreCase(o.getName());
    }
    
    public int size() {
        int size = 0;
        for (final String elt : this.value) {
            size += elt.length();
        }
        return size;
    }
    
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        for (final String elt : this.value) {
            result.append(elt);
        }
        return result.toString();
    }
    
    @Override
    public String getName() {
        return "InternalAttribute";
    }
}
