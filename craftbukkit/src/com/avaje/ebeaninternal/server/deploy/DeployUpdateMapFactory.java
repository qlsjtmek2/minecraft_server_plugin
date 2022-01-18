// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
import java.util.logging.Level;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DeployUpdateMapFactory
{
    private static final Logger logger;
    
    public static Map<String, String> build(final BeanDescriptor<?> descriptor) {
        final Map<String, String> deployMap = new HashMap<String, String>();
        final String shortName = descriptor.getName();
        final String beanName = shortName.toLowerCase();
        deployMap.put(beanName, descriptor.getBaseTable());
        final BeanProperty[] arr$;
        final BeanProperty[] baseScalar = arr$ = descriptor.propertiesBaseScalar();
        for (final BeanProperty baseProp : arr$) {
            if (baseProp.isDbInsertable() || baseProp.isDbUpdatable()) {
                deployMap.put(baseProp.getName().toLowerCase(), baseProp.getDbColumn());
            }
        }
        final BeanPropertyAssocOne<?>[] arr$2;
        final BeanPropertyAssocOne<?>[] oneImported = arr$2 = descriptor.propertiesOneImported();
        for (final BeanPropertyAssocOne<?> assocOne : arr$2) {
            final ImportedId importedId = assocOne.getImportedId();
            if (importedId == null) {
                final String m = descriptor.getFullName() + " importedId is null for associated: " + assocOne.getFullBeanName();
                DeployUpdateMapFactory.logger.log(Level.SEVERE, m);
            }
            else if (importedId.isScalar()) {
                deployMap.put(importedId.getLogicalName(), importedId.getDbColumn());
            }
        }
        return deployMap;
    }
    
    static {
        logger = Logger.getLogger(DeployUpdateMapFactory.class.getName());
    }
}
