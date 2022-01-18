// 
// Decompiled by Procyon v0.5.30
// 

package org.fusesource.hawtjni.runtime;

import java.net.MalformedURLException;
import java.util.regex.Pattern;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class Library
{
    static final String SLASH;
    private final String name;
    private final String version;
    private final ClassLoader classLoader;
    private boolean loaded;
    
    public Library(final String name) {
        this(name, null, null);
    }
    
    public Library(final String name, final Class<?> clazz) {
        this(name, version(clazz), clazz.getClassLoader());
    }
    
    public Library(final String name, final String version) {
        this(name, version, null);
    }
    
    public Library(final String name, final String version, final ClassLoader classLoader) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        this.name = name;
        this.version = version;
        this.classLoader = classLoader;
    }
    
    private static String version(final Class<?> clazz) {
        try {
            return clazz.getPackage().getImplementationVersion();
        }
        catch (Throwable e) {
            return null;
        }
    }
    
    public static String getOperatingSystem() {
        final String name = System.getProperty("os.name").toLowerCase().trim();
        if (name.startsWith("linux")) {
            return "linux";
        }
        if (name.startsWith("mac os x")) {
            return "osx";
        }
        if (name.startsWith("win")) {
            return "windows";
        }
        return name.replaceAll("\\W+", "_");
    }
    
    public static String getPlatform() {
        return getOperatingSystem() + getBitModel();
    }
    
    public static int getBitModel() {
        String prop = System.getProperty("sun.arch.data.model");
        if (prop == null) {
            prop = System.getProperty("com.ibm.vm.bitmode");
        }
        if (prop != null) {
            return Integer.parseInt(prop);
        }
        return -1;
    }
    
    public synchronized void load() {
        if (this.loaded) {
            return;
        }
        this.doLoad();
        this.loaded = true;
    }
    
    private void doLoad() {
        String version = System.getProperty("library." + this.name + ".version");
        if (version == null) {
            version = this.version;
        }
        final ArrayList<String> errors = new ArrayList<String>();
        final String customPath = System.getProperty("library." + this.name + ".path");
        if (customPath != null) {
            if (version != null && this.load(errors, this.file(customPath, this.map(this.name + "-" + version)))) {
                return;
            }
            if (this.load(errors, this.file(customPath, this.map(this.name)))) {
                return;
            }
        }
        if (version != null && this.load(errors, this.name + getBitModel() + "-" + version)) {
            return;
        }
        if (version != null && this.load(errors, this.name + "-" + version)) {
            return;
        }
        if (this.load(errors, this.name)) {
            return;
        }
        if (this.classLoader != null) {
            if (this.exractAndLoad(errors, version, customPath, this.getPlatformSpecifcResourcePath())) {
                return;
            }
            if (this.exractAndLoad(errors, version, customPath, this.getOperatingSystemSpecifcResourcePath())) {
                return;
            }
            if (this.exractAndLoad(errors, version, customPath, this.getResorucePath())) {
                return;
            }
        }
        throw new UnsatisfiedLinkError("Could not load library. Reasons: " + errors.toString());
    }
    
    public final String getOperatingSystemSpecifcResourcePath() {
        return this.getPlatformSpecifcResourcePath(getOperatingSystem());
    }
    
    public final String getPlatformSpecifcResourcePath() {
        return this.getPlatformSpecifcResourcePath(getPlatform());
    }
    
    public final String getPlatformSpecifcResourcePath(final String platform) {
        return "META-INF/native/" + platform + "/" + this.map(this.name);
    }
    
    public final String getResorucePath() {
        return "META-INF/native/" + this.map(this.name);
    }
    
    public final String getLibraryFileName() {
        return this.map(this.name);
    }
    
    private boolean exractAndLoad(final ArrayList<String> errors, final String version, String customPath, final String resourcePath) {
        final URL resource = this.classLoader.getResource(resourcePath);
        if (resource != null) {
            String libName = this.name + "-" + getBitModel();
            if (version != null) {
                libName = libName + "-" + version;
            }
            if (customPath != null) {
                final File target = this.file(customPath, this.map(libName));
                if (this.extract(errors, resource, target) && this.load(errors, target)) {
                    return true;
                }
            }
            customPath = System.getProperty("java.io.tmpdir");
            final File target = this.file(customPath, this.map(libName));
            if (this.extract(errors, resource, target) && this.load(errors, target)) {
                return true;
            }
        }
        return false;
    }
    
    private File file(final String... paths) {
        File rc = null;
        for (final String path : paths) {
            if (rc == null) {
                rc = new File(path);
            }
            else {
                rc = new File(rc, path);
            }
        }
        return rc;
    }
    
    private String map(String libName) {
        libName = System.mapLibraryName(libName);
        final String ext = ".dylib";
        if (libName.endsWith(ext)) {
            libName = libName.substring(0, libName.length() - ext.length()) + ".jnilib";
        }
        return libName;
    }
    
    private boolean extract(final ArrayList<String> errors, final URL source, final File target) {
        FileOutputStream os = null;
        InputStream is = null;
        boolean extracting = false;
        try {
            if (!target.exists() || this.isStale(source, target)) {
                is = source.openStream();
                if (is != null) {
                    final byte[] buffer = new byte[4096];
                    os = new FileOutputStream(target);
                    extracting = true;
                    int read;
                    while ((read = is.read(buffer)) != -1) {
                        os.write(buffer, 0, read);
                    }
                    os.close();
                    is.close();
                    this.chmod("755", target);
                }
            }
        }
        catch (Throwable e) {
            try {
                if (os != null) {
                    os.close();
                }
            }
            catch (IOException ex) {}
            try {
                if (is != null) {
                    is.close();
                }
            }
            catch (IOException ex2) {}
            if (extracting && target.exists()) {
                target.delete();
            }
            errors.add(e.getMessage());
            return false;
        }
        return true;
    }
    
    private boolean isStale(URL source, final File target) {
        if (source.getProtocol().equals("jar")) {
            try {
                final String[] parts = source.getFile().split(Pattern.quote("!"));
                source = new URL(parts[0]);
            }
            catch (MalformedURLException e) {
                return false;
            }
        }
        File sourceFile = null;
        if (source.getProtocol().equals("file")) {
            sourceFile = new File(source.getFile());
        }
        return sourceFile != null && sourceFile.exists() && sourceFile.lastModified() > target.lastModified();
    }
    
    private void chmod(final String permision, final File path) {
        if (getPlatform().startsWith("windows")) {
            return;
        }
        try {
            Runtime.getRuntime().exec(new String[] { "chmod", permision, path.getCanonicalPath() }).waitFor();
        }
        catch (Throwable t) {}
    }
    
    private boolean load(final ArrayList<String> errors, final File lib) {
        try {
            System.load(lib.getPath());
            return true;
        }
        catch (UnsatisfiedLinkError e) {
            errors.add(e.getMessage());
            return false;
        }
    }
    
    private boolean load(final ArrayList<String> errors, final String lib) {
        try {
            System.loadLibrary(lib);
            return true;
        }
        catch (UnsatisfiedLinkError e) {
            errors.add(e.getMessage());
            return false;
        }
    }
    
    static {
        SLASH = System.getProperty("file.separator");
    }
}
