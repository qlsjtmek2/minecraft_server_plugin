// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebeaninternal.api.SpiExpression;

public abstract class AbstractExpression implements SpiExpression
{
    private static final long serialVersionUID = 4072786211853856174L;
    protected final String propName;
    protected final FilterExprPath pathPrefix;
    
    protected AbstractExpression(final FilterExprPath pathPrefix, final String propName) {
        this.pathPrefix = pathPrefix;
        this.propName = propName;
    }
    
    protected String getPropertyName() {
        if (this.pathPrefix == null) {
            return this.propName;
        }
        final String path = this.pathPrefix.getPath();
        if (path == null || path.length() == 0) {
            return this.propName;
        }
        return path + "." + this.propName;
    }
    
    public boolean isLuceneResolvable(final LuceneResolvableRequest req) {
        return false;
    }
    
    public void containsMany(final BeanDescriptor<?> desc, final ManyWhereJoins manyWhereJoin) {
        final String propertyName = this.getPropertyName();
        if (propertyName != null) {
            final ElPropertyDeploy elProp = desc.getElPropertyDeploy(propertyName);
            if (elProp != null && elProp.containsMany()) {
                manyWhereJoin.add(elProp);
            }
        }
    }
    
    protected ElPropertyValue getElProp(final SpiExpressionRequest request) {
        final String propertyName = this.getPropertyName();
        return request.getBeanDescriptor().getElGetValue(propertyName);
    }
}
