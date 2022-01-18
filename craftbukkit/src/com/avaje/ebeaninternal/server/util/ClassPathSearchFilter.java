// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.util;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

public class ClassPathSearchFilter
{
    private static final String COM_AVAJE_EBEANINTERNAL_SERVER_BEAN = "com.avaje.ebeaninternal.server.bean";
    private static final String COM_AVAJE_EBEAN_META = "com.avaje.ebean.meta";
    private boolean defaultPackageMatch;
    private boolean defaultJarMatch;
    private String ebeanJarPrefix;
    private HashSet<String> includePackageSet;
    private HashSet<String> excludePackageSet;
    private HashSet<String> includeJarSet;
    private HashSet<String> excludeJarSet;
    
    public ClassPathSearchFilter() {
        this.defaultPackageMatch = true;
        this.defaultJarMatch = false;
        this.ebeanJarPrefix = "ebean";
        this.includePackageSet = new HashSet<String>();
        this.excludePackageSet = new HashSet<String>();
        this.includeJarSet = new HashSet<String>();
        this.excludeJarSet = new HashSet<String>();
        this.addDefaultExcludePackages();
    }
    
    public void setEbeanJarPrefix(final String ebeanJarPrefix) {
        this.ebeanJarPrefix = ebeanJarPrefix;
    }
    
    public Set<String> getIncludePackages() {
        return this.includePackageSet;
    }
    
    public void addDefaultExcludePackages() {
        this.excludePackage("sun");
        this.excludePackage("com.sun");
        this.excludePackage("java");
        this.excludePackage("javax");
        this.excludePackage("junit");
        this.excludePackage("org.w3c");
        this.excludePackage("org.xml");
        this.excludePackage("org.apache");
        this.excludePackage("com.mysql");
        this.excludePackage("oracle.jdbc");
        this.excludePackage("com.microsoft.sqlserver");
        this.excludePackage("com.avaje.ebean");
        this.excludePackage("com.avaje.lib");
    }
    
    public void clearExcludePackages() {
        this.excludePackageSet.clear();
    }
    
    public void setDefaultJarMatch(final boolean defaultJarMatch) {
        this.defaultJarMatch = defaultJarMatch;
    }
    
    public void setDefaultPackageMatch(final boolean defaultPackageMatch) {
        this.defaultPackageMatch = defaultPackageMatch;
    }
    
    public void includePackage(final String pckgName) {
        this.includePackageSet.add(pckgName);
    }
    
    public void excludePackage(final String pckgName) {
        this.excludePackageSet.add(pckgName);
    }
    
    public void excludeJar(final String jarName) {
        this.includeJarSet.add(jarName);
    }
    
    public void includeJar(final String jarName) {
        this.includeJarSet.add(jarName);
    }
    
    public boolean isSearchPackage(final String packageName) {
        if ("com.avaje.ebean.meta".equals(packageName)) {
            return true;
        }
        if ("com.avaje.ebeaninternal.server.bean".equals(packageName)) {
            return true;
        }
        if (this.includePackageSet != null && !this.includePackageSet.isEmpty()) {
            return this.containedIn(this.includePackageSet, packageName);
        }
        return !this.containedIn(this.excludePackageSet, packageName) && this.defaultPackageMatch;
    }
    
    public boolean isSearchJar(final String jarName) {
        return jarName.startsWith(this.ebeanJarPrefix) || this.containedIn(this.includeJarSet, jarName) || (!this.containedIn(this.excludeJarSet, jarName) && this.defaultJarMatch);
    }
    
    protected boolean containedIn(final HashSet<String> set, final String match) {
        if (set.contains(match)) {
            return true;
        }
        for (final String val : set) {
            if (match.startsWith(val)) {
                return true;
            }
        }
        return false;
    }
}
