// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

public class MethodMeta
{
    final int access;
    final String name;
    final String desc;
    final AnnotationInfo annotationInfo;
    
    public MethodMeta(final AnnotationInfo classAnnotationInfo, final int access, final String name, final String desc) {
        this.annotationInfo = new AnnotationInfo(classAnnotationInfo);
        this.access = access;
        this.name = name;
        this.desc = desc;
    }
    
    public String toString() {
        return this.name + " " + this.desc;
    }
    
    public boolean isMatch(final String methodName, final String methodDesc) {
        return this.name.equals(methodName) && this.desc.equals(methodDesc);
    }
    
    public AnnotationInfo getAnnotationInfo() {
        return this.annotationInfo;
    }
}
