// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.SQLException;
import java.math.BigInteger;

public class ScalarTypeMathBigInteger extends ScalarTypeBase<BigInteger>
{
    public ScalarTypeMathBigInteger() {
        super(BigInteger.class, false, -5);
    }
    
    public void bind(final DataBind b, final BigInteger value) throws SQLException {
        if (value == null) {
            b.setNull(-5);
        }
        else {
            b.setLong(value.longValue());
        }
    }
    
    public BigInteger read(final DataReader dataReader) throws SQLException {
        final Long l = dataReader.getLong();
        if (l == null) {
            return null;
        }
        return new BigInteger(String.valueOf(l));
    }
    
    public Object toJdbcType(final Object value) {
        return BasicTypeConverter.toLong(value);
    }
    
    public BigInteger toBeanType(final Object value) {
        return BasicTypeConverter.toMathBigInteger(value);
    }
    
    public String formatValue(final BigInteger v) {
        return v.toString();
    }
    
    public BigInteger parse(final String value) {
        return new BigInteger(value);
    }
    
    public BigInteger parseDateTime(final long systemTimeMillis) {
        return BigInteger.valueOf(systemTimeMillis);
    }
    
    public boolean isDateTimeCapable() {
        return true;
    }
    
    public int getLuceneType() {
        return 2;
    }
    
    public Object luceneFromIndexValue(final Object value) {
        return BigInteger.valueOf((long)value);
    }
    
    public Object luceneToIndexValue(final Object value) {
        return ((BigInteger)value).longValue();
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        final long val = dataInput.readLong();
        return val;
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        final Long value = (Long)v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        }
        else {
            dataOutput.writeBoolean(true);
            dataOutput.writeLong(value);
        }
    }
}
