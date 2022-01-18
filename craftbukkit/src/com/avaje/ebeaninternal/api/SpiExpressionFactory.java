// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import com.avaje.ebeaninternal.server.expression.FilterExprPath;
import com.avaje.ebean.ExpressionFactory;

public interface SpiExpressionFactory extends ExpressionFactory
{
    ExpressionFactory createExpressionFactory(final FilterExprPath p0);
}
