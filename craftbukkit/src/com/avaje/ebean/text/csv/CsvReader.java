// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text.csv;

import java.io.Reader;
import com.avaje.ebean.text.StringParser;
import java.util.Locale;

public interface CsvReader<T>
{
    void setDefaultLocale(final Locale p0);
    
    void setDefaultTimeFormat(final String p0);
    
    void setDefaultDateFormat(final String p0);
    
    void setDefaultTimestampFormat(final String p0);
    
    void setPersistBatchSize(final int p0);
    
    void setHasHeader(final boolean p0, final boolean p1);
    
    void setAddPropertiesFromHeader();
    
    void setIgnoreHeader();
    
    void setLogInfoFrequency(final int p0);
    
    void addIgnore();
    
    void addProperty(final String p0);
    
    void addReference(final String p0);
    
    void addProperty(final String p0, final StringParser p1);
    
    void addDateTime(final String p0, final String p1);
    
    void addDateTime(final String p0, final String p1, final Locale p2);
    
    void process(final Reader p0) throws Exception;
    
    void process(final Reader p0, final CsvCallback<T> p1) throws Exception;
}
