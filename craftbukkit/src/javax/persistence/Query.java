// 
// Decompiled by Procyon v0.5.30
// 

package javax.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface Query
{
    List getResultList();
    
    Object getSingleResult();
    
    int executeUpdate();
    
    Query setMaxResults(final int p0);
    
    Query setFirstResult(final int p0);
    
    Query setHint(final String p0, final Object p1);
    
    Query setParameter(final String p0, final Object p1);
    
    Query setParameter(final String p0, final Date p1, final TemporalType p2);
    
    Query setParameter(final String p0, final Calendar p1, final TemporalType p2);
    
    Query setParameter(final int p0, final Object p1);
    
    Query setParameter(final int p0, final Date p1, final TemporalType p2);
    
    Query setParameter(final int p0, final Calendar p1, final TemporalType p2);
    
    Query setFlushMode(final FlushModeType p0);
}
