// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.jdbc;

import com.avaje.ebeaninternal.server.lib.sql.ExtendedPreparedStatement;
import java.sql.PreparedStatement;
import com.avaje.ebean.config.PstmtDelegate;

public class StandardPstmtDelegate implements PstmtDelegate
{
    public PreparedStatement unwrap(final PreparedStatement pstmt) {
        return ((ExtendedPreparedStatement)pstmt).getDelegate();
    }
}
