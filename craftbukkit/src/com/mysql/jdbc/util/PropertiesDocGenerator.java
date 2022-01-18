// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.util;

import java.sql.SQLException;
import com.mysql.jdbc.ConnectionPropertiesImpl;

public class PropertiesDocGenerator extends ConnectionPropertiesImpl
{
    public static void main(final String[] args) throws SQLException {
        System.out.println(new PropertiesDocGenerator().exposeAsXml());
    }
}
