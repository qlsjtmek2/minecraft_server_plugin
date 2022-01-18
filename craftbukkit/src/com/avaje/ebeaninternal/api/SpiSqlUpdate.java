// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import com.avaje.ebean.SqlUpdate;

public interface SpiSqlUpdate extends SqlUpdate
{
    BindParams getBindParams();
}
