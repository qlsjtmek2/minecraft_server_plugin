// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.PersistExecute;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.SpiSqlUpdate;

public final class PersistRequestUpdateSql extends PersistRequest
{
    private final SpiSqlUpdate updateSql;
    private int rowCount;
    private String bindLog;
    private SqlType sqlType;
    private String tableName;
    private String description;
    
    public PersistRequestUpdateSql(final SpiEbeanServer server, final SqlUpdate updateSql, final SpiTransaction t, final PersistExecute persistExecute) {
        super(server, t, persistExecute);
        this.type = Type.UPDATESQL;
        this.updateSql = (SpiSqlUpdate)updateSql;
    }
    
    public int executeNow() {
        return this.persistExecute.executeSqlUpdate(this);
    }
    
    public int executeOrQueue() {
        return this.executeStatement();
    }
    
    public SpiSqlUpdate getUpdateSql() {
        return this.updateSql;
    }
    
    public void checkRowCount(final int count) throws SQLException {
        this.rowCount = count;
    }
    
    public boolean useGeneratedKeys() {
        return false;
    }
    
    public void setGeneratedKey(final Object idValue) {
    }
    
    public void setType(final SqlType sqlType, final String tableName, final String description) {
        this.sqlType = sqlType;
        this.tableName = tableName;
        this.description = description;
    }
    
    public void setBindLog(final String bindLog) {
        this.bindLog = bindLog;
    }
    
    public void postExecute() throws SQLException {
        if (this.transaction.isLogSummary()) {
            final String m = this.description + " table[" + this.tableName + "] rows[" + this.rowCount + "] bind[" + this.bindLog + "]";
            this.transaction.logInternal(m);
        }
        if (this.updateSql.isAutoTableMod()) {
            switch (this.sqlType) {
                case SQL_INSERT: {
                    this.transaction.getEvent().add(this.tableName, true, false, false);
                    break;
                }
                case SQL_UPDATE: {
                    this.transaction.getEvent().add(this.tableName, false, true, false);
                    break;
                }
                case SQL_DELETE: {
                    this.transaction.getEvent().add(this.tableName, false, false, true);
                    break;
                }
            }
        }
    }
    
    public enum SqlType
    {
        SQL_UPDATE, 
        SQL_DELETE, 
        SQL_INSERT, 
        SQL_UNKNOWN;
    }
}
