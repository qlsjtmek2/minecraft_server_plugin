// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.persist.dml.DmlMode;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.List;

public class FactoryBaseProperties
{
    private final FactoryProperty factoryProperty;
    
    public FactoryBaseProperties(final boolean bindEncryptDataFirst) {
        this.factoryProperty = new FactoryProperty(bindEncryptDataFirst);
    }
    
    public void create(final List<Bindable> list, final BeanDescriptor<?> desc, final DmlMode mode, final boolean withLobs) {
        this.add(desc.propertiesBaseScalar(), list, desc, mode, withLobs);
        final BeanPropertyCompound[] compoundProps = desc.propertiesBaseCompound();
        for (int i = 0; i < compoundProps.length; ++i) {
            final BeanProperty[] props = compoundProps[i].getScalarProperties();
            final ArrayList<Bindable> newList = new ArrayList<Bindable>(props.length);
            this.add(props, newList, desc, mode, withLobs);
            final BindableCompound compoundBindable = new BindableCompound(compoundProps[i], newList);
            list.add(compoundBindable);
        }
    }
    
    private void add(final BeanProperty[] props, final List<Bindable> list, final BeanDescriptor<?> desc, final DmlMode mode, final boolean withLobs) {
        for (int i = 0; i < props.length; ++i) {
            final Bindable item = this.factoryProperty.create(props[i], mode, withLobs);
            if (item != null) {
                list.add(item);
            }
        }
    }
}
