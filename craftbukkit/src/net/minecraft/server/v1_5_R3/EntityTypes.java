// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;

public class EntityTypes
{
    private static Map b;
    private static Map c;
    private static Map d;
    private static Map e;
    private static Map f;
    public static HashMap a;
    
    private static void a(final Class clazz, final String s, final int n) {
        EntityTypes.b.put(s, clazz);
        EntityTypes.c.put(clazz, s);
        EntityTypes.d.put(n, clazz);
        EntityTypes.e.put(clazz, n);
        EntityTypes.f.put(s, n);
    }
    
    private static void a(final Class clazz, final String s, final int n, final int n2, final int n3) {
        a(clazz, s, n);
        EntityTypes.a.put(n, new MonsterEggInfo(n, n2, n3));
    }
    
    public static Entity createEntityByName(final String s, final World world) {
        Entity entity = null;
        try {
            final Class<Entity> clazz = EntityTypes.b.get(s);
            if (clazz != null) {
                entity = clazz.getConstructor(World.class).newInstance(world);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return entity;
    }
    
    public static Entity a(final NBTTagCompound nbttagcompound, final World world) {
        Entity entity = null;
        if ("Minecart".equals(nbttagcompound.getString("id"))) {
            switch (nbttagcompound.getInt("Type")) {
                case 1: {
                    nbttagcompound.setString("id", "MinecartChest");
                    break;
                }
                case 2: {
                    nbttagcompound.setString("id", "MinecartFurnace");
                    break;
                }
                case 0: {
                    nbttagcompound.setString("id", "MinecartRideable");
                    break;
                }
            }
            nbttagcompound.remove("Type");
        }
        try {
            final Class<Entity> clazz = EntityTypes.b.get(nbttagcompound.getString("id"));
            if (clazz != null) {
                entity = clazz.getConstructor(World.class).newInstance(world);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        if (entity != null) {
            entity.f(nbttagcompound);
        }
        else {
            world.getLogger().warning("Skipping Entity with id " + nbttagcompound.getString("id"));
        }
        return entity;
    }
    
    public static Entity a(final int n, final World world) {
        Entity entity = null;
        try {
            final Class a = a(n);
            if (a != null) {
                entity = a.getConstructor(World.class).newInstance(world);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        if (entity == null) {
            world.getLogger().warning("Skipping Entity with id " + n);
        }
        return entity;
    }
    
    public static int a(final Entity entity) {
        final Class<? extends Entity> class1 = entity.getClass();
        return (int)(EntityTypes.e.containsKey(class1) ? EntityTypes.e.get(class1) : 0);
    }
    
    public static Class a(final int n) {
        return EntityTypes.d.get(n);
    }
    
    public static String b(final Entity entity) {
        return EntityTypes.c.get(entity.getClass());
    }
    
    public static String b(final int n) {
        final Class a = a(n);
        if (a != null) {
            return (String)EntityTypes.c.get(a);
        }
        return null;
    }
    
    static {
        EntityTypes.b = new HashMap();
        EntityTypes.c = new HashMap();
        EntityTypes.d = new HashMap();
        EntityTypes.e = new HashMap();
        EntityTypes.f = new HashMap();
        EntityTypes.a = new LinkedHashMap();
        a(EntityItem.class, "Item", 1);
        a(EntityExperienceOrb.class, "XPOrb", 2);
        a(EntityPainting.class, "Painting", 9);
        a(EntityArrow.class, "Arrow", 10);
        a(EntitySnowball.class, "Snowball", 11);
        a(EntityLargeFireball.class, "Fireball", 12);
        a(EntitySmallFireball.class, "SmallFireball", 13);
        a(EntityEnderPearl.class, "ThrownEnderpearl", 14);
        a(EntityEnderSignal.class, "EyeOfEnderSignal", 15);
        a(EntityPotion.class, "ThrownPotion", 16);
        a(EntityThrownExpBottle.class, "ThrownExpBottle", 17);
        a(EntityItemFrame.class, "ItemFrame", 18);
        a(EntityWitherSkull.class, "WitherSkull", 19);
        a(EntityTNTPrimed.class, "PrimedTnt", 20);
        a(EntityFallingBlock.class, "FallingSand", 21);
        a(EntityFireworks.class, "FireworksRocketEntity", 22);
        a(EntityBoat.class, "Boat", 41);
        a(EntityMinecartRideable.class, "MinecartRideable", 42);
        a(EntityMinecartChest.class, "MinecartChest", 43);
        a(EntityMinecartFurnace.class, "MinecartFurnace", 44);
        a(EntityMinecartTNT.class, "MinecartTNT", 45);
        a(EntityMinecartHopper.class, "MinecartHopper", 46);
        a(EntityMinecartMobSpawner.class, "MinecartSpawner", 47);
        a(EntityLiving.class, "Mob", 48);
        a(EntityMonster.class, "Monster", 49);
        a(EntityCreeper.class, "Creeper", 50, 894731, 0);
        a(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
        a(EntitySpider.class, "Spider", 52, 3419431, 11013646);
        a(EntityGiantZombie.class, "Giant", 53);
        a(EntityZombie.class, "Zombie", 54, 44975, 7969893);
        a(EntitySlime.class, "Slime", 55, 5349438, 8306542);
        a(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
        a(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
        a(EntityEnderman.class, "Enderman", 58, 1447446, 0);
        a(EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
        a(EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
        a(EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
        a(EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
        a(EntityEnderDragon.class, "EnderDragon", 63);
        a(EntityWither.class, "WitherBoss", 64);
        a(EntityBat.class, "Bat", 65, 4996656, 986895);
        a(EntityWitch.class, "Witch", 66, 3407872, 5349438);
        a(EntityPig.class, "Pig", 90, 15771042, 14377823);
        a(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
        a(EntityCow.class, "Cow", 92, 4470310, 10592673);
        a(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
        a(EntitySquid.class, "Squid", 94, 2243405, 7375001);
        a(EntityWolf.class, "Wolf", 95, 14144467, 13545366);
        a(EntityMushroomCow.class, "MushroomCow", 96, 10489616, 12040119);
        a(EntitySnowman.class, "SnowMan", 97);
        a(EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
        a(EntityIronGolem.class, "VillagerGolem", 99);
        a(EntityVillager.class, "Villager", 120, 5651507, 12422002);
        a(EntityEnderCrystal.class, "EnderCrystal", 200);
    }
}
