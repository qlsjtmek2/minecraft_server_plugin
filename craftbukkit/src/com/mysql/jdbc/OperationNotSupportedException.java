// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLException;

class OperationNotSupportedException extends SQLException
{
    OperationNotSupportedException() {
        super(Messages.getString("RowDataDynamic.10"), "S1009");
    }
}
