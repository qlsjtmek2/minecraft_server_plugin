// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import com.avaje.ebeaninternal.server.deploy.id.ImportedIdEmbedded;
import com.avaje.ebeaninternal.server.deploy.id.ImportedIdMultiple;
import com.avaje.ebeaninternal.server.deploy.id.ImportedIdSimple;
import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
import java.util.LinkedHashMap;
import com.avaje.ebeaninternal.server.core.InternString;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import java.util.logging.Logger;

public abstract class BeanPropertyAssoc<T> extends BeanProperty
{
    private static final Logger logger;
    BeanDescriptor<T> targetDescriptor;
    IdBinder targetIdBinder;
    InheritInfo targetInheritInfo;
    String targetIdProperty;
    final BeanCascadeInfo cascadeInfo;
    final TableJoin tableJoin;
    final Class<T> targetType;
    final BeanTable beanTable;
    final String mappedBy;
    final boolean isOuterJoin;
    String extraWhere;
    boolean saveRecurseSkippable;
    boolean deleteRecurseSkippable;
    
    public BeanPropertyAssoc(final BeanDescriptorMap owner, final BeanDescriptor<?> descriptor, final DeployBeanPropertyAssoc<T> deploy) {
        super(owner, descriptor, deploy);
        this.extraWhere = InternString.intern(deploy.getExtraWhere());
        this.isOuterJoin = deploy.isOuterJoin();
        this.beanTable = deploy.getBeanTable();
        this.mappedBy = InternString.intern(deploy.getMappedBy());
        this.tableJoin = new TableJoin(deploy.getTableJoin(), null);
        this.targetType = deploy.getTargetType();
        this.cascadeInfo = deploy.getCascadeInfo();
    }
    
    public void initialise() {
        if (!this.isTransient) {
            this.targetDescriptor = this.descriptor.getBeanDescriptor(this.targetType);
            this.targetIdBinder = this.targetDescriptor.getIdBinder();
            this.targetInheritInfo = this.targetDescriptor.getInheritInfo();
            this.saveRecurseSkippable = this.targetDescriptor.isSaveRecurseSkippable();
            this.deleteRecurseSkippable = this.targetDescriptor.isDeleteRecurseSkippable();
            this.cascadeValidate = this.cascadeInfo.isValidate();
            if (!this.targetIdBinder.isComplexId()) {
                this.targetIdProperty = this.targetIdBinder.getIdProperty();
            }
        }
    }
    
    protected ElPropertyValue createElPropertyValue(final String propName, final String remainder, ElPropertyChainBuilder chain, final boolean propertyDeploy) {
        final BeanDescriptor<?> embDesc = this.getTargetDescriptor();
        if (chain == null) {
            chain = new ElPropertyChainBuilder(this.isEmbedded(), propName);
        }
        chain.add(this);
        if (this.containsMany()) {
            chain.setContainsMany(true);
        }
        return embDesc.buildElGetValue(remainder, chain, propertyDeploy);
    }
    
    public boolean addJoin(final boolean forceOuterJoin, final String prefix, final DbSqlContext ctx) {
        return this.tableJoin.addJoin(forceOuterJoin, prefix, ctx);
    }
    
    public boolean addJoin(final boolean forceOuterJoin, final String a1, final String a2, final DbSqlContext ctx) {
        return this.tableJoin.addJoin(forceOuterJoin, a1, a2, ctx);
    }
    
    public void addInnerJoin(final String a1, final String a2, final DbSqlContext ctx) {
        this.tableJoin.addInnerJoin(a1, a2, ctx);
    }
    
    public boolean isScalar() {
        return false;
    }
    
    public String getMappedBy() {
        return this.mappedBy;
    }
    
    public String getTargetIdProperty() {
        return this.targetIdProperty;
    }
    
    public BeanDescriptor<T> getTargetDescriptor() {
        return this.targetDescriptor;
    }
    
    public boolean isSaveRecurseSkippable(final Object bean) {
        return this.saveRecurseSkippable && bean instanceof EntityBean && !((EntityBean)bean)._ebean_getIntercept().isNewOrDirty();
    }
    
    public boolean isSaveRecurseSkippable() {
        return this.saveRecurseSkippable;
    }
    
    public boolean isDeleteRecurseSkippable() {
        return this.deleteRecurseSkippable;
    }
    
    public boolean hasId(final Object bean) {
        final BeanDescriptor<?> targetDesc = this.getTargetDescriptor();
        final BeanProperty[] uids = targetDesc.propertiesId();
        for (int i = 0; i < uids.length; ++i) {
            final Object value = uids[i].getValue(bean);
            if (value == null) {
                return false;
            }
        }
        return true;
    }
    
    public Class<?> getTargetType() {
        return this.targetType;
    }
    
    public String getExtraWhere() {
        return this.extraWhere;
    }
    
    public boolean isOuterJoin() {
        return this.isOuterJoin;
    }
    
    public boolean isUpdateable() {
        return this.tableJoin.columns().length <= 0 || this.tableJoin.columns()[0].isUpdateable();
    }
    
    public boolean isInsertable() {
        return this.tableJoin.columns().length <= 0 || this.tableJoin.columns()[0].isInsertable();
    }
    
    public TableJoin getTableJoin() {
        return this.tableJoin;
    }
    
    public BeanTable getBeanTable() {
        return this.beanTable;
    }
    
    public BeanCascadeInfo getCascadeInfo() {
        return this.cascadeInfo;
    }
    
    protected ImportedId createImportedId(final BeanPropertyAssoc<?> owner, final BeanDescriptor<?> target, final TableJoin join) {
        final BeanProperty[] props = target.propertiesId();
        final BeanProperty[] others = target.propertiesBaseScalar();
        if (this.descriptor.isSqlSelectBased()) {
            final String dbColumn = owner.getDbColumn();
            return new ImportedIdSimple(owner, dbColumn, props[0], 0);
        }
        final TableJoinColumn[] cols = join.columns();
        if (props.length != 1) {
            final ImportedIdSimple[] scalars = this.createImportedList(owner, cols, props, others);
            return new ImportedIdMultiple(owner, scalars);
        }
        if (props[0].isEmbedded()) {
            final BeanPropertyAssocOne<?> embProp = (BeanPropertyAssocOne<?>)props[0];
            final BeanProperty[] embBaseProps = embProp.getTargetDescriptor().propertiesBaseScalar();
            final ImportedIdSimple[] scalars2 = this.createImportedList(owner, cols, embBaseProps, others);
            return new ImportedIdEmbedded(owner, embProp, scalars2);
        }
        if (cols.length != 1) {
            final String msg = "No Imported Id column for [" + props[0] + "] in table [" + join.getTable() + "]";
            BeanPropertyAssoc.logger.log(Level.SEVERE, msg);
            return null;
        }
        return this.createImportedScalar(owner, cols[0], props, others);
    }
    
    private ImportedIdSimple[] createImportedList(final BeanPropertyAssoc<?> owner, final TableJoinColumn[] cols, final BeanProperty[] props, final BeanProperty[] others) {
        final ArrayList<ImportedIdSimple> list = new ArrayList<ImportedIdSimple>();
        for (int i = 0; i < cols.length; ++i) {
            list.add(this.createImportedScalar(owner, cols[i], props, others));
        }
        return ImportedIdSimple.sort(list);
    }
    
    private ImportedIdSimple createImportedScalar(final BeanPropertyAssoc<?> owner, final TableJoinColumn col, final BeanProperty[] props, final BeanProperty[] others) {
        final String matchColumn = col.getForeignDbColumn();
        final String localColumn = col.getLocalDbColumn();
        for (int j = 0; j < props.length; ++j) {
            if (props[j].getDbColumn().equalsIgnoreCase(matchColumn)) {
                return new ImportedIdSimple(owner, localColumn, props[j], j);
            }
        }
        for (int j = 0; j < others.length; ++j) {
            if (others[j].getDbColumn().equalsIgnoreCase(matchColumn)) {
                return new ImportedIdSimple(owner, localColumn, others[j], j + props.length);
            }
        }
        final String msg = "Error with the Join on [" + this.getFullBeanName() + "]. Could not find the local match for [" + matchColumn + "] " + " Perhaps an error in a @JoinColumn";
        throw new PersistenceException(msg);
    }
    
    static {
        logger = Logger.getLogger(BeanPropertyAssoc.class.getName());
    }
}
