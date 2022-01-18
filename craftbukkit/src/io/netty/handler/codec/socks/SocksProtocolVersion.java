// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.socks;

public enum SocksProtocolVersion
{
    SOCKS4a((byte)4), 
    SOCKS5((byte)5), 
    UNKNOWN((byte)(-1));
    
    private final byte b;
    
    private SocksProtocolVersion(final byte b) {
        this.b = b;
    }
    
    public static SocksProtocolVersion fromByte(final byte b) {
        for (final SocksProtocolVersion code : values()) {
            if (code.b == b) {
                return code;
            }
        }
        return SocksProtocolVersion.UNKNOWN;
    }
    
    public byte byteValue() {
        return this.b;
    }
}
