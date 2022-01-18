// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.exceptions;

public class MySQLNonTransientConnectionException extends MySQLNonTransientException
{
    public MySQLNonTransientConnectionException() {
    }
    
    public MySQLNonTransientConnectionException(final String reason, final String SQLState, final int vendorCode) {
        super(reason, SQLState, vendorCode);
    }
    
    public MySQLNonTransientConnectionException(final String reason, final String SQLState) {
        super(reason, SQLState);
    }
    
    public MySQLNonTransientConnectionException(final String reason) {
        super(reason);
    }
}
