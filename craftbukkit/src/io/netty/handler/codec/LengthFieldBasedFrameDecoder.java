// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec;

import io.netty.buffer.Unpooled;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.nio.ByteOrder;

public class LengthFieldBasedFrameDecoder extends ByteToMessageDecoder
{
    private final ByteOrder byteOrder;
    private final int maxFrameLength;
    private final int lengthFieldOffset;
    private final int lengthFieldLength;
    private final int lengthFieldEndOffset;
    private final int lengthAdjustment;
    private final int initialBytesToStrip;
    private final boolean failFast;
    private boolean discardingTooLongFrame;
    private long tooLongFrameLength;
    private long bytesToDiscard;
    
    public LengthFieldBasedFrameDecoder(final int maxFrameLength, final int lengthFieldOffset, final int lengthFieldLength) {
        this(maxFrameLength, lengthFieldOffset, lengthFieldLength, 0, 0);
    }
    
    public LengthFieldBasedFrameDecoder(final int maxFrameLength, final int lengthFieldOffset, final int lengthFieldLength, final int lengthAdjustment, final int initialBytesToStrip) {
        this(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, true);
    }
    
    public LengthFieldBasedFrameDecoder(final int maxFrameLength, final int lengthFieldOffset, final int lengthFieldLength, final int lengthAdjustment, final int initialBytesToStrip, final boolean failFast) {
        this(ByteOrder.BIG_ENDIAN, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }
    
    public LengthFieldBasedFrameDecoder(final ByteOrder byteOrder, final int maxFrameLength, final int lengthFieldOffset, final int lengthFieldLength, final int lengthAdjustment, final int initialBytesToStrip, final boolean failFast) {
        if (byteOrder == null) {
            throw new NullPointerException("byteOrder");
        }
        if (maxFrameLength <= 0) {
            throw new IllegalArgumentException("maxFrameLength must be a positive integer: " + maxFrameLength);
        }
        if (lengthFieldOffset < 0) {
            throw new IllegalArgumentException("lengthFieldOffset must be a non-negative integer: " + lengthFieldOffset);
        }
        if (initialBytesToStrip < 0) {
            throw new IllegalArgumentException("initialBytesToStrip must be a non-negative integer: " + initialBytesToStrip);
        }
        if (lengthFieldLength != 1 && lengthFieldLength != 2 && lengthFieldLength != 3 && lengthFieldLength != 4 && lengthFieldLength != 8) {
            throw new IllegalArgumentException("lengthFieldLength must be either 1, 2, 3, 4, or 8: " + lengthFieldLength);
        }
        if (lengthFieldOffset > maxFrameLength - lengthFieldLength) {
            throw new IllegalArgumentException("maxFrameLength (" + maxFrameLength + ") " + "must be equal to or greater than " + "lengthFieldOffset (" + lengthFieldOffset + ") + " + "lengthFieldLength (" + lengthFieldLength + ").");
        }
        this.byteOrder = byteOrder;
        this.maxFrameLength = maxFrameLength;
        this.lengthFieldOffset = lengthFieldOffset;
        this.lengthFieldLength = lengthFieldLength;
        this.lengthAdjustment = lengthAdjustment;
        this.lengthFieldEndOffset = lengthFieldOffset + lengthFieldLength;
        this.initialBytesToStrip = initialBytesToStrip;
        this.failFast = failFast;
    }
    
    @Override
    protected Object decode(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
        if (this.discardingTooLongFrame) {
            long bytesToDiscard = this.bytesToDiscard;
            final int localBytesToDiscard = (int)Math.min(bytesToDiscard, in.readableBytes());
            in.skipBytes(localBytesToDiscard);
            bytesToDiscard -= localBytesToDiscard;
            this.bytesToDiscard = bytesToDiscard;
            this.failIfNecessary(ctx, false);
            return null;
        }
        if (in.readableBytes() < this.lengthFieldEndOffset) {
            return null;
        }
        final int actualLengthFieldOffset = in.readerIndex() + this.lengthFieldOffset;
        long frameLength = this.getFrameLength(in, actualLengthFieldOffset);
        if (frameLength < 0L) {
            in.skipBytes(this.lengthFieldEndOffset);
            throw new CorruptedFrameException("negative pre-adjustment length field: " + frameLength);
        }
        frameLength += this.lengthAdjustment + this.lengthFieldEndOffset;
        if (frameLength < this.lengthFieldEndOffset) {
            in.skipBytes(this.lengthFieldEndOffset);
            throw new CorruptedFrameException("Adjusted frame length (" + frameLength + ") is less " + "than lengthFieldEndOffset: " + this.lengthFieldEndOffset);
        }
        if (frameLength > this.maxFrameLength) {
            this.discardingTooLongFrame = true;
            this.tooLongFrameLength = frameLength;
            this.bytesToDiscard = frameLength - in.readableBytes();
            in.skipBytes(in.readableBytes());
            this.failIfNecessary(ctx, true);
            return null;
        }
        final int frameLengthInt = (int)frameLength;
        if (in.readableBytes() < frameLengthInt) {
            return null;
        }
        if (this.initialBytesToStrip > frameLengthInt) {
            in.skipBytes(frameLengthInt);
            throw new CorruptedFrameException("Adjusted frame length (" + frameLength + ") is less " + "than initialBytesToStrip: " + this.initialBytesToStrip);
        }
        in.skipBytes(this.initialBytesToStrip);
        final int readerIndex = in.readerIndex();
        final int actualFrameLength = frameLengthInt - this.initialBytesToStrip;
        final ByteBuf frame = this.extractFrame(in, readerIndex, actualFrameLength);
        in.readerIndex(readerIndex + actualFrameLength);
        return frame;
    }
    
    private long getFrameLength(ByteBuf in, final int actualLengthFieldOffset) {
        in = in.order(this.byteOrder);
        long frameLength = 0L;
        switch (this.lengthFieldLength) {
            case 1: {
                frameLength = in.getUnsignedByte(actualLengthFieldOffset);
                break;
            }
            case 2: {
                frameLength = in.getUnsignedShort(actualLengthFieldOffset);
                break;
            }
            case 3: {
                frameLength = in.getUnsignedMedium(actualLengthFieldOffset);
                break;
            }
            case 4: {
                frameLength = in.getUnsignedInt(actualLengthFieldOffset);
                break;
            }
            case 8: {
                frameLength = in.getLong(actualLengthFieldOffset);
                break;
            }
            default: {
                throw new Error("should not reach here");
            }
        }
        return frameLength;
    }
    
    private void failIfNecessary(final ChannelHandlerContext ctx, final boolean firstDetectionOfTooLongFrame) {
        if (this.bytesToDiscard == 0L) {
            final long tooLongFrameLength = this.tooLongFrameLength;
            this.tooLongFrameLength = 0L;
            this.discardingTooLongFrame = false;
            if (!this.failFast || (this.failFast && firstDetectionOfTooLongFrame)) {
                this.fail(ctx, tooLongFrameLength);
            }
        }
        else if (this.failFast && firstDetectionOfTooLongFrame) {
            this.fail(ctx, this.tooLongFrameLength);
        }
    }
    
    protected ByteBuf extractFrame(final ByteBuf buffer, final int index, final int length) {
        final ByteBuf frame = Unpooled.buffer(length);
        frame.writeBytes(buffer, index, length);
        return frame;
    }
    
    private void fail(final ChannelHandlerContext ctx, final long frameLength) {
        if (frameLength > 0L) {
            ctx.fireExceptionCaught((Throwable)new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + ": " + frameLength + " - discarded"));
        }
        else {
            ctx.fireExceptionCaught((Throwable)new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + " - discarding"));
        }
    }
}
