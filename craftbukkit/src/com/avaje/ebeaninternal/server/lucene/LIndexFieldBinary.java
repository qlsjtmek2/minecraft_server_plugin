// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import org.apache.lucene.document.Fieldable;
import com.avaje.ebeaninternal.server.type.ScalarType;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import java.util.Set;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import org.apache.lucene.analysis.Analyzer;

public final class LIndexFieldBinary extends LIndexFieldBase
{
    public LIndexFieldBinary(final Analyzer queryAnalyzer, final String fieldName, final FieldFactory fieldFactory, final ElPropertyValue property) {
        super(queryAnalyzer, fieldName, 7, property, fieldFactory);
    }
    
    public void addIndexResolvePropertyNames(final Set<String> resolvePropertyNames) {
    }
    
    public void addIndexRestorePropertyNames(final Set<String> restorePropertyNames) {
        if (this.propertyName != null && this.isStored()) {
            restorePropertyNames.add(this.propertyName);
        }
    }
    
    public String getSortableProperty() {
        return null;
    }
    
    public void readValue(final Document doc, final Object bean) {
        Object v = doc.get(this.fieldName);
        if (v != null) {
            v = this.scalarType.luceneFromIndexValue(v);
        }
        this.property.elSetValue(bean, v, true, false);
    }
    
    public DocFieldWriter createDocFieldWriter() {
        final Field f = (Field)this.fieldFactory.createFieldable();
        return new Writer(this.property, (ScalarType)this.scalarType, f);
    }
    
    private static class Writer implements DocFieldWriter
    {
        private final ElPropertyValue property;
        private final ScalarType<?> scalarType;
        private final Field field;
        
        private Writer(final ElPropertyValue property, final ScalarType<?> scalarType, final Field field) {
            this.property = property;
            this.scalarType = scalarType;
            this.field = field;
        }
        
        public void writeValue(final Object bean, final Document document) {
            final Object value = this.property.elGetValue(bean);
            if (value != null) {
                final byte[] s = (byte[])this.scalarType.luceneToIndexValue(value);
                this.field.setValue(s);
                document.add((Fieldable)this.field);
            }
        }
    }
}
