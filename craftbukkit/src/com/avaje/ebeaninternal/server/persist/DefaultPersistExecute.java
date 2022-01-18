// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.server.core.PersistRequestCallableSql;
import com.avaje.ebeaninternal.server.core.PersistRequestUpdateSql;
import com.avaje.ebeaninternal.server.core.PersistRequestOrmUpdate;
import com.avaje.ebean.event.BeanPersistController;
import com.avaje.ebeaninternal.server.deploy.BeanManager;
import com.avaje.ebean.event.BeanPersistRequest;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.server.core.PstmtBatch;

public final class DefaultPersistExecute implements PersistExecute
{
    private final ExeCallableSql exeCallableSql;
    private final ExeUpdateSql exeUpdateSql;
    private final ExeOrmUpdate exeOrmUpdate;
    private final int defaultBatchSize;
    private final boolean defaultBatchGenKeys;
    private final boolean validate;
    
    public DefaultPersistExecute(final boolean validate, final Binder binder, final PstmtBatch pstmtBatch) {
        this.validate = validate;
        this.exeOrmUpdate = new ExeOrmUpdate(binder, pstmtBatch);
        this.exeUpdateSql = new ExeUpdateSql(binder, pstmtBatch);
        this.exeCallableSql = new ExeCallableSql(binder, pstmtBatch);
        this.defaultBatchGenKeys = GlobalProperties.getBoolean("batch.getgeneratedkeys", true);
        this.defaultBatchSize = GlobalProperties.getInt("batch.size", 20);
    }
    
    public BatchControl createBatchControl(final SpiTransaction t) {
        return new BatchControl(t, this.defaultBatchSize, this.defaultBatchGenKeys);
    }
    
    public <T> void executeInsertBean(final PersistRequestBean<T> request) {
        final BeanManager<T> mgr = request.getBeanManager();
        final BeanPersister persister = mgr.getBeanPersister();
        final BeanPersistController controller = request.getBeanController();
        if (controller == null || controller.preInsert(request)) {
            if (this.validate) {
                request.validate();
            }
            persister.insert(request);
        }
    }
    
    public <T> void executeUpdateBean(final PersistRequestBean<T> request) {
        final BeanManager<T> mgr = request.getBeanManager();
        final BeanPersister persister = mgr.getBeanPersister();
        final BeanPersistController controller = request.getBeanController();
        if (controller == null || controller.preUpdate(request)) {
            if (this.validate) {
                request.validate();
            }
            persister.update(request);
        }
    }
    
    public <T> void executeDeleteBean(final PersistRequestBean<T> request) {
        final BeanManager<T> mgr = request.getBeanManager();
        final BeanPersister persister = mgr.getBeanPersister();
        final BeanPersistController controller = request.getBeanController();
        if (controller == null || controller.preDelete(request)) {
            persister.delete(request);
        }
    }
    
    public int executeOrmUpdate(final PersistRequestOrmUpdate request) {
        return this.exeOrmUpdate.execute(request);
    }
    
    public int executeSqlUpdate(final PersistRequestUpdateSql request) {
        return this.exeUpdateSql.execute(request);
    }
    
    public int executeSqlCallable(final PersistRequestCallableSql request) {
        return this.exeCallableSql.execute(request);
    }
}
