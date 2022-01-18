// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import java.io.IOException;
import java.io.DataOutputStream;
import java.io.DataOutput;
import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public class BeanDeltaProperty
{
    private final BeanProperty beanProperty;
    private final Object value;
    
    public BeanDeltaProperty(final BeanProperty beanProperty, final Object value) {
        this.beanProperty = beanProperty;
        this.value = value;
    }
    
    public String toString() {
        return this.beanProperty.getName() + ":" + this.value;
    }
    
    public void apply(final Object bean) {
        this.beanProperty.setValue(bean, this.value);
    }
    
    public void writeBinaryMessage(final BinaryMessage m) throws IOException {
        final DataOutputStream os = m.getOs();
        os.writeUTF(this.beanProperty.getName());
        this.beanProperty.getScalarType().writeData(os, this.value);
    }
}
