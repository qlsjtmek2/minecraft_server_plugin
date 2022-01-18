// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.resource;

import java.io.File;

public class FileResourceSource extends AbstractResourceSource implements ResourceSource
{
    String directory;
    String baseDir;
    
    public FileResourceSource(final String directory) {
        this.directory = directory;
        this.baseDir = directory + File.separator;
    }
    
    public FileResourceSource(final File dir) {
        this(dir.getPath());
    }
    
    public String getRealPath() {
        return this.directory;
    }
    
    public ResourceContent getContent(final String entry) {
        final String fullPath = this.baseDir + entry;
        final File f = new File(fullPath);
        if (f.exists()) {
            final FileResourceContent content = new FileResourceContent(f, entry);
            return content;
        }
        return null;
    }
}
