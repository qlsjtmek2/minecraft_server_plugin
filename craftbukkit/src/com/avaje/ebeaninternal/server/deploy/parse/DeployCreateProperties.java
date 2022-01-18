// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.parse;

import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import javax.persistence.Transient;
import com.avaje.ebeaninternal.server.type.reflect.CheckImmutableResponse;
import com.avaje.ebeaninternal.server.type.CtCompoundType;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyCompound;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertySimpleCollection;
import com.avaje.ebeaninternal.server.deploy.ManyType;
import java.util.logging.Level;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.persistence.PersistenceException;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.core.Message;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import com.avaje.ebeaninternal.server.type.ScalaOptionTypeConverter;
import com.avaje.ebeaninternal.server.type.TypeManager;
import com.avaje.ebeaninternal.server.deploy.DetermineManyType;
import com.avaje.ebean.config.ScalarTypeConverter;
import java.util.logging.Logger;

public class DeployCreateProperties
{
    private static final Logger logger;
    private final Class<?> scalaOptionClass;
    private final ScalarTypeConverter scalaOptionTypeConverter;
    private final DetermineManyType determineManyType;
    private final TypeManager typeManager;
    
    public DeployCreateProperties(final TypeManager typeManager) {
        this.typeManager = typeManager;
        final Class<?> tmpOptionClass = DetectScala.getScalaOptionClass();
        if (tmpOptionClass == null) {
            this.scalaOptionClass = null;
            this.scalaOptionTypeConverter = null;
        }
        else {
            this.scalaOptionClass = tmpOptionClass;
            this.scalaOptionTypeConverter = new ScalaOptionTypeConverter();
        }
        this.determineManyType = new DetermineManyType(tmpOptionClass != null);
    }
    
    public void createProperties(final DeployBeanDescriptor<?> desc) {
        this.createProperties(desc, desc.getBeanType(), 0);
        desc.sortProperties();
        final Iterator<DeployBeanProperty> it = desc.propertiesAll();
        while (it.hasNext()) {
            final DeployBeanProperty prop = it.next();
            if (prop.isTransient()) {
                if (prop.getWriteMethod() == null || prop.getReadMethod() == null) {
                    DeployCreateProperties.logger.finest("... transient: " + prop.getFullBeanName());
                }
                else {
                    final String msg = Message.msg("deploy.property.nofield", desc.getFullName(), prop.getName());
                    DeployCreateProperties.logger.warning(msg);
                }
            }
        }
    }
    
    private boolean ignoreFieldByName(final String fieldName) {
        return fieldName.startsWith("_ebean_") || fieldName.startsWith("ajc$instance$");
    }
    
    private void createProperties(final DeployBeanDescriptor<?> desc, final Class<?> beanType, final int level) {
        final boolean scalaObject = desc.isScalaObject();
        try {
            final Method[] declaredMethods = beanType.getDeclaredMethods();
            final Field[] fields = beanType.getDeclaredFields();
            for (int i = 0; i < fields.length; ++i) {
                final Field field = fields[i];
                if (!Modifier.isStatic(field.getModifiers())) {
                    if (Modifier.isTransient(field.getModifiers())) {
                        DeployCreateProperties.logger.finer("Skipping transient field " + field.getName() + " in " + beanType.getName());
                    }
                    else if (!this.ignoreFieldByName(field.getName())) {
                        final String fieldName = this.getFieldName(field, beanType);
                        final String initFieldName = this.initCap(fieldName);
                        final Method getter = this.findGetter(field, initFieldName, declaredMethods, scalaObject);
                        final Method setter = this.findSetter(field, initFieldName, declaredMethods, scalaObject);
                        final DeployBeanProperty prop = this.createProp(level, desc, field, beanType, getter, setter);
                        final int sortOverride = prop.getSortOverride();
                        prop.setSortOrder(level * 10000 + 100 - i + sortOverride);
                        final DeployBeanProperty replaced = desc.addBeanProperty(prop);
                        if (replaced != null) {
                            if (!replaced.isTransient()) {
                                String msg = "Huh??? property " + prop.getFullBeanName() + " being defined twice";
                                msg += " but replaced property was not transient? This is not expected?";
                                DeployCreateProperties.logger.warning(msg);
                            }
                        }
                    }
                }
            }
            final Class<?> superClass = beanType.getSuperclass();
            if (!superClass.equals(Object.class)) {
                this.createProperties(desc, superClass, level + 1);
            }
        }
        catch (PersistenceException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            throw new PersistenceException(ex2);
        }
    }
    
    private String initCap(final String str) {
        if (str.length() > 1) {
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        }
        return str.toUpperCase();
    }
    
    private String getFieldName(final Field field, final Class<?> beanType) {
        final String name = field.getName();
        if ((Boolean.class.equals(field.getType()) || Boolean.TYPE.equals(field.getType())) && name.startsWith("is") && name.length() > 2) {
            final char c = name.charAt(2);
            if (Character.isUpperCase(c)) {
                final String msg = "trimming off 'is' from boolean field name " + name + " in class " + beanType.getName();
                DeployCreateProperties.logger.log(Level.INFO, msg);
                return name.substring(2);
            }
        }
        return name;
    }
    
    private Method findGetter(final Field field, final String initFieldName, final Method[] declaredMethods, final boolean scalaObject) {
        final String methGetName = "get" + initFieldName;
        final String methIsName = "is" + initFieldName;
        final String scalaGet = field.getName();
        for (int i = 0; i < declaredMethods.length; ++i) {
            final Method m = declaredMethods[i];
            if ((scalaObject && m.getName().equals(scalaGet)) || m.getName().equals(methGetName) || m.getName().equals(methIsName)) {
                final Class<?>[] params = m.getParameterTypes();
                if (params.length == 0 && field.getType().equals(m.getReturnType())) {
                    final int modifiers = m.getModifiers();
                    if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
                        return m;
                    }
                }
            }
        }
        return null;
    }
    
    private Method findSetter(final Field field, final String initFieldName, final Method[] declaredMethods, final boolean scalaObject) {
        final String methSetName = "set" + initFieldName;
        final String scalaSetName = field.getName() + "_$eq";
        for (int i = 0; i < declaredMethods.length; ++i) {
            final Method m = declaredMethods[i];
            if ((scalaObject && m.getName().equals(scalaSetName)) || m.getName().equals(methSetName)) {
                final Class<?>[] params = m.getParameterTypes();
                if (params.length == 1 && field.getType().equals(params[0]) && Void.TYPE.equals(m.getReturnType())) {
                    final int modifiers = m.getModifiers();
                    if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
                        return m;
                    }
                }
            }
        }
        return null;
    }
    
    private DeployBeanProperty createManyType(final DeployBeanDescriptor<?> desc, final Class<?> targetType, final ManyType manyType) {
        final ScalarType<?> scalarType = this.typeManager.getScalarType(targetType);
        if (scalarType != null) {
            return new DeployBeanPropertySimpleCollection<Object>(desc, targetType, scalarType, manyType);
        }
        return new DeployBeanPropertyAssocMany<Object>(desc, targetType, manyType);
    }
    
    private DeployBeanProperty createProp(final DeployBeanDescriptor<?> desc, final Field field) {
        Class<?> innerType;
        final Class<?> propertyType = innerType = field.getType();
        ScalarTypeConverter<?, ?> typeConverter = null;
        if (propertyType.equals(this.scalaOptionClass)) {
            innerType = this.determineTargetType(field);
            typeConverter = (ScalarTypeConverter<?, ?>)this.scalaOptionTypeConverter;
        }
        final ManyType manyType = this.determineManyType.getManyType(propertyType);
        if (manyType != null) {
            final Class<?> targetType = this.determineTargetType(field);
            if (targetType == null) {
                DeployCreateProperties.logger.warning("Could not find parameter type (via reflection) on " + desc.getFullName() + " " + field.getName());
            }
            return this.createManyType(desc, targetType, manyType);
        }
        if (innerType.isEnum() || innerType.isPrimitive()) {
            return new DeployBeanProperty(desc, propertyType, null, typeConverter);
        }
        ScalarType<?> scalarType = this.typeManager.getScalarType(innerType);
        if (scalarType != null) {
            return new DeployBeanProperty(desc, propertyType, scalarType, typeConverter);
        }
        CtCompoundType<?> compoundType = this.typeManager.getCompoundType(innerType);
        if (compoundType != null) {
            return new DeployBeanPropertyCompound(desc, propertyType, compoundType, typeConverter);
        }
        if (!this.isTransientField(field)) {
            try {
                final CheckImmutableResponse checkImmutable = this.typeManager.checkImmutable(innerType);
                if (checkImmutable.isImmutable()) {
                    if (!checkImmutable.isCompoundType()) {
                        scalarType = this.typeManager.recursiveCreateScalarTypes(innerType);
                        return new DeployBeanProperty(desc, propertyType, scalarType, typeConverter);
                    }
                    this.typeManager.recursiveCreateScalarDataReader(innerType);
                    compoundType = this.typeManager.getCompoundType(innerType);
                    if (compoundType != null) {
                        return new DeployBeanPropertyCompound(desc, propertyType, compoundType, typeConverter);
                    }
                }
            }
            catch (Exception e) {
                DeployCreateProperties.logger.log(Level.SEVERE, "Error with " + desc + " field:" + field.getName(), e);
            }
        }
        return new DeployBeanPropertyAssocOne<Object>(desc, propertyType);
    }
    
    private boolean isTransientField(final Field field) {
        final Transient t = field.getAnnotation(Transient.class);
        return t != null;
    }
    
    private DeployBeanProperty createProp(final int level, final DeployBeanDescriptor<?> desc, final Field field, final Class<?> beanType, final Method getter, final Method setter) {
        final DeployBeanProperty prop = this.createProp(desc, field);
        prop.setOwningType(beanType);
        prop.setName(field.getName());
        prop.setReadMethod(getter);
        prop.setWriteMethod(setter);
        prop.setField(field);
        return prop;
    }
    
    private Class<?> determineTargetType(final Field field) {
        final Type genType = field.getGenericType();
        if (genType instanceof ParameterizedType) {
            final ParameterizedType ptype = (ParameterizedType)genType;
            final Type[] typeArgs = ptype.getActualTypeArguments();
            if (typeArgs.length == 1) {
                if (typeArgs[0] instanceof Class) {
                    return (Class<?>)typeArgs[0];
                }
                throw new RuntimeException("Unexpected Parameterised Type? " + typeArgs[0]);
            }
            else if (typeArgs.length == 2) {
                return (Class<?>)typeArgs[1];
            }
        }
        return null;
    }
    
    static {
        logger = Logger.getLogger(DeployCreateProperties.class.getName());
    }
}
