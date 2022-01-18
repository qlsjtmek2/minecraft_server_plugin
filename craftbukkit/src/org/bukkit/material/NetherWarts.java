// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.NetherWartsState;
import org.bukkit.Material;

public class NetherWarts extends MaterialData
{
    public NetherWarts() {
        super(Material.NETHER_WARTS);
    }
    
    public NetherWarts(final NetherWartsState state) {
        this();
        this.setState(state);
    }
    
    public NetherWarts(final int type) {
        super(type);
    }
    
    public NetherWarts(final Material type) {
        super(type);
    }
    
    public NetherWarts(final int type, final byte data) {
        super(type, data);
    }
    
    public NetherWarts(final Material type, final byte data) {
        super(type, data);
    }
    
    public NetherWartsState getState() {
        switch (this.getData()) {
            case 0: {
                return NetherWartsState.SEEDED;
            }
            case 1: {
                return NetherWartsState.STAGE_ONE;
            }
            case 2: {
                return NetherWartsState.STAGE_TWO;
            }
            default: {
                return NetherWartsState.RIPE;
            }
        }
    }
    
    public void setState(final NetherWartsState state) {
        switch (state) {
            case SEEDED: {
                this.setData((byte)0);
            }
            case STAGE_ONE: {
                this.setData((byte)1);
            }
            case STAGE_TWO: {
                this.setData((byte)2);
            }
            case RIPE: {
                this.setData((byte)3);
            }
            default: {}
        }
    }
    
    public String toString() {
        return this.getState() + " " + super.toString();
    }
    
    public NetherWarts clone() {
        return (NetherWarts)super.clone();
    }
}
