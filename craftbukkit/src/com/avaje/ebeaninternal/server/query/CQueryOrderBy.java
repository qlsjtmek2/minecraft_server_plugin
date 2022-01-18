// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
import java.util.List;
import com.avaje.ebean.OrderBy;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public class CQueryOrderBy
{
    private final BeanDescriptor<?> desc;
    private final SpiQuery<?> query;
    
    public static String parse(final BeanDescriptor<?> desc, final SpiQuery<?> query) {
        return new CQueryOrderBy(desc, query).parseInternal();
    }
    
    private CQueryOrderBy(final BeanDescriptor<?> desc, final SpiQuery<?> query) {
        this.desc = desc;
        this.query = query;
    }
    
    private String parseInternal() {
        final OrderBy<?> orderBy = this.query.getOrderBy();
        if (orderBy == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        final List<OrderBy.Property> properties = orderBy.getProperties();
        if (properties.isEmpty()) {
            return null;
        }
        for (int i = 0; i < properties.size(); ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            final OrderBy.Property p = properties.get(i);
            final String expression = this.parseProperty(p);
            sb.append(expression);
        }
        return sb.toString();
    }
    
    private String parseProperty(final OrderBy.Property p) {
        final String propName = p.getProperty();
        final ElPropertyValue el = this.desc.getElGetValue(propName);
        if (el == null) {
            return p.toStringFormat();
        }
        final BeanProperty beanProperty = el.getBeanProperty();
        if (beanProperty instanceof BeanPropertyAssoc) {
            final BeanPropertyAssoc<?> ap = (BeanPropertyAssoc<?>)beanProperty;
            final IdBinder idBinder = ap.getTargetDescriptor().getIdBinder();
            return idBinder.getOrderBy(el.getElName(), p.isAscending());
        }
        return p.toStringFormat();
    }
}
