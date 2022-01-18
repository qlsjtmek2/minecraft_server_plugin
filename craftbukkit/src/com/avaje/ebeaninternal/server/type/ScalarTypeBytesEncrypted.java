// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebean.text.json.JsonValueAdapter;
import java.sql.SQLException;

public class ScalarTypeBytesEncrypted implements ScalarType<byte[]>
{
    private final ScalarTypeBytesBase baseType;
    private final DataEncryptSupport dataEncryptSupport;
    
    public ScalarTypeBytesEncrypted(final ScalarTypeBytesBase baseType, final DataEncryptSupport dataEncryptSupport) {
        this.baseType = baseType;
        this.dataEncryptSupport = dataEncryptSupport;
    }
    
    public void bind(final DataBind b, byte[] value) throws SQLException {
        value = this.dataEncryptSupport.encrypt(value);
        this.baseType.bind(b, value);
    }
    
    public int getJdbcType() {
        return this.baseType.getJdbcType();
    }
    
    public int getLength() {
        return this.baseType.getLength();
    }
    
    public Class<byte[]> getType() {
        return byte[].class;
    }
    
    public boolean isDateTimeCapable() {
        return this.baseType.isDateTimeCapable();
    }
    
    public boolean isJdbcNative() {
        return this.baseType.isJdbcNative();
    }
    
    public void loadIgnore(final DataReader dataReader) {
        this.baseType.loadIgnore(dataReader);
    }
    
    public String format(final Object v) {
        throw new RuntimeException("Not used");
    }
    
    public String formatValue(final byte[] v) {
        throw new RuntimeException("Not used");
    }
    
    public byte[] parse(final String value) {
        return this.baseType.parse(value);
    }
    
    public byte[] parseDateTime(final long systemTimeMillis) {
        return this.baseType.parseDateTime(systemTimeMillis);
    }
    
    public byte[] read(final DataReader dataReader) throws SQLException {
        byte[] data = this.baseType.read(dataReader);
        data = this.dataEncryptSupport.decrypt(data);
        return data;
    }
    
    public byte[] toBeanType(final Object value) {
        return this.baseType.toBeanType(value);
    }
    
    public Object toJdbcType(final Object value) {
        return this.baseType.toJdbcType(value);
    }
    
    public void accumulateScalarTypes(final String propName, final CtCompoundTypeScalarList list) {
        this.baseType.accumulateScalarTypes(propName, list);
    }
    
    public String jsonToString(final byte[] value, final JsonValueAdapter ctx) {
        return this.baseType.jsonToString(value, ctx);
    }
    
    public byte[] jsonFromString(final String value, final JsonValueAdapter ctx) {
        return this.baseType.jsonFromString(value, ctx);
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        final int len = dataInput.readInt();
        final byte[] value = new byte[len];
        dataInput.readFully(value);
        return value;
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        final byte[] value = (byte[])v;
        dataOutput.writeInt(value.length);
        dataOutput.write(value);
    }
    
    public int getLuceneType() {
        return this.baseType.getLuceneType();
    }
    
    public Object luceneFromIndexValue(final Object value) {
        return this.baseType.luceneFromIndexValue(value);
    }
    
    public Object luceneToIndexValue(final Object value) {
        return this.baseType.luceneToIndexValue(value);
    }
}
