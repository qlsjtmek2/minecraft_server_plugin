// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.asm;

public class ByteVector
{
    byte[] data;
    int length;
    
    public ByteVector() {
        this.data = new byte[64];
    }
    
    public ByteVector(final int initialSize) {
        this.data = new byte[initialSize];
    }
    
    public ByteVector putByte(final int b) {
        int length = this.length;
        if (length + 1 > this.data.length) {
            this.enlarge(1);
        }
        this.data[length++] = (byte)b;
        this.length = length;
        return this;
    }
    
    ByteVector put11(final int b1, final int b2) {
        int length = this.length;
        if (length + 2 > this.data.length) {
            this.enlarge(2);
        }
        final byte[] data = this.data;
        data[length++] = (byte)b1;
        data[length++] = (byte)b2;
        this.length = length;
        return this;
    }
    
    public ByteVector putShort(final int s) {
        int length = this.length;
        if (length + 2 > this.data.length) {
            this.enlarge(2);
        }
        final byte[] data = this.data;
        data[length++] = (byte)(s >>> 8);
        data[length++] = (byte)s;
        this.length = length;
        return this;
    }
    
    ByteVector put12(final int b, final int s) {
        int length = this.length;
        if (length + 3 > this.data.length) {
            this.enlarge(3);
        }
        final byte[] data = this.data;
        data[length++] = (byte)b;
        data[length++] = (byte)(s >>> 8);
        data[length++] = (byte)s;
        this.length = length;
        return this;
    }
    
    public ByteVector putInt(final int i) {
        int length = this.length;
        if (length + 4 > this.data.length) {
            this.enlarge(4);
        }
        final byte[] data = this.data;
        data[length++] = (byte)(i >>> 24);
        data[length++] = (byte)(i >>> 16);
        data[length++] = (byte)(i >>> 8);
        data[length++] = (byte)i;
        this.length = length;
        return this;
    }
    
    public ByteVector putLong(final long l) {
        int length = this.length;
        if (length + 8 > this.data.length) {
            this.enlarge(8);
        }
        final byte[] data = this.data;
        int i = (int)(l >>> 32);
        data[length++] = (byte)(i >>> 24);
        data[length++] = (byte)(i >>> 16);
        data[length++] = (byte)(i >>> 8);
        data[length++] = (byte)i;
        i = (int)l;
        data[length++] = (byte)(i >>> 24);
        data[length++] = (byte)(i >>> 16);
        data[length++] = (byte)(i >>> 8);
        data[length++] = (byte)i;
        this.length = length;
        return this;
    }
    
    public ByteVector putUTF8(final String s) {
        final int charLength = s.length();
        if (this.length + 2 + charLength > this.data.length) {
            this.enlarge(2 + charLength);
        }
        int len = this.length;
        byte[] data = this.data;
        data[len++] = (byte)(charLength >>> 8);
        data[len++] = (byte)charLength;
        for (int i = 0; i < charLength; ++i) {
            char c = s.charAt(i);
            if (c < '\u0001' || c > '\u007f') {
                int byteLength = i;
                for (int j = i; j < charLength; ++j) {
                    c = s.charAt(j);
                    if (c >= '\u0001' && c <= '\u007f') {
                        ++byteLength;
                    }
                    else if (c > '\u07ff') {
                        byteLength += 3;
                    }
                    else {
                        byteLength += 2;
                    }
                }
                data[this.length] = (byte)(byteLength >>> 8);
                data[this.length + 1] = (byte)byteLength;
                if (this.length + 2 + byteLength > data.length) {
                    this.length = len;
                    this.enlarge(2 + byteLength);
                    data = this.data;
                }
                for (int j = i; j < charLength; ++j) {
                    c = s.charAt(j);
                    if (c >= '\u0001' && c <= '\u007f') {
                        data[len++] = (byte)c;
                    }
                    else if (c > '\u07ff') {
                        data[len++] = (byte)('\u00e0' | (c >> 12 & '\u000f'));
                        data[len++] = (byte)('\u0080' | (c >> 6 & '?'));
                        data[len++] = (byte)('\u0080' | (c & '?'));
                    }
                    else {
                        data[len++] = (byte)('\u00c0' | (c >> 6 & '\u001f'));
                        data[len++] = (byte)('\u0080' | (c & '?'));
                    }
                }
                break;
            }
            data[len++] = (byte)c;
        }
        this.length = len;
        return this;
    }
    
    public ByteVector putByteArray(final byte[] b, final int off, final int len) {
        if (this.length + len > this.data.length) {
            this.enlarge(len);
        }
        if (b != null) {
            System.arraycopy(b, off, this.data, this.length, len);
        }
        this.length += len;
        return this;
    }
    
    private void enlarge(final int size) {
        final int length1 = 2 * this.data.length;
        final int length2 = this.length + size;
        final byte[] newData = new byte[(length1 > length2) ? length1 : length2];
        System.arraycopy(this.data, 0, newData, 0, this.length);
        this.data = newData;
    }
}
