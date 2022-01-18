// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.el;

import java.util.ArrayList;
import java.util.List;

public class ElPropertyChainBuilder
{
    private final String expression;
    private final List<ElPropertyValue> chain;
    private final boolean embedded;
    private boolean containsMany;
    
    public ElPropertyChainBuilder(final boolean embedded, final String expression) {
        this.chain = new ArrayList<ElPropertyValue>();
        this.embedded = embedded;
        this.expression = expression;
    }
    
    public boolean isContainsMany() {
        return this.containsMany;
    }
    
    public void setContainsMany(final boolean containsMany) {
        this.containsMany = containsMany;
    }
    
    public String getExpression() {
        return this.expression;
    }
    
    public ElPropertyChainBuilder add(final ElPropertyValue element) {
        if (element == null) {
            throw new NullPointerException("element null in expression " + this.expression);
        }
        this.chain.add(element);
        return this;
    }
    
    public ElPropertyChain build() {
        return new ElPropertyChain(this.containsMany, this.embedded, this.expression, this.chain.toArray(new ElPropertyValue[this.chain.size()]));
    }
}
