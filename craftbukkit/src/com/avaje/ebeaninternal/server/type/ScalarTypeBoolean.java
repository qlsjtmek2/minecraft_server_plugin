// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebean.text.TextException;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;

public class ScalarTypeBoolean
{
    public static class Native extends BooleanBase
    {
        public Native() {
            super(true, 16);
        }
        
        public Boolean toBeanType(final Object value) {
            return BasicTypeConverter.toBoolean(value);
        }
        
        public Object toJdbcType(final Object value) {
            return BasicTypeConverter.convert(value, this.jdbcType);
        }
        
        public void bind(final DataBind b, final Boolean value) throws SQLException {
            if (value == null) {
                b.setNull(16);
            }
            else {
                b.setBoolean(value);
            }
        }
        
        public Boolean read(final DataReader dataReader) throws SQLException {
            return dataReader.getBoolean();
        }
    }
    
    public static class BitBoolean extends BooleanBase
    {
        public BitBoolean() {
            super(true, -7);
        }
        
        public Boolean toBeanType(final Object value) {
            return BasicTypeConverter.toBoolean(value);
        }
        
        public Object toJdbcType(final Object value) {
            return BasicTypeConverter.toBoolean(value);
        }
        
        public void bind(final DataBind b, final Boolean value) throws SQLException {
            if (value == null) {
                b.setNull(-7);
            }
            else {
                b.setBoolean(value);
            }
        }
        
        public Boolean read(final DataReader dataReader) throws SQLException {
            return dataReader.getBoolean();
        }
    }
    
    public static class IntBoolean extends BooleanBase
    {
        private final Integer trueValue;
        private final Integer falseValue;
        
        public IntBoolean(final Integer trueValue, final Integer falseValue) {
            super(false, 4);
            this.trueValue = trueValue;
            this.falseValue = falseValue;
        }
        
        public int getLength() {
            return 1;
        }
        
        public void bind(final DataBind b, final Boolean value) throws SQLException {
            if (value == null) {
                b.setNull(4);
            }
            else {
                b.setInt(this.toInteger(value));
            }
        }
        
        public Boolean read(final DataReader dataReader) throws SQLException {
            final Integer i = dataReader.getInt();
            if (i == null) {
                return null;
            }
            if (i.equals(this.trueValue)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
        
        public Object toJdbcType(final Object value) {
            return this.toInteger(value);
        }
        
        public Integer toInteger(final Object value) {
            if (value == null) {
                return null;
            }
            final Boolean b = (Boolean)value;
            if (b) {
                return this.trueValue;
            }
            return this.falseValue;
        }
        
        public Boolean toBeanType(final Object value) {
            if (value == null) {
                return null;
            }
            if (value instanceof Boolean) {
                return (Boolean)value;
            }
            if (this.trueValue.equals(value)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
    }
    
    public static class StringBoolean extends BooleanBase
    {
        private final String trueValue;
        private final String falseValue;
        
        public StringBoolean(final String trueValue, final String falseValue) {
            super(false, 12);
            this.trueValue = trueValue;
            this.falseValue = falseValue;
        }
        
        public int getLength() {
            return Math.max(this.trueValue.length(), this.falseValue.length());
        }
        
        public void bind(final DataBind b, final Boolean value) throws SQLException {
            if (value == null) {
                b.setNull(12);
            }
            else {
                b.setString(this.toString(value));
            }
        }
        
        public Boolean read(final DataReader dataReader) throws SQLException {
            final String string = dataReader.getString();
            if (string == null) {
                return null;
            }
            if (string.equals(this.trueValue)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
        
        public Object toJdbcType(final Object value) {
            return this.toString(value);
        }
        
        public String toString(final Object value) {
            if (value == null) {
                return null;
            }
            final Boolean b = (Boolean)value;
            if (b) {
                return this.trueValue;
            }
            return this.falseValue;
        }
        
        public Boolean toBeanType(final Object value) {
            if (value == null) {
                return null;
            }
            if (value instanceof Boolean) {
                return (Boolean)value;
            }
            if (this.trueValue.equals(value)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
    }
    
    public abstract static class BooleanBase extends ScalarTypeBase<Boolean>
    {
        public BooleanBase(final boolean jdbcNative, final int jdbcType) {
            super(Boolean.class, jdbcNative, jdbcType);
        }
        
        public String formatValue(final Boolean t) {
            return t.toString();
        }
        
        public Boolean parse(final String value) {
            return Boolean.valueOf(value);
        }
        
        public Boolean parseDateTime(final long systemTimeMillis) {
            throw new TextException("Not Supported");
        }
        
        public boolean isDateTimeCapable() {
            return false;
        }
        
        public int getLuceneType() {
            return 0;
        }
        
        public Object luceneFromIndexValue(final Object value) {
            return this.parse((String)value);
        }
        
        public Object luceneToIndexValue(final Object value) {
            return this.format(value);
        }
        
        public Object readData(final DataInput dataInput) throws IOException {
            if (!dataInput.readBoolean()) {
                return null;
            }
            final boolean val = dataInput.readBoolean();
            return val;
        }
        
        public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
            final Boolean val = (Boolean)v;
            if (val == null) {
                dataOutput.writeBoolean(false);
            }
            else {
                dataOutput.writeBoolean(true);
                dataOutput.writeBoolean(val);
            }
        }
    }
}
