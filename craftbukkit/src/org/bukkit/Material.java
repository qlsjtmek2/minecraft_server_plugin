// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import org.bukkit.util.Java15Compat;
import com.google.common.collect.Maps;
import org.bukkit.material.SpawnEgg;
import org.bukkit.material.Dye;
import org.bukkit.material.Coal;
import org.bukkit.material.Skull;
import org.bukkit.material.FlowerPot;
import org.bukkit.material.Command;
import org.bukkit.material.Tripwire;
import org.bukkit.material.TripwireHook;
import org.bukkit.material.EnderChest;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.WoodenStep;
import org.bukkit.material.Cauldron;
import org.bukkit.material.NetherWarts;
import org.bukkit.material.Gate;
import org.bukkit.material.Vine;
import org.bukkit.material.Mushroom;
import org.bukkit.material.SmoothBrick;
import org.bukkit.material.MonsterEggs;
import org.bukkit.material.TrapDoor;
import org.bukkit.material.Diode;
import org.bukkit.material.Cake;
import org.bukkit.material.Pumpkin;
import org.bukkit.material.Button;
import org.bukkit.material.RedstoneTorch;
import org.bukkit.material.PressurePlate;
import org.bukkit.material.Lever;
import org.bukkit.material.Rails;
import org.bukkit.material.Ladder;
import org.bukkit.material.Door;
import org.bukkit.material.Sign;
import org.bukkit.material.Furnace;
import org.bukkit.material.Crops;
import org.bukkit.material.RedstoneWire;
import org.bukkit.material.Chest;
import org.bukkit.material.Stairs;
import org.bukkit.material.Torch;
import org.bukkit.material.Step;
import org.bukkit.material.Wool;
import org.bukkit.material.PistonExtensionMaterial;
import org.bukkit.material.LongGrass;
import org.bukkit.material.PistonBaseMaterial;
import org.bukkit.material.DetectorRail;
import org.bukkit.material.PoweredRail;
import org.bukkit.material.Bed;
import org.bukkit.material.Sandstone;
import org.bukkit.material.Dispenser;
import org.bukkit.material.Tree;
import org.apache.commons.lang.Validate;
import java.util.Map;
import org.bukkit.material.MaterialData;
import java.lang.reflect.Constructor;

public enum Material
{
    AIR(0, 0), 
    STONE(1), 
    GRASS(2), 
    DIRT(3), 
    COBBLESTONE(4), 
    WOOD(5, (Class<? extends MaterialData>)Tree.class), 
    SAPLING(6, (Class<? extends MaterialData>)Tree.class), 
    BEDROCK(7), 
    WATER(8, (Class<? extends MaterialData>)MaterialData.class), 
    STATIONARY_WATER(9, (Class<? extends MaterialData>)MaterialData.class), 
    LAVA(10, (Class<? extends MaterialData>)MaterialData.class), 
    STATIONARY_LAVA(11, (Class<? extends MaterialData>)MaterialData.class), 
    SAND(12), 
    GRAVEL(13), 
    GOLD_ORE(14), 
    IRON_ORE(15), 
    COAL_ORE(16), 
    LOG(17, (Class<? extends MaterialData>)Tree.class), 
    LEAVES(18, (Class<? extends MaterialData>)Tree.class), 
    SPONGE(19), 
    GLASS(20), 
    LAPIS_ORE(21), 
    LAPIS_BLOCK(22), 
    DISPENSER(23, (Class<? extends MaterialData>)Dispenser.class), 
    SANDSTONE(24, (Class<? extends MaterialData>)Sandstone.class), 
    NOTE_BLOCK(25), 
    BED_BLOCK(26, (Class<? extends MaterialData>)Bed.class), 
    POWERED_RAIL(27, (Class<? extends MaterialData>)PoweredRail.class), 
    DETECTOR_RAIL(28, (Class<? extends MaterialData>)DetectorRail.class), 
    PISTON_STICKY_BASE(29, (Class<? extends MaterialData>)PistonBaseMaterial.class), 
    WEB(30), 
    LONG_GRASS(31, (Class<? extends MaterialData>)LongGrass.class), 
    DEAD_BUSH(32), 
    PISTON_BASE(33, (Class<? extends MaterialData>)PistonBaseMaterial.class), 
    PISTON_EXTENSION(34, (Class<? extends MaterialData>)PistonExtensionMaterial.class), 
    WOOL(35, (Class<? extends MaterialData>)Wool.class), 
    PISTON_MOVING_PIECE(36), 
    YELLOW_FLOWER(37), 
    RED_ROSE(38), 
    BROWN_MUSHROOM(39), 
    RED_MUSHROOM(40), 
    GOLD_BLOCK(41), 
    IRON_BLOCK(42), 
    DOUBLE_STEP(43, (Class<? extends MaterialData>)Step.class), 
    STEP(44, (Class<? extends MaterialData>)Step.class), 
    BRICK(45), 
    TNT(46), 
    BOOKSHELF(47), 
    MOSSY_COBBLESTONE(48), 
    OBSIDIAN(49), 
    TORCH(50, (Class<? extends MaterialData>)Torch.class), 
    FIRE(51), 
    MOB_SPAWNER(52), 
    WOOD_STAIRS(53, (Class<? extends MaterialData>)Stairs.class), 
    CHEST(54, (Class<? extends MaterialData>)Chest.class), 
    REDSTONE_WIRE(55, (Class<? extends MaterialData>)RedstoneWire.class), 
    DIAMOND_ORE(56), 
    DIAMOND_BLOCK(57), 
    WORKBENCH(58), 
    CROPS(59, (Class<? extends MaterialData>)Crops.class), 
    SOIL(60, (Class<? extends MaterialData>)MaterialData.class), 
    FURNACE(61, (Class<? extends MaterialData>)Furnace.class), 
    BURNING_FURNACE(62, (Class<? extends MaterialData>)Furnace.class), 
    SIGN_POST(63, 64, (Class<? extends MaterialData>)Sign.class), 
    WOODEN_DOOR(64, (Class<? extends MaterialData>)Door.class), 
    LADDER(65, (Class<? extends MaterialData>)Ladder.class), 
    RAILS(66, (Class<? extends MaterialData>)Rails.class), 
    COBBLESTONE_STAIRS(67, (Class<? extends MaterialData>)Stairs.class), 
    WALL_SIGN(68, 64, (Class<? extends MaterialData>)Sign.class), 
    LEVER(69, (Class<? extends MaterialData>)Lever.class), 
    STONE_PLATE(70, (Class<? extends MaterialData>)PressurePlate.class), 
    IRON_DOOR_BLOCK(71, (Class<? extends MaterialData>)Door.class), 
    WOOD_PLATE(72, (Class<? extends MaterialData>)PressurePlate.class), 
    REDSTONE_ORE(73), 
    GLOWING_REDSTONE_ORE(74), 
    REDSTONE_TORCH_OFF(75, (Class<? extends MaterialData>)RedstoneTorch.class), 
    REDSTONE_TORCH_ON(76, (Class<? extends MaterialData>)RedstoneTorch.class), 
    STONE_BUTTON(77, (Class<? extends MaterialData>)Button.class), 
    SNOW(78), 
    ICE(79), 
    SNOW_BLOCK(80), 
    CACTUS(81, (Class<? extends MaterialData>)MaterialData.class), 
    CLAY(82), 
    SUGAR_CANE_BLOCK(83, (Class<? extends MaterialData>)MaterialData.class), 
    JUKEBOX(84), 
    FENCE(85), 
    PUMPKIN(86, (Class<? extends MaterialData>)Pumpkin.class), 
    NETHERRACK(87), 
    SOUL_SAND(88), 
    GLOWSTONE(89), 
    PORTAL(90), 
    JACK_O_LANTERN(91, (Class<? extends MaterialData>)Pumpkin.class), 
    CAKE_BLOCK(92, 64, (Class<? extends MaterialData>)Cake.class), 
    DIODE_BLOCK_OFF(93, (Class<? extends MaterialData>)Diode.class), 
    DIODE_BLOCK_ON(94, (Class<? extends MaterialData>)Diode.class), 
    LOCKED_CHEST(95), 
    TRAP_DOOR(96, (Class<? extends MaterialData>)TrapDoor.class), 
    MONSTER_EGGS(97, (Class<? extends MaterialData>)MonsterEggs.class), 
    SMOOTH_BRICK(98, (Class<? extends MaterialData>)SmoothBrick.class), 
    HUGE_MUSHROOM_1(99, (Class<? extends MaterialData>)Mushroom.class), 
    HUGE_MUSHROOM_2(100, (Class<? extends MaterialData>)Mushroom.class), 
    IRON_FENCE(101), 
    THIN_GLASS(102), 
    MELON_BLOCK(103), 
    PUMPKIN_STEM(104, (Class<? extends MaterialData>)MaterialData.class), 
    MELON_STEM(105, (Class<? extends MaterialData>)MaterialData.class), 
    VINE(106, (Class<? extends MaterialData>)Vine.class), 
    FENCE_GATE(107, (Class<? extends MaterialData>)Gate.class), 
    BRICK_STAIRS(108, (Class<? extends MaterialData>)Stairs.class), 
    SMOOTH_STAIRS(109, (Class<? extends MaterialData>)Stairs.class), 
    MYCEL(110), 
    WATER_LILY(111), 
    NETHER_BRICK(112), 
    NETHER_FENCE(113), 
    NETHER_BRICK_STAIRS(114, (Class<? extends MaterialData>)Stairs.class), 
    NETHER_WARTS(115, (Class<? extends MaterialData>)NetherWarts.class), 
    ENCHANTMENT_TABLE(116), 
    BREWING_STAND(117, (Class<? extends MaterialData>)MaterialData.class), 
    CAULDRON(118, (Class<? extends MaterialData>)Cauldron.class), 
    ENDER_PORTAL(119), 
    ENDER_PORTAL_FRAME(120), 
    ENDER_STONE(121), 
    DRAGON_EGG(122), 
    REDSTONE_LAMP_OFF(123), 
    REDSTONE_LAMP_ON(124), 
    WOOD_DOUBLE_STEP(125, (Class<? extends MaterialData>)WoodenStep.class), 
    WOOD_STEP(126, (Class<? extends MaterialData>)WoodenStep.class), 
    COCOA(127, (Class<? extends MaterialData>)CocoaPlant.class), 
    SANDSTONE_STAIRS(128, (Class<? extends MaterialData>)Stairs.class), 
    EMERALD_ORE(129), 
    ENDER_CHEST(130, (Class<? extends MaterialData>)EnderChest.class), 
    TRIPWIRE_HOOK(131, (Class<? extends MaterialData>)TripwireHook.class), 
    TRIPWIRE(132, (Class<? extends MaterialData>)Tripwire.class), 
    EMERALD_BLOCK(133), 
    SPRUCE_WOOD_STAIRS(134, (Class<? extends MaterialData>)Stairs.class), 
    BIRCH_WOOD_STAIRS(135, (Class<? extends MaterialData>)Stairs.class), 
    JUNGLE_WOOD_STAIRS(136, (Class<? extends MaterialData>)Stairs.class), 
    COMMAND(137, (Class<? extends MaterialData>)Command.class), 
    BEACON(138), 
    COBBLE_WALL(139), 
    FLOWER_POT(140, (Class<? extends MaterialData>)FlowerPot.class), 
    CARROT(141), 
    POTATO(142), 
    WOOD_BUTTON(143, (Class<? extends MaterialData>)Button.class), 
    SKULL(144, (Class<? extends MaterialData>)Skull.class), 
    ANVIL(145), 
    TRAPPED_CHEST(146), 
    GOLD_PLATE(147), 
    IRON_PLATE(148), 
    REDSTONE_COMPARATOR_OFF(149), 
    REDSTONE_COMPARATOR_ON(150), 
    DAYLIGHT_DETECTOR(151), 
    REDSTONE_BLOCK(152), 
    QUARTZ_ORE(153), 
    HOPPER(154), 
    QUARTZ_BLOCK(155), 
    QUARTZ_STAIRS(156, (Class<? extends MaterialData>)Stairs.class), 
    ACTIVATOR_RAIL(157, (Class<? extends MaterialData>)PoweredRail.class), 
    DROPPER(158, (Class<? extends MaterialData>)Dispenser.class), 
    IRON_SPADE(256, 1, 250), 
    IRON_PICKAXE(257, 1, 250), 
    IRON_AXE(258, 1, 250), 
    FLINT_AND_STEEL(259, 1, 64), 
    APPLE(260), 
    BOW(261, 1, 384), 
    ARROW(262), 
    COAL(263, (Class<? extends MaterialData>)Coal.class), 
    DIAMOND(264), 
    IRON_INGOT(265), 
    GOLD_INGOT(266), 
    IRON_SWORD(267, 1, 250), 
    WOOD_SWORD(268, 1, 59), 
    WOOD_SPADE(269, 1, 59), 
    WOOD_PICKAXE(270, 1, 59), 
    WOOD_AXE(271, 1, 59), 
    STONE_SWORD(272, 1, 131), 
    STONE_SPADE(273, 1, 131), 
    STONE_PICKAXE(274, 1, 131), 
    STONE_AXE(275, 1, 131), 
    DIAMOND_SWORD(276, 1, 1561), 
    DIAMOND_SPADE(277, 1, 1561), 
    DIAMOND_PICKAXE(278, 1, 1561), 
    DIAMOND_AXE(279, 1, 1561), 
    STICK(280), 
    BOWL(281), 
    MUSHROOM_SOUP(282, 1), 
    GOLD_SWORD(283, 1, 32), 
    GOLD_SPADE(284, 1, 32), 
    GOLD_PICKAXE(285, 1, 32), 
    GOLD_AXE(286, 1, 32), 
    STRING(287), 
    FEATHER(288), 
    SULPHUR(289), 
    WOOD_HOE(290, 1, 59), 
    STONE_HOE(291, 1, 131), 
    IRON_HOE(292, 1, 250), 
    DIAMOND_HOE(293, 1, 1561), 
    GOLD_HOE(294, 1, 32), 
    SEEDS(295), 
    WHEAT(296), 
    BREAD(297), 
    LEATHER_HELMET(298, 1, 55), 
    LEATHER_CHESTPLATE(299, 1, 80), 
    LEATHER_LEGGINGS(300, 1, 75), 
    LEATHER_BOOTS(301, 1, 65), 
    CHAINMAIL_HELMET(302, 1, 165), 
    CHAINMAIL_CHESTPLATE(303, 1, 240), 
    CHAINMAIL_LEGGINGS(304, 1, 225), 
    CHAINMAIL_BOOTS(305, 1, 195), 
    IRON_HELMET(306, 1, 165), 
    IRON_CHESTPLATE(307, 1, 240), 
    IRON_LEGGINGS(308, 1, 225), 
    IRON_BOOTS(309, 1, 195), 
    DIAMOND_HELMET(310, 1, 363), 
    DIAMOND_CHESTPLATE(311, 1, 528), 
    DIAMOND_LEGGINGS(312, 1, 495), 
    DIAMOND_BOOTS(313, 1, 429), 
    GOLD_HELMET(314, 1, 77), 
    GOLD_CHESTPLATE(315, 1, 112), 
    GOLD_LEGGINGS(316, 1, 105), 
    GOLD_BOOTS(317, 1, 91), 
    FLINT(318), 
    PORK(319), 
    GRILLED_PORK(320), 
    PAINTING(321), 
    GOLDEN_APPLE(322), 
    SIGN(323, 16), 
    WOOD_DOOR(324, 1), 
    BUCKET(325, 16), 
    WATER_BUCKET(326, 1), 
    LAVA_BUCKET(327, 1), 
    MINECART(328, 1), 
    SADDLE(329, 1), 
    IRON_DOOR(330, 1), 
    REDSTONE(331), 
    SNOW_BALL(332, 16), 
    BOAT(333, 1), 
    LEATHER(334), 
    MILK_BUCKET(335, 1), 
    CLAY_BRICK(336), 
    CLAY_BALL(337), 
    SUGAR_CANE(338), 
    PAPER(339), 
    BOOK(340), 
    SLIME_BALL(341), 
    STORAGE_MINECART(342, 1), 
    POWERED_MINECART(343, 1), 
    EGG(344, 16), 
    COMPASS(345), 
    FISHING_ROD(346, 1, 64), 
    WATCH(347), 
    GLOWSTONE_DUST(348), 
    RAW_FISH(349), 
    COOKED_FISH(350), 
    INK_SACK(351, (Class<? extends MaterialData>)Dye.class), 
    BONE(352), 
    SUGAR(353), 
    CAKE(354, 1), 
    BED(355, 1), 
    DIODE(356), 
    COOKIE(357), 
    MAP(358, (Class<? extends MaterialData>)MaterialData.class), 
    SHEARS(359, 1, 238), 
    MELON(360), 
    PUMPKIN_SEEDS(361), 
    MELON_SEEDS(362), 
    RAW_BEEF(363), 
    COOKED_BEEF(364), 
    RAW_CHICKEN(365), 
    COOKED_CHICKEN(366), 
    ROTTEN_FLESH(367), 
    ENDER_PEARL(368, 16), 
    BLAZE_ROD(369), 
    GHAST_TEAR(370), 
    GOLD_NUGGET(371), 
    NETHER_STALK(372), 
    POTION(373, 1, (Class<? extends MaterialData>)MaterialData.class), 
    GLASS_BOTTLE(374), 
    SPIDER_EYE(375), 
    FERMENTED_SPIDER_EYE(376), 
    BLAZE_POWDER(377), 
    MAGMA_CREAM(378), 
    BREWING_STAND_ITEM(379), 
    CAULDRON_ITEM(380), 
    EYE_OF_ENDER(381), 
    SPECKLED_MELON(382), 
    MONSTER_EGG(383, 64, (Class<? extends MaterialData>)SpawnEgg.class), 
    EXP_BOTTLE(384, 64), 
    FIREBALL(385, 64), 
    BOOK_AND_QUILL(386, 1), 
    WRITTEN_BOOK(387, 1), 
    EMERALD(388, 64), 
    ITEM_FRAME(389), 
    FLOWER_POT_ITEM(390), 
    CARROT_ITEM(391), 
    POTATO_ITEM(392), 
    BAKED_POTATO(393), 
    POISONOUS_POTATO(394), 
    EMPTY_MAP(395), 
    GOLDEN_CARROT(396), 
    SKULL_ITEM(397), 
    CARROT_STICK(398, 1, 25), 
    NETHER_STAR(399), 
    PUMPKIN_PIE(400), 
    FIREWORK(401), 
    FIREWORK_CHARGE(402), 
    ENCHANTED_BOOK(403, 1), 
    REDSTONE_COMPARATOR(404), 
    NETHER_BRICK_ITEM(405), 
    QUARTZ(406), 
    EXPLOSIVE_MINECART(407, 1), 
    HOPPER_MINECART(408, 1), 
    GOLD_RECORD(2256, 1), 
    GREEN_RECORD(2257, 1), 
    RECORD_3(2258, 1), 
    RECORD_4(2259, 1), 
    RECORD_5(2260, 1), 
    RECORD_6(2261, 1), 
    RECORD_7(2262, 1), 
    RECORD_8(2263, 1), 
    RECORD_9(2264, 1), 
    RECORD_10(2265, 1), 
    RECORD_11(2266, 1), 
    RECORD_12(2267, 1);
    
    private final int id;
    private final Constructor<? extends MaterialData> ctor;
    private static Material[] byId;
    private static final Map<String, Material> BY_NAME;
    private final int maxStack;
    private final short durability;
    
    private Material(final int id) {
        this(id, 64);
    }
    
    private Material(final int id, final int stack) {
        this(id, stack, MaterialData.class);
    }
    
    private Material(final int id, final int stack, final int durability) {
        this(id, stack, durability, MaterialData.class);
    }
    
    private Material(final int id, final Class<? extends MaterialData> data) {
        this(id, 64, data);
    }
    
    private Material(final int id, final int stack, final Class<? extends MaterialData> data) {
        this(id, stack, 0, data);
    }
    
    private Material(final int id, final int stack, final int durability, final Class<? extends MaterialData> data) {
        this.id = id;
        this.durability = (short)durability;
        this.maxStack = stack;
        try {
            this.ctor = data.getConstructor(Integer.TYPE, Byte.TYPE);
        }
        catch (NoSuchMethodException ex) {
            throw new AssertionError((Object)ex);
        }
        catch (SecurityException ex2) {
            throw new AssertionError((Object)ex2);
        }
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getMaxStackSize() {
        return this.maxStack;
    }
    
    public short getMaxDurability() {
        return this.durability;
    }
    
    public Class<? extends MaterialData> getData() {
        return this.ctor.getDeclaringClass();
    }
    
    public MaterialData getNewData(final byte raw) {
        try {
            return (MaterialData)this.ctor.newInstance(this.id, raw);
        }
        catch (InstantiationException ex) {
            final Throwable t = ex.getCause();
            if (t instanceof RuntimeException) {
                throw (RuntimeException)t;
            }
            if (t instanceof Error) {
                throw (Error)t;
            }
            throw new AssertionError((Object)t);
        }
        catch (Throwable t2) {
            throw new AssertionError((Object)t2);
        }
    }
    
    public boolean isBlock() {
        return this.id < 256;
    }
    
    public boolean isEdible() {
        switch (this) {
            case BREAD:
            case CARROT_ITEM:
            case BAKED_POTATO:
            case POTATO_ITEM:
            case POISONOUS_POTATO:
            case GOLDEN_CARROT:
            case PUMPKIN_PIE:
            case COOKIE:
            case MELON:
            case MUSHROOM_SOUP:
            case RAW_CHICKEN:
            case COOKED_CHICKEN:
            case RAW_BEEF:
            case COOKED_BEEF:
            case RAW_FISH:
            case COOKED_FISH:
            case PORK:
            case GRILLED_PORK:
            case APPLE:
            case GOLDEN_APPLE:
            case ROTTEN_FLESH:
            case SPIDER_EYE: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public static Material getMaterial(final int id) {
        if (Material.byId.length > id && id >= 0) {
            return Material.byId[id];
        }
        return null;
    }
    
    public static Material getMaterial(final String name) {
        return Material.BY_NAME.get(name);
    }
    
    public static Material matchMaterial(final String name) {
        Validate.notNull(name, "Name cannot be null");
        Material result = null;
        try {
            result = getMaterial(Integer.parseInt(name));
        }
        catch (NumberFormatException ex) {}
        if (result == null) {
            String filtered = name.toUpperCase();
            filtered = filtered.replaceAll("\\s+", "_").replaceAll("\\W", "");
            result = Material.BY_NAME.get(filtered);
        }
        return result;
    }
    
    public boolean isRecord() {
        return this.id >= Material.GOLD_RECORD.id && this.id <= Material.RECORD_12.id;
    }
    
    public boolean isSolid() {
        if (!this.isBlock() || this.id == 0) {
            return false;
        }
        switch (this) {
            case STONE:
            case GRASS:
            case DIRT:
            case COBBLESTONE:
            case WOOD:
            case BEDROCK:
            case SAND:
            case GRAVEL:
            case GOLD_ORE:
            case IRON_ORE:
            case COAL_ORE:
            case LOG:
            case LEAVES:
            case SPONGE:
            case GLASS:
            case LAPIS_ORE:
            case LAPIS_BLOCK:
            case DISPENSER:
            case SANDSTONE:
            case NOTE_BLOCK:
            case BED_BLOCK:
            case PISTON_STICKY_BASE:
            case PISTON_BASE:
            case PISTON_EXTENSION:
            case WOOL:
            case PISTON_MOVING_PIECE:
            case GOLD_BLOCK:
            case IRON_BLOCK:
            case DOUBLE_STEP:
            case STEP:
            case BRICK:
            case TNT:
            case BOOKSHELF:
            case MOSSY_COBBLESTONE:
            case OBSIDIAN:
            case MOB_SPAWNER:
            case WOOD_STAIRS:
            case CHEST:
            case DIAMOND_ORE:
            case DIAMOND_BLOCK:
            case WORKBENCH:
            case SOIL:
            case FURNACE:
            case BURNING_FURNACE:
            case SIGN_POST:
            case WOODEN_DOOR:
            case COBBLESTONE_STAIRS:
            case WALL_SIGN:
            case STONE_PLATE:
            case IRON_DOOR_BLOCK:
            case WOOD_PLATE:
            case REDSTONE_ORE:
            case GLOWING_REDSTONE_ORE:
            case ICE:
            case SNOW_BLOCK:
            case CACTUS:
            case CLAY:
            case JUKEBOX:
            case FENCE:
            case PUMPKIN:
            case NETHERRACK:
            case SOUL_SAND:
            case GLOWSTONE:
            case JACK_O_LANTERN:
            case CAKE_BLOCK:
            case LOCKED_CHEST:
            case TRAP_DOOR:
            case MONSTER_EGGS:
            case SMOOTH_BRICK:
            case HUGE_MUSHROOM_1:
            case HUGE_MUSHROOM_2:
            case IRON_FENCE:
            case THIN_GLASS:
            case MELON_BLOCK:
            case FENCE_GATE:
            case BRICK_STAIRS:
            case SMOOTH_STAIRS:
            case MYCEL:
            case NETHER_BRICK:
            case NETHER_FENCE:
            case NETHER_BRICK_STAIRS:
            case ENCHANTMENT_TABLE:
            case BREWING_STAND:
            case CAULDRON:
            case ENDER_PORTAL_FRAME:
            case ENDER_STONE:
            case DRAGON_EGG:
            case REDSTONE_LAMP_OFF:
            case REDSTONE_LAMP_ON:
            case WOOD_DOUBLE_STEP:
            case WOOD_STEP:
            case SANDSTONE_STAIRS:
            case EMERALD_ORE:
            case ENDER_CHEST:
            case EMERALD_BLOCK:
            case SPRUCE_WOOD_STAIRS:
            case BIRCH_WOOD_STAIRS:
            case JUNGLE_WOOD_STAIRS:
            case COMMAND:
            case BEACON:
            case COBBLE_WALL:
            case ANVIL:
            case TRAPPED_CHEST:
            case GOLD_PLATE:
            case IRON_PLATE:
            case DAYLIGHT_DETECTOR:
            case REDSTONE_BLOCK:
            case QUARTZ_ORE:
            case HOPPER:
            case QUARTZ_BLOCK:
            case QUARTZ_STAIRS:
            case DROPPER: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean isTransparent() {
        if (!this.isBlock()) {
            return false;
        }
        switch (this) {
            case AIR:
            case SAPLING:
            case POWERED_RAIL:
            case DETECTOR_RAIL:
            case LONG_GRASS:
            case DEAD_BUSH:
            case YELLOW_FLOWER:
            case RED_ROSE:
            case BROWN_MUSHROOM:
            case RED_MUSHROOM:
            case TORCH:
            case FIRE:
            case REDSTONE_WIRE:
            case CROPS:
            case LADDER:
            case RAILS:
            case LEVER:
            case REDSTONE_TORCH_OFF:
            case REDSTONE_TORCH_ON:
            case STONE_BUTTON:
            case SNOW:
            case SUGAR_CANE_BLOCK:
            case PORTAL:
            case DIODE_BLOCK_OFF:
            case DIODE_BLOCK_ON:
            case PUMPKIN_STEM:
            case MELON_STEM:
            case VINE:
            case WATER_LILY:
            case NETHER_WARTS:
            case ENDER_PORTAL:
            case COCOA:
            case TRIPWIRE_HOOK:
            case TRIPWIRE:
            case FLOWER_POT:
            case CARROT:
            case POTATO:
            case WOOD_BUTTON:
            case SKULL:
            case REDSTONE_COMPARATOR_OFF:
            case REDSTONE_COMPARATOR_ON:
            case ACTIVATOR_RAIL: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean isFlammable() {
        if (!this.isBlock()) {
            return false;
        }
        switch (this) {
            case WOOD:
            case LOG:
            case LEAVES:
            case NOTE_BLOCK:
            case BED_BLOCK:
            case WOOL:
            case TNT:
            case BOOKSHELF:
            case WOOD_STAIRS:
            case CHEST:
            case WORKBENCH:
            case SIGN_POST:
            case WOODEN_DOOR:
            case WALL_SIGN:
            case WOOD_PLATE:
            case JUKEBOX:
            case FENCE:
            case LOCKED_CHEST:
            case TRAP_DOOR:
            case HUGE_MUSHROOM_1:
            case HUGE_MUSHROOM_2:
            case FENCE_GATE:
            case WOOD_DOUBLE_STEP:
            case WOOD_STEP:
            case SPRUCE_WOOD_STAIRS:
            case BIRCH_WOOD_STAIRS:
            case JUNGLE_WOOD_STAIRS:
            case TRAPPED_CHEST:
            case DAYLIGHT_DETECTOR:
            case LONG_GRASS:
            case DEAD_BUSH:
            case VINE: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean isBurnable() {
        if (!this.isBlock()) {
            return false;
        }
        switch (this) {
            case WOOD:
            case LOG:
            case LEAVES:
            case WOOL:
            case TNT:
            case BOOKSHELF:
            case WOOD_STAIRS:
            case FENCE:
            case WOOD_DOUBLE_STEP:
            case WOOD_STEP:
            case SPRUCE_WOOD_STAIRS:
            case BIRCH_WOOD_STAIRS:
            case JUNGLE_WOOD_STAIRS:
            case LONG_GRASS:
            case VINE: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean isOccluding() {
        if (!this.isBlock()) {
            return false;
        }
        switch (this) {
            case STONE:
            case GRASS:
            case DIRT:
            case COBBLESTONE:
            case WOOD:
            case BEDROCK:
            case SAND:
            case GRAVEL:
            case GOLD_ORE:
            case IRON_ORE:
            case COAL_ORE:
            case LOG:
            case SPONGE:
            case LAPIS_ORE:
            case LAPIS_BLOCK:
            case DISPENSER:
            case SANDSTONE:
            case NOTE_BLOCK:
            case WOOL:
            case GOLD_BLOCK:
            case IRON_BLOCK:
            case DOUBLE_STEP:
            case BRICK:
            case BOOKSHELF:
            case MOSSY_COBBLESTONE:
            case OBSIDIAN:
            case MOB_SPAWNER:
            case DIAMOND_ORE:
            case DIAMOND_BLOCK:
            case WORKBENCH:
            case FURNACE:
            case BURNING_FURNACE:
            case REDSTONE_ORE:
            case GLOWING_REDSTONE_ORE:
            case SNOW_BLOCK:
            case CLAY:
            case JUKEBOX:
            case PUMPKIN:
            case NETHERRACK:
            case SOUL_SAND:
            case JACK_O_LANTERN:
            case LOCKED_CHEST:
            case MONSTER_EGGS:
            case SMOOTH_BRICK:
            case HUGE_MUSHROOM_1:
            case HUGE_MUSHROOM_2:
            case MELON_BLOCK:
            case MYCEL:
            case NETHER_BRICK:
            case ENDER_PORTAL_FRAME:
            case ENDER_STONE:
            case REDSTONE_LAMP_OFF:
            case REDSTONE_LAMP_ON:
            case WOOD_DOUBLE_STEP:
            case EMERALD_ORE:
            case EMERALD_BLOCK:
            case COMMAND:
            case QUARTZ_ORE:
            case QUARTZ_BLOCK:
            case DROPPER: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean hasGravity() {
        if (!this.isBlock()) {
            return false;
        }
        switch (this) {
            case SAND:
            case GRAVEL:
            case ANVIL: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    static {
        Material.byId = new Material[383];
        BY_NAME = Maps.newHashMap();
        for (final Material material : values()) {
            if (Material.byId.length > material.id) {
                Material.byId[material.id] = material;
            }
            else {
                (Material.byId = Java15Compat.Arrays_copyOfRange(Material.byId, 0, material.id + 2))[material.id] = material;
            }
            Material.BY_NAME.put(material.name(), material);
        }
    }
}
