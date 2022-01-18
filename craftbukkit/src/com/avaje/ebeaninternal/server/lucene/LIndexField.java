// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.document.Document;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import java.util.Set;

public interface LIndexField
{
    String getName();
    
    DocFieldWriter createDocFieldWriter();
    
    void addIndexResolvePropertyNames(final Set<String> p0);
    
    void addIndexRestorePropertyNames(final Set<String> p0);
    
    void addIndexRequiredPropertyNames(final Set<String> p0);
    
    String getSortableProperty();
    
    int getSortType();
    
    boolean isIndexed();
    
    boolean isStored();
    
    boolean isBeanProperty();
    
    ElPropertyValue getElBeanProperty();
    
    void readValue(final Document p0, final Object p1);
    
    QueryParser createQueryParser();
    
    int getPropertyOrder();
}
