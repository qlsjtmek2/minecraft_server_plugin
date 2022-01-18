// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.core.InternString;

public class ExportedProperty
{
    private final String foreignDbColumn;
    private final BeanProperty property;
    private final boolean embedded;
    
    public ExportedProperty(final boolean embedded, final String foreignDbColumn, final BeanProperty property) {
        this.embedded = embedded;
        this.foreignDbColumn = InternString.intern(foreignDbColumn);
        this.property = property;
    }
    
    public boolean isEmbedded() {
        return this.embedded;
    }
    
    public Object getValue(final Object bean) {
        return this.property.getValue(bean);
    }
    
    public String getForeignDbColumn() {
        return this.foreignDbColumn;
    }
}
