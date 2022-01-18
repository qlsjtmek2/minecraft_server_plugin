// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.TextException;
import java.net.MalformedURLException;
import java.net.URL;

public class ScalarTypeURL extends ScalarTypeBaseVarchar<URL>
{
    public ScalarTypeURL() {
        super(URL.class);
    }
    
    public URL convertFromDbString(final String dbValue) {
        try {
            return new URL(dbValue);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Error with URL [" + dbValue + "] " + e);
        }
    }
    
    public String convertToDbString(final URL beanValue) {
        return this.formatValue(beanValue);
    }
    
    public String formatValue(final URL v) {
        return v.toString();
    }
    
    public URL parse(final String value) {
        try {
            return new URL(value);
        }
        catch (MalformedURLException e) {
            throw new TextException(e);
        }
    }
}
