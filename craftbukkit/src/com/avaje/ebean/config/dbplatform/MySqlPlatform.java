// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

import javax.sql.DataSource;
import com.avaje.ebean.BackgroundExecutor;

public class MySqlPlatform extends DatabasePlatform
{
    public MySqlPlatform() {
        this.name = "mysql";
        this.selectCountWithAlias = true;
        this.dbEncrypt = new MySqlDbEncrypt();
        this.dbIdentity.setIdType(IdType.IDENTITY);
        this.dbIdentity.setSupportsGetGeneratedKeys(true);
        this.dbIdentity.setSupportsIdentity(true);
        this.dbIdentity.setSupportsSequence(false);
        this.openQuote = "`";
        this.closeQuote = "`";
        this.booleanDbType = -7;
        this.dbTypeMap.put(-7, new DbType("tinyint(1) default 0"));
        this.dbTypeMap.put(16, new DbType("tinyint(1) default 0"));
        this.dbTypeMap.put(93, new DbType("datetime"));
        this.dbTypeMap.put(2005, new MySqlClob());
        this.dbTypeMap.put(2004, new MySqlBlob());
        this.dbTypeMap.put(-2, new DbType("binary", 255));
        this.dbTypeMap.put(-3, new DbType("varbinary", 255));
        this.dbDdlSyntax.setDisableReferentialIntegrity("SET FOREIGN_KEY_CHECKS=0");
        this.dbDdlSyntax.setEnableReferentialIntegrity("SET FOREIGN_KEY_CHECKS=1");
        this.dbDdlSyntax.setForeignKeySuffix("on delete restrict on update restrict");
    }
    
    public IdGenerator createSequenceIdGenerator(final BackgroundExecutor be, final DataSource ds, final String seqName, final int batchSize) {
        return null;
    }
}
