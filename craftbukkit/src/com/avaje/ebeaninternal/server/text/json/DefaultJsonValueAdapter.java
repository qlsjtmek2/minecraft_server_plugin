// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.text.json;

import java.sql.Timestamp;
import java.sql.Date;
import java.text.SimpleDateFormat;
import com.avaje.ebean.text.json.JsonValueAdapter;

public class DefaultJsonValueAdapter implements JsonValueAdapter
{
    private final SimpleDateFormat dateTimeProto;
    
    public DefaultJsonValueAdapter(final String dateTimeFormat) {
        this.dateTimeProto = new SimpleDateFormat(dateTimeFormat);
    }
    
    public DefaultJsonValueAdapter() {
        this("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    }
    
    private SimpleDateFormat dtFormat() {
        return (SimpleDateFormat)this.dateTimeProto.clone();
    }
    
    public String jsonFromDate(final Date date) {
        return date.toString();
    }
    
    public String jsonFromTimestamp(final Timestamp date) {
        return this.dtFormat().format(date);
    }
    
    public Date jsonToDate(final String jsonDate) {
        return Date.valueOf(jsonDate);
    }
    
    public Timestamp jsonToTimestamp(final String jsonDateTime) {
        try {
            final java.util.Date d = this.dtFormat().parse(jsonDateTime);
            return new Timestamp(d.getTime());
        }
        catch (Exception e) {
            final String m = "Error parsing Datetime[" + jsonDateTime + "]";
            throw new RuntimeException(m, e);
        }
    }
}
