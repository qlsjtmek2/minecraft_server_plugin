// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import java.util.List;
import java.io.DataOutputStream;
import java.io.DataOutput;
import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import java.io.Serializable;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public class BeanPathUpdateIds
{
    private transient BeanDescriptor<?> beanDescriptor;
    private final String descriptorId;
    private String path;
    private ArrayList<Serializable> ids;
    
    public BeanPathUpdateIds(final BeanDescriptor<?> desc, final String path) {
        this.beanDescriptor = desc;
        this.descriptorId = desc.getDescriptorId();
        this.path = path;
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (this.beanDescriptor != null) {
            sb.append(this.beanDescriptor.getFullName());
        }
        else {
            sb.append("descId:").append(this.descriptorId);
        }
        sb.append(" path:").append(this.path);
        sb.append(" ids:").append(this.ids);
        return sb.toString();
    }
    
    public static BeanPathUpdateIds readBinaryMessage(final SpiEbeanServer server, final DataInput dataInput) throws IOException {
        final String descriptorId = dataInput.readUTF();
        final String path = dataInput.readUTF();
        final BeanDescriptor<?> desc = server.getBeanDescriptorById(descriptorId);
        final BeanPathUpdateIds bp = new BeanPathUpdateIds(desc, path);
        bp.read(dataInput);
        return bp;
    }
    
    private void read(final DataInput dataInput) throws IOException {
        final IdBinder idBinder = this.beanDescriptor.getIdBinder();
        this.ids = this.readIdList(dataInput, idBinder);
    }
    
    private ArrayList<Serializable> readIdList(final DataInput dataInput, final IdBinder idBinder) throws IOException {
        final int count = dataInput.readInt();
        if (count < 1) {
            return null;
        }
        final ArrayList<Serializable> idList = new ArrayList<Serializable>(count);
        for (int i = 0; i < count; ++i) {
            final Object id = idBinder.readData(dataInput);
            idList.add((Serializable)id);
        }
        return idList;
    }
    
    public void writeBinaryMessage(final BinaryMessageList msgList) throws IOException {
        final IdBinder idBinder = this.beanDescriptor.getIdBinder();
        final int count = (this.ids == null) ? 0 : this.ids.size();
        if (count > 0) {
            int loop = 0;
            int i = 0;
            final int eof = this.ids.size();
            do {
                ++loop;
                final int endOfLoop = Math.min(eof, loop * 100);
                final BinaryMessage m = new BinaryMessage(endOfLoop * 4 + 20);
                final DataOutputStream os = m.getOs();
                os.writeInt(4);
                os.writeUTF(this.descriptorId);
                os.writeUTF(this.path);
                os.writeInt(count);
                while (i < endOfLoop) {
                    final Serializable idValue = this.ids.get(i);
                    idBinder.writeData(os, idValue);
                    ++i;
                }
                os.flush();
                msgList.add(m);
            } while (i < eof);
        }
    }
    
    public void addId(final Serializable id) {
        this.ids.add(id);
    }
    
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.beanDescriptor;
    }
    
    public String getDescriptorId() {
        return this.descriptorId;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public List<Serializable> getIds() {
        return this.ids;
    }
}
