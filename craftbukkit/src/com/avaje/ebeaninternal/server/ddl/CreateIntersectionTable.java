// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.TableJoinColumn;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;

public class CreateIntersectionTable
{
    private static final String NEW_LINE = "\n";
    private final DdlGenContext ctx;
    private final BeanPropertyAssocMany<?> manyProp;
    private final TableJoin intersectionTableJoin;
    private final TableJoin tableJoin;
    private StringBuilder sb;
    private StringBuilder pkeySb;
    private int foreignKeyCount;
    private int maxFkeyLength;
    
    public CreateIntersectionTable(final DdlGenContext ctx, final BeanPropertyAssocMany<?> manyProp) {
        this.sb = new StringBuilder();
        this.pkeySb = new StringBuilder();
        this.ctx = ctx;
        this.manyProp = manyProp;
        this.intersectionTableJoin = manyProp.getIntersectionTableJoin();
        this.tableJoin = manyProp.getTableJoin();
        this.maxFkeyLength = ctx.getDdlSyntax().getMaxConstraintNameLength() - 3;
    }
    
    public void build() {
        final String createTable = this.buildCreateTable();
        this.ctx.addCreateIntersectionTable(createTable);
        this.foreignKeyCount = 0;
        this.buildFkConstraints();
    }
    
    private void buildFkConstraints() {
        final BeanDescriptor<?> localDesc = this.manyProp.getBeanDescriptor();
        final String fk1 = this.buildFkConstraints(localDesc, this.intersectionTableJoin.columns(), true);
        this.ctx.addIntersectionTableFk(fk1);
        final BeanDescriptor<?> targetDesc = this.manyProp.getTargetDescriptor();
        final String fk2 = this.buildFkConstraints(targetDesc, this.tableJoin.columns(), false);
        this.ctx.addIntersectionTableFk(fk2);
    }
    
    private String getFkNameSuffix() {
        ++this.foreignKeyCount;
        if (this.foreignKeyCount > 9) {
            return "_" + this.foreignKeyCount;
        }
        return "_0" + this.foreignKeyCount;
    }
    
    private String getFkNameWithSuffix(String fkName) {
        if (fkName.length() > this.maxFkeyLength) {
            fkName = fkName.substring(0, this.maxFkeyLength);
        }
        return fkName + this.getFkNameSuffix();
    }
    
    private String buildFkConstraints(final BeanDescriptor<?> desc, final TableJoinColumn[] columns, final boolean direction) {
        final StringBuilder fkBuf = new StringBuilder();
        String fkName = "fk_" + this.intersectionTableJoin.getTable() + "_" + desc.getBaseTable();
        fkName = this.getFkNameWithSuffix(fkName);
        fkBuf.append("alter table ");
        fkBuf.append(this.intersectionTableJoin.getTable());
        fkBuf.append(" add constraint ").append(fkName);
        fkBuf.append(" foreign key (");
        for (int i = 0; i < columns.length; ++i) {
            if (i > 0) {
                fkBuf.append(", ");
            }
            final String col = direction ? columns[i].getForeignDbColumn() : columns[i].getLocalDbColumn();
            fkBuf.append(col);
        }
        fkBuf.append(") references ").append(desc.getBaseTable()).append(" (");
        for (int i = 0; i < columns.length; ++i) {
            if (i > 0) {
                fkBuf.append(", ");
            }
            final String col = direction ? columns[i].getLocalDbColumn() : columns[i].getForeignDbColumn();
            fkBuf.append(col);
        }
        fkBuf.append(")");
        final String fkeySuffix = this.ctx.getDdlSyntax().getForeignKeySuffix();
        if (fkeySuffix != null) {
            fkBuf.append(" ");
            fkBuf.append(fkeySuffix);
        }
        fkBuf.append(";").append("\n");
        return fkBuf.toString();
    }
    
    private String buildCreateTable() {
        final BeanDescriptor<?> localDesc = this.manyProp.getBeanDescriptor();
        final BeanDescriptor<?> targetDesc = this.manyProp.getTargetDescriptor();
        this.sb.append("create table ");
        this.sb.append(this.intersectionTableJoin.getTable());
        this.sb.append(" (").append("\n");
        final TableJoinColumn[] columns = this.intersectionTableJoin.columns();
        for (int i = 0; i < columns.length; ++i) {
            this.addColumn(localDesc, columns[i].getForeignDbColumn(), columns[i].getLocalDbColumn());
        }
        final TableJoinColumn[] otherColumns = this.tableJoin.columns();
        for (int j = 0; j < otherColumns.length; ++j) {
            this.addColumn(targetDesc, otherColumns[j].getLocalDbColumn(), otherColumns[j].getForeignDbColumn());
        }
        this.sb.append("  constraint pk_").append(this.intersectionTableJoin.getTable());
        this.sb.append(" primary key (").append(this.pkeySb.toString().substring(2));
        this.sb.append("))").append("\n").append(";").append("\n");
        return this.sb.toString();
    }
    
    private void addColumn(final BeanDescriptor<?> desc, final String column, final String findPropColumn) {
        this.pkeySb.append(", ");
        this.pkeySb.append(column);
        this.writeColumn(column);
        final BeanProperty p = desc.getIdBinder().findBeanProperty(findPropColumn);
        if (p == null) {
            throw new RuntimeException("Could not find id property for " + findPropColumn);
        }
        final String columnDefn = this.ctx.getColumnDefn(p);
        this.sb.append(columnDefn);
        this.sb.append(" not null");
        this.sb.append(",").append("\n");
    }
    
    private void writeColumn(final String columnName) {
        this.sb.append("  ").append(this.ctx.pad(columnName, 30)).append(" ");
    }
}
