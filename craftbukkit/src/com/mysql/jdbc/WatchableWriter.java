// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.io.CharArrayWriter;

class WatchableWriter extends CharArrayWriter
{
    private WriterWatcher watcher;
    
    public void close() {
        super.close();
        if (this.watcher != null) {
            this.watcher.writerClosed(this);
        }
    }
    
    public void setWatcher(final WriterWatcher watcher) {
        this.watcher = watcher;
    }
}
