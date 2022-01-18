// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.exceptions;

public class MySQLSyntaxErrorException extends MySQLNonTransientException
{
    public MySQLSyntaxErrorException() {
    }
    
    public MySQLSyntaxErrorException(final String reason, final String SQLState, final int vendorCode) {
        super(reason, SQLState, vendorCode);
    }
    
    public MySQLSyntaxErrorException(final String reason, final String SQLState) {
        super(reason, SQLState);
    }
    
    public MySQLSyntaxErrorException(final String reason) {
        super(reason);
    }
}
