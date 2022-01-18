// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.sql.SQLException;

public class ScalarTypeBytesLongVarbinary extends ScalarTypeBytesBase
{
    public ScalarTypeBytesLongVarbinary() {
        super(true, -4);
    }
    
    public byte[] read(final DataReader dataReader) throws SQLException {
        return dataReader.getBinaryBytes();
    }
}
