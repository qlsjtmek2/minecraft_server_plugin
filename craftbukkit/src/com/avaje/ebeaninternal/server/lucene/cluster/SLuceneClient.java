// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene.cluster;

import java.io.InputStream;
import java.io.IOException;
import java.io.DataOutput;
import com.avaje.ebeaninternal.server.lucene.LIndex;

public class SLuceneClient
{
    private final String serverName;
    private final LIndex index;
    private final long localVersion;
    private long remoteVersion;
    private final SocketClient client;
    
    public SLuceneClient(final String serverName, final SocketClient client, final long localVersion, final LIndex index) {
        this.serverName = serverName;
        this.client = client;
        this.localVersion = localVersion;
        this.index = index;
    }
    
    public void setRemoteVersion(final long remoteVersion) {
        this.remoteVersion = remoteVersion;
    }
    
    public LIndex getIndex() {
        return this.index;
    }
    
    public void disconnect() {
        this.client.disconnect();
    }
    
    public SocketClient getSocketClient() {
        return this.client;
    }
    
    private void sendMessageHeader(final short msgType, final long version) throws IOException {
        this.client.connect();
        this.client.initData();
        final DataOutput dataOutput = this.client.getDataOutput();
        dataOutput.writeUTF(this.serverName);
        dataOutput.writeShort(msgType);
        dataOutput.writeUTF(this.index.getName());
        dataOutput.writeLong(version);
    }
    
    private boolean sendMessageHeader2(final short msgType, final long version) throws IOException {
        this.sendMessageHeader(msgType, version);
        this.client.getOutputStream().flush();
        return this.client.getDataInput().readBoolean();
    }
    
    public boolean sendObtainCommit() throws IOException {
        return this.sendMessageHeader2((short)2, this.localVersion);
    }
    
    public void sendRelease() throws IOException {
        this.sendMessageHeader2((short)3, this.remoteVersion);
    }
    
    public InputStream sendGetFile(final String fileName) throws IOException {
        this.sendMessageHeader((short)4, this.remoteVersion);
        this.client.getDataOutput().writeUTF(fileName);
        this.client.getOutputStream().flush();
        final boolean exists = this.client.getDataInput().readBoolean();
        if (!exists) {
            return null;
        }
        return this.client.getInputStream();
    }
}
