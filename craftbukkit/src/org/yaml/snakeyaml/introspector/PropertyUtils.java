// 
// Decompiled by Procyon v0.5.30
// 

package org.yaml.snakeyaml.introspector;

import java.util.Iterator;
import java.util.Collection;
import java.util.TreeSet;
import java.beans.IntrospectionException;
import java.lang.reflect.Method;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import org.yaml.snakeyaml.error.YAMLException;
import java.beans.Introspector;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

public class PropertyUtils
{
    private final Map<Class<?>, Map<String, Property>> propertiesCache;
    private final Map<Class<?>, Set<Property>> readableProperties;
    private BeanAccess beanAccess;
    private boolean allowReadOnlyProperties;
    
    public PropertyUtils() {
        this.propertiesCache = new HashMap<Class<?>, Map<String, Property>>();
        this.readableProperties = new HashMap<Class<?>, Set<Property>>();
        this.beanAccess = BeanAccess.DEFAULT;
        this.allowReadOnlyProperties = false;
    }
    
    protected Map<String, Property> getPropertiesMap(final Class<?> type, final BeanAccess bAccess) throws IntrospectionException {
        if (this.propertiesCache.containsKey(type)) {
            return this.propertiesCache.get(type);
        }
        final Map<String, Property> properties = new LinkedHashMap<String, Property>();
        boolean inaccessableFieldsExist = false;
        switch (bAccess) {
            case FIELD: {
                for (Class<?> c = type; c != null; c = c.getSuperclass()) {
                    for (final Field field : c.getDeclaredFields()) {
                        final int modifiers = field.getModifiers();
                        if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers) && !properties.containsKey(field.getName())) {
                            properties.put(field.getName(), new FieldProperty(field));
                        }
                    }
                }
                break;
            }
            default: {
                for (final PropertyDescriptor property : Introspector.getBeanInfo(type).getPropertyDescriptors()) {
                    final Method readMethod = property.getReadMethod();
                    if (readMethod == null || !readMethod.getName().equals("getClass")) {
                        properties.put(property.getName(), new MethodProperty(property));
                    }
                }
                for (Class<?> c = type; c != null; c = c.getSuperclass()) {
                    for (final Field field : c.getDeclaredFields()) {
                        final int modifiers = field.getModifiers();
                        if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                            if (Modifier.isPublic(modifiers)) {
                                properties.put(field.getName(), new FieldProperty(field));
                            }
                            else {
                                inaccessableFieldsExist = true;
                            }
                        }
                    }
                }
                break;
            }
        }
        if (properties.isEmpty() && inaccessableFieldsExist) {
            throw new YAMLException("No JavaBean properties found in " + type.getName());
        }
        this.propertiesCache.put(type, properties);
        return properties;
    }
    
    public Set<Property> getProperties(final Class<?> type) throws IntrospectionException {
        return this.getProperties(type, this.beanAccess);
    }
    
    public Set<Property> getProperties(final Class<?> type, final BeanAccess bAccess) throws IntrospectionException {
        if (this.readableProperties.containsKey(type)) {
            return this.readableProperties.get(type);
        }
        final Set<Property> properties = this.createPropertySet(type, bAccess);
        this.readableProperties.put(type, properties);
        return properties;
    }
    
    protected Set<Property> createPropertySet(final Class<?> type, final BeanAccess bAccess) throws IntrospectionException {
        final Set<Property> properties = new TreeSet<Property>();
        final Collection<Property> props = this.getPropertiesMap(type, bAccess).values();
        for (final Property property : props) {
            if (property.isReadable() && (this.allowReadOnlyProperties || property.isWritable())) {
                properties.add(property);
            }
        }
        return properties;
    }
    
    public Property getProperty(final Class<?> type, final String name) throws IntrospectionException {
        return this.getProperty(type, name, this.beanAccess);
    }
    
    public Property getProperty(final Class<?> type, final String name, final BeanAccess bAccess) throws IntrospectionException {
        final Map<String, Property> properties = this.getPropertiesMap(type, bAccess);
        final Property property = properties.get(name);
        if (property == null || !property.isWritable()) {
            throw new YAMLException("Unable to find property '" + name + "' on class: " + type.getName());
        }
        return property;
    }
    
    public void setBeanAccess(final BeanAccess beanAccess) {
        if (this.beanAccess != beanAccess) {
            this.beanAccess = beanAccess;
            this.propertiesCache.clear();
            this.readableProperties.clear();
        }
    }
    
    public void setAllowReadOnlyProperties(final boolean allowReadOnlyProperties) {
        if (this.allowReadOnlyProperties != allowReadOnlyProperties) {
            this.allowReadOnlyProperties = allowReadOnlyProperties;
            this.readableProperties.clear();
        }
    }
}
