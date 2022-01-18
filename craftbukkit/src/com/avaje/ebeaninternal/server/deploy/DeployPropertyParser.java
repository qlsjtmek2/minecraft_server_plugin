// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
import java.util.HashSet;
import java.util.Set;

public final class DeployPropertyParser extends DeployParser
{
    private final BeanDescriptor<?> beanDescriptor;
    private final Set<String> includes;
    
    public DeployPropertyParser(final BeanDescriptor<?> beanDescriptor) {
        this.includes = new HashSet<String>();
        this.beanDescriptor = beanDescriptor;
    }
    
    public Set<String> getIncludes() {
        return this.includes;
    }
    
    public String getDeployWord(final String expression) {
        final ElPropertyDeploy elProp = this.beanDescriptor.getElPropertyDeploy(expression);
        if (elProp == null) {
            return null;
        }
        this.addIncludes(elProp.getElPrefix());
        return elProp.getElPlaceholder(this.encrypted);
    }
    
    public String convertWord() {
        final String r = this.getDeployWord(this.word);
        return (r == null) ? this.word : r;
    }
    
    private void addIncludes(final String prefix) {
        if (prefix != null) {
            this.includes.add(prefix);
        }
    }
}
