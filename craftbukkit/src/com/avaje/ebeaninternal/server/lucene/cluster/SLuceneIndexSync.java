// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene.cluster;

import java.io.IOException;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterIndexSync;

public class SLuceneIndexSync implements LuceneClusterIndexSync
{
    private Mode mode;
    private String masterHost;
    
    public boolean sync(final LIndex index, final String masterHost) throws IOException {
        final SLuceneClusterSocketClient c = new SLuceneClusterSocketClient(index);
        if (c.isSynchIndex(masterHost)) {
            c.transferFiles();
            index.refresh(true);
            return true;
        }
        return false;
    }
    
    public boolean isMaster() {
        return Mode.MASTER_MODE.equals(this.mode);
    }
    
    public String getMasterHost() {
        return this.masterHost;
    }
    
    public Mode getMode() {
        return this.mode;
    }
    
    public void setMasterHost(final String masterHost) {
        this.masterHost = masterHost;
    }
    
    public void setMode(final Mode mode) {
        this.mode = mode;
    }
}
