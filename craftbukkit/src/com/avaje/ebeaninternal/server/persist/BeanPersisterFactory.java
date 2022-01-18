// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public interface BeanPersisterFactory
{
    BeanPersister create(final BeanDescriptor<?> p0);
}
