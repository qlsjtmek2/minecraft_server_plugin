// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.ant;

import java.lang.instrument.IllegalClassFormatException;
import java.io.IOException;
import java.io.File;
import com.avaje.ebean.enhance.agent.Transformer;
import com.avaje.ebean.enhance.agent.InputStreamTransform;

public class OfflineFileTransform
{
    final InputStreamTransform inputStreamTransform;
    final String inDir;
    final String outDir;
    private TransformationListener listener;
    
    public OfflineFileTransform(final Transformer transformer, final ClassLoader classLoader, String inDir, final String outDir) {
        this.inputStreamTransform = new InputStreamTransform(transformer, classLoader);
        inDir = this.trimSlash(inDir);
        this.inDir = inDir;
        this.outDir = ((outDir == null) ? inDir : outDir);
    }
    
    public void setListener(final TransformationListener v) {
        this.listener = v;
    }
    
    private String trimSlash(final String dir) {
        if (dir.endsWith("/")) {
            return dir.substring(0, dir.length() - 1);
        }
        return dir;
    }
    
    public void process(final String packageNames) {
        if (packageNames == null) {
            this.processPackage("", true);
            return;
        }
        final String[] pkgs = packageNames.split(",");
        for (int i = 0; i < pkgs.length; ++i) {
            String pkg = pkgs[i].trim().replace('.', '/');
            boolean recurse = false;
            if (pkg.endsWith("**")) {
                recurse = true;
                pkg = pkg.substring(0, pkg.length() - 2);
            }
            else if (pkg.endsWith("*")) {
                recurse = true;
                pkg = pkg.substring(0, pkg.length() - 1);
            }
            pkg = this.trimSlash(pkg);
            this.processPackage(pkg, recurse);
        }
    }
    
    private void processPackage(final String dir, final boolean recurse) {
        this.inputStreamTransform.log(1, "transform> pkg: " + dir);
        final String dirPath = this.inDir + "/" + dir;
        final File d = new File(dirPath);
        if (!d.exists()) {
            final String m = "File not found " + dirPath;
            throw new RuntimeException(m);
        }
        final File[] files = d.listFiles();
        File file = null;
        try {
            for (int i = 0; i < files.length; ++i) {
                file = files[i];
                if (file.isDirectory()) {
                    if (recurse) {
                        final String subdir = dir + "/" + file.getName();
                        this.processPackage(subdir, recurse);
                    }
                }
                else {
                    final String fileName = file.getName();
                    if (fileName.endsWith(".java")) {
                        System.err.println("Expecting a .class file but got " + fileName + " ... ignoring");
                    }
                    else if (fileName.endsWith(".class")) {
                        this.transformFile(file);
                    }
                }
            }
        }
        catch (Exception e) {
            final String fileName = (file == null) ? "null" : file.getName();
            final String j = "Error transforming file " + fileName;
            throw new RuntimeException(j, e);
        }
    }
    
    private void transformFile(final File file) throws IOException, IllegalClassFormatException {
        final String className = this.getClassName(file);
        final byte[] result = this.inputStreamTransform.transform(className, file);
        if (result != null) {
            InputStreamTransform.writeBytes(result, file);
            if (this.listener != null) {
                this.listener.logEvent("Enhanced " + file);
            }
        }
        else if (this.listener != null) {
            this.listener.logError("Unable to enhance " + file);
        }
    }
    
    private String getClassName(final File file) {
        String path = file.getPath();
        path = path.substring(this.inDir.length() + 1);
        path = path.substring(0, path.length() - ".class".length());
        return StringReplace.replace(path, "\\", "/");
    }
}
