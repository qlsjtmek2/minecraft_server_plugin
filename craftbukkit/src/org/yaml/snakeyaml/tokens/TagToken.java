// 
// Decompiled by Procyon v0.5.30
// 

package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.Mark;

public final class TagToken extends Token
{
    private final TagTuple value;
    
    public TagToken(final TagTuple value, final Mark startMark, final Mark endMark) {
        super(startMark, endMark);
        this.value = value;
    }
    
    public TagTuple getValue() {
        return this.value;
    }
    
    protected String getArguments() {
        return "value=[" + this.value.getHandle() + ", " + this.value.getSuffix() + "]";
    }
    
    public ID getTokenId() {
        return ID.Tag;
    }
}
