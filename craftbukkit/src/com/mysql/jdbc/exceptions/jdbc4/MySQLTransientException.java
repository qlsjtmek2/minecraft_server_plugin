// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.exceptions.jdbc4;

import java.sql.SQLTransientException;

public class MySQLTransientException extends SQLTransientException
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
