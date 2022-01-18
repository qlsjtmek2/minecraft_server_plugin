// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.resource;

import java.io.IOException;
import java.io.InputStream;

public interface ResourceContent
{
    String getName();
    
    long size();
    
    long lastModified();
    
    InputStream getInputStream() throws IOException;
}
