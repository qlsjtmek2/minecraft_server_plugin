// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import java.io.Serializable;
import java.util.Comparator;

public class BatchDepthComparator implements Comparator<BatchedBeanHolder>, Serializable
{
    private static final long serialVersionUID = 264611821665757991L;
    
    public int compare(final BatchedBeanHolder o1, final BatchedBeanHolder o2) {
        if (o1.getOrder() < o2.getOrder()) {
            return -1;
        }
        if (o1.getOrder() == o2.getOrder()) {
            return 0;
        }
        return 1;
    }
}
