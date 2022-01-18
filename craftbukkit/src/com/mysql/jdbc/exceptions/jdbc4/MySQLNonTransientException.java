// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.exceptions.jdbc4;

import java.sql.SQLNonTransientException;

public class MySQLNonTransientException extends SQLNonTransientException
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
