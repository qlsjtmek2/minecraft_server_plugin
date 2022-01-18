// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene.cluster;

import java.io.File;
import com.avaje.ebeaninternal.server.lucene.LIndexFileInfo;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import com.avaje.ebeaninternal.server.lucene.LIndexCommitInfo;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import java.util.logging.Level;
import java.io.IOException;
import java.io.InputStream;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.Socket;
import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import java.io.DataOutput;
import java.io.DataInput;
import java.io.OutputStream;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import java.util.logging.Logger;

public class SLuceneClusterSocketRequest implements Runnable, SLuceneSocketMessageTypes
{
    private static final Logger logger;
    private final LuceneIndexManager manager;
    private final OutputStream os;
    private final DataInput dataInput;
    private final DataOutput dataOutput;
    private final String serverName;
    private final short msgType;
    private final String idxName;
    private final long remoteIndexVersion;
    
    public SLuceneClusterSocketRequest(final ClusterManager clusterManager, final Socket socket) throws IOException {
        this.os = socket.getOutputStream();
        final InputStream is = socket.getInputStream();
        this.dataInput = new DataInputStream(is);
        this.dataOutput = new DataOutputStream(this.os);
        this.serverName = this.dataInput.readUTF();
        this.msgType = this.dataInput.readShort();
        this.idxName = this.dataInput.readUTF();
        this.remoteIndexVersion = this.dataInput.readLong();
        final SpiEbeanServer server = (SpiEbeanServer)clusterManager.getServer(this.serverName);
        this.manager = server.getLuceneIndexManager();
    }
    
    public void run() {
        try {
            switch (this.msgType) {
                case 2: {
                    this.obtainCommit();
                    break;
                }
                case 3: {
                    this.releaseCommit();
                    break;
                }
                case 4: {
                    this.getFile();
                    break;
                }
                default: {
                    throw new IOException("Invalid msgType " + this.msgType);
                }
            }
        }
        catch (IOException e) {
            final String msg = "Error processing msg " + this.msgType + " " + this.idxName;
            SLuceneClusterSocketRequest.logger.log(Level.SEVERE, msg, e);
        }
        finally {
            this.flush();
        }
    }
    
    private void flush() {
        try {
            this.os.flush();
        }
        catch (IOException e) {
            final String msg = "Error flushing Socket OuputStream";
            SLuceneClusterSocketRequest.logger.log(Level.SEVERE, msg, e);
        }
        try {
            this.os.close();
        }
        catch (IOException e) {
            final String msg = "Error closing Socket OuputStream";
            SLuceneClusterSocketRequest.logger.log(Level.SEVERE, msg, e);
        }
    }
    
    private void releaseCommit() throws IOException {
        final LIndex index = this.manager.getIndex(this.idxName);
        index.releaseIndexCommit(this.remoteIndexVersion);
        this.dataOutput.writeBoolean(true);
    }
    
    private void obtainCommit() throws IOException {
        final LIndex index = this.manager.getIndex(this.idxName);
        final LIndexCommitInfo commitInfo = index.obtainLastIndexCommitIfNewer(this.remoteIndexVersion);
        if (commitInfo == null) {
            this.dataOutput.writeBoolean(false);
        }
        else {
            this.dataOutput.writeBoolean(true);
            commitInfo.write(this.dataOutput);
        }
    }
    
    private void getFile() throws IOException {
        final LIndex index = this.manager.getIndex(this.idxName);
        final String fileName = this.dataInput.readUTF();
        final LIndexFileInfo fileInfo = index.getFile(this.remoteIndexVersion, fileName);
        final File f = fileInfo.getFile();
        if (!f.exists()) {
            this.dataOutput.writeBoolean(false);
        }
        else {
            this.dataOutput.writeBoolean(true);
            final FileInputStream fis = new FileInputStream(f);
            try {
                final byte[] buf = new byte[2048];
                final BufferedInputStream bis = new BufferedInputStream(fis);
                int len = 0;
                while ((len = bis.read(buf)) > -1) {
                    this.dataOutput.write(buf, 0, len);
                }
            }
            finally {
                try {
                    fis.close();
                }
                catch (IOException e) {
                    final String msg = "Error closing InputStream on " + f.getAbsolutePath();
                    SLuceneClusterSocketRequest.logger.log(Level.SEVERE, msg, e);
                }
            }
        }
    }
    
    static {
        logger = Logger.getLogger(SLuceneClusterSocketRequest.class.getName());
    }
}
