// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public interface BeanVisitor
{
    void visitBegin();
    
    boolean visitBean(final BeanDescriptor<?> p0);
    
    PropertyVisitor visitProperty(final BeanProperty p0);
    
    void visitBeanEnd(final BeanDescriptor<?> p0);
    
    void visitEnd();
}
