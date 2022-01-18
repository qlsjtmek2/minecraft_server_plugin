// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public class FactoryId
{
    public BindableId createId(final BeanDescriptor<?> desc) {
        final BeanProperty[] uids = desc.propertiesId();
        if (uids.length != 1) {
            return new BindableIdMap(uids, desc);
        }
        if (!uids[0].isEmbedded()) {
            return new BindableIdScalar(uids[0]);
        }
        final BeanPropertyAssocOne<?> embId = (BeanPropertyAssocOne<?>)uids[0];
        return new BindableIdEmbedded(embId, desc);
    }
}
