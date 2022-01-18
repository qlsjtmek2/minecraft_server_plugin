// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum Instrument
{
    PIANO(0), 
    BASS_DRUM(1), 
    SNARE_DRUM(2), 
    STICKS(3), 
    BASS_GUITAR(4);
    
    private final byte type;
    private static final Map<Byte, Instrument> BY_DATA;
    
    private Instrument(final int type) {
        this.type = (byte)type;
    }
    
    public byte getType() {
        return this.type;
    }
    
    public static Instrument getByType(final byte type) {
        return Instrument.BY_DATA.get(type);
    }
    
    static {
        BY_DATA = Maps.newHashMap();
        for (final Instrument instrument : values()) {
            Instrument.BY_DATA.put(instrument.getType(), instrument);
        }
    }
}
