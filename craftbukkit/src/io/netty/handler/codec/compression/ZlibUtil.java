// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.compression;

import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZStream;

final class ZlibUtil
{
    static void fail(final ZStream z, final String message, final int resultCode) {
        throw exception(z, message, resultCode);
    }
    
    static CompressionException exception(final ZStream z, final String message, final int resultCode) {
        return new CompressionException(message + " (" + resultCode + ')' + ((z.msg != null) ? (": " + z.msg) : ""));
    }
    
    static JZlib.WrapperType convertWrapperType(final ZlibWrapper wrapper) {
        JZlib.WrapperType convertedWrapperType = null;
        switch (wrapper) {
            case NONE: {
                convertedWrapperType = JZlib.W_NONE;
                break;
            }
            case ZLIB: {
                convertedWrapperType = JZlib.W_ZLIB;
                break;
            }
            case GZIP: {
                convertedWrapperType = JZlib.W_GZIP;
                break;
            }
            case ZLIB_OR_NONE: {
                convertedWrapperType = JZlib.W_ANY;
                break;
            }
            default: {
                throw new Error();
            }
        }
        return convertedWrapperType;
    }
}
