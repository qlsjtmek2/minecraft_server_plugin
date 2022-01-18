// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.sql.SQLException;

public class ScalarTypeBytesBlob extends ScalarTypeBytesBase
{
    public ScalarTypeBytesBlob() {
        super(true, 2004);
    }
    
    public byte[] read(final DataReader dataReader) throws SQLException {
        return dataReader.getBlobBytes();
    }
}
