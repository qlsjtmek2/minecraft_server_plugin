// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.socks;

import io.netty.channel.ChannelHandler;
import io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class SocksAuthRequestDecoder extends ReplayingDecoder<State>
{
    private static final String name = "SOCKS_AUTH_REQUEST_DECODER";
    private SocksSubnegotiationVersion version;
    private int fieldLength;
    private String username;
    private String password;
    private SocksRequest msg;
    
    public static String getName() {
        return "SOCKS_AUTH_REQUEST_DECODER";
    }
    
    public SocksAuthRequestDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
        this.msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;
    }
    
    @Override
    protected Object decode(final ChannelHandlerContext ctx, final ByteBuf byteBuf) throws Exception {
        switch (this.state()) {
            case CHECK_PROTOCOL_VERSION: {
                this.version = SocksSubnegotiationVersion.fromByte(byteBuf.readByte());
                if (this.version != SocksSubnegotiationVersion.AUTH_PASSWORD) {
                    break;
                }
                this.checkpoint(State.READ_USERNAME);
            }
            case READ_USERNAME: {
                this.fieldLength = byteBuf.readByte();
                this.username = byteBuf.readBytes(this.fieldLength).toString(CharsetUtil.US_ASCII);
                this.checkpoint(State.READ_PASSWORD);
            }
            case READ_PASSWORD: {
                this.fieldLength = byteBuf.readByte();
                this.password = byteBuf.readBytes(this.fieldLength).toString(CharsetUtil.US_ASCII);
                this.msg = new SocksAuthRequest(this.username, this.password);
                break;
            }
        }
        ctx.pipeline().remove(this);
        return this.msg;
    }
    
    enum State
    {
        CHECK_PROTOCOL_VERSION, 
        READ_USERNAME, 
        READ_PASSWORD;
    }
}
