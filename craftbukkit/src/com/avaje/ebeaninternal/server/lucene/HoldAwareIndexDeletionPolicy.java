// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import java.util.logging.Level;
import java.util.List;
import java.util.HashMap;
import org.apache.lucene.index.IndexCommit;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.lucene.index.IndexDeletionPolicy;

public class HoldAwareIndexDeletionPolicy implements IndexDeletionPolicy
{
    private static final Logger logger;
    private final Map<Long, CommitRefCount> commitRefCounts;
    private IndexCommit lastCommit;
    private final String indexDir;
    
    public HoldAwareIndexDeletionPolicy(final String indexDir) {
        this.commitRefCounts = new HashMap<Long, CommitRefCount>();
        this.indexDir = indexDir;
    }
    
    public void onInit(final List<? extends IndexCommit> commits) {
        this.onCommit(commits);
    }
    
    public void onCommit(final List<? extends IndexCommit> commits) {
        synchronized (this.commitRefCounts) {
            final int size = commits.size();
            this.lastCommit = (IndexCommit)commits.get(size - 1);
            for (int i = 0; i < size - 1; ++i) {
                final IndexCommit indexCommit = (IndexCommit)commits.get(i);
                if (!this.commitRefCounts.containsKey(indexCommit.getVersion())) {
                    this.potentialIndexCommitDelete(indexCommit);
                }
            }
        }
    }
    
    private void potentialIndexCommitDelete(final IndexCommit indexCommit) {
        indexCommit.delete();
    }
    
    public long getLastVersion() {
        synchronized (this.commitRefCounts) {
            if (this.lastCommit == null) {
                return 0L;
            }
            return this.lastCommit.getVersion();
        }
    }
    
    public LIndexCommitInfo obtainLastIndexCommitIfNewer(final long remoteIndexVersion) {
        synchronized (this.commitRefCounts) {
            if (remoteIndexVersion != 0L && remoteIndexVersion == this.lastCommit.getVersion()) {
                return null;
            }
            this.incRefIndexCommit(this.lastCommit);
            return new LIndexCommitInfo(this.indexDir, this.lastCommit);
        }
    }
    
    private void incRefIndexCommit(final IndexCommit indexCommit) {
        final Long commitVersion = indexCommit.getVersion();
        CommitRefCount refCount = this.commitRefCounts.get(commitVersion);
        if (refCount == null) {
            refCount = new CommitRefCount();
            this.commitRefCounts.put(commitVersion, refCount);
        }
        refCount.inc();
    }
    
    public void releaseIndexCommit(final long indexCommitVersion) {
        synchronized (this.commitRefCounts) {
            final Long commitVersion = indexCommitVersion;
            final CommitRefCount refCount = this.commitRefCounts.get(commitVersion);
            if (refCount == null) {
                HoldAwareIndexDeletionPolicy.logger.log(Level.WARNING, "No Reference counter for indexCommitVersion: " + commitVersion);
            }
            else if (refCount.dec() <= 0) {
                this.commitRefCounts.remove(commitVersion);
            }
        }
    }
    
    public void touch(final long indexCommitVersion) {
        synchronized (this.commitRefCounts) {
            final Long commitVersion = indexCommitVersion;
            final CommitRefCount refCount = this.commitRefCounts.get(commitVersion);
            if (refCount == null) {
                HoldAwareIndexDeletionPolicy.logger.warning("No Reference counter for indexCommitVersion: " + commitVersion);
            }
            else {
                refCount.touch();
            }
        }
    }
    
    public long getLastTouched(final long indexCommitVersion) {
        synchronized (this.commitRefCounts) {
            final Long commitVersion = indexCommitVersion;
            final CommitRefCount refCount = this.commitRefCounts.get(commitVersion);
            if (refCount == null) {
                return 0L;
            }
            return refCount.getLastTouched();
        }
    }
    
    static {
        logger = Logger.getLogger(HoldAwareIndexDeletionPolicy.class.getName());
    }
    
    private static class CommitRefCount
    {
        private int refCount;
        private long lastTouched;
        
        private CommitRefCount() {
            this.lastTouched = System.currentTimeMillis();
        }
        
        public void inc() {
            ++this.refCount;
        }
        
        public int dec() {
            return --this.refCount;
        }
        
        public void touch() {
            this.lastTouched = System.currentTimeMillis();
        }
        
        public long getLastTouched() {
            return this.lastTouched;
        }
    }
}
