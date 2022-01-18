// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.util.logging.Level;
import java.util.Collection;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import java.util.ArrayList;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import java.util.Set;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import java.util.HashSet;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiQuery;
import java.util.logging.Logger;

public class SqlTreeBuilder
{
    private static final Logger logger;
    private final SpiQuery<?> query;
    private final BeanDescriptor<?> desc;
    private final OrmQueryDetail queryDetail;
    private final StringBuilder summary;
    private final CQueryPredicates predicates;
    private final boolean subQuery;
    private BeanPropertyAssocMany<?> manyProperty;
    private String manyPropertyName;
    private final SqlTreeAlias alias;
    private final DefaultDbSqlContext ctx;
    private final HashSet<String> selectIncludes;
    private final ManyWhereJoins manyWhereJoins;
    private final TableJoin includeJoin;
    private final boolean rawSql;
    
    public SqlTreeBuilder(final OrmQueryRequest<?> request, final CQueryPredicates predicates, final OrmQueryDetail queryDetail) {
        this.summary = new StringBuilder();
        this.selectIncludes = new HashSet<String>();
        this.rawSql = true;
        this.desc = request.getBeanDescriptor();
        this.query = null;
        this.subQuery = false;
        this.queryDetail = queryDetail;
        this.predicates = predicates;
        this.includeJoin = null;
        this.manyWhereJoins = null;
        this.alias = null;
        this.ctx = null;
    }
    
    public SqlTreeBuilder(final String tableAliasPlaceHolder, final String columnAliasPrefix, final OrmQueryRequest<?> request, final CQueryPredicates predicates) {
        this.summary = new StringBuilder();
        this.selectIncludes = new HashSet<String>();
        this.rawSql = false;
        this.desc = request.getBeanDescriptor();
        this.query = request.getQuery();
        this.subQuery = Query.Type.SUBQUERY.equals(this.query.getType());
        this.includeJoin = this.query.getIncludeTableJoin();
        this.manyWhereJoins = this.query.getManyWhereJoins();
        this.queryDetail = this.query.getDetail();
        this.predicates = predicates;
        this.alias = new SqlTreeAlias(request.getBeanDescriptor().getBaseTableAlias());
        this.ctx = new DefaultDbSqlContext(this.alias, tableAliasPlaceHolder, columnAliasPrefix, !this.subQuery);
    }
    
    public SqlTree build() {
        final SqlTree sqlTree = new SqlTree();
        this.summary.append(this.desc.getName());
        this.buildRoot(this.desc, sqlTree);
        final SqlTreeNode rootNode = sqlTree.getRootNode();
        if (!this.rawSql) {
            sqlTree.setSelectSql(this.buildSelectClause(rootNode));
            sqlTree.setFromSql(this.buildFromClause(rootNode));
            sqlTree.setInheritanceWhereSql(this.buildWhereClause(rootNode));
            sqlTree.setEncryptedProps(this.ctx.getEncryptedProps());
        }
        sqlTree.setIncludes(this.queryDetail.getIncludes());
        sqlTree.setSummary(this.summary.toString());
        if (this.manyPropertyName != null) {
            final ElPropertyValue manyPropEl = this.desc.getElGetValue(this.manyPropertyName);
            sqlTree.setManyProperty(this.manyProperty, this.manyPropertyName, manyPropEl);
        }
        return sqlTree;
    }
    
    private String buildSelectClause(final SqlTreeNode rootNode) {
        if (this.rawSql) {
            return "Not Used";
        }
        rootNode.appendSelect(this.ctx, this.subQuery);
        String selectSql = this.ctx.getContent();
        if (selectSql.length() >= ", ".length()) {
            selectSql = selectSql.substring(", ".length());
        }
        return selectSql;
    }
    
    private String buildWhereClause(final SqlTreeNode rootNode) {
        if (this.rawSql) {
            return "Not Used";
        }
        rootNode.appendWhere(this.ctx);
        return this.ctx.getContent();
    }
    
    private String buildFromClause(final SqlTreeNode rootNode) {
        if (this.rawSql) {
            return "Not Used";
        }
        rootNode.appendFrom(this.ctx, false);
        return this.ctx.getContent();
    }
    
    private void buildRoot(final BeanDescriptor<?> desc, final SqlTree sqlTree) {
        final SqlTreeNode selectRoot = this.buildSelectChain(null, null, desc, null);
        sqlTree.setRootNode(selectRoot);
        if (!this.rawSql) {
            this.alias.addJoin(this.queryDetail.getIncludes(), desc);
            this.alias.addJoin(this.predicates.getPredicateIncludes(), desc);
            this.alias.addManyWhereJoins(this.manyWhereJoins.getJoins());
            this.alias.buildAlias();
            this.predicates.parseTableAlias(this.alias);
        }
    }
    
    private SqlTreeNode buildSelectChain(final String prefix, final BeanPropertyAssoc<?> prop, final BeanDescriptor<?> desc, final List<SqlTreeNode> joinList) {
        final List<SqlTreeNode> myJoinList = new ArrayList<SqlTreeNode>();
        final BeanPropertyAssocOne<?>[] ones = desc.propertiesOne();
        for (int i = 0; i < ones.length; ++i) {
            final String propPrefix = SplitName.add(prefix, ones[i].getName());
            if (this.isIncludeBean(propPrefix, ones[i])) {
                this.selectIncludes.add(propPrefix);
                this.buildSelectChain(propPrefix, ones[i], ones[i].getTargetDescriptor(), myJoinList);
            }
        }
        final BeanPropertyAssocMany<?>[] manys = desc.propertiesMany();
        for (int j = 0; j < manys.length; ++j) {
            final String propPrefix2 = SplitName.add(prefix, manys[j].getName());
            if (this.isIncludeMany(prefix, propPrefix2, manys[j])) {
                this.selectIncludes.add(propPrefix2);
                this.buildSelectChain(propPrefix2, manys[j], manys[j].getTargetDescriptor(), myJoinList);
            }
        }
        if (prefix == null && !this.rawSql) {
            this.addManyWhereJoins(myJoinList);
        }
        final SqlTreeNode selectNode = this.buildNode(prefix, prop, desc, myJoinList);
        if (joinList != null) {
            joinList.add(selectNode);
        }
        return selectNode;
    }
    
    private void addManyWhereJoins(final List<SqlTreeNode> myJoinList) {
        final Set<String> includes = this.manyWhereJoins.getJoins();
        for (final String joinProp : includes) {
            final BeanPropertyAssoc<?> beanProperty = (BeanPropertyAssoc<?>)this.desc.getBeanPropertyFromPath(joinProp);
            final SqlTreeNodeManyWhereJoin nodeJoin = new SqlTreeNodeManyWhereJoin(joinProp, beanProperty);
            myJoinList.add(nodeJoin);
        }
    }
    
    private SqlTreeNode buildNode(final String prefix, final BeanPropertyAssoc<?> prop, final BeanDescriptor<?> desc, final List<SqlTreeNode> myList) {
        final OrmQueryProperties queryProps = this.queryDetail.getChunk(prefix, false);
        final SqlTreeProperties props = this.getBaseSelect(desc, queryProps);
        if (prefix == null) {
            this.buildExtraJoins(desc, myList);
            return new SqlTreeNodeRoot(desc, props, myList, !this.subQuery, this.includeJoin);
        }
        if (prop instanceof BeanPropertyAssocMany) {
            return new SqlTreeNodeManyRoot(prefix, (BeanPropertyAssocMany)prop, props, myList);
        }
        return new SqlTreeNodeBean(prefix, prop, props, myList, true);
    }
    
    private void buildExtraJoins(final BeanDescriptor<?> desc, final List<SqlTreeNode> myList) {
        if (this.rawSql) {
            return;
        }
        final Set<String> predicateIncludes = this.predicates.getPredicateIncludes();
        if (predicateIncludes == null) {
            return;
        }
        predicateIncludes.removeAll(this.manyWhereJoins.getJoins());
        final IncludesDistiller extraJoinDistill = new IncludesDistiller((BeanDescriptor)desc, (Set)this.selectIncludes, (Set)predicateIncludes);
        final Collection<SqlTreeNodeExtraJoin> extraJoins = extraJoinDistill.getExtraJoinRootNodes();
        if (extraJoins.isEmpty()) {
            return;
        }
        for (final SqlTreeNodeExtraJoin extraJoin : extraJoins) {
            myList.add(extraJoin);
            if (extraJoin.isManyJoin()) {
                this.query.setDistinct(true);
            }
        }
    }
    
    private void addPropertyToSubQuery(final SqlTreeProperties selectProps, final BeanDescriptor<?> desc, final OrmQueryProperties queryProps, final String propName) {
        BeanProperty p = desc.findBeanProperty(propName);
        if (p == null) {
            SqlTreeBuilder.logger.log(Level.SEVERE, "property [" + propName + "]not found on " + desc + " for query - excluding it.");
        }
        else if (p instanceof BeanPropertyAssoc && p.isEmbedded()) {
            final int pos = propName.indexOf(".");
            if (pos > -1) {
                final String name = propName.substring(pos + 1);
                p = ((BeanPropertyAssoc)p).getTargetDescriptor().findBeanProperty(name);
            }
        }
        selectProps.add(p);
    }
    
    private void addProperty(final SqlTreeProperties selectProps, final BeanDescriptor<?> desc, final OrmQueryProperties queryProps, final String propName) {
        if (this.subQuery) {
            this.addPropertyToSubQuery(selectProps, desc, queryProps, propName);
            return;
        }
        final int basePos = propName.indexOf(46);
        if (basePos > -1) {
            final String baseName = propName.substring(0, basePos);
            if (!selectProps.containsProperty(baseName)) {
                final BeanProperty p = desc.findBeanProperty(baseName);
                if (p == null) {
                    final String m = "property [" + propName + "] not found on " + desc + " for query - excluding it.";
                    SqlTreeBuilder.logger.log(Level.SEVERE, m);
                }
                else if (p.isEmbedded()) {
                    selectProps.add(p);
                    selectProps.getIncludedProperties().add(baseName);
                }
                else {
                    final String m = "property [" + p.getFullBeanName() + "] expected to be an embedded bean for query - excluding it.";
                    SqlTreeBuilder.logger.log(Level.SEVERE, m);
                }
            }
        }
        else {
            final BeanProperty p2 = desc.findBeanProperty(propName);
            if (p2 == null) {
                SqlTreeBuilder.logger.log(Level.SEVERE, "property [" + propName + "] not found on " + desc + " for query - excluding it.");
            }
            else if (!p2.isId()) {
                if (p2 instanceof BeanPropertyAssoc) {
                    if (!queryProps.isIncludedBeanJoin(p2.getName())) {
                        selectProps.add(p2);
                    }
                }
                else {
                    selectProps.add(p2);
                }
            }
        }
    }
    
    private SqlTreeProperties getBaseSelectPartial(final BeanDescriptor<?> desc, final OrmQueryProperties queryProps) {
        final SqlTreeProperties selectProps = new SqlTreeProperties();
        selectProps.setReadOnly(queryProps.isReadOnly());
        selectProps.setIncludedProperties(queryProps.getAllIncludedProperties());
        final Iterator<String> it = queryProps.getSelectProperties();
        while (it.hasNext()) {
            final String propName = it.next();
            if (propName.length() > 0) {
                this.addProperty(selectProps, desc, queryProps, propName);
            }
        }
        return selectProps;
    }
    
    private SqlTreeProperties getBaseSelect(final BeanDescriptor<?> desc, final OrmQueryProperties queryProps) {
        final boolean partial = queryProps != null && !queryProps.allProperties();
        if (partial) {
            return this.getBaseSelectPartial(desc, queryProps);
        }
        final SqlTreeProperties selectProps = new SqlTreeProperties();
        selectProps.add(desc.propertiesBaseScalar());
        selectProps.add(desc.propertiesBaseCompound());
        selectProps.add(desc.propertiesEmbedded());
        final BeanPropertyAssocOne<?>[] propertiesOne = desc.propertiesOne();
        for (int i = 0; i < propertiesOne.length; ++i) {
            if (queryProps == null || !queryProps.isIncludedBeanJoin(propertiesOne[i].getName())) {
                selectProps.add(propertiesOne[i]);
            }
        }
        selectProps.setTableJoins(desc.tableJoins());
        final InheritInfo inheritInfo = desc.getInheritInfo();
        if (inheritInfo != null) {
            inheritInfo.addChildrenProperties(selectProps);
        }
        return selectProps;
    }
    
    private boolean isIncludeMany(final String prefix, final String propName, final BeanPropertyAssocMany<?> manyProp) {
        if (this.queryDetail.isJoinsEmpty()) {
            return false;
        }
        if (!this.queryDetail.includes(propName)) {
            return false;
        }
        if (this.manyProperty != null) {
            if (SqlTreeBuilder.logger.isLoggable(Level.FINE)) {
                final String msg = "Not joining [" + propName + "] as already joined to a Many[" + this.manyProperty + "].";
                SqlTreeBuilder.logger.fine(msg);
            }
            return false;
        }
        this.manyProperty = manyProp;
        this.manyPropertyName = propName;
        this.summary.append(" +many:").append(propName);
        return true;
    }
    
    private boolean isIncludeBean(final String prefix, final BeanPropertyAssocOne<?> prop) {
        if (this.queryDetail.includes(prefix)) {
            this.summary.append(", ").append(prefix);
            final String[] splitNames = SplitName.split(prefix);
            this.queryDetail.includeBeanJoin(splitNames[0], splitNames[1]);
            return true;
        }
        return false;
    }
    
    static {
        logger = Logger.getLogger(SqlTreeBuilder.class.getName());
    }
    
    private static class IncludesDistiller
    {
        private final Set<String> selectIncludes;
        private final Set<String> predicateIncludes;
        private final Map<String, SqlTreeNodeExtraJoin> joinRegister;
        private final Map<String, SqlTreeNodeExtraJoin> rootRegister;
        private final BeanDescriptor<?> desc;
        
        private IncludesDistiller(final BeanDescriptor<?> desc, final Set<String> selectIncludes, final Set<String> predicateIncludes) {
            this.joinRegister = new HashMap<String, SqlTreeNodeExtraJoin>();
            this.rootRegister = new HashMap<String, SqlTreeNodeExtraJoin>();
            this.desc = desc;
            this.selectIncludes = selectIncludes;
            this.predicateIncludes = predicateIncludes;
        }
        
        private Collection<SqlTreeNodeExtraJoin> getExtraJoinRootNodes() {
            final String[] extras = this.findExtras();
            if (extras.length == 0) {
                return this.rootRegister.values();
            }
            Arrays.sort(extras);
            for (int i = 0; i < extras.length; ++i) {
                this.createExtraJoin(extras[i]);
            }
            return this.rootRegister.values();
        }
        
        private void createExtraJoin(final String includeProp) {
            final SqlTreeNodeExtraJoin extraJoin = this.createJoinLeaf(includeProp);
            if (extraJoin != null) {
                final SqlTreeNodeExtraJoin root = this.findExtraJoinRoot(includeProp, extraJoin);
                this.rootRegister.put(root.getName(), root);
            }
        }
        
        private SqlTreeNodeExtraJoin createJoinLeaf(final String propertyName) {
            final ElPropertyValue elGetValue = this.desc.getElGetValue(propertyName);
            if (elGetValue == null) {
                return null;
            }
            final BeanProperty beanProperty = elGetValue.getBeanProperty();
            if (!(beanProperty instanceof BeanPropertyAssoc)) {
                return null;
            }
            final BeanPropertyAssoc<?> assocProp = (BeanPropertyAssoc<?>)beanProperty;
            if (assocProp.isEmbedded()) {
                return null;
            }
            final SqlTreeNodeExtraJoin extraJoin = new SqlTreeNodeExtraJoin(propertyName, assocProp);
            this.joinRegister.put(propertyName, extraJoin);
            return extraJoin;
        }
        
        private SqlTreeNodeExtraJoin findExtraJoinRoot(final String includeProp, final SqlTreeNodeExtraJoin childJoin) {
            final int dotPos = includeProp.lastIndexOf(46);
            if (dotPos == -1) {
                return childJoin;
            }
            final String parentPropertyName = includeProp.substring(0, dotPos);
            if (this.selectIncludes.contains(parentPropertyName)) {
                return childJoin;
            }
            SqlTreeNodeExtraJoin parentJoin = this.joinRegister.get(parentPropertyName);
            if (parentJoin == null) {
                parentJoin = this.createJoinLeaf(parentPropertyName);
            }
            parentJoin.addChild(childJoin);
            return this.findExtraJoinRoot(parentPropertyName, parentJoin);
        }
        
        private String[] findExtras() {
            final List<String> extras = new ArrayList<String>();
            for (final String predProp : this.predicateIncludes) {
                if (!this.selectIncludes.contains(predProp)) {
                    extras.add(predProp);
                }
            }
            return extras.toArray(new String[extras.size()]);
        }
    }
}
