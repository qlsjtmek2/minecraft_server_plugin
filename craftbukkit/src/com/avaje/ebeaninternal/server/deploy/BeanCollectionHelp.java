// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.Query;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.InvalidValue;
import java.util.ArrayList;
import com.avaje.ebean.bean.BeanCollection;
import java.util.Iterator;
import com.avaje.ebean.bean.BeanCollectionAdd;
import com.avaje.ebean.bean.BeanCollectionLoader;

public interface BeanCollectionHelp<T>
{
    Object copyCollection(final Object p0, final CopyContext p1, final int p2, final Object p3);
    
    void setLoader(final BeanCollectionLoader p0);
    
    BeanCollectionAdd getBeanCollectionAdd(final Object p0, final String p1);
    
    Object createEmpty(final boolean p0);
    
    Iterator<?> getIterator(final Object p0);
    
    void add(final BeanCollection<?> p0, final Object p1);
    
    BeanCollection<T> createReference(final Object p0, final String p1);
    
    ArrayList<InvalidValue> validate(final Object p0);
    
    void refresh(final EbeanServer p0, final Query<?> p1, final Transaction p2, final Object p3);
    
    void refresh(final BeanCollection<?> p0, final Object p1);
    
    void jsonWrite(final WriteJsonContext p0, final String p1, final Object p2, final boolean p3);
}
