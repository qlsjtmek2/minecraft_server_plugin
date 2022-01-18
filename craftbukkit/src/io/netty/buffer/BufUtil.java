// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CharacterCodingException;
import io.netty.util.CharsetUtil;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.CharBuffer;
import java.nio.ByteOrder;

public final class BufUtil
{
    private static final char[] HEXDUMP_TABLE;
    
    public static <T> T retain(final T msg) {
        if (msg instanceof ReferenceCounted) {
            return (T)((ReferenceCounted)msg).retain();
        }
        return msg;
    }
    
    public static <T> T retain(final T msg, final int increment) {
        if (msg instanceof ReferenceCounted) {
            return (T)((ReferenceCounted)msg).retain(increment);
        }
        return msg;
    }
    
    public static boolean release(final Object msg) {
        return msg instanceof ReferenceCounted && ((ReferenceCounted)msg).release();
    }
    
    public static boolean release(final Object msg, final int decrement) {
        return msg instanceof ReferenceCounted && ((ReferenceCounted)msg).release(decrement);
    }
    
    public static String hexDump(final ByteBuf buffer) {
        return hexDump(buffer, buffer.readerIndex(), buffer.readableBytes());
    }
    
    public static String hexDump(final ByteBuf buffer, final int fromIndex, final int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length: " + length);
        }
        if (length == 0) {
            return "";
        }
        final int endIndex = fromIndex + length;
        final char[] buf = new char[length << 1];
        for (int srcIdx = fromIndex, dstIdx = 0; srcIdx < endIndex; ++srcIdx, dstIdx += 2) {
            System.arraycopy(BufUtil.HEXDUMP_TABLE, buffer.getUnsignedByte(srcIdx) << 1, buf, dstIdx, 2);
        }
        return new String(buf);
    }
    
    public static int hashCode(final ByteBuf buffer) {
        final int aLen = buffer.readableBytes();
        final int intCount = aLen >>> 2;
        final int byteCount = aLen & 0x3;
        int hashCode = 1;
        int arrayIndex = buffer.readerIndex();
        if (buffer.order() == ByteOrder.BIG_ENDIAN) {
            for (int i = intCount; i > 0; --i) {
                hashCode = 31 * hashCode + buffer.getInt(arrayIndex);
                arrayIndex += 4;
            }
        }
        else {
            for (int i = intCount; i > 0; --i) {
                hashCode = 31 * hashCode + swapInt(buffer.getInt(arrayIndex));
                arrayIndex += 4;
            }
        }
        for (int i = byteCount; i > 0; --i) {
            hashCode = 31 * hashCode + buffer.getByte(arrayIndex++);
        }
        if (hashCode == 0) {
            hashCode = 1;
        }
        return hashCode;
    }
    
    public static boolean equals(final ByteBuf bufferA, final ByteBuf bufferB) {
        final int aLen = bufferA.readableBytes();
        if (aLen != bufferB.readableBytes()) {
            return false;
        }
        final int longCount = aLen >>> 3;
        final int byteCount = aLen & 0x7;
        int aIndex = bufferA.readerIndex();
        int bIndex = bufferB.readerIndex();
        if (bufferA.order() == bufferB.order()) {
            for (int i = longCount; i > 0; --i) {
                if (bufferA.getLong(aIndex) != bufferB.getLong(bIndex)) {
                    return false;
                }
                aIndex += 8;
                bIndex += 8;
            }
        }
        else {
            for (int i = longCount; i > 0; --i) {
                if (bufferA.getLong(aIndex) != swapLong(bufferB.getLong(bIndex))) {
                    return false;
                }
                aIndex += 8;
                bIndex += 8;
            }
        }
        for (int i = byteCount; i > 0; --i) {
            if (bufferA.getByte(aIndex) != bufferB.getByte(bIndex)) {
                return false;
            }
            ++aIndex;
            ++bIndex;
        }
        return true;
    }
    
    public static int compare(final ByteBuf bufferA, final ByteBuf bufferB) {
        final int aLen = bufferA.readableBytes();
        final int bLen = bufferB.readableBytes();
        final int minLength = Math.min(aLen, bLen);
        final int uintCount = minLength >>> 2;
        final int byteCount = minLength & 0x3;
        int aIndex = bufferA.readerIndex();
        int bIndex = bufferB.readerIndex();
        if (bufferA.order() == bufferB.order()) {
            for (int i = uintCount; i > 0; --i) {
                final long va = bufferA.getUnsignedInt(aIndex);
                final long vb = bufferB.getUnsignedInt(bIndex);
                if (va > vb) {
                    return 1;
                }
                if (va < vb) {
                    return -1;
                }
                aIndex += 4;
                bIndex += 4;
            }
        }
        else {
            for (int i = uintCount; i > 0; --i) {
                final long va = bufferA.getUnsignedInt(aIndex);
                final long vb = swapInt(bufferB.getInt(bIndex)) & 0xFFFFFFFFL;
                if (va > vb) {
                    return 1;
                }
                if (va < vb) {
                    return -1;
                }
                aIndex += 4;
                bIndex += 4;
            }
        }
        for (int i = byteCount; i > 0; --i) {
            final short va2 = bufferA.getUnsignedByte(aIndex);
            final short vb2 = bufferB.getUnsignedByte(bIndex);
            if (va2 > vb2) {
                return 1;
            }
            if (va2 < vb2) {
                return -1;
            }
            ++aIndex;
            ++bIndex;
        }
        return aLen - bLen;
    }
    
    public static int indexOf(final ByteBuf buffer, final int fromIndex, final int toIndex, final byte value) {
        if (fromIndex <= toIndex) {
            return firstIndexOf(buffer, fromIndex, toIndex, value);
        }
        return lastIndexOf(buffer, fromIndex, toIndex, value);
    }
    
    public static int indexOf(final ByteBuf buffer, final int fromIndex, final int toIndex, final ByteBufIndexFinder indexFinder) {
        if (fromIndex <= toIndex) {
            return firstIndexOf(buffer, fromIndex, toIndex, indexFinder);
        }
        return lastIndexOf(buffer, fromIndex, toIndex, indexFinder);
    }
    
    public static short swapShort(final short value) {
        return Short.reverseBytes(value);
    }
    
    public static int swapMedium(final int value) {
        int swapped = (value << 16 & 0xFF0000) | (value & 0xFF00) | (value >>> 16 & 0xFF);
        if ((swapped & 0x800000) != 0x0) {
            swapped |= 0xFF000000;
        }
        return swapped;
    }
    
    public static int swapInt(final int value) {
        return Integer.reverseBytes(value);
    }
    
    public static long swapLong(final long value) {
        return Long.reverseBytes(value);
    }
    
    private static int firstIndexOf(final ByteBuf buffer, int fromIndex, final int toIndex, final byte value) {
        fromIndex = Math.max(fromIndex, 0);
        if (fromIndex >= toIndex || buffer.capacity() == 0) {
            return -1;
        }
        for (int i = fromIndex; i < toIndex; ++i) {
            if (buffer.getByte(i) == value) {
                return i;
            }
        }
        return -1;
    }
    
    private static int lastIndexOf(final ByteBuf buffer, int fromIndex, final int toIndex, final byte value) {
        fromIndex = Math.min(fromIndex, buffer.capacity());
        if (fromIndex < 0 || buffer.capacity() == 0) {
            return -1;
        }
        for (int i = fromIndex - 1; i >= toIndex; --i) {
            if (buffer.getByte(i) == value) {
                return i;
            }
        }
        return -1;
    }
    
    private static int firstIndexOf(final ByteBuf buffer, int fromIndex, final int toIndex, final ByteBufIndexFinder indexFinder) {
        fromIndex = Math.max(fromIndex, 0);
        if (fromIndex >= toIndex || buffer.capacity() == 0) {
            return -1;
        }
        for (int i = fromIndex; i < toIndex; ++i) {
            if (indexFinder.find(buffer, i)) {
                return i;
            }
        }
        return -1;
    }
    
    private static int lastIndexOf(final ByteBuf buffer, int fromIndex, final int toIndex, final ByteBufIndexFinder indexFinder) {
        fromIndex = Math.min(fromIndex, buffer.capacity());
        if (fromIndex < 0 || buffer.capacity() == 0) {
            return -1;
        }
        for (int i = fromIndex - 1; i >= toIndex; --i) {
            if (indexFinder.find(buffer, i)) {
                return i;
            }
        }
        return -1;
    }
    
    static ByteBuffer encodeString(final CharBuffer src, final Charset charset) {
        final CharsetEncoder encoder = CharsetUtil.getEncoder(charset);
        final ByteBuffer dst = ByteBuffer.allocate((int)(src.remaining() * encoder.maxBytesPerChar()));
        try {
            CoderResult cr = encoder.encode(src, dst, true);
            if (!cr.isUnderflow()) {
                cr.throwException();
            }
            cr = encoder.flush(dst);
            if (!cr.isUnderflow()) {
                cr.throwException();
            }
        }
        catch (CharacterCodingException x) {
            throw new IllegalStateException(x);
        }
        dst.flip();
        return dst;
    }
    
    static String decodeString(final ByteBuffer src, final Charset charset) {
        final CharsetDecoder decoder = CharsetUtil.getDecoder(charset);
        final CharBuffer dst = CharBuffer.allocate((int)(src.remaining() * decoder.maxCharsPerByte()));
        try {
            CoderResult cr = decoder.decode(src, dst, true);
            if (!cr.isUnderflow()) {
                cr.throwException();
            }
            cr = decoder.flush(dst);
            if (!cr.isUnderflow()) {
                cr.throwException();
            }
        }
        catch (CharacterCodingException x) {
            throw new IllegalStateException(x);
        }
        return dst.flip().toString();
    }
    
    static {
        HEXDUMP_TABLE = new char[1024];
        final char[] DIGITS = "0123456789abcdef".toCharArray();
        for (int i = 0; i < 256; ++i) {
            BufUtil.HEXDUMP_TABLE[i << 1] = DIGITS[i >>> 4 & 0xF];
            BufUtil.HEXDUMP_TABLE[(i << 1) + 1] = DIGITS[i & 0xF];
        }
    }
}
