// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;

public abstract class AbstractPropertyVisitor implements PropertyVisitor
{
    public void visitEmbedded(final BeanPropertyAssocOne<?> p) {
    }
    
    public void visitEmbeddedScalar(final BeanProperty p, final BeanPropertyAssocOne<?> embedded) {
    }
    
    public void visitMany(final BeanPropertyAssocMany<?> p) {
    }
    
    public void visitOneExported(final BeanPropertyAssocOne<?> p) {
    }
    
    public void visitOneImported(final BeanPropertyAssocOne<?> p) {
    }
    
    public void visitScalar(final BeanProperty p) {
    }
    
    public void visitCompound(final BeanPropertyCompound p) {
    }
    
    public void visitCompoundScalar(final BeanPropertyCompound compound, final BeanProperty p) {
    }
}
