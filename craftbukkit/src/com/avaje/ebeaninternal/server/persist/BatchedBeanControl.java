// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.core.PersistRequest;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.api.SpiTransaction;
import java.util.HashMap;

public class BatchedBeanControl
{
    private final HashMap<String, BatchedBeanHolder> beanHoldMap;
    private final SpiTransaction transaction;
    private final BatchControl batchControl;
    private int topOrder;
    
    public BatchedBeanControl(final SpiTransaction t, final BatchControl batchControl) {
        this.beanHoldMap = new HashMap<String, BatchedBeanHolder>();
        this.transaction = t;
        this.batchControl = batchControl;
    }
    
    public ArrayList<PersistRequest> getPersistList(final PersistRequestBean<?> request) {
        final BatchedBeanHolder beanHolder = this.getBeanHolder(request);
        return beanHolder.getList(request);
    }
    
    private BatchedBeanHolder getBeanHolder(final PersistRequestBean<?> request) {
        final BeanDescriptor<?> beanDescriptor = request.getBeanDescriptor();
        BatchedBeanHolder batchBeanHolder = this.beanHoldMap.get(beanDescriptor.getFullName());
        if (batchBeanHolder == null) {
            final int relativeDepth = this.transaction.depth(0);
            if (relativeDepth == 0) {
                ++this.topOrder;
            }
            final int stmtOrder = this.topOrder * 100 + relativeDepth;
            batchBeanHolder = new BatchedBeanHolder(this.batchControl, beanDescriptor, stmtOrder);
            this.beanHoldMap.put(beanDescriptor.getFullName(), batchBeanHolder);
        }
        return batchBeanHolder;
    }
    
    public boolean isEmpty() {
        return this.beanHoldMap.isEmpty();
    }
    
    public BatchedBeanHolder[] getArray() {
        final BatchedBeanHolder[] bsArray = new BatchedBeanHolder[this.beanHoldMap.size()];
        this.beanHoldMap.values().toArray(bsArray);
        this.beanHoldMap.clear();
        this.topOrder = 0;
        return bsArray;
    }
}
