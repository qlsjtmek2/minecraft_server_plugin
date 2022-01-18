// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import java.util.HashMap;

public class IgnoreClassHelper
{
    private final String[] processPackages;
    
    public IgnoreClassHelper(final String agentArgs) {
        final HashMap<String, String> args = ArgParser.parse(agentArgs);
        final String packages = args.get("packages");
        if (packages != null) {
            final String[] pkgs = packages.split(",");
            this.processPackages = new String[pkgs.length];
            for (int i = 0; i < pkgs.length; ++i) {
                this.processPackages[i] = this.convertPackage(pkgs[i]);
            }
        }
        else {
            this.processPackages = new String[0];
        }
    }
    
    private String convertPackage(String pkg) {
        pkg = pkg.trim().replace('.', '/');
        if (pkg.endsWith("*")) {
            return pkg.substring(0, pkg.length() - 1);
        }
        if (pkg.endsWith("/")) {
            return pkg;
        }
        return pkg + "/";
    }
    
    private boolean specificMatching(final String className) {
        for (int i = 0; i < this.processPackages.length; ++i) {
            if (className.startsWith(this.processPackages[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isIgnoreClass(String className) {
        className = className.replace('.', '/');
        if (className.startsWith("com/avaje/ebean/meta/")) {
            return false;
        }
        if (this.processPackages.length > 0) {
            return this.specificMatching(className);
        }
        return className.startsWith("com/avaje/ebean") || (className.startsWith("java/") || className.startsWith("javax/")) || (className.startsWith("sun/") || className.startsWith("sunw/") || className.startsWith("com/sun/")) || (className.startsWith("org/wc3/") || className.startsWith("org/xml/")) || (className.startsWith("org/junit/") || className.startsWith("junit/")) || className.startsWith("org/apache/") || className.startsWith("org/eclipse/") || className.startsWith("org/joda/") || className.startsWith("com/mysql/jdbc") || className.startsWith("org/postgresql/") || className.startsWith("org/h2/") || className.startsWith("oracle/") || className.startsWith("groovy/") || className.startsWith("$");
    }
}
