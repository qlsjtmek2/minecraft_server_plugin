// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.persist.dml.DmlMode;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.List;

public class FactoryAssocOnes
{
    public List<Bindable> create(final List<Bindable> list, final BeanDescriptor<?> desc, final DmlMode mode) {
        final BeanPropertyAssocOne<?>[] ones = desc.propertiesOneImported();
        for (int i = 0; i < ones.length; ++i) {
            if (!ones[i].isImportedPrimaryKey()) {
                switch (mode) {
                    case INSERT: {
                        if (!ones[i].isInsertable()) {
                            continue;
                        }
                        break;
                    }
                    case UPDATE: {
                        if (!ones[i].isUpdateable()) {
                            continue;
                        }
                        break;
                    }
                }
                list.add(new BindableAssocOne(ones[i]));
            }
        }
        return list;
    }
}
