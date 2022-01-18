// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.multipart;

import java.io.Serializable;
import java.util.Comparator;

public final class CaseIgnoringComparator implements Comparator<String>, Serializable
{
    private static final long serialVersionUID = 4582133183775373862L;
    public static final CaseIgnoringComparator INSTANCE;
    
    @Override
    public int compare(final String o1, final String o2) {
        return o1.compareToIgnoreCase(o2);
    }
    
    private Object readResolve() {
        return CaseIgnoringComparator.INSTANCE;
    }
    
    static {
        INSTANCE = new CaseIgnoringComparator();
    }
}
