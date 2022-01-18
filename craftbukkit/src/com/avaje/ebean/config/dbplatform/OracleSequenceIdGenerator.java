// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

import javax.sql.DataSource;
import com.avaje.ebean.BackgroundExecutor;

public class OracleSequenceIdGenerator extends SequenceIdGenerator
{
    private final String baseSql;
    
    public OracleSequenceIdGenerator(final BackgroundExecutor be, final DataSource ds, final String seqName, final int batchSize) {
        super(be, ds, seqName, batchSize);
        this.baseSql = "select " + seqName + ".nextval, a from (select level as a FROM dual CONNECT BY level <= ";
    }
    
    public String getSql(final int batchSize) {
        return this.baseSql + batchSize + ")";
    }
}
