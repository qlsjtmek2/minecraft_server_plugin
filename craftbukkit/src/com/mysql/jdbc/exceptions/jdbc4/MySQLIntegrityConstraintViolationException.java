// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.exceptions.jdbc4;

import java.sql.SQLIntegrityConstraintViolationException;

public class MySQLIntegrityConstraintViolationException extends SQLIntegrityConstraintViolationException
{
    public MySQLIntegrityConstraintViolationException() {
    }
    
    public MySQLIntegrityConstraintViolationException(final String reason, final String SQLState, final int vendorCode) {
        super(reason, SQLState, vendorCode);
    }
    
    public MySQLIntegrityConstraintViolationException(final String reason, final String SQLState) {
        super(reason, SQLState);
    }
    
    public MySQLIntegrityConstraintViolationException(final String reason) {
        super(reason);
    }
}
