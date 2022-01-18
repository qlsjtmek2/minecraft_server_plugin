// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.buffer.ByteBuf;
import java.util.Iterator;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.channel.ChannelHandlerContext;
import java.util.HashMap;
import io.netty.handler.codec.http.FullHttpMessage;
import java.util.Map;
import io.netty.handler.codec.MessageToMessageDecoder;

public class SpdyHttpDecoder extends MessageToMessageDecoder<SpdyDataOrControlFrame>
{
    private final int spdyVersion;
    private final int maxContentLength;
    private final Map<Integer, FullHttpMessage> messageMap;
    
    public SpdyHttpDecoder(final int version, final int maxContentLength) {
        this.messageMap = new HashMap<Integer, FullHttpMessage>();
        if (version < 2 || version > 3) {
            throw new IllegalArgumentException("unsupported version: " + version);
        }
        if (maxContentLength <= 0) {
            throw new IllegalArgumentException("maxContentLength must be a positive integer: " + maxContentLength);
        }
        this.spdyVersion = version;
        this.maxContentLength = maxContentLength;
    }
    
    @Override
    protected Object decode(final ChannelHandlerContext ctx, final SpdyDataOrControlFrame msg) throws Exception {
        if (msg instanceof SpdySynStreamFrame) {
            final SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
            final int streamID = spdySynStreamFrame.getStreamId();
            if (SpdyCodecUtil.isServerId(streamID)) {
                final int associatedToStreamId = spdySynStreamFrame.getAssociatedToStreamId();
                if (associatedToStreamId == 0) {
                    final SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamID, SpdyStreamStatus.INVALID_STREAM);
                    ctx.write(spdyRstStreamFrame);
                }
                final String URL = SpdyHeaders.getUrl(this.spdyVersion, spdySynStreamFrame);
                if (URL == null) {
                    final SpdyRstStreamFrame spdyRstStreamFrame2 = new DefaultSpdyRstStreamFrame(streamID, SpdyStreamStatus.PROTOCOL_ERROR);
                    ctx.write(spdyRstStreamFrame2);
                }
                try {
                    final FullHttpResponse httpResponseWithEntity = createHttpResponse(this.spdyVersion, spdySynStreamFrame);
                    SpdyHttpHeaders.setStreamId(httpResponseWithEntity, streamID);
                    SpdyHttpHeaders.setAssociatedToStreamId(httpResponseWithEntity, associatedToStreamId);
                    SpdyHttpHeaders.setPriority(httpResponseWithEntity, spdySynStreamFrame.getPriority());
                    SpdyHttpHeaders.setUrl(httpResponseWithEntity, URL);
                    if (spdySynStreamFrame.isLast()) {
                        HttpHeaders.setContentLength(httpResponseWithEntity, 0L);
                        return httpResponseWithEntity;
                    }
                    this.messageMap.put(streamID, httpResponseWithEntity);
                }
                catch (Exception e2) {
                    final SpdyRstStreamFrame spdyRstStreamFrame3 = new DefaultSpdyRstStreamFrame(streamID, SpdyStreamStatus.PROTOCOL_ERROR);
                    ctx.write(spdyRstStreamFrame3);
                }
            }
            else {
                try {
                    final FullHttpRequest httpRequestWithEntity = createHttpRequest(this.spdyVersion, spdySynStreamFrame);
                    SpdyHttpHeaders.setStreamId(httpRequestWithEntity, streamID);
                    if (spdySynStreamFrame.isLast()) {
                        return httpRequestWithEntity;
                    }
                    this.messageMap.put(streamID, httpRequestWithEntity);
                }
                catch (Exception e3) {
                    final SpdySynReplyFrame spdySynReplyFrame = new DefaultSpdySynReplyFrame(streamID);
                    spdySynReplyFrame.setLast(true);
                    SpdyHeaders.setStatus(this.spdyVersion, spdySynReplyFrame, HttpResponseStatus.BAD_REQUEST);
                    SpdyHeaders.setVersion(this.spdyVersion, spdySynReplyFrame, HttpVersion.HTTP_1_0);
                    ctx.write(spdySynReplyFrame);
                }
            }
        }
        else if (msg instanceof SpdySynReplyFrame) {
            final SpdySynReplyFrame spdySynReplyFrame2 = (SpdySynReplyFrame)msg;
            final int streamID = spdySynReplyFrame2.getStreamId();
            try {
                final FullHttpResponse httpResponseWithEntity2 = createHttpResponse(this.spdyVersion, spdySynReplyFrame2);
                SpdyHttpHeaders.setStreamId(httpResponseWithEntity2, streamID);
                if (spdySynReplyFrame2.isLast()) {
                    HttpHeaders.setContentLength(httpResponseWithEntity2, 0L);
                    return httpResponseWithEntity2;
                }
                this.messageMap.put(streamID, httpResponseWithEntity2);
            }
            catch (Exception e3) {
                final SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamID, SpdyStreamStatus.PROTOCOL_ERROR);
                ctx.write(spdyRstStreamFrame);
            }
        }
        else if (msg instanceof SpdyHeadersFrame) {
            final SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
            final Integer streamID2 = spdyHeadersFrame.getStreamId();
            final HttpMessage httpMessage = this.messageMap.get(streamID2);
            if (httpMessage == null) {
                return null;
            }
            for (final Map.Entry<String, String> e : spdyHeadersFrame.headers().entries()) {
                httpMessage.headers().add(e.getKey(), e.getValue());
            }
        }
        else if (msg instanceof SpdyDataFrame) {
            final SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
            final Integer streamID2 = spdyDataFrame.getStreamId();
            final FullHttpMessage fullHttpMessage = this.messageMap.get(streamID2);
            if (fullHttpMessage == null) {
                return null;
            }
            final ByteBuf content = fullHttpMessage.data();
            if (content.readableBytes() > this.maxContentLength - spdyDataFrame.data().readableBytes()) {
                this.messageMap.remove(streamID2);
                throw new TooLongFrameException("HTTP content length exceeded " + this.maxContentLength + " bytes.");
            }
            final ByteBuf spdyDataFrameData = spdyDataFrame.data();
            final int spdyDataFrameDataLen = spdyDataFrameData.readableBytes();
            content.writeBytes(spdyDataFrameData, spdyDataFrameData.readerIndex(), spdyDataFrameDataLen);
            if (spdyDataFrame.isLast()) {
                HttpHeaders.setContentLength(fullHttpMessage, content.readableBytes());
                this.messageMap.remove(streamID2);
                return fullHttpMessage;
            }
        }
        else if (msg instanceof SpdyRstStreamFrame) {
            final SpdyRstStreamFrame spdyRstStreamFrame4 = (SpdyRstStreamFrame)msg;
            final Integer streamID2 = spdyRstStreamFrame4.getStreamId();
            this.messageMap.remove(streamID2);
        }
        return null;
    }
    
    private static FullHttpRequest createHttpRequest(final int spdyVersion, final SpdyHeaderBlock requestFrame) throws Exception {
        final HttpMethod method = SpdyHeaders.getMethod(spdyVersion, requestFrame);
        final String url = SpdyHeaders.getUrl(spdyVersion, requestFrame);
        final HttpVersion httpVersion = SpdyHeaders.getVersion(spdyVersion, requestFrame);
        SpdyHeaders.removeMethod(spdyVersion, requestFrame);
        SpdyHeaders.removeUrl(spdyVersion, requestFrame);
        SpdyHeaders.removeVersion(spdyVersion, requestFrame);
        final FullHttpRequest req = new DefaultFullHttpRequest(httpVersion, method, url);
        SpdyHeaders.removeScheme(spdyVersion, requestFrame);
        if (spdyVersion >= 3) {
            final String host = SpdyHeaders.getHost(requestFrame);
            SpdyHeaders.removeHost(requestFrame);
            HttpHeaders.setHost(req, host);
        }
        for (final Map.Entry<String, String> e : requestFrame.headers().entries()) {
            req.headers().add(e.getKey(), e.getValue());
        }
        HttpHeaders.setKeepAlive(req, true);
        req.headers().remove("Transfer-Encoding");
        return req;
    }
    
    private static FullHttpResponse createHttpResponse(final int spdyVersion, final SpdyHeaderBlock responseFrame) throws Exception {
        final HttpResponseStatus status = SpdyHeaders.getStatus(spdyVersion, responseFrame);
        final HttpVersion version = SpdyHeaders.getVersion(spdyVersion, responseFrame);
        SpdyHeaders.removeStatus(spdyVersion, responseFrame);
        SpdyHeaders.removeVersion(spdyVersion, responseFrame);
        final FullHttpResponse res = new DefaultFullHttpResponse(version, status);
        for (final Map.Entry<String, String> e : responseFrame.headers().entries()) {
            res.headers().add(e.getKey(), e.getValue());
        }
        HttpHeaders.setKeepAlive(res, true);
        res.headers().remove("Transfer-Encoding");
        res.headers().remove("Trailer");
        return res;
    }
}
