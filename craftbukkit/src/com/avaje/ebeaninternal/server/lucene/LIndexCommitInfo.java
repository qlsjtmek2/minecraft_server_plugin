// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import java.io.File;
import java.util.Iterator;
import java.util.Collection;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.io.DataInput;
import org.apache.lucene.index.IndexCommit;
import java.util.List;

public class LIndexCommitInfo
{
    private final String indexDir;
    private final LIndexVersion version;
    private final List<LIndexFileInfo> fileInfo;
    
    public LIndexCommitInfo(final String indexDir, final IndexCommit indexCommit) {
        this.indexDir = indexDir;
        this.version = new LIndexVersion(indexCommit.getGeneration(), indexCommit.getVersion());
        this.fileInfo = this.createFileInfo(indexCommit);
    }
    
    public LIndexCommitInfo(final String indexDir, final LIndexVersion version, final List<LIndexFileInfo> fileInfo) {
        this.indexDir = indexDir;
        this.version = version;
        this.fileInfo = fileInfo;
    }
    
    public String toString() {
        return this.indexDir + " " + this.version + " " + this.fileInfo;
    }
    
    public LIndexVersion getVersion() {
        return this.version;
    }
    
    public List<LIndexFileInfo> getFileInfo() {
        return this.fileInfo;
    }
    
    public static LIndexCommitInfo read(final DataInput dataInput) throws IOException {
        final String idxDir = dataInput.readUTF();
        final long gen = dataInput.readLong();
        final long ver = dataInput.readLong();
        final int fileCount = dataInput.readInt();
        final List<LIndexFileInfo> fileInfo = new ArrayList<LIndexFileInfo>(fileCount);
        for (int i = 0; i < fileCount; ++i) {
            fileInfo.add(LIndexFileInfo.read(dataInput));
        }
        return new LIndexCommitInfo(idxDir, new LIndexVersion(gen, ver), fileInfo);
    }
    
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.indexDir);
        dataOutput.writeLong(this.version.getGeneration());
        dataOutput.writeLong(this.version.getVersion());
        final int fileCount = this.fileInfo.size();
        dataOutput.writeInt(fileCount);
        for (int i = 0; i < this.fileInfo.size(); ++i) {
            this.fileInfo.get(i).write(dataOutput);
        }
    }
    
    private List<LIndexFileInfo> createFileInfo(final IndexCommit indexCommit) {
        try {
            final Collection<String> fileNames = (Collection<String>)indexCommit.getFileNames();
            final List<LIndexFileInfo> files = new ArrayList<LIndexFileInfo>(fileNames.size());
            for (final String fileName : fileNames) {
                files.add(this.getFileInfo(fileName));
            }
            return files;
        }
        catch (IOException e) {
            throw new PersistenceLuceneException(e);
        }
    }
    
    private LIndexFileInfo getFileInfo(final String fileName) {
        final File f = new File(this.indexDir, fileName);
        return new LIndexFileInfo(f);
    }
}
