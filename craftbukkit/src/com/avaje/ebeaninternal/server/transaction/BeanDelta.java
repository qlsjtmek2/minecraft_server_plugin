// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import java.io.DataOutputStream;
import java.io.DataOutput;
import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.List;

public class BeanDelta
{
    private final List<BeanDeltaProperty> properties;
    private final BeanDescriptor<?> beanDescriptor;
    private final Object id;
    
    public BeanDelta(final BeanDescriptor<?> beanDescriptor, final Object id) {
        this.beanDescriptor = beanDescriptor;
        this.id = id;
        this.properties = new ArrayList<BeanDeltaProperty>();
    }
    
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.beanDescriptor;
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BeanDelta[");
        sb.append(this.beanDescriptor.getName()).append(":");
        sb.append(this.properties);
        sb.append("]");
        return sb.toString();
    }
    
    public Object getId() {
        return this.id;
    }
    
    public void add(final BeanProperty beanProperty, final Object value) {
        this.properties.add(new BeanDeltaProperty(beanProperty, value));
    }
    
    public void add(final BeanDeltaProperty propertyDelta) {
        this.properties.add(propertyDelta);
    }
    
    public void apply(final Object bean) {
        for (int i = 0; i < this.properties.size(); ++i) {
            this.properties.get(i).apply(bean);
        }
    }
    
    public static BeanDelta readBinaryMessage(final SpiEbeanServer server, final DataInput dataInput) throws IOException {
        final String descriptorId = dataInput.readUTF();
        final BeanDescriptor<?> desc = server.getBeanDescriptorById(descriptorId);
        final Object id = desc.getIdBinder().readData(dataInput);
        final BeanDelta bp = new BeanDelta(desc, id);
        for (int count = dataInput.readInt(), i = 0; i < count; ++i) {
            final String propName = dataInput.readUTF();
            final BeanProperty beanProperty = desc.getBeanProperty(propName);
            final Object value = beanProperty.getScalarType().readData(dataInput);
            bp.add(beanProperty, value);
        }
        return bp;
    }
    
    public void writeBinaryMessage(final BinaryMessageList msgList) throws IOException {
        final BinaryMessage m = new BinaryMessage(50);
        final DataOutputStream os = m.getOs();
        os.writeInt(3);
        os.writeUTF(this.beanDescriptor.getDescriptorId());
        this.beanDescriptor.getIdBinder().writeData(os, this.id);
        os.writeInt(this.properties.size());
        for (int i = 0; i < this.properties.size(); ++i) {
            this.properties.get(i).writeBinaryMessage(m);
        }
        os.flush();
        msgList.add(m);
    }
}
