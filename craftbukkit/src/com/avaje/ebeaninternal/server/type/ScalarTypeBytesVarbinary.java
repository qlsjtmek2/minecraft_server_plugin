// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.sql.SQLException;

public class ScalarTypeBytesVarbinary extends ScalarTypeBytesBase
{
    public ScalarTypeBytesVarbinary() {
        super(true, -3);
    }
    
    public byte[] read(final DataReader dataReader) throws SQLException {
        return dataReader.getBytes();
    }
}
