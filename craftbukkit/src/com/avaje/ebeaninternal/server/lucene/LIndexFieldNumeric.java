// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import org.apache.lucene.document.Fieldable;
import org.apache.lucene.document.Document;
import com.avaje.ebeaninternal.server.type.ScalarType;
import org.apache.lucene.document.NumericField;
import java.util.Set;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import org.apache.lucene.analysis.Analyzer;

public final class LIndexFieldNumeric extends LIndexFieldBase
{
    public LIndexFieldNumeric(final Analyzer queryAnalyzer, final String fieldName, final FieldFactory fieldFactory, final int luceneType, final ElPropertyValue property) {
        super(queryAnalyzer, fieldName, luceneType, property, fieldFactory);
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
        return this.isIndexed() ? this.propertyName : null;
    }
    
    public DocFieldWriter createDocFieldWriter() {
        final NumericField f = (NumericField)this.fieldFactory.createFieldable();
        return new Writer(this.luceneType, this.property, this.scalarType, f);
    }
    
    private static class Writer implements DocFieldWriter
    {
        private final int luceneType;
        private final ElPropertyValue property;
        private final ScalarType<?> scalarType;
        private final NumericField field;
        
        Writer(final int luceneType, final ElPropertyValue property, final ScalarType<?> scalarType, final NumericField field) {
            this.luceneType = luceneType;
            this.property = property;
            this.scalarType = scalarType;
            this.field = field;
        }
        
        public void writeValue(final Object bean, final Document document) {
            Object value = this.property.elGetValue(bean);
            if (value != null) {
                System.out.println("- write " + this.field.name() + " " + value);
                value = this.scalarType.luceneToIndexValue(value);
                this.setValueToField(value);
                document.add((Fieldable)this.field);
            }
        }
        
        protected void setValueToField(final Object value) {
            switch (this.luceneType) {
                case 1: {
                    this.field.setIntValue((int)value);
                    break;
                }
                case 2: {
                    this.field.setLongValue((long)value);
                    break;
                }
                case 5: {
                    this.field.setLongValue((long)value);
                    break;
                }
                case 6: {
                    this.field.setLongValue((long)value);
                    break;
                }
                case 3: {
                    this.field.setDoubleValue((double)value);
                    break;
                }
                case 4: {
                    this.field.setFloatValue((float)value);
                    break;
                }
                default: {
                    throw new RuntimeException("Unhandled type " + this.luceneType);
                }
            }
        }
    }
}
