// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.resource;

import java.net.URL;
import java.net.MalformedURLException;
import com.avaje.ebeaninternal.server.lib.util.GeneralException;
import javax.servlet.ServletContext;

public class UrlResourceSource extends AbstractResourceSource implements ResourceSource
{
    ServletContext sc;
    String basePath;
    String realPath;
    
    public UrlResourceSource(final ServletContext sc, final String basePath) {
        this.sc = sc;
        if (basePath == null) {
            this.basePath = "/";
        }
        else {
            this.basePath = "/" + basePath + "/";
        }
        this.realPath = sc.getRealPath(basePath);
    }
    
    public String getRealPath() {
        return this.realPath;
    }
    
    public ResourceContent getContent(final String entry) {
        try {
            final URL url = this.sc.getResource(this.basePath + entry);
            if (url != null) {
                return new UrlResourceContent(url, entry);
            }
            return null;
        }
        catch (MalformedURLException ex) {
            throw new GeneralException(ex);
        }
    }
}
