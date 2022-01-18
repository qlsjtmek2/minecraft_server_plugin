// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLException;

public class NdbLoadBalanceExceptionChecker extends StandardLoadBalanceExceptionChecker
{
    public boolean shouldExceptionTriggerFailover(final SQLException ex) {
        return super.shouldExceptionTriggerFailover(ex) || this.checkNdbException(ex);
    }
    
    private boolean checkNdbException(final SQLException ex) {
        return ex.getMessage().startsWith("Lock wait timeout exceeded") || (ex.getMessage().startsWith("Got temporary error") && ex.getMessage().endsWith("from NDB"));
    }
}
