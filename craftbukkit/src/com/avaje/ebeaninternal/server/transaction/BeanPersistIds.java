// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebean.event.BeanPersistListener;
import java.util.List;
import com.avaje.ebeaninternal.server.core.PersistRequest;
import java.io.DataOutputStream;
import java.io.DataOutput;
import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.io.Serializable;

public class BeanPersistIds implements Serializable
{
    private static final long serialVersionUID = 8389469180931531409L;
    private transient BeanDescriptor<?> beanDescriptor;
    private final String descriptorId;
    private ArrayList<Serializable> insertIds;
    private ArrayList<Serializable> updateIds;
    private ArrayList<Serializable> deleteIds;
    
    public BeanPersistIds(final BeanDescriptor<?> desc) {
        this.beanDescriptor = desc;
        this.descriptorId = desc.getDescriptorId();
    }
    
    public static BeanPersistIds readBinaryMessage(final SpiEbeanServer server, final DataInput dataInput) throws IOException {
        final String descriptorId = dataInput.readUTF();
        final BeanDescriptor<?> desc = server.getBeanDescriptorById(descriptorId);
        final BeanPersistIds bp = new BeanPersistIds(desc);
        bp.read(dataInput);
        return bp;
    }
    
    private void read(final DataInput dataInput) throws IOException {
        final IdBinder idBinder = this.beanDescriptor.getIdBinder();
        final int iudType = dataInput.readInt();
        final ArrayList<Serializable> idList = this.readIdList(dataInput, idBinder);
        switch (iudType) {
            case 0: {
                this.insertIds = idList;
                break;
            }
            case 1: {
                this.updateIds = idList;
                break;
            }
            case 2: {
                this.deleteIds = idList;
                break;
            }
            default: {
                throw new RuntimeException("Invalid iudType " + iudType);
            }
        }
    }
    
    public void writeBinaryMessage(final BinaryMessageList msgList) throws IOException {
        this.writeIdList(this.beanDescriptor, 0, this.insertIds, msgList);
        this.writeIdList(this.beanDescriptor, 1, this.updateIds, msgList);
        this.writeIdList(this.beanDescriptor, 2, this.deleteIds, msgList);
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
    
    private void writeIdList(final BeanDescriptor<?> desc, final int iudType, final ArrayList<Serializable> idList, final BinaryMessageList msgList) throws IOException {
        final IdBinder idBinder = desc.getIdBinder();
        final int count = (idList == null) ? 0 : idList.size();
        if (count > 0) {
            int loop = 0;
            int i = 0;
            final int eof = idList.size();
            do {
                ++loop;
                final int endOfLoop = Math.min(eof, loop * 100);
                final BinaryMessage m = new BinaryMessage(endOfLoop * 4 + 20);
                final DataOutputStream os = m.getOs();
                os.writeInt(1);
                os.writeUTF(this.descriptorId);
                os.writeInt(iudType);
                os.writeInt(count);
                while (i < endOfLoop) {
                    final Serializable idValue = idList.get(i);
                    idBinder.writeData(os, idValue);
                    ++i;
                }
                os.flush();
                msgList.add(m);
            } while (i < eof);
        }
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (this.beanDescriptor != null) {
            sb.append(this.beanDescriptor.getFullName());
        }
        else {
            sb.append("descId:").append(this.descriptorId);
        }
        if (this.insertIds != null) {
            sb.append(" insertIds:").append(this.insertIds);
        }
        if (this.updateIds != null) {
            sb.append(" updateIds:").append(this.updateIds);
        }
        if (this.deleteIds != null) {
            sb.append(" deleteIds:").append(this.deleteIds);
        }
        return sb.toString();
    }
    
    public void addId(final PersistRequest.Type type, final Serializable id) {
        switch (type) {
            case INSERT: {
                this.addInsertId(id);
                break;
            }
            case UPDATE: {
                this.addUpdateId(id);
                break;
            }
            case DELETE: {
                this.addDeleteId(id);
                break;
            }
        }
    }
    
    private void addInsertId(final Serializable id) {
        if (this.insertIds == null) {
            this.insertIds = new ArrayList<Serializable>();
        }
        this.insertIds.add(id);
    }
    
    private void addUpdateId(final Serializable id) {
        if (this.updateIds == null) {
            this.updateIds = new ArrayList<Serializable>();
        }
        this.updateIds.add(id);
    }
    
    private void addDeleteId(final Serializable id) {
        if (this.deleteIds == null) {
            this.deleteIds = new ArrayList<Serializable>();
        }
        this.deleteIds.add(id);
    }
    
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.beanDescriptor;
    }
    
    public String getDescriptorId() {
        return this.descriptorId;
    }
    
    public List<Serializable> getInsertIds() {
        return this.insertIds;
    }
    
    public List<Serializable> getUpdateIds() {
        return this.updateIds;
    }
    
    public List<Serializable> getDeleteIds() {
        return this.deleteIds;
    }
    
    public void setBeanDescriptor(final BeanDescriptor<?> beanDescriptor) {
        this.beanDescriptor = beanDescriptor;
    }
    
    public void notifyCacheAndListener() {
        final BeanPersistListener<?> listener = this.beanDescriptor.getPersistListener();
        this.beanDescriptor.queryCacheClear();
        if (this.insertIds != null && listener != null) {
            for (int i = 0; i < this.insertIds.size(); ++i) {
                listener.remoteInsert(this.insertIds.get(i));
            }
        }
        if (this.updateIds != null) {
            for (int i = 0; i < this.updateIds.size(); ++i) {
                final Serializable id = this.updateIds.get(i);
                this.beanDescriptor.cacheRemove(id);
                if (listener != null) {
                    listener.remoteInsert(id);
                }
            }
        }
        if (this.deleteIds != null) {
            for (int i = 0; i < this.deleteIds.size(); ++i) {
                final Serializable id = this.deleteIds.get(i);
                this.beanDescriptor.cacheRemove(id);
                if (listener != null) {
                    listener.remoteInsert(id);
                }
            }
        }
    }
}
