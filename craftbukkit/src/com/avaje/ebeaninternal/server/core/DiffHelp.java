// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.util.ValueUtil;
import java.util.LinkedHashMap;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.ValuePair;
import java.util.Map;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public class DiffHelp
{
    public Map<String, ValuePair> diff(final Object a, Object b, final BeanDescriptor<?> desc) {
        boolean oldValues = false;
        if (b == null && a instanceof EntityBean) {
            final EntityBean eb = (EntityBean)a;
            b = eb._ebean_getIntercept().getOldValues();
            oldValues = true;
        }
        final Map<String, ValuePair> map = new LinkedHashMap<String, ValuePair>();
        if (b == null) {
            return map;
        }
        final BeanProperty[] base = desc.propertiesBaseScalar();
        for (int i = 0; i < base.length; ++i) {
            final Object aval = base[i].getValue(a);
            final Object bval = base[i].getValue(b);
            if (!ValueUtil.areEqual(aval, bval)) {
                map.put(base[i].getName(), new ValuePair(aval, bval));
            }
        }
        this.diffAssocOne(a, b, desc, map);
        this.diffEmbedded(a, b, desc, map, oldValues);
        return map;
    }
    
    private void diffEmbedded(final Object a, final Object b, final BeanDescriptor<?> desc, final Map<String, ValuePair> map, final boolean oldValues) {
        final BeanPropertyAssocOne<?>[] emb = desc.propertiesEmbedded();
        for (int i = 0; i < emb.length; ++i) {
            final Object aval = emb[i].getValue(a);
            Object bval = emb[i].getValue(b);
            if (oldValues) {
                bval = ((EntityBean)bval)._ebean_getIntercept().getOldValues();
                if (bval == null) {
                    continue;
                }
            }
            if (!this.isBothNull(aval, bval)) {
                if (this.isDiffNull(aval, bval)) {
                    map.put(emb[i].getName(), new ValuePair(aval, bval));
                }
                else {
                    final BeanProperty[] props = emb[i].getProperties();
                    for (int j = 0; j < props.length; ++j) {
                        final Object aEmbPropVal = props[j].getValue(aval);
                        final Object bEmbPropVal = props[j].getValue(bval);
                        if (!ValueUtil.areEqual(aEmbPropVal, bEmbPropVal)) {
                            map.put(emb[i].getName(), new ValuePair(aval, bval));
                        }
                    }
                }
            }
        }
    }
    
    private void diffAssocOne(final Object a, final Object b, final BeanDescriptor<?> desc, final Map<String, ValuePair> map) {
        final BeanPropertyAssocOne<?>[] ones = desc.propertiesOne();
        for (int i = 0; i < ones.length; ++i) {
            final Object aval = ones[i].getValue(a);
            final Object bval = ones[i].getValue(b);
            if (!this.isBothNull(aval, bval)) {
                if (this.isDiffNull(aval, bval)) {
                    map.put(ones[i].getName(), new ValuePair(aval, bval));
                }
                else {
                    final BeanDescriptor<?> oneDesc = ones[i].getTargetDescriptor();
                    final Object aOneId = oneDesc.getId(aval);
                    final Object bOneId = oneDesc.getId(bval);
                    if (!ValueUtil.areEqual(aOneId, bOneId)) {
                        map.put(ones[i].getName(), new ValuePair(aval, bval));
                    }
                }
            }
        }
    }
    
    private boolean isBothNull(final Object aval, final Object bval) {
        return aval == null && bval == null;
    }
    
    private boolean isDiffNull(final Object aval, final Object bval) {
        if (aval == null) {
            return bval != null;
        }
        return bval == null;
    }
}
