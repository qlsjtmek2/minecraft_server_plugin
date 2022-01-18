// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.text.json;

public interface ReadJsonSource
{
    char nextChar(final String p0);
    
    void ignoreWhiteSpace();
    
    void back();
    
    int pos();
    
    String getErrorHelp();
}
