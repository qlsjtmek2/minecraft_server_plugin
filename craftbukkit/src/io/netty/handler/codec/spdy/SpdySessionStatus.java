// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

public class SpdySessionStatus implements Comparable<SpdySessionStatus>
{
    public static final SpdySessionStatus OK;
    public static final SpdySessionStatus PROTOCOL_ERROR;
    public static final SpdySessionStatus INTERNAL_ERROR;
    private final int code;
    private final String statusPhrase;
    
    public static SpdySessionStatus valueOf(final int code) {
        switch (code) {
            case 0: {
                return SpdySessionStatus.OK;
            }
            case 1: {
                return SpdySessionStatus.PROTOCOL_ERROR;
            }
            case 2: {
                return SpdySessionStatus.INTERNAL_ERROR;
            }
            default: {
                return new SpdySessionStatus(code, "UNKNOWN (" + code + ')');
            }
        }
    }
    
    public SpdySessionStatus(final int code, final String statusPhrase) {
        if (statusPhrase == null) {
            throw new NullPointerException("statusPhrase");
        }
        this.code = code;
        this.statusPhrase = statusPhrase;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public String getStatusPhrase() {
        return this.statusPhrase;
    }
    
    @Override
    public int hashCode() {
        return this.getCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof SpdySessionStatus && this.getCode() == ((SpdySessionStatus)o).getCode();
    }
    
    @Override
    public String toString() {
        return this.getStatusPhrase();
    }
    
    @Override
    public int compareTo(final SpdySessionStatus o) {
        return this.getCode() - o.getCode();
    }
    
    static {
        OK = new SpdySessionStatus(0, "OK");
        PROTOCOL_ERROR = new SpdySessionStatus(1, "PROTOCOL_ERROR");
        INTERNAL_ERROR = new SpdySessionStatus(2, "INTERNAL_ERROR");
    }
}
