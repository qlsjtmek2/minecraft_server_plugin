// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.config.dbplatform.DbDdlSyntax;

public class DropTableVisitor implements BeanVisitor
{
    final DdlGenContext ctx;
    final DbDdlSyntax ddlSyntax;
    
    public DropTableVisitor(final DdlGenContext ctx) {
        this.ctx = ctx;
        this.ddlSyntax = ctx.getDdlSyntax();
    }
    
    protected void writeDropTable(final BeanDescriptor<?> descriptor) {
        this.writeDropTable(descriptor.getBaseTable());
    }
    
    protected void writeDropTable(final String tableName) {
        this.ctx.write("drop table ");
        if (this.ddlSyntax.getDropIfExists() != null) {
            this.ctx.write(this.ddlSyntax.getDropIfExists()).write(" ");
        }
        this.ctx.write(tableName);
        if (this.ddlSyntax.getDropTableCascade() != null) {
            this.ctx.write(" ").write(this.ddlSyntax.getDropTableCascade());
        }
        this.ctx.write(";").writeNewLine().writeNewLine();
    }
    
    public boolean visitBean(final BeanDescriptor<?> descriptor) {
        if (!descriptor.isInheritanceRoot()) {
            return false;
        }
        this.writeDropTable(descriptor);
        this.dropIntersectionTables(descriptor);
        return true;
    }
    
    private void dropIntersectionTables(final BeanDescriptor<?> descriptor) {
        final BeanPropertyAssocMany<?>[] manyProps = descriptor.propertiesMany();
        for (int i = 0; i < manyProps.length; ++i) {
            if (manyProps[i].isManyToMany()) {
                final String intTable = manyProps[i].getIntersectionTableJoin().getTable();
                if (this.ctx.isProcessIntersectionTable(intTable)) {
                    this.writeDropTable(intTable);
                }
            }
        }
    }
    
    public void visitBeanEnd(final BeanDescriptor<?> descriptor) {
    }
    
    public void visitBegin() {
        if (this.ddlSyntax.getDisableReferentialIntegrity() != null) {
            this.ctx.write(this.ddlSyntax.getDisableReferentialIntegrity());
            this.ctx.write(";").writeNewLine().writeNewLine();
        }
    }
    
    public void visitEnd() {
        if (this.ddlSyntax.getEnableReferentialIntegrity() != null) {
            this.ctx.write(this.ddlSyntax.getEnableReferentialIntegrity());
            this.ctx.write(";").writeNewLine().writeNewLine();
        }
    }
    
    public PropertyVisitor visitProperty(final BeanProperty p) {
        return null;
    }
}
