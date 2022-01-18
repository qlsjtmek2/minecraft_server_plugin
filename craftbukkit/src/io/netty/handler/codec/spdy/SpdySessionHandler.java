// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import io.netty.buffer.Buf;
import io.netty.channel.ChannelFuture;
import java.util.Iterator;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.channel.ChannelFutureListener;
import io.netty.buffer.Unpooled;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import java.util.concurrent.atomic.AtomicInteger;
import io.netty.channel.ChannelOutboundMessageHandler;
import io.netty.channel.ChannelInboundMessageHandler;
import io.netty.channel.ChannelDuplexHandler;

public class SpdySessionHandler extends ChannelDuplexHandler implements ChannelInboundMessageHandler<Object>, ChannelOutboundMessageHandler<Object>
{
    private static final SpdyProtocolException PROTOCOL_EXCEPTION;
    private static final SpdyProtocolException STREAM_CLOSED;
    private final SpdySession spdySession;
    private volatile int lastGoodStreamId;
    private volatile int remoteConcurrentStreams;
    private volatile int localConcurrentStreams;
    private volatile int maxConcurrentStreams;
    private static final int DEFAULT_WINDOW_SIZE = 65536;
    private volatile int initialSendWindowSize;
    private volatile int initialReceiveWindowSize;
    private final Object flowControlLock;
    private final AtomicInteger pings;
    private volatile boolean sentGoAwayFrame;
    private volatile boolean receivedGoAwayFrame;
    private volatile ChannelPromise closeSessionFuture;
    private final boolean server;
    private final boolean flowControl;
    
    public SpdySessionHandler(final int version, final boolean server) {
        this.spdySession = new SpdySession();
        this.initialSendWindowSize = 65536;
        this.initialReceiveWindowSize = 65536;
        this.flowControlLock = new Object();
        this.pings = new AtomicInteger();
        if (version < 2 || version > 3) {
            throw new IllegalArgumentException("unsupported version: " + version);
        }
        this.server = server;
        this.flowControl = (version >= 3);
    }
    
    @Override
    public MessageBuf<Object> newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return Unpooled.messageBuffer();
    }
    
    @Override
    public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        ctx.inboundMessageBuffer().release();
    }
    
    @Override
    public MessageBuf<Object> newOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return Unpooled.messageBuffer();
    }
    
    @Override
    public void freeOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        ctx.outboundMessageBuffer().release();
    }
    
    @Override
    public void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        final MessageBuf<Object> in = ctx.inboundMessageBuffer();
        boolean handled = false;
        while (true) {
            final Object msg = in.poll();
            if (msg == null) {
                break;
            }
            if (msg instanceof SpdySynStreamFrame && handled) {
                ctx.fireInboundBufferUpdated();
            }
            this.handleInboundMessage(ctx, msg);
            handled = true;
        }
        ctx.fireInboundBufferUpdated();
    }
    
    private void handleInboundMessage(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        if (msg instanceof SpdyDataFrame) {
            final SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
            final int streamID = spdyDataFrame.getStreamId();
            if (!this.spdySession.isActiveStream(streamID)) {
                if (streamID <= this.lastGoodStreamId) {
                    this.issueStreamError(ctx, streamID, SpdyStreamStatus.PROTOCOL_ERROR);
                }
                else if (!this.sentGoAwayFrame) {
                    this.issueStreamError(ctx, streamID, SpdyStreamStatus.INVALID_STREAM);
                }
                return;
            }
            if (this.spdySession.isRemoteSideClosed(streamID)) {
                this.issueStreamError(ctx, streamID, SpdyStreamStatus.STREAM_ALREADY_CLOSED);
                return;
            }
            if (!this.isRemoteInitiatedID(streamID) && !this.spdySession.hasReceivedReply(streamID)) {
                this.issueStreamError(ctx, streamID, SpdyStreamStatus.PROTOCOL_ERROR);
                return;
            }
            if (this.flowControl) {
                int deltaWindowSize = -1 * spdyDataFrame.data().readableBytes();
                final int newWindowSize = this.spdySession.updateReceiveWindowSize(streamID, deltaWindowSize);
                if (newWindowSize < this.spdySession.getReceiveWindowSizeLowerBound(streamID)) {
                    this.issueStreamError(ctx, streamID, SpdyStreamStatus.FLOW_CONTROL_ERROR);
                    return;
                }
                if (newWindowSize < 0) {
                    while (spdyDataFrame.data().readableBytes() > this.initialReceiveWindowSize) {
                        final SpdyDataFrame partialDataFrame = new DefaultSpdyDataFrame(streamID, spdyDataFrame.data().readSlice(this.initialReceiveWindowSize));
                        ctx.nextOutboundMessageBuffer().add(partialDataFrame);
                        ctx.flush();
                    }
                }
                if (newWindowSize <= this.initialReceiveWindowSize / 2 && !spdyDataFrame.isLast()) {
                    deltaWindowSize = this.initialReceiveWindowSize - newWindowSize;
                    this.spdySession.updateReceiveWindowSize(streamID, deltaWindowSize);
                    final SpdyWindowUpdateFrame spdyWindowUpdateFrame = new DefaultSpdyWindowUpdateFrame(streamID, deltaWindowSize);
                    ctx.write(spdyWindowUpdateFrame);
                }
            }
            if (spdyDataFrame.isLast()) {
                this.halfCloseStream(streamID, true);
            }
        }
        else if (msg instanceof SpdySynStreamFrame) {
            final SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
            final int streamID = spdySynStreamFrame.getStreamId();
            if (spdySynStreamFrame.isInvalid() || !this.isRemoteInitiatedID(streamID) || this.spdySession.isActiveStream(streamID)) {
                this.issueStreamError(ctx, streamID, SpdyStreamStatus.PROTOCOL_ERROR);
                return;
            }
            if (streamID <= this.lastGoodStreamId) {
                this.issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
                return;
            }
            final byte priority = spdySynStreamFrame.getPriority();
            final boolean remoteSideClosed = spdySynStreamFrame.isLast();
            final boolean localSideClosed = spdySynStreamFrame.isUnidirectional();
            if (!this.acceptStream(streamID, priority, remoteSideClosed, localSideClosed)) {
                this.issueStreamError(ctx, streamID, SpdyStreamStatus.REFUSED_STREAM);
                return;
            }
        }
        else if (msg instanceof SpdySynReplyFrame) {
            final SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)msg;
            final int streamID = spdySynReplyFrame.getStreamId();
            if (spdySynReplyFrame.isInvalid() || this.isRemoteInitiatedID(streamID) || this.spdySession.isRemoteSideClosed(streamID)) {
                this.issueStreamError(ctx, streamID, SpdyStreamStatus.INVALID_STREAM);
                return;
            }
            if (this.spdySession.hasReceivedReply(streamID)) {
                this.issueStreamError(ctx, streamID, SpdyStreamStatus.STREAM_IN_USE);
                return;
            }
            this.spdySession.receivedReply(streamID);
            if (spdySynReplyFrame.isLast()) {
                this.halfCloseStream(streamID, true);
            }
        }
        else if (msg instanceof SpdyRstStreamFrame) {
            final SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)msg;
            this.removeStream(ctx, spdyRstStreamFrame.getStreamId());
        }
        else if (msg instanceof SpdySettingsFrame) {
            final SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame)msg;
            final int newConcurrentStreams = spdySettingsFrame.getValue(4);
            if (newConcurrentStreams >= 0) {
                this.updateConcurrentStreams(newConcurrentStreams, true);
            }
            if (spdySettingsFrame.isPersisted(7)) {
                spdySettingsFrame.removeValue(7);
            }
            spdySettingsFrame.setPersistValue(7, false);
            if (this.flowControl) {
                final int newInitialWindowSize = spdySettingsFrame.getValue(7);
                if (newInitialWindowSize >= 0) {
                    this.updateInitialSendWindowSize(newInitialWindowSize);
                }
            }
        }
        else if (msg instanceof SpdyPingFrame) {
            final SpdyPingFrame spdyPingFrame = (SpdyPingFrame)msg;
            if (this.isRemoteInitiatedID(spdyPingFrame.getId())) {
                ctx.write(spdyPingFrame);
                return;
            }
            if (this.pings.get() == 0) {
                return;
            }
            this.pings.getAndDecrement();
        }
        else if (msg instanceof SpdyGoAwayFrame) {
            this.receivedGoAwayFrame = true;
        }
        else if (msg instanceof SpdyHeadersFrame) {
            final SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
            final int streamID = spdyHeadersFrame.getStreamId();
            if (spdyHeadersFrame.isInvalid()) {
                this.issueStreamError(ctx, streamID, SpdyStreamStatus.PROTOCOL_ERROR);
                return;
            }
            if (this.spdySession.isRemoteSideClosed(streamID)) {
                this.issueStreamError(ctx, streamID, SpdyStreamStatus.INVALID_STREAM);
                return;
            }
            if (spdyHeadersFrame.isLast()) {
                this.halfCloseStream(streamID, true);
            }
        }
        else if (msg instanceof SpdyWindowUpdateFrame && this.flowControl) {
            final SpdyWindowUpdateFrame spdyWindowUpdateFrame2 = (SpdyWindowUpdateFrame)msg;
            final int streamID = spdyWindowUpdateFrame2.getStreamId();
            final int deltaWindowSize = spdyWindowUpdateFrame2.getDeltaWindowSize();
            if (this.spdySession.isLocalSideClosed(streamID)) {
                return;
            }
            if (this.spdySession.getSendWindowSize(streamID) > Integer.MAX_VALUE - deltaWindowSize) {
                this.issueStreamError(ctx, streamID, SpdyStreamStatus.FLOW_CONTROL_ERROR);
                return;
            }
            this.updateSendWindowSize(ctx, streamID, deltaWindowSize);
        }
        ctx.nextInboundMessageBuffer().add(msg);
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        if (cause instanceof SpdyProtocolException) {
            this.issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
        }
        super.exceptionCaught(ctx, cause);
    }
    
    @Override
    public void close(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        this.sendGoAwayFrame(ctx, promise);
    }
    
    @Override
    public void flush(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        final MessageBuf<Object> in = ctx.outboundMessageBuffer();
        while (true) {
            final Object msg = in.poll();
            if (msg == null) {
                break;
            }
            if (msg instanceof SpdyDataFrame || msg instanceof SpdySynStreamFrame || msg instanceof SpdySynReplyFrame || msg instanceof SpdyRstStreamFrame || msg instanceof SpdySettingsFrame || msg instanceof SpdyPingFrame || msg instanceof SpdyGoAwayFrame || msg instanceof SpdyHeadersFrame || msg instanceof SpdyWindowUpdateFrame) {
                this.handleOutboundMessage(ctx, msg);
            }
            else {
                ctx.nextOutboundMessageBuffer().add(msg);
            }
        }
        ctx.flush(promise);
    }
    
    private void handleOutboundMessage(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        if (msg instanceof SpdyDataFrame) {
            final SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
            final int streamID = spdyDataFrame.getStreamId();
            if (this.spdySession.isLocalSideClosed(streamID)) {
                ctx.fireExceptionCaught((Throwable)SpdySessionHandler.PROTOCOL_EXCEPTION);
                return;
            }
            if (this.flowControl) {
                synchronized (this.flowControlLock) {
                    final int dataLength = spdyDataFrame.data().readableBytes();
                    final int sendWindowSize = this.spdySession.getSendWindowSize(streamID);
                    if (sendWindowSize >= dataLength) {
                        this.spdySession.updateSendWindowSize(streamID, -1 * dataLength);
                    }
                    else {
                        if (sendWindowSize > 0) {
                            this.spdySession.updateSendWindowSize(streamID, -1 * sendWindowSize);
                            final SpdyDataFrame partialDataFrame = new DefaultSpdyDataFrame(streamID, spdyDataFrame.data().readSlice(sendWindowSize));
                            this.spdySession.putPendingWrite(streamID, spdyDataFrame);
                            ctx.nextOutboundMessageBuffer().add(partialDataFrame);
                            return;
                        }
                        this.spdySession.putPendingWrite(streamID, spdyDataFrame);
                        return;
                    }
                }
            }
            if (spdyDataFrame.isLast()) {
                this.halfCloseStream(streamID, false);
            }
        }
        else if (msg instanceof SpdySynStreamFrame) {
            final SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
            final int streamID = spdySynStreamFrame.getStreamId();
            if (this.isRemoteInitiatedID(streamID)) {
                ctx.fireExceptionCaught((Throwable)SpdySessionHandler.PROTOCOL_EXCEPTION);
                return;
            }
            final byte priority = spdySynStreamFrame.getPriority();
            final boolean remoteSideClosed = spdySynStreamFrame.isUnidirectional();
            final boolean localSideClosed = spdySynStreamFrame.isLast();
            if (!this.acceptStream(streamID, priority, remoteSideClosed, localSideClosed)) {
                ctx.fireExceptionCaught((Throwable)SpdySessionHandler.PROTOCOL_EXCEPTION);
                return;
            }
        }
        else if (msg instanceof SpdySynReplyFrame) {
            final SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)msg;
            final int streamID = spdySynReplyFrame.getStreamId();
            if (!this.isRemoteInitiatedID(streamID) || this.spdySession.isLocalSideClosed(streamID)) {
                ctx.fireExceptionCaught((Throwable)SpdySessionHandler.PROTOCOL_EXCEPTION);
                return;
            }
            if (spdySynReplyFrame.isLast()) {
                this.halfCloseStream(streamID, false);
            }
        }
        else if (msg instanceof SpdyRstStreamFrame) {
            final SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)msg;
            this.removeStream(ctx, spdyRstStreamFrame.getStreamId());
        }
        else if (msg instanceof SpdySettingsFrame) {
            final SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame)msg;
            final int newConcurrentStreams = spdySettingsFrame.getValue(4);
            if (newConcurrentStreams >= 0) {
                this.updateConcurrentStreams(newConcurrentStreams, false);
            }
            if (spdySettingsFrame.isPersisted(7)) {
                spdySettingsFrame.removeValue(7);
            }
            spdySettingsFrame.setPersistValue(7, false);
            if (this.flowControl) {
                final int newInitialWindowSize = spdySettingsFrame.getValue(7);
                if (newInitialWindowSize >= 0) {
                    this.updateInitialReceiveWindowSize(newInitialWindowSize);
                }
            }
        }
        else if (msg instanceof SpdyPingFrame) {
            final SpdyPingFrame spdyPingFrame = (SpdyPingFrame)msg;
            if (this.isRemoteInitiatedID(spdyPingFrame.getId())) {
                ctx.fireExceptionCaught((Throwable)new IllegalArgumentException("invalid PING ID: " + spdyPingFrame.getId()));
                return;
            }
            this.pings.getAndIncrement();
        }
        else {
            if (msg instanceof SpdyGoAwayFrame) {
                ctx.fireExceptionCaught((Throwable)SpdySessionHandler.PROTOCOL_EXCEPTION);
                return;
            }
            if (msg instanceof SpdyHeadersFrame) {
                final SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
                final int streamID = spdyHeadersFrame.getStreamId();
                if (this.spdySession.isLocalSideClosed(streamID)) {
                    ctx.fireExceptionCaught((Throwable)SpdySessionHandler.PROTOCOL_EXCEPTION);
                    return;
                }
                if (spdyHeadersFrame.isLast()) {
                    this.halfCloseStream(streamID, false);
                }
            }
            else if (msg instanceof SpdyWindowUpdateFrame) {
                ctx.fireExceptionCaught((Throwable)SpdySessionHandler.PROTOCOL_EXCEPTION);
            }
        }
        ctx.nextOutboundMessageBuffer().add(msg);
    }
    
    private void issueSessionError(final ChannelHandlerContext ctx, final SpdySessionStatus status) {
        this.sendGoAwayFrame(ctx, status);
        ctx.flush().addListener((GenericFutureListener<? extends Future<Void>>)ChannelFutureListener.CLOSE);
    }
    
    private void issueStreamError(final ChannelHandlerContext ctx, final int streamID, final SpdyStreamStatus status) {
        final boolean fireMessageReceived = !this.spdySession.isRemoteSideClosed(streamID);
        this.removeStream(ctx, streamID);
        final SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamID, status);
        ctx.write(spdyRstStreamFrame);
        if (fireMessageReceived) {
            ctx.nextInboundMessageBuffer().add(spdyRstStreamFrame);
            ctx.fireInboundBufferUpdated();
        }
    }
    
    private boolean isRemoteInitiatedID(final int id) {
        final boolean serverID = SpdyCodecUtil.isServerId(id);
        return (this.server && !serverID) || (!this.server && serverID);
    }
    
    private void updateConcurrentStreams(final int newConcurrentStreams, final boolean remote) {
        if (remote) {
            this.remoteConcurrentStreams = newConcurrentStreams;
        }
        else {
            this.localConcurrentStreams = newConcurrentStreams;
        }
        if (this.localConcurrentStreams == this.remoteConcurrentStreams) {
            this.maxConcurrentStreams = this.localConcurrentStreams;
            return;
        }
        if (this.localConcurrentStreams == 0) {
            this.maxConcurrentStreams = this.remoteConcurrentStreams;
            return;
        }
        if (this.remoteConcurrentStreams == 0) {
            this.maxConcurrentStreams = this.localConcurrentStreams;
            return;
        }
        if (this.localConcurrentStreams > this.remoteConcurrentStreams) {
            this.maxConcurrentStreams = this.remoteConcurrentStreams;
        }
        else {
            this.maxConcurrentStreams = this.localConcurrentStreams;
        }
    }
    
    private synchronized void updateInitialSendWindowSize(final int newInitialWindowSize) {
        final int deltaWindowSize = newInitialWindowSize - this.initialSendWindowSize;
        this.initialSendWindowSize = newInitialWindowSize;
        for (final Integer streamId : this.spdySession.getActiveStreams()) {
            this.spdySession.updateSendWindowSize(streamId, deltaWindowSize);
        }
    }
    
    private synchronized void updateInitialReceiveWindowSize(final int newInitialWindowSize) {
        final int deltaWindowSize = newInitialWindowSize - this.initialReceiveWindowSize;
        this.initialReceiveWindowSize = newInitialWindowSize;
        this.spdySession.updateAllReceiveWindowSizes(deltaWindowSize);
    }
    
    private synchronized boolean acceptStream(final int streamID, final byte priority, final boolean remoteSideClosed, final boolean localSideClosed) {
        if (this.receivedGoAwayFrame || this.sentGoAwayFrame) {
            return false;
        }
        final int maxConcurrentStreams = this.maxConcurrentStreams;
        if (maxConcurrentStreams != 0 && this.spdySession.numActiveStreams() >= maxConcurrentStreams) {
            return false;
        }
        this.spdySession.acceptStream(streamID, priority, remoteSideClosed, localSideClosed, this.initialSendWindowSize, this.initialReceiveWindowSize);
        if (this.isRemoteInitiatedID(streamID)) {
            this.lastGoodStreamId = streamID;
        }
        return true;
    }
    
    private void halfCloseStream(final int streamID, final boolean remote) {
        if (remote) {
            this.spdySession.closeRemoteSide(streamID);
        }
        else {
            this.spdySession.closeLocalSide(streamID);
        }
        if (this.closeSessionFuture != null && this.spdySession.noActiveStreams()) {
            this.closeSessionFuture.setSuccess();
        }
    }
    
    private void removeStream(final ChannelHandlerContext ctx, final int streamID) {
        if (this.spdySession.removeStream(streamID)) {
            ctx.fireExceptionCaught((Throwable)SpdySessionHandler.STREAM_CLOSED);
        }
        if (this.closeSessionFuture != null && this.spdySession.noActiveStreams()) {
            this.closeSessionFuture.setSuccess();
        }
    }
    
    private void updateSendWindowSize(final ChannelHandlerContext ctx, final int streamID, final int deltaWindowSize) {
        synchronized (this.flowControlLock) {
            int newWindowSize = this.spdySession.updateSendWindowSize(streamID, deltaWindowSize);
            while (newWindowSize > 0) {
                final SpdyDataFrame spdyDataFrame = (SpdyDataFrame)this.spdySession.getPendingWrite(streamID);
                if (spdyDataFrame == null) {
                    break;
                }
                final int dataFrameSize = spdyDataFrame.data().readableBytes();
                if (newWindowSize >= dataFrameSize) {
                    this.spdySession.removePendingWrite(streamID);
                    newWindowSize = this.spdySession.updateSendWindowSize(streamID, -1 * dataFrameSize);
                    if (spdyDataFrame.isLast()) {
                        this.halfCloseStream(streamID, false);
                    }
                    ctx.nextOutboundMessageBuffer().add(spdyDataFrame);
                }
                else {
                    this.spdySession.updateSendWindowSize(streamID, -1 * newWindowSize);
                    final SpdyDataFrame partialDataFrame = new DefaultSpdyDataFrame(streamID, spdyDataFrame.data().readSlice(newWindowSize));
                    ctx.nextOutboundMessageBuffer().add(partialDataFrame);
                    newWindowSize = 0;
                }
            }
        }
    }
    
    private void sendGoAwayFrame(final ChannelHandlerContext ctx, final ChannelPromise future) {
        if (!ctx.channel().isActive()) {
            ctx.close(future);
            return;
        }
        this.sendGoAwayFrame(ctx, SpdySessionStatus.OK);
        final ChannelFuture f = ctx.flush();
        if (this.spdySession.noActiveStreams()) {
            f.addListener((GenericFutureListener<? extends Future<Void>>)new ClosingChannelFutureListener(ctx, future));
        }
        else {
            (this.closeSessionFuture = ctx.newPromise()).addListener((GenericFutureListener<? extends Future<Void>>)new ClosingChannelFutureListener(ctx, future));
        }
    }
    
    private synchronized void sendGoAwayFrame(final ChannelHandlerContext ctx, final SpdySessionStatus status) {
        if (!this.sentGoAwayFrame) {
            this.sentGoAwayFrame = true;
            final SpdyGoAwayFrame spdyGoAwayFrame = new DefaultSpdyGoAwayFrame(this.lastGoodStreamId, status);
            ctx.nextOutboundMessageBuffer().add(spdyGoAwayFrame);
        }
    }
    
    static {
        PROTOCOL_EXCEPTION = new SpdyProtocolException();
        STREAM_CLOSED = new SpdyProtocolException("Stream closed");
        final StackTraceElement[] emptyTrace = new StackTraceElement[0];
        SpdySessionHandler.PROTOCOL_EXCEPTION.setStackTrace(emptyTrace);
        SpdySessionHandler.STREAM_CLOSED.setStackTrace(emptyTrace);
    }
    
    private static final class ClosingChannelFutureListener implements ChannelFutureListener
    {
        private final ChannelHandlerContext ctx;
        private final ChannelPromise promise;
        
        ClosingChannelFutureListener(final ChannelHandlerContext ctx, final ChannelPromise promise) {
            this.ctx = ctx;
            this.promise = promise;
        }
        
        @Override
        public void operationComplete(final ChannelFuture sentGoAwayFuture) throws Exception {
            this.ctx.close(this.promise);
        }
    }
}
