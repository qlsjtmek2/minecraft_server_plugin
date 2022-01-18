// 
// Decompiled by Procyon v0.5.30
// 

package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public final class AliasEvent extends NodeEvent
{
    public AliasEvent(final String anchor, final Mark startMark, final Mark endMark) {
        super(anchor, startMark, endMark);
    }
    
    public boolean is(final ID id) {
        return ID.Alias == id;
    }
}
