// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.integration.jboss;

import java.sql.SQLException;
import org.jboss.resource.adapter.jdbc.vendor.MySQLExceptionSorter;

public final class ExtendedMysqlExceptionSorter extends MySQLExceptionSorter
{
    public boolean isExceptionFatal(final SQLException ex) {
        final String sqlState = ex.getSQLState();
        return (sqlState != null && sqlState.startsWith("08")) || super.isExceptionFatal(ex);
    }
}
