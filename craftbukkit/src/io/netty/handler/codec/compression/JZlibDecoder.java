// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZStream;
import com.jcraft.jzlib.Inflater;

public class JZlibDecoder extends ZlibDecoder
{
    private final Inflater z;
    private byte[] dictionary;
    private volatile boolean finished;
    
    public JZlibDecoder() {
        this(ZlibWrapper.ZLIB);
    }
    
    public JZlibDecoder(final ZlibWrapper wrapper) {
        this.z = new Inflater();
        if (wrapper == null) {
            throw new NullPointerException("wrapper");
        }
        final int resultCode = this.z.init(ZlibUtil.convertWrapperType(wrapper));
        if (resultCode != 0) {
            ZlibUtil.fail((ZStream)this.z, "initialization failure", resultCode);
        }
    }
    
    public JZlibDecoder(final byte[] dictionary) {
        this.z = new Inflater();
        if (dictionary == null) {
            throw new NullPointerException("dictionary");
        }
        this.dictionary = dictionary;
        final int resultCode = this.z.inflateInit(JZlib.W_ZLIB);
        if (resultCode != 0) {
            ZlibUtil.fail((ZStream)this.z, "initialization failure", resultCode);
        }
    }
    
    @Override
    public boolean isClosed() {
        return this.finished;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final ByteBuf out) throws Exception {
        if (!in.isReadable()) {
            return;
        }
        try {
            final int inputLength = in.readableBytes();
            final boolean inHasArray = in.hasArray();
            this.z.avail_in = inputLength;
            if (inHasArray) {
                this.z.next_in = in.array();
                this.z.next_in_index = in.arrayOffset() + in.readerIndex();
            }
            else {
                final byte[] array = new byte[inputLength];
                in.readBytes(array);
                this.z.next_in = array;
                this.z.next_in_index = 0;
            }
            final int oldNextInIndex = this.z.next_in_index;
            final int maxOutputLength = inputLength << 1;
            final boolean outHasArray = out.hasArray();
            Label_0147: {
                if (outHasArray) {
                    break Label_0147;
                }
                this.z.next_out = new byte[maxOutputLength];
                try {
                Label_0444:
                    while (true) {
                        this.z.avail_out = maxOutputLength;
                        if (outHasArray) {
                            out.ensureWritable(maxOutputLength);
                            this.z.next_out = out.array();
                            this.z.next_out_index = out.arrayOffset() + out.writerIndex();
                        }
                        else {
                            this.z.next_out_index = 0;
                        }
                        final int oldNextOutIndex = this.z.next_out_index;
                        int resultCode = this.z.inflate(2);
                        final int outputLength = this.z.next_out_index - oldNextOutIndex;
                        if (outputLength > 0) {
                            if (outHasArray) {
                                out.writerIndex(out.writerIndex() + outputLength);
                            }
                            else {
                                out.writeBytes(this.z.next_out, 0, outputLength);
                            }
                        }
                        switch (resultCode) {
                            case 2: {
                                if (this.dictionary == null) {
                                    ZlibUtil.fail((ZStream)this.z, "decompression failure", resultCode);
                                    continue;
                                }
                                resultCode = this.z.inflateSetDictionary(this.dictionary, this.dictionary.length);
                                if (resultCode != 0) {
                                    ZlibUtil.fail((ZStream)this.z, "failed to set the dictionary", resultCode);
                                    continue;
                                }
                                continue;
                            }
                            case 1: {
                                this.finished = true;
                                this.z.inflateEnd();
                                break Label_0444;
                            }
                            case 0: {
                                continue;
                            }
                            case -5: {
                                if (this.z.avail_in <= 0) {
                                    break Label_0444;
                                }
                                continue;
                            }
                            default: {
                                ZlibUtil.fail((ZStream)this.z, "decompression failure", resultCode);
                                continue;
                            }
                        }
                    }
                }
                finally {
                    if (inHasArray) {
                        in.skipBytes(this.z.next_in_index - oldNextInIndex);
                    }
                }
            }
        }
        finally {
            this.z.next_in = null;
            this.z.next_out = null;
        }
    }
}
