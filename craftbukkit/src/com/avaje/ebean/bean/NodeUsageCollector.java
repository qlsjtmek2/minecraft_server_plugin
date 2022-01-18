// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.bean;

import java.util.HashSet;
import java.lang.ref.WeakReference;

public final class NodeUsageCollector
{
    private final ObjectGraphNode node;
    private final WeakReference<NodeUsageListener> managerRef;
    private final HashSet<String> used;
    private boolean modified;
    private String loadProperty;
    
    public NodeUsageCollector(final ObjectGraphNode node, final WeakReference<NodeUsageListener> managerRef) {
        this.used = new HashSet<String>();
        this.node = node;
        this.managerRef = managerRef;
    }
    
    public void setModified() {
        this.modified = true;
    }
    
    public void addUsed(final String property) {
        this.used.add(property);
    }
    
    public void setLoadProperty(final String loadProperty) {
        this.loadProperty = loadProperty;
    }
    
    private void publishUsageInfo() {
        final NodeUsageListener manager = this.managerRef.get();
        if (manager != null) {
            manager.collectNodeUsage(this);
        }
    }
    
    protected void finalize() throws Throwable {
        this.publishUsageInfo();
        super.finalize();
    }
    
    public ObjectGraphNode getNode() {
        return this.node;
    }
    
    public boolean isEmpty() {
        return this.used.isEmpty();
    }
    
    public HashSet<String> getUsed() {
        return this.used;
    }
    
    public boolean isModified() {
        return this.modified;
    }
    
    public String getLoadProperty() {
        return this.loadProperty;
    }
    
    public String toString() {
        return this.node + " read:" + this.used + " modified:" + this.modified;
    }
}
