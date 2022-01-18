// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import java.util.Collections;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public abstract class HttpHeaders implements Iterable<Map.Entry<String, String>>
{
    public static final HttpHeaders EMPTY_HEADERS;
    
    public static boolean isKeepAlive(final HttpMessage message) {
        final String connection = message.headers().get("Connection");
        if ("close".equalsIgnoreCase(connection)) {
            return false;
        }
        if (message.getProtocolVersion().isKeepAliveDefault()) {
            return !"close".equalsIgnoreCase(connection);
        }
        return "keep-alive".equalsIgnoreCase(connection);
    }
    
    public static void setKeepAlive(final HttpMessage message, final boolean keepAlive) {
        final HttpHeaders h = message.headers();
        if (message.getProtocolVersion().isKeepAliveDefault()) {
            if (keepAlive) {
                h.remove("Connection");
            }
            else {
                h.set("Connection", "close");
            }
        }
        else if (keepAlive) {
            h.set("Connection", "keep-alive");
        }
        else {
            h.remove("Connection");
        }
    }
    
    public static String getHeader(final HttpMessage message, final String name) {
        return message.headers().get(name);
    }
    
    public static String getHeader(final HttpMessage message, final String name, final String defaultValue) {
        final String value = message.headers().get(name);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
    
    public static void setHeader(final HttpMessage message, final String name, final Object value) {
        message.headers().set(name, value);
    }
    
    public static void setHeader(final HttpMessage message, final String name, final Iterable<?> values) {
        message.headers().set(name, values);
    }
    
    public static void addHeader(final HttpMessage message, final String name, final Object value) {
        message.headers().add(name, value);
    }
    
    public static void removeHeader(final HttpMessage message, final String name) {
        message.headers().remove(name);
    }
    
    public static void clearHeaders(final HttpMessage message) {
        message.headers().clear();
    }
    
    public static int getIntHeader(final HttpMessage message, final String name) {
        final String value = getHeader(message, name);
        if (value == null) {
            throw new NumberFormatException("header not found: " + name);
        }
        return Integer.parseInt(value);
    }
    
    public static int getIntHeader(final HttpMessage message, final String name, final int defaultValue) {
        final String value = getHeader(message, name);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    public static void setIntHeader(final HttpMessage message, final String name, final int value) {
        message.headers().set(name, value);
    }
    
    public static void setIntHeader(final HttpMessage message, final String name, final Iterable<Integer> values) {
        message.headers().set(name, values);
    }
    
    public static void addIntHeader(final HttpMessage message, final String name, final int value) {
        message.headers().add(name, value);
    }
    
    public static Date getDateHeader(final HttpMessage message, final String name) throws ParseException {
        final String value = getHeader(message, name);
        if (value == null) {
            throw new ParseException("header not found: " + name, 0);
        }
        return new HttpHeaderDateFormat().parse(value);
    }
    
    public static Date getDateHeader(final HttpMessage message, final String name, final Date defaultValue) {
        final String value = getHeader(message, name);
        if (value == null) {
            return defaultValue;
        }
        try {
            return new HttpHeaderDateFormat().parse(value);
        }
        catch (ParseException e) {
            return defaultValue;
        }
    }
    
    public static void setDateHeader(final HttpMessage message, final String name, final Date value) {
        if (value != null) {
            message.headers().set(name, new HttpHeaderDateFormat().format(value));
        }
        else {
            message.headers().set(name, null);
        }
    }
    
    public static void setDateHeader(final HttpMessage message, final String name, final Iterable<Date> values) {
        message.headers().set(name, values);
    }
    
    public static void addDateHeader(final HttpMessage message, final String name, final Date value) {
        message.headers().add(name, value);
    }
    
    public static long getContentLength(final HttpMessage message) {
        final String value = getHeader(message, "Content-Length");
        if (value != null) {
            return Long.parseLong(value);
        }
        final long webSocketContentLength = getWebSocketContentLength(message);
        if (webSocketContentLength >= 0L) {
            return webSocketContentLength;
        }
        throw new NumberFormatException("header not found: Content-Length");
    }
    
    public static long getContentLength(final HttpMessage message, final long defaultValue) {
        final String contentLength = message.headers().get("Content-Length");
        if (contentLength != null) {
            try {
                return Long.parseLong(contentLength);
            }
            catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        final long webSocketContentLength = getWebSocketContentLength(message);
        if (webSocketContentLength >= 0L) {
            return webSocketContentLength;
        }
        return defaultValue;
    }
    
    private static int getWebSocketContentLength(final HttpMessage message) {
        final HttpHeaders h = message.headers();
        if (message instanceof HttpRequest) {
            final HttpRequest req = (HttpRequest)message;
            if (HttpMethod.GET.equals(req.getMethod()) && h.contains("Sec-WebSocket-Key1") && h.contains("Sec-WebSocket-Key2")) {
                return 8;
            }
        }
        else if (message instanceof HttpResponse) {
            final HttpResponse res = (HttpResponse)message;
            if (res.getStatus().code() == 101 && h.contains("Sec-WebSocket-Origin") && h.contains("Sec-WebSocket-Location")) {
                return 16;
            }
        }
        return -1;
    }
    
    public static void setContentLength(final HttpMessage message, final long length) {
        message.headers().set("Content-Length", length);
    }
    
    public static String getHost(final HttpMessage message) {
        return message.headers().get("Host");
    }
    
    public static String getHost(final HttpMessage message, final String defaultValue) {
        return getHeader(message, "Host", defaultValue);
    }
    
    public static void setHost(final HttpMessage message, final String value) {
        message.headers().set("Host", value);
    }
    
    public static Date getDate(final HttpMessage message) throws ParseException {
        return getDateHeader(message, "Date");
    }
    
    public static Date getDate(final HttpMessage message, final Date defaultValue) {
        return getDateHeader(message, "Date", defaultValue);
    }
    
    public static void setDate(final HttpMessage message, final Date value) {
        if (value != null) {
            message.headers().set("Date", new HttpHeaderDateFormat().format(value));
        }
        else {
            message.headers().set("Date", null);
        }
    }
    
    public static boolean is100ContinueExpected(final HttpMessage message) {
        if (!(message instanceof HttpRequest)) {
            return false;
        }
        if (message.getProtocolVersion().compareTo(HttpVersion.HTTP_1_1) < 0) {
            return false;
        }
        final String value = message.headers().get("Expect");
        if (value == null) {
            return false;
        }
        if ("100-continue".equalsIgnoreCase(value)) {
            return true;
        }
        for (final String v : message.headers().getAll("Expect")) {
            if ("100-continue".equalsIgnoreCase(v)) {
                return true;
            }
        }
        return false;
    }
    
    public static void set100ContinueExpected(final HttpMessage message) {
        set100ContinueExpected(message, true);
    }
    
    public static void set100ContinueExpected(final HttpMessage message, final boolean set) {
        if (set) {
            message.headers().set("Expect", "100-continue");
        }
        else {
            message.headers().remove("Expect");
        }
    }
    
    static void validateHeaderName(final String headerName) {
        if (headerName == null) {
            throw new NullPointerException("Header names cannot be null");
        }
        int index = 0;
        while (index < headerName.length()) {
            final char character = headerName.charAt(index);
            if (character > '\u007f') {
                throw new IllegalArgumentException("Header name cannot contain non-ASCII characters: " + headerName);
            }
            switch (character) {
                case '\t':
                case '\n':
                case '\u000b':
                case '\f':
                case '\r':
                case ' ':
                case ',':
                case ':':
                case ';':
                case '=': {
                    throw new IllegalArgumentException("Header name cannot contain the following prohibited characters: =,;: \\t\\r\\n\\v\\f: " + headerName);
                }
                default: {
                    ++index;
                    continue;
                }
            }
        }
    }
    
    static void validateHeaderValue(final String headerValue) {
        if (headerValue == null) {
            throw new NullPointerException("Header values cannot be null");
        }
        int state = 0;
        int index = 0;
        while (index < headerValue.length()) {
            final char character = headerValue.charAt(index);
            switch (character) {
                case '\u000b': {
                    throw new IllegalArgumentException("Header value contains a prohibited character '\\v': " + headerValue);
                }
                case '\f': {
                    throw new IllegalArgumentException("Header value contains a prohibited character '\\f': " + headerValue);
                }
                default: {
                    Label_0293: {
                        switch (state) {
                            case 0: {
                                switch (character) {
                                    case '\r': {
                                        state = 1;
                                        break;
                                    }
                                    case '\n': {
                                        state = 2;
                                        break;
                                    }
                                }
                                break;
                            }
                            case 1: {
                                switch (character) {
                                    case '\n': {
                                        state = 2;
                                        break Label_0293;
                                    }
                                    default: {
                                        throw new IllegalArgumentException("Only '\\n' is allowed after '\\r': " + headerValue);
                                    }
                                }
                                break;
                            }
                            case 2: {
                                switch (character) {
                                    case '\t':
                                    case ' ': {
                                        state = 0;
                                        break Label_0293;
                                    }
                                    default: {
                                        throw new IllegalArgumentException("Only ' ' and '\\t' are allowed after '\\n': " + headerValue);
                                    }
                                }
                                break;
                            }
                        }
                    }
                    ++index;
                    continue;
                }
            }
        }
        if (state != 0) {
            throw new IllegalArgumentException("Header value must not end with '\\r' or '\\n':" + headerValue);
        }
    }
    
    public static boolean isTransferEncodingChunked(final HttpMessage message) {
        final List<String> transferEncodingHeaders = message.headers().getAll("Transfer-Encoding");
        if (transferEncodingHeaders.isEmpty()) {
            return false;
        }
        for (final String value : transferEncodingHeaders) {
            if (value.equalsIgnoreCase("chunked")) {
                return true;
            }
        }
        return false;
    }
    
    public static void removeTransferEncodingChunked(final HttpMessage m) {
        final List<String> values = m.headers().getAll("Transfer-Encoding");
        values.remove("chunked");
        if (values.isEmpty()) {
            m.headers().remove("Transfer-Encoding");
        }
        else {
            m.headers().set("Transfer-Encoding", values);
        }
    }
    
    public static void setTransferEncodingChunked(final HttpMessage m) {
        addHeader(m, "Transfer-Encoding", "chunked");
        removeHeader(m, "Content-Length");
    }
    
    public static boolean isContentLengthSet(final HttpMessage m) {
        final List<String> contentLength = m.headers().getAll("Content-Length");
        return !contentLength.isEmpty();
    }
    
    public abstract String get(final String p0);
    
    public abstract List<String> getAll(final String p0);
    
    public abstract List<Map.Entry<String, String>> entries();
    
    public abstract boolean contains(final String p0);
    
    public abstract boolean isEmpty();
    
    public abstract Set<String> names();
    
    public abstract HttpHeaders add(final String p0, final Object p1);
    
    public abstract HttpHeaders add(final String p0, final Iterable<?> p1);
    
    public HttpHeaders add(final HttpHeaders headers) {
        if (headers == null) {
            throw new NullPointerException("headers");
        }
        for (final Map.Entry<String, String> e : headers) {
            this.add(e.getKey(), e.getValue());
        }
        return this;
    }
    
    public abstract HttpHeaders set(final String p0, final Object p1);
    
    public abstract HttpHeaders set(final String p0, final Iterable<?> p1);
    
    public HttpHeaders set(final HttpHeaders headers) {
        if (headers == null) {
            throw new NullPointerException("headers");
        }
        this.clear();
        for (final Map.Entry<String, String> e : headers) {
            this.add(e.getKey(), e.getValue());
        }
        return this;
    }
    
    public abstract HttpHeaders remove(final String p0);
    
    public abstract HttpHeaders clear();
    
    static {
        EMPTY_HEADERS = new HttpHeaders() {
            @Override
            public String get(final String name) {
                return null;
            }
            
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
            public HttpHeaders add(final String name, final Object value) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public HttpHeaders add(final String name, final Iterable<?> values) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public HttpHeaders set(final String name, final Object value) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public HttpHeaders set(final String name, final Iterable<?> values) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public HttpHeaders remove(final String name) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public HttpHeaders clear() {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public Iterator<Map.Entry<String, String>> iterator() {
                return this.entries().iterator();
            }
        };
    }
    
    public static final class Names
    {
        public static final String ACCEPT = "Accept";
        public static final String ACCEPT_CHARSET = "Accept-Charset";
        public static final String ACCEPT_ENCODING = "Accept-Encoding";
        public static final String ACCEPT_LANGUAGE = "Accept-Language";
        public static final String ACCEPT_RANGES = "Accept-Ranges";
        public static final String ACCEPT_PATCH = "Accept-Patch";
        public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
        public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
        public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
        public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
        public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
        public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
        public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
        public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
        public static final String AGE = "Age";
        public static final String ALLOW = "Allow";
        public static final String AUTHORIZATION = "Authorization";
        public static final String CACHE_CONTROL = "Cache-Control";
        public static final String CONNECTION = "Connection";
        public static final String CONTENT_BASE = "Content-Base";
        public static final String CONTENT_ENCODING = "Content-Encoding";
        public static final String CONTENT_LANGUAGE = "Content-Language";
        public static final String CONTENT_LENGTH = "Content-Length";
        public static final String CONTENT_LOCATION = "Content-Location";
        public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
        public static final String CONTENT_MD5 = "Content-MD5";
        public static final String CONTENT_RANGE = "Content-Range";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String COOKIE = "Cookie";
        public static final String DATE = "Date";
        public static final String ETAG = "ETag";
        public static final String EXPECT = "Expect";
        public static final String EXPIRES = "Expires";
        public static final String FROM = "From";
        public static final String HOST = "Host";
        public static final String IF_MATCH = "If-Match";
        public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
        public static final String IF_NONE_MATCH = "If-None-Match";
        public static final String IF_RANGE = "If-Range";
        public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
        public static final String LAST_MODIFIED = "Last-Modified";
        public static final String LOCATION = "Location";
        public static final String MAX_FORWARDS = "Max-Forwards";
        public static final String ORIGIN = "Origin";
        public static final String PRAGMA = "Pragma";
        public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
        public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
        public static final String RANGE = "Range";
        public static final String REFERER = "Referer";
        public static final String RETRY_AFTER = "Retry-After";
        public static final String SEC_WEBSOCKET_KEY1 = "Sec-WebSocket-Key1";
        public static final String SEC_WEBSOCKET_KEY2 = "Sec-WebSocket-Key2";
        public static final String SEC_WEBSOCKET_LOCATION = "Sec-WebSocket-Location";
        public static final String SEC_WEBSOCKET_ORIGIN = "Sec-WebSocket-Origin";
        public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
        public static final String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
        public static final String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
        public static final String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";
        public static final String SERVER = "Server";
        public static final String SET_COOKIE = "Set-Cookie";
        public static final String SET_COOKIE2 = "Set-Cookie2";
        public static final String TE = "TE";
        public static final String TRAILER = "Trailer";
        public static final String TRANSFER_ENCODING = "Transfer-Encoding";
        public static final String UPGRADE = "Upgrade";
        public static final String USER_AGENT = "User-Agent";
        public static final String VARY = "Vary";
        public static final String VIA = "Via";
        public static final String WARNING = "Warning";
        public static final String WEBSOCKET_LOCATION = "WebSocket-Location";
        public static final String WEBSOCKET_ORIGIN = "WebSocket-Origin";
        public static final String WEBSOCKET_PROTOCOL = "WebSocket-Protocol";
        public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    }
    
    public static final class Values
    {
        public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
        public static final String BASE64 = "base64";
        public static final String BINARY = "binary";
        public static final String BOUNDARY = "boundary";
        public static final String BYTES = "bytes";
        public static final String CHARSET = "charset";
        public static final String CHUNKED = "chunked";
        public static final String CLOSE = "close";
        public static final String COMPRESS = "compress";
        public static final String CONTINUE = "100-continue";
        public static final String DEFLATE = "deflate";
        public static final String GZIP = "gzip";
        public static final String IDENTITY = "identity";
        public static final String KEEP_ALIVE = "keep-alive";
        public static final String MAX_AGE = "max-age";
        public static final String MAX_STALE = "max-stale";
        public static final String MIN_FRESH = "min-fresh";
        public static final String MULTIPART_FORM_DATA = "multipart/form-data";
        public static final String MUST_REVALIDATE = "must-revalidate";
        public static final String NO_CACHE = "no-cache";
        public static final String NO_STORE = "no-store";
        public static final String NO_TRANSFORM = "no-transform";
        public static final String NONE = "none";
        public static final String ONLY_IF_CACHED = "only-if-cached";
        public static final String PRIVATE = "private";
        public static final String PROXY_REVALIDATE = "proxy-revalidate";
        public static final String PUBLIC = "public";
        public static final String QUOTED_PRINTABLE = "quoted-printable";
        public static final String S_MAXAGE = "s-maxage";
        public static final String TRAILERS = "trailers";
        public static final String UPGRADE = "Upgrade";
        public static final String WEBSOCKET = "WebSocket";
    }
}
