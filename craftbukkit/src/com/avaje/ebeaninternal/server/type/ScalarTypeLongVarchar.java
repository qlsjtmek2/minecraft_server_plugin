// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.sql.SQLException;

public class ScalarTypeLongVarchar extends ScalarTypeClob
{
    public ScalarTypeLongVarchar() {
        super(true, -1);
    }
    
    public String read(final DataReader dataReader) throws SQLException {
        return dataReader.getStringFromStream();
    }
}
