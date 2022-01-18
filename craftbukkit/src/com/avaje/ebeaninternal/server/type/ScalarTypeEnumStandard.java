// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebean.text.TextException;
import com.avaje.ebeaninternal.server.query.LuceneIndexDataReader;
import java.util.EnumSet;
import java.sql.SQLException;

public class ScalarTypeEnumStandard
{
    public static class StringEnum extends EnumBase implements ScalarTypeEnum
    {
        private final int length;
        
        public StringEnum(final Class enumType) {
            super(enumType, false, 12);
            this.length = this.maxValueLength(enumType);
        }
        
        public String getContraintInValues() {
            final StringBuilder sb = new StringBuilder();
            sb.append("(");
            final Object[] ea = this.enumType.getEnumConstants();
            for (int i = 0; i < ea.length; ++i) {
                final Enum<?> e = (Enum<?>)ea[i];
                if (i > 0) {
                    sb.append(",");
                }
                sb.append("'").append(e.toString()).append("'");
            }
            sb.append(")");
            return sb.toString();
        }
        
        private int maxValueLength(final Class<?> enumType) {
            int maxLen = 0;
            final Object[] ea = (Object[])enumType.getEnumConstants();
            for (int i = 0; i < ea.length; ++i) {
                final Enum<?> e = (Enum<?>)ea[i];
                maxLen = Math.max(maxLen, e.toString().length());
            }
            return maxLen;
        }
        
        public int getLength() {
            return this.length;
        }
        
        public void bind(final DataBind b, final Object value) throws SQLException {
            if (value == null) {
                b.setNull(12);
            }
            else {
                b.setString(value.toString());
            }
        }
        
        public Object read(final DataReader dataReader) throws SQLException {
            final String string = dataReader.getString();
            if (string == null) {
                return null;
            }
            return Enum.valueOf((Class<Object>)this.enumType, string);
        }
        
        public Object toJdbcType(final Object beanValue) {
            if (beanValue == null) {
                return null;
            }
            final Enum<?> e = (Enum<?>)beanValue;
            return e.toString();
        }
        
        public Object toBeanType(final Object dbValue) {
            if (dbValue == null) {
                return null;
            }
            return Enum.valueOf((Class<Object>)this.enumType, (String)dbValue);
        }
    }
    
    public static class OrdinalEnum extends EnumBase implements ScalarTypeEnum
    {
        private final Object[] enumArray;
        
        public OrdinalEnum(final Class enumType) {
            super(enumType, false, 4);
            this.enumArray = EnumSet.allOf((Class<Enum>)enumType).toArray();
        }
        
        public String getContraintInValues() {
            final StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (int i = 0; i < this.enumArray.length; ++i) {
                final Enum<?> e = (Enum<?>)this.enumArray[i];
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(e.ordinal());
            }
            sb.append(")");
            return sb.toString();
        }
        
        public void bind(final DataBind b, final Object value) throws SQLException {
            if (value == null) {
                b.setNull(4);
            }
            else {
                final Enum<?> e = (Enum<?>)value;
                b.setInt(e.ordinal());
            }
        }
        
        public Object read(final DataReader dataReader) throws SQLException {
            if (dataReader instanceof LuceneIndexDataReader) {
                final String s = dataReader.getString();
                return (s == null) ? null : this.parse(s);
            }
            final Integer ordinal = dataReader.getInt();
            if (ordinal == null) {
                return null;
            }
            if (ordinal < 0 || ordinal >= this.enumArray.length) {
                final String m = "Unexpected ordinal [" + ordinal + "] out of range [" + this.enumArray.length + "]";
                throw new IllegalStateException(m);
            }
            return this.enumArray[ordinal];
        }
        
        public Object toJdbcType(final Object beanValue) {
            if (beanValue == null) {
                return null;
            }
            final Enum e = (Enum)beanValue;
            return e.ordinal();
        }
        
        public Object toBeanType(final Object dbValue) {
            if (dbValue == null) {
                return null;
            }
            final int ordinal = (int)dbValue;
            if (ordinal < 0 || ordinal >= this.enumArray.length) {
                final String m = "Unexpected ordinal [" + ordinal + "] out of range [" + this.enumArray.length + "]";
                throw new IllegalStateException(m);
            }
            return this.enumArray[ordinal];
        }
    }
    
    public abstract static class EnumBase extends ScalarTypeBase
    {
        protected final Class enumType;
        
        public EnumBase(final Class<?> type, final boolean jdbcNative, final int jdbcType) {
            super(type, jdbcNative, jdbcType);
            this.enumType = type;
        }
        
        public String format(final Object t) {
            return t.toString();
        }
        
        public String formatValue(final Object t) {
            return t.toString();
        }
        
        public Object parse(final String value) {
            return Enum.valueOf((Class<Object>)this.enumType, value);
        }
        
        public Object parseDateTime(final long systemTimeMillis) {
            throw new TextException("Not Supported");
        }
        
        public boolean isDateTimeCapable() {
            return false;
        }
        
        public Object jsonFromString(final String value, final JsonValueAdapter ctx) {
            return this.parse(value);
        }
        
        public String jsonToString(final Object value, final JsonValueAdapter ctx) {
            return EscapeJson.escapeQuote(value.toString());
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
            final String s = dataInput.readUTF();
            return this.parse(s);
        }
        
        public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
            if (v == null) {
                dataOutput.writeBoolean(false);
            }
            else {
                dataOutput.writeBoolean(true);
                dataOutput.writeUTF(this.format(v));
            }
        }
    }
}
