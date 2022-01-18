// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

public class ValuePair
{
    final Object value1;
    final Object value2;
    
    public ValuePair(final Object value1, final Object value2) {
        this.value1 = value1;
        this.value2 = value2;
    }
    
    public Object getValue1() {
        return this.value1;
    }
    
    public Object getValue2() {
        return this.value2;
    }
    
    public String toString() {
        return this.value1 + "," + this.value2;
    }
}
