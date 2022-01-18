// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.bean;

import java.io.Serializable;

public final class CallStack implements Serializable
{
    private static final long serialVersionUID = -8590644046907438579L;
    private final String zeroHash;
    private final String pathHash;
    private final StackTraceElement[] callStack;
    private static final int radix = 64;
    private static final int mask = 63;
    private static final char[] intToBase64;
    
    public CallStack(final StackTraceElement[] callStack) {
        this.callStack = callStack;
        this.zeroHash = enc(callStack[0].hashCode());
        int hc = 0;
        for (int i = 1; i < callStack.length; ++i) {
            hc = 31 * hc + callStack[i].hashCode();
        }
        this.pathHash = enc(hc);
    }
    
    public StackTraceElement getFirstStackTraceElement() {
        return this.callStack[0];
    }
    
    public StackTraceElement[] getCallStack() {
        return this.callStack;
    }
    
    public String getZeroHash() {
        return this.zeroHash;
    }
    
    public String getPathHash() {
        return this.pathHash;
    }
    
    public String toString() {
        return this.zeroHash + ":" + this.pathHash + ":" + this.callStack[0];
    }
    
    public String getOriginKey(final int queryHash) {
        return this.zeroHash + "." + enc(queryHash) + "." + this.pathHash;
    }
    
    public static String enc(int i) {
        final char[] buf = new char[32];
        int charPos = 32;
        do {
            buf[--charPos] = CallStack.intToBase64[i & 0x3F];
            i >>>= 6;
        } while (i != 0);
        return new String(buf, charPos, 32 - charPos);
    }
    
    static {
        intToBase64 = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_' };
    }
}
