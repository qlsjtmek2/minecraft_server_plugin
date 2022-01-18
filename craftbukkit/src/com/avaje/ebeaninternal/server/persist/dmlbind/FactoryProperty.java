// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;
import com.avaje.ebeaninternal.server.persist.dml.DmlMode;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public class FactoryProperty
{
    private final boolean bindEncryptDataFirst;
    
    public FactoryProperty(final boolean bindEncryptDataFirst) {
        this.bindEncryptDataFirst = bindEncryptDataFirst;
    }
    
    public Bindable create(final BeanProperty prop, final DmlMode mode, final boolean withLobs) {
        if (DmlMode.INSERT.equals(mode) && !prop.isDbInsertable()) {
            return null;
        }
        if (DmlMode.UPDATE.equals(mode) && !prop.isDbUpdatable()) {
            return null;
        }
        if (!prop.isLob()) {
            final GeneratedProperty gen = prop.getGeneratedProperty();
            if (gen != null) {
                if (DmlMode.INSERT.equals(mode)) {
                    if (gen.includeInInsert()) {
                        return new BindablePropertyInsertGenerated(prop, gen);
                    }
                    return null;
                }
                else if (DmlMode.UPDATE.equals(mode)) {
                    if (gen.includeInUpdate()) {
                        return new BindablePropertyUpdateGenerated(prop, gen);
                    }
                    return null;
                }
            }
            return prop.isDbEncrypted() ? new BindableEncryptedProperty(prop, this.bindEncryptDataFirst) : new BindableProperty(prop);
        }
        if (DmlMode.WHERE.equals(mode) || !withLobs) {
            return null;
        }
        return prop.isDbEncrypted() ? new BindableEncryptedProperty(prop, this.bindEncryptDataFirst) : new BindableProperty(prop);
    }
}
