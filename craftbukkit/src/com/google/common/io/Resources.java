// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.io;

import java.io.OutputStream;
import java.util.List;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.io.IOException;
import com.google.common.base.Preconditions;
import java.io.InputStream;
import java.net.URL;
import com.google.common.annotations.Beta;

@Beta
public final class Resources
{
    public static InputSupplier<InputStream> newInputStreamSupplier(final URL url) {
        Preconditions.checkNotNull(url);
        return new InputSupplier<InputStream>() {
            public InputStream getInput() throws IOException {
                return url.openStream();
            }
        };
    }
    
    public static InputSupplier<InputStreamReader> newReaderSupplier(final URL url, final Charset charset) {
        return CharStreams.newReaderSupplier(newInputStreamSupplier(url), charset);
    }
    
    public static byte[] toByteArray(final URL url) throws IOException {
        return ByteStreams.toByteArray(newInputStreamSupplier(url));
    }
    
    public static String toString(final URL url, final Charset charset) throws IOException {
        return CharStreams.toString(newReaderSupplier(url, charset));
    }
    
    public static <T> T readLines(final URL url, final Charset charset, final LineProcessor<T> callback) throws IOException {
        return CharStreams.readLines(newReaderSupplier(url, charset), callback);
    }
    
    public static List<String> readLines(final URL url, final Charset charset) throws IOException {
        return CharStreams.readLines(newReaderSupplier(url, charset));
    }
    
    public static void copy(final URL from, final OutputStream to) throws IOException {
        ByteStreams.copy(newInputStreamSupplier(from), to);
    }
    
    public static URL getResource(final String resourceName) {
        final URL url = Resources.class.getClassLoader().getResource(resourceName);
        Preconditions.checkArgument(url != null, "resource %s not found.", resourceName);
        return url;
    }
    
    public static URL getResource(final Class<?> contextClass, final String resourceName) {
        final URL url = contextClass.getResource(resourceName);
        Preconditions.checkArgument(url != null, "resource %s relative to %s not found.", resourceName, contextClass.getName());
        return url;
    }
}
