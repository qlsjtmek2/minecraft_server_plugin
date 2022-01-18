// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
import com.avaje.ebeaninternal.server.deploy.TableJoinColumn;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public class AddForeignKeysVisitor extends AbstractBeanVisitor
{
    final DdlGenContext ctx;
    final FkeyPropertyVisitor pv;
    
    public AddForeignKeysVisitor(final DdlGenContext ctx) {
        this.ctx = ctx;
        this.pv = new FkeyPropertyVisitor(this, ctx);
    }
    
    public boolean visitBean(final BeanDescriptor<?> descriptor) {
        return descriptor.isInheritanceRoot();
    }
    
    public void visitBeanEnd(final BeanDescriptor<?> descriptor) {
        this.visitInheritanceProperties(descriptor, this.pv);
    }
    
    public void visitBegin() {
    }
    
    public void visitEnd() {
        this.ctx.addIntersectionFkeys();
    }
    
    public PropertyVisitor visitProperty(final BeanProperty p) {
        return this.pv;
    }
    
    public static class FkeyPropertyVisitor extends BaseTablePropertyVisitor
    {
        final DdlGenContext ctx;
        final AddForeignKeysVisitor parent;
        
        public FkeyPropertyVisitor(final AddForeignKeysVisitor parent, final DdlGenContext ctx) {
            this.parent = parent;
            this.ctx = ctx;
        }
        
        public void visitEmbeddedScalar(final BeanProperty p, final BeanPropertyAssocOne<?> embedded) {
        }
        
        public void visitOneImported(final BeanPropertyAssocOne<?> p) {
            final String baseTable = p.getBeanDescriptor().getBaseTable();
            final TableJoin tableJoin = p.getTableJoin();
            final TableJoinColumn[] columns = tableJoin.columns();
            final String tableName = p.getBeanDescriptor().getBaseTable();
            final String fkName = this.ctx.getDdlSyntax().getForeignKeyName(tableName, p.getName(), this.ctx.incrementFkCount());
            this.ctx.write("alter table ").write(baseTable).write(" add ");
            if (fkName != null) {
                this.ctx.write("constraint ").write(fkName).write(" ");
            }
            this.ctx.write("foreign key (");
            for (int i = 0; i < columns.length; ++i) {
                if (i > 0) {
                    this.ctx.write(",");
                }
                this.ctx.write(columns[i].getLocalDbColumn());
            }
            this.ctx.write(")");
            this.ctx.write(" references ");
            this.ctx.write(tableJoin.getTable());
            this.ctx.write(" (");
            for (int i = 0; i < columns.length; ++i) {
                if (i > 0) {
                    this.ctx.write(",");
                }
                this.ctx.write(columns[i].getForeignDbColumn());
            }
            this.ctx.write(")");
            final String fkeySuffix = this.ctx.getDdlSyntax().getForeignKeySuffix();
            if (fkeySuffix != null) {
                this.ctx.write(" ").write(fkeySuffix);
            }
            this.ctx.write(";").writeNewLine();
            if (this.ctx.getDdlSyntax().isRenderIndexForFkey()) {
                this.ctx.write("create index ");
                final String idxName = this.ctx.getDdlSyntax().getIndexName(tableName, p.getName(), this.ctx.incrementIxCount());
                if (idxName != null) {
                    this.ctx.write(idxName);
                }
                this.ctx.write(" on ").write(baseTable).write(" (");
                for (int j = 0; j < columns.length; ++j) {
                    if (j > 0) {
                        this.ctx.write(",");
                    }
                    this.ctx.write(columns[j].getLocalDbColumn());
                }
                this.ctx.write(");").writeNewLine();
            }
        }
        
        public void visitScalar(final BeanProperty p) {
        }
        
        public void visitCompound(final BeanPropertyCompound p) {
        }
        
        public void visitCompoundScalar(final BeanPropertyCompound compound, final BeanProperty p) {
        }
    }
}
