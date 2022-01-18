// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.Location;
import java.util.Collection;
import org.bukkit.event.Event;
import org.bukkit.event.world.PortalCreateEvent;
import java.util.HashSet;
import java.util.Random;

public class BlockPortal extends BlockHalfTransparant
{
    public BlockPortal(final int i) {
        super(i, "portal", Material.PORTAL, false);
        this.b(true);
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        super.a(world, i, j, k, random);
        if (world.worldProvider.d() && random.nextInt(2000) < world.difficulty) {
            int l;
            for (l = j; !world.w(i, l, k) && l > 0; --l) {}
            if (l > 0 && !world.u(i, l + 1, k)) {
                final Entity entity = ItemMonsterEgg.a(world, 57, i + 0.5, l + 1.1, k + 0.5);
                if (entity != null) {
                    entity.portalCooldown = entity.aa();
                }
            }
        }
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        return null;
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        if (iblockaccess.getTypeId(i - 1, j, k) != this.id && iblockaccess.getTypeId(i + 1, j, k) != this.id) {
            final float f = 0.125f;
            final float f2 = 0.5f;
            this.a(0.5f - f, 0.0f, 0.5f - f2, 0.5f + f, 1.0f, 0.5f + f2);
        }
        else {
            final float f = 0.5f;
            final float f2 = 0.125f;
            this.a(0.5f - f, 0.0f, 0.5f - f2, 0.5f + f, 1.0f, 0.5f + f2);
        }
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean n_(final World world, int i, final int j, int k) {
        byte b0 = 0;
        byte b2 = 0;
        if (world.getTypeId(i - 1, j, k) == Block.OBSIDIAN.id || world.getTypeId(i + 1, j, k) == Block.OBSIDIAN.id) {
            b0 = 1;
        }
        if (world.getTypeId(i, j, k - 1) == Block.OBSIDIAN.id || world.getTypeId(i, j, k + 1) == Block.OBSIDIAN.id) {
            b2 = 1;
        }
        if (b0 == b2) {
            return false;
        }
        final Collection<org.bukkit.block.Block> blocks = new HashSet<org.bukkit.block.Block>();
        final org.bukkit.World bworld = world.getWorld();
        if (world.getTypeId(i - b0, j, k - b2) == 0) {
            i -= b0;
            k -= b2;
        }
        for (int l = -1; l <= 2; ++l) {
            for (int i2 = -1; i2 <= 3; ++i2) {
                final boolean flag = l == -1 || l == 2 || i2 == -1 || i2 == 3;
                if ((l != -1 && l != 2) || (i2 != -1 && i2 != 3)) {
                    final int j2 = world.getTypeId(i + b0 * l, j + i2, k + b2 * l);
                    if (flag) {
                        if (j2 != Block.OBSIDIAN.id) {
                            return false;
                        }
                        blocks.add(bworld.getBlockAt(i + b0 * l, j + i2, k + b2 * l));
                    }
                    else if (j2 != 0 && j2 != Block.FIRE.id) {
                        return false;
                    }
                }
            }
        }
        for (int l = 0; l < 2; ++l) {
            for (int i2 = 0; i2 < 3; ++i2) {
                blocks.add(bworld.getBlockAt(i + b0 * l, j + i2, k + b2 * l));
            }
        }
        final PortalCreateEvent event = new PortalCreateEvent(blocks, bworld, PortalCreateEvent.CreateReason.FIRE);
        world.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }
        for (int l = 0; l < 2; ++l) {
            for (int i2 = 0; i2 < 3; ++i2) {
                world.setTypeIdAndData(i + b0 * l, j + i2, k + b2 * l, Block.PORTAL.id, 0, 2);
            }
        }
        return true;
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        byte b0 = 0;
        byte b2 = 1;
        if (world.getTypeId(i - 1, j, k) == this.id || world.getTypeId(i + 1, j, k) == this.id) {
            b0 = 1;
            b2 = 0;
        }
        int i2;
        for (i2 = j; world.getTypeId(i, i2 - 1, k) == this.id; --i2) {}
        if (world.getTypeId(i, i2 - 1, k) != Block.OBSIDIAN.id) {
            world.setAir(i, j, k);
        }
        else {
            int j2;
            for (j2 = 1; j2 < 4 && world.getTypeId(i, i2 + j2, k) == this.id; ++j2) {}
            if (j2 == 3 && world.getTypeId(i, i2 + j2, k) == Block.OBSIDIAN.id) {
                final boolean flag = world.getTypeId(i - 1, j, k) == this.id || world.getTypeId(i + 1, j, k) == this.id;
                final boolean flag2 = world.getTypeId(i, j, k - 1) == this.id || world.getTypeId(i, j, k + 1) == this.id;
                if (flag && flag2) {
                    world.setAir(i, j, k);
                }
                else if ((world.getTypeId(i + b0, j, k + b2) != Block.OBSIDIAN.id || world.getTypeId(i - b0, j, k - b2) != this.id) && (world.getTypeId(i - b0, j, k - b2) != Block.OBSIDIAN.id || world.getTypeId(i + b0, j, k + b2) != this.id)) {
                    world.setAir(i, j, k);
                }
            }
            else {
                world.setAir(i, j, k);
            }
        }
    }
    
    public int a(final Random random) {
        return 0;
    }
    
    public void a(final World world, final int i, final int j, final int k, final Entity entity) {
        if (entity.vehicle == null && entity.passenger == null) {
            final EntityPortalEnterEvent event = new EntityPortalEnterEvent(entity.getBukkitEntity(), new Location(world.getWorld(), i, j, k));
            world.getServer().getPluginManager().callEvent(event);
            entity.Z();
        }
    }
}
