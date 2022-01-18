// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class Item
{
    private CreativeModeTab a;
    protected static Random e;
    public static Item[] byId;
    public static Item IRON_SPADE;
    public static Item IRON_PICKAXE;
    public static Item IRON_AXE;
    public static Item FLINT_AND_STEEL;
    public static Item APPLE;
    public static ItemBow BOW;
    public static Item ARROW;
    public static Item COAL;
    public static Item DIAMOND;
    public static Item IRON_INGOT;
    public static Item GOLD_INGOT;
    public static Item IRON_SWORD;
    public static Item WOOD_SWORD;
    public static Item WOOD_SPADE;
    public static Item WOOD_PICKAXE;
    public static Item WOOD_AXE;
    public static Item STONE_SWORD;
    public static Item STONE_SPADE;
    public static Item STONE_PICKAXE;
    public static Item STONE_AXE;
    public static Item DIAMOND_SWORD;
    public static Item DIAMOND_SPADE;
    public static Item DIAMOND_PICKAXE;
    public static Item DIAMOND_AXE;
    public static Item STICK;
    public static Item BOWL;
    public static Item MUSHROOM_SOUP;
    public static Item GOLD_SWORD;
    public static Item GOLD_SPADE;
    public static Item GOLD_PICKAXE;
    public static Item GOLD_AXE;
    public static Item STRING;
    public static Item FEATHER;
    public static Item SULPHUR;
    public static Item WOOD_HOE;
    public static Item STONE_HOE;
    public static Item IRON_HOE;
    public static Item DIAMOND_HOE;
    public static Item GOLD_HOE;
    public static Item SEEDS;
    public static Item WHEAT;
    public static Item BREAD;
    public static ItemArmor LEATHER_HELMET;
    public static ItemArmor LEATHER_CHESTPLATE;
    public static ItemArmor LEATHER_LEGGINGS;
    public static ItemArmor LEATHER_BOOTS;
    public static ItemArmor CHAINMAIL_HELMET;
    public static ItemArmor CHAINMAIL_CHESTPLATE;
    public static ItemArmor CHAINMAIL_LEGGINGS;
    public static ItemArmor CHAINMAIL_BOOTS;
    public static ItemArmor IRON_HELMET;
    public static ItemArmor IRON_CHESTPLATE;
    public static ItemArmor IRON_LEGGINGS;
    public static ItemArmor IRON_BOOTS;
    public static ItemArmor DIAMOND_HELMET;
    public static ItemArmor DIAMOND_CHESTPLATE;
    public static ItemArmor DIAMOND_LEGGINGS;
    public static ItemArmor DIAMOND_BOOTS;
    public static ItemArmor GOLD_HELMET;
    public static ItemArmor GOLD_CHESTPLATE;
    public static ItemArmor GOLD_LEGGINGS;
    public static ItemArmor GOLD_BOOTS;
    public static Item FLINT;
    public static Item PORK;
    public static Item GRILLED_PORK;
    public static Item PAINTING;
    public static Item GOLDEN_APPLE;
    public static Item SIGN;
    public static Item WOOD_DOOR;
    public static Item BUCKET;
    public static Item WATER_BUCKET;
    public static Item LAVA_BUCKET;
    public static Item MINECART;
    public static Item SADDLE;
    public static Item IRON_DOOR;
    public static Item REDSTONE;
    public static Item SNOW_BALL;
    public static Item BOAT;
    public static Item LEATHER;
    public static Item MILK_BUCKET;
    public static Item CLAY_BRICK;
    public static Item CLAY_BALL;
    public static Item SUGAR_CANE;
    public static Item PAPER;
    public static Item BOOK;
    public static Item SLIME_BALL;
    public static Item STORAGE_MINECART;
    public static Item POWERED_MINECART;
    public static Item EGG;
    public static Item COMPASS;
    public static ItemFishingRod FISHING_ROD;
    public static Item WATCH;
    public static Item GLOWSTONE_DUST;
    public static Item RAW_FISH;
    public static Item COOKED_FISH;
    public static Item INK_SACK;
    public static Item BONE;
    public static Item SUGAR;
    public static Item CAKE;
    public static Item BED;
    public static Item DIODE;
    public static Item COOKIE;
    public static ItemWorldMap MAP;
    public static ItemShears SHEARS;
    public static Item MELON;
    public static Item PUMPKIN_SEEDS;
    public static Item MELON_SEEDS;
    public static Item RAW_BEEF;
    public static Item COOKED_BEEF;
    public static Item RAW_CHICKEN;
    public static Item COOKED_CHICKEN;
    public static Item ROTTEN_FLESH;
    public static Item ENDER_PEARL;
    public static Item BLAZE_ROD;
    public static Item GHAST_TEAR;
    public static Item GOLD_NUGGET;
    public static Item NETHER_STALK;
    public static ItemPotion POTION;
    public static Item GLASS_BOTTLE;
    public static Item SPIDER_EYE;
    public static Item FERMENTED_SPIDER_EYE;
    public static Item BLAZE_POWDER;
    public static Item MAGMA_CREAM;
    public static Item BREWING_STAND;
    public static Item CAULDRON;
    public static Item EYE_OF_ENDER;
    public static Item SPECKLED_MELON;
    public static Item MONSTER_EGG;
    public static Item EXP_BOTTLE;
    public static Item FIREBALL;
    public static Item BOOK_AND_QUILL;
    public static Item WRITTEN_BOOK;
    public static Item EMERALD;
    public static Item ITEM_FRAME;
    public static Item FLOWER_POT;
    public static Item CARROT;
    public static Item POTATO;
    public static Item POTATO_BAKED;
    public static Item POTATO_POISON;
    public static ItemMapEmpty MAP_EMPTY;
    public static Item CARROT_GOLDEN;
    public static Item SKULL;
    public static Item CARROT_STICK;
    public static Item NETHER_STAR;
    public static Item PUMPKIN_PIE;
    public static Item FIREWORKS;
    public static Item FIREWORKS_CHARGE;
    public static ItemEnchantedBook ENCHANTED_BOOK;
    public static Item REDSTONE_COMPARATOR;
    public static Item NETHER_BRICK;
    public static Item QUARTZ;
    public static Item MINECART_TNT;
    public static Item MINECART_HOPPER;
    public static Item RECORD_1;
    public static Item RECORD_2;
    public static Item RECORD_3;
    public static Item RECORD_4;
    public static Item RECORD_5;
    public static Item RECORD_6;
    public static Item RECORD_7;
    public static Item RECORD_8;
    public static Item RECORD_9;
    public static Item RECORD_10;
    public static Item RECORD_11;
    public static Item RECORD_12;
    public final int id;
    protected int maxStackSize;
    private int durability;
    protected boolean cr;
    protected boolean cs;
    private Item craftingResult;
    private String d;
    private String name;
    
    protected Item(final int n) {
        this.a = null;
        this.maxStackSize = 64;
        this.durability = 0;
        this.cr = false;
        this.cs = false;
        this.craftingResult = null;
        this.d = null;
        this.id = 256 + n;
        if (Item.byId[256 + n] != null) {
            System.out.println("CONFLICT @ " + n);
        }
        Item.byId[256 + n] = this;
    }
    
    public Item d(final int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
    }
    
    public boolean interactWith(final ItemStack itemStack, final EntityHuman entityHuman, final World world, final int n, final int n2, final int n3, final int n4, final float n5, final float n6, final float n7) {
        return false;
    }
    
    public float getDestroySpeed(final ItemStack itemStack, final Block block) {
        return 1.0f;
    }
    
    public ItemStack a(final ItemStack itemStack, final World world, final EntityHuman entityHuman) {
        return itemStack;
    }
    
    public ItemStack b(final ItemStack itemStack, final World world, final EntityHuman entityHuman) {
        return itemStack;
    }
    
    public int getMaxStackSize() {
        return this.maxStackSize;
    }
    
    public int filterData(final int n) {
        return 0;
    }
    
    public boolean m() {
        return this.cs;
    }
    
    protected Item a(final boolean cs) {
        this.cs = cs;
        return this;
    }
    
    public int getMaxDurability() {
        return this.durability;
    }
    
    protected Item setMaxDurability(final int durability) {
        this.durability = durability;
        return this;
    }
    
    public boolean usesDurability() {
        return this.durability > 0 && !this.cs;
    }
    
    public boolean a(final ItemStack itemStack, final EntityLiving entityLiving, final EntityLiving entityLiving2) {
        return false;
    }
    
    public boolean a(final ItemStack itemStack, final World world, final int n, final int n2, final int n3, final int n4, final EntityLiving entityLiving) {
        return false;
    }
    
    public int a(final Entity entity) {
        return 1;
    }
    
    public boolean canDestroySpecialBlock(final Block block) {
        return false;
    }
    
    public boolean a(final ItemStack itemStack, final EntityLiving entityLiving) {
        return false;
    }
    
    public Item p() {
        this.cr = true;
        return this;
    }
    
    public Item b(final String name) {
        this.name = name;
        return this;
    }
    
    public String i(final ItemStack itemStack) {
        final String d = this.d(itemStack);
        if (d == null) {
            return "";
        }
        return LocaleI18n.get(d);
    }
    
    public String getName() {
        return "item." + this.name;
    }
    
    public String d(final ItemStack itemStack) {
        return "item." + this.name;
    }
    
    public Item a(final Item craftingResult) {
        this.craftingResult = craftingResult;
        return this;
    }
    
    public boolean j(final ItemStack itemStack) {
        return true;
    }
    
    public boolean r() {
        return true;
    }
    
    public Item s() {
        return this.craftingResult;
    }
    
    public boolean t() {
        return this.craftingResult != null;
    }
    
    public String u() {
        return LocaleI18n.get(this.getName() + ".name");
    }
    
    public String k(final ItemStack itemStack) {
        return LocaleI18n.get(this.d(itemStack) + ".name");
    }
    
    public void a(final ItemStack itemStack, final World world, final Entity entity, final int n, final boolean b) {
    }
    
    public void d(final ItemStack itemStack, final World world, final EntityHuman entityHuman) {
    }
    
    public boolean f() {
        return false;
    }
    
    public EnumAnimation b_(final ItemStack itemStack) {
        return EnumAnimation.NONE;
    }
    
    public int c_(final ItemStack itemStack) {
        return 0;
    }
    
    public void a(final ItemStack itemStack, final World world, final EntityHuman entityHuman, final int n) {
    }
    
    protected Item c(final String d) {
        this.d = d;
        return this;
    }
    
    public String v() {
        return this.d;
    }
    
    public boolean w() {
        return this.d != null;
    }
    
    public String l(final ItemStack itemStack) {
        return ("" + LocaleLanguage.a().c(this.i(itemStack))).trim();
    }
    
    public boolean d_(final ItemStack itemStack) {
        return this.getMaxStackSize() == 1 && this.usesDurability();
    }
    
    protected MovingObjectPosition a(final World world, final EntityHuman entityHuman, final boolean flag) {
        final float n = 1.0f;
        final float n2 = entityHuman.lastPitch + (entityHuman.pitch - entityHuman.lastPitch) * n;
        final float n3 = entityHuman.lastYaw + (entityHuman.yaw - entityHuman.lastYaw) * n;
        final Vec3D create = world.getVec3DPool().create(entityHuman.lastX + (entityHuman.locX - entityHuman.lastX) * n, entityHuman.lastY + (entityHuman.locY - entityHuman.lastY) * n + 1.62 - entityHuman.height, entityHuman.lastZ + (entityHuman.locZ - entityHuman.lastZ) * n);
        final float cos = MathHelper.cos(-n3 * 0.017453292f - 3.1415927f);
        final float sin = MathHelper.sin(-n3 * 0.017453292f - 3.1415927f);
        final float n4 = -MathHelper.cos(-n2 * 0.017453292f);
        final float sin2 = MathHelper.sin(-n2 * 0.017453292f);
        final float n5 = sin * n4;
        final float n6 = sin2;
        final float n7 = cos * n4;
        final double n8 = 5.0;
        return world.rayTrace(create, create.add(n5 * n8, n6 * n8, n7 * n8), flag, !flag);
    }
    
    public int c() {
        return 0;
    }
    
    public Item a(final CreativeModeTab a) {
        this.a = a;
        return this;
    }
    
    public boolean y() {
        return true;
    }
    
    public boolean a(final ItemStack itemStack, final ItemStack itemStack2) {
        return false;
    }
    
    static {
        Item.e = new Random();
        Item.byId = new Item[32000];
        Item.IRON_SPADE = new ItemSpade(0, EnumToolMaterial.IRON).b("shovelIron");
        Item.IRON_PICKAXE = new ItemPickaxe(1, EnumToolMaterial.IRON).b("pickaxeIron");
        Item.IRON_AXE = new ItemAxe(2, EnumToolMaterial.IRON).b("hatchetIron");
        Item.FLINT_AND_STEEL = new ItemFlintAndSteel(3).b("flintAndSteel");
        Item.APPLE = new ItemFood(4, 4, 0.3f, false).b("apple");
        Item.BOW = (ItemBow)new ItemBow(5).b("bow");
        Item.ARROW = new Item(6).b("arrow").a(CreativeModeTab.j);
        Item.COAL = new ItemCoal(7).b("coal");
        Item.DIAMOND = new Item(8).b("diamond").a(CreativeModeTab.l);
        Item.IRON_INGOT = new Item(9).b("ingotIron").a(CreativeModeTab.l);
        Item.GOLD_INGOT = new Item(10).b("ingotGold").a(CreativeModeTab.l);
        Item.IRON_SWORD = new ItemSword(11, EnumToolMaterial.IRON).b("swordIron");
        Item.WOOD_SWORD = new ItemSword(12, EnumToolMaterial.WOOD).b("swordWood");
        Item.WOOD_SPADE = new ItemSpade(13, EnumToolMaterial.WOOD).b("shovelWood");
        Item.WOOD_PICKAXE = new ItemPickaxe(14, EnumToolMaterial.WOOD).b("pickaxeWood");
        Item.WOOD_AXE = new ItemAxe(15, EnumToolMaterial.WOOD).b("hatchetWood");
        Item.STONE_SWORD = new ItemSword(16, EnumToolMaterial.STONE).b("swordStone");
        Item.STONE_SPADE = new ItemSpade(17, EnumToolMaterial.STONE).b("shovelStone");
        Item.STONE_PICKAXE = new ItemPickaxe(18, EnumToolMaterial.STONE).b("pickaxeStone");
        Item.STONE_AXE = new ItemAxe(19, EnumToolMaterial.STONE).b("hatchetStone");
        Item.DIAMOND_SWORD = new ItemSword(20, EnumToolMaterial.DIAMOND).b("swordDiamond");
        Item.DIAMOND_SPADE = new ItemSpade(21, EnumToolMaterial.DIAMOND).b("shovelDiamond");
        Item.DIAMOND_PICKAXE = new ItemPickaxe(22, EnumToolMaterial.DIAMOND).b("pickaxeDiamond");
        Item.DIAMOND_AXE = new ItemAxe(23, EnumToolMaterial.DIAMOND).b("hatchetDiamond");
        Item.STICK = new Item(24).p().b("stick").a(CreativeModeTab.l);
        Item.BOWL = new Item(25).b("bowl").a(CreativeModeTab.l);
        Item.MUSHROOM_SOUP = new ItemSoup(26, 6).b("mushroomStew");
        Item.GOLD_SWORD = new ItemSword(27, EnumToolMaterial.GOLD).b("swordGold");
        Item.GOLD_SPADE = new ItemSpade(28, EnumToolMaterial.GOLD).b("shovelGold");
        Item.GOLD_PICKAXE = new ItemPickaxe(29, EnumToolMaterial.GOLD).b("pickaxeGold");
        Item.GOLD_AXE = new ItemAxe(30, EnumToolMaterial.GOLD).b("hatchetGold");
        Item.STRING = new ItemReed(31, Block.TRIPWIRE).b("string").a(CreativeModeTab.l);
        Item.FEATHER = new Item(32).b("feather").a(CreativeModeTab.l);
        Item.SULPHUR = new Item(33).b("sulphur").c(PotionBrewer.k).a(CreativeModeTab.l);
        Item.WOOD_HOE = new ItemHoe(34, EnumToolMaterial.WOOD).b("hoeWood");
        Item.STONE_HOE = new ItemHoe(35, EnumToolMaterial.STONE).b("hoeStone");
        Item.IRON_HOE = new ItemHoe(36, EnumToolMaterial.IRON).b("hoeIron");
        Item.DIAMOND_HOE = new ItemHoe(37, EnumToolMaterial.DIAMOND).b("hoeDiamond");
        Item.GOLD_HOE = new ItemHoe(38, EnumToolMaterial.GOLD).b("hoeGold");
        Item.SEEDS = new ItemSeeds(39, Block.CROPS.id, Block.SOIL.id).b("seeds");
        Item.WHEAT = new Item(40).b("wheat").a(CreativeModeTab.l);
        Item.BREAD = new ItemFood(41, 5, 0.6f, false).b("bread");
        Item.LEATHER_HELMET = (ItemArmor)new ItemArmor(42, EnumArmorMaterial.CLOTH, 0, 0).b("helmetCloth");
        Item.LEATHER_CHESTPLATE = (ItemArmor)new ItemArmor(43, EnumArmorMaterial.CLOTH, 0, 1).b("chestplateCloth");
        Item.LEATHER_LEGGINGS = (ItemArmor)new ItemArmor(44, EnumArmorMaterial.CLOTH, 0, 2).b("leggingsCloth");
        Item.LEATHER_BOOTS = (ItemArmor)new ItemArmor(45, EnumArmorMaterial.CLOTH, 0, 3).b("bootsCloth");
        Item.CHAINMAIL_HELMET = (ItemArmor)new ItemArmor(46, EnumArmorMaterial.CHAIN, 1, 0).b("helmetChain");
        Item.CHAINMAIL_CHESTPLATE = (ItemArmor)new ItemArmor(47, EnumArmorMaterial.CHAIN, 1, 1).b("chestplateChain");
        Item.CHAINMAIL_LEGGINGS = (ItemArmor)new ItemArmor(48, EnumArmorMaterial.CHAIN, 1, 2).b("leggingsChain");
        Item.CHAINMAIL_BOOTS = (ItemArmor)new ItemArmor(49, EnumArmorMaterial.CHAIN, 1, 3).b("bootsChain");
        Item.IRON_HELMET = (ItemArmor)new ItemArmor(50, EnumArmorMaterial.IRON, 2, 0).b("helmetIron");
        Item.IRON_CHESTPLATE = (ItemArmor)new ItemArmor(51, EnumArmorMaterial.IRON, 2, 1).b("chestplateIron");
        Item.IRON_LEGGINGS = (ItemArmor)new ItemArmor(52, EnumArmorMaterial.IRON, 2, 2).b("leggingsIron");
        Item.IRON_BOOTS = (ItemArmor)new ItemArmor(53, EnumArmorMaterial.IRON, 2, 3).b("bootsIron");
        Item.DIAMOND_HELMET = (ItemArmor)new ItemArmor(54, EnumArmorMaterial.DIAMOND, 3, 0).b("helmetDiamond");
        Item.DIAMOND_CHESTPLATE = (ItemArmor)new ItemArmor(55, EnumArmorMaterial.DIAMOND, 3, 1).b("chestplateDiamond");
        Item.DIAMOND_LEGGINGS = (ItemArmor)new ItemArmor(56, EnumArmorMaterial.DIAMOND, 3, 2).b("leggingsDiamond");
        Item.DIAMOND_BOOTS = (ItemArmor)new ItemArmor(57, EnumArmorMaterial.DIAMOND, 3, 3).b("bootsDiamond");
        Item.GOLD_HELMET = (ItemArmor)new ItemArmor(58, EnumArmorMaterial.GOLD, 4, 0).b("helmetGold");
        Item.GOLD_CHESTPLATE = (ItemArmor)new ItemArmor(59, EnumArmorMaterial.GOLD, 4, 1).b("chestplateGold");
        Item.GOLD_LEGGINGS = (ItemArmor)new ItemArmor(60, EnumArmorMaterial.GOLD, 4, 2).b("leggingsGold");
        Item.GOLD_BOOTS = (ItemArmor)new ItemArmor(61, EnumArmorMaterial.GOLD, 4, 3).b("bootsGold");
        Item.FLINT = new Item(62).b("flint").a(CreativeModeTab.l);
        Item.PORK = new ItemFood(63, 3, 0.3f, true).b("porkchopRaw");
        Item.GRILLED_PORK = new ItemFood(64, 8, 0.8f, true).b("porkchopCooked");
        Item.PAINTING = new ItemHanging(65, EntityPainting.class).b("painting");
        Item.GOLDEN_APPLE = new ItemGoldenApple(66, 4, 1.2f, false).j().a(MobEffectList.REGENERATION.id, 5, 0, 1.0f).b("appleGold");
        Item.SIGN = new ItemSign(67).b("sign");
        Item.WOOD_DOOR = new ItemDoor(68, Material.WOOD).b("doorWood");
        Item.BUCKET = new ItemBucket(69, 0).b("bucket").d(16);
        Item.WATER_BUCKET = new ItemBucket(70, Block.WATER.id).b("bucketWater").a(Item.BUCKET);
        Item.LAVA_BUCKET = new ItemBucket(71, Block.LAVA.id).b("bucketLava").a(Item.BUCKET);
        Item.MINECART = new ItemMinecart(72, 0).b("minecart");
        Item.SADDLE = new ItemSaddle(73).b("saddle");
        Item.IRON_DOOR = new ItemDoor(74, Material.ORE).b("doorIron");
        Item.REDSTONE = new ItemRedstone(75).b("redstone").c(PotionBrewer.i);
        Item.SNOW_BALL = new ItemSnowball(76).b("snowball");
        Item.BOAT = new ItemBoat(77).b("boat");
        Item.LEATHER = new Item(78).b("leather").a(CreativeModeTab.l);
        Item.MILK_BUCKET = new ItemMilkBucket(79).b("milk").a(Item.BUCKET);
        Item.CLAY_BRICK = new Item(80).b("brick").a(CreativeModeTab.l);
        Item.CLAY_BALL = new Item(81).b("clay").a(CreativeModeTab.l);
        Item.SUGAR_CANE = new ItemReed(82, Block.SUGAR_CANE_BLOCK).b("reeds").a(CreativeModeTab.l);
        Item.PAPER = new Item(83).b("paper").a(CreativeModeTab.f);
        Item.BOOK = new ItemBook(84).b("book").a(CreativeModeTab.f);
        Item.SLIME_BALL = new Item(85).b("slimeball").a(CreativeModeTab.f);
        Item.STORAGE_MINECART = new ItemMinecart(86, 1).b("minecartChest");
        Item.POWERED_MINECART = new ItemMinecart(87, 2).b("minecartFurnace");
        Item.EGG = new ItemEgg(88).b("egg");
        Item.COMPASS = new Item(89).b("compass").a(CreativeModeTab.i);
        Item.FISHING_ROD = (ItemFishingRod)new ItemFishingRod(90).b("fishingRod");
        Item.WATCH = new Item(91).b("clock").a(CreativeModeTab.i);
        Item.GLOWSTONE_DUST = new Item(92).b("yellowDust").c(PotionBrewer.j).a(CreativeModeTab.l);
        Item.RAW_FISH = new ItemFood(93, 2, 0.3f, false).b("fishRaw");
        Item.COOKED_FISH = new ItemFood(94, 5, 0.6f, false).b("fishCooked");
        Item.INK_SACK = new ItemDye(95).b("dyePowder");
        Item.BONE = new Item(96).b("bone").p().a(CreativeModeTab.f);
        Item.SUGAR = new Item(97).b("sugar").c(PotionBrewer.b).a(CreativeModeTab.l);
        Item.CAKE = new ItemReed(98, Block.CAKE_BLOCK).d(1).b("cake").a(CreativeModeTab.h);
        Item.BED = new ItemBed(99).d(1).b("bed");
        Item.DIODE = new ItemReed(100, Block.DIODE_OFF).b("diode").a(CreativeModeTab.d);
        Item.COOKIE = new ItemFood(101, 2, 0.1f, false).b("cookie");
        Item.MAP = (ItemWorldMap)new ItemWorldMap(102).b("map");
        Item.SHEARS = (ItemShears)new ItemShears(103).b("shears");
        Item.MELON = new ItemFood(104, 2, 0.3f, false).b("melon");
        Item.PUMPKIN_SEEDS = new ItemSeeds(105, Block.PUMPKIN_STEM.id, Block.SOIL.id).b("seeds_pumpkin");
        Item.MELON_SEEDS = new ItemSeeds(106, Block.MELON_STEM.id, Block.SOIL.id).b("seeds_melon");
        Item.RAW_BEEF = new ItemFood(107, 3, 0.3f, true).b("beefRaw");
        Item.COOKED_BEEF = new ItemFood(108, 8, 0.8f, true).b("beefCooked");
        Item.RAW_CHICKEN = new ItemFood(109, 2, 0.3f, true).a(MobEffectList.HUNGER.id, 30, 0, 0.3f).b("chickenRaw");
        Item.COOKED_CHICKEN = new ItemFood(110, 6, 0.6f, true).b("chickenCooked");
        Item.ROTTEN_FLESH = new ItemFood(111, 4, 0.1f, true).a(MobEffectList.HUNGER.id, 30, 0, 0.8f).b("rottenFlesh");
        Item.ENDER_PEARL = new ItemEnderPearl(112).b("enderPearl");
        Item.BLAZE_ROD = new Item(113).b("blazeRod").a(CreativeModeTab.l);
        Item.GHAST_TEAR = new Item(114).b("ghastTear").c(PotionBrewer.c).a(CreativeModeTab.k);
        Item.GOLD_NUGGET = new Item(115).b("goldNugget").a(CreativeModeTab.l);
        Item.NETHER_STALK = new ItemSeeds(116, Block.NETHER_WART.id, Block.SOUL_SAND.id).b("netherStalkSeeds").c("+4");
        Item.POTION = (ItemPotion)new ItemPotion(117).b("potion");
        Item.GLASS_BOTTLE = new ItemGlassBottle(118).b("glassBottle");
        Item.SPIDER_EYE = new ItemFood(119, 2, 0.8f, false).a(MobEffectList.POISON.id, 5, 0, 1.0f).b("spiderEye").c(PotionBrewer.d);
        Item.FERMENTED_SPIDER_EYE = new Item(120).b("fermentedSpiderEye").c(PotionBrewer.e).a(CreativeModeTab.k);
        Item.BLAZE_POWDER = new Item(121).b("blazePowder").c(PotionBrewer.g).a(CreativeModeTab.k);
        Item.MAGMA_CREAM = new Item(122).b("magmaCream").c(PotionBrewer.h).a(CreativeModeTab.k);
        Item.BREWING_STAND = new ItemReed(123, Block.BREWING_STAND).b("brewingStand").a(CreativeModeTab.k);
        Item.CAULDRON = new ItemReed(124, Block.CAULDRON).b("cauldron").a(CreativeModeTab.k);
        Item.EYE_OF_ENDER = new ItemEnderEye(125).b("eyeOfEnder");
        Item.SPECKLED_MELON = new Item(126).b("speckledMelon").c(PotionBrewer.f).a(CreativeModeTab.k);
        Item.MONSTER_EGG = new ItemMonsterEgg(127).b("monsterPlacer");
        Item.EXP_BOTTLE = new ItemExpBottle(128).b("expBottle");
        Item.FIREBALL = new ItemFireball(129).b("fireball");
        Item.BOOK_AND_QUILL = new ItemBookAndQuill(130).b("writingBook").a(CreativeModeTab.f);
        Item.WRITTEN_BOOK = new ItemWrittenBook(131).b("writtenBook");
        Item.EMERALD = new Item(132).b("emerald").a(CreativeModeTab.l);
        Item.ITEM_FRAME = new ItemHanging(133, EntityItemFrame.class).b("frame");
        Item.FLOWER_POT = new ItemReed(134, Block.FLOWER_POT).b("flowerPot").a(CreativeModeTab.c);
        Item.CARROT = new ItemSeedFood(135, 4, 0.6f, Block.CARROTS.id, Block.SOIL.id).b("carrots");
        Item.POTATO = new ItemSeedFood(136, 1, 0.3f, Block.POTATOES.id, Block.SOIL.id).b("potato");
        Item.POTATO_BAKED = new ItemFood(137, 6, 0.6f, false).b("potatoBaked");
        Item.POTATO_POISON = new ItemFood(138, 2, 0.3f, false).a(MobEffectList.POISON.id, 5, 0, 0.6f).b("potatoPoisonous");
        Item.MAP_EMPTY = (ItemMapEmpty)new ItemMapEmpty(139).b("emptyMap");
        Item.CARROT_GOLDEN = new ItemFood(140, 6, 1.2f, false).b("carrotGolden").c(PotionBrewer.l);
        Item.SKULL = new ItemSkull(141).b("skull");
        Item.CARROT_STICK = new ItemCarrotStick(142).b("carrotOnAStick");
        Item.NETHER_STAR = new ItemNetherStar(143).b("netherStar").a(CreativeModeTab.l);
        Item.PUMPKIN_PIE = new ItemFood(144, 8, 0.3f, false).b("pumpkinPie").a(CreativeModeTab.h);
        Item.FIREWORKS = new ItemFireworks(145).b("fireworks");
        Item.FIREWORKS_CHARGE = new ItemFireworksCharge(146).b("fireworksCharge").a(CreativeModeTab.f);
        Item.ENCHANTED_BOOK = (ItemEnchantedBook)new ItemEnchantedBook(147).d(1).b("enchantedBook");
        Item.REDSTONE_COMPARATOR = new ItemReed(148, Block.REDSTONE_COMPARATOR_OFF).b("comparator").a(CreativeModeTab.d);
        Item.NETHER_BRICK = new Item(149).b("netherbrick").a(CreativeModeTab.l);
        Item.QUARTZ = new Item(150).b("netherquartz").a(CreativeModeTab.l);
        Item.MINECART_TNT = new ItemMinecart(151, 3).b("minecartTnt");
        Item.MINECART_HOPPER = new ItemMinecart(152, 5).b("minecartHopper");
        Item.RECORD_1 = new ItemRecord(2000, "13").b("record");
        Item.RECORD_2 = new ItemRecord(2001, "cat").b("record");
        Item.RECORD_3 = new ItemRecord(2002, "blocks").b("record");
        Item.RECORD_4 = new ItemRecord(2003, "chirp").b("record");
        Item.RECORD_5 = new ItemRecord(2004, "far").b("record");
        Item.RECORD_6 = new ItemRecord(2005, "mall").b("record");
        Item.RECORD_7 = new ItemRecord(2006, "mellohi").b("record");
        Item.RECORD_8 = new ItemRecord(2007, "stal").b("record");
        Item.RECORD_9 = new ItemRecord(2008, "strad").b("record");
        Item.RECORD_10 = new ItemRecord(2009, "ward").b("record");
        Item.RECORD_11 = new ItemRecord(2010, "11").b("record");
        Item.RECORD_12 = new ItemRecord(2011, "wait").b("record");
        StatisticList.c();
    }
}
