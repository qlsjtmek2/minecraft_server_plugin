// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

public abstract class AbstractResourceSource
{
    public String readString(final ResourceContent content, final int bufSize) throws IOException {
        if (content == null) {
            throw new NullPointerException("content is null?");
        }
        final StringWriter writer = new StringWriter();
        final InputStream is = content.getInputStream();
        final InputStreamReader reader = new InputStreamReader(is);
        final char[] buf = new char[bufSize];
        int len;
        while ((len = reader.read(buf, 0, buf.length)) != -1) {
            writer.write(buf, 0, len);
        }
        reader.close();
        return writer.toString();
    }
    
    public byte[] readBytes(final ResourceContent content, final int bufSize) throws IOException {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        final InputStream is = content.getInputStream();
        final byte[] buf = new byte[bufSize];
        int len;
        while ((len = is.read(buf, 0, buf.length)) != -1) {
            bytes.write(buf, 0, len);
        }
        is.close();
        return bytes.toByteArray();
    }
}
