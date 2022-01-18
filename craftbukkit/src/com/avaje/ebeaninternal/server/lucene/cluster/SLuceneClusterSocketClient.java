// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene.cluster;

import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.util.List;
import com.avaje.ebeaninternal.server.lucene.LIndexFileInfo;
import com.avaje.ebeaninternal.server.lucene.LIndexCommitInfo;
import com.avaje.ebeaninternal.server.lucene.LIndexVersion;
import java.io.IOException;
import java.util.logging.Level;
import java.util.ArrayList;
import java.io.File;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class SLuceneClusterSocketClient implements SLuceneSocketMessageTypes
{
    private static final Logger logger;
    private InetSocketAddress master;
    private LIndex index;
    private SLuceneClient client;
    private File tmpDir;
    private ArrayList<File> addFiles;
    private ArrayList<File> replaceFiles;
    
    public SLuceneClusterSocketClient(final LIndex index) {
        this.addFiles = new ArrayList<File>();
        this.replaceFiles = new ArrayList<File>();
        this.index = index;
    }
    
    public boolean isSynchIndex(final String masterHost) throws IOException {
        this.master = SocketClient.parseHostPort(masterHost);
        final LIndexVersion localVersion = this.index.getLastestVersion();
        System.out.println("-- Got localVersion " + localVersion);
        final SocketClient client = new SocketClient(this.master);
        final String serverName = this.index.getBeanDescriptor().getServerName();
        this.client = new SLuceneClient(serverName, client, localVersion.getVersion(), this.index);
        try {
            final LIndexCommitInfo commitInfo = this.getCommitInfo();
            if (commitInfo == null) {
                SLuceneClusterSocketClient.logger.info("Lucene index up to date [" + this.index.getName() + "]");
                return false;
            }
            this.getCommitFiles(localVersion, commitInfo);
            return true;
        }
        catch (IOException e) {
            final String msg = "Error synch'ing index " + this.index.getName();
            SLuceneClusterSocketClient.logger.log(Level.SEVERE, msg, e);
            throw e;
        }
    }
    
    public void transferFiles() {
        final File indexDir = this.index.getIndexDir();
        for (int i = 0; i < this.addFiles.size(); ++i) {
            final File addFile = this.addFiles.get(i);
            final File destFile = new File(indexDir, addFile.getName());
            addFile.renameTo(destFile);
        }
        this.tmpDir.delete();
    }
    
    private void getCommitFiles(final LIndexVersion localVersion, final LIndexCommitInfo commitInfo) throws IOException {
        try {
            this.client.setRemoteVersion(commitInfo.getVersion().getVersion());
            this.copyFiles(commitInfo);
        }
        finally {
            try {
                this.client.sendRelease();
            }
            catch (IOException e) {
                final String msg = "Error sending release for index " + this.client.getIndex();
                SLuceneClusterSocketClient.logger.log(Level.SEVERE, msg, e);
            }
        }
    }
    
    private void copyFiles(final LIndexCommitInfo commitInfo) throws IOException {
        final LIndex index = this.client.getIndex();
        final File indexDir = index.getIndexDir();
        this.tmpDir = new File(indexDir, "tmp-" + System.currentTimeMillis());
        if (!this.tmpDir.exists() && !this.tmpDir.mkdirs()) {
            final String msg = "Could not create directory tmpDir: " + this.tmpDir;
            throw new IOException(msg);
        }
        final List<LIndexFileInfo> fileInfo = commitInfo.getFileInfo();
        SLuceneClusterSocketClient.logger.info("Lucene index synchonizing from[" + this.master + "] ver[" + commitInfo.getVersion() + "] files[" + fileInfo + "]");
        for (int i = 0; i < fileInfo.size(); ++i) {
            final LIndexFileInfo fi = fileInfo.get(i);
            final String fileName = fi.getName();
            final LIndexFileInfo localFileInfo = index.getLocalFile(fileName);
            if (localFileInfo.exists()) {
                if (localFileInfo.isMatch(fi)) {
                    SLuceneClusterSocketClient.logger.info("... skip [" + fi.getName() + "]");
                }
                else {
                    SLuceneClusterSocketClient.logger.warning("Lucene index file [" + fi.getName() + "] exists but not match size or lastModified?");
                    this.downloadFile(false, fi);
                }
            }
            else {
                this.downloadFile(true, fi);
            }
        }
    }
    
    private void downloadFile(final boolean addFile, final LIndexFileInfo fi) throws IOException {
        try {
            final String fileName = fi.getName();
            final InputStream fileInputStream = this.client.sendGetFile(fileName);
            final BufferedInputStream bufIs = new BufferedInputStream(fileInputStream);
            final File fileOut = new File(this.tmpDir, fileName);
            final FileOutputStream os = new FileOutputStream(fileOut);
            final byte[] buf = new byte[2048];
            int totalLen = 0;
            int len = 0;
            while ((len = bufIs.read(buf)) > -1) {
                os.write(buf, 0, len);
                totalLen += len;
            }
            os.flush();
            os.close();
            fileOut.setLastModified(fi.getLastModified());
            System.out.println("got file len:" + totalLen + " " + fileName);
            if (addFile) {
                this.addFiles.add(fileOut);
            }
            else {
                this.replaceFiles.add(fileOut);
            }
        }
        finally {
            this.client.disconnect();
        }
    }
    
    private LIndexCommitInfo getCommitInfo() throws IOException {
        try {
            final boolean gotInfo = this.client.sendObtainCommit();
            if (!gotInfo) {
                return null;
            }
            return LIndexCommitInfo.read(this.client.getSocketClient().getDataInput());
        }
        finally {
            this.client.disconnect();
        }
    }
    
    static {
        logger = Logger.getLogger(SLuceneClusterSocketClient.class.getName());
    }
}
