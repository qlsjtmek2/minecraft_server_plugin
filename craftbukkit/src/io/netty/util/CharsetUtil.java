// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util;

import java.util.IdentityHashMap;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Map;
import java.nio.charset.Charset;

public final class CharsetUtil
{
    public static final Charset UTF_16;
    public static final Charset UTF_16BE;
    public static final Charset UTF_16LE;
    public static final Charset UTF_8;
    public static final Charset ISO_8859_1;
    public static final Charset US_ASCII;
    private static final ThreadLocal<Map<Charset, CharsetEncoder>> encoders;
    private static final ThreadLocal<Map<Charset, CharsetDecoder>> decoders;
    
    public static CharsetEncoder getEncoder(final Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        final Map<Charset, CharsetEncoder> map = CharsetUtil.encoders.get();
        CharsetEncoder e = map.get(charset);
        if (e != null) {
            e.reset();
            e.onMalformedInput(CodingErrorAction.REPLACE);
            e.onUnmappableCharacter(CodingErrorAction.REPLACE);
            return e;
        }
        e = charset.newEncoder();
        e.onMalformedInput(CodingErrorAction.REPLACE);
        e.onUnmappableCharacter(CodingErrorAction.REPLACE);
        map.put(charset, e);
        return e;
    }
    
    public static CharsetDecoder getDecoder(final Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        final Map<Charset, CharsetDecoder> map = CharsetUtil.decoders.get();
        CharsetDecoder d = map.get(charset);
        if (d != null) {
            d.reset();
            d.onMalformedInput(CodingErrorAction.REPLACE);
            d.onUnmappableCharacter(CodingErrorAction.REPLACE);
            return d;
        }
        d = charset.newDecoder();
        d.onMalformedInput(CodingErrorAction.REPLACE);
        d.onUnmappableCharacter(CodingErrorAction.REPLACE);
        map.put(charset, d);
        return d;
    }
    
    static {
        UTF_16 = Charset.forName("UTF-16");
        UTF_16BE = Charset.forName("UTF-16BE");
        UTF_16LE = Charset.forName("UTF-16LE");
        UTF_8 = Charset.forName("UTF-8");
        ISO_8859_1 = Charset.forName("ISO-8859-1");
        US_ASCII = Charset.forName("US-ASCII");
        encoders = new ThreadLocal<Map<Charset, CharsetEncoder>>() {
            @Override
            protected Map<Charset, CharsetEncoder> initialValue() {
                return new IdentityHashMap<Charset, CharsetEncoder>();
            }
        };
        decoders = new ThreadLocal<Map<Charset, CharsetDecoder>>() {
            @Override
            protected Map<Charset, CharsetDecoder> initialValue() {
                return new IdentityHashMap<Charset, CharsetDecoder>();
            }
        };
    }
}
