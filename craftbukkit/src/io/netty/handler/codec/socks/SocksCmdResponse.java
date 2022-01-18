// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;

public final class SocksCmdResponse extends SocksResponse
{
    private final SocksCmdStatus cmdStatus;
    private final SocksAddressType addressType;
    private static final byte[] IPv4_HOSTNAME_ZEROED;
    private static final byte[] IPv6_HOSTNAME_ZEROED;
    
    public SocksCmdResponse(final SocksCmdStatus cmdStatus, final SocksAddressType addressType) {
        super(SocksResponseType.CMD);
        if (cmdStatus == null) {
            throw new NullPointerException("cmdStatus");
        }
        if (addressType == null) {
            throw new NullPointerException("addressType");
        }
        this.cmdStatus = cmdStatus;
        this.addressType = addressType;
    }
    
    public SocksCmdStatus cmdStatus() {
        return this.cmdStatus;
    }
    
    public SocksAddressType addressType() {
        return this.addressType;
    }
    
    @Override
    public void encodeAsByteBuf(final ByteBuf byteBuf) {
        byteBuf.writeByte(this.protocolVersion().byteValue());
        byteBuf.writeByte(this.cmdStatus.byteValue());
        byteBuf.writeByte(0);
        byteBuf.writeByte(this.addressType.byteValue());
        switch (this.addressType) {
            case IPv4: {
                byteBuf.writeBytes(SocksCmdResponse.IPv4_HOSTNAME_ZEROED);
                byteBuf.writeShort(0);
                break;
            }
            case DOMAIN: {
                byteBuf.writeByte(1);
                byteBuf.writeByte(0);
                byteBuf.writeShort(0);
                break;
            }
            case IPv6: {
                byteBuf.writeBytes(SocksCmdResponse.IPv6_HOSTNAME_ZEROED);
                byteBuf.writeShort(0);
                break;
            }
        }
    }
    
    static {
        IPv4_HOSTNAME_ZEROED = new byte[] { 0, 0, 0, 0 };
        IPv6_HOSTNAME_ZEROED = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    }
}
