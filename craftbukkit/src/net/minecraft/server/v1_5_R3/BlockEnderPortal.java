// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.Location;
import java.util.Random;
import java.util.List;

public class BlockEnderPortal extends BlockContainer
{
    public static boolean a;
    
    protected BlockEnderPortal(final int i, final Material material) {
        super(i, material);
        this.a(1.0f);
    }
    
    public TileEntity b(final World world) {
        return new TileEntityEnderPortal();
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        final float f = 0.0625f;
        this.a(0.0f, 0.0f, 0.0f, 1.0f, f, 1.0f);
    }
    
    public void a(final World world, final int i, final int j, final int k, final AxisAlignedBB axisalignedbb, final List list, final Entity entity) {
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public int a(final Random random) {
        return 0;
    }
    
    public void a(final World world, final int i, final int j, final int k, final Entity entity) {
        if (entity.vehicle == null && entity.passenger == null && !world.isStatic) {
            final EntityPortalEnterEvent event = new EntityPortalEnterEvent(entity.getBukkitEntity(), new Location(world.getWorld(), i, j, k));
            world.getServer().getPluginManager().callEvent(event);
            entity.c(1);
        }
    }
    
    public int d() {
        return -1;
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        if (!BlockEnderPortal.a && world.worldProvider.dimension != 0) {
            world.setAir(i, j, k);
        }
    }
    
    static {
        BlockEnderPortal.a = false;
    }
}
