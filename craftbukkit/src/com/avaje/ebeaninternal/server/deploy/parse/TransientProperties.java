// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.parse;

import java.util.List;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;

public class TransientProperties
{
    public void process(final DeployBeanDescriptor<?> desc) {
        final List<DeployBeanProperty> props = desc.propertiesBase();
        for (int i = 0; i < props.size(); ++i) {
            final DeployBeanProperty prop = props.get(i);
            if (!prop.isDbRead() && !prop.isDbInsertable() && !prop.isDbUpdateable()) {
                prop.setTransient(true);
            }
        }
        final List<DeployBeanPropertyAssocOne<?>> ones = desc.propertiesAssocOne();
        for (int j = 0; j < ones.size(); ++j) {
            final DeployBeanPropertyAssocOne<?> prop2 = ones.get(j);
            if (prop2.getBeanTable() == null && !prop2.isEmbedded()) {
                prop2.setTransient(true);
            }
        }
        final List<DeployBeanPropertyAssocMany<?>> manys = desc.propertiesAssocMany();
        for (int k = 0; k < manys.size(); ++k) {
            final DeployBeanPropertyAssocMany<?> prop3 = manys.get(k);
            if (prop3.getBeanTable() == null) {
                prop3.setTransient(true);
            }
        }
    }
}
