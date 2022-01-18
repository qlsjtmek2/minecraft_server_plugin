// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;

public class SqlTreeNodeExtraJoin implements SqlTreeNode
{
    final BeanPropertyAssoc<?> assocBeanProperty;
    final String prefix;
    final boolean manyJoin;
    List<SqlTreeNodeExtraJoin> children;
    
    public SqlTreeNodeExtraJoin(final String prefix, final BeanPropertyAssoc<?> assocBeanProperty) {
        this.prefix = prefix;
        this.assocBeanProperty = assocBeanProperty;
        this.manyJoin = (assocBeanProperty instanceof BeanPropertyAssocMany);
    }
    
    public void buildSelectExpressionChain(final List<String> selectChain) {
    }
    
    public boolean isManyJoin() {
        return this.manyJoin;
    }
    
    public String getName() {
        return this.prefix;
    }
    
    public void addChild(final SqlTreeNodeExtraJoin child) {
        if (this.children == null) {
            this.children = new ArrayList<SqlTreeNodeExtraJoin>();
        }
        this.children.add(child);
    }
    
    public void appendFrom(final DbSqlContext ctx, boolean forceOuterJoin) {
        boolean manyToMany = false;
        if (this.assocBeanProperty instanceof BeanPropertyAssocMany) {
            final BeanPropertyAssocMany<?> manyProp = (BeanPropertyAssocMany<?>)(BeanPropertyAssocMany)this.assocBeanProperty;
            if (manyProp.isManyToMany()) {
                manyToMany = true;
                final String alias = ctx.getTableAlias(this.prefix);
                final String[] split = SplitName.split(this.prefix);
                final String parentAlias = ctx.getTableAlias(split[0]);
                final String alias2 = alias + "z_";
                final TableJoin manyToManyJoin = manyProp.getIntersectionTableJoin();
                manyToManyJoin.addJoin(forceOuterJoin, parentAlias, alias2, ctx);
                this.assocBeanProperty.addJoin(forceOuterJoin, alias2, alias, ctx);
            }
        }
        if (!manyToMany) {
            this.assocBeanProperty.addJoin(forceOuterJoin, this.prefix, ctx);
        }
        if (this.children != null) {
            if (this.manyJoin) {
                forceOuterJoin = true;
            }
            for (int i = 0; i < this.children.size(); ++i) {
                final SqlTreeNodeExtraJoin child = this.children.get(i);
                child.appendFrom(ctx, forceOuterJoin);
            }
        }
    }
    
    public void appendSelect(final DbSqlContext ctx, final boolean subQuery) {
    }
    
    public void appendWhere(final DbSqlContext ctx) {
    }
    
    public void load(final DbReadContext ctx, final Object parentBean, final int parentState) throws SQLException {
    }
}
