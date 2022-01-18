// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import java.io.DataOutputStream;
import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
import java.io.IOException;
import java.io.DataInput;

public class IndexEvent
{
    public static final int COMMIT_EVENT = 1;
    public static final int OPTIMISE_EVENT = 2;
    private final int eventType;
    private final String indexName;
    
    public IndexEvent(final int eventType, final String indexName) {
        this.eventType = eventType;
        this.indexName = indexName;
    }
    
    public int getEventType() {
        return this.eventType;
    }
    
    public String getIndexName() {
        return this.indexName;
    }
    
    public static IndexEvent readBinaryMessage(final DataInput dataInput) throws IOException {
        final int eventType = dataInput.readInt();
        final String indexName = dataInput.readUTF();
        return new IndexEvent(eventType, indexName);
    }
    
    public void writeBinaryMessage(final BinaryMessageList msgList) throws IOException {
        final BinaryMessage msg = new BinaryMessage(this.indexName.length() + 10);
        final DataOutputStream os = msg.getOs();
        os.writeInt(7);
        os.writeInt(this.eventType);
        os.writeUTF(this.indexName);
        msgList.add(msg);
    }
}
