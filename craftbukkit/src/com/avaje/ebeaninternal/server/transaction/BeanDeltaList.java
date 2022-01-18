// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import java.io.IOException;
import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
import java.util.ArrayList;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public class BeanDeltaList
{
    private final BeanDescriptor<?> beanDescriptor;
    private final List<BeanDelta> deltaBeans;
    
    public BeanDeltaList(final BeanDescriptor<?> beanDescriptor) {
        this.deltaBeans = new ArrayList<BeanDelta>();
        this.beanDescriptor = beanDescriptor;
    }
    
    public String toString() {
        return this.deltaBeans.toString();
    }
    
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.beanDescriptor;
    }
    
    public void add(final BeanDelta b) {
        this.deltaBeans.add(b);
    }
    
    public List<BeanDelta> getDeltaBeans() {
        return this.deltaBeans;
    }
    
    public void writeBinaryMessage(final BinaryMessageList msgList) throws IOException {
        for (int i = 0; i < this.deltaBeans.size(); ++i) {
            this.deltaBeans.get(i).writeBinaryMessage(msgList);
        }
    }
}
