// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.util.Set;
import java.util.Map;

public final class DeployPropertyParserMap extends DeployParser
{
    private final Map<String, String> map;
    
    public DeployPropertyParserMap(final Map<String, String> map) {
        this.map = map;
    }
    
    public Set<String> getIncludes() {
        return null;
    }
    
    public String convertWord() {
        final String r = this.getDeployWord(this.word);
        return (r == null) ? this.word : r;
    }
    
    public String getDeployWord(final String expression) {
        final String deployExpr = this.map.get(expression);
        if (deployExpr == null) {
            return null;
        }
        return deployExpr;
    }
}
