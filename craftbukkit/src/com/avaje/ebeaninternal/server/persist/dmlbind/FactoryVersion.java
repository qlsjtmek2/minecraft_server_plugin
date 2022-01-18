// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.util.List;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public class FactoryVersion
{
    public Bindable create(final BeanDescriptor<?> desc) {
        final List<Bindable> verList = new ArrayList<Bindable>();
        final BeanProperty[] vers = desc.propertiesVersion();
        for (int i = 0; i < vers.length; ++i) {
            verList.add(new BindableProperty(vers[i]));
        }
        final BeanPropertyAssocOne<?>[] embedded = desc.propertiesEmbedded();
        for (int j = 0; j < embedded.length; ++j) {
            if (embedded[j].isEmbeddedVersion()) {
                final List<Bindable> bindList = new ArrayList<Bindable>();
                final BeanProperty[] embProps = embedded[j].getProperties();
                for (int k = 0; k < embProps.length; ++k) {
                    if (embProps[k].isVersion()) {
                        bindList.add(new BindableProperty(embProps[k]));
                    }
                }
                verList.add(new BindableEmbedded(embedded[j], bindList));
            }
        }
        if (verList.size() == 0) {
            return null;
        }
        if (verList.size() == 1) {
            return verList.get(0);
        }
        return new BindableList(verList);
    }
}
