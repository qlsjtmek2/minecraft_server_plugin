// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.Material;

public class FurnaceAndDispenser extends DirectionalContainer
{
    public FurnaceAndDispenser(final int type) {
        super(type);
    }
    
    public FurnaceAndDispenser(final Material type) {
        super(type);
    }
    
    public FurnaceAndDispenser(final int type, final byte data) {
        super(type, data);
    }
    
    public FurnaceAndDispenser(final Material type, final byte data) {
        super(type, data);
    }
    
    public FurnaceAndDispenser clone() {
        return (FurnaceAndDispenser)super.clone();
    }
}
