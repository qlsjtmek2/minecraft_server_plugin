// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.Transaction;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import java.sql.SQLException;
import javax.persistence.OptimisticLockException;
import com.avaje.ebeaninternal.server.persist.BatchControl;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebean.InvalidValue;
import com.avaje.ebean.ValidationException;
import com.avaje.ebeaninternal.server.transaction.BeanDelta;
import com.avaje.ebeaninternal.server.transaction.BeanPersistIdMap;
import com.avaje.ebeaninternal.api.TransactionEvent;
import java.util.Collection;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebeaninternal.server.persist.PersistExecute;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import java.util.Set;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.event.BeanPersistController;
import com.avaje.ebean.event.BeanPersistListener;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanManager;
import com.avaje.ebean.event.BeanPersistRequest;

public class PersistRequestBean<T> extends PersistRequest implements BeanPersistRequest<T>
{
    protected final BeanManager<T> beanManager;
    protected final BeanDescriptor<T> beanDescriptor;
    protected final BeanPersistListener<T> beanPersistListener;
    protected final BeanPersistController controller;
    protected final EntityBeanIntercept intercept;
    protected final Object parentBean;
    protected final boolean isDirty;
    protected final boolean vanilla;
    protected final T bean;
    protected T oldValues;
    protected ConcurrencyMode concurrencyMode;
    protected final Set<String> loadedProps;
    protected Object idValue;
    protected Integer beanHash;
    protected Integer beanIdentityHash;
    protected final Set<String> changedProps;
    protected boolean notifyCache;
    private boolean statelessUpdate;
    private boolean deleteMissingChildren;
    private boolean updateNullProperties;
    
    public PersistRequestBean(final SpiEbeanServer server, final T bean, final Object parentBean, final BeanManager<T> mgr, final SpiTransaction t, final PersistExecute persistExecute, final Set<String> updateProps, final ConcurrencyMode concurrencyMode) {
        super(server, t, persistExecute);
        this.beanManager = mgr;
        this.beanDescriptor = mgr.getBeanDescriptor();
        this.beanPersistListener = this.beanDescriptor.getPersistListener();
        this.bean = bean;
        this.parentBean = parentBean;
        this.controller = this.beanDescriptor.getPersistController();
        this.concurrencyMode = this.beanDescriptor.getConcurrencyMode();
        this.concurrencyMode = concurrencyMode;
        this.loadedProps = updateProps;
        this.changedProps = updateProps;
        this.vanilla = true;
        this.isDirty = true;
        this.oldValues = bean;
        if (bean instanceof EntityBean) {
            this.intercept = ((EntityBean)bean)._ebean_getIntercept();
        }
        else {
            this.intercept = null;
        }
    }
    
    public PersistRequestBean(final SpiEbeanServer server, final T bean, final Object parentBean, final BeanManager<T> mgr, final SpiTransaction t, final PersistExecute persistExecute) {
        super(server, t, persistExecute);
        this.beanManager = mgr;
        this.beanDescriptor = mgr.getBeanDescriptor();
        this.beanPersistListener = this.beanDescriptor.getPersistListener();
        this.bean = bean;
        this.parentBean = parentBean;
        this.controller = this.beanDescriptor.getPersistController();
        this.concurrencyMode = this.beanDescriptor.getConcurrencyMode();
        if (bean instanceof EntityBean) {
            this.intercept = ((EntityBean)bean)._ebean_getIntercept();
            if (this.intercept.isReference()) {
                this.concurrencyMode = ConcurrencyMode.NONE;
            }
            if (!(this.isDirty = this.intercept.isDirty())) {
                this.changedProps = this.intercept.getChangedProps();
            }
            else {
                final Set<String> beanChangedProps = this.intercept.getChangedProps();
                final Set<String> dirtyEmbedded = this.beanDescriptor.getDirtyEmbeddedProperties(bean);
                this.changedProps = this.mergeChangedProperties(beanChangedProps, dirtyEmbedded);
            }
            this.loadedProps = this.intercept.getLoadedProps();
            this.oldValues = (T)this.intercept.getOldValues();
            this.vanilla = false;
        }
        else {
            this.vanilla = true;
            this.isDirty = true;
            this.loadedProps = null;
            this.changedProps = null;
            this.intercept = null;
            if (this.concurrencyMode.equals(ConcurrencyMode.ALL)) {
                this.concurrencyMode = ConcurrencyMode.NONE;
            }
        }
    }
    
    private Set<String> mergeChangedProperties(final Set<String> beanChangedProps, final Set<String> embChanged) {
        if (embChanged == null) {
            return beanChangedProps;
        }
        if (beanChangedProps == null) {
            return embChanged;
        }
        beanChangedProps.addAll(embChanged);
        return beanChangedProps;
    }
    
    public boolean isNotify(final TransactionEvent txnEvent) {
        return this.notifyCache || this.isNotifyPersistListener() || this.beanDescriptor.isNotifyLucene(txnEvent);
    }
    
    public boolean isNotifyCache() {
        return this.notifyCache;
    }
    
    public boolean isNotifyPersistListener() {
        return this.beanPersistListener != null;
    }
    
    public void notifyCache() {
        if (this.notifyCache) {
            if (this.type == Type.INSERT) {
                this.beanDescriptor.queryCacheClear();
            }
            else {
                this.beanDescriptor.cacheRemove(this.idValue);
            }
        }
    }
    
    public void pauseIndexInvalidate() {
        this.transaction.getEvent().pauseIndexInvalidate(this.beanDescriptor.getBeanType());
    }
    
    public void resumeIndexInvalidate() {
        this.transaction.getEvent().resumeIndexInvalidate(this.beanDescriptor.getBeanType());
    }
    
    public void addToPersistMap(final BeanPersistIdMap beanPersistMap) {
        beanPersistMap.add(this.beanDescriptor, this.type, this.idValue);
    }
    
    public boolean notifyLocalPersistListener() {
        if (this.beanPersistListener == null) {
            return false;
        }
        switch (this.type) {
            case INSERT: {
                return this.beanPersistListener.inserted(this.bean);
            }
            case UPDATE: {
                return this.beanPersistListener.updated(this.bean, this.getUpdatedProperties());
            }
            case DELETE: {
                return this.beanPersistListener.deleted(this.bean);
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean isParent(final Object o) {
        return o == this.parentBean;
    }
    
    private Integer getBeanHash() {
        if (this.beanHash == null) {
            final Object id = this.beanDescriptor.getId(this.bean);
            int hc = 31 * this.bean.getClass().getName().hashCode();
            if (id != null) {
                hc += id.hashCode();
            }
            this.beanHash = hc;
        }
        return this.beanHash;
    }
    
    private Integer getBeanIdentityHash() {
        if (this.beanIdentityHash == null) {
            this.beanIdentityHash = System.identityHashCode(this.bean);
        }
        return this.beanIdentityHash;
    }
    
    public void registerVanillaBean() {
        if (this.intercept == null) {
            this.transaction.registerBean(this.getBeanIdentityHash());
        }
    }
    
    public boolean isRegisteredVanillaBean() {
        return this.intercept == null && this.transaction != null && this.transaction.isRegisteredBean(this.getBeanIdentityHash());
    }
    
    public void registerBean() {
        this.transaction.registerBean(this.getBeanHash());
    }
    
    public void unregisterBean() {
        this.transaction.unregisterBean(this.getBeanHash());
    }
    
    public boolean isRegistered() {
        return this.transaction != null && this.transaction.isRegisteredBean(this.getBeanHash());
    }
    
    public void setType(final Type type) {
        this.type = type;
        if (type == Type.DELETE || type == Type.UPDATE) {
            if (this.oldValues == null) {
                this.oldValues = this.bean;
            }
            this.notifyCache = this.beanDescriptor.isCacheNotify(false);
        }
        else {
            this.notifyCache = this.beanDescriptor.isCacheNotify(true);
        }
    }
    
    public BeanManager<T> getBeanManager() {
        return this.beanManager;
    }
    
    public BeanDescriptor<T> getBeanDescriptor() {
        return this.beanDescriptor;
    }
    
    public boolean isStatelessUpdate() {
        return this.statelessUpdate;
    }
    
    public boolean isDeleteMissingChildren() {
        return this.deleteMissingChildren;
    }
    
    public boolean isUpdateNullProperties() {
        return this.updateNullProperties;
    }
    
    public void setStatelessUpdate(final boolean statelessUpdate, final boolean deleteMissingChildren, final boolean updateNullProperties) {
        this.statelessUpdate = statelessUpdate;
        this.deleteMissingChildren = deleteMissingChildren;
        this.updateNullProperties = updateNullProperties;
    }
    
    public boolean isDirty() {
        return this.isDirty;
    }
    
    public ConcurrencyMode getConcurrencyMode() {
        return this.concurrencyMode;
    }
    
    public void setLoadedProps(final Set<String> additionalProps) {
        if (this.intercept != null) {
            this.intercept.setLoadedProps(additionalProps);
        }
    }
    
    public Set<String> getLoadedProperties() {
        return this.loadedProps;
    }
    
    public String getFullName() {
        return this.beanDescriptor.getFullName();
    }
    
    public T getBean() {
        return this.bean;
    }
    
    public Object getBeanId() {
        return this.beanDescriptor.getId(this.bean);
    }
    
    public BeanDelta createDeltaBean() {
        return new BeanDelta(this.beanDescriptor, this.getBeanId());
    }
    
    public T getOldValues() {
        return this.oldValues;
    }
    
    public Object getParentBean() {
        return this.parentBean;
    }
    
    public BeanPersistController getBeanController() {
        return this.controller;
    }
    
    public EntityBeanIntercept getEntityBeanIntercept() {
        return this.intercept;
    }
    
    public void validate() {
        final InvalidValue errs = this.beanDescriptor.validate(false, this.bean);
        if (errs != null) {
            throw new ValidationException(errs);
        }
    }
    
    public boolean isLoadedProperty(final BeanProperty prop) {
        return this.loadedProps == null || this.loadedProps.contains(prop.getName());
    }
    
    public int executeNow() {
        switch (this.type) {
            case INSERT: {
                this.persistExecute.executeInsertBean((PersistRequestBean<Object>)this);
                return -1;
            }
            case UPDATE: {
                this.persistExecute.executeUpdateBean((PersistRequestBean<Object>)this);
                return -1;
            }
            case DELETE: {
                this.persistExecute.executeDeleteBean((PersistRequestBean<Object>)this);
                return -1;
            }
            default: {
                throw new RuntimeException("Invalid type " + this.type);
            }
        }
    }
    
    public int executeOrQueue() {
        final boolean batch = this.transaction.isBatchThisRequest();
        BatchControl control = this.transaction.getBatchControl();
        if (control != null) {
            return control.executeOrQueue(this, batch);
        }
        if (batch) {
            control = this.persistExecute.createBatchControl(this.transaction);
            return control.executeOrQueue(this, batch);
        }
        return this.executeNow();
    }
    
    public void setGeneratedKey(Object idValue) {
        if (idValue != null) {
            idValue = this.beanDescriptor.convertSetId(idValue, this.bean);
            this.idValue = idValue;
        }
    }
    
    public void setBoundId(final Object idValue) {
        this.idValue = idValue;
    }
    
    public final void checkRowCount(final int rowCount) throws SQLException {
        if (rowCount != 1) {
            final String m = Message.msg("persist.conc2", "" + rowCount);
            throw new OptimisticLockException(m, null, this.bean);
        }
    }
    
    public void postExecute() throws SQLException {
        if (this.controller != null) {
            this.controllerPost();
        }
        if (this.intercept != null) {
            this.intercept.setLoaded();
        }
        this.addEvent();
        if (this.isLogSummary()) {
            this.logSummary();
        }
    }
    
    private void controllerPost() {
        switch (this.type) {
            case INSERT: {
                this.controller.postInsert(this);
                break;
            }
            case UPDATE: {
                this.controller.postUpdate(this);
                break;
            }
            case DELETE: {
                this.controller.postDelete(this);
                break;
            }
        }
    }
    
    private void logSummary() {
        final String name = this.beanDescriptor.getName();
        switch (this.type) {
            case INSERT: {
                this.transaction.logInternal("Inserted [" + name + "] [" + this.idValue + "]");
                break;
            }
            case UPDATE: {
                this.transaction.logInternal("Updated [" + name + "] [" + this.idValue + "]");
                break;
            }
            case DELETE: {
                this.transaction.logInternal("Deleted [" + name + "] [" + this.idValue + "]");
                break;
            }
        }
    }
    
    private void addEvent() {
        final TransactionEvent event = this.transaction.getEvent();
        if (event != null) {
            event.add(this);
        }
    }
    
    public ConcurrencyMode determineConcurrencyMode() {
        if (this.loadedProps != null && this.concurrencyMode.equals(ConcurrencyMode.VERSION)) {
            final BeanProperty prop = this.beanDescriptor.firstVersionProperty();
            if (prop == null || !this.loadedProps.contains(prop.getName())) {
                this.concurrencyMode = ConcurrencyMode.ALL;
            }
        }
        return this.concurrencyMode;
    }
    
    public boolean isDynamicUpdateSql() {
        return (!this.vanilla && this.beanDescriptor.isUpdateChangesOnly()) || this.loadedProps != null;
    }
    
    public GenerateDmlRequest createGenerateDmlRequest(final boolean emptyStringAsNull) {
        if (this.beanDescriptor.isUpdateChangesOnly()) {
            return new GenerateDmlRequest(emptyStringAsNull, this.changedProps, this.loadedProps, this.oldValues);
        }
        return new GenerateDmlRequest(emptyStringAsNull, this.loadedProps, this.loadedProps, this.oldValues);
    }
    
    public Set<String> getUpdatedProperties() {
        if (this.changedProps != null) {
            return this.changedProps;
        }
        return this.loadedProps;
    }
    
    public boolean hasChanged(final BeanProperty prop) {
        return this.changedProps.contains(prop.getName());
    }
}
