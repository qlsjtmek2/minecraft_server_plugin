// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster;

import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;

public class BinaryMessage
{
    public static final int TYPE_MSGCONTROL = 0;
    public static final int TYPE_BEANIUD = 1;
    public static final int TYPE_TABLEIUD = 2;
    public static final int TYPE_BEANDELTA = 3;
    public static final int TYPE_BEANPATHUPDATE = 4;
    public static final int TYPE_INDEX_INVALIDATE = 6;
    public static final int TYPE_INDEX = 7;
    public static final int TYPE_MSGACK = 8;
    public static final int TYPE_MSGRESEND = 9;
    private final ByteArrayOutputStream buffer;
    private final DataOutputStream os;
    private byte[] bytes;
    
    public BinaryMessage(final int bufSize) {
        this.buffer = new ByteArrayOutputStream(bufSize);
        this.os = new DataOutputStream(this.buffer);
    }
    
    public DataOutputStream getOs() {
        return this.os;
    }
    
    public byte[] getByteArray() {
        if (this.bytes == null) {
            this.bytes = this.buffer.toByteArray();
        }
        return this.bytes;
    }
}
