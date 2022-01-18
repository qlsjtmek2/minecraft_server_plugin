// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.util.Map;
import java.util.Set;
import com.avaje.ebeaninternal.server.query.SplitName;
import com.avaje.ebean.config.CompoundTypeProperty;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;

public final class CtCompoundTypeScalarList
{
    private final LinkedHashMap<String, ScalarType<?>> scalarProps;
    private final LinkedHashMap<String, CtCompoundProperty> compoundProperties;
    
    public CtCompoundTypeScalarList() {
        this.scalarProps = new LinkedHashMap<String, ScalarType<?>>();
        this.compoundProperties = new LinkedHashMap<String, CtCompoundProperty>();
    }
    
    public List<CtCompoundProperty> getNonScalarProperties() {
        final List<CtCompoundProperty> nonScalarProps = new ArrayList<CtCompoundProperty>();
        for (final String propKey : this.compoundProperties.keySet()) {
            if (!this.scalarProps.containsKey(propKey)) {
                nonScalarProps.add(this.compoundProperties.get(propKey));
            }
        }
        return nonScalarProps;
    }
    
    public void addCompoundProperty(final String propName, final CtCompoundType<?> t, final CompoundTypeProperty<?, ?> prop) {
        CtCompoundProperty parent = null;
        final String[] split = SplitName.split(propName);
        if (split[0] != null) {
            parent = this.compoundProperties.get(split[0]);
        }
        final CtCompoundProperty p = new CtCompoundProperty(propName, parent, t, prop);
        this.compoundProperties.put(propName, p);
    }
    
    public void addScalarType(final String propName, final ScalarType<?> scalar) {
        this.scalarProps.put(propName, scalar);
    }
    
    public CtCompoundProperty getCompoundType(final String propName) {
        return this.compoundProperties.get(propName);
    }
    
    public Set<Map.Entry<String, ScalarType<?>>> entries() {
        return this.scalarProps.entrySet();
    }
}
