// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.exceptions;

import java.sql.SQLException;

public class MySQLTransientException extends SQLException
{
    public MySQLTransientException(final String reason, final String SQLState, final int vendorCode) {
        super(reason, SQLState, vendorCode);
    }
    
    public MySQLTransientException(final String reason, final String SQLState) {
        super(reason, SQLState);
    }
    
    public MySQLTransientException(final String reason) {
        super(reason);
    }
    
    public MySQLTransientException() {
    }
}
