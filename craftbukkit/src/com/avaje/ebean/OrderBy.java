// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public final class OrderBy<T> implements Serializable
{
    private static final long serialVersionUID = 9157089257745730539L;
    private transient Query<T> query;
    private List<Property> list;
    
    public OrderBy() {
        this.list = new ArrayList<Property>(2);
    }
    
    public OrderBy(final String orderByClause) {
        this(null, orderByClause);
    }
    
    public OrderBy(final Query<T> query, final String orderByClause) {
        this.query = query;
        this.list = new ArrayList<Property>(2);
        this.parse(orderByClause);
    }
    
    public void reverse() {
        for (int i = 0; i < this.list.size(); ++i) {
            this.list.get(i).reverse();
        }
    }
    
    public Query<T> asc(final String propertyName) {
        this.list.add(new Property(propertyName, true));
        return this.query;
    }
    
    public Query<T> desc(final String propertyName) {
        this.list.add(new Property(propertyName, false));
        return this.query;
    }
    
    public List<Property> getProperties() {
        return this.list;
    }
    
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
    
    public Query<T> getQuery() {
        return this.query;
    }
    
    public void setQuery(final Query<T> query) {
        this.query = query;
    }
    
    public OrderBy<T> copy() {
        final OrderBy<T> copy = new OrderBy<T>();
        for (int i = 0; i < this.list.size(); ++i) {
            copy.add(this.list.get(i).copy());
        }
        return copy;
    }
    
    public void add(final Property p) {
        this.list.add(p);
    }
    
    public String toString() {
        return this.list.toString();
    }
    
    public String toStringFormat() {
        if (this.list.isEmpty()) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.list.size(); ++i) {
            final Property property = this.list.get(i);
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(property.toStringFormat());
        }
        return sb.toString();
    }
    
    public boolean equals(final Object obj) {
        if (!(obj instanceof OrderBy)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        final OrderBy<?> other = (OrderBy<?>)obj;
        return this.hashCode() == other.hashCode();
    }
    
    public int hashCode() {
        return this.hash();
    }
    
    public int hash() {
        int hc = OrderBy.class.getName().hashCode();
        for (int i = 0; i < this.list.size(); ++i) {
            hc = hc * 31 + this.list.get(i).hash();
        }
        return hc;
    }
    
    private void parse(final String orderByClause) {
        if (orderByClause == null) {
            return;
        }
        final String[] chunks = orderByClause.split(",");
        for (int i = 0; i < chunks.length; ++i) {
            final String[] pairs = chunks[i].split(" ");
            final Property p = this.parseProperty(pairs);
            if (p != null) {
                this.list.add(p);
            }
        }
    }
    
    private Property parseProperty(final String[] pairs) {
        if (pairs.length == 0) {
            return null;
        }
        final ArrayList<String> wordList = new ArrayList<String>(pairs.length);
        for (int i = 0; i < pairs.length; ++i) {
            if (!this.isEmptyString(pairs[i])) {
                wordList.add(pairs[i]);
            }
        }
        if (wordList.isEmpty()) {
            return null;
        }
        if (wordList.size() == 1) {
            return new Property(wordList.get(0), true);
        }
        if (wordList.size() == 2) {
            final boolean asc = this.isAscending(wordList.get(1));
            return new Property(wordList.get(0), asc);
        }
        final String m = "Expecting a max of 2 words in [" + Arrays.toString(pairs) + "] but got " + wordList.size();
        throw new RuntimeException(m);
    }
    
    private boolean isAscending(String s) {
        s = s.toLowerCase();
        if (s.startsWith("asc")) {
            return true;
        }
        if (s.startsWith("desc")) {
            return false;
        }
        final String m = "Expecting [" + s + "] to be asc or desc?";
        throw new RuntimeException(m);
    }
    
    private boolean isEmptyString(final String s) {
        return s == null || s.length() == 0;
    }
    
    public static final class Property implements Serializable
    {
        private static final long serialVersionUID = 1546009780322478077L;
        private String property;
        private boolean ascending;
        
        public Property(final String property, final boolean ascending) {
            this.property = property;
            this.ascending = ascending;
        }
        
        protected int hash() {
            int hc = this.property.hashCode();
            hc = hc * 31 + (this.ascending ? 0 : 1);
            return hc;
        }
        
        public String toString() {
            return this.toStringFormat();
        }
        
        public String toStringFormat() {
            if (this.ascending) {
                return this.property;
            }
            return this.property + " desc";
        }
        
        public void reverse() {
            this.ascending = !this.ascending;
        }
        
        public void trim(final String pathPrefix) {
            this.property = this.property.substring(pathPrefix.length() + 1);
        }
        
        public Property copy() {
            return new Property(this.property, this.ascending);
        }
        
        public String getProperty() {
            return this.property;
        }
        
        public void setProperty(final String property) {
            this.property = property;
        }
        
        public boolean isAscending() {
            return this.ascending;
        }
        
        public void setAscending(final boolean ascending) {
            this.ascending = ascending;
        }
    }
}
