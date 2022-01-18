// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebean.bean.BeanCollection;
import java.sql.SQLException;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.util.Set;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public class SqlTreeNodeBean implements SqlTreeNode
{
    private static final SqlTreeNode[] NO_CHILDREN;
    final BeanDescriptor<?> desc;
    final IdBinder idBinder;
    final SqlTreeNode[] children;
    final boolean readOnlyLeaf;
    final boolean partialObject;
    final Set<String> partialProps;
    final int partialHash;
    final BeanProperty[] properties;
    final String extraWhere;
    final BeanPropertyAssoc<?> nodeBeanProp;
    final TableJoin[] tableJoins;
    final boolean readId;
    final boolean disableLazyLoad;
    final InheritInfo inheritInfo;
    final String prefix;
    final Set<String> includedProps;
    final Map<String, String> pathMap;
    
    public SqlTreeNodeBean(final String prefix, final BeanPropertyAssoc<?> beanProp, final SqlTreeProperties props, final List<SqlTreeNode> myChildren, final boolean withId) {
        this(prefix, beanProp, beanProp.getTargetDescriptor(), props, myChildren, withId);
    }
    
    public SqlTreeNodeBean(final String prefix, final BeanPropertyAssoc<?> beanProp, final BeanDescriptor<?> desc, final SqlTreeProperties props, final List<SqlTreeNode> myChildren, final boolean withId) {
        this.prefix = prefix;
        this.nodeBeanProp = beanProp;
        this.desc = desc;
        this.inheritInfo = desc.getInheritInfo();
        this.extraWhere = ((beanProp == null) ? null : beanProp.getExtraWhere());
        this.idBinder = desc.getIdBinder();
        this.readId = (withId && desc.propertiesId().length > 0);
        this.disableLazyLoad = (!this.readId || desc.isSqlSelectBased());
        this.tableJoins = props.getTableJoins();
        this.partialObject = props.isPartialObject();
        this.partialProps = props.getIncludedProperties();
        this.partialHash = (this.partialObject ? this.partialProps.hashCode() : 0);
        this.readOnlyLeaf = props.isReadOnly();
        this.properties = props.getProps();
        if (this.partialObject) {
            this.includedProps = LoadedPropertiesCache.get(this.partialHash, this.partialProps, desc);
        }
        else {
            this.includedProps = null;
        }
        if (myChildren == null) {
            this.children = SqlTreeNodeBean.NO_CHILDREN;
        }
        else {
            this.children = myChildren.toArray(new SqlTreeNode[myChildren.size()]);
        }
        this.pathMap = this.createPathMap(prefix, desc);
    }
    
    private Map<String, String> createPathMap(final String prefix, final BeanDescriptor<?> desc) {
        final BeanPropertyAssocMany<?>[] manys = desc.propertiesMany();
        final HashMap<String, String> m = new HashMap<String, String>();
        for (int i = 0; i < manys.length; ++i) {
            final String name = manys[i].getName();
            m.put(name, this.getPath(prefix, name));
        }
        return m;
    }
    
    private String getPath(final String prefix, final String propertyName) {
        if (prefix == null) {
            return propertyName;
        }
        return prefix + "." + propertyName;
    }
    
    protected void postLoad(final DbReadContext cquery, final Object loadedBean, final Object id) {
    }
    
    public void buildSelectExpressionChain(final List<String> selectChain) {
        if (this.readId) {
            this.idBinder.buildSelectExpressionChain(this.prefix, selectChain);
        }
        for (int i = 0, x = this.properties.length; i < x; ++i) {
            this.properties[i].buildSelectExpressionChain(this.prefix, selectChain);
        }
        for (int i = 0; i < this.children.length; ++i) {
            this.children[i].buildSelectExpressionChain(selectChain);
        }
    }
    
    public void load(final DbReadContext ctx, final Object parentBean, final int parentState) throws SQLException {
        Object contextBean = null;
        IdBinder localIdBinder;
        Object localBean;
        Class<?> localType;
        BeanDescriptor<?> localDesc;
        if (this.inheritInfo != null) {
            final InheritInfo localInfo = this.inheritInfo.readType(ctx);
            if (localInfo == null) {
                localIdBinder = this.idBinder;
                localBean = null;
                localType = null;
                localDesc = this.desc;
            }
            else {
                localBean = localInfo.createBean(ctx.isVanillaMode());
                localType = localInfo.getType();
                localIdBinder = localInfo.getIdBinder();
                localDesc = localInfo.getBeanDescriptor();
            }
        }
        else {
            localType = null;
            localDesc = this.desc;
            localBean = this.desc.createBean(ctx.isVanillaMode());
            localIdBinder = this.idBinder;
        }
        final SpiQuery.Mode queryMode = ctx.getQueryMode();
        final PersistenceContext persistenceContext = ctx.getPersistenceContext();
        Object id = null;
        if (this.readId) {
            id = localIdBinder.readSet(ctx, localBean);
            if (id == null) {
                localBean = null;
            }
            else {
                contextBean = persistenceContext.putIfAbsent(id, localBean);
                if (contextBean == null) {
                    contextBean = localBean;
                }
                else if (queryMode.isLoadContextBean()) {
                    localBean = contextBean;
                    if (localBean instanceof EntityBean) {
                        ((EntityBean)localBean)._ebean_getIntercept().setIntercepting(false);
                    }
                }
                else {
                    localBean = null;
                }
            }
        }
        ctx.setCurrentPrefix(this.prefix, this.pathMap);
        ctx.propagateState(localBean);
        final SqlBeanLoad sqlBeanLoad = new SqlBeanLoad(ctx, localType, localBean, queryMode);
        if (this.inheritInfo == null) {
            for (int i = 0, x = this.properties.length; i < x; ++i) {
                this.properties[i].load(sqlBeanLoad);
            }
        }
        else {
            for (int i = 0, x = this.properties.length; i < x; ++i) {
                final BeanProperty p = localDesc.getBeanProperty(this.properties[i].getName());
                if (p != null) {
                    p.load(sqlBeanLoad);
                }
                else {
                    this.properties[i].loadIgnore(ctx);
                }
            }
        }
        for (int i = 0, x = this.tableJoins.length; i < x; ++i) {
            this.tableJoins[i].load(sqlBeanLoad);
        }
        boolean lazyLoadMany = false;
        if (localBean == null && queryMode.equals(SpiQuery.Mode.LAZYLOAD_MANY)) {
            localBean = contextBean;
            lazyLoadMany = true;
        }
        for (int j = 0; j < this.children.length; ++j) {
            this.children[j].load(ctx, localBean, parentState);
        }
        if (!lazyLoadMany) {
            if (localBean != null) {
                ctx.setCurrentPrefix(this.prefix, this.pathMap);
                if (!ctx.isVanillaMode()) {
                    this.createListProxies(localDesc, ctx, localBean);
                }
                localDesc.postLoad(localBean, this.includedProps);
                if (localBean instanceof EntityBean) {
                    final EntityBeanIntercept ebi = ((EntityBean)localBean)._ebean_getIntercept();
                    ebi.setPersistenceContext(persistenceContext);
                    ebi.setLoadedProps(this.includedProps);
                    if (SpiQuery.Mode.LAZYLOAD_BEAN.equals(queryMode)) {
                        ebi.setLoadedLazy();
                    }
                    else {
                        ebi.setLoaded();
                    }
                    if (this.partialObject) {
                        ctx.register(null, ebi);
                    }
                    if (this.disableLazyLoad) {
                        ebi.setDisableLazyLoad(true);
                    }
                    if (ctx.isAutoFetchProfiling()) {
                        ctx.profileBean(ebi, this.prefix);
                    }
                }
            }
        }
        if (parentBean != null && contextBean != null) {
            this.nodeBeanProp.setValue(parentBean, contextBean);
        }
        if (!this.readId) {
            this.postLoad(ctx, localBean, id);
        }
        else {
            this.postLoad(ctx, contextBean, id);
        }
    }
    
    private void createListProxies(final BeanDescriptor<?> localDesc, final DbReadContext ctx, final Object localBean) {
        final BeanPropertyAssocMany<?> fetchedMany = ctx.getManyProperty();
        final BeanPropertyAssocMany<?>[] manys = localDesc.propertiesMany();
        for (int i = 0; i < manys.length; ++i) {
            if (fetchedMany == null || !fetchedMany.equals(manys[i])) {
                final BeanCollection<?> ref = manys[i].createReferenceIfNull(localBean);
                if (ref != null) {
                    ctx.register(manys[i].getName(), ref);
                }
            }
        }
    }
    
    public void appendSelect(final DbSqlContext ctx, final boolean subQuery) {
        ctx.pushJoin(this.prefix);
        ctx.pushTableAlias(this.prefix);
        if (this.nodeBeanProp != null) {
            ctx.append('\n').append("        ");
        }
        if (!subQuery && this.inheritInfo != null) {
            ctx.appendColumn(this.inheritInfo.getDiscriminatorColumn());
        }
        if (this.readId) {
            this.appendSelect(ctx, false, this.idBinder.getProperties());
        }
        this.appendSelect(ctx, subQuery, this.properties);
        this.appendSelectTableJoins(ctx);
        for (int i = 0; i < this.children.length; ++i) {
            this.children[i].appendSelect(ctx, subQuery);
        }
        ctx.popTableAlias();
        ctx.popJoin();
    }
    
    private void appendSelectTableJoins(final DbSqlContext ctx) {
        final String baseAlias = ctx.getTableAlias(this.prefix);
        for (int i = 0; i < this.tableJoins.length; ++i) {
            final TableJoin join = this.tableJoins[i];
            final String alias = baseAlias + i;
            ctx.pushSecondaryTableAlias(alias);
            join.appendSelect(ctx, false);
            ctx.popTableAlias();
        }
    }
    
    private void appendSelect(final DbSqlContext ctx, final boolean subQuery, final BeanProperty[] props) {
        for (int i = 0; i < props.length; ++i) {
            props[i].appendSelect(ctx, subQuery);
        }
    }
    
    public void appendWhere(final DbSqlContext ctx) {
        if (this.inheritInfo != null) {
            if (!this.inheritInfo.isRoot()) {
                if (ctx.length() > 0) {
                    ctx.append(" and");
                }
                ctx.append(" ").append(ctx.getTableAlias(this.prefix)).append(".");
                ctx.append(this.inheritInfo.getWhere()).append(" ");
            }
        }
        if (this.extraWhere != null) {
            if (ctx.length() > 0) {
                ctx.append(" and");
            }
            final String ta = ctx.getTableAlias(this.prefix);
            final String ew = StringHelper.replaceString(this.extraWhere, "${ta}", ta);
            ctx.append(" ").append(ew).append(" ");
        }
        for (int i = 0; i < this.children.length; ++i) {
            this.children[i].appendWhere(ctx);
        }
    }
    
    public void appendFrom(final DbSqlContext ctx, boolean forceOuterJoin) {
        ctx.pushJoin(this.prefix);
        ctx.pushTableAlias(this.prefix);
        forceOuterJoin = this.appendFromBaseTable(ctx, forceOuterJoin);
        for (int i = 0; i < this.properties.length; ++i) {
            this.properties[i].appendFrom(ctx, forceOuterJoin);
        }
        for (int i = 0; i < this.children.length; ++i) {
            this.children[i].appendFrom(ctx, forceOuterJoin);
        }
        ctx.popTableAlias();
        ctx.popJoin();
    }
    
    public boolean appendFromBaseTable(final DbSqlContext ctx, final boolean forceOuterJoin) {
        if (this.nodeBeanProp instanceof BeanPropertyAssocMany) {
            final BeanPropertyAssocMany<?> manyProp = (BeanPropertyAssocMany<?>)(BeanPropertyAssocMany)this.nodeBeanProp;
            if (manyProp.isManyToMany()) {
                final String alias = ctx.getTableAlias(this.prefix);
                final String[] split = SplitName.split(this.prefix);
                final String parentAlias = ctx.getTableAlias(split[0]);
                final String alias2 = alias + "z_";
                final TableJoin manyToManyJoin = manyProp.getIntersectionTableJoin();
                manyToManyJoin.addJoin(forceOuterJoin, parentAlias, alias2, ctx);
                return this.nodeBeanProp.addJoin(forceOuterJoin, alias2, alias, ctx);
            }
        }
        return this.nodeBeanProp.addJoin(forceOuterJoin, this.prefix, ctx);
    }
    
    public String toString() {
        return "SqlTreeNodeBean: " + this.desc;
    }
    
    static {
        NO_CHILDREN = new SqlTreeNode[0];
    }
}
