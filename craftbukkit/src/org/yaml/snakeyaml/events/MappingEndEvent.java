// 
// Decompiled by Procyon v0.5.30
// 

package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public final class MappingEndEvent extends CollectionEndEvent
{
    public MappingEndEvent(final Mark startMark, final Mark endMark) {
        super(startMark, endMark);
    }
    
    public boolean is(final ID id) {
        return ID.MappingEnd == id;
    }
}
