// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap.expression;

import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiExpression;

public abstract class LdAbstractExpression implements SpiExpression
{
    private static final long serialVersionUID = 4072786211853856174L;
    protected final String propertyName;
    
    protected LdAbstractExpression(final String propertyName) {
        this.propertyName = propertyName;
    }
    
    protected String nextParam(final SpiExpressionRequest request) {
        final int pos = request.nextParameter();
        return "{" + (pos - 1) + "}";
    }
    
    public void containsMany(final BeanDescriptor<?> desc, final ManyWhereJoins manyWhereJoin) {
        if (this.propertyName != null) {
            final ElPropertyDeploy elProp = desc.getElPropertyDeploy(this.propertyName);
            if (elProp != null && elProp.containsMany()) {
                manyWhereJoin.add(elProp);
            }
        }
    }
    
    protected ElPropertyValue getElProp(final SpiExpressionRequest request) {
        return request.getBeanDescriptor().getElGetValue(this.propertyName);
    }
}
