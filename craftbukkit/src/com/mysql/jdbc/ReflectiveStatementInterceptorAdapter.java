// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Properties;
import java.lang.reflect.Method;

public class ReflectiveStatementInterceptorAdapter implements StatementInterceptorV2
{
    private final StatementInterceptor toProxy;
    final Method v2PostProcessMethod;
    
    public ReflectiveStatementInterceptorAdapter(final StatementInterceptor toProxy) {
        this.toProxy = toProxy;
        this.v2PostProcessMethod = getV2PostProcessMethod(toProxy.getClass());
    }
    
    public void destroy() {
        this.toProxy.destroy();
    }
    
    public boolean executeTopLevelOnly() {
        return this.toProxy.executeTopLevelOnly();
    }
    
    public void init(final Connection conn, final Properties props) throws SQLException {
        this.toProxy.init(conn, props);
    }
    
    public ResultSetInternalMethods postProcess(final String sql, final Statement interceptedStatement, final ResultSetInternalMethods originalResultSet, final Connection connection, final int warningCount, final boolean noIndexUsed, final boolean noGoodIndexUsed, final SQLException statementException) throws SQLException {
        try {
            return (ResultSetInternalMethods)this.v2PostProcessMethod.invoke(this.toProxy, sql, interceptedStatement, originalResultSet, connection, new Integer(warningCount), noIndexUsed ? Boolean.TRUE : Boolean.FALSE, noGoodIndexUsed ? Boolean.TRUE : Boolean.FALSE, statementException);
        }
        catch (IllegalArgumentException e) {
            final SQLException sqlEx = new SQLException("Unable to reflectively invoke interceptor");
            sqlEx.initCause(e);
            throw sqlEx;
        }
        catch (IllegalAccessException e2) {
            final SQLException sqlEx = new SQLException("Unable to reflectively invoke interceptor");
            sqlEx.initCause(e2);
            throw sqlEx;
        }
        catch (InvocationTargetException e3) {
            final SQLException sqlEx = new SQLException("Unable to reflectively invoke interceptor");
            sqlEx.initCause(e3);
            throw sqlEx;
        }
    }
    
    public ResultSetInternalMethods preProcess(final String sql, final Statement interceptedStatement, final Connection connection) throws SQLException {
        return this.toProxy.preProcess(sql, interceptedStatement, connection);
    }
    
    public static final Method getV2PostProcessMethod(final Class toProxyClass) {
        try {
            final Method postProcessMethod = toProxyClass.getMethod("postProcess", String.class, Statement.class, ResultSetInternalMethods.class, Connection.class, Integer.TYPE, Boolean.TYPE, Boolean.TYPE, SQLException.class);
            return postProcessMethod;
        }
        catch (SecurityException e) {
            return null;
        }
        catch (NoSuchMethodException e2) {
            return null;
        }
    }
}
