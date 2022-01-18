// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.util;

import com.mysql.jdbc.SQLError;

public class ErrorMappingsDocGenerator
{
    public static void main(final String[] args) throws Exception {
        SQLError.dumpSqlStatesMappingsAsXml();
    }
}
