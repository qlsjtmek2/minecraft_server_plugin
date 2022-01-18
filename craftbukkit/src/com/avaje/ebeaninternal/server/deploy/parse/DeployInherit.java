// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.parse;

import java.lang.annotation.Annotation;
import javax.persistence.DiscriminatorValue;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Inheritance;
import java.util.Iterator;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import java.util.LinkedHashMap;
import com.avaje.ebeaninternal.server.core.BootupClasses;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import java.util.Map;

public class DeployInherit
{
    private final Map<Class<?>, DeployInheritInfo> deployMap;
    private final Map<Class<?>, InheritInfo> finalMap;
    private final BootupClasses bootupClasses;
    
    public DeployInherit(final BootupClasses bootupClasses) {
        this.deployMap = new LinkedHashMap<Class<?>, DeployInheritInfo>();
        this.finalMap = new LinkedHashMap<Class<?>, InheritInfo>();
        this.bootupClasses = bootupClasses;
        this.initialise();
    }
    
    public void process(final DeployBeanDescriptor<?> desc) {
        final InheritInfo inheritInfo = this.finalMap.get(desc.getBeanType());
        desc.setInheritInfo(inheritInfo);
    }
    
    private void initialise() {
        final List<Class<?>> entityList = this.bootupClasses.getEntities();
        this.findInheritClasses(entityList);
        this.buildDeployTree();
        this.buildFinalTree();
    }
    
    private void findInheritClasses(final List<Class<?>> entityList) {
        for (final Class<?> cls : entityList) {
            if (this.isInheritanceClass(cls)) {
                final DeployInheritInfo info = this.createInfo(cls);
                this.deployMap.put(cls, info);
            }
        }
    }
    
    private void buildDeployTree() {
        for (final DeployInheritInfo info : this.deployMap.values()) {
            if (!info.isRoot()) {
                final DeployInheritInfo parent = this.getInfo(info.getParent());
                parent.addChild(info);
            }
        }
    }
    
    private void buildFinalTree() {
        for (final DeployInheritInfo deploy : this.deployMap.values()) {
            if (deploy.isRoot()) {
                this.createFinalInfo(null, null, deploy);
            }
        }
    }
    
    private InheritInfo createFinalInfo(InheritInfo root, final InheritInfo parent, final DeployInheritInfo deploy) {
        final InheritInfo node = new InheritInfo(root, parent, deploy);
        if (parent != null) {
            parent.addChild(node);
        }
        this.finalMap.put(node.getType(), node);
        if (root == null) {
            root = node;
        }
        final Iterator<DeployInheritInfo> it = deploy.children();
        while (it.hasNext()) {
            final DeployInheritInfo childDeploy = it.next();
            this.createFinalInfo(root, node, childDeploy);
        }
        return node;
    }
    
    private DeployInheritInfo getInfo(final Class<?> cls) {
        return this.deployMap.get(cls);
    }
    
    private DeployInheritInfo createInfo(final Class<?> cls) {
        final DeployInheritInfo info = new DeployInheritInfo(cls);
        final Class<?> parent = this.findParent(cls);
        if (parent != null) {
            info.setParent(parent);
        }
        final Inheritance ia = cls.getAnnotation(Inheritance.class);
        if (ia != null) {
            ia.strategy();
        }
        final DiscriminatorColumn da = cls.getAnnotation(DiscriminatorColumn.class);
        if (da != null) {
            info.setDiscriminatorColumn(da.name());
            final DiscriminatorType discriminatorType = da.discriminatorType();
            if (discriminatorType.equals(DiscriminatorType.INTEGER)) {
                info.setDiscriminatorType(4);
            }
            else {
                info.setDiscriminatorType(12);
            }
            info.setDiscriminatorLength(da.length());
        }
        final DiscriminatorValue dv = cls.getAnnotation(DiscriminatorValue.class);
        if (dv != null) {
            info.setDiscriminatorValue(dv.value());
        }
        return info;
    }
    
    private Class<?> findParent(final Class<?> cls) {
        final Class<?> superCls = cls.getSuperclass();
        if (this.isInheritanceClass(superCls)) {
            return superCls;
        }
        return null;
    }
    
    private boolean isInheritanceClass(final Class<?> cls) {
        if (cls.equals(Object.class)) {
            return false;
        }
        final Annotation a = cls.getAnnotation(Inheritance.class);
        return a != null || this.isInheritanceClass(cls.getSuperclass());
    }
}
