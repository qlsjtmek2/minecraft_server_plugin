// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockWeb extends Block
{
    public BlockWeb(final int i) {
        super(i, Material.WEB);
        this.a(CreativeModeTab.c);
    }
    
    public void a(final World world, final int n, final int n2, final int n3, final Entity entity) {
        entity.al();
    }
    
    public boolean c() {
        return false;
    }
    
    public AxisAlignedBB b(final World world, final int n, final int n2, final int n3) {
        return null;
    }
    
    public int d() {
        return 1;
    }
    
    public boolean b() {
        return false;
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Item.STRING.id;
    }
    
    protected boolean r_() {
        return true;
    }
}
