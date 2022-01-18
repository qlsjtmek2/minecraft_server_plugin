// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import javax.persistence.PersistenceException;
import java.util.Comparator;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.logging.Level;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.core.PersistRequest;
import com.avaje.ebeaninternal.api.SpiTransaction;
import java.util.logging.Logger;

public final class BatchControl
{
    private static final Logger logger;
    private static final BatchDepthComparator depthComparator;
    private final SpiTransaction transaction;
    private final BatchedPstmtHolder pstmtHolder;
    private int batchSize;
    private boolean getGeneratedKeys;
    private boolean batchFlushOnMixed;
    private final BatchedBeanControl beanControl;
    
    public BatchControl(final SpiTransaction t, final int batchSize, final boolean getGenKeys) {
        this.pstmtHolder = new BatchedPstmtHolder();
        this.batchFlushOnMixed = true;
        this.transaction = t;
        this.batchSize = batchSize;
        this.getGeneratedKeys = getGenKeys;
        this.beanControl = new BatchedBeanControl(t, this);
        this.transaction.setBatchControl(this);
    }
    
    public void setBatchFlushOnMixed(final boolean flushBatchOnMixed) {
        this.batchFlushOnMixed = flushBatchOnMixed;
    }
    
    public int getBatchSize() {
        return this.batchSize;
    }
    
    public void setBatchSize(final int batchSize) {
        if (batchSize > 1) {
            this.batchSize = batchSize;
        }
    }
    
    public void setGetGeneratedKeys(final Boolean getGeneratedKeys) {
        if (getGeneratedKeys != null) {
            this.getGeneratedKeys = getGeneratedKeys;
        }
    }
    
    public int executeStatementOrBatch(final PersistRequest request, final boolean batch) {
        if (!batch || (this.batchFlushOnMixed && !this.beanControl.isEmpty())) {
            this.flush();
        }
        if (!batch) {
            return request.executeNow();
        }
        if (this.pstmtHolder.getMaxSize() >= this.batchSize) {
            this.flush();
        }
        request.executeNow();
        return -1;
    }
    
    public int executeOrQueue(final PersistRequestBean<?> request, final boolean batch) {
        if (!batch || (this.batchFlushOnMixed && !this.pstmtHolder.isEmpty())) {
            this.flush();
        }
        if (!batch) {
            return request.executeNow();
        }
        ArrayList<PersistRequest> persistList = this.beanControl.getPersistList(request);
        if (persistList == null) {
            if (BatchControl.logger.isLoggable(Level.FINE)) {
                BatchControl.logger.fine("Bean instance already in this batch: " + request.getBean());
            }
            return -1;
        }
        if (persistList.size() >= this.batchSize) {
            this.flush();
            persistList = this.beanControl.getPersistList(request);
        }
        persistList.add(request);
        return -1;
    }
    
    public BatchedPstmtHolder getPstmtHolder() {
        return this.pstmtHolder;
    }
    
    public boolean isEmpty() {
        return this.beanControl.isEmpty() && this.pstmtHolder.isEmpty();
    }
    
    protected void flushPstmtHolder() {
        this.pstmtHolder.flush(this.getGeneratedKeys);
    }
    
    protected void executeNow(final ArrayList<PersistRequest> list) {
        for (int i = 0; i < list.size(); ++i) {
            final PersistRequest request = list.get(i);
            request.executeNow();
        }
    }
    
    public void flush() throws PersistenceException {
        if (!this.pstmtHolder.isEmpty()) {
            this.flushPstmtHolder();
        }
        if (this.beanControl.isEmpty()) {
            return;
        }
        final BatchedBeanHolder[] bsArray = this.beanControl.getArray();
        Arrays.sort(bsArray, BatchControl.depthComparator);
        if (this.transaction.isLogSummary()) {
            this.transaction.logInternal("BatchControl flush " + Arrays.toString(bsArray));
        }
        for (int i = 0; i < bsArray.length; ++i) {
            final BatchedBeanHolder bs = bsArray[i];
            bs.executeNow();
            this.flushPstmtHolder();
        }
    }
    
    static {
        logger = Logger.getLogger(BatchControl.class.getName());
        depthComparator = new BatchDepthComparator();
    }
}
