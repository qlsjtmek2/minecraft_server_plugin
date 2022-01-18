// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.util.Version;
import org.apache.lucene.queryParser.QueryParser;
import java.util.Set;
import org.apache.lucene.document.Fieldable;
import com.avaje.ebeaninternal.server.query.SplitName;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import org.apache.lucene.analysis.Analyzer;

public abstract class LIndexFieldBase implements LIndexField
{
    protected final Analyzer queryAnalyzer;
    protected final String fieldName;
    protected final String propertyName;
    protected final int luceneType;
    protected final int sortType;
    protected final ElPropertyValue property;
    protected final ScalarType<?> scalarType;
    protected final FieldFactory fieldFactory;
    protected final boolean indexed;
    protected final boolean stored;
    protected final boolean tokenized;
    
    public LIndexFieldBase(final Analyzer queryAnalyzer, final String fieldName, final int luceneType, final ElPropertyValue property, final FieldFactory fieldFactory) {
        this.queryAnalyzer = queryAnalyzer;
        this.fieldName = fieldName;
        this.luceneType = luceneType;
        this.sortType = this.getSortType(luceneType);
        this.property = property;
        this.fieldFactory = fieldFactory;
        final Fieldable fieldPrototype = fieldFactory.createFieldable();
        this.indexed = fieldPrototype.isIndexed();
        this.stored = fieldPrototype.isStored();
        this.tokenized = fieldPrototype.isTokenized();
        if (property == null) {
            this.scalarType = null;
            this.propertyName = null;
        }
        else {
            this.scalarType = property.getBeanProperty().getScalarType();
            this.propertyName = SplitName.add(property.getElPrefix(), property.getName());
        }
    }
    
    public String toString() {
        return this.propertyName;
    }
    
    public void addIndexRequiredPropertyNames(final Set<String> requiredPropertyNames) {
        if (this.propertyName != null) {
            requiredPropertyNames.add(this.propertyName);
        }
    }
    
    public int getSortType() {
        return this.sortType;
    }
    
    public QueryParser createQueryParser() {
        return new QueryParser(Version.LUCENE_30, this.fieldName, this.queryAnalyzer);
    }
    
    public String getName() {
        return this.fieldName;
    }
    
    public boolean isIndexed() {
        return this.indexed;
    }
    
    public boolean isStored() {
        return this.stored;
    }
    
    public boolean isTokenized() {
        return this.tokenized;
    }
    
    public boolean isBeanProperty() {
        return this.property != null;
    }
    
    public int getPropertyOrder() {
        return (this.property == null) ? 0 : this.property.getDeployOrder();
    }
    
    public ElPropertyValue getElBeanProperty() {
        return this.property;
    }
    
    public void readValue(final Document doc, final Object bean) {
        Object v = this.readIndexValue(doc);
        if (v != null) {
            v = this.scalarType.luceneFromIndexValue(v);
        }
        this.property.elSetValue(bean, v, true, false);
    }
    
    protected Object readIndexValue(final Document doc) {
        final String s = doc.get(this.fieldName);
        if (s == null) {
            return null;
        }
        switch (this.luceneType) {
            case 1: {
                return Integer.parseInt(s);
            }
            case 2: {
                return Long.parseLong(s);
            }
            case 5: {
                return Long.parseLong(s);
            }
            case 6: {
                return Long.parseLong(s);
            }
            case 3: {
                return Double.parseDouble(s);
            }
            case 4: {
                return Float.parseFloat(s);
            }
            default: {
                throw new RuntimeException("Unhandled type " + this.luceneType);
            }
        }
    }
    
    private int getSortType(final int luceneType) {
        switch (luceneType) {
            case 1: {
                return 4;
            }
            case 2: {
                return 6;
            }
            case 5: {
                return 6;
            }
            case 6: {
                return 6;
            }
            case 3: {
                return 7;
            }
            case 4: {
                return 5;
            }
            case 0: {
                return 3;
            }
            default: {
                return -1;
            }
        }
    }
}
