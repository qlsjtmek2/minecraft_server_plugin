// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.meta;

import java.util.HashMap;
import java.util.Map;

public class DeployBeanEmbedded
{
    Map<String, String> propMap;
    
    public DeployBeanEmbedded() {
        this.propMap = new HashMap<String, String>();
    }
    
    public void put(final String propertyName, final String dbCoumn) {
        this.propMap.put(propertyName, dbCoumn);
    }
    
    public void putAll(final Map<String, String> propertyColumnMap) {
        this.propMap.putAll(propertyColumnMap);
    }
    
    public Map<String, String> getPropertyColumnMap() {
        return this.propMap;
    }
}
