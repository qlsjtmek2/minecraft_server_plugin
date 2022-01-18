// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.util.Arrays;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.query.CQueryPredicates;
import com.avaje.ebeaninternal.server.query.SqlTreeNode;
import com.avaje.ebeaninternal.server.query.SqlTreeNodeRoot;
import java.util.Set;
import java.util.logging.Level;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.query.SqlTreeProperties;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.List;
import com.avaje.ebeaninternal.server.query.SqlTree;
import java.util.Map;
import java.util.logging.Logger;

public class DRawSqlSelect
{
    private static final Logger logger;
    private final BeanDescriptor<?> desc;
    private final DRawSqlColumnInfo[] selectColumns;
    private final Map<String, DRawSqlColumnInfo> columnMap;
    private final String preWhereExprSql;
    private final boolean andWhereExpr;
    private final String preHavingExprSql;
    private final boolean andHavingExpr;
    private final String orderBySql;
    private final String whereClause;
    private final String havingClause;
    private final String query;
    private final String columnMapping;
    private final String name;
    private final SqlTree sqlTree;
    private boolean withId;
    private final String tableAlias;
    
    public DRawSqlSelect(final BeanDescriptor<?> desc, final List<DRawSqlColumnInfo> selectColumns, final String tableAlias, final String preWhereExprSql, final boolean andWhereExpr, final String preHavingExprSql, final boolean andHavingExpr, final String orderBySql, final DRawSqlMeta meta) {
        this.desc = desc;
        this.tableAlias = tableAlias;
        this.selectColumns = selectColumns.toArray(new DRawSqlColumnInfo[selectColumns.size()]);
        this.preHavingExprSql = preHavingExprSql;
        this.preWhereExprSql = preWhereExprSql;
        this.andHavingExpr = andHavingExpr;
        this.andWhereExpr = andWhereExpr;
        this.orderBySql = orderBySql;
        this.name = meta.getName();
        this.whereClause = meta.getWhere();
        this.havingClause = meta.getHaving();
        this.query = meta.getQuery();
        this.columnMapping = meta.getColumnMapping();
        this.sqlTree = this.initialise(desc);
        this.columnMap = this.createColumnMap(this.selectColumns);
    }
    
    private Map<String, DRawSqlColumnInfo> createColumnMap(final DRawSqlColumnInfo[] selectColumns) {
        final HashMap<String, DRawSqlColumnInfo> m = new HashMap<String, DRawSqlColumnInfo>();
        for (int i = 0; i < selectColumns.length; ++i) {
            m.put(selectColumns[i].getPropertyName(), selectColumns[i]);
        }
        return m;
    }
    
    private SqlTree initialise(final BeanDescriptor<?> owner) {
        try {
            return this.buildSqlTree(owner);
        }
        catch (Exception e) {
            final String m = "Bug? initialising query " + this.name + " on " + owner;
            throw new RuntimeException(m, e);
        }
    }
    
    public DRawSqlColumnInfo getRawSqlColumnInfo(final String propertyName) {
        return this.columnMap.get(propertyName);
    }
    
    public String getTableAlias() {
        return this.tableAlias;
    }
    
    private SqlTree buildSqlTree(final BeanDescriptor<?> desc) {
        final SqlTree sqlTree = new SqlTree();
        sqlTree.setSummary(desc.getName());
        final LinkedHashSet<String> includedProps = new LinkedHashSet<String>();
        final SqlTreeProperties selectProps = new SqlTreeProperties();
        for (int i = 0; i < this.selectColumns.length; ++i) {
            final DRawSqlColumnInfo columnInfo = this.selectColumns[i];
            final String propName = columnInfo.getPropertyName();
            final BeanProperty beanProperty = desc.getBeanProperty(propName);
            if (beanProperty != null) {
                if (beanProperty.isId()) {
                    if (i > 0) {
                        final String m = "With " + desc + " query:" + this.name + " the ID is not the first column in the select. It must be...";
                        throw new PersistenceException(m);
                    }
                    this.withId = true;
                }
                else {
                    includedProps.add(beanProperty.getName());
                    selectProps.add(beanProperty);
                }
            }
            else {
                String m = "Mapping for " + desc.getFullName();
                m = m + " query[" + this.name + "] column[" + columnInfo + "] index[" + i;
                m += "] not matched to bean property?";
                DRawSqlSelect.logger.log(Level.SEVERE, m);
            }
        }
        selectProps.setIncludedProperties(includedProps);
        final SqlTreeNode sqlRoot = new SqlTreeNodeRoot(desc, selectProps, null, this.withId);
        sqlTree.setRootNode(sqlRoot);
        return sqlTree;
    }
    
    public String buildSql(final String orderBy, final CQueryPredicates predicates, final OrmQueryRequest<?> request) {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.preWhereExprSql);
        sb.append(" ");
        String dynamicWhere = null;
        if (request.getQuery().getId() != null) {
            final BeanDescriptor<?> descriptor = request.getBeanDescriptor();
            dynamicWhere = descriptor.getIdBinderIdSql();
        }
        final String dbWhere = predicates.getDbWhere();
        if (dbWhere != null && dbWhere.length() > 0) {
            if (dynamicWhere == null) {
                dynamicWhere = dbWhere;
            }
            else {
                dynamicWhere = dynamicWhere + " and " + dbWhere;
            }
        }
        if (dynamicWhere != null) {
            if (this.andWhereExpr) {
                sb.append(" and ");
            }
            else {
                sb.append(" where ");
            }
            sb.append(dynamicWhere);
            sb.append(" ");
        }
        if (this.preHavingExprSql != null) {
            sb.append(this.preHavingExprSql);
            sb.append(" ");
        }
        final String dbHaving = predicates.getDbHaving();
        if (dbHaving != null && dbHaving.length() > 0) {
            if (this.andHavingExpr) {
                sb.append(" and ");
            }
            else {
                sb.append(" having ");
            }
            sb.append(dbHaving);
            sb.append(" ");
        }
        if (orderBy != null) {
            sb.append(" order by ").append(orderBy);
        }
        return sb.toString();
    }
    
    public String getOrderBy(final CQueryPredicates predicates) {
        final String orderBy = predicates.getDbOrderBy();
        if (orderBy != null) {
            return orderBy;
        }
        return this.orderBySql;
    }
    
    public String getName() {
        return this.name;
    }
    
    public SqlTree getSqlTree() {
        return this.sqlTree;
    }
    
    public boolean isWithId() {
        return this.withId;
    }
    
    public String getQuery() {
        return this.query;
    }
    
    public String getColumnMapping() {
        return this.columnMapping;
    }
    
    public String getWhereClause() {
        return this.whereClause;
    }
    
    public String getHavingClause() {
        return this.havingClause;
    }
    
    public String toString() {
        return Arrays.toString(this.selectColumns);
    }
    
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.desc;
    }
    
    public DeployParser createDeployPropertyParser() {
        return new DeployPropertyParserRawSql(this);
    }
    
    static {
        logger = Logger.getLogger(DRawSqlSelect.class.getName());
    }
}
