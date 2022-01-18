// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import java.util.Iterator;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.bean.BeanCollectionAdd;
import com.avaje.ebean.InvalidValue;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebean.Expression;
import com.avaje.ebean.Query;
import com.avaje.ebean.EbeanServer;
import java.util.ArrayList;
import com.avaje.ebean.Transaction;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import com.avaje.ebean.SqlUpdate;
import java.util.List;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
import com.avaje.ebean.bean.BeanCollection;

public class BeanPropertyAssocMany<T> extends BeanPropertyAssoc<T>
{
    final TableJoin intersectionJoin;
    final TableJoin inverseJoin;
    final boolean unidirectional;
    final boolean manyToMany;
    final String fetchOrderBy;
    final String mapKey;
    final ManyType manyType;
    final String serverName;
    final BeanCollection.ModifyListenMode modifyListenMode;
    BeanProperty mapKeyProperty;
    ExportedProperty[] exportedProperties;
    BeanProperty childMasterProperty;
    boolean embeddedExportedProperties;
    BeanCollectionHelp<T> help;
    ImportedId importedId;
    String deleteByParentIdSql;
    String deleteByParentIdInSql;
    final CollectionTypeConverter typeConverter;
    
    public BeanPropertyAssocMany(final BeanDescriptorMap owner, final BeanDescriptor<?> descriptor, final DeployBeanPropertyAssocMany<T> deploy) {
        super(owner, descriptor, deploy);
        this.unidirectional = deploy.isUnidirectional();
        this.manyToMany = deploy.isManyToMany();
        this.serverName = descriptor.getServerName();
        this.manyType = deploy.getManyType();
        this.typeConverter = this.manyType.getTypeConverter();
        this.mapKey = deploy.getMapKey();
        this.fetchOrderBy = deploy.getFetchOrderBy();
        this.intersectionJoin = deploy.createIntersectionTableJoin();
        this.inverseJoin = deploy.createInverseTableJoin();
        this.modifyListenMode = deploy.getModifyListenMode();
    }
    
    public void initialise() {
        super.initialise();
        if (!this.isTransient) {
            this.help = BeanCollectionHelpFactory.create(this);
            if (this.manyToMany) {
                this.importedId = this.createImportedId(this, this.targetDescriptor, this.tableJoin);
            }
            else {
                this.childMasterProperty = this.initChildMasterProperty();
            }
            if (this.mapKey != null) {
                this.mapKeyProperty = this.initMapKeyProperty();
            }
            this.exportedProperties = this.createExported();
            if (this.exportedProperties.length > 0) {
                this.embeddedExportedProperties = this.exportedProperties[0].isEmbedded();
            }
            String delStmt;
            if (this.manyToMany) {
                delStmt = "delete from " + this.inverseJoin.getTable() + " where ";
            }
            else {
                delStmt = "delete from " + this.targetDescriptor.getBaseTable() + " where ";
            }
            this.deleteByParentIdSql = delStmt + this.deriveWhereParentIdSql(false);
            this.deleteByParentIdInSql = delStmt + this.deriveWhereParentIdSql(true);
        }
    }
    
    public Object getValueUnderlying(final Object bean) {
        Object value = this.getValue(bean);
        if (this.typeConverter != null) {
            value = this.typeConverter.toUnderlying(value);
        }
        return value;
    }
    
    public Object getValue(final Object bean) {
        return super.getValue(bean);
    }
    
    public Object getValueIntercept(final Object bean) {
        return super.getValueIntercept(bean);
    }
    
    public void setValue(final Object bean, Object value) {
        if (this.typeConverter != null) {
            value = this.typeConverter.toWrapped(value);
        }
        super.setValue(bean, value);
    }
    
    public void setValueIntercept(final Object bean, Object value) {
        if (this.typeConverter != null) {
            value = this.typeConverter.toWrapped(value);
        }
        super.setValueIntercept(bean, value);
    }
    
    public ElPropertyValue buildElPropertyValue(final String propName, final String remainder, final ElPropertyChainBuilder chain, final boolean propertyDeploy) {
        return this.createElPropertyValue(propName, remainder, chain, propertyDeploy);
    }
    
    public void copyProperty(final Object sourceBean, final Object destBean, final CopyContext ctx, final int maxDepth) {
        final Object sourceCollection = this.getValueUnderlying(sourceBean);
        if (sourceCollection != null) {
            final Object copyCollection = this.help.copyCollection(sourceCollection, ctx, maxDepth, destBean);
            this.setValue(destBean, copyCollection);
        }
    }
    
    public SqlUpdate deleteByParentId(final Object parentId, final List<Object> parentIdist) {
        if (parentId != null) {
            return this.deleteByParentId(parentId);
        }
        return this.deleteByParentIdList(parentIdist);
    }
    
    private SqlUpdate deleteByParentId(final Object parentId) {
        final DefaultSqlUpdate sqlDelete = new DefaultSqlUpdate(this.deleteByParentIdSql);
        this.bindWhereParendId(sqlDelete, parentId);
        return sqlDelete;
    }
    
    public List<Object> findIdsByParentId(final Object parentId, final List<Object> parentIdist, final Transaction t, final ArrayList<Object> excludeDetailIds) {
        if (parentId != null) {
            return this.findIdsByParentId(parentId, t, excludeDetailIds);
        }
        return this.findIdsByParentIdList(parentIdist, t, excludeDetailIds);
    }
    
    private List<Object> findIdsByParentId(final Object parentId, final Transaction t, final ArrayList<Object> excludeDetailIds) {
        final String rawWhere = this.deriveWhereParentIdSql(false);
        final EbeanServer server = this.getBeanDescriptor().getEbeanServer();
        final Query<?> q = server.find(this.getPropertyType()).where().raw(rawWhere).query();
        this.bindWhereParendId(1, q, parentId);
        if (excludeDetailIds != null && !excludeDetailIds.isEmpty()) {
            final Expression idIn = q.getExpressionFactory().idIn(excludeDetailIds);
            q.where().not(idIn);
        }
        return server.findIds(q, t);
    }
    
    private List<Object> findIdsByParentIdList(final List<Object> parentIdist, final Transaction t, final ArrayList<Object> excludeDetailIds) {
        final String rawWhere = this.deriveWhereParentIdSql(true);
        final String inClause = this.targetIdBinder.getIdInValueExpr(parentIdist.size());
        final String expr = rawWhere + inClause;
        final EbeanServer server = this.getBeanDescriptor().getEbeanServer();
        final Query<?> q = server.find(this.getPropertyType()).where().raw(expr).query();
        int pos = 1;
        for (int i = 0; i < parentIdist.size(); ++i) {
            pos = this.bindWhereParendId(pos, q, parentIdist.get(i));
        }
        if (excludeDetailIds != null && !excludeDetailIds.isEmpty()) {
            final Expression idIn = q.getExpressionFactory().idIn(excludeDetailIds);
            q.where().not(idIn);
        }
        return server.findIds(q, t);
    }
    
    private SqlUpdate deleteByParentIdList(final List<Object> parentIdist) {
        final StringBuilder sb = new StringBuilder(100);
        sb.append(this.deleteByParentIdInSql);
        final String inClause = this.targetIdBinder.getIdInValueExpr(parentIdist.size());
        sb.append(inClause);
        final DefaultSqlUpdate delete = new DefaultSqlUpdate(sb.toString());
        for (int i = 0; i < parentIdist.size(); ++i) {
            this.bindWhereParendId(delete, parentIdist.get(i));
        }
        return delete;
    }
    
    public void setLoader(final BeanCollectionLoader loader) {
        if (this.help != null) {
            this.help.setLoader(loader);
        }
    }
    
    public BeanCollection.ModifyListenMode getModifyListenMode() {
        return this.modifyListenMode;
    }
    
    public boolean hasChanged(final Object bean, final Object oldValues) {
        return false;
    }
    
    public void appendSelect(final DbSqlContext ctx, final boolean subQuery) {
    }
    
    public void loadIgnore(final DbReadContext ctx) {
    }
    
    public void load(final SqlBeanLoad sqlBeanLoad) throws SQLException {
        sqlBeanLoad.loadAssocMany(this);
    }
    
    public Object readSet(final DbReadContext ctx, final Object bean, final Class<?> type) throws SQLException {
        return null;
    }
    
    public Object read(final DbReadContext ctx) throws SQLException {
        return null;
    }
    
    public boolean isValueLoaded(final Object value) {
        return !(value instanceof BeanCollection) || ((BeanCollection)value).isPopulated();
    }
    
    public void add(final BeanCollection<?> collection, final Object bean) {
        this.help.add(collection, bean);
    }
    
    public InvalidValue validateCascade(final Object manyValue) {
        final ArrayList<InvalidValue> errs = this.help.validate(manyValue);
        if (errs == null) {
            return null;
        }
        return new InvalidValue("recurse.many", this.targetDescriptor.getFullName(), manyValue, InvalidValue.toArray(errs));
    }
    
    public void refresh(final EbeanServer server, final Query<?> query, final Transaction t, final Object parentBean) {
        this.help.refresh(server, query, t, parentBean);
    }
    
    public void refresh(final BeanCollection<?> bc, final Object parentBean) {
        this.help.refresh(bc, parentBean);
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
        return true;
    }
    
    public boolean isAssocProperty() {
        return true;
    }
    
    public boolean containsMany() {
        return true;
    }
    
    public ManyType getManyType() {
        return this.manyType;
    }
    
    public boolean isManyToMany() {
        return this.manyToMany;
    }
    
    public TableJoin getIntersectionTableJoin() {
        return this.intersectionJoin;
    }
    
    public void setJoinValuesToChild(final Object parent, final Object child, final Object mapKeyValue) {
        if (this.mapKeyProperty != null) {
            this.mapKeyProperty.setValue(child, mapKeyValue);
        }
        if (!this.manyToMany && this.childMasterProperty != null) {
            this.childMasterProperty.setValue(child, parent);
        }
    }
    
    public String getFetchOrderBy() {
        return this.fetchOrderBy;
    }
    
    public String getMapKey() {
        return this.mapKey;
    }
    
    public BeanCollection<?> createReferenceIfNull(final Object parentBean) {
        final Object v = this.getValue(parentBean);
        if (v instanceof BeanCollection) {
            final BeanCollection<?> bc = (BeanCollection<?>)v;
            return bc.isReference() ? bc : null;
        }
        return this.createReference(parentBean);
    }
    
    public BeanCollection<?> createReference(final Object parentBean) {
        final BeanCollection<?> ref = this.help.createReference(parentBean, this.name);
        this.setValue(parentBean, ref);
        return ref;
    }
    
    public Object createEmpty(final boolean vanilla) {
        return this.help.createEmpty(vanilla);
    }
    
    public BeanCollectionAdd getBeanCollectionAdd(final Object bc, final String mapKey) {
        return this.help.getBeanCollectionAdd(bc, mapKey);
    }
    
    public Object getParentId(final Object parentBean) {
        return this.descriptor.getId(parentBean);
    }
    
    private void bindWhereParendId(final DefaultSqlUpdate sqlUpd, final Object parentId) {
        if (this.exportedProperties.length == 1) {
            sqlUpd.addParameter(parentId);
            return;
        }
        for (int i = 0; i < this.exportedProperties.length; ++i) {
            final Object embVal = this.exportedProperties[i].getValue(parentId);
            sqlUpd.addParameter(embVal);
        }
    }
    
    private int bindWhereParendId(int pos, final Query<?> q, final Object parentId) {
        if (this.exportedProperties.length == 1) {
            q.setParameter(pos++, parentId);
        }
        else {
            for (int i = 0; i < this.exportedProperties.length; ++i) {
                final Object embVal = this.exportedProperties[i].getValue(parentId);
                q.setParameter(pos++, embVal);
            }
        }
        return pos;
    }
    
    private String deriveWhereParentIdSql(final boolean inClause) {
        final StringBuilder sb = new StringBuilder();
        if (inClause) {
            sb.append("(");
        }
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
        if (inClause) {
            sb.append(")");
        }
        return sb.toString();
    }
    
    public void setPredicates(final SpiQuery<?> query, Object parentBean) {
        if (this.manyToMany) {
            query.setIncludeTableJoin(this.inverseJoin);
        }
        if (this.embeddedExportedProperties) {
            final BeanProperty[] uids = this.descriptor.propertiesId();
            parentBean = uids[0].getValue(parentBean);
        }
        for (int i = 0; i < this.exportedProperties.length; ++i) {
            final Object val = this.exportedProperties[i].getValue(parentBean);
            String fkColumn = this.exportedProperties[i].getForeignDbColumn();
            if (!this.manyToMany) {
                fkColumn = this.targetDescriptor.getBaseTableAlias() + "." + fkColumn;
            }
            else {
                fkColumn = "int_." + fkColumn;
            }
            query.where().eq(fkColumn, val);
        }
        if (this.extraWhere != null) {
            final String ta = this.targetDescriptor.getBaseTableAlias();
            final String where = StringHelper.replaceString(this.extraWhere, "${ta}", ta);
            query.where().raw(where);
        }
        if (this.fetchOrderBy != null) {
            query.order(this.fetchOrderBy);
        }
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
    
    private ExportedProperty findMatch(final boolean embedded, final BeanProperty prop) {
        final String matchColumn = prop.getDbColumn();
        TableJoinColumn[] columns;
        String searchTable;
        if (this.manyToMany) {
            columns = this.intersectionJoin.columns();
            searchTable = this.intersectionJoin.getTable();
        }
        else {
            columns = this.tableJoin.columns();
            searchTable = this.tableJoin.getTable();
        }
        for (int i = 0; i < columns.length; ++i) {
            final String matchTo = columns[i].getLocalDbColumn();
            if (matchColumn.equalsIgnoreCase(matchTo)) {
                final String foreignCol = columns[i].getForeignDbColumn();
                return new ExportedProperty(embedded, foreignCol, prop);
            }
        }
        final String msg = "Error with the Join on [" + this.getFullBeanName() + "]. Could not find the matching foreign key for [" + matchColumn + "] in table[" + searchTable + "]?" + " Perhaps using a @JoinColumn with the name/referencedColumnName attributes swapped?";
        throw new PersistenceException(msg);
    }
    
    private BeanProperty initChildMasterProperty() {
        if (this.unidirectional) {
            return null;
        }
        final Class<?> beanType = this.descriptor.getBeanType();
        final BeanDescriptor<?> targetDesc = this.getTargetDescriptor();
        final BeanPropertyAssocOne<?>[] ones = targetDesc.propertiesOne();
        for (int i = 0; i < ones.length; ++i) {
            final BeanPropertyAssocOne<?> prop = ones[i];
            if (this.mappedBy != null) {
                if (this.mappedBy.equalsIgnoreCase(prop.getName())) {
                    return prop;
                }
            }
            else if (prop.getTargetType().equals(beanType)) {
                return prop;
            }
        }
        final String msg = "Can not find Master [" + beanType + "] in Child[" + targetDesc + "]";
        throw new RuntimeException(msg);
    }
    
    private BeanProperty initMapKeyProperty() {
        final BeanDescriptor<?> targetDesc = this.getTargetDescriptor();
        final Iterator<BeanProperty> it = targetDesc.propertiesAll();
        while (it.hasNext()) {
            final BeanProperty prop = it.next();
            if (this.mapKey.equalsIgnoreCase(prop.getName())) {
                return prop;
            }
        }
        final String from = this.descriptor.getFullName();
        final String to = targetDesc.getFullName();
        final String msg = from + ": Could not find mapKey property [" + this.mapKey + "] on [" + to + "]";
        throw new PersistenceException(msg);
    }
    
    public IntersectionRow buildManyDeleteChildren(final Object parentBean, final ArrayList<Object> excludeDetailIds) {
        final IntersectionRow row = new IntersectionRow(this.tableJoin.getTable());
        if (excludeDetailIds != null && !excludeDetailIds.isEmpty()) {
            row.setExcludeIds(excludeDetailIds, this.getTargetDescriptor());
        }
        this.buildExport(row, parentBean);
        return row;
    }
    
    public IntersectionRow buildManyToManyDeleteChildren(final Object parentBean) {
        final IntersectionRow row = new IntersectionRow(this.intersectionJoin.getTable());
        this.buildExport(row, parentBean);
        return row;
    }
    
    public IntersectionRow buildManyToManyMapBean(final Object parent, final Object other) {
        final IntersectionRow row = new IntersectionRow(this.intersectionJoin.getTable());
        this.buildExport(row, parent);
        this.buildImport(row, other);
        return row;
    }
    
    private void buildExport(final IntersectionRow row, Object parentBean) {
        if (this.embeddedExportedProperties) {
            final BeanProperty[] uids = this.descriptor.propertiesId();
            parentBean = uids[0].getValue(parentBean);
        }
        for (int i = 0; i < this.exportedProperties.length; ++i) {
            final Object val = this.exportedProperties[i].getValue(parentBean);
            final String fkColumn = this.exportedProperties[i].getForeignDbColumn();
            row.put(fkColumn, val);
        }
    }
    
    private void buildImport(final IntersectionRow row, final Object otherBean) {
        this.importedId.buildImport(row, otherBean);
    }
    
    public boolean hasImportedId(final Object otherBean) {
        return null != this.targetDescriptor.getId(otherBean);
    }
    
    public void jsonWrite(final WriteJsonContext ctx, final Object bean) {
        final Boolean include = ctx.includeMany(this.name);
        if (Boolean.FALSE.equals(include)) {
            return;
        }
        final Object value = this.getValueIntercept(bean);
        if (value != null) {
            ctx.pushParentBeanMany(bean);
            this.help.jsonWrite(ctx, this.name, value, include != null);
            ctx.popParentBeanMany();
        }
    }
    
    public void jsonRead(final ReadJsonContext ctx, final Object bean) {
        if (!ctx.readArrayBegin()) {
            return;
        }
        final Object collection = this.help.createEmpty(false);
        final BeanCollectionAdd add = this.getBeanCollectionAdd(collection, null);
        do {
            final ReadJsonContext.ReadBeanState detailBeanState = this.targetDescriptor.jsonRead(ctx, this.name);
            if (detailBeanState == null) {
                break;
            }
            final Object detailBean = detailBeanState.getBean();
            add.addBean(detailBean);
            if (bean != null && this.childMasterProperty != null) {
                this.childMasterProperty.setValue(detailBean, bean);
                detailBeanState.setLoaded(this.childMasterProperty.getName());
            }
            detailBeanState.setLoadedState();
        } while (ctx.readArrayNext());
        this.setValue(bean, collection);
    }
}
