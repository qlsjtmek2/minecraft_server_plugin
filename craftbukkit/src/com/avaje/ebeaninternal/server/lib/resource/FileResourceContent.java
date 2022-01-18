// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.resource;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.io.File;

public class FileResourceContent implements ResourceContent
{
    File file;
    String entryName;
    
    public FileResourceContent(final File file, final String entryName) {
        this.file = file;
        this.entryName = entryName;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[").append(this.getName());
        sb.append("] size[").append(this.size());
        sb.append("] lastModified[").append(new Date(this.lastModified()));
        sb.append("]");
        return sb.toString();
    }
    
    public String getName() {
        return this.entryName;
    }
    
    public long lastModified() {
        return this.file.lastModified();
    }
    
    public long size() {
        return this.file.length();
    }
    
    public InputStream getInputStream() throws IOException {
        final FileInputStream is = new FileInputStream(this.file);
        return is;
    }
}
