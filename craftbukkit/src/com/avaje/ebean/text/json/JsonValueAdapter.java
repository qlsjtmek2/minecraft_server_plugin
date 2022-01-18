// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text.json;

import java.sql.Timestamp;
import java.sql.Date;

public interface JsonValueAdapter
{
    String jsonFromDate(final Date p0);
    
    String jsonFromTimestamp(final Timestamp p0);
    
    Date jsonToDate(final String p0);
    
    Timestamp jsonToTimestamp(final String p0);
}
