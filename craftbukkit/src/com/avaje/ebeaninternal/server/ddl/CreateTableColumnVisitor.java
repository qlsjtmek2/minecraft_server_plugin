// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ddl;

import java.util.logging.Level;
import com.avaje.ebean.config.dbplatform.IdType;
import com.avaje.ebeaninternal.server.deploy.TableJoinColumn;
import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebean.config.dbplatform.DbDdlSyntax;
import java.util.logging.Logger;

public class CreateTableColumnVisitor extends BaseTablePropertyVisitor
{
    private static final Logger logger;
    private final DdlGenContext ctx;
    private final DbDdlSyntax ddl;
    private final CreateTableVisitor parent;
    
    public CreateTableColumnVisitor(final CreateTableVisitor parent, final DdlGenContext ctx) {
        this.parent = parent;
        this.ctx = ctx;
        this.ddl = ctx.getDdlSyntax();
    }
    
    public void visitMany(final BeanPropertyAssocMany<?> p) {
        if (p.isManyToMany()) {
            if (p.getMappedBy() == null) {
                final TableJoin intersectionTableJoin = p.getIntersectionTableJoin();
                final String intTable = intersectionTableJoin.getTable();
                if (this.ctx.isProcessIntersectionTable(intTable)) {
                    new CreateIntersectionTable(this.ctx, p).build();
                }
            }
        }
    }
    
    public void visitCompoundScalar(final BeanPropertyCompound compound, final BeanProperty p) {
        this.visitScalar(p);
    }
    
    public void visitCompound(final BeanPropertyCompound p) {
    }
    
    public void visitEmbeddedScalar(final BeanProperty p, final BeanPropertyAssocOne<?> embedded) {
        this.visitScalar(p);
    }
    
    private StringBuilder createUniqueConstraintBuffer(final String table, final String column) {
        String uqConstraintName = "uq_" + table + "_" + column;
        if (uqConstraintName.length() > this.ddl.getMaxConstraintNameLength()) {
            uqConstraintName = uqConstraintName.substring(0, this.ddl.getMaxConstraintNameLength());
        }
        final StringBuilder constraintExpr = new StringBuilder();
        constraintExpr.append("constraint ").append(uqConstraintName).append(" unique (");
        return constraintExpr;
    }
    
    public void visitOneImported(final BeanPropertyAssocOne<?> p) {
        final ImportedId importedId = p.getImportedId();
        final TableJoinColumn[] columns = p.getTableJoin().columns();
        if (columns.length == 0) {
            final String msg = "No join columns for " + p.getFullBeanName();
            throw new RuntimeException(msg);
        }
        final StringBuilder constraintExpr = this.createUniqueConstraintBuffer(p.getBeanDescriptor().getBaseTable(), columns[0].getLocalDbColumn());
        for (int i = 0; i < columns.length; ++i) {
            final String dbCol = columns[i].getLocalDbColumn();
            if (i > 0) {
                constraintExpr.append(", ");
            }
            constraintExpr.append(dbCol);
            if (!this.parent.isDbColumnWritten(dbCol)) {
                this.parent.writeColumnName(dbCol, p);
                final BeanProperty importedProperty = importedId.findMatchImport(dbCol);
                if (importedProperty == null) {
                    throw new RuntimeException("Imported BeanProperty not found?");
                }
                final String columnDefn = this.ctx.getColumnDefn(importedProperty);
                this.ctx.write(columnDefn);
                if (!p.isNullable()) {
                    this.ctx.write(" not null");
                }
                this.ctx.write(",").writeNewLine();
            }
        }
        constraintExpr.append(")");
        if (p.isOneToOne() && this.ddl.isAddOneToOneUniqueContraint()) {
            this.parent.addUniqueConstraint(constraintExpr.toString());
        }
    }
    
    public void visitScalar(final BeanProperty p) {
        if (p.isSecondaryTable()) {
            return;
        }
        if (this.parent.isDbColumnWritten(p.getDbColumn())) {
            return;
        }
        this.parent.writeColumnName(p.getDbColumn(), p);
        final String columnDefn = this.ctx.getColumnDefn(p);
        this.ctx.write(columnDefn);
        if (this.isIdentity(p)) {
            this.writeIdentity();
        }
        if (p.isId() && this.ddl.isInlinePrimaryKeyConstraint()) {
            this.ctx.write(" primary key");
        }
        else if (!p.isNullable() || p.isDDLNotNull()) {
            this.ctx.write(" not null");
        }
        if (p.isUnique() && !p.isId()) {
            this.parent.addUniqueConstraint(this.createUniqueConstraint(p));
        }
        this.parent.addCheckConstraint(p);
        this.ctx.write(",").writeNewLine();
    }
    
    private String createUniqueConstraint(final BeanProperty p) {
        final StringBuilder expr = this.createUniqueConstraintBuffer(p.getBeanDescriptor().getBaseTable(), p.getDbColumn());
        expr.append(p.getDbColumn()).append(")");
        return expr.toString();
    }
    
    protected void writeIdentity() {
        final String identity = this.ddl.getIdentity();
        if (identity != null && identity.length() > 0) {
            this.ctx.write(" ").write(identity);
        }
    }
    
    protected boolean isIdentity(final BeanProperty p) {
        if (p.isId()) {
            try {
                final IdType idType = p.getBeanDescriptor().getIdType();
                if (idType.equals(IdType.IDENTITY)) {
                    final int jdbcType = p.getScalarType().getJdbcType();
                    if (jdbcType == 4 || jdbcType == -5 || jdbcType == 5) {
                        return true;
                    }
                }
            }
            catch (Exception e) {
                final String msg = "Error determining identity on property " + p.getFullBeanName();
                CreateTableColumnVisitor.logger.log(Level.SEVERE, msg, e);
            }
        }
        return false;
    }
    
    static {
        logger = Logger.getLogger(CreateTableColumnVisitor.class.getName());
    }
}
