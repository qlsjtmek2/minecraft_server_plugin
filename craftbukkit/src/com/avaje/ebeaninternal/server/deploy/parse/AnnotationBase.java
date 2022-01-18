// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.parse;

import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.annotation.Annotation;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebean.config.NamingConvention;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;

public abstract class AnnotationBase
{
    protected final DatabasePlatform databasePlatform;
    protected final NamingConvention namingConvention;
    protected final DeployUtil util;
    
    protected AnnotationBase(final DeployUtil util) {
        this.util = util;
        this.databasePlatform = util.getDbPlatform();
        this.namingConvention = util.getNamingConvention();
    }
    
    public abstract void parse();
    
    protected boolean isEmpty(final String s) {
        return s == null || s.trim().length() == 0;
    }
    
    protected <T extends Annotation> T get(final DeployBeanProperty prop, final Class<T> annClass) {
        T a = null;
        final Field field = prop.getField();
        if (field != null) {
            a = field.getAnnotation(annClass);
        }
        if (a == null) {
            final Method m = prop.getReadMethod();
            if (m != null) {
                a = m.getAnnotation(annClass);
            }
        }
        return a;
    }
    
    protected <T extends Annotation> T find(final DeployBeanProperty prop, final Class<T> annClass) {
        T a = (T)this.get(prop, (Class<Annotation>)annClass);
        if (a == null) {
            a = prop.getOwningType().getAnnotation(annClass);
        }
        return a;
    }
}
