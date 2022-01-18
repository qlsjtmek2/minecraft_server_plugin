// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.SQLException;
import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import java.math.BigDecimal;

public class ScalarTypeBigDecimal extends ScalarTypeBase<BigDecimal>
{
    public ScalarTypeBigDecimal() {
        super(BigDecimal.class, true, 3);
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        final double val = dataInput.readDouble();
        return new BigDecimal(val);
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        final BigDecimal b = (BigDecimal)v;
        if (b == null) {
            dataOutput.writeBoolean(false);
        }
        else {
            dataOutput.writeBoolean(true);
            dataOutput.writeDouble(b.doubleValue());
        }
    }
    
    public void bind(final DataBind b, final BigDecimal value) throws SQLException {
        if (value == null) {
            b.setNull(3);
        }
        else {
            b.setBigDecimal(value);
        }
    }
    
    public BigDecimal read(final DataReader dataReader) throws SQLException {
        return dataReader.getBigDecimal();
    }
    
    public Object toJdbcType(final Object value) {
        return BasicTypeConverter.toBigDecimal(value);
    }
    
    public BigDecimal toBeanType(final Object value) {
        return BasicTypeConverter.toBigDecimal(value);
    }
    
    public String formatValue(final BigDecimal t) {
        return t.toPlainString();
    }
    
    public BigDecimal parse(final String value) {
        return new BigDecimal(value);
    }
    
    public BigDecimal parseDateTime(final long systemTimeMillis) {
        return BigDecimal.valueOf(systemTimeMillis);
    }
    
    public boolean isDateTimeCapable() {
        return true;
    }
    
    public Object luceneFromIndexValue(final Object value) {
        final Double v = (Double)value;
        return new BigDecimal(v);
    }
    
    public Object luceneToIndexValue(final Object value) {
        final BigDecimal v = (BigDecimal)value;
        return v.doubleValue();
    }
    
    public int getLuceneType() {
        return 3;
    }
}
