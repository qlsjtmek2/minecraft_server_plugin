// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.ClassVisitor;

public class VisitMethodParams
{
    private final ClassVisitor cv;
    private int access;
    private final String name;
    private final String desc;
    private final String signiture;
    private final String[] exceptions;
    
    public VisitMethodParams(final ClassVisitor cv, final int access, final String name, final String desc, final String signiture, final String[] exceptions) {
        this.cv = cv;
        this.access = access;
        this.name = name;
        this.desc = desc;
        this.exceptions = exceptions;
        this.signiture = signiture;
    }
    
    public boolean forcePublic() {
        if (this.access != 1) {
            this.access = 1;
            return true;
        }
        return false;
    }
    
    public MethodVisitor visitMethod() {
        return this.cv.visitMethod(this.access, this.name, this.desc, this.signiture, this.exceptions);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDesc() {
        return this.desc;
    }
}
