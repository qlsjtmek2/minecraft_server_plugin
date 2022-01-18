// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.util;

import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import java.util.Stack;
import org.xml.sax.helpers.DefaultHandler;

public class DnodeParser extends DefaultHandler
{
    Dnode root;
    Dnode currentNode;
    StringBuffer buffer;
    Stack<Dnode> stack;
    Class<?> nodeClass;
    int depth;
    boolean trimWhitespace;
    String contentName;
    int contentDepth;
    
    public DnodeParser() {
        this.stack = new Stack<Dnode>();
        this.nodeClass = Dnode.class;
        this.depth = 0;
        this.trimWhitespace = true;
    }
    
    public boolean isTrimWhitespace() {
        return this.trimWhitespace;
    }
    
    public void setTrimWhitespace(final boolean trimWhitespace) {
        this.trimWhitespace = trimWhitespace;
    }
    
    public Dnode getRoot() {
        return this.root;
    }
    
    public void setNodeClass(final Class<?> nodeClass) {
        this.nodeClass = nodeClass;
    }
    
    private Dnode createNewNode() {
        try {
            return (Dnode)this.nodeClass.newInstance();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        ++this.depth;
        final boolean isContent = this.contentName != null;
        if (isContent) {
            this.buffer.append("<").append(localName);
            for (int i = 0; i < attributes.getLength(); ++i) {
                final String key = attributes.getLocalName(i);
                final String val = attributes.getValue(i);
                this.buffer.append(" ").append(key).append("='").append(val).append("'");
            }
            this.buffer.append(">");
            return;
        }
        this.buffer = new StringBuffer();
        final Dnode node = this.createNewNode();
        node.setNodeName(localName);
        for (int j = 0; j < attributes.getLength(); ++j) {
            final String key2 = attributes.getLocalName(j);
            final String val2 = attributes.getValue(j);
            node.setAttribute(key2, val2);
            if ("type".equalsIgnoreCase(key2) && "content".equalsIgnoreCase(val2)) {
                this.contentName = localName;
                this.contentDepth = this.depth - 1;
            }
        }
        if (this.root == null) {
            this.root = node;
        }
        if (this.currentNode != null) {
            this.currentNode.addChild(node);
        }
        this.stack.push(node);
        this.currentNode = node;
    }
    
    public void characters(final char[] ch, final int start, final int length) throws SAXException {
        super.characters(ch, start, length);
        String s = new String(ch, start, length);
        final int p = s.indexOf(13);
        final int p2 = s.indexOf(10);
        if (p == -1 && p2 > -1) {
            s = StringHelper.replaceString(s, "\n", "\r\n");
        }
        this.buffer.append(s);
    }
    
    public void endElement(final String uri, final String localName, final String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        --this.depth;
        if (this.contentName != null) {
            if (this.contentName.equals(localName) && this.contentDepth == this.depth) {
                this.contentName = null;
            }
            else {
                this.buffer.append("</").append(localName).append(">");
            }
            return;
        }
        String content = this.buffer.toString();
        if (content.length() > 0) {
            if (this.trimWhitespace) {
                content = content.trim();
            }
            if (content.length() > 0) {
                this.currentNode.setNodeContent(content);
            }
        }
        this.stack.pop();
        if (!this.stack.isEmpty()) {
            this.currentNode = this.stack.pop();
            this.stack.push(this.currentNode);
        }
    }
}
