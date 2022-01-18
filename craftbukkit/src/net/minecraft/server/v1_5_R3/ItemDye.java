// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.DyeColor;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Player;

public class ItemDye extends Item
{
    public static final String[] a;
    public static final String[] b;
    public static final int[] c;
    
    public ItemDye(final int i) {
        super(i);
        this.a(true);
        this.setMaxDurability(0);
        this.a(CreativeModeTab.l);
    }
    
    public String d(final ItemStack itemstack) {
        final int i = MathHelper.a(itemstack.getData(), 0, 15);
        return super.getName() + "." + ItemDye.a[i];
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, int i, final int j, int k, final int l, final float f, final float f1, final float f2) {
        if (!entityhuman.a(i, j, k, l, itemstack)) {
            return false;
        }
        if (itemstack.getData() == 15) {
            if (a(itemstack, world, i, j, k, entityhuman)) {
                if (!world.isStatic) {
                    world.triggerEffect(2005, i, j, k, 0);
                }
                return true;
            }
        }
        else if (itemstack.getData() == 3) {
            final int i2 = world.getTypeId(i, j, k);
            final int j2 = world.getData(i, j, k);
            if (i2 == Block.LOG.id && BlockLog.d(j2) == 3) {
                if (l == 0) {
                    return false;
                }
                if (l == 1) {
                    return false;
                }
                if (l == 2) {
                    --k;
                }
                if (l == 3) {
                    ++k;
                }
                if (l == 4) {
                    --i;
                }
                if (l == 5) {
                    ++i;
                }
                if (world.isEmpty(i, j, k)) {
                    final int k2 = Block.byId[Block.COCOA.id].getPlacedData(world, i, j, k, l, f, f1, f2, 0);
                    world.setTypeIdAndData(i, j, k, Block.COCOA.id, k2, 2);
                    if (!entityhuman.abilities.canInstantlyBuild) {
                        --itemstack.count;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    public static boolean a(final ItemStack itemstack, final World world, final int i, final int j, final int k) {
        return a(itemstack, world, i, j, k, null);
    }
    
    public static boolean a(final ItemStack itemstack, final World world, final int i, final int j, final int k, final EntityHuman entityhuman) {
        final int l = world.getTypeId(i, j, k);
        if (l == Block.SAPLING.id) {
            if (!world.isStatic) {
                if (world.random.nextFloat() < 0.45) {
                    final Player player = (entityhuman instanceof EntityPlayer) ? ((Player)entityhuman.getBukkitEntity()) : null;
                    ((BlockSapling)Block.SAPLING).grow(world, i, j, k, world.random, true, player, null);
                }
                --itemstack.count;
            }
            return true;
        }
        if (l == Block.BROWN_MUSHROOM.id || l == Block.RED_MUSHROOM.id) {
            if (!world.isStatic && world.random.nextFloat() < 0.4) {
                final Player player = (entityhuman instanceof EntityPlayer) ? ((Player)entityhuman.getBukkitEntity()) : null;
                ((BlockMushroom)Block.byId[l]).grow(world, i, j, k, world.random, true, player, itemstack);
            }
            return true;
        }
        if (l != Block.MELON_STEM.id && l != Block.PUMPKIN_STEM.id) {
            if (l > 0 && Block.byId[l] instanceof BlockCrops) {
                if (world.getData(i, j, k) == 7) {
                    return false;
                }
                if (!world.isStatic) {
                    ((BlockCrops)Block.byId[l]).e_(world, i, j, k);
                    --itemstack.count;
                }
                return true;
            }
            else if (l == Block.COCOA.id) {
                final int i2 = world.getData(i, j, k);
                final int j2 = BlockDirectional.j(i2);
                int k2 = BlockCocoa.c(i2);
                if (k2 >= 2) {
                    return false;
                }
                if (!world.isStatic) {
                    ++k2;
                    world.setData(i, j, k, k2 << 2 | j2, 2);
                    --itemstack.count;
                }
                return true;
            }
            else {
                if (l != Block.GRASS.id) {
                    return false;
                }
                if (!world.isStatic) {
                    --itemstack.count;
                    int i2 = 0;
                Label_0595_Outer:
                    while (i2 < 128) {
                        int j2 = i;
                        int k2 = j + 1;
                        int l2 = k;
                        int i3 = 0;
                        while (true) {
                            while (i3 < i2 / 16) {
                                j2 += ItemDye.e.nextInt(3) - 1;
                                k2 += (ItemDye.e.nextInt(3) - 1) * ItemDye.e.nextInt(3) / 2;
                                l2 += ItemDye.e.nextInt(3) - 1;
                                if (world.getTypeId(j2, k2 - 1, l2) == Block.GRASS.id) {
                                    if (!world.u(j2, k2, l2)) {
                                        ++i3;
                                        continue Label_0595_Outer;
                                    }
                                }
                                ++i2;
                                continue Label_0595_Outer;
                            }
                            if (world.getTypeId(j2, k2, l2) != 0) {
                                continue;
                            }
                            if (ItemDye.e.nextInt(10) != 0) {
                                if (Block.LONG_GRASS.f(world, j2, k2, l2)) {
                                    world.setTypeIdAndData(j2, k2, l2, Block.LONG_GRASS.id, 1, 3);
                                }
                                continue;
                            }
                            else if (ItemDye.e.nextInt(3) != 0) {
                                if (Block.YELLOW_FLOWER.f(world, j2, k2, l2)) {
                                    world.setTypeIdUpdate(j2, k2, l2, Block.YELLOW_FLOWER.id);
                                }
                                continue;
                            }
                            else {
                                if (Block.RED_ROSE.f(world, j2, k2, l2)) {
                                    world.setTypeIdUpdate(j2, k2, l2, Block.RED_ROSE.id);
                                }
                                continue;
                            }
                            break;
                        }
                    }
                }
                return true;
            }
        }
        else {
            if (world.getData(i, j, k) == 7) {
                return false;
            }
            if (!world.isStatic) {
                ((BlockStem)Block.byId[l]).k(world, i, j, k);
                --itemstack.count;
            }
            return true;
        }
    }
    
    public boolean a(final ItemStack itemstack, final EntityLiving entityliving) {
        if (entityliving instanceof EntitySheep) {
            final EntitySheep entitysheep = (EntitySheep)entityliving;
            int i = BlockCloth.g_(itemstack.getData());
            if (!entitysheep.isSheared() && entitysheep.getColor() != i) {
                final byte bColor = (byte)i;
                final SheepDyeWoolEvent event = new SheepDyeWoolEvent((Sheep)entitysheep.getBukkitEntity(), DyeColor.getByData(bColor));
                entitysheep.world.getServer().getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    return false;
                }
                i = event.getColor().getWoolData();
                entitysheep.setColor(i);
                --itemstack.count;
            }
            return true;
        }
        return false;
    }
    
    static {
        a = new String[] { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white" };
        b = new String[] { "dyePowder_black", "dyePowder_red", "dyePowder_green", "dyePowder_brown", "dyePowder_blue", "dyePowder_purple", "dyePowder_cyan", "dyePowder_silver", "dyePowder_gray", "dyePowder_pink", "dyePowder_lime", "dyePowder_yellow", "dyePowder_lightBlue", "dyePowder_magenta", "dyePowder_orange", "dyePowder_white" };
        c = new int[] { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
    }
}
