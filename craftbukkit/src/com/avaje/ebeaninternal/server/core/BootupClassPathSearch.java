// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.util.Iterator;
import java.util.Set;
import com.avaje.ebeaninternal.server.util.ClassPathSearchFilter;
import com.avaje.ebeaninternal.server.util.ClassPathSearchMatcher;
import com.avaje.ebeaninternal.server.util.ClassPathSearch;
import java.util.List;
import java.util.logging.Logger;

public class BootupClassPathSearch
{
    private static final Logger logger;
    private final Object monitor;
    private final ClassLoader classLoader;
    private final List<String> packages;
    private final List<String> jars;
    private BootupClasses bootupClasses;
    
    public BootupClassPathSearch(final ClassLoader classLoader, final List<String> packages, final List<String> jars) {
        this.monitor = new Object();
        this.classLoader = ((classLoader == null) ? this.getClass().getClassLoader() : classLoader);
        this.packages = packages;
        this.jars = jars;
    }
    
    public BootupClasses getBootupClasses() {
        synchronized (this.monitor) {
            if (this.bootupClasses == null) {
                this.bootupClasses = this.search();
            }
            return this.bootupClasses;
        }
    }
    
    private BootupClasses search() {
        synchronized (this.monitor) {
            try {
                final BootupClasses bc = new BootupClasses();
                final long st = System.currentTimeMillis();
                final ClassPathSearchFilter filter = this.createFilter();
                final ClassPathSearch finder = new ClassPathSearch(this.classLoader, filter, bc);
                finder.findClasses();
                final Set<String> jars = finder.getJarHits();
                final Set<String> pkgs = finder.getPackageHits();
                final long searchTime = System.currentTimeMillis() - st;
                final String msg = "Classpath search hits in jars" + jars + " pkgs" + pkgs + "  searchTime[" + searchTime + "]";
                BootupClassPathSearch.logger.info(msg);
                return bc;
            }
            catch (Exception ex) {
                final String msg2 = "Error in classpath search (looking for entities etc)";
                throw new RuntimeException(msg2, ex);
            }
        }
    }
    
    private ClassPathSearchFilter createFilter() {
        final ClassPathSearchFilter filter = new ClassPathSearchFilter();
        filter.addDefaultExcludePackages();
        if (this.packages != null) {
            for (final String packageName : this.packages) {
                filter.includePackage(packageName);
            }
        }
        if (this.jars != null) {
            for (final String jarName : this.jars) {
                filter.includeJar(jarName);
            }
        }
        return filter;
    }
    
    static {
        logger = Logger.getLogger(BootupClassPathSearch.class.getName());
    }
}
