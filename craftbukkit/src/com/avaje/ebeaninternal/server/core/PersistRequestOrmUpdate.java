// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.PersistExecute;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.deploy.BeanManager;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.SpiUpdate;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public final class PersistRequestOrmUpdate extends PersistRequest
{
    private final BeanDescriptor<?> beanDescriptor;
    private SpiUpdate<?> ormUpdate;
    private int rowCount;
    private String bindLog;
    
    public PersistRequestOrmUpdate(final SpiEbeanServer server, final BeanManager<?> mgr, final SpiUpdate<?> ormUpdate, final SpiTransaction t, final PersistExecute persistExecute) {
        super(server, t, persistExecute);
        this.beanDescriptor = mgr.getBeanDescriptor();
        this.ormUpdate = ormUpdate;
    }
    
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.beanDescriptor;
    }
    
    public int executeNow() {
        return this.persistExecute.executeOrmUpdate(this);
    }
    
    public int executeOrQueue() {
        return this.executeStatement();
    }
    
    public SpiUpdate<?> getOrmUpdate() {
        return this.ormUpdate;
    }
    
    public void checkRowCount(final int count) throws SQLException {
        this.rowCount = count;
    }
    
    public boolean useGeneratedKeys() {
        return false;
    }
    
    public void setGeneratedKey(final Object idValue) {
    }
    
    public void setBindLog(final String bindLog) {
        this.bindLog = bindLog;
    }
    
    public void postExecute() throws SQLException {
        final SpiUpdate.OrmUpdateType ormUpdateType = this.ormUpdate.getOrmUpdateType();
        final String tableName = this.ormUpdate.getBaseTable();
        if (this.transaction.isLogSummary()) {
            final String m = ormUpdateType + " table[" + tableName + "] rows[" + this.rowCount + "] bind[" + this.bindLog + "]";
            this.transaction.logInternal(m);
        }
        if (this.ormUpdate.isNotifyCache()) {
            switch (ormUpdateType) {
                case INSERT: {
                    this.transaction.getEvent().add(tableName, true, false, false);
                    break;
                }
                case UPDATE: {
                    this.transaction.getEvent().add(tableName, false, true, false);
                    break;
                }
                case DELETE: {
                    this.transaction.getEvent().add(tableName, false, false, true);
                    break;
                }
            }
        }
    }
}
