// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebeaninternal.server.core.ReferenceOptions;
import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
import java.sql.SQLException;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.query.SplitName;
import com.avaje.ebean.InvalidValue;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.Query;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Transaction;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import com.avaje.ebean.SqlUpdate;
import java.util.List;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
import java.util.HashMap;

public class BeanPropertyAssocOne<T> extends BeanPropertyAssoc<T>
{
    private final boolean oneToOne;
    private final boolean oneToOneExported;
    private final boolean embeddedVersion;
    private final boolean importedPrimaryKey;
    private final LocalHelp localHelp;
    private final BeanProperty[] embeddedProps;
    private final HashMap<String, BeanProperty> embeddedPropsMap;
    private ImportedId importedId;
    private ExportedProperty[] exportedProperties;
    private String deleteByParentIdSql;
    private String deleteByParentIdInSql;
    
    public BeanPropertyAssocOne(final BeanDescriptorMap owner, final DeployBeanPropertyAssocOne<T> deploy) {
        this(owner, null, deploy);
    }
    
    public BeanPropertyAssocOne(final BeanDescriptorMap owner, final BeanDescriptor<?> descriptor, final DeployBeanPropertyAssocOne<T> deploy) {
        super(owner, descriptor, deploy);
        this.importedPrimaryKey = deploy.isImportedPrimaryKey();
        this.oneToOne = deploy.isOneToOne();
        this.oneToOneExported = deploy.isOneToOneExported();
        if (this.embedded) {
            final BeanEmbeddedMeta overrideMeta = BeanEmbeddedMetaFactory.create(owner, deploy, descriptor);
            this.embeddedProps = overrideMeta.getProperties();
            if (this.id) {
                this.embeddedVersion = false;
            }
            else {
                this.embeddedVersion = overrideMeta.isEmbeddedVersion();
            }
            this.embeddedPropsMap = new HashMap<String, BeanProperty>();
            for (int i = 0; i < this.embeddedProps.length; ++i) {
                this.embeddedPropsMap.put(this.embeddedProps[i].getName(), this.embeddedProps[i]);
            }
        }
        else {
            this.embeddedProps = null;
            this.embeddedPropsMap = null;
            this.embeddedVersion = false;
        }
        this.localHelp = this.createHelp(this.embedded, this.oneToOneExported);
    }
    
    public void initialise() {
        super.initialise();
        if (!this.isTransient) {
            if (!this.embedded) {
                if (!this.oneToOneExported) {
                    this.importedId = this.createImportedId(this, this.targetDescriptor, this.tableJoin);
                }
                else {
                    this.exportedProperties = this.createExported();
                    final String delStmt = "delete from " + this.targetDescriptor.getBaseTable() + " where ";
                    this.deleteByParentIdSql = delStmt + this.deriveWhereParentIdSql(false);
                    this.deleteByParentIdInSql = delStmt + this.deriveWhereParentIdSql(true);
                }
            }
        }
    }
    
    public ElPropertyValue buildElPropertyValue(final String propName, final String remainder, ElPropertyChainBuilder chain, final boolean propertyDeploy) {
        if (!this.embedded) {
            return this.createElPropertyValue(propName, remainder, chain, propertyDeploy);
        }
        final BeanProperty embProp = this.embeddedPropsMap.get(remainder);
        if (embProp == null) {
            final String msg = "Embedded Property " + remainder + " not found in " + this.getFullBeanName();
            throw new PersistenceException(msg);
        }
        if (chain == null) {
            chain = new ElPropertyChainBuilder(true, propName);
        }
        chain.add(this);
        return chain.add(embProp).build();
    }
    
    public String getElPlaceholder(final boolean encrypted) {
        return encrypted ? this.elPlaceHolderEncrypted : this.elPlaceHolder;
    }
    
    public void copyProperty(final Object sourceBean, final Object destBean, final CopyContext ctx, final int maxDepth) {
        this.localHelp.copyProperty(sourceBean, destBean, ctx, maxDepth);
    }
    
    public SqlUpdate deleteByParentId(final Object parentId, final List<Object> parentIdist) {
        if (parentId != null) {
            return this.deleteByParentId(parentId);
        }
        return this.deleteByParentIdList(parentIdist);
    }
    
    private SqlUpdate deleteByParentIdList(final List<Object> parentIdist) {
        final StringBuilder sb = new StringBuilder(100);
        sb.append(this.deleteByParentIdInSql);
        final String inClause = this.targetIdBinder.getIdInValueExpr(parentIdist.size());
        sb.append(inClause);
        final DefaultSqlUpdate delete = new DefaultSqlUpdate(sb.toString());
        for (int i = 0; i < parentIdist.size(); ++i) {
            this.targetIdBinder.bindId(delete, parentIdist.get(i));
        }
        return delete;
    }
    
    private SqlUpdate deleteByParentId(final Object parentId) {
        final DefaultSqlUpdate delete = new DefaultSqlUpdate(this.deleteByParentIdSql);
        if (this.exportedProperties.length == 1) {
            delete.addParameter(parentId);
        }
        else {
            this.targetDescriptor.getIdBinder().bindId(delete, parentId);
        }
        return delete;
    }
    
    public List<Object> findIdsByParentId(final Object parentId, final List<Object> parentIdist, final Transaction t) {
        if (parentId != null) {
            return this.findIdsByParentId(parentId, t);
        }
        return this.findIdsByParentIdList(parentIdist, t);
    }
    
    private List<Object> findIdsByParentId(final Object parentId, final Transaction t) {
        final String rawWhere = this.deriveWhereParentIdSql(false);
        final EbeanServer server = this.getBeanDescriptor().getEbeanServer();
        final Query<?> q = server.find(this.getPropertyType()).where().raw(rawWhere).query();
        this.bindWhereParendId(q, parentId);
        return server.findIds(q, t);
    }
    
    private List<Object> findIdsByParentIdList(final List<Object> parentIdist, final Transaction t) {
        final String rawWhere = this.deriveWhereParentIdSql(true);
        final String inClause = this.targetIdBinder.getIdInValueExpr(parentIdist.size());
        final String expr = rawWhere + inClause;
        final EbeanServer server = this.getBeanDescriptor().getEbeanServer();
        final Query<?> q = (Query<?>)(Query)server.find(this.getPropertyType()).where().raw(expr);
        for (int i = 0; i < parentIdist.size(); ++i) {
            this.bindWhereParendId(q, parentIdist.get(i));
        }
        return server.findIds(q, t);
    }
    
    private void bindWhereParendId(final Query<?> q, final Object parentId) {
        if (this.exportedProperties.length == 1) {
            q.setParameter(1, parentId);
        }
        else {
            int pos = 1;
            for (int i = 0; i < this.exportedProperties.length; ++i) {
                final Object embVal = this.exportedProperties[i].getValue(parentId);
                q.setParameter(pos++, embVal);
            }
        }
    }
    
    public void addFkey() {
        if (this.importedId != null) {
            this.importedId.addFkeys(this.name);
        }
    }
    
    public boolean isValueLoaded(final Object value) {
        return !(value instanceof EntityBean) || ((EntityBean)value)._ebean_getIntercept().isLoaded();
    }
    
    public InvalidValue validateCascade(final Object value) {
        final BeanDescriptor<?> target = this.getTargetDescriptor();
        return target.validate(true, value);
    }
    
    private boolean hasChangedEmbedded(final Object bean, final Object oldValues) {
        final Object embValue = this.getValue(oldValues);
        if (embValue instanceof EntityBean) {
            return ((EntityBean)embValue)._ebean_getIntercept().isNewOrDirty();
        }
        return embValue == null && this.getValue(bean) != null;
    }
    
    public boolean hasChanged(final Object bean, final Object oldValues) {
        if (this.embedded) {
            return this.hasChangedEmbedded(bean, oldValues);
        }
        final Object value = this.getValue(bean);
        final Object oldVal = this.getValue(oldValues);
        if (this.oneToOneExported) {
            return false;
        }
        if (value == null) {
            return oldVal != null;
        }
        return oldValues == null || this.importedId.hasChanged(value, oldVal);
    }
    
    public BeanProperty[] getProperties() {
        return this.embeddedProps;
    }
    
    public void buildSelectExpressionChain(String prefix, final List<String> selectChain) {
        prefix = SplitName.add(prefix, this.name);
        if (!this.embedded) {
            this.targetIdBinder.buildSelectExpressionChain(prefix, selectChain);
        }
        else {
            for (int i = 0; i < this.embeddedProps.length; ++i) {
                this.embeddedProps[i].buildSelectExpressionChain(prefix, selectChain);
            }
        }
    }
    
    public boolean isOneToOne() {
        return this.oneToOne;
    }
    
    public boolean isOneToOneExported() {
        return this.oneToOneExported;
    }
    
    public boolean isEmbeddedVersion() {
        return this.embeddedVersion;
    }
    
    public boolean isImportedPrimaryKey() {
        return this.importedPrimaryKey;
    }
    
    public Class<?> getTargetType() {
        return this.getPropertyType();
    }
    
    public Object[] getAssocOneIdValues(final Object bean) {
        return this.targetDescriptor.getIdBinder().getIdValues(bean);
    }
    
    public String getAssocOneIdExpr(final String prefix, final String operator) {
        return this.targetDescriptor.getIdBinder().getAssocOneIdExpr(prefix, operator);
    }
    
    public String getAssocIdInValueExpr(final int size) {
        return this.targetDescriptor.getIdBinder().getIdInValueExpr(size);
    }
    
    public String getAssocIdInExpr(final String prefix) {
        return this.targetDescriptor.getIdBinder().getAssocIdInExpr(prefix);
    }
    
    public boolean isAssocId() {
        return !this.embedded;
    }
    
    public boolean isAssocProperty() {
        return !this.embedded;
    }
    
    public Object createEmbeddedId() {
        return this.getTargetDescriptor().createVanillaBean();
    }
    
    public Object createEmptyReference() {
        return this.targetDescriptor.createEntityBean();
    }
    
    public void elSetReference(final Object bean) {
        final Object value = this.getValueIntercept(bean);
        if (value != null) {
            ((EntityBean)value)._ebean_getIntercept().setReference();
        }
    }
    
    public Object elGetReference(final Object bean) {
        Object value = this.getValueIntercept(bean);
        if (value == null) {
            value = this.targetDescriptor.createEntityBean();
            this.setValueIntercept(bean, value);
        }
        return value;
    }
    
    public ImportedId getImportedId() {
        return this.importedId;
    }
    
    private String deriveWhereParentIdSql(final boolean inClause) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.exportedProperties.length; ++i) {
            final String fkColumn = this.exportedProperties[i].getForeignDbColumn();
            if (i > 0) {
                final String s = inClause ? "," : " and ";
                sb.append(s);
            }
            sb.append(fkColumn);
            if (!inClause) {
                sb.append("=? ");
            }
        }
        return sb.toString();
    }
    
    private ExportedProperty[] createExported() {
        final BeanProperty[] uids = this.descriptor.propertiesId();
        final ArrayList<ExportedProperty> list = new ArrayList<ExportedProperty>();
        if (uids.length == 1 && uids[0].isEmbedded()) {
            final BeanPropertyAssocOne<?> one = (BeanPropertyAssocOne<?>)uids[0];
            final BeanDescriptor<?> targetDesc = one.getTargetDescriptor();
            final BeanProperty[] emIds = targetDesc.propertiesBaseScalar();
            try {
                for (int i = 0; i < emIds.length; ++i) {
                    final ExportedProperty expProp = this.findMatch(true, emIds[i]);
                    list.add(expProp);
                }
            }
            catch (PersistenceException e) {
                e.printStackTrace();
            }
        }
        else {
            for (int j = 0; j < uids.length; ++j) {
                final ExportedProperty expProp2 = this.findMatch(false, uids[j]);
                list.add(expProp2);
            }
        }
        return list.toArray(new ExportedProperty[list.size()]);
    }
    
    private ExportedProperty findMatch(final boolean embeddedProp, final BeanProperty prop) {
        final String matchColumn = prop.getDbColumn();
        final String searchTable = this.tableJoin.getTable();
        final TableJoinColumn[] columns = this.tableJoin.columns();
        for (int i = 0; i < columns.length; ++i) {
            final String matchTo = columns[i].getLocalDbColumn();
            if (matchColumn.equalsIgnoreCase(matchTo)) {
                final String foreignCol = columns[i].getForeignDbColumn();
                return new ExportedProperty(embeddedProp, foreignCol, prop);
            }
        }
        final String msg = "Error with the Join on [" + this.getFullBeanName() + "]. Could not find the matching foreign key for [" + matchColumn + "] in table[" + searchTable + "]?" + " Perhaps using a @JoinColumn with the name/referencedColumnName attributes swapped?";
        throw new PersistenceException(msg);
    }
    
    public void appendSelect(final DbSqlContext ctx, final boolean subQuery) {
        if (!this.isTransient) {
            this.localHelp.appendSelect(ctx, subQuery);
        }
    }
    
    public void appendFrom(final DbSqlContext ctx, final boolean forceOuterJoin) {
        if (!this.isTransient) {
            this.localHelp.appendFrom(ctx, forceOuterJoin);
        }
    }
    
    public Object readSet(final DbReadContext ctx, final Object bean, final Class<?> type) throws SQLException {
        final boolean assignable = type == null || this.owningType.isAssignableFrom(type);
        return this.localHelp.readSet(ctx, bean, assignable);
    }
    
    public Object read(final DbReadContext ctx) throws SQLException {
        return this.localHelp.read(ctx);
    }
    
    public void loadIgnore(final DbReadContext ctx) {
        this.localHelp.loadIgnore(ctx);
    }
    
    public void load(final SqlBeanLoad sqlBeanLoad) throws SQLException {
        final Object dbVal = sqlBeanLoad.load(this);
        if (this.embedded && sqlBeanLoad.isLazyLoad() && dbVal instanceof EntityBean) {
            ((EntityBean)dbVal)._ebean_getIntercept().setLoaded();
        }
    }
    
    private LocalHelp createHelp(final boolean embedded, final boolean oneToOneExported) {
        if (embedded) {
            return new Embedded();
        }
        if (oneToOneExported) {
            return new ReferenceExported();
        }
        return new Reference(this);
    }
    
    public void jsonWrite(final WriteJsonContext ctx, final Object bean) {
        final Object value = this.getValueIntercept(bean);
        if (value == null) {
            ctx.beginAssocOneIsNull(this.name);
        }
        else if (!ctx.isParentBean(value)) {
            ctx.pushParentBean(bean);
            ctx.beginAssocOne(this.name);
            final BeanDescriptor<?> refDesc = this.descriptor.getBeanDescriptor(value.getClass());
            refDesc.jsonWrite(ctx, value);
            ctx.endAssocOne();
            ctx.popParentBean();
        }
    }
    
    public void jsonRead(final ReadJsonContext ctx, final Object bean) {
        final T assocBean = this.targetDescriptor.jsonReadBean(ctx, this.name);
        this.setValue(bean, assocBean);
    }
    
    private abstract class LocalHelp
    {
        abstract void copyProperty(final Object p0, final Object p1, final CopyContext p2, final int p3);
        
        abstract void loadIgnore(final DbReadContext p0);
        
        abstract Object read(final DbReadContext p0) throws SQLException;
        
        abstract Object readSet(final DbReadContext p0, final Object p1, final boolean p2) throws SQLException;
        
        abstract void appendSelect(final DbSqlContext p0, final boolean p1);
        
        abstract void appendFrom(final DbSqlContext p0, final boolean p1);
        
        void copy(final Object sourceBean, final Object destBean, final CopyContext ctx, final int maxDepth) {
            final Object value = BeanPropertyAssocOne.this.getValue(sourceBean);
            if (value != null) {
                final Class<?> valueClass = value.getClass();
                final BeanDescriptor<?> refDesc = BeanPropertyAssocOne.this.descriptor.getBeanDescriptor(valueClass);
                final Object refId = refDesc.getId(value);
                Object destRef = ctx.get(valueClass, refId);
                if (destRef == null) {
                    if (maxDepth > 1) {
                        destRef = refDesc.createCopy(value, ctx, maxDepth - 1);
                    }
                    else {
                        destRef = refDesc.createReference(ctx.isVanillaMode(), refId, destBean, null);
                    }
                }
                BeanPropertyAssocOne.this.setValue(destBean, destRef);
            }
        }
    }
    
    private final class Embedded extends LocalHelp
    {
        void copyProperty(final Object sourceBean, final Object destBean, final CopyContext ctx, final int maxDepth) {
            final Object srcEmb = BeanPropertyAssocOne.this.getValue(sourceBean);
            if (srcEmb != null) {
                final Object dstEmb = BeanPropertyAssocOne.this.targetDescriptor.createBean(ctx.isVanillaMode());
                for (int i = 0; i < BeanPropertyAssocOne.this.embeddedProps.length; ++i) {
                    BeanPropertyAssocOne.this.embeddedProps[i].copyProperty(srcEmb, dstEmb, ctx, maxDepth);
                }
                BeanPropertyAssocOne.this.setValue(destBean, dstEmb);
            }
        }
        
        void loadIgnore(final DbReadContext ctx) {
            for (int i = 0; i < BeanPropertyAssocOne.this.embeddedProps.length; ++i) {
                BeanPropertyAssocOne.this.embeddedProps[i].loadIgnore(ctx);
            }
        }
        
        Object readSet(final DbReadContext ctx, final Object bean, final boolean assignable) throws SQLException {
            final Object dbVal = this.read(ctx);
            if (bean != null && assignable) {
                BeanPropertyAssocOne.this.setValue(bean, dbVal);
                ctx.propagateState(dbVal);
                return dbVal;
            }
            return null;
        }
        
        Object read(final DbReadContext ctx) throws SQLException {
            final EntityBean embeddedBean = BeanPropertyAssocOne.this.targetDescriptor.createEntityBean();
            boolean notNull = false;
            for (int i = 0; i < BeanPropertyAssocOne.this.embeddedProps.length; ++i) {
                final Object value = BeanPropertyAssocOne.this.embeddedProps[i].readSet(ctx, embeddedBean, null);
                if (value != null) {
                    notNull = true;
                }
            }
            if (notNull) {
                ctx.propagateState(embeddedBean);
                return embeddedBean;
            }
            return null;
        }
        
        void appendFrom(final DbSqlContext ctx, final boolean forceOuterJoin) {
        }
        
        void appendSelect(final DbSqlContext ctx, final boolean subQuery) {
            for (int i = 0; i < BeanPropertyAssocOne.this.embeddedProps.length; ++i) {
                BeanPropertyAssocOne.this.embeddedProps[i].appendSelect(ctx, subQuery);
            }
        }
    }
    
    private final class Reference extends LocalHelp
    {
        private final BeanPropertyAssocOne<?> beanProp;
        
        Reference(final BeanPropertyAssocOne<?> beanProp) {
            this.beanProp = beanProp;
        }
        
        void copyProperty(final Object sourceBean, final Object destBean, final CopyContext ctx, final int maxDepth) {
            this.copy(sourceBean, destBean, ctx, maxDepth);
        }
        
        void loadIgnore(final DbReadContext ctx) {
            BeanPropertyAssocOne.this.targetIdBinder.loadIgnore(ctx);
            if (BeanPropertyAssocOne.this.targetInheritInfo != null) {
                ctx.getDataReader().incrementPos(1);
            }
        }
        
        Object readSet(final DbReadContext ctx, final Object bean, final boolean assignable) throws SQLException {
            final Object val = this.read(ctx);
            if (bean != null && assignable) {
                BeanPropertyAssocOne.this.setValue(bean, val);
                ctx.propagateState(val);
            }
            return val;
        }
        
        Object read(final DbReadContext ctx) throws SQLException {
            BeanDescriptor<?> rowDescriptor = null;
            Class<?> rowType = BeanPropertyAssocOne.this.targetType;
            if (BeanPropertyAssocOne.this.targetInheritInfo != null) {
                final InheritInfo rowInheritInfo = BeanPropertyAssocOne.this.targetInheritInfo.readType(ctx);
                if (rowInheritInfo != null) {
                    rowType = rowInheritInfo.getType();
                    rowDescriptor = rowInheritInfo.getBeanDescriptor();
                }
            }
            final Object id = BeanPropertyAssocOne.this.targetIdBinder.read(ctx);
            if (id == null) {
                return null;
            }
            final Object existing = ctx.getPersistenceContext().get(rowType, id);
            if (existing != null) {
                return existing;
            }
            final Object parent = null;
            Object ref = null;
            final boolean vanillaMode = ctx.isVanillaMode();
            final int parentState = ctx.getParentState();
            final ReferenceOptions options = ctx.getReferenceOptionsFor(this.beanProp);
            if (options != null && options.isUseCache()) {
                ref = BeanPropertyAssocOne.this.targetDescriptor.cacheGet(id);
                if (ref != null) {
                    if (vanillaMode) {
                        ref = BeanPropertyAssocOne.this.targetDescriptor.createCopy(ref, new CopyContext(true), 5);
                    }
                    else if (parentState == 1) {
                        ref = BeanPropertyAssocOne.this.targetDescriptor.createCopy(ref, new CopyContext(false), 5);
                    }
                    else if (parentState == 0 && !options.isReadOnly()) {
                        ref = BeanPropertyAssocOne.this.targetDescriptor.createCopy(ref, new CopyContext(false), 5);
                    }
                }
            }
            boolean createReference = false;
            if (ref == null) {
                createReference = true;
                if (BeanPropertyAssocOne.this.targetInheritInfo != null) {
                    ref = rowDescriptor.createReference(vanillaMode, id, parent, options);
                }
                else {
                    ref = BeanPropertyAssocOne.this.targetDescriptor.createReference(vanillaMode, id, parent, options);
                }
            }
            final Object existingBean = ctx.getPersistenceContext().putIfAbsent(id, ref);
            if (existingBean != null) {
                ref = existingBean;
                createReference = false;
            }
            if (!vanillaMode) {
                final EntityBeanIntercept ebi = ((EntityBean)ref)._ebean_getIntercept();
                if (createReference) {
                    if (parentState != 0) {
                        ebi.setState(parentState);
                    }
                    ctx.register(BeanPropertyAssocOne.this.name, ebi);
                }
            }
            return ref;
        }
        
        void appendFrom(final DbSqlContext ctx, final boolean forceOuterJoin) {
            if (BeanPropertyAssocOne.this.targetInheritInfo != null) {
                final String relativePrefix = ctx.getRelativePrefix(BeanPropertyAssocOne.this.name);
                BeanPropertyAssocOne.this.tableJoin.addJoin(forceOuterJoin, relativePrefix, ctx);
            }
        }
        
        void appendSelect(final DbSqlContext ctx, final boolean subQuery) {
            if (!subQuery && BeanPropertyAssocOne.this.targetInheritInfo != null) {
                final String relativePrefix = ctx.getRelativePrefix(BeanPropertyAssocOne.this.getName());
                final String tableAlias = ctx.getTableAlias(relativePrefix);
                ctx.appendColumn(tableAlias, BeanPropertyAssocOne.this.targetInheritInfo.getDiscriminatorColumn());
            }
            BeanPropertyAssocOne.this.importedId.sqlAppend(ctx);
        }
    }
    
    private final class ReferenceExported extends LocalHelp
    {
        void copyProperty(final Object sourceBean, final Object destBean, final CopyContext ctx, final int maxDepth) {
            this.copy(sourceBean, destBean, ctx, maxDepth);
        }
        
        void loadIgnore(final DbReadContext ctx) {
            BeanPropertyAssocOne.this.targetDescriptor.getIdBinder().loadIgnore(ctx);
        }
        
        Object readSet(final DbReadContext ctx, final Object bean, final boolean assignable) throws SQLException {
            final Object dbVal = this.read(ctx);
            if (bean != null && assignable) {
                BeanPropertyAssocOne.this.setValue(bean, dbVal);
                ctx.propagateState(dbVal);
            }
            return dbVal;
        }
        
        Object read(final DbReadContext ctx) throws SQLException {
            final IdBinder idBinder = BeanPropertyAssocOne.this.targetDescriptor.getIdBinder();
            final Object id = idBinder.read(ctx);
            if (id == null) {
                return null;
            }
            final PersistenceContext persistCtx = ctx.getPersistenceContext();
            final Object existing = persistCtx.get(BeanPropertyAssocOne.this.targetType, id);
            if (existing != null) {
                return existing;
            }
            final boolean vanillaMode = ctx.isVanillaMode();
            final Object parent = null;
            final Object ref = BeanPropertyAssocOne.this.targetDescriptor.createReference(vanillaMode, id, parent, null);
            if (!vanillaMode) {
                final EntityBeanIntercept ebi = ((EntityBean)ref)._ebean_getIntercept();
                if (ctx.getParentState() != 0) {
                    ebi.setState(ctx.getParentState());
                }
                persistCtx.put(id, ref);
                ctx.register(BeanPropertyAssocOne.this.name, ebi);
            }
            return ref;
        }
        
        void appendSelect(final DbSqlContext ctx, final boolean subQuery) {
            final String relativePrefix = ctx.getRelativePrefix(BeanPropertyAssocOne.this.getName());
            ctx.pushTableAlias(relativePrefix);
            final IdBinder idBinder = BeanPropertyAssocOne.this.targetDescriptor.getIdBinder();
            idBinder.appendSelect(ctx, subQuery);
            ctx.popTableAlias();
        }
        
        void appendFrom(final DbSqlContext ctx, final boolean forceOuterJoin) {
            final String relativePrefix = ctx.getRelativePrefix(BeanPropertyAssocOne.this.getName());
            BeanPropertyAssocOne.this.tableJoin.addJoin(forceOuterJoin, relativePrefix, ctx);
        }
    }
}
