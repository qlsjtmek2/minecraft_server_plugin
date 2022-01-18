// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

import javax.sql.DataSource;
import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebean.config.GlobalProperties;

public class PostgresPlatform extends DatabasePlatform
{
    public PostgresPlatform() {
        this.name = "postgres";
        this.selectCountWithAlias = true;
        this.blobDbType = -4;
        this.clobDbType = 12;
        this.dbEncrypt = new PostgresDbEncrypt();
        this.dbIdentity.setSupportsGetGeneratedKeys(false);
        this.dbIdentity.setIdType(IdType.SEQUENCE);
        this.dbIdentity.setSupportsSequence(true);
        final String colAlias = GlobalProperties.get("ebean.columnAliasPrefix", null);
        if (colAlias == null) {
            GlobalProperties.put("ebean.columnAliasPrefix", "as c");
        }
        this.openQuote = "\"";
        this.closeQuote = "\"";
        this.dbTypeMap.put(4, new DbType("integer", false));
        this.dbTypeMap.put(8, new DbType("float"));
        this.dbTypeMap.put(-6, new DbType("smallint"));
        this.dbTypeMap.put(3, new DbType("decimal", 38));
        this.dbTypeMap.put(-2, new DbType("bytea", false));
        this.dbTypeMap.put(-3, new DbType("bytea", false));
        this.dbTypeMap.put(2004, new DbType("bytea", false));
        this.dbTypeMap.put(2005, new DbType("text"));
        this.dbTypeMap.put(-4, new DbType("bytea", false));
        this.dbTypeMap.put(-1, new DbType("text"));
        this.dbDdlSyntax.setDropTableCascade("cascade");
        this.dbDdlSyntax.setDropIfExists("if exists");
    }
    
    public IdGenerator createSequenceIdGenerator(final BackgroundExecutor be, final DataSource ds, final String seqName, final int batchSize) {
        return new PostgresSequenceIdGenerator(be, ds, seqName, batchSize);
    }
}
