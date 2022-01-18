// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebean.text.json.JsonValueAdapter;
import java.sql.SQLException;
import com.avaje.ebean.text.StringFormatter;
import com.avaje.ebean.text.StringParser;

public interface ScalarType<T> extends StringParser, StringFormatter, ScalarDataReader<T>
{
    int getLength();
    
    boolean isJdbcNative();
    
    int getJdbcType();
    
    Class<T> getType();
    
    T read(final DataReader p0) throws SQLException;
    
    void loadIgnore(final DataReader p0);
    
    void bind(final DataBind p0, final T p1) throws SQLException;
    
    Object toJdbcType(final Object p0);
    
    T toBeanType(final Object p0);
    
    String formatValue(final T p0);
    
    String format(final Object p0);
    
    T parse(final String p0);
    
    T parseDateTime(final long p0);
    
    boolean isDateTimeCapable();
    
    String jsonToString(final T p0, final JsonValueAdapter p1);
    
    T jsonFromString(final String p0, final JsonValueAdapter p1);
    
    Object readData(final DataInput p0) throws IOException;
    
    void writeData(final DataOutput p0, final Object p1) throws IOException;
    
    Object luceneToIndexValue(final Object p0);
    
    Object luceneFromIndexValue(final Object p0);
    
    int getLuceneType();
}
