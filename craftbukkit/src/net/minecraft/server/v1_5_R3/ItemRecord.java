// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.HashMap;
import java.util.Map;

public class ItemRecord extends Item
{
    private static final Map b;
    public final String a;
    
    protected ItemRecord(final int n, final String a) {
        super(n);
        this.a = a;
        this.maxStackSize = 1;
        this.a(CreativeModeTab.f);
        ItemRecord.b.put(a, this);
    }
    
    public boolean interactWith(final ItemStack itemStack, final EntityHuman entityHuman, final World world, final int j, final int k, final int l, final int n, final float n2, final float n3, final float n4) {
        if (world.getTypeId(j, k, l) != Block.JUKEBOX.id || world.getData(j, k, l) != 0) {
            return false;
        }
        if (world.isStatic) {
            return true;
        }
        ((BlockJukeBox)Block.JUKEBOX).a(world, j, k, l, itemStack);
        world.a(null, 1005, j, k, l, this.id);
        --itemStack.count;
        return true;
    }
    
    static {
        b = new HashMap();
    }
}
