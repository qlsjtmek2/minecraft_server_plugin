// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;

public abstract class BaseTablePropertyVisitor implements PropertyVisitor
{
    public void visitEmbedded(final BeanPropertyAssocOne<?> p) {
    }
    
    public abstract void visitEmbeddedScalar(final BeanProperty p0, final BeanPropertyAssocOne<?> p1);
    
    public void visitMany(final BeanPropertyAssocMany<?> p) {
    }
    
    public void visitOneExported(final BeanPropertyAssocOne<?> p) {
    }
    
    public abstract void visitOneImported(final BeanPropertyAssocOne<?> p0);
    
    public abstract void visitScalar(final BeanProperty p0);
    
    public void visitCompound(final BeanPropertyCompound p) {
    }
    
    public abstract void visitCompoundScalar(final BeanPropertyCompound p0, final BeanProperty p1);
}
