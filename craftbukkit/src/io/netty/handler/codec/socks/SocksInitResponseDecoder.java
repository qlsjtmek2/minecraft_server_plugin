// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.socks;

import io.netty.channel.ChannelHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class SocksInitResponseDecoder extends ReplayingDecoder<State>
{
    private static final String name = "SOCKS_INIT_RESPONSE_DECODER";
    private SocksProtocolVersion version;
    private SocksAuthScheme authScheme;
    private SocksResponse msg;
    
    public static String getName() {
        return "SOCKS_INIT_RESPONSE_DECODER";
    }
    
    public SocksInitResponseDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
        this.msg = SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE;
    }
    
    @Override
    protected SocksResponse decode(final ChannelHandlerContext ctx, final ByteBuf byteBuf) throws Exception {
        switch (this.state()) {
            case CHECK_PROTOCOL_VERSION: {
                this.version = SocksProtocolVersion.fromByte(byteBuf.readByte());
                if (this.version != SocksProtocolVersion.SOCKS5) {
                    break;
                }
                this.checkpoint(State.READ_PREFFERED_AUTH_TYPE);
            }
            case READ_PREFFERED_AUTH_TYPE: {
                this.authScheme = SocksAuthScheme.fromByte(byteBuf.readByte());
                this.msg = new SocksInitResponse(this.authScheme);
                break;
            }
        }
        ctx.pipeline().remove(this);
        return this.msg;
    }
    
    enum State
    {
        CHECK_PROTOCOL_VERSION, 
        READ_PREFFERED_AUTH_TYPE;
    }
}
