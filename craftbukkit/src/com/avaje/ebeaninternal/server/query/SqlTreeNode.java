// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import java.util.List;

public interface SqlTreeNode
{
    public static final char NEW_LINE = '\n';
    public static final String PERIOD = ".";
    public static final String COMMA = ", ";
    public static final int NORMAL = 0;
    public static final int SHARED = 1;
    public static final int READONLY = 2;
    
    void buildSelectExpressionChain(final List<String> p0);
    
    void appendSelect(final DbSqlContext p0, final boolean p1);
    
    void appendFrom(final DbSqlContext p0, final boolean p1);
    
    void appendWhere(final DbSqlContext p0);
    
    void load(final DbReadContext p0, final Object p1, final int p2) throws SQLException;
}
