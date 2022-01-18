// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text.json;

import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;
import java.io.Reader;

public interface JsonContext
{
     <T> T toBean(final Class<T> p0, final String p1);
    
     <T> T toBean(final Class<T> p0, final Reader p1);
    
     <T> T toBean(final Class<T> p0, final String p1, final JsonReadOptions p2);
    
     <T> T toBean(final Class<T> p0, final Reader p1, final JsonReadOptions p2);
    
     <T> List<T> toList(final Class<T> p0, final String p1);
    
     <T> List<T> toList(final Class<T> p0, final String p1, final JsonReadOptions p2);
    
     <T> List<T> toList(final Class<T> p0, final Reader p1);
    
     <T> List<T> toList(final Class<T> p0, final Reader p1, final JsonReadOptions p2);
    
    Object toObject(final Type p0, final Reader p1, final JsonReadOptions p2);
    
    Object toObject(final Type p0, final String p1, final JsonReadOptions p2);
    
    void toJsonWriter(final Object p0, final Writer p1);
    
    void toJsonWriter(final Object p0, final Writer p1, final boolean p2);
    
    void toJsonWriter(final Object p0, final Writer p1, final boolean p2, final JsonWriteOptions p3);
    
    void toJsonWriter(final Object p0, final Writer p1, final boolean p2, final JsonWriteOptions p3, final String p4);
    
    String toJsonString(final Object p0);
    
    String toJsonString(final Object p0, final boolean p1);
    
    String toJsonString(final Object p0, final boolean p1, final JsonWriteOptions p2);
    
    String toJsonString(final Object p0, final boolean p1, final JsonWriteOptions p2, final String p3);
    
    boolean isSupportedType(final Type p0);
}
