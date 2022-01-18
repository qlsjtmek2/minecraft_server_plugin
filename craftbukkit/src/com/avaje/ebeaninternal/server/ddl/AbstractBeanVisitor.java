// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import com.avaje.ebeaninternal.server.deploy.InheritInfoVisitor;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public abstract class AbstractBeanVisitor implements BeanVisitor
{
    public void visitInheritanceProperties(final BeanDescriptor<?> descriptor, final PropertyVisitor pv) {
        final InheritInfo inheritInfo = descriptor.getInheritInfo();
        if (inheritInfo != null && inheritInfo.isRoot()) {
            final InheritChildVisitor childVisitor = new InheritChildVisitor(pv);
            inheritInfo.visitChildren(childVisitor);
        }
    }
    
    protected static class InheritChildVisitor implements InheritInfoVisitor
    {
        final PropertyVisitor pv;
        
        protected InheritChildVisitor(final PropertyVisitor pv) {
            this.pv = pv;
        }
        
        public void visit(final InheritInfo inheritInfo) {
            final BeanProperty[] propertiesLocal = inheritInfo.getBeanDescriptor().propertiesLocal();
            VisitorUtil.visit(propertiesLocal, this.pv);
        }
    }
}
