// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

import java.sql.PreparedStatement;

public interface PstmtDelegate
{
    PreparedStatement unwrap(final PreparedStatement p0);
}
