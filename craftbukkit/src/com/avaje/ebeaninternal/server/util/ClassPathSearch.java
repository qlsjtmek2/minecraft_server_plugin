// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.jar.JarFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.logging.Level;
import com.avaje.ebeaninternal.api.ClassUtil;
import com.avaje.ebean.config.GlobalProperties;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ClassPathSearch
{
    private static final Logger logger;
    ClassLoader classLoader;
    Object[] classPaths;
    ClassPathSearchFilter filter;
    ClassPathSearchMatcher matcher;
    ArrayList<Class<?>> matchList;
    HashSet<String> jarHits;
    HashSet<String> packageHits;
    ClassPathReader classPathReader;
    
    public ClassPathSearch(final ClassLoader classLoader, final ClassPathSearchFilter filter, final ClassPathSearchMatcher matcher) {
        this.matchList = new ArrayList<Class<?>>();
        this.jarHits = new HashSet<String>();
        this.packageHits = new HashSet<String>();
        this.classPathReader = new DefaultClassPathReader();
        this.classLoader = classLoader;
        this.filter = filter;
        this.matcher = matcher;
        this.initClassPaths();
    }
    
    private void initClassPaths() {
        try {
            final String cn = GlobalProperties.get("ebean.classpathreader", null);
            if (cn != null) {
                ClassPathSearch.logger.info("Using [" + cn + "] to read the searchable class path");
                this.classPathReader = (ClassPathReader)ClassUtil.newInstance(cn, this.getClass());
            }
            this.classPaths = this.classPathReader.readPath(this.classLoader);
            if (this.classPaths == null || this.classPaths.length == 0) {
                final String msg = "ClassPath is EMPTY using ClassPathReader [" + this.classPathReader + "]";
                ClassPathSearch.logger.warning(msg);
            }
            final boolean debug = GlobalProperties.getBoolean("ebean.debug.classpath", false);
            if (debug || ClassPathSearch.logger.isLoggable(Level.FINER)) {
                final String msg2 = "Classpath " + Arrays.toString(this.classPaths);
                ClassPathSearch.logger.info(msg2);
            }
        }
        catch (Exception e) {
            final String msg = "Error trying to read the classpath entries";
            throw new RuntimeException(msg, e);
        }
    }
    
    public Set<String> getJarHits() {
        return this.jarHits;
    }
    
    public Set<String> getPackageHits() {
        return this.packageHits;
    }
    
    private void registerHit(final String jarFileName, final Class<?> cls) {
        if (jarFileName != null) {
            this.jarHits.add(jarFileName);
        }
        final Package pkg = cls.getPackage();
        if (pkg != null) {
            this.packageHits.add(pkg.getName());
        }
        else {
            this.packageHits.add("");
        }
    }
    
    public List<Class<?>> findClasses() throws ClassNotFoundException {
        if (this.classPaths == null || this.classPaths.length == 0) {
            return this.matchList;
        }
        final String charsetName = Charset.defaultCharset().name();
        for (int h = 0; h < this.classPaths.length; ++h) {
            String jarFileName = null;
            Enumeration<?> files = null;
            JarFile module = null;
            File classPath;
            if (URL.class.isInstance(this.classPaths[h])) {
                classPath = new File(((URL)this.classPaths[h]).getFile());
            }
            else {
                classPath = new File(this.classPaths[h].toString());
            }
            try {
                final String path = URLDecoder.decode(classPath.getAbsolutePath(), charsetName);
                classPath = new File(path);
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            Label_0316: {
                if (classPath.isDirectory()) {
                    files = this.getDirectoryEnumeration(classPath);
                }
                else {
                    if (classPath.getName().endsWith(".jar")) {
                        jarFileName = classPath.getName();
                        if (!this.filter.isSearchJar(jarFileName)) {
                            continue;
                        }
                        try {
                            module = new JarFile(classPath);
                            files = module.entries();
                            break Label_0316;
                        }
                        catch (MalformedURLException ex) {
                            throw new ClassNotFoundException("Bad classpath. Error: ", ex);
                        }
                        catch (IOException ex2) {
                            final String msg = "jar file '" + classPath.getAbsolutePath() + "' could not be instantiate from file path. Error: ";
                            throw new ClassNotFoundException(msg, ex2);
                        }
                    }
                    final String msg2 = "Error: expected classPath entry [" + classPath.getAbsolutePath() + "] to be a directory or a .jar file but it is not either of those?";
                    ClassPathSearch.logger.log(Level.SEVERE, msg2);
                }
            }
            this.searchFiles(files, jarFileName);
            if (module != null) {
                try {
                    module.close();
                }
                catch (IOException e2) {
                    final String msg = "Error closing jar";
                    throw new ClassNotFoundException(msg, e2);
                }
            }
        }
        if (this.matchList.isEmpty()) {
            final String msg3 = "No Entities found in ClassPath using ClassPathReader [" + this.classPathReader + "] Classpath Searched[" + Arrays.toString(this.classPaths) + "]";
            ClassPathSearch.logger.warning(msg3);
        }
        return this.matchList;
    }
    
    private Enumeration<?> getDirectoryEnumeration(final File classPath) {
        final ArrayList<String> fileNameList = new ArrayList<String>();
        final Set<String> includePkgs = this.filter.getIncludePackages();
        if (includePkgs.size() > 0) {
            for (final String pkg : includePkgs) {
                final String relPath = pkg.replace('.', '/');
                final File dir = new File(classPath, relPath);
                if (dir.exists()) {
                    this.recursivelyListDir(fileNameList, dir, new StringBuilder(relPath));
                }
            }
        }
        else {
            this.recursivelyListDir(fileNameList, classPath, new StringBuilder());
        }
        return Collections.enumeration((Collection<?>)fileNameList);
    }
    
    private void searchFiles(final Enumeration<?> files, final String jarFileName) {
        while (files != null && files.hasMoreElements()) {
            final String fileName = files.nextElement().toString();
            if (fileName.endsWith(".class")) {
                final String className = fileName.replace('/', '.').substring(0, fileName.length() - 6);
                final int lastPeriod = className.lastIndexOf(".");
                String pckgName;
                if (lastPeriod > 0) {
                    pckgName = className.substring(0, lastPeriod);
                }
                else {
                    pckgName = "";
                }
                if (!this.filter.isSearchPackage(pckgName)) {
                    continue;
                }
                Class<?> theClass = null;
                try {
                    theClass = Class.forName(className, false, this.classLoader);
                    if (!this.matcher.isMatch(theClass)) {
                        continue;
                    }
                    this.matchList.add(theClass);
                    this.registerHit(jarFileName, theClass);
                }
                catch (ClassNotFoundException e) {
                    ClassPathSearch.logger.finer("Error searching classpath" + e.getMessage());
                }
                catch (NoClassDefFoundError e2) {
                    ClassPathSearch.logger.finer("Error searching classpath: " + e2.getMessage());
                }
            }
        }
    }
    
    private void recursivelyListDir(final List<String> fileNameList, final File dir, final StringBuilder relativePath) {
        if (dir.isDirectory()) {
            final File[] files = dir.listFiles();
            for (int i = 0; i < files.length; ++i) {
                final int prevLen = relativePath.length();
                relativePath.append((prevLen == 0) ? "" : "/").append(files[i].getName());
                this.recursivelyListDir(fileNameList, files[i], relativePath);
                relativePath.delete(prevLen, relativePath.length());
            }
        }
        else {
            fileNameList.add(relativePath.toString());
        }
    }
    
    static {
        logger = Logger.getLogger(ClassPathSearch.class.getName());
    }
}
