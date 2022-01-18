// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.persist.dml.DmlMode;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.List;

public class FactoryEmbedded
{
    private final FactoryProperty factoryProperty;
    
    public FactoryEmbedded(final boolean bindEncryptDataFirst) {
        this.factoryProperty = new FactoryProperty(bindEncryptDataFirst);
    }
    
    public void create(final List<Bindable> list, final BeanDescriptor<?> desc, final DmlMode mode, final boolean withLobs) {
        final BeanPropertyAssocOne<?>[] embedded = desc.propertiesEmbedded();
        for (int j = 0; j < embedded.length; ++j) {
            final List<Bindable> bindList = new ArrayList<Bindable>();
            final BeanProperty[] props = embedded[j].getProperties();
            for (int i = 0; i < props.length; ++i) {
                final Bindable item = this.factoryProperty.create(props[i], mode, withLobs);
                if (item != null) {
                    bindList.add(item);
                }
            }
            list.add(new BindableEmbedded(embedded[j], bindList));
        }
    }
}
