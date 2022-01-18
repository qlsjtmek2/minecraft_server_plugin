// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;

public interface PropertyVisitor
{
    void visitMany(final BeanPropertyAssocMany<?> p0);
    
    void visitOneImported(final BeanPropertyAssocOne<?> p0);
    
    void visitOneExported(final BeanPropertyAssocOne<?> p0);
    
    void visitEmbedded(final BeanPropertyAssocOne<?> p0);
    
    void visitEmbeddedScalar(final BeanProperty p0, final BeanPropertyAssocOne<?> p1);
    
    void visitScalar(final BeanProperty p0);
    
    void visitCompound(final BeanPropertyCompound p0);
    
    void visitCompoundScalar(final BeanPropertyCompound p0, final BeanProperty p1);
}
