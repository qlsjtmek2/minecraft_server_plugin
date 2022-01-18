// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import java.util.List;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;

public class SqlTreeNodeManyWhereJoin implements SqlTreeNode
{
    private final String parentPrefix;
    private final String prefix;
    private final BeanPropertyAssoc<?> nodeBeanProp;
    private final SqlTreeNode[] children;
    
    public SqlTreeNodeManyWhereJoin(final String prefix, final BeanPropertyAssoc<?> prop) {
        this.nodeBeanProp = prop;
        this.prefix = prefix;
        final String[] split = SplitName.split(prefix);
        this.parentPrefix = split[0];
        final List<SqlTreeNode> childrenList = new ArrayList<SqlTreeNode>(0);
        this.children = childrenList.toArray(new SqlTreeNode[childrenList.size()]);
    }
    
    public void appendFrom(final DbSqlContext ctx, final boolean forceOuterJoin) {
        this.appendFromBaseTable(ctx, forceOuterJoin);
        for (int i = 0; i < this.children.length; ++i) {
            this.children[i].appendFrom(ctx, forceOuterJoin);
        }
    }
    
    public void appendFromBaseTable(final DbSqlContext ctx, final boolean forceOuterJoin) {
        final String alias = ctx.getTableAliasManyWhere(this.prefix);
        final String parentAlias = ctx.getTableAliasManyWhere(this.parentPrefix);
        if (this.nodeBeanProp instanceof BeanPropertyAssocOne) {
            this.nodeBeanProp.addInnerJoin(parentAlias, alias, ctx);
        }
        else {
            final BeanPropertyAssocMany<?> manyProp = (BeanPropertyAssocMany<?>)(BeanPropertyAssocMany)this.nodeBeanProp;
            if (!manyProp.isManyToMany()) {
                manyProp.addInnerJoin(parentAlias, alias, ctx);
            }
            else {
                final String alias2 = alias + "z_";
                final TableJoin manyToManyJoin = manyProp.getIntersectionTableJoin();
                manyToManyJoin.addInnerJoin(parentAlias, alias2, ctx);
                manyProp.addInnerJoin(alias2, alias, ctx);
            }
        }
    }
    
    public void buildSelectExpressionChain(final List<String> selectChain) {
    }
    
    public void appendSelect(final DbSqlContext ctx, final boolean subQuery) {
    }
    
    public void appendWhere(final DbSqlContext ctx) {
    }
    
    public void load(final DbReadContext ctx, final Object parentBean, final int parentState) throws SQLException {
    }
}
