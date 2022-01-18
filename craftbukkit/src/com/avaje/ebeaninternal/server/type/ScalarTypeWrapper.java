// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.json.JsonValueAdapter;
import java.sql.SQLException;
import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebean.config.ScalarTypeConverter;

public class ScalarTypeWrapper<B, S> implements ScalarType<B>
{
    private final ScalarType<S> scalarType;
    private final ScalarTypeConverter<B, S> converter;
    private final Class<B> wrapperType;
    private final B nullValue;
    
    public ScalarTypeWrapper(final Class<B> wrapperType, final ScalarType<S> scalarType, final ScalarTypeConverter<B, S> converter) {
        this.scalarType = scalarType;
        this.converter = converter;
        this.nullValue = converter.getNullValue();
        this.wrapperType = wrapperType;
    }
    
    public String toString() {
        return "ScalarTypeWrapper " + this.wrapperType + " to " + this.scalarType.getType();
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        final Object v = this.scalarType.readData(dataInput);
        return this.converter.wrapValue((S)v);
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        final S sv = this.converter.unwrapValue((B)v);
        this.scalarType.writeData(dataOutput, sv);
    }
    
    public void bind(final DataBind b, final B value) throws SQLException {
        if (value == null) {
            this.scalarType.bind(b, null);
        }
        else {
            final S sv = this.converter.unwrapValue(value);
            this.scalarType.bind(b, sv);
        }
    }
    
    public int getJdbcType() {
        return this.scalarType.getJdbcType();
    }
    
    public int getLength() {
        return this.scalarType.getLength();
    }
    
    public Class<B> getType() {
        return this.wrapperType;
    }
    
    public boolean isDateTimeCapable() {
        return this.scalarType.isDateTimeCapable();
    }
    
    public boolean isJdbcNative() {
        return false;
    }
    
    public String format(final Object v) {
        return this.formatValue(v);
    }
    
    public String formatValue(final B v) {
        final S sv = this.converter.unwrapValue(v);
        return this.scalarType.formatValue(sv);
    }
    
    public B parse(final String value) {
        final S sv = this.scalarType.parse(value);
        if (sv == null) {
            return this.nullValue;
        }
        return this.converter.wrapValue(sv);
    }
    
    public B parseDateTime(final long systemTimeMillis) {
        final S sv = this.scalarType.parseDateTime(systemTimeMillis);
        if (sv == null) {
            return this.nullValue;
        }
        return this.converter.wrapValue(sv);
    }
    
    public void loadIgnore(final DataReader dataReader) {
        dataReader.incrementPos(1);
    }
    
    public B read(final DataReader dataReader) throws SQLException {
        final S sv = this.scalarType.read(dataReader);
        if (sv == null) {
            return this.nullValue;
        }
        return this.converter.wrapValue(sv);
    }
    
    public B toBeanType(final Object value) {
        if (value == null) {
            return this.nullValue;
        }
        if (this.getType().isAssignableFrom(value.getClass())) {
            return (B)value;
        }
        if (value instanceof String) {
            return this.parse((String)value);
        }
        final S sv = this.scalarType.toBeanType(value);
        return this.converter.wrapValue(sv);
    }
    
    public Object toJdbcType(final Object value) {
        final Object sv = this.converter.unwrapValue((B)value);
        if (sv == null) {
            return this.nullValue;
        }
        return this.scalarType.toJdbcType(sv);
    }
    
    public void accumulateScalarTypes(final String propName, final CtCompoundTypeScalarList list) {
        list.addScalarType(propName, this);
    }
    
    public ScalarType<?> getScalarType() {
        return this;
    }
    
    public String jsonToString(final B value, final JsonValueAdapter ctx) {
        final S sv = this.converter.unwrapValue(value);
        return this.scalarType.jsonToString(sv, ctx);
    }
    
    public B jsonFromString(final String value, final JsonValueAdapter ctx) {
        final S s = this.scalarType.jsonFromString(value, ctx);
        return this.converter.wrapValue(s);
    }
    
    public Object luceneFromIndexValue(final Object value) {
        final S s = (S)this.scalarType.luceneFromIndexValue(value);
        return this.converter.wrapValue(s);
    }
    
    public Object luceneToIndexValue(final Object value) {
        final S sv = this.converter.unwrapValue((B)value);
        return this.scalarType.luceneToIndexValue(sv);
    }
    
    public int getLuceneType() {
        return this.scalarType.getLuceneType();
    }
}
