// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum EntityEffect
{
    HURT(2), 
    DEATH(3), 
    WOLF_SMOKE(6), 
    WOLF_HEARTS(7), 
    WOLF_SHAKE(8), 
    SHEEP_EAT(10);
    
    private final byte data;
    private static final Map<Byte, EntityEffect> BY_DATA;
    
    private EntityEffect(final int data) {
        this.data = (byte)data;
    }
    
    public byte getData() {
        return this.data;
    }
    
    public static EntityEffect getByData(final byte data) {
        return EntityEffect.BY_DATA.get(data);
    }
    
    static {
        BY_DATA = Maps.newHashMap();
        for (final EntityEffect entityEffect : values()) {
            EntityEffect.BY_DATA.put(entityEffect.data, entityEffect);
        }
    }
}
