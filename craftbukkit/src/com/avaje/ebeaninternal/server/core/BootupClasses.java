// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Embeddable;
import java.lang.annotation.Annotation;
import com.avaje.ebean.annotation.LdapDomain;
import javax.persistence.Table;
import javax.persistence.Entity;
import com.avaje.ebean.event.BeanFinder;
import com.avaje.ebean.config.CompoundType;
import com.avaje.ebean.config.ScalarTypeConverter;
import com.avaje.ebeaninternal.server.type.ScalarType;
import java.util.logging.Level;
import java.util.Iterator;
import java.util.Collection;
import com.avaje.ebean.config.lucene.IndexDefn;
import com.avaje.ebean.event.BeanQueryAdapter;
import com.avaje.ebean.event.BeanPersistListener;
import com.avaje.ebean.event.BeanPersistController;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.server.util.ClassPathSearchMatcher;

public class BootupClasses implements ClassPathSearchMatcher
{
    private static final Logger logger;
    private ArrayList<Class<?>> xmlBeanList;
    private ArrayList<Class<?>> embeddableList;
    private ArrayList<Class<?>> entityList;
    private ArrayList<Class<?>> scalarTypeList;
    private ArrayList<Class<?>> scalarConverterList;
    private ArrayList<Class<?>> compoundTypeList;
    private ArrayList<Class<?>> beanControllerList;
    private ArrayList<Class<?>> beanFinderList;
    private ArrayList<Class<?>> beanListenerList;
    private ArrayList<Class<?>> beanQueryAdapterList;
    private ArrayList<Class<?>> luceneIndexList;
    private List<BeanPersistController> persistControllerInstances;
    private List<BeanPersistListener<?>> persistListenerInstances;
    private List<BeanQueryAdapter> queryAdapterInstances;
    private List<IndexDefn<?>> luceneIndexInstances;
    
    public BootupClasses() {
        this.xmlBeanList = new ArrayList<Class<?>>();
        this.embeddableList = new ArrayList<Class<?>>();
        this.entityList = new ArrayList<Class<?>>();
        this.scalarTypeList = new ArrayList<Class<?>>();
        this.scalarConverterList = new ArrayList<Class<?>>();
        this.compoundTypeList = new ArrayList<Class<?>>();
        this.beanControllerList = new ArrayList<Class<?>>();
        this.beanFinderList = new ArrayList<Class<?>>();
        this.beanListenerList = new ArrayList<Class<?>>();
        this.beanQueryAdapterList = new ArrayList<Class<?>>();
        this.luceneIndexList = new ArrayList<Class<?>>();
        this.persistControllerInstances = new ArrayList<BeanPersistController>();
        this.persistListenerInstances = new ArrayList<BeanPersistListener<?>>();
        this.queryAdapterInstances = new ArrayList<BeanQueryAdapter>();
        this.luceneIndexInstances = new ArrayList<IndexDefn<?>>();
    }
    
    public BootupClasses(final List<Class<?>> list) {
        this.xmlBeanList = new ArrayList<Class<?>>();
        this.embeddableList = new ArrayList<Class<?>>();
        this.entityList = new ArrayList<Class<?>>();
        this.scalarTypeList = new ArrayList<Class<?>>();
        this.scalarConverterList = new ArrayList<Class<?>>();
        this.compoundTypeList = new ArrayList<Class<?>>();
        this.beanControllerList = new ArrayList<Class<?>>();
        this.beanFinderList = new ArrayList<Class<?>>();
        this.beanListenerList = new ArrayList<Class<?>>();
        this.beanQueryAdapterList = new ArrayList<Class<?>>();
        this.luceneIndexList = new ArrayList<Class<?>>();
        this.persistControllerInstances = new ArrayList<BeanPersistController>();
        this.persistListenerInstances = new ArrayList<BeanPersistListener<?>>();
        this.queryAdapterInstances = new ArrayList<BeanQueryAdapter>();
        this.luceneIndexInstances = new ArrayList<IndexDefn<?>>();
        if (list != null) {
            this.process(list.iterator());
        }
    }
    
    private BootupClasses(final BootupClasses parent) {
        this.xmlBeanList = new ArrayList<Class<?>>();
        this.embeddableList = new ArrayList<Class<?>>();
        this.entityList = new ArrayList<Class<?>>();
        this.scalarTypeList = new ArrayList<Class<?>>();
        this.scalarConverterList = new ArrayList<Class<?>>();
        this.compoundTypeList = new ArrayList<Class<?>>();
        this.beanControllerList = new ArrayList<Class<?>>();
        this.beanFinderList = new ArrayList<Class<?>>();
        this.beanListenerList = new ArrayList<Class<?>>();
        this.beanQueryAdapterList = new ArrayList<Class<?>>();
        this.luceneIndexList = new ArrayList<Class<?>>();
        this.persistControllerInstances = new ArrayList<BeanPersistController>();
        this.persistListenerInstances = new ArrayList<BeanPersistListener<?>>();
        this.queryAdapterInstances = new ArrayList<BeanQueryAdapter>();
        this.luceneIndexInstances = new ArrayList<IndexDefn<?>>();
        this.xmlBeanList.addAll(parent.xmlBeanList);
        this.embeddableList.addAll(parent.embeddableList);
        this.entityList.addAll(parent.entityList);
        this.scalarTypeList.addAll(parent.scalarTypeList);
        this.scalarConverterList.addAll(parent.scalarConverterList);
        this.compoundTypeList.addAll(parent.compoundTypeList);
        this.beanControllerList.addAll(parent.beanControllerList);
        this.beanFinderList.addAll(parent.beanFinderList);
        this.beanListenerList.addAll(parent.beanListenerList);
        this.beanQueryAdapterList.addAll(parent.beanQueryAdapterList);
        this.luceneIndexList.addAll(parent.luceneIndexList);
    }
    
    private void process(final Iterator<Class<?>> it) {
        while (it.hasNext()) {
            final Class<?> cls = it.next();
            this.isMatch(cls);
        }
    }
    
    public BootupClasses createCopy() {
        return new BootupClasses(this);
    }
    
    public void addIndexDefns(final List<IndexDefn<?>> indexInstances) {
        if (indexInstances != null) {
            this.luceneIndexInstances.addAll(indexInstances);
        }
    }
    
    public void addQueryAdapters(final List<BeanQueryAdapter> queryAdapterInstances) {
        if (queryAdapterInstances != null) {
            this.queryAdapterInstances.addAll(queryAdapterInstances);
        }
    }
    
    public void addPersistControllers(final List<BeanPersistController> beanControllerInstances) {
        if (beanControllerInstances != null) {
            this.persistControllerInstances.addAll(beanControllerInstances);
        }
    }
    
    public void addPersistListeners(final List<BeanPersistListener<?>> listenerInstances) {
        if (listenerInstances != null) {
            this.persistListenerInstances.addAll(listenerInstances);
        }
    }
    
    public List<BeanQueryAdapter> getBeanQueryAdapters() {
        for (final Class<?> cls : this.beanQueryAdapterList) {
            try {
                final BeanQueryAdapter newInstance = (BeanQueryAdapter)cls.newInstance();
                this.queryAdapterInstances.add(newInstance);
            }
            catch (Exception e) {
                final String msg = "Error creating BeanQueryAdapter " + cls;
                BootupClasses.logger.log(Level.SEVERE, msg, e);
            }
        }
        return this.queryAdapterInstances;
    }
    
    public List<BeanPersistListener<?>> getBeanPersistListeners() {
        for (final Class<?> cls : this.beanListenerList) {
            try {
                final BeanPersistListener<?> newInstance = (BeanPersistListener<?>)cls.newInstance();
                this.persistListenerInstances.add(newInstance);
            }
            catch (Exception e) {
                final String msg = "Error creating BeanPersistController " + cls;
                BootupClasses.logger.log(Level.SEVERE, msg, e);
            }
        }
        return this.persistListenerInstances;
    }
    
    public List<BeanPersistController> getBeanPersistControllers() {
        for (final Class<?> cls : this.beanControllerList) {
            try {
                final BeanPersistController newInstance = (BeanPersistController)cls.newInstance();
                this.persistControllerInstances.add(newInstance);
            }
            catch (Exception e) {
                final String msg = "Error creating BeanPersistController " + cls;
                BootupClasses.logger.log(Level.SEVERE, msg, e);
            }
        }
        return this.persistControllerInstances;
    }
    
    public List<IndexDefn<?>> getLuceneIndexInstances() {
        for (final Class<?> cls : this.luceneIndexList) {
            try {
                final IndexDefn<?> indexDefn = (IndexDefn<?>)cls.newInstance();
                this.luceneIndexInstances.add(indexDefn);
            }
            catch (Exception e) {
                final String msg = "Error creating BeanPersistController " + cls;
                BootupClasses.logger.log(Level.SEVERE, msg, e);
            }
        }
        return this.luceneIndexInstances;
    }
    
    public ArrayList<Class<?>> getEmbeddables() {
        return this.embeddableList;
    }
    
    public ArrayList<Class<?>> getEntities() {
        return this.entityList;
    }
    
    public ArrayList<Class<?>> getScalarTypes() {
        return this.scalarTypeList;
    }
    
    public ArrayList<Class<?>> getScalarConverters() {
        return this.scalarConverterList;
    }
    
    public ArrayList<Class<?>> getCompoundTypes() {
        return this.compoundTypeList;
    }
    
    public ArrayList<Class<?>> getBeanControllers() {
        return this.beanControllerList;
    }
    
    public ArrayList<Class<?>> getBeanFinders() {
        return this.beanFinderList;
    }
    
    public ArrayList<Class<?>> getBeanListeners() {
        return this.beanListenerList;
    }
    
    public ArrayList<Class<?>> getXmlBeanList() {
        return this.xmlBeanList;
    }
    
    public void add(final Iterator<Class<?>> it) {
        while (it.hasNext()) {
            final Class<?> clazz = it.next();
            this.isMatch(clazz);
        }
    }
    
    public boolean isMatch(final Class<?> cls) {
        if (this.isEmbeddable(cls)) {
            this.embeddableList.add(cls);
        }
        else if (this.isEntity(cls)) {
            this.entityList.add(cls);
        }
        else {
            if (!this.isXmlBean(cls)) {
                return this.isInterestingInterface(cls);
            }
            this.entityList.add(cls);
        }
        return true;
    }
    
    private boolean isInterestingInterface(final Class<?> cls) {
        boolean interesting = false;
        if (BeanPersistController.class.isAssignableFrom(cls)) {
            this.beanControllerList.add(cls);
            interesting = true;
        }
        if (ScalarType.class.isAssignableFrom(cls)) {
            this.scalarTypeList.add(cls);
            interesting = true;
        }
        if (ScalarTypeConverter.class.isAssignableFrom(cls)) {
            this.scalarConverterList.add(cls);
            interesting = true;
        }
        if (CompoundType.class.isAssignableFrom(cls)) {
            this.compoundTypeList.add(cls);
            interesting = true;
        }
        if (BeanFinder.class.isAssignableFrom(cls)) {
            this.beanFinderList.add(cls);
            interesting = true;
        }
        if (BeanPersistListener.class.isAssignableFrom(cls)) {
            this.beanListenerList.add(cls);
            interesting = true;
        }
        if (BeanQueryAdapter.class.isAssignableFrom(cls)) {
            this.beanQueryAdapterList.add(cls);
            interesting = true;
        }
        if (IndexDefn.class.isAssignableFrom(cls)) {
            this.luceneIndexList.add(cls);
            interesting = true;
        }
        return interesting;
    }
    
    private boolean isEntity(final Class<?> cls) {
        Annotation ann = cls.getAnnotation(Entity.class);
        if (ann != null) {
            return true;
        }
        ann = cls.getAnnotation(Table.class);
        if (ann != null) {
            return true;
        }
        ann = cls.getAnnotation(LdapDomain.class);
        return ann != null;
    }
    
    private boolean isEmbeddable(final Class<?> cls) {
        final Annotation ann = cls.getAnnotation(Embeddable.class);
        return ann != null;
    }
    
    private boolean isXmlBean(final Class<?> cls) {
        Annotation ann = cls.getAnnotation(XmlRootElement.class);
        if (ann != null) {
            return true;
        }
        ann = cls.getAnnotation(XmlType.class);
        return ann != null && !cls.isEnum();
    }
    
    static {
        logger = Logger.getLogger(BootupClasses.class.getName());
    }
}
