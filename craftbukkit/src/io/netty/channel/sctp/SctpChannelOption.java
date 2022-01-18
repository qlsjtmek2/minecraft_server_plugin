// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.sctp;

import java.net.SocketAddress;
import java.util.List;
import io.netty.channel.ChannelOption;

public class SctpChannelOption<T> extends ChannelOption<T>
{
    public static final SctpChannelOption<Boolean> SCTP_DISABLE_FRAGMENTS;
    public static final SctpChannelOption<Boolean> SCTP_EXPLICIT_COMPLETE;
    public static final SctpChannelOption<Integer> SCTP_FRAGMENT_INTERLEAVE;
    public static final SctpChannelOption<List<Integer>> SCTP_INIT_MAXSTREAMS;
    public static final SctpChannelOption<Boolean> SCTP_NODELAY;
    public static final SctpChannelOption<SocketAddress> SCTP_PRIMARY_ADDR;
    public static final SctpChannelOption<SocketAddress> SCTP_SET_PEER_PRIMARY_ADDR;
    
    protected SctpChannelOption(final String name) {
        super(name);
    }
    
    static {
        SCTP_DISABLE_FRAGMENTS = new SctpChannelOption<Boolean>("SCTP_DISABLE_FRAGMENTS");
        SCTP_EXPLICIT_COMPLETE = new SctpChannelOption<Boolean>("SCTP_EXPLICIT_COMPLETE");
        SCTP_FRAGMENT_INTERLEAVE = new SctpChannelOption<Integer>("SCTP_FRAGMENT_INTERLEAVE");
        SCTP_INIT_MAXSTREAMS = new SctpChannelOption<List<Integer>>("SCTP_INIT_MAXSTREAMS") {
            @Override
            public void validate(final List<Integer> value) {
                super.validate(value);
                if (value.size() != 2) {
                    throw new IllegalArgumentException("value must be a List of 2 Integers: " + value);
                }
                if (value.get(0) == null) {
                    throw new NullPointerException("value[0]");
                }
                if (value.get(1) == null) {
                    throw new NullPointerException("value[1]");
                }
            }
        };
        SCTP_NODELAY = new SctpChannelOption<Boolean>("SCTP_NODELAY");
        SCTP_PRIMARY_ADDR = new SctpChannelOption<SocketAddress>("SCTP_PRIMARY_ADDR");
        SCTP_SET_PEER_PRIMARY_ADDR = new SctpChannelOption<SocketAddress>("SCTP_SET_PEER_PRIMARY_ADDR");
    }
}
