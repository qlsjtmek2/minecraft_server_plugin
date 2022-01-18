package me.espoo.part;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@SuppressWarnings({ "rawtypes", "unchecked" })
public enum Particle{
	HUGE_EXPLOSION("hugeexplosion", 0), LARGE_EXPLODE("largeexplode", 1), FIREWORKS_SPARK("fireworksSpark", 2), BUBBLE("bubble", 3), SUSPEND("suspend", 4), DEPTH_SUSPEND("depthSuspend", 5), TOWN_AURA("townaura", 6), CRIT("crit", 7), MAGIC_CRIT("magicCrit", 8), MOB_SPELL("mobSpell", 9), MOB_SPELL_AMBIENT("mobSpellAmbient", 10), SPELL("spell", 11), INSTANT_SPELL("instantSpell", 12), WITCH_MAGIC("witchMagic", 13), NOTE("note", 14), PORTAL("portal", 15), ENCHANTMENT_TABLE("enchantmenttable", 16), EXPLODE("explode", 17), FLAME("flame", 18), LAVA("lava", 19), FOOTSTEP("footstep", 20), SPLASH("splash", 21), LARGE_SMOKE("largesmoke", 22), CLOUD("cloud", 23), RED_DUST("reddust", 24), SNOWBALL_POOF("snowballpoof", 25), DRIP_WATER("dripWater", 26), DRIP_LAVA("dripLava", 27), SNOW_SHOVEL("snowshovel", 28), SLIME("slime", 29), HEART("heart", 30), ANGRY_VILLAGER("angryVillager", 31), HAPPY_VILLAGER("happyVillager", 32), ICONCRACK("iconcrack", 33), TILECRACK("tilecrack", 34);
	
	public static String bukkitversion;
	private String name;
	private int id;
	private static final Map<String, Particle> NAME_MAP;
	private static final Map<Integer, Particle> ID_MAP;
	
	private Particle(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName(){
		return this.name;
	}

	public int getId() {
		return this.id;
	}

	public static Particle fromName(String name){
		if (name == null) {
			return null;
		}
		for (Map.Entry e : NAME_MAP.entrySet()) {
			if (((String)e.getKey()).equalsIgnoreCase(name)) {
				return (Particle)e.getValue();
			}
		}
		return null;
	}
	
	public static Particle fromId(int id) {
	    return (Particle)ID_MAP.get(Integer.valueOf(id));
	}

	public static void sendToPlayer(Particle effect, Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count, String version) throws Exception {
		bukkitversion = version;
		Object packet = createPacket(effect, location, offsetX, offsetY, offsetZ, speed, count);
		sendPacket(player, packet);
	}

	public static void sendToLocation(Particle effect, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count, String version) throws Exception {
		bukkitversion = version;
		Object packet = createPacket(effect, location, offsetX, offsetY, offsetZ, speed, count);
		for (Player player : Bukkit.getOnlinePlayers())
			sendPacket(player, packet);
	}
	
	public static void sendCrackToPlayer(boolean icon, int id, byte data, Player player, Location location, float offsetX, float offsetY, float offsetZ, int count) throws Exception{
		Object packet = createCrackPacket(icon, id, data, location, offsetX, offsetY, offsetZ, count);
		sendPacket(player, packet);
	}

	public static void sendCrackToLocation(boolean icon, int id, byte data, Location location, float offsetX, float offsetY, float offsetZ, int count) throws Exception {
		Object packet = createCrackPacket(icon, id, data, location, offsetX, offsetY, offsetZ, count);
		for (Player player : Bukkit.getOnlinePlayers())
			sendPacket(player, packet);
	}

	public static Object createPacket(Particle effect, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) throws Exception{
		if (count <= 0)
			count = 1;
		Object packet = getPacket63WorldParticles();
		setValue(packet, "a", effect.name);
		setValue(packet, "b", Float.valueOf((float)location.getX()));
		setValue(packet, "c", Float.valueOf((float)location.getY()));
		setValue(packet, "d", Float.valueOf((float)location.getZ()));
		setValue(packet, "e", Float.valueOf(offsetX));
		setValue(packet, "f", Float.valueOf(offsetY));
		setValue(packet, "g", Float.valueOf(offsetZ));
		setValue(packet, "h", Float.valueOf(speed));
		setValue(packet, "i", Integer.valueOf(count));
		return packet;
	}
	
	public static Object createCrackPacket(boolean icon, int id, byte data, Location location, float offsetX, float offsetY, float offsetZ, int count) throws Exception {
		if (count <= 0)
			count = 1;
		Object packet = getPacket63WorldParticles();
		String modifier = "iconcrack_" + id;
		if (!icon) {
			modifier = "tilecrack_" + id + "_" + data;
		}
		setValue(packet, "a", modifier);
		setValue(packet, "b", Float.valueOf((float)location.getX()));
		setValue(packet, "c", Float.valueOf((float)location.getY()));
		setValue(packet, "d", Float.valueOf((float)location.getZ()));
		setValue(packet, "e", Float.valueOf(offsetX));
		setValue(packet, "f", Float.valueOf(offsetY));
		setValue(packet, "g", Float.valueOf(offsetZ));
		setValue(packet, "h", Float.valueOf(0.1F));
		setValue(packet, "i", Integer.valueOf(count));
		return packet;
	}

	private static void setValue(Object instance, String fieldName, Object value) throws Exception {
		Field field = instance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(instance, value);
	}

	private static Object getEntityPlayer(Player p) throws Exception {
		Method getHandle = p.getClass().getMethod("getHandle", new Class[0]);
		return getHandle.invoke(p, new Object[0]);
	}

	private static String getPackageName() {
		return "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	}

	private static Object getPacket63WorldParticles() throws Exception {
		Class packet = null;
	    if(bukkitversion.contains("1.7")){
	    	packet = Class.forName(getPackageName() + ".PacketPlayOutWorldParticles");
	    }else{
	    	packet = Class.forName(getPackageName() + ".Packet63WorldParticles");
	    }
	    return packet.getConstructors()[0].newInstance(new Object[0]);
	}

	private static void sendPacket(Player p, Object packet) throws Exception {
		Object eplayer = getEntityPlayer(p);
		Field playerConnectionField = eplayer.getClass().getField("playerConnection");
		Object playerConnection = playerConnectionField.get(eplayer);
		for (Method m : playerConnection.getClass().getMethods())
			if (m.getName().equalsIgnoreCase("sendPacket")) {
				m.invoke(playerConnection, new Object[] { packet });
				return;
			}
	}

	static{
		NAME_MAP = new HashMap();
		ID_MAP = new HashMap();
		
		for (Particle effect : values()) {
			NAME_MAP.put(effect.name, effect);
			ID_MAP.put(Integer.valueOf(effect.id), effect);
		}
	}
}
