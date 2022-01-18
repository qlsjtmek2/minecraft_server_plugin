// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.compression;

import io.netty.util.internal.PlatformDependent;

public final class ZlibCodecFactory
{
    public static ZlibEncoder newZlibEncoder(final int compressionLevel) {
        if (PlatformDependent.javaVersion() < 7) {
            return new JZlibEncoder(compressionLevel);
        }
        return new JdkZlibEncoder(compressionLevel);
    }
    
    public static ZlibEncoder newZlibEncoder(final ZlibWrapper wrapper) {
        if (PlatformDependent.javaVersion() < 7) {
            return new JZlibEncoder(wrapper);
        }
        return new JdkZlibEncoder(wrapper);
    }
    
    public static ZlibEncoder newZlibEncoder(final ZlibWrapper wrapper, final int compressionLevel) {
        if (PlatformDependent.javaVersion() < 7) {
            return new JZlibEncoder(wrapper, compressionLevel);
        }
        return new JdkZlibEncoder(wrapper, compressionLevel);
    }
    
    public static ZlibEncoder newZlibEncoder(final ZlibWrapper wrapper, final int compressionLevel, final int windowBits, final int memLevel) {
        if (PlatformDependent.javaVersion() < 7) {
            return new JZlibEncoder(wrapper, compressionLevel, windowBits, memLevel);
        }
        return new JdkZlibEncoder(wrapper, compressionLevel);
    }
    
    public static ZlibEncoder newZlibEncoder(final byte[] dictionary) {
        if (PlatformDependent.javaVersion() < 7) {
            return new JZlibEncoder(dictionary);
        }
        return new JdkZlibEncoder(dictionary);
    }
    
    public static ZlibEncoder newZlibEncoder(final int compressionLevel, final byte[] dictionary) {
        if (PlatformDependent.javaVersion() < 7) {
            return new JZlibEncoder(compressionLevel, dictionary);
        }
        return new JdkZlibEncoder(compressionLevel, dictionary);
    }
    
    public static ZlibEncoder newZlibEncoder(final int compressionLevel, final int windowBits, final int memLevel, final byte[] dictionary) {
        if (PlatformDependent.javaVersion() < 7) {
            return new JZlibEncoder(compressionLevel, windowBits, memLevel, dictionary);
        }
        return new JdkZlibEncoder(compressionLevel, dictionary);
    }
    
    public static ZlibDecoder newZlibDecoder() {
        return new JZlibDecoder();
    }
    
    public static ZlibDecoder newZlibDecoder(final ZlibWrapper wrapper) {
        return new JZlibDecoder(wrapper);
    }
    
    public static ZlibDecoder newZlibDecoder(final byte[] dictionary) {
        return new JZlibDecoder(dictionary);
    }
}
