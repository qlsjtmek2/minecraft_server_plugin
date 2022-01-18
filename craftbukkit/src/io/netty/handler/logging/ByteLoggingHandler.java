// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.logging;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import io.netty.buffer.Buf;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelHandlerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundByteHandler;
import io.netty.channel.ChannelInboundByteHandler;

public class ByteLoggingHandler extends LoggingHandler implements ChannelInboundByteHandler, ChannelOutboundByteHandler
{
    private static final String NEWLINE;
    private static final String[] BYTE2HEX;
    private static final String[] HEXPADDING;
    private static final String[] BYTEPADDING;
    private static final char[] BYTE2CHAR;
    
    public ByteLoggingHandler() {
    }
    
    public ByteLoggingHandler(final Class<?> clazz, final LogLevel level) {
        super(clazz, level);
    }
    
    public ByteLoggingHandler(final Class<?> clazz) {
        super(clazz);
    }
    
    public ByteLoggingHandler(final LogLevel level) {
        super(level);
    }
    
    public ByteLoggingHandler(final String name, final LogLevel level) {
        super(name, level);
    }
    
    public ByteLoggingHandler(final String name) {
        super(name);
    }
    
    @Override
    public ByteBuf newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return ChannelHandlerUtil.allocate(ctx);
    }
    
    @Override
    public void discardInboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
        ctx.inboundByteBuffer().discardSomeReadBytes();
    }
    
    @Override
    public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        ctx.inboundByteBuffer().release();
    }
    
    @Override
    public ByteBuf newOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return ChannelHandlerUtil.allocate(ctx);
    }
    
    @Override
    public void discardOutboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
        ctx.outboundByteBuffer().discardSomeReadBytes();
    }
    
    @Override
    public void freeOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        ctx.outboundByteBuffer().release();
    }
    
    @Override
    public void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        final ByteBuf buf = ctx.inboundByteBuffer();
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(ctx, this.formatBuffer("RECEIVED", buf)));
        }
        ctx.nextInboundByteBuffer().writeBytes(buf);
        ctx.fireInboundBufferUpdated();
    }
    
    @Override
    public void flush(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        final ByteBuf buf = ctx.outboundByteBuffer();
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(ctx, this.formatBuffer("WRITE", buf)));
        }
        ctx.nextOutboundByteBuffer().writeBytes(buf);
        ctx.flush(promise);
    }
    
    protected String formatBuffer(final String message, final ByteBuf buf) {
        final int length = buf.readableBytes();
        final int rows = length / 16 + ((length % 15 != 0) ? 1 : 0) + 4;
        final StringBuilder dump = new StringBuilder(rows * 80 + message.length() + 16);
        dump.append(message).append('(').append(length).append('B').append(')');
        dump.append(ByteLoggingHandler.NEWLINE + "         +-------------------------------------------------+" + ByteLoggingHandler.NEWLINE + "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |" + ByteLoggingHandler.NEWLINE + "+--------+-------------------------------------------------+----------------+");
        final int startIndex = buf.readerIndex();
        int endIndex;
        int i;
        for (endIndex = buf.writerIndex(), i = startIndex; i < endIndex; ++i) {
            final int relIdx = i - startIndex;
            final int relIdxMod16 = relIdx & 0xF;
            if (relIdxMod16 == 0) {
                dump.append(ByteLoggingHandler.NEWLINE);
                dump.append(Long.toHexString((relIdx & 0xFFFFFFFFL) | 0x100000000L));
                dump.setCharAt(dump.length() - 9, '|');
                dump.append('|');
            }
            dump.append(ByteLoggingHandler.BYTE2HEX[buf.getUnsignedByte(i)]);
            if (relIdxMod16 == 15) {
                dump.append(" |");
                for (int j = i - 15; j <= i; ++j) {
                    dump.append(ByteLoggingHandler.BYTE2CHAR[buf.getUnsignedByte(j)]);
                }
                dump.append('|');
            }
        }
        if ((i - startIndex & 0xF) != 0x0) {
            final int remainder = length & 0xF;
            dump.append(ByteLoggingHandler.HEXPADDING[remainder]);
            dump.append(" |");
            for (int k = i - remainder; k < i; ++k) {
                dump.append(ByteLoggingHandler.BYTE2CHAR[buf.getUnsignedByte(k)]);
            }
            dump.append(ByteLoggingHandler.BYTEPADDING[remainder]);
            dump.append('|');
        }
        dump.append(ByteLoggingHandler.NEWLINE + "+--------+-------------------------------------------------+----------------+");
        return dump.toString();
    }
    
    static {
        NEWLINE = String.format("%n", new Object[0]);
        BYTE2HEX = new String[256];
        HEXPADDING = new String[16];
        BYTEPADDING = new String[16];
        BYTE2CHAR = new char[256];
        int i;
        for (i = 0; i < 10; ++i) {
            final StringBuilder buf = new StringBuilder(3);
            buf.append(" 0");
            buf.append(i);
            ByteLoggingHandler.BYTE2HEX[i] = buf.toString();
        }
        while (i < 16) {
            final StringBuilder buf = new StringBuilder(3);
            buf.append(" 0");
            buf.append((char)(97 + i - 10));
            ByteLoggingHandler.BYTE2HEX[i] = buf.toString();
            ++i;
        }
        while (i < ByteLoggingHandler.BYTE2HEX.length) {
            final StringBuilder buf = new StringBuilder(3);
            buf.append(' ');
            buf.append(Integer.toHexString(i));
            ByteLoggingHandler.BYTE2HEX[i] = buf.toString();
            ++i;
        }
        for (i = 0; i < ByteLoggingHandler.HEXPADDING.length; ++i) {
            final int padding = ByteLoggingHandler.HEXPADDING.length - i;
            final StringBuilder buf2 = new StringBuilder(padding * 3);
            for (int j = 0; j < padding; ++j) {
                buf2.append("   ");
            }
            ByteLoggingHandler.HEXPADDING[i] = buf2.toString();
        }
        for (i = 0; i < ByteLoggingHandler.BYTEPADDING.length; ++i) {
            final int padding = ByteLoggingHandler.BYTEPADDING.length - i;
            final StringBuilder buf2 = new StringBuilder(padding);
            for (int j = 0; j < padding; ++j) {
                buf2.append(' ');
            }
            ByteLoggingHandler.BYTEPADDING[i] = buf2.toString();
        }
        for (i = 0; i < ByteLoggingHandler.BYTE2CHAR.length; ++i) {
            if (i <= 31 || i >= 127) {
                ByteLoggingHandler.BYTE2CHAR[i] = '.';
            }
            else {
                ByteLoggingHandler.BYTE2CHAR[i] = (char)i;
            }
        }
    }
}
