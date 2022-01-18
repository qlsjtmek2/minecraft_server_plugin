// 
// Decompiled by Procyon v0.5.30
// 

package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public abstract class Event
{
    private final Mark startMark;
    private final Mark endMark;
    
    public Event(final Mark startMark, final Mark endMark) {
        this.startMark = startMark;
        this.endMark = endMark;
    }
    
    public String toString() {
        return "<" + this.getClass().getName() + "(" + this.getArguments() + ")>";
    }
    
    public Mark getStartMark() {
        return this.startMark;
    }
    
    public Mark getEndMark() {
        return this.endMark;
    }
    
    protected String getArguments() {
        return "";
    }
    
    public abstract boolean is(final ID p0);
    
    public boolean equals(final Object obj) {
        return obj instanceof Event && this.toString().equals(obj.toString());
    }
    
    public enum ID
    {
        Alias, 
        DocumentEnd, 
        DocumentStart, 
        MappingEnd, 
        MappingStart, 
        Scalar, 
        SequenceEnd, 
        SequenceStart, 
        StreamEnd, 
        StreamStart;
    }
}
