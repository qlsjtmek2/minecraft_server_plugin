// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.exceptions;

public class MySQLTransientConnectionException extends MySQLTransientException
{
    public MySQLTransientConnectionException(final String reason, final String SQLState, final int vendorCode) {
        super(reason, SQLState, vendorCode);
    }
    
    public MySQLTransientConnectionException(final String reason, final String SQLState) {
        super(reason, SQLState);
    }
    
    public MySQLTransientConnectionException(final String reason) {
        super(reason);
    }
    
    public MySQLTransientConnectionException() {
    }
}
