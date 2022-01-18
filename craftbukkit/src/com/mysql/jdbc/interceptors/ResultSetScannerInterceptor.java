// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.interceptors;

import java.lang.reflect.Proxy;
import java.util.regex.Matcher;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.InvocationHandler;
import com.mysql.jdbc.ResultSetInternalMethods;
import com.mysql.jdbc.Statement;
import java.sql.SQLException;
import java.util.Properties;
import com.mysql.jdbc.Connection;
import java.util.regex.Pattern;
import com.mysql.jdbc.StatementInterceptor;

public class ResultSetScannerInterceptor implements StatementInterceptor
{
    private Pattern regexP;
    
    public void init(final Connection conn, final Properties props) throws SQLException {
        final String regexFromUser = props.getProperty("resultSetScannerRegex");
        if (regexFromUser == null || regexFromUser.length() == 0) {
            throw new SQLException("resultSetScannerRegex must be configured, and must be > 0 characters");
        }
        try {
            this.regexP = Pattern.compile(regexFromUser);
        }
        catch (Throwable t) {
            final SQLException sqlEx = new SQLException("Can't use configured regex due to underlying exception.");
            sqlEx.initCause(t);
            throw sqlEx;
        }
    }
    
    public ResultSetInternalMethods postProcess(final String sql, final Statement interceptedStatement, final ResultSetInternalMethods originalResultSet, final Connection connection) throws SQLException {
        return (ResultSetInternalMethods)Proxy.newProxyInstance(originalResultSet.getClass().getClassLoader(), new Class[] { ResultSetInternalMethods.class }, new InvocationHandler() {
            public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
                final Object invocationResult = method.invoke(originalResultSet, args);
                final String methodName = method.getName();
                if ((invocationResult != null && invocationResult instanceof String) || "getString".equals(methodName) || "getObject".equals(methodName) || "getObjectStoredProc".equals(methodName)) {
                    final Matcher matcher = ResultSetScannerInterceptor.this.regexP.matcher(invocationResult.toString());
                    if (matcher.matches()) {
                        throw new SQLException("value disallowed by filter");
                    }
                }
                return invocationResult;
            }
        });
    }
    
    public ResultSetInternalMethods preProcess(final String sql, final Statement interceptedStatement, final Connection connection) throws SQLException {
        return null;
    }
    
    public boolean executeTopLevelOnly() {
        return false;
    }
    
    public void destroy() {
    }
}
