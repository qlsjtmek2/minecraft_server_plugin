// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.player.PlayerInteractEvent;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.block.Action;

public class ItemBoat extends Item
{
    public ItemBoat(final int i) {
        super(i);
        this.maxStackSize = 1;
        this.a(CreativeModeTab.e);
    }
    
    public ItemStack a(final ItemStack itemstack, final World world, final EntityHuman entityhuman) {
        final float f = 1.0f;
        final float f2 = entityhuman.lastPitch + (entityhuman.pitch - entityhuman.lastPitch) * f;
        final float f3 = entityhuman.lastYaw + (entityhuman.yaw - entityhuman.lastYaw) * f;
        final double d0 = entityhuman.lastX + (entityhuman.locX - entityhuman.lastX) * f;
        final double d2 = entityhuman.lastY + (entityhuman.locY - entityhuman.lastY) * f + 1.62 - entityhuman.height;
        final double d3 = entityhuman.lastZ + (entityhuman.locZ - entityhuman.lastZ) * f;
        final Vec3D vec3d = world.getVec3DPool().create(d0, d2, d3);
        final float f4 = MathHelper.cos(-f3 * 0.017453292f - 3.1415927f);
        final float f5 = MathHelper.sin(-f3 * 0.017453292f - 3.1415927f);
        final float f6 = -MathHelper.cos(-f2 * 0.017453292f);
        final float f7 = MathHelper.sin(-f2 * 0.017453292f);
        final float f8 = f5 * f6;
        final float f9 = f4 * f6;
        final double d4 = 5.0;
        final Vec3D vec3d2 = vec3d.add(f8 * d4, f7 * d4, f9 * d4);
        final MovingObjectPosition movingobjectposition = world.rayTrace(vec3d, vec3d2, true);
        if (movingobjectposition == null) {
            return itemstack;
        }
        final Vec3D vec3d3 = entityhuman.i(f);
        boolean flag = false;
        final float f10 = 1.0f;
        final List list = world.getEntities(entityhuman, entityhuman.boundingBox.a(vec3d3.c * d4, vec3d3.d * d4, vec3d3.e * d4).grow(f10, f10, f10));
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity.K()) {
                final float f11 = entity.X();
                final AxisAlignedBB axisalignedbb = entity.boundingBox.grow(f11, f11, f11);
                if (axisalignedbb.a(vec3d)) {
                    flag = true;
                }
            }
        }
        if (flag) {
            return itemstack;
        }
        if (movingobjectposition.type == EnumMovingObjectType.TILE) {
            final int i = movingobjectposition.b;
            int j = movingobjectposition.c;
            final int k = movingobjectposition.d;
            final PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(entityhuman, Action.RIGHT_CLICK_BLOCK, i, j, k, movingobjectposition.face, itemstack);
            if (event.isCancelled()) {
                return itemstack;
            }
            if (world.getTypeId(i, j, k) == Block.SNOW.id) {
                --j;
            }
            final EntityBoat entityboat = new EntityBoat(world, i + 0.5f, j + 1.0f, k + 0.5f);
            entityboat.yaw = ((MathHelper.floor(entityhuman.yaw * 4.0f / 360.0f + 0.5) & 0x3) - 1) * 90;
            if (!world.getCubes(entityboat, entityboat.boundingBox.grow(-0.1, -0.1, -0.1)).isEmpty()) {
                return itemstack;
            }
            if (!world.isStatic) {
                world.addEntity(entityboat);
            }
            if (!entityhuman.abilities.canInstantlyBuild) {
                --itemstack.count;
            }
        }
        return itemstack;
    }
}
