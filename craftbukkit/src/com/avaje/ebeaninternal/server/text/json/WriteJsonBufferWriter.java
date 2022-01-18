// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.text.json;

import java.io.IOException;
import com.avaje.ebean.text.TextException;
import java.io.Writer;

public class WriteJsonBufferWriter implements WriteJsonBuffer
{
    private final Writer buffer;
    
    public WriteJsonBufferWriter(final Writer buffer) {
        this.buffer = buffer;
    }
    
    public WriteJsonBufferWriter append(final String content) {
        try {
            this.buffer.write(content);
            return this;
        }
        catch (IOException e) {
            throw new TextException(e);
        }
    }
}
