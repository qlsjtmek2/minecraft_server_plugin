// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import java.util.Collections;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpMethod;
import java.util.Map;

public abstract class SpdyHeaders implements Iterable<Map.Entry<String, String>>
{
    public static final SpdyHeaders EMPTY_HEADERS;
    
    public static String getHeader(final SpdyHeaderBlock block, final String name) {
        return block.headers().get(name);
    }
    
    public static String getHeader(final SpdyHeaderBlock block, final String name, final String defaultValue) {
        final String value = block.headers().get(name);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
    
    public static void setHeader(final SpdyHeaderBlock block, final String name, final Object value) {
        block.headers().set(name, value);
    }
    
    public static void setHeader(final SpdyHeaderBlock block, final String name, final Iterable<?> values) {
        block.headers().set(name, values);
    }
    
    public static void addHeader(final SpdyHeaderBlock block, final String name, final Object value) {
        block.headers().add(name, value);
    }
    
    public static void removeHost(final SpdyHeaderBlock block) {
        block.headers().remove(":host");
    }
    
    public static String getHost(final SpdyHeaderBlock block) {
        return block.headers().get(":host");
    }
    
    public static void setHost(final SpdyHeaderBlock block, final String host) {
        block.headers().set(":host", host);
    }
    
    public static void removeMethod(final int spdyVersion, final SpdyHeaderBlock block) {
        if (spdyVersion < 3) {
            block.headers().remove("method");
        }
        else {
            block.headers().remove(":method");
        }
    }
    
    public static HttpMethod getMethod(final int spdyVersion, final SpdyHeaderBlock block) {
        try {
            if (spdyVersion < 3) {
                return HttpMethod.valueOf(block.headers().get("method"));
            }
            return HttpMethod.valueOf(block.headers().get(":method"));
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static void setMethod(final int spdyVersion, final SpdyHeaderBlock block, final HttpMethod method) {
        if (spdyVersion < 3) {
            block.headers().set("method", method.name());
        }
        else {
            block.headers().set(":method", method.name());
        }
    }
    
    public static void removeScheme(final int spdyVersion, final SpdyHeaderBlock block) {
        if (spdyVersion < 2) {
            block.headers().remove("scheme");
        }
        else {
            block.headers().remove(":scheme");
        }
    }
    
    public static String getScheme(final int spdyVersion, final SpdyHeaderBlock block) {
        if (spdyVersion < 3) {
            return block.headers().get("scheme");
        }
        return block.headers().get(":scheme");
    }
    
    public static void setScheme(final int spdyVersion, final SpdyHeaderBlock block, final String scheme) {
        if (spdyVersion < 3) {
            block.headers().set("scheme", scheme);
        }
        else {
            block.headers().set(":scheme", scheme);
        }
    }
    
    public static void removeStatus(final int spdyVersion, final SpdyHeaderBlock block) {
        if (spdyVersion < 3) {
            block.headers().remove("status");
        }
        else {
            block.headers().remove(":status");
        }
    }
    
    public static HttpResponseStatus getStatus(final int spdyVersion, final SpdyHeaderBlock block) {
        try {
            String status;
            if (spdyVersion < 3) {
                status = block.headers().get("status");
            }
            else {
                status = block.headers().get(":status");
            }
            final int space = status.indexOf(32);
            if (space == -1) {
                return HttpResponseStatus.valueOf(Integer.parseInt(status));
            }
            final int code = Integer.parseInt(status.substring(0, space));
            final String reasonPhrase = status.substring(space + 1);
            final HttpResponseStatus responseStatus = HttpResponseStatus.valueOf(code);
            if (responseStatus.reasonPhrase().equals(reasonPhrase)) {
                return responseStatus;
            }
            return new HttpResponseStatus(code, reasonPhrase);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static void setStatus(final int spdyVersion, final SpdyHeaderBlock block, final HttpResponseStatus status) {
        if (spdyVersion < 3) {
            block.headers().set("status", status.toString());
        }
        else {
            block.headers().set(":status", status.toString());
        }
    }
    
    public static void removeUrl(final int spdyVersion, final SpdyHeaderBlock block) {
        if (spdyVersion < 3) {
            block.headers().remove("url");
        }
        else {
            block.headers().remove(":path");
        }
    }
    
    public static String getUrl(final int spdyVersion, final SpdyHeaderBlock block) {
        if (spdyVersion < 3) {
            return block.headers().get("url");
        }
        return block.headers().get(":path");
    }
    
    public static void setUrl(final int spdyVersion, final SpdyHeaderBlock block, final String path) {
        if (spdyVersion < 3) {
            block.headers().set("url", path);
        }
        else {
            block.headers().set(":path", path);
        }
    }
    
    public static void removeVersion(final int spdyVersion, final SpdyHeaderBlock block) {
        if (spdyVersion < 3) {
            block.headers().remove("version");
        }
        else {
            block.headers().remove(":version");
        }
    }
    
    public static HttpVersion getVersion(final int spdyVersion, final SpdyHeaderBlock block) {
        try {
            if (spdyVersion < 3) {
                return HttpVersion.valueOf(block.headers().get("version"));
            }
            return HttpVersion.valueOf(block.headers().get(":version"));
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static void setVersion(final int spdyVersion, final SpdyHeaderBlock block, final HttpVersion httpVersion) {
        if (spdyVersion < 3) {
            block.headers().set("version", httpVersion.text());
        }
        else {
            block.headers().set(":version", httpVersion.text());
        }
    }
    
    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return this.entries().iterator();
    }
    
    public abstract String get(final String p0);
    
    public abstract List<String> getAll(final String p0);
    
    public abstract List<Map.Entry<String, String>> entries();
    
    public abstract boolean contains(final String p0);
    
    public abstract Set<String> names();
    
    public abstract SpdyHeaders add(final String p0, final Object p1);
    
    public abstract SpdyHeaders add(final String p0, final Iterable<?> p1);
    
    public abstract SpdyHeaders set(final String p0, final Object p1);
    
    public abstract SpdyHeaders set(final String p0, final Iterable<?> p1);
    
    public abstract SpdyHeaders remove(final String p0);
    
    public abstract SpdyHeaders clear();
    
    public abstract boolean isEmpty();
    
    static {
        EMPTY_HEADERS = new SpdyHeaders() {
            @Override
            public List<String> getAll(final String name) {
                return Collections.emptyList();
            }
            
            @Override
            public List<Map.Entry<String, String>> entries() {
                return Collections.emptyList();
            }
            
            @Override
            public boolean contains(final String name) {
                return false;
            }
            
            @Override
            public boolean isEmpty() {
                return true;
            }
            
            @Override
            public Set<String> names() {
                return Collections.emptySet();
            }
            
            @Override
            public SpdyHeaders add(final String name, final Object value) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public SpdyHeaders add(final String name, final Iterable<?> values) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public SpdyHeaders set(final String name, final Object value) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public SpdyHeaders set(final String name, final Iterable<?> values) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public SpdyHeaders remove(final String name) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public SpdyHeaders clear() {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public Iterator<Map.Entry<String, String>> iterator() {
                return this.entries().iterator();
            }
            
            @Override
            public String get(final String name) {
                return null;
            }
        };
    }
    
    public static final class HttpNames
    {
        public static final String HOST = ":host";
        public static final String METHOD = ":method";
        public static final String PATH = ":path";
        public static final String SCHEME = ":scheme";
        public static final String STATUS = ":status";
        public static final String VERSION = ":version";
    }
    
    public static final class Spdy2HttpNames
    {
        public static final String METHOD = "method";
        public static final String SCHEME = "scheme";
        public static final String STATUS = "status";
        public static final String URL = "url";
        public static final String VERSION = "version";
    }
}
