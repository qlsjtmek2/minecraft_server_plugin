// 
// Decompiled by Procyon v0.5.30
// 

package org.yaml.snakeyaml.introspector;

import org.yaml.snakeyaml.error.YAMLException;
import java.beans.PropertyDescriptor;

public class MethodProperty extends GenericProperty
{
    private final PropertyDescriptor property;
    private final boolean readable;
    private final boolean writable;
    
    public MethodProperty(final PropertyDescriptor property) {
        super(property.getName(), property.getPropertyType(), (property.getReadMethod() == null) ? null : property.getReadMethod().getGenericReturnType());
        this.property = property;
        this.readable = (property.getReadMethod() != null);
        this.writable = (property.getWriteMethod() != null);
    }
    
    public void set(final Object object, final Object value) throws Exception {
        this.property.getWriteMethod().invoke(object, value);
    }
    
    public Object get(final Object object) {
        try {
            this.property.getReadMethod().setAccessible(true);
            return this.property.getReadMethod().invoke(object, new Object[0]);
        }
        catch (Exception e) {
            throw new YAMLException("Unable to find getter for property '" + this.property.getName() + "' on object " + object + ":" + e);
        }
    }
    
    public boolean isWritable() {
        return this.writable;
    }
    
    public boolean isReadable() {
        return this.readable;
    }
}
