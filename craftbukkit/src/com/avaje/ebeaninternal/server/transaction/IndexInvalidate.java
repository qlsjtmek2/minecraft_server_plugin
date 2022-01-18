// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import java.io.DataOutputStream;
import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
import java.io.IOException;
import java.io.DataInput;

public class IndexInvalidate
{
    private final String indexName;
    
    public IndexInvalidate(final String indexName) {
        this.indexName = indexName;
    }
    
    public String getIndexName() {
        return this.indexName;
    }
    
    public int hashCode() {
        int hc = IndexInvalidate.class.hashCode();
        hc = hc * 31 + this.indexName.hashCode();
        return hc;
    }
    
    public boolean equals(final Object o) {
        return o instanceof IndexInvalidate && this.indexName.equals(((IndexInvalidate)o).indexName);
    }
    
    public static IndexInvalidate readBinaryMessage(final DataInput dataInput) throws IOException {
        final String indexName = dataInput.readUTF();
        return new IndexInvalidate(indexName);
    }
    
    public void writeBinaryMessage(final BinaryMessageList msgList) throws IOException {
        final BinaryMessage msg = new BinaryMessage(this.indexName.length() + 10);
        final DataOutputStream os = msg.getOs();
        os.writeInt(6);
        os.writeUTF(this.indexName);
        msgList.add(msg);
    }
}
