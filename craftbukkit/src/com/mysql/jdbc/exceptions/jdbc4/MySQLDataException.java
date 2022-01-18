// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.exceptions.jdbc4;

import java.sql.SQLDataException;

public class MySQLDataException extends SQLDataException
{
    public MySQLDataException() {
    }
    
    public MySQLDataException(final String reason, final String SQLState, final int vendorCode) {
        super(reason, SQLState, vendorCode);
    }
    
    public MySQLDataException(final String reason, final String SQLState) {
        super(reason, SQLState);
    }
    
    public MySQLDataException(final String reason) {
        super(reason);
    }
}
