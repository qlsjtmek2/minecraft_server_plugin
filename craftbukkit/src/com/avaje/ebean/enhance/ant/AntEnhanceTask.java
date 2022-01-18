// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.ant;

import org.apache.tools.ant.BuildException;
import com.avaje.ebean.enhance.agent.Transformer;
import java.io.File;
import org.apache.tools.ant.Task;

public class AntEnhanceTask extends Task
{
    String classpath;
    String classSource;
    String classDestination;
    String transformArgs;
    String packages;
    
    public void execute() throws BuildException {
        final File f = new File("");
        System.out.println("Current Directory: " + f.getAbsolutePath());
        final StringBuilder extraClassPath = new StringBuilder();
        extraClassPath.append(this.classSource);
        if (this.classpath != null) {
            if (!extraClassPath.toString().endsWith(";")) {
                extraClassPath.append(";");
            }
            extraClassPath.append(this.classpath);
        }
        final Transformer t = new Transformer(extraClassPath.toString(), this.transformArgs);
        final ClassLoader cl = AntEnhanceTask.class.getClassLoader();
        final OfflineFileTransform ft = new OfflineFileTransform(t, cl, this.classSource, this.classDestination);
        ft.process(this.packages);
    }
    
    public String getClasspath() {
        return this.classpath;
    }
    
    public void setClasspath(final String classpath) {
        this.classpath = classpath;
    }
    
    public void setClassSource(final String source) {
        this.classSource = source;
    }
    
    public void setClassDestination(final String destination) {
        this.classDestination = destination;
    }
    
    public void setTransformArgs(final String transformArgs) {
        this.transformArgs = transformArgs;
    }
    
    public void setPackages(final String packages) {
        this.packages = packages;
    }
}
