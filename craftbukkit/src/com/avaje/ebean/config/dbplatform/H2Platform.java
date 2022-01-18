// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

import javax.sql.DataSource;
import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebean.config.GlobalProperties;

public class H2Platform extends DatabasePlatform
{
    public H2Platform() {
        this.name = "h2";
        this.dbEncrypt = new H2DbEncrypt();
        final boolean useIdentity = GlobalProperties.getBoolean("ebean.h2platform.useIdentity", false);
        final IdType idType = useIdentity ? IdType.IDENTITY : IdType.SEQUENCE;
        this.dbIdentity.setIdType(idType);
        this.dbIdentity.setSupportsGetGeneratedKeys(true);
        this.dbIdentity.setSupportsSequence(true);
        this.dbIdentity.setSupportsIdentity(true);
        this.openQuote = "\"";
        this.closeQuote = "\"";
        this.dbDdlSyntax.setDropIfExists("if exists");
        this.dbDdlSyntax.setDisableReferentialIntegrity("SET REFERENTIAL_INTEGRITY FALSE");
        this.dbDdlSyntax.setEnableReferentialIntegrity("SET REFERENTIAL_INTEGRITY TRUE");
        this.dbDdlSyntax.setForeignKeySuffix("on delete restrict on update restrict");
    }
    
    public IdGenerator createSequenceIdGenerator(final BackgroundExecutor be, final DataSource ds, final String seqName, final int batchSize) {
        return new H2SequenceIdGenerator(be, ds, seqName, batchSize);
    }
}
