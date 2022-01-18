// 
// Decompiled by Procyon v0.5.30
// 

package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public final class StreamEndEvent extends Event
{
    public StreamEndEvent(final Mark startMark, final Mark endMark) {
        super(startMark, endMark);
    }
    
    public boolean is(final ID id) {
        return ID.StreamEnd == id;
    }
}
