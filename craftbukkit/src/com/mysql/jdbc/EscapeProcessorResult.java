// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

class EscapeProcessorResult
{
    boolean callingStoredFunction;
    String escapedSql;
    byte usesVariables;
    
    EscapeProcessorResult() {
        this.callingStoredFunction = false;
        this.usesVariables = 0;
    }
}
