// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.TextException;
import java.net.URISyntaxException;
import java.net.URI;

public class ScalarTypeURI extends ScalarTypeBaseVarchar<URI>
{
    public ScalarTypeURI() {
        super(URI.class);
    }
    
    public URI convertFromDbString(final String dbValue) {
        try {
            return new URI(dbValue);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException("Error with URI [" + dbValue + "] " + e);
        }
    }
    
    public String convertToDbString(final URI beanValue) {
        return beanValue.toString();
    }
    
    public String formatValue(final URI v) {
        return v.toString();
    }
    
    public URI parse(final String value) {
        try {
            return new URI(value);
        }
        catch (URISyntaxException e) {
            throw new TextException("Error with URI [" + value + "] ", e);
        }
    }
}
