// 
// Decompiled by Procyon v0.5.30
// 

package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.Mark;

public final class ValueToken extends Token
{
    public ValueToken(final Mark startMark, final Mark endMark) {
        super(startMark, endMark);
    }
    
    public ID getTokenId() {
        return ID.Value;
    }
}
