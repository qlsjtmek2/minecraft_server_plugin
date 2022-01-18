// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.TableJoin;

public final class SqlTreeNodeRoot extends SqlTreeNodeBean
{
    private final TableJoin includeJoin;
    
    public SqlTreeNodeRoot(final BeanDescriptor<?> desc, final SqlTreeProperties props, final List<SqlTreeNode> myList, final boolean withId, final TableJoin includeJoin) {
        super(null, null, desc, props, myList, withId);
        this.includeJoin = includeJoin;
    }
    
    public SqlTreeNodeRoot(final BeanDescriptor<?> desc, final SqlTreeProperties props, final List<SqlTreeNode> myList, final boolean withId) {
        super(null, null, desc, props, myList, withId);
        this.includeJoin = null;
    }
    
    protected void postLoad(final DbReadContext cquery, final Object loadedBean, final Object id) {
        cquery.setLoadedBean(loadedBean, id);
    }
    
    public boolean appendFromBaseTable(final DbSqlContext ctx, final boolean forceOuterJoin) {
        ctx.append(this.desc.getBaseTable());
        ctx.append(" ").append(ctx.getTableAlias(null));
        if (this.includeJoin != null) {
            final String a1 = ctx.getTableAlias(null);
            final String a2 = "int_";
            this.includeJoin.addJoin(forceOuterJoin, a1, a2, ctx);
        }
        return forceOuterJoin;
    }
}
