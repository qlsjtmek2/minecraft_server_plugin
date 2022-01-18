// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.util;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ArrayList;

public class Dnode
{
    int level;
    String nodeName;
    String nodeContent;
    ArrayList<Dnode> children;
    LinkedHashMap<String, String> attrList;
    
    public Dnode() {
        this.level = 0;
        this.attrList = new LinkedHashMap<String, String>();
    }
    
    public String toXml() {
        final StringBuilder sb = new StringBuilder();
        this.generate(sb);
        return sb.toString();
    }
    
    public StringBuilder generate(StringBuilder sb) {
        if (sb == null) {
            sb = new StringBuilder();
        }
        sb.append("<").append(this.nodeName);
        final Iterator<String> it = this.attributeNames();
        while (it.hasNext()) {
            final String attr = it.next();
            final Object attrValue = this.getAttribute(attr);
            sb.append(" ").append(attr).append("=\"");
            if (attrValue != null) {
                sb.append(attrValue);
            }
            sb.append("\"");
        }
        if (this.nodeContent == null && !this.hasChildren()) {
            sb.append(" />");
        }
        else {
            sb.append(">");
            if (this.children != null && this.children.size() > 0) {
                for (int i = 0; i < this.children.size(); ++i) {
                    final Dnode child = this.children.get(i);
                    child.generate(sb);
                }
            }
            if (this.nodeContent != null) {
                sb.append(this.nodeContent);
            }
            sb.append("</").append(this.nodeName).append(">");
        }
        return sb;
    }
    
    public String getNodeName() {
        return this.nodeName;
    }
    
    public void setNodeName(final String nodeName) {
        this.nodeName = nodeName;
    }
    
    public String getNodeContent() {
        return this.nodeContent;
    }
    
    public void setNodeContent(final String nodeContent) {
        this.nodeContent = nodeContent;
    }
    
    public boolean hasChildren() {
        return this.getChildrenCount() > 0;
    }
    
    public int getChildrenCount() {
        if (this.children == null) {
            return 0;
        }
        return this.children.size();
    }
    
    public boolean remove(final Dnode node) {
        if (this.children == null) {
            return false;
        }
        if (this.children.remove(node)) {
            return true;
        }
        for (final Dnode child : this.children) {
            if (child.remove(node)) {
                return true;
            }
        }
        return false;
    }
    
    public List<Dnode> children() {
        if (this.children == null) {
            return null;
        }
        return this.children;
    }
    
    public void addChild(final Dnode child) {
        if (this.children == null) {
            this.children = new ArrayList<Dnode>();
        }
        this.children.add(child);
        child.setLevel(this.level + 1);
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public void setLevel(final int level) {
        this.level = level;
        if (this.children != null) {
            for (int i = 0; i < this.children.size(); ++i) {
                final Dnode child = this.children.get(i);
                child.setLevel(level + 1);
            }
        }
    }
    
    public Dnode find(final String nodeName) {
        return this.find(nodeName, null, null);
    }
    
    public Dnode find(final String nodeName, final String attrName, final Object value) {
        return this.find(nodeName, attrName, value, -1);
    }
    
    public Dnode find(final String nodeName, final String attrName, final Object value, final int maxLevel) {
        final ArrayList<Dnode> list = new ArrayList<Dnode>();
        this.findByNode(list, nodeName, true, attrName, value, maxLevel);
        if (list.size() >= 1) {
            return list.get(0);
        }
        return null;
    }
    
    public List<Dnode> findAll(final String nodeName, final int maxLevel) {
        int level = -1;
        if (maxLevel > 0) {
            level = this.level + maxLevel;
        }
        return this.findAll(nodeName, null, null, level);
    }
    
    public List<Dnode> findAll(final String nodeName, final String attrName, final Object value, final int maxLevel) {
        if (nodeName == null && attrName == null) {
            throw new RuntimeException("You can not have both nodeName and attrName null");
        }
        final ArrayList<Dnode> list = new ArrayList<Dnode>();
        this.findByNode(list, nodeName, false, attrName, value, maxLevel);
        return list;
    }
    
    private void findByNode(final List<Dnode> list, final String node, final boolean findOne, final String attrName, final Object value, final int maxLevel) {
        if (findOne && list.size() == 1) {
            return;
        }
        if ((node == null || node.equals(this.nodeName)) && (attrName == null || value.equals(this.getAttribute(attrName)))) {
            list.add(this);
            if (findOne) {
                return;
            }
        }
        if (maxLevel <= 0 || this.level < maxLevel) {
            if (this.children != null) {
                for (int i = 0; i < this.children.size(); ++i) {
                    final Dnode child = this.children.get(i);
                    child.findByNode(list, node, findOne, attrName, value, maxLevel);
                }
            }
        }
    }
    
    public Iterator<String> attributeNames() {
        return this.attrList.keySet().iterator();
    }
    
    public String getAttribute(final String name) {
        return this.attrList.get(name);
    }
    
    public String getStringAttr(final String name, final String defaultValue) {
        final Object o = this.attrList.get(name);
        if (o == null) {
            return defaultValue;
        }
        return o.toString();
    }
    
    public void setAttribute(final String name, final String value) {
        this.attrList.put(name, value);
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[").append(this.getNodeName()).append(" ").append(this.attrList).append("]");
        return sb.toString();
    }
}
