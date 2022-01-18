// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.channel.ChannelHandler;
import io.netty.channel.embedded.EmbeddedByteChannel;

public class HttpContentDecompressor extends HttpContentDecoder
{
    @Override
    protected EmbeddedByteChannel newContentDecoder(final String contentEncoding) throws Exception {
        if ("gzip".equalsIgnoreCase(contentEncoding) || "x-gzip".equalsIgnoreCase(contentEncoding)) {
            return new EmbeddedByteChannel(new ChannelHandler[] { ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP) });
        }
        if ("deflate".equalsIgnoreCase(contentEncoding) || "x-deflate".equalsIgnoreCase(contentEncoding)) {
            return new EmbeddedByteChannel(new ChannelHandler[] { ZlibCodecFactory.newZlibDecoder(ZlibWrapper.ZLIB_OR_NONE) });
        }
        return null;
    }
}
