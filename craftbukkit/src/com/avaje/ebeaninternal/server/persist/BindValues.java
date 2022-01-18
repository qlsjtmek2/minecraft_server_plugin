// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import java.util.ArrayList;

public class BindValues
{
    int commentCount;
    final ArrayList<Value> list;
    
    public BindValues() {
        this.list = new ArrayList<Value>();
    }
    
    public int size() {
        return this.list.size() - this.commentCount;
    }
    
    public void add(final Object value, final int dbType, final String name) {
        this.list.add(new Value(value, dbType, name));
    }
    
    public void addComment(final String comment) {
        ++this.commentCount;
        this.list.add(new Value(comment));
    }
    
    public ArrayList<Value> values() {
        return this.list;
    }
    
    public static class Value
    {
        private final Object value;
        private final int dbType;
        private final String name;
        private final boolean isComment;
        
        public Value(final String comment) {
            this.name = comment;
            this.isComment = true;
            this.value = null;
            this.dbType = 0;
        }
        
        public Value(final Object value, final int dbType, final String name) {
            this.isComment = false;
            this.value = value;
            this.dbType = dbType;
            this.name = name;
        }
        
        public boolean isComment() {
            return this.isComment;
        }
        
        public int getDbType() {
            return this.dbType;
        }
        
        public Object getValue() {
            return this.value;
        }
        
        public String getName() {
            return this.name;
        }
        
        public String toString() {
            return "" + this.value;
        }
    }
}
