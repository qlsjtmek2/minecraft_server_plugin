// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.protobuf;

import io.netty.buffer.Unpooled;
import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler;
import com.google.protobuf.MessageLiteOrBuilder;
import io.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class ProtobufEncoder extends MessageToMessageEncoder<MessageLiteOrBuilder>
{
    @Override
    protected Object encode(final ChannelHandlerContext ctx, final MessageLiteOrBuilder msg) throws Exception {
        if (msg instanceof MessageLite) {
            return Unpooled.wrappedBuffer(((MessageLite)msg).toByteArray());
        }
        if (msg instanceof MessageLite.Builder) {
            return Unpooled.wrappedBuffer(((MessageLite.Builder)msg).build().toByteArray());
        }
        return null;
    }
}
