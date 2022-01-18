// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Fieldable;
import java.io.Reader;
import java.io.StringReader;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import com.avaje.ebeaninternal.server.query.SplitName;
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;

public final class LIndexFieldStringConcat extends LIndexFieldBase
{
    private final ElPropertyValue[] properties;
    private final ScalarType<?>[] scalarTypes;
    private final Analyzer indexAnalyzer;
    
    public LIndexFieldStringConcat(final Analyzer queryAnalyzer, final String fieldName, final FieldFactory fieldFactory, final ElPropertyValue[] properties, final Analyzer indexAnalyzer) {
        super(queryAnalyzer, fieldName, 0, null, fieldFactory);
        this.properties = properties;
        this.indexAnalyzer = indexAnalyzer;
        this.scalarTypes = (ScalarType<?>[])new ScalarType[properties.length];
        for (int i = 0; i < this.scalarTypes.length; ++i) {
            this.scalarTypes[i] = properties[i].getBeanProperty().getScalarType();
        }
    }
    
    public void addIndexRequiredPropertyNames(final Set<String> requiredPropertyNames) {
        for (int i = 0; i < this.properties.length; ++i) {
            final String prefix = this.properties[i].getElPrefix();
            final String name = this.properties[i].getName();
            final String fullPath = SplitName.add(prefix, name);
            requiredPropertyNames.add(fullPath);
        }
    }
    
    public void addIndexResolvePropertyNames(final Set<String> resolvePropertyNames) {
    }
    
    public void addIndexRestorePropertyNames(final Set<String> restorePropertyNames) {
    }
    
    public String getSortableProperty() {
        return null;
    }
    
    public boolean isBeanProperty() {
        return false;
    }
    
    public void readValue(final Document doc, final Object bean) {
    }
    
    public DocFieldWriter createDocFieldWriter() {
        final Field f = (Field)this.fieldFactory.createFieldable();
        return new Writer(f, this.properties, (ScalarType[])this.scalarTypes, this.indexAnalyzer);
    }
    
    private static class Writer implements DocFieldWriter
    {
        private final Field field;
        private final ElPropertyValue[] properties;
        private final ScalarType<?>[] scalarTypes;
        private final Analyzer indexAnalyzer;
        
        private Writer(final Field field, final ElPropertyValue[] properties, final ScalarType<?>[] scalarTypes, final Analyzer indexAnalyzer) {
            this.field = field;
            this.properties = properties;
            this.scalarTypes = scalarTypes;
            this.indexAnalyzer = indexAnalyzer;
        }
        
        public void writeValue(final Object bean, final Document document) {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.properties.length; ++i) {
                final Object value = this.properties[i].elGetValue(bean);
                if (value != null) {
                    final String s = (String)this.scalarTypes[i].luceneToIndexValue(value);
                    sb.append(s);
                    sb.append(" ");
                }
            }
            final String s2 = sb.toString();
            if (this.indexAnalyzer == null) {
                this.field.setValue(s2);
            }
            else {
                final StringReader sr = new StringReader(s2);
                final TokenStream tokenStream = this.indexAnalyzer.tokenStream(this.field.name(), (Reader)sr);
                this.field.setTokenStream(tokenStream);
            }
            document.add((Fieldable)this.field);
        }
    }
}
