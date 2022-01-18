// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import java.util.ArrayList;
import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.lucene.LIndex;

public interface SpiExpressionRequest
{
    LIndex getLuceneIndex();
    
    String parseDeploy(final String p0);
    
    BeanDescriptor<?> getBeanDescriptor();
    
    SpiOrmQueryRequest<?> getQueryRequest();
    
    SpiExpressionRequest append(final String p0);
    
    void addBindValue(final Object p0);
    
    String getSql();
    
    ArrayList<Object> getBindValues();
    
    int nextParameter();
}
