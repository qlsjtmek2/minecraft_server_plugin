// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.DataTruncation;

public class MysqlDataTruncation extends DataTruncation
{
    private String message;
    private int vendorErrorCode;
    
    public MysqlDataTruncation(final String message, final int index, final boolean parameter, final boolean read, final int dataSize, final int transferSize, final int vendorErrorCode) {
        super(index, parameter, read, dataSize, transferSize);
        this.message = message;
        this.vendorErrorCode = vendorErrorCode;
    }
    
    public int getErrorCode() {
        return this.vendorErrorCode;
    }
    
    public String getMessage() {
        return super.getMessage() + ": " + this.message;
    }
}
