// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.el;

import com.avaje.ebean.text.StringFormatter;
import com.avaje.ebean.text.StringParser;

public interface ElPropertyValue extends ElPropertyDeploy
{
    Object[] getAssocOneIdValues(final Object p0);
    
    String getAssocOneIdExpr(final String p0, final String p1);
    
    String getAssocIdInValueExpr(final int p0);
    
    String getAssocIdInExpr(final String p0);
    
    boolean isAssocId();
    
    boolean isAssocProperty();
    
    boolean isLocalEncrypted();
    
    boolean isDbEncrypted();
    
    int getDeployOrder();
    
    StringParser getStringParser();
    
    StringFormatter getStringFormatter();
    
    boolean isDateTimeCapable();
    
    int getJdbcType();
    
    Object parseDateTime(final long p0);
    
    Object elGetValue(final Object p0);
    
    Object elGetReference(final Object p0);
    
    void elSetValue(final Object p0, final Object p1, final boolean p2, final boolean p3);
    
    void elSetReference(final Object p0);
    
    Object elConvertType(final Object p0);
}
