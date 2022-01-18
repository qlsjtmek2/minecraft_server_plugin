// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.json.JsonValueAdapter;
import java.sql.SQLException;
import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;

public class ScalarTypeEncryptedWrapper<T> implements ScalarType<T>
{
    private final ScalarType<T> wrapped;
    private final DataEncryptSupport dataEncryptSupport;
    private final ScalarTypeBytesBase byteArrayType;
    
    public ScalarTypeEncryptedWrapper(final ScalarType<T> wrapped, final ScalarTypeBytesBase byteArrayType, final DataEncryptSupport dataEncryptSupport) {
        this.wrapped = wrapped;
        this.byteArrayType = byteArrayType;
        this.dataEncryptSupport = dataEncryptSupport;
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        return this.wrapped.readData(dataInput);
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        this.wrapped.writeData(dataOutput, v);
    }
    
    public T read(final DataReader dataReader) throws SQLException {
        final byte[] data = dataReader.getBytes();
        final String formattedValue = this.dataEncryptSupport.decryptObject(data);
        if (formattedValue == null) {
            return null;
        }
        return this.wrapped.parse(formattedValue);
    }
    
    private byte[] encrypt(final T value) {
        final String formatValue = this.wrapped.formatValue(value);
        return this.dataEncryptSupport.encryptObject(formatValue);
    }
    
    public void bind(final DataBind b, final T value) throws SQLException {
        final byte[] encryptedValue = this.encrypt(value);
        this.byteArrayType.bind(b, encryptedValue);
    }
    
    public int getJdbcType() {
        return this.byteArrayType.getJdbcType();
    }
    
    public int getLength() {
        return this.byteArrayType.getLength();
    }
    
    public Class<T> getType() {
        return this.wrapped.getType();
    }
    
    public boolean isDateTimeCapable() {
        return this.wrapped.isDateTimeCapable();
    }
    
    public boolean isJdbcNative() {
        return false;
    }
    
    public void loadIgnore(final DataReader dataReader) {
        this.wrapped.loadIgnore(dataReader);
    }
    
    public String format(final Object v) {
        return this.formatValue(v);
    }
    
    public String formatValue(final T v) {
        return this.wrapped.formatValue(v);
    }
    
    public T parse(final String value) {
        return this.wrapped.parse(value);
    }
    
    public T parseDateTime(final long systemTimeMillis) {
        return this.wrapped.parseDateTime(systemTimeMillis);
    }
    
    public T toBeanType(final Object value) {
        return this.wrapped.toBeanType(value);
    }
    
    public Object toJdbcType(final Object value) {
        return this.wrapped.toJdbcType(value);
    }
    
    public void accumulateScalarTypes(final String propName, final CtCompoundTypeScalarList list) {
        this.wrapped.accumulateScalarTypes(propName, list);
    }
    
    public String jsonToString(final T value, final JsonValueAdapter ctx) {
        return this.wrapped.jsonToString(value, ctx);
    }
    
    public T jsonFromString(final String value, final JsonValueAdapter ctx) {
        return this.wrapped.jsonFromString(value, ctx);
    }
    
    public int getLuceneType() {
        return this.wrapped.getLuceneType();
    }
    
    public Object luceneFromIndexValue(final Object value) {
        return this.wrapped.luceneFromIndexValue(value);
    }
    
    public Object luceneToIndexValue(final Object value) {
        return this.wrapped.luceneToIndexValue(value);
    }
}
