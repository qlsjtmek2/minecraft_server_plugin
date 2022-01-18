// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.socks;

import io.netty.channel.ChannelHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class SocksAuthResponseDecoder extends ReplayingDecoder<State>
{
    private static final String name = "SOCKS_AUTH_RESPONSE_DECODER";
    private SocksSubnegotiationVersion version;
    private SocksAuthStatus authStatus;
    private SocksResponse msg;
    
    public static String getName() {
        return "SOCKS_AUTH_RESPONSE_DECODER";
    }
    
    public SocksAuthResponseDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
        this.msg = SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE;
    }
    
    @Override
    protected Object decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws Exception {
        switch (this.state()) {
            case CHECK_PROTOCOL_VERSION: {
                this.version = SocksSubnegotiationVersion.fromByte(byteBuf.readByte());
                if (this.version != SocksSubnegotiationVersion.AUTH_PASSWORD) {
                    break;
                }
                this.checkpoint(State.READ_AUTH_RESPONSE);
            }
            case READ_AUTH_RESPONSE: {
                this.authStatus = SocksAuthStatus.fromByte(byteBuf.readByte());
                this.msg = new SocksAuthResponse(this.authStatus);
                break;
            }
        }
        channelHandlerContext.pipeline().remove(this);
        return this.msg;
    }
    
    enum State
    {
        CHECK_PROTOCOL_VERSION, 
        READ_AUTH_RESPONSE;
    }
}
