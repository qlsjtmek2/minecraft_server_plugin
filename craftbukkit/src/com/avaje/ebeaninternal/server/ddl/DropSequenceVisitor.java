// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.config.dbplatform.DbDdlSyntax;
import java.util.logging.Logger;

public class DropSequenceVisitor implements BeanVisitor
{
    private static final Logger logger;
    private final DdlGenContext ctx;
    private final DbDdlSyntax ddlSyntax;
    private final boolean supportsSequence;
    
    public DropSequenceVisitor(final DdlGenContext ctx) {
        this.ctx = ctx;
        this.ddlSyntax = ctx.getDdlSyntax();
        this.supportsSequence = ctx.getDbPlatform().getDbIdentity().isSupportsSequence();
    }
    
    public boolean visitBean(final BeanDescriptor<?> descriptor) {
        if (!descriptor.isInheritanceRoot()) {
            return false;
        }
        if (descriptor.getSequenceName() != null) {
            if (!this.supportsSequence) {
                final String msg = "Not dropping sequence " + descriptor.getSequenceName() + " on Bean " + descriptor.getName() + " as DatabasePlatform does not support sequences";
                DropSequenceVisitor.logger.finer(msg);
                return false;
            }
            this.ctx.write("drop sequence ");
            if (this.ddlSyntax.getDropIfExists() != null) {
                this.ctx.write(this.ddlSyntax.getDropIfExists()).write(" ");
            }
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
