// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.parse;

import com.avaje.ebeaninternal.server.deploy.DRawSqlMeta;
import com.avaje.ebean.annotation.SqlSelect;
import com.avaje.ebean.annotation.Sql;

public class AnnotationSql extends AnnotationParser
{
    public AnnotationSql(final DeployBeanInfo<?> info) {
        super(info);
    }
    
    public void parse() {
        final Class<?> cls = this.descriptor.getBeanType();
        final Sql sql = cls.getAnnotation(Sql.class);
        if (sql != null) {
            this.setSql(sql);
        }
        final SqlSelect sqlSelect = cls.getAnnotation(SqlSelect.class);
        if (sqlSelect != null) {
            this.setSqlSelect(sqlSelect);
        }
    }
    
    private void setSql(final Sql sql) {
        final SqlSelect[] select = sql.select();
        for (int i = 0; i < select.length; ++i) {
            this.setSqlSelect(select[i]);
        }
    }
    
    private void setSqlSelect(final SqlSelect sqlSelect) {
        final DRawSqlMeta rawSqlMeta = new DRawSqlMeta(sqlSelect);
        this.descriptor.add(rawSqlMeta);
    }
}
