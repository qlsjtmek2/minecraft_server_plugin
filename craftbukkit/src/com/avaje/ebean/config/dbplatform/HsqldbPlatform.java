// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

import javax.sql.DataSource;
import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebean.config.GlobalProperties;

public class HsqldbPlatform extends DatabasePlatform
{
    public HsqldbPlatform() {
        this.name = "hsqldb";
        this.dbEncrypt = new H2DbEncrypt();
        final boolean useIdentity = GlobalProperties.getBoolean("ebean.hsqldb.useIdentity", true);
        final IdType idType = useIdentity ? IdType.IDENTITY : IdType.SEQUENCE;
        this.dbIdentity.setIdType(idType);
        this.dbIdentity.setSupportsGetGeneratedKeys(true);
        this.dbIdentity.setSupportsSequence(true);
        this.dbIdentity.setSupportsIdentity(true);
        this.openQuote = "\"";
        this.closeQuote = "\"";
        this.dbTypeMap.put(4, new DbType("integer", false));
        this.dbDdlSyntax.setDropIfExists("if exists");
        this.dbDdlSyntax.setDisableReferentialIntegrity("SET DATABASE REFERENTIAL INTEGRITY FALSE");
        this.dbDdlSyntax.setEnableReferentialIntegrity("SET DATABASE REFERENTIAL INTEGRITY TRUE");
        this.dbDdlSyntax.setForeignKeySuffix("on delete restrict on update restrict");
        this.dbDdlSyntax.setIdentity("GENERATED BY DEFAULT AS IDENTITY (START WITH 1) ");
    }
    
    public IdGenerator createSequenceIdGenerator(final BackgroundExecutor be, final DataSource ds, final String seqName, final int batchSize) {
        return new H2SequenceIdGenerator(be, ds, seqName, batchSize);
    }
}
