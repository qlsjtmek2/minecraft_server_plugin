// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.exceptions;

public class MySQLInvalidAuthorizationSpecException extends MySQLNonTransientException
{
    public MySQLInvalidAuthorizationSpecException() {
    }
    
    public MySQLInvalidAuthorizationSpecException(final String reason, final String SQLState, final int vendorCode) {
        super(reason, SQLState, vendorCode);
    }
    
    public MySQLInvalidAuthorizationSpecException(final String reason, final String SQLState) {
        super(reason, SQLState);
    }
    
    public MySQLInvalidAuthorizationSpecException(final String reason) {
        super(reason);
    }
}
