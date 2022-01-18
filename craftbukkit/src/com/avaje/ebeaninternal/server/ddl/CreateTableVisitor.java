// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.CompoundUniqueContraint;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebean.config.dbplatform.DbType;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.parse.SqlReservedWords;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;
import com.avaje.ebean.config.dbplatform.DbDdlSyntax;
import java.util.logging.Logger;

public class CreateTableVisitor extends AbstractBeanVisitor
{
    protected static final Logger logger;
    final DdlGenContext ctx;
    final PropertyVisitor pv;
    final DbDdlSyntax ddl;
    final int columnNameWidth;
    private final Set<String> wroteColumns;
    private ArrayList<String> checkConstraints;
    private ArrayList<String> uniqueConstraints;
    private String table;
    private String schema;
    
    public Set<String> getWroteColumns() {
        return this.wroteColumns;
    }
    
    public CreateTableVisitor(final DdlGenContext ctx) {
        this.wroteColumns = new HashSet<String>();
        this.checkConstraints = new ArrayList<String>();
        this.uniqueConstraints = new ArrayList<String>();
        this.ctx = ctx;
        this.ddl = ctx.getDdlSyntax();
        this.columnNameWidth = this.ddl.getColumnNameWidth();
        this.pv = new CreateTableColumnVisitor(this, ctx);
    }
    
    public boolean isDbColumnWritten(final String dbColumn) {
        return this.wroteColumns.contains(dbColumn.toLowerCase());
    }
    
    public void addDbColumnWritten(final String dbColumn) {
        this.wroteColumns.add(dbColumn.toLowerCase());
    }
    
    protected void writeTableName(final BeanDescriptor<?> descriptor) {
        final String tableName = descriptor.getBaseTable();
        final int dotPos = tableName.lastIndexOf(46);
        if (dotPos > -1) {
            this.schema = tableName.substring(0, dotPos);
            this.table = tableName.substring(dotPos + 1);
        }
        else {
            this.table = tableName;
        }
        if (SqlReservedWords.isKeyword(this.table)) {
            CreateTableVisitor.logger.warning("Table name [" + this.table + "] is a suspected SQL reserved word for bean " + descriptor.getFullName());
        }
        this.ctx.write(tableName);
    }
    
    protected String getTable() {
        return this.table;
    }
    
    protected String getSchema() {
        return this.schema;
    }
    
    protected void writeColumnName(final String columnName, final BeanProperty p) {
        this.addDbColumnWritten(columnName);
        if (SqlReservedWords.isKeyword(columnName)) {
            final String propName = (p == null) ? "(Unknown)" : p.getFullBeanName();
            CreateTableVisitor.logger.warning("Column name [" + columnName + "] is a suspected SQL reserved word for property " + propName);
        }
        this.ctx.write("  ").write(columnName, this.columnNameWidth).write(" ");
    }
    
    protected void addCheckConstraint(final BeanProperty p, final String prefix, final String constraintExpression) {
        if (p != null && constraintExpression != null) {
            final String s = "constraint " + this.getConstraintName(prefix, p) + " " + constraintExpression;
            this.checkConstraints.add(s);
        }
    }
    
    protected String getConstraintName(final String prefix, final BeanProperty p) {
        return prefix + this.table + "_" + p.getDbColumn();
    }
    
    protected void addUniqueConstraint(final String constraintExpression) {
        this.uniqueConstraints.add(constraintExpression);
    }
    
    protected void addCheckConstraint(final String constraintExpression) {
        this.checkConstraints.add(constraintExpression);
    }
    
    protected void addCheckConstraint(final BeanProperty p) {
        this.addCheckConstraint(p, "ck_", p.getDbConstraintExpression());
    }
    
    public boolean visitBean(final BeanDescriptor<?> descriptor) {
        this.wroteColumns.clear();
        if (!descriptor.isInheritanceRoot()) {
            return false;
        }
        this.ctx.write("create table ");
        this.writeTableName(descriptor);
        this.ctx.write(" (").writeNewLine();
        final InheritInfo inheritInfo = descriptor.getInheritInfo();
        if (inheritInfo != null && inheritInfo.isRoot()) {
            final String discColumn = inheritInfo.getDiscriminatorColumn();
            final int discType = inheritInfo.getDiscriminatorType();
            final int discLength = inheritInfo.getDiscriminatorLength();
            final DbType dbType = this.ctx.getDbTypeMap().get(discType);
            final String discDbType = dbType.renderType(discLength, 0);
            this.writeColumnName(discColumn, null);
            this.ctx.write(discDbType);
            this.ctx.write(" not null,");
            this.ctx.writeNewLine();
        }
        return true;
    }
    
    public void visitBeanEnd(final BeanDescriptor<?> descriptor) {
        this.visitInheritanceProperties(descriptor, this.pv);
        if (this.checkConstraints.size() > 0) {
            for (final String checkConstraint : this.checkConstraints) {
                this.ctx.write("  ").write(checkConstraint).write(",").writeNewLine();
            }
            this.checkConstraints = new ArrayList<String>();
        }
        if (this.uniqueConstraints.size() > 0) {
            for (final String constraint : this.uniqueConstraints) {
                this.ctx.write("  ").write(constraint).write(",").writeNewLine();
            }
            this.uniqueConstraints = new ArrayList<String>();
        }
        final CompoundUniqueContraint[] compoundUniqueConstraints = descriptor.getCompoundUniqueConstraints();
        if (compoundUniqueConstraints != null) {
            final String table = descriptor.getBaseTable();
            for (int i = 0; i < compoundUniqueConstraints.length; ++i) {
                final String constraint2 = this.createUniqueConstraint(table, i, compoundUniqueConstraints[i]);
                this.ctx.write("  ").write(constraint2).write(",").writeNewLine();
            }
        }
        final BeanProperty[] ids = descriptor.propertiesId();
        if (ids.length == 0) {
            this.ctx.removeLast().removeLast();
        }
        else if (ids.length > 1 || this.ddl.isInlinePrimaryKeyConstraint()) {
            this.ctx.removeLast().removeLast();
        }
        else {
            final String pkName = this.ddl.getPrimaryKeyName(this.table);
            this.ctx.write("  constraint ").write(pkName).write(" primary key (");
            VisitorUtil.visit(ids, new AbstractPropertyVisitor() {
                public void visitEmbeddedScalar(final BeanProperty p, final BeanPropertyAssocOne<?> embedded) {
                    CreateTableVisitor.this.ctx.write(p.getDbColumn()).write(", ");
                }
                
                public void visitScalar(final BeanProperty p) {
                    CreateTableVisitor.this.ctx.write(p.getDbColumn()).write(", ");
                }
                
                public void visitCompoundScalar(final BeanPropertyCompound compound, final BeanProperty p) {
                    CreateTableVisitor.this.ctx.write(p.getDbColumn()).write(", ");
                }
            });
            this.ctx.removeLast().write(")");
        }
        this.ctx.write(")").writeNewLine();
        this.ctx.write(";").writeNewLine().writeNewLine();
        this.ctx.flush();
    }
    
    private String createUniqueConstraint(final String table, final int idx, final CompoundUniqueContraint uc) {
        final String uqConstraintName = "uq_" + table + "_" + (idx + 1);
        final StringBuilder sb = new StringBuilder();
        sb.append("constraint ").append(uqConstraintName).append(" unique (");
        final String[] columns = uc.getColumns();
        for (int i = 0; i < columns.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(columns[i]);
        }
        sb.append(")");
        return sb.toString();
    }
    
    public void visitBeanDescriptorEnd() {
        this.ctx.write(");").writeNewLine().writeNewLine();
    }
    
    public PropertyVisitor visitProperty(final BeanProperty p) {
        return this.pv;
    }
    
    public void visitBegin() {
    }
    
    public void visitEnd() {
        this.ctx.addIntersectionCreateTables();
        this.ctx.flush();
    }
    
    static {
        logger = Logger.getLogger(CreateTableVisitor.class.getName());
    }
}
