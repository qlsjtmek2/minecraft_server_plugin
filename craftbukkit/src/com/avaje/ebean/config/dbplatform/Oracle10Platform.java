// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

import javax.sql.DataSource;
import com.avaje.ebean.BackgroundExecutor;

public class Oracle10Platform extends DatabasePlatform
{
    public Oracle10Platform() {
        this.name = "oracle";
        this.dbEncrypt = new Oracle10DbEncrypt();
        this.sqlLimiter = new RownumSqlLimiter();
        this.dbIdentity.setSupportsGetGeneratedKeys(false);
        this.dbIdentity.setIdType(IdType.SEQUENCE);
        this.dbIdentity.setSupportsSequence(true);
        this.treatEmptyStringsAsNull = true;
        this.openQuote = "\"";
        this.closeQuote = "\"";
        this.booleanDbType = 4;
        this.dbTypeMap.put(16, new DbType("number(1) default 0"));
        this.dbTypeMap.put(4, new DbType("number", 10));
        this.dbTypeMap.put(-5, new DbType("number", 19));
        this.dbTypeMap.put(7, new DbType("number", 19, 4));
        this.dbTypeMap.put(8, new DbType("number", 19, 4));
        this.dbTypeMap.put(5, new DbType("number", 5));
        this.dbTypeMap.put(-6, new DbType("number", 3));
        this.dbTypeMap.put(3, new DbType("number", 38));
        this.dbTypeMap.put(12, new DbType("varchar2", 255));
        this.dbTypeMap.put(-4, new DbType("blob"));
        this.dbTypeMap.put(-1, new DbType("clob"));
        this.dbTypeMap.put(-3, new DbType("raw", 255));
        this.dbTypeMap.put(-2, new DbType("raw", 255));
        this.dbTypeMap.put(92, new DbType("timestamp"));
        this.dbDdlSyntax.setDropTableCascade("cascade constraints purge");
        this.dbDdlSyntax.setIdentity(null);
        this.dbDdlSyntax.setMaxConstraintNameLength(30);
    }
    
    public IdGenerator createSequenceIdGenerator(final BackgroundExecutor be, final DataSource ds, final String seqName, final int batchSize) {
        return new OracleSequenceIdGenerator(be, ds, seqName, batchSize);
    }
}
