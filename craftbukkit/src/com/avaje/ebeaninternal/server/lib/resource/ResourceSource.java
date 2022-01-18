// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.resource;

import java.io.IOException;

public interface ResourceSource
{
    String getRealPath();
    
    ResourceContent getContent(final String p0);
    
    String readString(final ResourceContent p0, final int p1) throws IOException;
    
    byte[] readBytes(final ResourceContent p0, final int p1) throws IOException;
}
