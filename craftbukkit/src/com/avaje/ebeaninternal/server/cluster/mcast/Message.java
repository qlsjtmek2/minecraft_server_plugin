// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.mcast;

import java.io.IOException;
import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;

public interface Message
{
    void writeBinaryMessage(final BinaryMessageList p0) throws IOException;
    
    boolean isControlMessage();
    
    String getToHostPort();
}
