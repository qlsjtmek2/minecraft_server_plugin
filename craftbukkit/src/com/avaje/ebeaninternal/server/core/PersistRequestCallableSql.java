// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.util.List;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.PersistExecute;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebean.CallableSql;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.BindParams;
import java.sql.CallableStatement;
import com.avaje.ebeaninternal.api.SpiCallableSql;

public final class PersistRequestCallableSql extends PersistRequest
{
    private final SpiCallableSql callableSql;
    private int rowCount;
    private String bindLog;
    private CallableStatement cstmt;
    private BindParams bindParam;
    
    public PersistRequestCallableSql(final SpiEbeanServer server, final CallableSql cs, final SpiTransaction t, final PersistExecute persistExecute) {
        super(server, t, persistExecute);
        this.type = Type.CALLABLESQL;
        this.callableSql = (SpiCallableSql)cs;
    }
    
    public int executeOrQueue() {
        return this.executeStatement();
    }
    
    public int executeNow() {
        return this.persistExecute.executeSqlCallable(this);
    }
    
    public SpiCallableSql getCallableSql() {
        return this.callableSql;
    }
    
    public void setBindLog(final String bindLog) {
        this.bindLog = bindLog;
    }
    
    public void checkRowCount(final int count) throws SQLException {
        this.rowCount = count;
    }
    
    public void setGeneratedKey(final Object idValue) {
    }
    
    public boolean useGeneratedKeys() {
        return false;
    }
    
    public void postExecute() throws SQLException {
        if (this.transaction.isLogSummary()) {
            final String m = "CallableSql label[" + this.callableSql.getLabel() + "]" + " rows[" + this.rowCount + "]" + " bind[" + this.bindLog + "]";
            this.transaction.logInternal(m);
        }
        final TransactionEventTable tableEvents = this.callableSql.getTransactionEventTable();
        if (tableEvents != null && !tableEvents.isEmpty()) {
            this.transaction.getEvent().add(tableEvents);
        }
    }
    
    public void setBound(final BindParams bindParam, final CallableStatement cstmt) {
        this.bindParam = bindParam;
        this.cstmt = cstmt;
    }
    
    public int executeUpdate() throws SQLException {
        if (this.callableSql.executeOverride(this.cstmt)) {
            return -1;
        }
        this.rowCount = this.cstmt.executeUpdate();
        this.readOutParams();
        return this.rowCount;
    }
    
    private void readOutParams() throws SQLException {
        final List<BindParams.Param> list = this.bindParam.positionedParameters();
        int pos = 0;
        for (int i = 0; i < list.size(); ++i) {
            ++pos;
            final BindParams.Param param = list.get(i);
            if (param.isOutParam()) {
                final Object outValue = this.cstmt.getObject(pos);
                param.setOutValue(outValue);
            }
        }
    }
}
