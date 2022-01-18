// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;

class MatchedImportedProperty
{
    private final BeanPropertyAssocOne<?> assocOne;
    private final BeanProperty foreignProp;
    private final BeanProperty localProp;
    
    protected MatchedImportedProperty(final BeanPropertyAssocOne<?> assocOne, final BeanProperty foreignProp, final BeanProperty localProp) {
        this.assocOne = assocOne;
        this.foreignProp = foreignProp;
        this.localProp = localProp;
    }
    
    protected void populate(final Object sourceBean, final Object destBean) {
        final Object assocBean = this.assocOne.getValue(sourceBean);
        if (assocBean == null) {
            final String msg = "The assoc bean for " + this.assocOne + " is null?";
            throw new NullPointerException(msg);
        }
        final Object value = this.foreignProp.getValue(assocBean);
        this.localProp.setValue(destBean, value);
    }
    
    protected static MatchedImportedProperty[] build(final BeanProperty[] props, final BeanDescriptor<?> desc) {
        final MatchedImportedProperty[] matches = new MatchedImportedProperty[props.length];
        for (int i = 0; i < props.length; ++i) {
            matches[i] = findMatch(props[i], desc);
            if (matches[i] == null) {
                return null;
            }
        }
        return matches;
    }
    
    private static MatchedImportedProperty findMatch(final BeanProperty prop, final BeanDescriptor<?> desc) {
        final String dbColumn = prop.getDbColumn();
        final BeanPropertyAssocOne<?>[] assocOnes = desc.propertiesOne();
        for (int i = 0; i < assocOnes.length; ++i) {
            if (assocOnes[i].isImportedPrimaryKey()) {
                final BeanProperty foreignMatch = assocOnes[i].getImportedId().findMatchImport(dbColumn);
                if (foreignMatch != null) {
                    return new MatchedImportedProperty(assocOnes[i], foreignMatch, prop);
                }
            }
        }
        return null;
    }
}
