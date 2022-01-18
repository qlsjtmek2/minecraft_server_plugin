// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.exceptions;

public class MySQLStatementCancelledException extends MySQLNonTransientException
{
    public MySQLStatementCancelledException(final String reason, final String SQLState, final int vendorCode) {
        super(reason, SQLState, vendorCode);
    }
    
    public MySQLStatementCancelledException(final String reason, final String SQLState) {
        super(reason, SQLState);
    }
    
    public MySQLStatementCancelledException(final String reason) {
        super(reason);
    }
    
    public MySQLStatementCancelledException() {
        super("Statement cancelled due to client request");
    }
}
