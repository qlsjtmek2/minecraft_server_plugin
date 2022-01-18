// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;

public final class SqlTreeNodeManyRoot extends SqlTreeNodeBean
{
    public SqlTreeNodeManyRoot(final String prefix, final BeanPropertyAssocMany<?> prop, final SqlTreeProperties props, final List<SqlTreeNode> myList) {
        super(prefix, prop, prop.getTargetDescriptor(), props, myList, true);
    }
    
    protected void postLoad(final DbReadContext cquery, final Object loadedBean, final Object id) {
        cquery.setLoadedManyBean(loadedBean);
    }
    
    public void load(final DbReadContext cquery, final Object parentBean, final int parentState) throws SQLException {
        super.load(cquery, null, parentState);
    }
    
    public void appendFrom(final DbSqlContext ctx, final boolean forceOuterJoin) {
        super.appendFrom(ctx, true);
    }
}
