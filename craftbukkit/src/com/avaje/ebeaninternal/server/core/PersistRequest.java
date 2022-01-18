// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import com.avaje.ebeaninternal.server.persist.BatchControl;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.persist.PersistExecute;
import com.avaje.ebeaninternal.server.persist.BatchPostExecute;

public abstract class PersistRequest extends BeanRequest implements BatchPostExecute
{
    boolean persistCascade;
    Type type;
    final PersistExecute persistExecute;
    
    public PersistRequest(final SpiEbeanServer server, final SpiTransaction t, final PersistExecute persistExecute) {
        super(server, t);
        this.persistExecute = persistExecute;
    }
    
    public abstract int executeOrQueue();
    
    public abstract int executeNow();
    
    public PstmtBatch getPstmtBatch() {
        return this.ebeanServer.getPstmtBatch();
    }
    
    public boolean isLogSql() {
        return this.transaction.isLogSql();
    }
    
    public boolean isLogSummary() {
        return this.transaction.isLogSummary();
    }
    
    public int executeStatement() {
        final boolean batch = this.transaction.isBatchThisRequest();
        BatchControl control = this.transaction.getBatchControl();
        int rows;
        if (control != null) {
            rows = control.executeStatementOrBatch(this, batch);
        }
        else if (batch) {
            control = this.persistExecute.createBatchControl(this.transaction);
            rows = control.executeStatementOrBatch(this, batch);
        }
        else {
            rows = this.executeNow();
        }
        return rows;
    }
    
    public void initTransIfRequired() {
        this.createImplicitTransIfRequired(false);
        this.persistCascade = this.transaction.isPersistCascade();
    }
    
    public Type getType() {
        return this.type;
    }
    
    public void setType(final Type type) {
        this.type = type;
    }
    
    public boolean isPersistCascade() {
        return this.persistCascade;
    }
    
    public enum Type
    {
        INSERT, 
        UPDATE, 
        DELETE, 
        ORMUPDATE, 
        UPDATESQL, 
        CALLABLESQL;
    }
}
