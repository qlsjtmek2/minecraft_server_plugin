// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.CreatureSpawnEvent;

public class ItemMonsterEgg extends Item
{
    public ItemMonsterEgg(final int i) {
        super(i);
        this.a(true);
        this.a(CreativeModeTab.f);
    }
    
    public String i(final ItemStack itemstack) {
        String s = ("" + LocaleI18n.get(this.getName() + ".name")).trim();
        final String s2 = EntityTypes.b(itemstack.getData());
        if (s2 != null) {
            s = s + " " + LocaleI18n.get("entity." + s2 + ".name");
        }
        return s;
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, int i, int j, int k, final int l, final float f, final float f1, final float f2) {
        if (world.isStatic || itemstack.getData() == 48 || itemstack.getData() == 49 || itemstack.getData() == 63 || itemstack.getData() == 64) {
            return true;
        }
        final int i2 = world.getTypeId(i, j, k);
        i += Facing.b[l];
        j += Facing.c[l];
        k += Facing.d[l];
        double d0 = 0.0;
        if (l == 1 && Block.byId[i2] != null && Block.byId[i2].d() == 11) {
            d0 = 0.5;
        }
        final Entity entity = a(world, itemstack.getData(), i + 0.5, j + d0, k + 0.5);
        if (entity != null) {
            if (entity instanceof EntityLiving && itemstack.hasName()) {
                ((EntityLiving)entity).setCustomName(itemstack.getName());
            }
            if (!entityhuman.abilities.canInstantlyBuild) {
                --itemstack.count;
            }
        }
        return true;
    }
    
    public static Entity a(final World world, final int i, final double d0, final double d1, final double d2) {
        if (!EntityTypes.a.containsKey(i)) {
            return null;
        }
        Entity entity = null;
        for (int j = 0; j < 1; ++j) {
            entity = EntityTypes.a(i, world);
            if (entity != null && entity instanceof EntityLiving) {
                final EntityLiving entityliving = (EntityLiving)entity;
                entity.setPositionRotation(d0, d1, d2, MathHelper.g(world.random.nextFloat() * 360.0f), 0.0f);
                entityliving.aA = entityliving.yaw;
                entityliving.ay = entityliving.yaw;
                entityliving.bJ();
                world.addEntity(entity, CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
                entityliving.aR();
            }
        }
        return entity;
    }
}
