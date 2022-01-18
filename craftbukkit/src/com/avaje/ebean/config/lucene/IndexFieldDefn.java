// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Field;

public class IndexFieldDefn
{
    protected final String name;
    protected String propertyName;
    protected Field.Index index;
    protected Field.Store store;
    protected Sortable sortable;
    protected int precisionStep;
    protected float boost;
    protected Analyzer queryAnalyzer;
    protected Analyzer indexAnalyzer;
    protected String[] properties;
    
    public IndexFieldDefn(final String name) {
        this.precisionStep = -1;
        this.name = name;
        this.propertyName = name;
    }
    
    public IndexFieldDefn(final String name, final Field.Store store, final Field.Index index, final Sortable sortable) {
        this(name);
        this.store = store;
        this.index = index;
        this.sortable = sortable;
    }
    
    public String toString() {
        return this.name;
    }
    
    public IndexFieldDefn copyField(final String name) {
        final IndexFieldDefn copy = new IndexFieldDefn(name, this.store, this.index, this.sortable);
        copy.setPropertyName(name);
        copy.setIndexAnalyzer(this.indexAnalyzer);
        copy.setQueryAnalyzer(this.queryAnalyzer);
        copy.setPrecisionStep(this.precisionStep);
        copy.setBoost(this.boost);
        return copy;
    }
    
    public IndexFieldDefn copyFieldConcat(final String fieldName, final String[] properties) {
        final IndexFieldDefn copy = this.copyField(fieldName);
        copy.setPropertyName(null);
        copy.setPropertyNames(properties);
        return copy;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getPropertyName() {
        return this.propertyName;
    }
    
    public IndexFieldDefn setPropertyName(final String propertyName) {
        this.propertyName = propertyName;
        return this;
    }
    
    public Field.Index getIndex() {
        return this.index;
    }
    
    public IndexFieldDefn setIndex(final Field.Index index) {
        this.index = index;
        return this;
    }
    
    public Field.Store getStore() {
        return this.store;
    }
    
    public IndexFieldDefn setStore(final Field.Store store) {
        this.store = store;
        return this;
    }
    
    public Sortable getSortable() {
        return this.sortable;
    }
    
    public IndexFieldDefn setSortable(final Sortable sortable) {
        this.sortable = sortable;
        return this;
    }
    
    public int getPrecisionStep() {
        return this.precisionStep;
    }
    
    public IndexFieldDefn setPrecisionStep(final int precisionStep) {
        this.precisionStep = precisionStep;
        return this;
    }
    
    public float getBoost() {
        return this.boost;
    }
    
    public void setBoost(final float boost) {
        this.boost = boost;
    }
    
    public Analyzer getQueryAnalyzer() {
        return this.queryAnalyzer;
    }
    
    public Analyzer getIndexAnalyzer() {
        return this.indexAnalyzer;
    }
    
    public IndexFieldDefn setQueryAnalyzer(final Analyzer queryAnalyzer) {
        this.queryAnalyzer = queryAnalyzer;
        return this;
    }
    
    public IndexFieldDefn setIndexAnalyzer(final Analyzer indexAnalyzer) {
        this.indexAnalyzer = indexAnalyzer;
        return this;
    }
    
    public IndexFieldDefn setBothAnalyzers(final Analyzer analyzer) {
        this.queryAnalyzer = analyzer;
        this.indexAnalyzer = analyzer;
        return this;
    }
    
    public String[] getPropertyNames() {
        return this.properties;
    }
    
    public void setPropertyNames(final String[] properties) {
        this.properties = properties;
    }
    
    public enum Sortable
    {
        YES, 
        DEFAULT;
    }
}
