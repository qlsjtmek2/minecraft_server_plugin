// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.query.SplitName;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoinColumn;
import com.avaje.ebeaninternal.server.core.InternString;
import java.util.LinkedHashMap;
import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;

public final class TableJoin
{
    public static final String NEW_LINE = "\n";
    public static final String LEFT_OUTER = "left outer join";
    public static final String JOIN = "join";
    private final boolean importedPrimaryKey;
    private final String table;
    private final String type;
    private final BeanCascadeInfo cascadeInfo;
    private final BeanProperty[] properties;
    private final TableJoinColumn[] columns;
    
    public TableJoin(final DeployTableJoin deploy, final LinkedHashMap<String, BeanProperty> propMap) {
        this.importedPrimaryKey = deploy.isImportedPrimaryKey();
        this.table = InternString.intern(deploy.getTable());
        this.type = InternString.intern(deploy.getType());
        this.cascadeInfo = deploy.getCascadeInfo();
        final DeployTableJoinColumn[] deployCols = deploy.columns();
        this.columns = new TableJoinColumn[deployCols.length];
        for (int i = 0; i < deployCols.length; ++i) {
            this.columns[i] = new TableJoinColumn(deployCols[i]);
        }
        final DeployBeanProperty[] deployProps = deploy.properties();
        if (deployProps.length > 0 && propMap == null) {
            throw new NullPointerException("propMap is null?");
        }
        this.properties = new BeanProperty[deployProps.length];
        for (int j = 0; j < deployProps.length; ++j) {
            final BeanProperty prop = propMap.get(deployProps[j].getName());
            this.properties[j] = prop;
        }
    }
    
    public TableJoin createWithAlias(final String localAlias, final String foreignAlias) {
        return new TableJoin(this, localAlias, foreignAlias);
    }
    
    private TableJoin(final TableJoin join, final String localAlias, final String foreignAlias) {
        this.importedPrimaryKey = join.importedPrimaryKey;
        this.table = join.table;
        this.type = join.type;
        this.cascadeInfo = join.cascadeInfo;
        this.properties = join.properties;
        this.columns = join.columns;
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder(30);
        sb.append(this.type).append(" ").append(this.table).append(" ");
        for (int i = 0; i < this.columns.length; ++i) {
            sb.append(this.columns[i]).append(" ");
        }
        return sb.toString();
    }
    
    public void appendSelect(final DbSqlContext ctx, final boolean subQuery) {
        for (int i = 0, x = this.properties.length; i < x; ++i) {
            this.properties[i].appendSelect(ctx, subQuery);
        }
    }
    
    public void load(final SqlBeanLoad sqlBeanLoad) throws SQLException {
        for (int i = 0, x = this.properties.length; i < x; ++i) {
            this.properties[i].load(sqlBeanLoad);
        }
    }
    
    public Object readSet(final DbReadContext ctx, final Object bean, final Class<?> type) throws SQLException {
        for (int i = 0, x = this.properties.length; i < x; ++i) {
            this.properties[i].readSet(ctx, bean, type);
        }
        return null;
    }
    
    public boolean isImportedPrimaryKey() {
        return this.importedPrimaryKey;
    }
    
    public BeanCascadeInfo getCascadeInfo() {
        return this.cascadeInfo;
    }
    
    public TableJoinColumn[] columns() {
        return this.columns;
    }
    
    public BeanProperty[] properties() {
        return this.properties;
    }
    
    public String getTable() {
        return this.table;
    }
    
    public String getType() {
        return this.type;
    }
    
    public boolean isOuterJoin() {
        return this.type.equals("left outer join");
    }
    
    public boolean addJoin(final boolean forceOuterJoin, final String prefix, final DbSqlContext ctx) {
        final String[] names = SplitName.split(prefix);
        final String a1 = ctx.getTableAlias(names[0]);
        final String a2 = ctx.getTableAlias(prefix);
        return this.addJoin(forceOuterJoin, a1, a2, ctx);
    }
    
    public boolean addJoin(final boolean forceOuterJoin, final String a1, final String a2, final DbSqlContext ctx) {
        ctx.addJoin(forceOuterJoin ? "left outer join" : this.type, this.table, this.columns(), a1, a2);
        return forceOuterJoin || "left outer join".equals(this.type);
    }
    
    public void addInnerJoin(final String a1, final String a2, final DbSqlContext ctx) {
        ctx.addJoin("join", this.table, this.columns(), a1, a2);
    }
}
