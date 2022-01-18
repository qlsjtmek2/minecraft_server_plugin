// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.lucene.LIndexField;
import java.util.Set;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public class LuceneResolvableRequest
{
    private final BeanDescriptor<?> beanDescriptor;
    private final LIndex luceneIndex;
    private final Set<String> resolvePropertyNames;
    
    public LuceneResolvableRequest(final BeanDescriptor<?> beanDescriptor, final LIndex luceneIndex) {
        this.beanDescriptor = beanDescriptor;
        this.luceneIndex = luceneIndex;
        this.resolvePropertyNames = luceneIndex.getResolvePropertyNames();
    }
    
    public boolean indexContains(final String propertyName) {
        return this.resolvePropertyNames.contains(propertyName);
    }
    
    public LIndexField getSortableProperty(final String propertyName) {
        return this.luceneIndex.getIndexFieldDefn().getSortableField(propertyName);
    }
    
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.beanDescriptor;
    }
}
