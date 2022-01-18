// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.server.deploy.IntersectionRow;
import com.avaje.ebean.EbeanServer;
import java.util.Map;
import com.avaje.ebeaninternal.server.deploy.ManyType;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebean.Query;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import com.avaje.ebeaninternal.server.core.PersistRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.ldap.LdapPersistBeanRequest;
import java.util.Collection;
import java.util.HashSet;
import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebeaninternal.server.core.Message;
import java.util.Set;
import com.avaje.ebeaninternal.server.core.PersistRequestUpdateSql;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebeaninternal.server.deploy.BeanManager;
import com.avaje.ebeaninternal.server.core.PersistRequestOrmUpdate;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.api.SpiUpdate;
import com.avaje.ebean.Update;
import com.avaje.ebeaninternal.server.core.PersistRequestCallableSql;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.CallableSql;
import com.avaje.ebean.config.ldap.LdapContextFactory;
import com.avaje.ebeaninternal.server.core.PstmtBatch;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ldap.DefaultLdapPersister;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.server.core.Persister;

public final class DefaultPersister implements Persister
{
    private static final Logger logger;
    private final PersistExecute persistExecute;
    private final DefaultLdapPersister ldapPersister;
    private final SpiEbeanServer server;
    private final BeanDescriptorManager beanDescriptorManager;
    private final boolean defaultUpdateNullProperties;
    private final boolean defaultDeleteMissingChildren;
    
    public DefaultPersister(final SpiEbeanServer server, final boolean validate, final Binder binder, final BeanDescriptorManager descMgr, final PstmtBatch pstmtBatch, final LdapContextFactory contextFactory) {
        this.server = server;
        this.beanDescriptorManager = descMgr;
        this.persistExecute = new DefaultPersistExecute(validate, binder, pstmtBatch);
        this.ldapPersister = new DefaultLdapPersister(contextFactory);
        this.defaultUpdateNullProperties = server.isDefaultUpdateNullProperties();
        this.defaultDeleteMissingChildren = server.isDefaultDeleteMissingChildren();
    }
    
    public int executeCallable(final CallableSql callSql, final Transaction t) {
        final PersistRequestCallableSql request = new PersistRequestCallableSql(this.server, callSql, (SpiTransaction)t, this.persistExecute);
        try {
            request.initTransIfRequired();
            final int rc = request.executeOrQueue();
            request.commitTransIfRequired();
            return rc;
        }
        catch (RuntimeException e) {
            request.rollbackTransIfRequired();
            throw e;
        }
    }
    
    public int executeOrmUpdate(final Update<?> update, final Transaction t) {
        final SpiUpdate<?> ormUpdate = (SpiUpdate<?>)(SpiUpdate)update;
        final BeanManager<?> mgr = this.beanDescriptorManager.getBeanManager(ormUpdate.getBeanType());
        if (mgr == null) {
            final String msg = "No BeanManager found for type [" + ormUpdate.getBeanType() + "]. Is it an entity?";
            throw new PersistenceException(msg);
        }
        final PersistRequestOrmUpdate request = new PersistRequestOrmUpdate(this.server, mgr, ormUpdate, (SpiTransaction)t, this.persistExecute);
        try {
            request.initTransIfRequired();
            final int rc = request.executeOrQueue();
            request.commitTransIfRequired();
            return rc;
        }
        catch (RuntimeException e) {
            request.rollbackTransIfRequired();
            throw e;
        }
    }
    
    public int executeSqlUpdate(final SqlUpdate updSql, final Transaction t) {
        final PersistRequestUpdateSql request = new PersistRequestUpdateSql(this.server, updSql, (SpiTransaction)t, this.persistExecute);
        try {
            request.initTransIfRequired();
            final int rc = request.executeOrQueue();
            request.commitTransIfRequired();
            return rc;
        }
        catch (RuntimeException e) {
            request.rollbackTransIfRequired();
            throw e;
        }
    }
    
    private void deleteRecurse(final Object detailBean, final Transaction t) {
        this.server.delete(detailBean, t);
    }
    
    public void forceUpdate(final Object bean, Set<String> updateProps, final Transaction t, final boolean deleteMissingChildren, final boolean updateNullProperties) {
        if (bean == null) {
            throw new NullPointerException(Message.msg("bean.isnull"));
        }
        if (updateProps == null && bean instanceof EntityBean) {
            final EntityBeanIntercept ebi = ((EntityBean)bean)._ebean_getIntercept();
            if (ebi.isDirty() || ebi.isLoaded()) {
                final PersistRequestBean<?> req = this.createRequest(bean, t, null);
                try {
                    req.initTransIfRequired();
                    this.update(req);
                    req.commitTransIfRequired();
                    return;
                }
                catch (RuntimeException ex) {
                    req.rollbackTransIfRequired();
                    throw ex;
                }
            }
            if (ebi.isReference()) {
                return;
            }
            updateProps = ebi.getLoadedProps();
        }
        final BeanManager<?> mgr = this.getBeanManager(bean);
        if (mgr == null) {
            throw new PersistenceException(this.errNotRegistered(bean.getClass()));
        }
        this.forceUpdateStateless(bean, t, null, mgr, updateProps, deleteMissingChildren, updateNullProperties);
    }
    
    private void forceUpdateStateless(final Object bean, final Transaction t, final Object parentBean, final BeanManager<?> mgr, Set<String> updateProps, final boolean deleteMissingChildren, final boolean updateNullProperties) {
        final BeanDescriptor<?> descriptor = mgr.getBeanDescriptor();
        final ConcurrencyMode mode = descriptor.determineConcurrencyMode(bean);
        if (updateProps == null) {
            updateProps = (updateNullProperties ? null : descriptor.determineLoadedProperties(bean));
        }
        else if (updateProps.isEmpty()) {
            updateProps = null;
        }
        else if (ConcurrencyMode.VERSION.equals(mode)) {
            final String verName = descriptor.firstVersionProperty().getName();
            if (!updateProps.contains(verName)) {
                updateProps = new HashSet<String>(updateProps);
                updateProps.add(verName);
            }
        }
        PersistRequestBean<?> req;
        if (descriptor.isLdapEntityType()) {
            req = new LdapPersistBeanRequest<Object>(this.server, bean, parentBean, mgr, this.ldapPersister, updateProps, mode);
        }
        else {
            req = new PersistRequestBean<Object>(this.server, bean, parentBean, mgr, (SpiTransaction)t, this.persistExecute, updateProps, mode);
            req.setStatelessUpdate(true, deleteMissingChildren, updateNullProperties);
        }
        try {
            req.initTransIfRequired();
            this.update(req);
            req.commitTransIfRequired();
        }
        catch (RuntimeException ex) {
            req.rollbackTransIfRequired();
            throw ex;
        }
    }
    
    public void save(final Object bean, final Transaction t) {
        this.saveRecurse(bean, t, null);
    }
    
    public void forceInsert(final Object bean, final Transaction t) {
        final PersistRequestBean<?> req = this.createRequest(bean, t, null);
        try {
            req.initTransIfRequired();
            this.insert(req);
            req.commitTransIfRequired();
        }
        catch (RuntimeException ex) {
            req.rollbackTransIfRequired();
            throw ex;
        }
    }
    
    private void saveRecurse(final Object bean, final Transaction t, final Object parentBean) {
        if (bean == null) {
            throw new NullPointerException(Message.msg("bean.isnull"));
        }
        if (!(bean instanceof EntityBean)) {
            this.saveVanillaRecurse(bean, t, parentBean);
            return;
        }
        final PersistRequestBean<?> req = this.createRequest(bean, t, parentBean);
        try {
            req.initTransIfRequired();
            this.saveEnhanced(req);
            req.commitTransIfRequired();
        }
        catch (RuntimeException ex) {
            req.rollbackTransIfRequired();
            throw ex;
        }
    }
    
    private void saveEnhanced(final PersistRequestBean<?> request) {
        final EntityBeanIntercept intercept = request.getEntityBeanIntercept();
        if (intercept.isReference()) {
            if (request.isPersistCascade()) {
                intercept.setLoaded();
                this.saveAssocMany(false, request);
                intercept.setReference();
            }
        }
        else if (intercept.isLoaded()) {
            this.update(request);
        }
        else {
            this.insert(request);
        }
    }
    
    private void saveVanillaRecurse(final Object bean, final Transaction t, final Object parentBean) {
        final BeanManager<?> mgr = this.getBeanManager(bean);
        if (mgr == null) {
            throw new RuntimeException("No Mgr found for " + bean + " " + bean.getClass());
        }
        if (mgr.getBeanDescriptor().isVanillaInsert(bean)) {
            this.saveVanillaInsert(bean, t, parentBean, mgr);
        }
        else {
            this.forceUpdateStateless(bean, t, parentBean, mgr, null, this.defaultDeleteMissingChildren, this.defaultUpdateNullProperties);
        }
    }
    
    private void saveVanillaInsert(final Object bean, final Transaction t, final Object parentBean, final BeanManager<?> mgr) {
        final PersistRequestBean<?> req = this.createRequest(bean, t, parentBean, mgr);
        try {
            req.initTransIfRequired();
            this.insert(req);
            req.commitTransIfRequired();
        }
        catch (RuntimeException ex) {
            req.rollbackTransIfRequired();
            throw ex;
        }
    }
    
    private void insert(final PersistRequestBean<?> request) {
        if (request.isRegisteredVanillaBean()) {
            return;
        }
        request.setType(PersistRequest.Type.INSERT);
        if (request.isPersistCascade()) {
            this.saveAssocOne(request);
        }
        this.setIdGenValue(request);
        request.executeOrQueue();
        request.registerVanillaBean();
        if (request.isPersistCascade()) {
            this.saveAssocMany(true, request);
        }
    }
    
    private void update(final PersistRequestBean<?> request) {
        if (request.isRegisteredVanillaBean()) {
            return;
        }
        request.setType(PersistRequest.Type.UPDATE);
        if (request.isPersistCascade()) {
            this.saveAssocOne(request);
        }
        if (request.isDirty()) {
            request.executeOrQueue();
            request.registerVanillaBean();
        }
        else if (DefaultPersister.logger.isLoggable(Level.FINE)) {
            DefaultPersister.logger.fine(Message.msg("persist.update.skipped", request.getBean()));
        }
        if (request.isPersistCascade()) {
            this.saveAssocMany(false, request);
        }
    }
    
    public void delete(final Object bean, final Transaction t) {
        final PersistRequestBean<?> req = this.createRequest(bean, t, null);
        if (req.isRegistered()) {
            if (DefaultPersister.logger.isLoggable(Level.FINE)) {
                DefaultPersister.logger.fine("skipping delete on alreadyRegistered " + bean);
            }
            return;
        }
        req.setType(PersistRequest.Type.DELETE);
        try {
            req.initTransIfRequired();
            this.delete(req);
            req.commitTransIfRequired();
        }
        catch (RuntimeException ex) {
            req.rollbackTransIfRequired();
            throw ex;
        }
    }
    
    private void deleteList(final List<?> beanList, final Transaction t) {
        for (int i = 0; i < beanList.size(); ++i) {
            final Object bean = beanList.get(i);
            this.delete(bean, t);
        }
    }
    
    public void deleteMany(final Class<?> beanType, final Collection<?> ids, final Transaction transaction) {
        if (ids == null || ids.size() == 0) {
            return;
        }
        final BeanDescriptor<?> descriptor = this.beanDescriptorManager.getBeanDescriptor(beanType);
        final ArrayList<Object> idList = new ArrayList<Object>(ids.size());
        for (final Object id : ids) {
            idList.add(descriptor.convertId(id));
        }
        this.delete(descriptor, null, idList, transaction);
    }
    
    public int delete(final Class<?> beanType, Object id, final Transaction transaction) {
        final BeanDescriptor<?> descriptor = this.beanDescriptorManager.getBeanDescriptor(beanType);
        id = descriptor.convertId(id);
        return this.delete(descriptor, id, null, transaction);
    }
    
    private int delete(final BeanDescriptor<?> descriptor, final Object id, final List<Object> idList, final Transaction transaction) {
        final SpiTransaction t = (SpiTransaction)transaction;
        if (t.isPersistCascade()) {
            final BeanPropertyAssocOne<?>[] propImportDelete = descriptor.propertiesOneImportedDelete();
            if (propImportDelete.length > 0) {
                final Query<?> q = this.deleteRequiresQuery(descriptor, propImportDelete);
                if (idList != null) {
                    q.where().idIn(idList);
                    if (t.isLogSummary()) {
                        t.logInternal("-- DeleteById of " + descriptor.getName() + " ids[" + idList + "] requires fetch of foreign key values");
                    }
                    final List<?> beanList = this.server.findList(q, t);
                    this.deleteList(beanList, t);
                    return beanList.size();
                }
                q.where().idEq(id);
                if (t.isLogSummary()) {
                    t.logInternal("-- DeleteById of " + descriptor.getName() + " id[" + id + "] requires fetch of foreign key values");
                }
                final Object bean = this.server.findUnique(q, t);
                if (bean == null) {
                    return 0;
                }
                this.delete(bean, t);
                return 1;
            }
        }
        if (t.isPersistCascade()) {
            final BeanPropertyAssocOne<?>[] expOnes = descriptor.propertiesOneExportedDelete();
            for (int i = 0; i < expOnes.length; ++i) {
                final BeanDescriptor<?> targetDesc = expOnes[i].getTargetDescriptor();
                if (targetDesc.isDeleteRecurseSkippable() && !targetDesc.isUsingL2Cache()) {
                    final SqlUpdate sqlDelete = expOnes[i].deleteByParentId(id, idList);
                    this.executeSqlUpdate(sqlDelete, t);
                }
                else {
                    final List<Object> childIds = expOnes[i].findIdsByParentId(id, idList, t);
                    this.delete(targetDesc, null, childIds, t);
                }
            }
            final BeanPropertyAssocMany<?>[] manys = descriptor.propertiesManyDelete();
            for (int j = 0; j < manys.length; ++j) {
                final BeanDescriptor<?> targetDesc2 = manys[j].getTargetDescriptor();
                if (targetDesc2.isDeleteRecurseSkippable() && !targetDesc2.isUsingL2Cache()) {
                    final SqlUpdate sqlDelete2 = manys[j].deleteByParentId(id, idList);
                    this.executeSqlUpdate(sqlDelete2, t);
                }
                else {
                    final List<Object> childIds2 = manys[j].findIdsByParentId(id, idList, t, null);
                    this.delete(targetDesc2, null, childIds2, t);
                }
            }
        }
        final BeanPropertyAssocMany<?>[] manys2 = descriptor.propertiesManyToMany();
        for (int i = 0; i < manys2.length; ++i) {
            final SqlUpdate sqlDelete3 = manys2[i].deleteByParentId(id, idList);
            if (t.isLogSummary()) {
                t.logInternal("-- Deleting intersection table entries: " + manys2[i].getFullBeanName());
            }
            this.executeSqlUpdate(sqlDelete3, t);
        }
        final SqlUpdate deleteById = descriptor.deleteById(id, idList);
        if (t.isLogSummary()) {
            t.logInternal("-- Deleting " + descriptor.getName() + " Ids" + idList);
        }
        deleteById.setAutoTableMod(false);
        if (idList != null) {
            t.getEvent().addDeleteByIdList(descriptor, idList);
        }
        else {
            t.getEvent().addDeleteById(descriptor, id);
        }
        return this.executeSqlUpdate(deleteById, t);
    }
    
    private Query<?> deleteRequiresQuery(final BeanDescriptor<?> desc, final BeanPropertyAssocOne<?>[] propImportDelete) {
        final Query<?> q = this.server.createQuery(desc.getBeanType());
        final StringBuilder sb = new StringBuilder(30);
        for (int i = 0; i < propImportDelete.length; ++i) {
            sb.append(propImportDelete[i].getName()).append(",");
        }
        q.setAutofetch(false);
        q.select(sb.toString());
        return q;
    }
    
    private void delete(final PersistRequestBean<?> request) {
        DeleteUnloadedForeignKeys unloadedForeignKeys = null;
        final boolean pauseIndexInvalidate = request.getBeanDescriptor().isLuceneIndexed();
        if (pauseIndexInvalidate) {
            request.pauseIndexInvalidate();
        }
        if (request.isPersistCascade()) {
            request.registerBean();
            this.deleteAssocMany(request);
            request.unregisterBean();
            unloadedForeignKeys = this.getDeleteUnloadedForeignKeys(request);
            if (unloadedForeignKeys != null) {
                unloadedForeignKeys.queryForeignKeys();
            }
        }
        request.executeOrQueue();
        if (request.isPersistCascade()) {
            this.deleteAssocOne(request);
            if (unloadedForeignKeys != null) {
                unloadedForeignKeys.deleteCascade();
            }
        }
        if (pauseIndexInvalidate) {
            request.resumeIndexInvalidate();
        }
    }
    
    private void saveAssocMany(final boolean insertedParent, final PersistRequestBean<?> request) {
        final Object parentBean = request.getBean();
        final BeanDescriptor<?> desc = request.getBeanDescriptor();
        final SpiTransaction t = request.getTransaction();
        final BeanPropertyAssocOne<?>[] expOnes = desc.propertiesOneExportedSave();
        for (int i = 0; i < expOnes.length; ++i) {
            final BeanPropertyAssocOne<?> prop = expOnes[i];
            if (request.isLoadedProperty(prop)) {
                final Object detailBean = prop.getValue(parentBean);
                if (detailBean != null) {
                    if (!prop.isSaveRecurseSkippable(detailBean)) {
                        t.depth(1);
                        this.saveRecurse(detailBean, t, parentBean);
                        t.depth(-1);
                    }
                }
            }
        }
        final BeanPropertyAssocMany<?>[] manys = desc.propertiesManySave();
        for (int j = 0; j < manys.length; ++j) {
            this.saveMany(new SaveManyPropRequest(insertedParent, (BeanPropertyAssocMany)manys[j], parentBean, (PersistRequestBean)request));
        }
    }
    
    private void saveMany(final SaveManyPropRequest saveMany) {
        if (saveMany.getMany().isManyToMany()) {
            if (saveMany.isCascade()) {
                this.saveAssocManyDetails(saveMany, false, saveMany.isUpdateNullProperties());
                this.saveAssocManyIntersection(saveMany, saveMany.isDeleteMissingChildren());
            }
        }
        else {
            if (saveMany.isCascade()) {
                this.saveAssocManyDetails(saveMany, saveMany.isDeleteMissingChildren(), saveMany.isUpdateNullProperties());
            }
            if (saveMany.isModifyListenMode()) {
                this.removeAssocManyPrivateOwned(saveMany);
            }
        }
    }
    
    private void removeAssocManyPrivateOwned(final SaveManyPropRequest saveMany) {
        final Object details = saveMany.getValueUnderlying();
        if (details instanceof BeanCollection) {
            final BeanCollection<?> c = (BeanCollection<?>)details;
            final Set<?> modifyRemovals = c.getModifyRemovals();
            if (modifyRemovals != null && !modifyRemovals.isEmpty()) {
                final SpiTransaction t = saveMany.getTransaction();
                t.depth(1);
                for (final Object removedBean : modifyRemovals) {
                    this.deleteRecurse(removedBean, t);
                }
                t.depth(-1);
            }
        }
    }
    
    private void saveAssocManyDetails(final SaveManyPropRequest saveMany, final boolean deleteMissingChildren, final boolean updateNullProperties) {
        final BeanPropertyAssocMany<?> prop = saveMany.getMany();
        final Object details = saveMany.getValueUnderlying();
        final Collection<?> collection = this.getDetailsIterator(details);
        if (collection == null) {
            return;
        }
        if (saveMany.isInsertedParent()) {
            prop.getTargetDescriptor().preAllocateIds(collection.size());
        }
        final BeanDescriptor<?> targetDescriptor = prop.getTargetDescriptor();
        ArrayList<Object> detailIds = null;
        if (deleteMissingChildren) {
            detailIds = new ArrayList<Object>();
        }
        final SpiTransaction t = saveMany.getTransaction();
        t.depth(1);
        final boolean isMap = ManyType.JAVA_MAP.equals(prop.getManyType());
        final Object parentBean = saveMany.getParentBean();
        Object mapKeyValue = null;
        final boolean saveSkippable = prop.isSaveRecurseSkippable();
        boolean skipSavingThisBean = false;
        for (Object detailBean : collection) {
            if (isMap) {
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)detailBean;
                mapKeyValue = entry.getKey();
                detailBean = entry.getValue();
            }
            if (prop.isManyToMany()) {
                if (detailBean instanceof EntityBean) {
                    skipSavingThisBean = ((EntityBean)detailBean)._ebean_getIntercept().isReference();
                }
            }
            else if (detailBean instanceof EntityBean) {
                final EntityBeanIntercept ebi = ((EntityBean)detailBean)._ebean_getIntercept();
                if (ebi.isNewOrDirty()) {
                    prop.setJoinValuesToChild(parentBean, detailBean, mapKeyValue);
                }
                else {
                    skipSavingThisBean = (ebi.isReference() || saveSkippable);
                }
            }
            else {
                prop.setJoinValuesToChild(parentBean, detailBean, mapKeyValue);
            }
            if (skipSavingThisBean) {
                skipSavingThisBean = false;
            }
            else if (!saveMany.isStatelessUpdate()) {
                this.saveRecurse(detailBean, t, parentBean);
            }
            else if (targetDescriptor.isStatelessUpdate(detailBean)) {
                this.forceUpdate(detailBean, null, t, deleteMissingChildren, updateNullProperties);
            }
            else {
                this.forceInsert(detailBean, t);
            }
            if (detailIds != null) {
                final Object id = targetDescriptor.getId(detailBean);
                if (DmlUtil.isNullOrZero(id)) {
                    continue;
                }
                detailIds.add(id);
            }
        }
        if (detailIds != null) {
            this.deleteManyDetails(t, prop.getBeanDescriptor(), parentBean, prop, detailIds);
        }
        t.depth(-1);
    }
    
    public int deleteManyToManyAssociations(final Object ownerBean, final String propertyName, final Transaction t) {
        final BeanDescriptor<?> descriptor = this.beanDescriptorManager.getBeanDescriptor(ownerBean.getClass());
        final BeanPropertyAssocMany<?> prop = (BeanPropertyAssocMany<?>)descriptor.getBeanProperty(propertyName);
        return this.deleteAssocManyIntersection(ownerBean, prop, t);
    }
    
    public void saveManyToManyAssociations(final Object ownerBean, final String propertyName, final Transaction t) {
        final BeanDescriptor<?> descriptor = this.beanDescriptorManager.getBeanDescriptor(ownerBean.getClass());
        final BeanPropertyAssocMany<?> prop = (BeanPropertyAssocMany<?>)descriptor.getBeanProperty(propertyName);
        this.saveAssocManyIntersection(new SaveManyPropRequest((BeanPropertyAssocMany)prop, ownerBean, (SpiTransaction)t), false);
    }
    
    public void saveAssociation(final Object parentBean, final String propertyName, final Transaction t) {
        final BeanDescriptor<?> descriptor = this.beanDescriptorManager.getBeanDescriptor(parentBean.getClass());
        final SpiTransaction trans = (SpiTransaction)t;
        final BeanProperty prop = descriptor.getBeanProperty(propertyName);
        if (prop == null) {
            final String msg = "Could not find property [" + propertyName + "] on bean " + parentBean.getClass();
            throw new PersistenceException(msg);
        }
        if (prop instanceof BeanPropertyAssocMany) {
            final BeanPropertyAssocMany<?> manyProp = (BeanPropertyAssocMany<?>)prop;
            this.saveMany(new SaveManyPropRequest((BeanPropertyAssocMany)manyProp, parentBean, (SpiTransaction)t));
        }
        else {
            if (!(prop instanceof BeanPropertyAssocOne)) {
                final String msg = "Expecting [" + prop.getFullBeanName() + "] to be a OneToMany, OneToOne, ManyToOne or ManyToMany property?";
                throw new PersistenceException(msg);
            }
            final BeanPropertyAssocOne<?> oneProp = (BeanPropertyAssocOne<?>)prop;
            final Object assocBean = oneProp.getValue(parentBean);
            final int depth = oneProp.isOneToOneExported() ? 1 : -1;
            final int revertDepth = -1 * depth;
            trans.depth(depth);
            this.saveRecurse(assocBean, t, parentBean);
            trans.depth(revertDepth);
        }
    }
    
    private void saveAssocManyIntersection(final SaveManyPropRequest saveManyPropRequest, final boolean deleteMissingChildren) {
        final BeanPropertyAssocMany<?> prop = saveManyPropRequest.getMany();
        final Object value = prop.getValueUnderlying(saveManyPropRequest.getParentBean());
        if (value == null) {
            return;
        }
        final SpiTransaction t = saveManyPropRequest.getTransaction();
        Collection<?> additions = null;
        Collection<?> deletions = null;
        final boolean vanillaCollection = !(value instanceof BeanCollection);
        if (deleteMissingChildren) {
            this.deleteAssocManyIntersection(saveManyPropRequest.getParentBean(), prop, t);
        }
        if (saveManyPropRequest.isInsertedParent() || vanillaCollection || deleteMissingChildren) {
            if (value instanceof Map) {
                additions = ((Map)value).values();
            }
            else {
                if (!(value instanceof Collection)) {
                    final String msg = "Unhandled ManyToMany type " + value.getClass().getName() + " for " + prop.getFullBeanName();
                    throw new PersistenceException(msg);
                }
                additions = (Collection<?>)value;
            }
            if (!vanillaCollection) {
                ((BeanCollection)value).modifyReset();
            }
        }
        else {
            final BeanCollection<?> manyValue = (BeanCollection<?>)value;
            additions = manyValue.getModifyAdditions();
            deletions = manyValue.getModifyRemovals();
            manyValue.modifyReset();
        }
        t.depth(1);
        if (additions != null && !additions.isEmpty()) {
            for (final Object otherBean : additions) {
                if (deletions != null && deletions.remove(otherBean)) {
                    final String m = "Inserting and Deleting same object? " + otherBean;
                    if (t.isLogSummary()) {
                        t.logInternal(m);
                    }
                    DefaultPersister.logger.log(Level.WARNING, m);
                }
                else {
                    if (!prop.hasImportedId(otherBean)) {
                        final String msg2 = "ManyToMany bean " + otherBean + " does not have an Id value.";
                        throw new PersistenceException(msg2);
                    }
                    final IntersectionRow intRow = prop.buildManyToManyMapBean(saveManyPropRequest.getParentBean(), otherBean);
                    final SqlUpdate sqlInsert = intRow.createInsert(this.server);
                    this.executeSqlUpdate(sqlInsert, t);
                }
            }
        }
        if (deletions != null && !deletions.isEmpty()) {
            for (final Object otherDelete : deletions) {
                final IntersectionRow intRow = prop.buildManyToManyMapBean(saveManyPropRequest.getParentBean(), otherDelete);
                final SqlUpdate sqlDelete = intRow.createDelete(this.server);
                this.executeSqlUpdate(sqlDelete, t);
            }
        }
        t.depth(-1);
    }
    
    private int deleteAssocManyIntersection(final Object bean, final BeanPropertyAssocMany<?> many, final Transaction t) {
        final IntersectionRow intRow = many.buildManyToManyDeleteChildren(bean);
        final SqlUpdate sqlDelete = intRow.createDeleteChildren(this.server);
        return this.executeSqlUpdate(sqlDelete, t);
    }
    
    private void deleteAssocMany(final PersistRequestBean<?> request) {
        final SpiTransaction t = request.getTransaction();
        t.depth(-1);
        final BeanDescriptor<?> desc = request.getBeanDescriptor();
        final Object parentBean = request.getBean();
        final BeanPropertyAssocOne<?>[] expOnes = desc.propertiesOneExportedDelete();
        if (expOnes.length > 0) {
            DeleteUnloadedForeignKeys unloaded = null;
            for (int i = 0; i < expOnes.length; ++i) {
                final BeanPropertyAssocOne<?> prop = expOnes[i];
                if (request.isLoadedProperty(prop)) {
                    final Object detailBean = prop.getValue(parentBean);
                    if (detailBean != null) {
                        this.deleteRecurse(detailBean, t);
                    }
                }
                else {
                    if (unloaded == null) {
                        unloaded = new DeleteUnloadedForeignKeys(this.server, request);
                    }
                    unloaded.add(prop);
                }
            }
            if (unloaded != null) {
                unloaded.queryForeignKeys();
                unloaded.deleteCascade();
            }
        }
        final BeanPropertyAssocMany<?>[] manys = desc.propertiesManyDelete();
        for (int i = 0; i < manys.length; ++i) {
            if (manys[i].isManyToMany()) {
                this.deleteAssocManyIntersection(parentBean, manys[i], t);
            }
            else {
                if (BeanCollection.ModifyListenMode.REMOVALS.equals(manys[i].getModifyListenMode())) {
                    final Object details = manys[i].getValueUnderlying(parentBean);
                    if (details instanceof BeanCollection) {
                        final Set<?> modifyRemovals = ((BeanCollection)details).getModifyRemovals();
                        if (modifyRemovals != null && !modifyRemovals.isEmpty()) {
                            for (final Object detailBean2 : modifyRemovals) {
                                if (manys[i].hasId(detailBean2)) {
                                    this.deleteRecurse(detailBean2, t);
                                }
                            }
                        }
                    }
                }
                this.deleteManyDetails(t, desc, parentBean, manys[i], null);
            }
        }
        t.depth(1);
    }
    
    private void deleteManyDetails(final SpiTransaction t, final BeanDescriptor<?> desc, final Object parentBean, final BeanPropertyAssocMany<?> many, final ArrayList<Object> excludeDetailIds) {
        if (many.getCascadeInfo().isDelete()) {
            final BeanDescriptor<?> targetDesc = many.getTargetDescriptor();
            if (targetDesc.isDeleteRecurseSkippable() && !targetDesc.isUsingL2Cache()) {
                final IntersectionRow intRow = many.buildManyDeleteChildren(parentBean, excludeDetailIds);
                final SqlUpdate sqlDelete = intRow.createDelete(this.server);
                this.executeSqlUpdate(sqlDelete, t);
            }
            else {
                final Object parentId = desc.getId(parentBean);
                final List<Object> idsByParentId = many.findIdsByParentId(parentId, null, t, excludeDetailIds);
                if (!idsByParentId.isEmpty()) {
                    this.delete(targetDesc, null, idsByParentId, t);
                }
            }
        }
    }
    
    private void saveAssocOne(final PersistRequestBean<?> request) {
        final BeanDescriptor<?> desc = request.getBeanDescriptor();
        final BeanPropertyAssocOne<?>[] ones = desc.propertiesOneImportedSave();
        for (int i = 0; i < ones.length; ++i) {
            final BeanPropertyAssocOne<?> prop = ones[i];
            if (request.isLoadedProperty(prop)) {
                final Object detailBean = prop.getValue(request.getBean());
                if (detailBean != null) {
                    if (!this.isReference(detailBean)) {
                        if (!request.isParent(detailBean)) {
                            if (!prop.isSaveRecurseSkippable(detailBean)) {
                                final SpiTransaction t = request.getTransaction();
                                t.depth(-1);
                                this.saveRecurse(detailBean, t, null);
                                t.depth(1);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private boolean isReference(final Object bean) {
        return bean instanceof EntityBean && ((EntityBean)bean)._ebean_getIntercept().isReference();
    }
    
    private DeleteUnloadedForeignKeys getDeleteUnloadedForeignKeys(final PersistRequestBean<?> request) {
        DeleteUnloadedForeignKeys fkeys = null;
        final BeanPropertyAssocOne<?>[] ones = request.getBeanDescriptor().propertiesOneImportedDelete();
        for (int i = 0; i < ones.length; ++i) {
            if (!request.isLoadedProperty(ones[i])) {
                if (fkeys == null) {
                    fkeys = new DeleteUnloadedForeignKeys(this.server, request);
                }
                fkeys.add(ones[i]);
            }
        }
        return fkeys;
    }
    
    private void deleteAssocOne(final PersistRequestBean<?> request) {
        final BeanDescriptor<?> desc = request.getBeanDescriptor();
        final BeanPropertyAssocOne<?>[] ones = desc.propertiesOneImportedDelete();
        for (int i = 0; i < ones.length; ++i) {
            final BeanPropertyAssocOne<?> prop = ones[i];
            if (request.isLoadedProperty(prop)) {
                final Object detailBean = prop.getValue(request.getBean());
                if (detailBean != null && prop.hasId(detailBean)) {
                    this.deleteRecurse(detailBean, request.getTransaction());
                }
            }
        }
    }
    
    private void setIdGenValue(final PersistRequestBean<?> request) {
        final BeanDescriptor<?> desc = request.getBeanDescriptor();
        if (!desc.isUseIdGenerator()) {
            return;
        }
        final BeanProperty idProp = desc.getSingleIdProperty();
        if (idProp == null || idProp.isEmbedded()) {
            return;
        }
        final Object bean = request.getBean();
        final Object uid = idProp.getValue(bean);
        if (DmlUtil.isNullOrZero(uid)) {
            final Object nextId = desc.nextId(request.getTransaction());
            desc.convertSetId(nextId, bean);
        }
    }
    
    private Collection<?> getDetailsIterator(final Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof BeanCollection) {
            final BeanCollection<?> bc = (BeanCollection<?>)o;
            if (!bc.isPopulated()) {
                return null;
            }
            return bc.getActualDetails();
        }
        else {
            if (o instanceof Map) {
                return (Collection<?>)((Map)o).entrySet();
            }
            if (o instanceof Collection) {
                return (Collection<?>)o;
            }
            final String m = "expecting a Map or Collection but got [" + o.getClass().getName() + "]";
            throw new PersistenceException(m);
        }
    }
    
    private <T> PersistRequestBean<T> createRequest(final T bean, final Transaction t, final Object parentBean) {
        final BeanManager<T> mgr = this.getBeanManager(bean);
        if (mgr == null) {
            throw new PersistenceException(this.errNotRegistered(bean.getClass()));
        }
        return (PersistRequestBean<T>)this.createRequest(bean, t, parentBean, mgr);
    }
    
    private String errNotRegistered(final Class<?> beanClass) {
        String msg = "The type [" + beanClass + "] is not a registered entity?";
        msg += " If you don't explicitly list the entity classes to use Ebean will search for them in the classpath.";
        msg += " If the entity is in a Jar check the ebean.search.jars property in ebean.properties file or check ServerConfig.addJar().";
        return msg;
    }
    
    private PersistRequestBean<?> createRequest(final Object bean, final Transaction t, final Object parentBean, final BeanManager<?> mgr) {
        if (mgr.isLdapEntityType()) {
            return new LdapPersistBeanRequest<Object>(this.server, bean, parentBean, mgr, this.ldapPersister);
        }
        return new PersistRequestBean<Object>(this.server, bean, parentBean, mgr, (SpiTransaction)t, this.persistExecute);
    }
    
    private <T> BeanManager<T> getBeanManager(final T bean) {
        return this.beanDescriptorManager.getBeanManager(bean.getClass());
    }
    
    static {
        logger = Logger.getLogger(DefaultPersister.class.getName());
    }
    
    private static class SaveManyPropRequest
    {
        private final boolean insertedParent;
        private final BeanPropertyAssocMany<?> many;
        private final Object parentBean;
        private final SpiTransaction t;
        private final boolean cascade;
        private final boolean statelessUpdate;
        private final boolean deleteMissingChildren;
        private final boolean updateNullProperties;
        
        private SaveManyPropRequest(final boolean insertedParent, final BeanPropertyAssocMany<?> many, final Object parentBean, final PersistRequestBean<?> request) {
            this.insertedParent = insertedParent;
            this.many = many;
            this.cascade = many.getCascadeInfo().isSave();
            this.parentBean = parentBean;
            this.t = request.getTransaction();
            this.statelessUpdate = request.isStatelessUpdate();
            this.deleteMissingChildren = request.isDeleteMissingChildren();
            this.updateNullProperties = request.isUpdateNullProperties();
        }
        
        private SaveManyPropRequest(final BeanPropertyAssocMany<?> many, final Object parentBean, final SpiTransaction t) {
            this.insertedParent = false;
            this.many = many;
            this.parentBean = parentBean;
            this.t = t;
            this.cascade = true;
            this.statelessUpdate = false;
            this.deleteMissingChildren = false;
            this.updateNullProperties = false;
        }
        
        private Object getValueUnderlying() {
            return this.many.getValueUnderlying(this.parentBean);
        }
        
        private boolean isModifyListenMode() {
            return BeanCollection.ModifyListenMode.REMOVALS.equals(this.many.getModifyListenMode());
        }
        
        private boolean isStatelessUpdate() {
            return this.statelessUpdate;
        }
        
        private boolean isDeleteMissingChildren() {
            return this.deleteMissingChildren;
        }
        
        private boolean isUpdateNullProperties() {
            return this.updateNullProperties;
        }
        
        private boolean isInsertedParent() {
            return this.insertedParent;
        }
        
        private BeanPropertyAssocMany<?> getMany() {
            return this.many;
        }
        
        private Object getParentBean() {
            return this.parentBean;
        }
        
        private SpiTransaction getTransaction() {
            return this.t;
        }
        
        private boolean isCascade() {
            return this.cascade;
        }
    }
}
