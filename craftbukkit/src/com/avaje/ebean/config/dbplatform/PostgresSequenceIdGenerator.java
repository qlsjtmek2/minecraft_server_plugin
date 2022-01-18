// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

import javax.sql.DataSource;
import com.avaje.ebean.BackgroundExecutor;

public class PostgresSequenceIdGenerator extends SequenceIdGenerator
{
    private final String baseSql;
    
    public PostgresSequenceIdGenerator(final BackgroundExecutor be, final DataSource ds, final String seqName, final int batchSize) {
        super(be, ds, seqName, batchSize);
        this.baseSql = "select nextval('" + seqName + "'), s.generate_series from (" + "select generate_series from generate_series(1,";
    }
    
    public String getSql(final int batchSize) {
        return this.baseSql + batchSize + ") ) as s";
    }
}
