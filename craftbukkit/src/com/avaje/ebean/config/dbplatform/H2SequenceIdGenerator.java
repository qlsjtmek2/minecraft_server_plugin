// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

import javax.sql.DataSource;
import com.avaje.ebean.BackgroundExecutor;

public class H2SequenceIdGenerator extends SequenceIdGenerator
{
    private final String baseSql;
    private final String unionBaseSql;
    
    public H2SequenceIdGenerator(final BackgroundExecutor be, final DataSource ds, final String seqName, final int batchSize) {
        super(be, ds, seqName, batchSize);
        this.baseSql = "select " + seqName + ".nextval";
        this.unionBaseSql = " union " + this.baseSql;
    }
    
    public String getSql(final int batchSize) {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.baseSql);
        for (int i = 1; i < batchSize; ++i) {
            sb.append(this.unionBaseSql);
        }
        return sb.toString();
    }
}
