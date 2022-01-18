// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

public class BeanEmbeddedMeta
{
    final BeanProperty[] properties;
    
    public BeanEmbeddedMeta(final BeanProperty[] properties) {
        this.properties = properties;
    }
    
    public BeanProperty[] getProperties() {
        return this.properties;
    }
    
    public boolean isEmbeddedVersion() {
        for (int i = 0; i < this.properties.length; ++i) {
            if (this.properties[i].isVersion()) {
                return true;
            }
        }
        return false;
    }
}
