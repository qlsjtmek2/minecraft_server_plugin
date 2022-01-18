// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum CropState
{
    SEEDED(0), 
    GERMINATED(1), 
    VERY_SMALL(2), 
    SMALL(3), 
    MEDIUM(4), 
    TALL(5), 
    VERY_TALL(6), 
    RIPE(7);
    
    private final byte data;
    private static final Map<Byte, CropState> BY_DATA;
    
    private CropState(final int data) {
        this.data = (byte)data;
    }
    
    public byte getData() {
        return this.data;
    }
    
    public static CropState getByData(final byte data) {
        return CropState.BY_DATA.get(data);
    }
    
    static {
        BY_DATA = Maps.newHashMap();
        for (final CropState cropState : values()) {
            CropState.BY_DATA.put(cropState.getData(), cropState);
        }
    }
}
