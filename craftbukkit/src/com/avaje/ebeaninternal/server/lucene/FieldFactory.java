// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import org.apache.lucene.document.NumericField;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.document.Field;

public class FieldFactory
{
    private final boolean numericField;
    private final String fieldName;
    private final Field.Store store;
    private final Field.Index index;
    private final float boost;
    private final int precisionStep;
    
    public static FieldFactory numeric(final String fieldName, final Field.Store store, final Field.Index index, final float boost, final int precisionStep) {
        return new FieldFactory(true, fieldName, store, index, boost, precisionStep);
    }
    
    public static FieldFactory normal(final String fieldName, final Field.Store store, final Field.Index index, final float boost) {
        return new FieldFactory(false, fieldName, store, index, boost, 0);
    }
    
    private FieldFactory(final boolean numericField, final String fieldName, final Field.Store store, final Field.Index index, final float boost, final int precisionStep) {
        this.numericField = numericField;
        this.fieldName = fieldName;
        this.store = store;
        this.index = index;
        this.boost = boost;
        this.precisionStep = precisionStep;
    }
    
    public Fieldable createFieldable() {
        return this.numericField ? this.createNumericField() : this.createNormalField();
    }
    
    private Fieldable createNormalField() {
        final Field f = new Field(this.fieldName, "", this.store, this.index);
        if (this.boost > 0.0f) {
            f.setBoost(this.boost);
        }
        return (Fieldable)f;
    }
    
    private Fieldable createNumericField() {
        final boolean indexed = this.index.isIndexed();
        final NumericField f = new NumericField(this.fieldName, this.precisionStep, this.store, indexed);
        if (this.boost > 0.0f) {
            f.setBoost(this.boost);
        }
        return (Fieldable)f;
    }
}
