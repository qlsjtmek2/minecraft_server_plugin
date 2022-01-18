// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import java.io.File;

public class LIndexFileInfo
{
    private final transient File file;
    private final String name;
    private final long length;
    private final long lastModified;
    
    public LIndexFileInfo(final File file) {
        this.file = file;
        this.name = file.getName();
        this.length = file.length();
        this.lastModified = file.lastModified();
    }
    
    public LIndexFileInfo(final String name, final long length, final long lastModified) {
        this.file = null;
        this.name = name;
        this.length = length;
        this.lastModified = lastModified;
    }
    
    public String toString() {
        return this.name + " length[" + this.length + "] lastModified[" + this.lastModified + "]";
    }
    
    public static LIndexFileInfo read(final DataInput dataInput) throws IOException {
        final String name = dataInput.readUTF();
        final long len = dataInput.readLong();
        final long lastMod = dataInput.readLong();
        return new LIndexFileInfo(name, len, lastMod);
    }
    
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.name);
        dataOutput.writeLong(this.length);
        dataOutput.writeLong(this.lastModified);
    }
    
    public boolean exists() {
        return this.file.exists();
    }
    
    public File getFile() {
        return this.file;
    }
    
    public String getName() {
        return this.name;
    }
    
    public long getLength() {
        return this.length;
    }
    
    public long getLastModified() {
        return this.lastModified;
    }
    
    public boolean isMatch(final LIndexFileInfo otherFile) {
        return otherFile.length == this.length && otherFile.lastModified == this.lastModified;
    }
}
