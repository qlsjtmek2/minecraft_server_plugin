// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.bean;

public class SerializeControl
{
    private static final String BEANS = "com.avaje.ebean.vanillabeans";
    private static final String COLLECTIONS = "com.avaje.ebean.vanillacollections";
    private static ThreadLocal<Boolean> vanillaBeans;
    private static ThreadLocal<Boolean> vanillaCollections;
    
    private static Boolean getDefault(final String key, final Boolean dflt) {
        final String val = System.getProperty(key);
        if (val != null) {
            return val.equalsIgnoreCase("true");
        }
        return dflt;
    }
    
    public static void setDefaultForBeans(final boolean vanillaOn) {
        final Boolean b = vanillaOn;
        System.setProperty("com.avaje.ebean.vanillabeans", b.toString());
    }
    
    public static void setDefaultForCollections(final boolean vanillaOn) {
        final Boolean b = vanillaOn;
        System.setProperty("com.avaje.ebean.vanillacollections", b.toString());
    }
    
    public static void resetToDefault() {
        final Boolean beans = getDefault("com.avaje.ebean.vanillabeans", Boolean.FALSE);
        setVanillaBeans(beans);
        final Boolean coll = getDefault("com.avaje.ebean.vanillacollections", Boolean.FALSE);
        setVanillaCollections(coll);
    }
    
    public static void setVanilla(final boolean vanillaOn) {
        if (vanillaOn) {
            SerializeControl.vanillaBeans.set(Boolean.TRUE);
            SerializeControl.vanillaCollections.set(Boolean.TRUE);
        }
        else {
            SerializeControl.vanillaBeans.set(Boolean.FALSE);
            SerializeControl.vanillaCollections.set(Boolean.FALSE);
        }
    }
    
    public static boolean isVanillaBeans() {
        return SerializeControl.vanillaBeans.get();
    }
    
    public static void setVanillaBeans(final boolean vanillaOn) {
        SerializeControl.vanillaBeans.set(vanillaOn);
    }
    
    public static boolean isVanillaCollections() {
        return SerializeControl.vanillaCollections.get();
    }
    
    public static void setVanillaCollections(final boolean vanillaOn) {
        SerializeControl.vanillaCollections.set(vanillaOn);
    }
    
    static {
        SerializeControl.vanillaBeans = new ThreadLocal<Boolean>() {
            protected synchronized Boolean initialValue() {
                return getDefault("com.avaje.ebean.vanillabeans", Boolean.TRUE);
            }
        };
        SerializeControl.vanillaCollections = new ThreadLocal<Boolean>() {
            protected synchronized Boolean initialValue() {
                return getDefault("com.avaje.ebean.vanillacollections", Boolean.TRUE);
            }
        };
    }
}
