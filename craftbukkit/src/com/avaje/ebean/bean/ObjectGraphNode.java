// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.bean;

import java.io.Serializable;

public final class ObjectGraphNode implements Serializable
{
    private static final long serialVersionUID = 2087081778650228996L;
    private final ObjectGraphOrigin originQueryPoint;
    private final String path;
    
    public ObjectGraphNode(final ObjectGraphNode parent, final String path) {
        this.originQueryPoint = parent.getOriginQueryPoint();
        this.path = parent.getChildPath(path);
    }
    
    public ObjectGraphNode(final ObjectGraphOrigin originQueryPoint, final String path) {
        this.originQueryPoint = originQueryPoint;
        this.path = path;
    }
    
    public ObjectGraphOrigin getOriginQueryPoint() {
        return this.originQueryPoint;
    }
    
    private String getChildPath(final String childPath) {
        if (this.path == null) {
            return childPath;
        }
        if (childPath == null) {
            return this.path;
        }
        return this.path + "." + childPath;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public String toString() {
        return "origin:" + this.originQueryPoint + " " + ":" + this.path + ":" + this.path;
    }
}
