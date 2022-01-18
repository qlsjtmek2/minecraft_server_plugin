// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebean.text.json.JsonValueAdapter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.persistence.PersistenceException;

public class ScalarTypeLdapTimestamp<T> implements ScalarType<T>
{
    private static final String timestampLDAPFormat = "yyyyMMddHHmmss'Z'";
    private final ScalarType<T> baseType;
    
    public ScalarTypeLdapTimestamp(final ScalarType<T> baseType) {
        this.baseType = baseType;
    }
    
    public T toBeanType(final Object value) {
        if (value == null) {
            return null;
        }
        if (!(value instanceof String)) {
            final String msg = "Expecting a String type but got " + value.getClass() + " value[" + value + "]";
            throw new PersistenceException(msg);
        }
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
            final Date date = sdf.parse((String)value);
            return this.baseType.parseDateTime(date.getTime());
        }
        catch (Exception e) {
            final String msg2 = "Error parsing LDAP timestamp " + value;
            throw new PersistenceException(msg2, e);
        }
    }
    
    public Object toJdbcType(final Object value) {
        if (value == null) {
            return null;
        }
        final Object ts = this.baseType.toJdbcType(value);
        if (!(ts instanceof Timestamp)) {
            final String msg = "Expecting a Timestamp type but got " + value.getClass() + " value[" + value + "]";
            throw new PersistenceException(msg);
        }
        final Timestamp t = (Timestamp)ts;
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
        return sdf.format(t);
    }
    
    public void bind(final DataBind b, final T value) throws SQLException {
        this.baseType.bind(b, value);
    }
    
    public int getJdbcType() {
        return 12;
    }
    
    public int getLength() {
        return this.baseType.getLength();
    }
    
    public Class<T> getType() {
        return this.baseType.getType();
    }
    
    public boolean isDateTimeCapable() {
        return this.baseType.isDateTimeCapable();
    }
    
    public boolean isJdbcNative() {
        return false;
    }
    
    public void loadIgnore(final DataReader dataReader) {
        this.baseType.loadIgnore(dataReader);
    }
    
    public String format(final Object v) {
        return this.baseType.format(v);
    }
    
    public String formatValue(final T t) {
        return this.baseType.formatValue(t);
    }
    
    public T parse(final String value) {
        return this.baseType.parse(value);
    }
    
    public T parseDateTime(final long systemTimeMillis) {
        return this.baseType.parseDateTime(systemTimeMillis);
    }
    
    public T read(final DataReader dataReader) throws SQLException {
        return this.baseType.read(dataReader);
    }
    
    public void accumulateScalarTypes(final String propName, final CtCompoundTypeScalarList list) {
        this.baseType.accumulateScalarTypes(propName, list);
    }
    
    public String jsonToString(final T value, final JsonValueAdapter ctx) {
        return this.baseType.jsonToString(value, ctx);
    }
    
    public T jsonFromString(final String value, final JsonValueAdapter ctx) {
        return this.baseType.jsonFromString(value, ctx);
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        return this.baseType.readData(dataInput);
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        this.baseType.writeData(dataOutput, v);
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
