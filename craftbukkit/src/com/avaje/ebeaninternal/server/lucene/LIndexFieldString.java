// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Fieldable;
import java.io.Reader;
import java.io.StringReader;
import com.avaje.ebeaninternal.server.type.ScalarType;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import java.util.Set;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import org.apache.lucene.analysis.Analyzer;

public final class LIndexFieldString extends LIndexFieldBase
{
    private final Analyzer indexAnalyzer;
    
    public LIndexFieldString(final Analyzer queryAnalyzer, final String fieldName, final FieldFactory fieldFactory, final ElPropertyValue property, final Analyzer indexAnalyzer) {
        super(queryAnalyzer, fieldName, 0, property, fieldFactory);
        this.indexAnalyzer = indexAnalyzer;
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
        Object v = doc.get(this.fieldName);
        if (v != null) {
            v = this.scalarType.luceneFromIndexValue(v);
        }
        this.property.elSetValue(bean, v, true, false);
    }
    
    public DocFieldWriter createDocFieldWriter() {
        final Field f = (Field)this.fieldFactory.createFieldable();
        return new Writer(this.property, this.scalarType, f, this.indexAnalyzer);
    }
    
    private static class Writer implements DocFieldWriter
    {
        private final ElPropertyValue property;
        private final ScalarType<?> scalarType;
        private final Field field;
        private final Analyzer indexAnalyzer;
        
        Writer(final ElPropertyValue property, final ScalarType<?> scalarType, final Field field, final Analyzer indexAnalyzer) {
            this.property = property;
            this.scalarType = scalarType;
            this.field = field;
            this.indexAnalyzer = indexAnalyzer;
        }
        
        public void writeValue(final Object bean, final Document document) {
            final Object value = this.property.elGetValue(bean);
            if (value != null) {
                System.out.println("- write " + this.field.name() + " " + value);
                final String s = (String)this.scalarType.luceneToIndexValue(value);
                if (this.indexAnalyzer == null) {
                    this.field.setValue(s);
                }
                else {
                    final StringReader sr = new StringReader(s);
                    final TokenStream tokenStream = this.indexAnalyzer.tokenStream(this.field.name(), (Reader)sr);
                    this.field.setTokenStream(tokenStream);
                }
                document.add((Fieldable)this.field);
            }
        }
    }
}
