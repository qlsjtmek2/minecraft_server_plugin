// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.resource;

import java.io.File;
import com.avaje.ebeaninternal.server.lib.resource.ResourceSource;

public class ResourceManager
{
    final ResourceSource resourceSource;
    final File autofetchDir;
    
    public ResourceManager(final ResourceSource resourceSource, final File autofetchDir) {
        this.resourceSource = resourceSource;
        this.autofetchDir = autofetchDir;
    }
    
    public ResourceSource getResourceSource() {
        return this.resourceSource;
    }
    
    public File getAutofetchDirectory() {
        return this.autofetchDir;
    }
}
