// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SortByClause
{
    public static final String NULLSHIGH = "nullshigh";
    public static final String NULLSLOW = "nullslow";
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    private List<Property> properties;
    
    public SortByClause() {
        this.properties = new ArrayList<Property>();
    }
    
    public int size() {
        return this.properties.size();
    }
    
    public List<Property> getProperties() {
        return this.properties;
    }
    
    public void add(final Property p) {
        this.properties.add(p);
    }
    
    public static class Property implements Serializable
    {
        private static final long serialVersionUID = 7588760362420690963L;
        private final String name;
        private final boolean ascending;
        private final Boolean nullsHigh;
        
        public Property(final String name, final boolean ascending, final Boolean nullsHigh) {
            this.name = name;
            this.ascending = ascending;
            this.nullsHigh = nullsHigh;
        }
        
        public String toString() {
            return this.name + " asc:" + this.ascending;
        }
        
        public String getName() {
            return this.name;
        }
        
        public boolean isAscending() {
            return this.ascending;
        }
        
        public Boolean getNullsHigh() {
            return this.nullsHigh;
        }
    }
}
