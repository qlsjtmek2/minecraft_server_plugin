// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type.reflect;

public class CheckImmutableResponse
{
    private boolean immutable;
    private String reasonNotImmutable;
    private boolean compoundType;
    
    public CheckImmutableResponse() {
        this.immutable = true;
    }
    
    public String toString() {
        if (this.immutable) {
            return "immutable";
        }
        return "not immutable due to:" + this.reasonNotImmutable;
    }
    
    public boolean isCompoundType() {
        return this.compoundType;
    }
    
    public void setCompoundType(final boolean compoundType) {
        this.compoundType = compoundType;
    }
    
    public String getReasonNotImmutable() {
        return this.reasonNotImmutable;
    }
    
    public void setReasonNotImmutable(final String error) {
        this.immutable = false;
        this.reasonNotImmutable = error;
    }
    
    public boolean isImmutable() {
        return this.immutable;
    }
}
