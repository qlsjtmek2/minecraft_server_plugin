// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster;

import java.io.IOException;
import com.avaje.ebeaninternal.server.lucene.LIndex;

public interface LuceneClusterIndexSync
{
    boolean sync(final LIndex p0, final String p1) throws IOException;
    
    boolean isMaster();
    
    Mode getMode();
    
    void setMode(final Mode p0);
    
    String getMasterHost();
    
    void setMasterHost(final String p0);
    
    public enum Mode
    {
        MASTER_MODE, 
        SLAVE_MODE;
    }
}
