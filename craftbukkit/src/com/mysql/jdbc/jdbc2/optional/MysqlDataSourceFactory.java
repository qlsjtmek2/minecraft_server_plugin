// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.jdbc2.optional;

import javax.naming.RefAddr;
import javax.naming.Reference;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;

public class MysqlDataSourceFactory implements ObjectFactory
{
    protected static final String DATA_SOURCE_CLASS_NAME = "com.mysql.jdbc.jdbc2.optional.MysqlDataSource";
    protected static final String POOL_DATA_SOURCE_CLASS_NAME = "com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource";
    protected static final String XA_DATA_SOURCE_CLASS_NAME = "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource";
    
    public Object getObjectInstance(final Object refObj, final Name nm, final Context ctx, final Hashtable env) throws Exception {
        final Reference ref = (Reference)refObj;
        final String className = ref.getClassName();
        if (className != null && (className.equals("com.mysql.jdbc.jdbc2.optional.MysqlDataSource") || className.equals("com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource") || className.equals("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource"))) {
            MysqlDataSource dataSource = null;
            try {
                dataSource = (MysqlDataSource)Class.forName(className).newInstance();
            }
            catch (Exception ex) {
                throw new RuntimeException("Unable to create DataSource of class '" + className + "', reason: " + ex.toString());
            }
            int portNumber = 3306;
            final String portNumberAsString = this.nullSafeRefAddrStringGet("port", ref);
            if (portNumberAsString != null) {
                portNumber = Integer.parseInt(portNumberAsString);
            }
            dataSource.setPort(portNumber);
            final String user = this.nullSafeRefAddrStringGet("user", ref);
            if (user != null) {
                dataSource.setUser(user);
            }
            final String password = this.nullSafeRefAddrStringGet("password", ref);
            if (password != null) {
                dataSource.setPassword(password);
            }
            final String serverName = this.nullSafeRefAddrStringGet("serverName", ref);
            if (serverName != null) {
                dataSource.setServerName(serverName);
            }
            final String databaseName = this.nullSafeRefAddrStringGet("databaseName", ref);
            if (databaseName != null) {
                dataSource.setDatabaseName(databaseName);
            }
            final String explicitUrlAsString = this.nullSafeRefAddrStringGet("explicitUrl", ref);
            if (explicitUrlAsString != null && Boolean.valueOf(explicitUrlAsString)) {
                dataSource.setUrl(this.nullSafeRefAddrStringGet("url", ref));
            }
            dataSource.setPropertiesViaRef(ref);
            return dataSource;
        }
        return null;
    }
    
    private String nullSafeRefAddrStringGet(final String referenceName, final Reference ref) {
        final RefAddr refAddr = ref.get(referenceName);
        final String asString = (refAddr != null) ? ((String)refAddr.getContent()) : null;
        return asString;
    }
}
