// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import java.io.Serializable;

public class FilterExprPath implements Serializable
{
    private static final long serialVersionUID = -6420905565372842018L;
    private String path;
    
    public FilterExprPath(final String path) {
        this.path = path;
    }
    
    public void trimPath(final int prefixTrim) {
        this.path = this.path.substring(prefixTrim);
    }
    
    public String getPath() {
        return this.path;
    }
}
