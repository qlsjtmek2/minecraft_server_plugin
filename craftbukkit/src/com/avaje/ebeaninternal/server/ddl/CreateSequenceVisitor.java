// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.logging.Logger;

public class CreateSequenceVisitor implements BeanVisitor
{
    private static final Logger logger;
    private final DdlGenContext ctx;
    private final boolean supportsSequence;
    
    public CreateSequenceVisitor(final DdlGenContext ctx) {
        this.ctx = ctx;
        this.supportsSequence = ctx.getDbPlatform().getDbIdentity().isSupportsSequence();
    }
    
    public boolean visitBean(final BeanDescriptor<?> descriptor) {
        if (!descriptor.isInheritanceRoot()) {
            return false;
        }
        if (descriptor.getSequenceName() == null) {
            return false;
        }
        if (!this.supportsSequence) {
            final String msg = "Not creating sequence " + descriptor.getSequenceName() + " on Bean " + descriptor.getName() + " as DatabasePlatform does not support sequences";
            CreateSequenceVisitor.logger.warning(msg);
            return false;
        }
        if (descriptor.getSequenceName() != null) {
            this.ctx.write("create sequence ");
            this.ctx.write(descriptor.getSequenceName());
            this.ctx.write(";").writeNewLine().writeNewLine();
        }
        return true;
    }
    
    public void visitBeanEnd(final BeanDescriptor<?> descriptor) {
    }
    
    public void visitBegin() {
    }
    
    public void visitEnd() {
    }
    
    public PropertyVisitor visitProperty(final BeanProperty p) {
        return null;
    }
    
    static {
        logger = Logger.getLogger(DropSequenceVisitor.class.getName());
    }
}
