// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import org.apache.lucene.document.Fieldable;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import java.util.Set;
import org.apache.lucene.index.Term;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import org.apache.lucene.analysis.Analyzer;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;

public final class LIndexFieldId extends LIndexFieldBase
{
    private final IdBinder idBinder;
    
    public LIndexFieldId(final Analyzer queryAnalyzer, final String fieldName, final FieldFactory fieldFactory, final ElPropertyValue property, final IdBinder idBinder) {
        super(queryAnalyzer, fieldName, 0, property, fieldFactory);
        this.idBinder = idBinder;
    }
    
    public Term createTerm(final Object id) {
        final String termVal = this.idBinder.writeTerm(id);
        return new Term(this.fieldName, termVal);
    }
    
    public void addIndexResolvePropertyNames(final Set<String> resolvePropertyNames) {
        if (this.propertyName != null && this.isIndexed()) {
            resolvePropertyNames.add(this.propertyName);
        }
    }
    
    public void addIndexRestorePropertyNames(final Set<String> restorePropertyNames) {
        if (this.propertyName != null && this.isStored()) {
            restorePropertyNames.add(this.propertyName);
        }
    }
    
    public String getSortableProperty() {
        if (this.isIndexed() && !this.isTokenized()) {
            return this.propertyName;
        }
        return null;
    }
    
    public void readValue(final Document doc, final Object bean) {
        final String v = doc.get(this.fieldName);
        if (v != null) {
            final Object id = this.idBinder.readTerm(v);
            this.property.elSetValue(bean, id, true, false);
        }
    }
    
    public DocFieldWriter createDocFieldWriter() {
        final Field f = (Field)this.fieldFactory.createFieldable();
        return new Writer(this.property, f, this.idBinder);
    }
    
    private static class Writer implements DocFieldWriter
    {
        private final IdBinder idBinder;
        private final ElPropertyValue property;
        private final Field field;
        
        Writer(final ElPropertyValue property, final Field field, final IdBinder idBinder) {
            this.property = property;
            this.field = field;
            this.idBinder = idBinder;
        }
        
        public void writeValue(final Object bean, final Document document) {
            final Object value = this.property.elGetValue(bean);
            if (value != null) {
                System.out.println("- write " + this.field.name() + " " + value);
                final String writeTerm = this.idBinder.writeTerm(value);
                this.field.setValue(writeTerm);
                document.add((Fieldable)this.field);
            }
        }
    }
}
