// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.id;

import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public class IdBinderFactory
{
    private static final IdBinderEmpty EMPTY;
    private final boolean idInExpandedForm;
    
    public IdBinderFactory(final boolean idInExpandedForm) {
        this.idInExpandedForm = idInExpandedForm;
    }
    
    public IdBinder createIdBinder(final BeanProperty[] uids) {
        if (uids.length == 0) {
            return IdBinderFactory.EMPTY;
        }
        if (uids.length != 1) {
            return new IdBinderMultiple(uids);
        }
        if (uids[0].isEmbedded()) {
            return new IdBinderEmbedded(this.idInExpandedForm, (BeanPropertyAssocOne<?>)uids[0]);
        }
        return new IdBinderSimple(uids[0]);
    }
    
    static {
        EMPTY = new IdBinderEmpty();
    }
}
