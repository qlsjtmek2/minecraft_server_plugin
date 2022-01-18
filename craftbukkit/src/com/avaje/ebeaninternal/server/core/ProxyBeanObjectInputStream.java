// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.ClassUtil;
import com.avaje.ebeaninternal.server.subclass.SubClassUtil;
import java.io.ObjectStreamClass;
import java.io.IOException;
import com.avaje.ebean.bean.SerializeControl;
import com.avaje.ebean.EbeanServer;
import java.io.InputStream;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import java.io.ObjectInputStream;

public class ProxyBeanObjectInputStream extends ObjectInputStream
{
    private final SpiEbeanServer ebeanServer;
    
    public ProxyBeanObjectInputStream(final InputStream in, final EbeanServer ebeanServer) throws IOException {
        super(in);
        this.ebeanServer = (SpiEbeanServer)ebeanServer;
        SerializeControl.setVanilla(false);
    }
    
    public void close() throws IOException {
        super.close();
        SerializeControl.resetToDefault();
    }
    
    protected Class<?> resolveGenerated(final ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        final String className = desc.getName();
        final String vanillaClassName = SubClassUtil.getSuperClassName(className);
        final Class<?> vanillaClass = ClassUtil.forName(vanillaClassName, this.getClass());
        final BeanDescriptor<?> d = this.ebeanServer.getBeanDescriptor(vanillaClass);
        if (d == null) {
            final String msg = "Could not find BeanDescriptor for " + vanillaClassName;
            throw new IOException(msg);
        }
        return d.getFactoryType();
    }
    
    protected Class<?> resolveClass(final ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        final String className = desc.getName();
        if (SubClassUtil.isSubClass(className)) {
            return this.resolveGenerated(desc);
        }
        return super.resolveClass(desc);
    }
}
