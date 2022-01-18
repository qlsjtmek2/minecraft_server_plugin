// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.exceptions;

import java.sql.SQLException;

public class MySQLNonTransientException extends SQLException
{
    public MySQLNonTransientException() {
    }
    
    public MySQLNonTransientException(final String reason, final String SQLState, final int vendorCode) {
        super(reason, SQLState, vendorCode);
    }
    
    public MySQLNonTransientException(final String reason, final String SQLState) {
        super(reason, SQLState);
    }
    
    public MySQLNonTransientException(final String reason) {
        super(reason);
    }
}
