// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpVersion implements Comparable<HttpVersion>
{
    private static final Pattern VERSION_PATTERN;
    public static final HttpVersion HTTP_1_0;
    public static final HttpVersion HTTP_1_1;
    private final String protocolName;
    private final int majorVersion;
    private final int minorVersion;
    private final String text;
    private final boolean keepAliveDefault;
    
    public static HttpVersion valueOf(String text) {
        if (text == null) {
            throw new NullPointerException("text");
        }
        text = text.trim().toUpperCase();
        if ("HTTP/1.1".equals(text)) {
            return HttpVersion.HTTP_1_1;
        }
        if ("HTTP/1.0".equals(text)) {
            return HttpVersion.HTTP_1_0;
        }
        return new HttpVersion(text, true);
    }
    
    public HttpVersion(String text, final boolean keepAliveDefault) {
        if (text == null) {
            throw new NullPointerException("text");
        }
        text = text.trim().toUpperCase();
        if (text.isEmpty()) {
            throw new IllegalArgumentException("empty text");
        }
        final Matcher m = HttpVersion.VERSION_PATTERN.matcher(text);
        if (!m.matches()) {
            throw new IllegalArgumentException("invalid version format: " + text);
        }
        this.protocolName = m.group(1);
        this.majorVersion = Integer.parseInt(m.group(2));
        this.minorVersion = Integer.parseInt(m.group(3));
        this.text = this.protocolName + '/' + this.majorVersion + '.' + this.minorVersion;
        this.keepAliveDefault = keepAliveDefault;
    }
    
    public HttpVersion(String protocolName, final int majorVersion, final int minorVersion, final boolean keepAliveDefault) {
        if (protocolName == null) {
            throw new NullPointerException("protocolName");
        }
        protocolName = protocolName.trim().toUpperCase();
        if (protocolName.isEmpty()) {
            throw new IllegalArgumentException("empty protocolName");
        }
        for (int i = 0; i < protocolName.length(); ++i) {
            if (Character.isISOControl(protocolName.charAt(i)) || Character.isWhitespace(protocolName.charAt(i))) {
                throw new IllegalArgumentException("invalid character in protocolName");
            }
        }
        if (majorVersion < 0) {
            throw new IllegalArgumentException("negative majorVersion");
        }
        if (minorVersion < 0) {
            throw new IllegalArgumentException("negative minorVersion");
        }
        this.protocolName = protocolName;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.text = protocolName + '/' + majorVersion + '.' + minorVersion;
        this.keepAliveDefault = keepAliveDefault;
    }
    
    public String protocolName() {
        return this.protocolName;
    }
    
    public int majorVersion() {
        return this.majorVersion;
    }
    
    public int minorVersion() {
        return this.minorVersion;
    }
    
    public String text() {
        return this.text;
    }
    
    public boolean isKeepAliveDefault() {
        return this.keepAliveDefault;
    }
    
    @Override
    public String toString() {
        return this.text();
    }
    
    @Override
    public int hashCode() {
        return (this.protocolName().hashCode() * 31 + this.majorVersion()) * 31 + this.minorVersion();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof HttpVersion)) {
            return false;
        }
        final HttpVersion that = (HttpVersion)o;
        return this.minorVersion() == that.minorVersion() && this.majorVersion() == that.majorVersion() && this.protocolName().equals(that.protocolName());
    }
    
    @Override
    public int compareTo(final HttpVersion o) {
        int v = this.protocolName().compareTo(o.protocolName());
        if (v != 0) {
            return v;
        }
        v = this.majorVersion() - o.majorVersion();
        if (v != 0) {
            return v;
        }
        return this.minorVersion() - o.minorVersion();
    }
    
    static {
        VERSION_PATTERN = Pattern.compile("(\\S+)/(\\d+)\\.(\\d+)");
        HTTP_1_0 = new HttpVersion("HTTP", 1, 0, false);
        HTTP_1_1 = new HttpVersion("HTTP", 1, 1, true);
    }
}
