// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import java.util.List;

public class Block
{
    private CreativeModeTab creativeTab;
    public static final StepSound f;
    public static final StepSound g;
    public static final StepSound h;
    public static final StepSound i;
    public static final StepSound j;
    public static final StepSound k;
    public static final StepSound l;
    public static final StepSound m;
    public static final StepSound n;
    public static final StepSound o;
    public static final StepSound p;
    public static final StepSound q;
    public static final Block[] byId;
    public static final boolean[] s;
    public static final int[] lightBlock;
    public static final boolean[] u;
    public static final int[] lightEmission;
    public static boolean[] w;
    public static final Block STONE;
    public static final BlockGrass GRASS;
    public static final Block DIRT;
    public static final Block COBBLESTONE;
    public static final Block WOOD;
    public static final Block SAPLING;
    public static final Block BEDROCK;
    public static final BlockFluids WATER;
    public static final Block STATIONARY_WATER;
    public static final BlockFluids LAVA;
    public static final Block STATIONARY_LAVA;
    public static final Block SAND;
    public static final Block GRAVEL;
    public static final Block GOLD_ORE;
    public static final Block IRON_ORE;
    public static final Block COAL_ORE;
    public static final Block LOG;
    public static final BlockLeaves LEAVES;
    public static final Block SPONGE;
    public static final Block GLASS;
    public static final Block LAPIS_ORE;
    public static final Block LAPIS_BLOCK;
    public static final Block DISPENSER;
    public static final Block SANDSTONE;
    public static final Block NOTE_BLOCK;
    public static final Block BED;
    public static final Block GOLDEN_RAIL;
    public static final Block DETECTOR_RAIL;
    public static final BlockPiston PISTON_STICKY;
    public static final Block WEB;
    public static final BlockLongGrass LONG_GRASS;
    public static final BlockDeadBush DEAD_BUSH;
    public static final BlockPiston PISTON;
    public static final BlockPistonExtension PISTON_EXTENSION;
    public static final Block WOOL;
    public static final BlockPistonMoving PISTON_MOVING;
    public static final BlockFlower YELLOW_FLOWER;
    public static final BlockFlower RED_ROSE;
    public static final BlockFlower BROWN_MUSHROOM;
    public static final BlockFlower RED_MUSHROOM;
    public static final Block GOLD_BLOCK;
    public static final Block IRON_BLOCK;
    public static final BlockStepAbstract DOUBLE_STEP;
    public static final BlockStepAbstract STEP;
    public static final Block BRICK;
    public static final Block TNT;
    public static final Block BOOKSHELF;
    public static final Block MOSSY_COBBLESTONE;
    public static final Block OBSIDIAN;
    public static final Block TORCH;
    public static final BlockFire FIRE;
    public static final Block MOB_SPAWNER;
    public static final Block WOOD_STAIRS;
    public static final BlockChest CHEST;
    public static final BlockRedstoneWire REDSTONE_WIRE;
    public static final Block DIAMOND_ORE;
    public static final Block DIAMOND_BLOCK;
    public static final Block WORKBENCH;
    public static final Block CROPS;
    public static final Block SOIL;
    public static final Block FURNACE;
    public static final Block BURNING_FURNACE;
    public static final Block SIGN_POST;
    public static final Block WOODEN_DOOR;
    public static final Block LADDER;
    public static final Block RAILS;
    public static final Block COBBLESTONE_STAIRS;
    public static final Block WALL_SIGN;
    public static final Block LEVER;
    public static final Block STONE_PLATE;
    public static final Block IRON_DOOR_BLOCK;
    public static final Block WOOD_PLATE;
    public static final Block REDSTONE_ORE;
    public static final Block GLOWING_REDSTONE_ORE;
    public static final Block REDSTONE_TORCH_OFF;
    public static final Block REDSTONE_TORCH_ON;
    public static final Block STONE_BUTTON;
    public static final Block SNOW;
    public static final Block ICE;
    public static final Block SNOW_BLOCK;
    public static final Block CACTUS;
    public static final Block CLAY;
    public static final Block SUGAR_CANE_BLOCK;
    public static final Block JUKEBOX;
    public static final Block FENCE;
    public static final Block PUMPKIN;
    public static final Block NETHERRACK;
    public static final Block SOUL_SAND;
    public static final Block GLOWSTONE;
    public static final BlockPortal PORTAL;
    public static final Block JACK_O_LANTERN;
    public static final Block CAKE_BLOCK;
    public static final BlockRepeater DIODE_OFF;
    public static final BlockRepeater DIODE_ON;
    public static final Block LOCKED_CHEST;
    public static final Block TRAP_DOOR;
    public static final Block MONSTER_EGGS;
    public static final Block SMOOTH_BRICK;
    public static final Block BIG_MUSHROOM_1;
    public static final Block BIG_MUSHROOM_2;
    public static final Block IRON_FENCE;
    public static final Block THIN_GLASS;
    public static final Block MELON;
    public static final Block PUMPKIN_STEM;
    public static final Block MELON_STEM;
    public static final Block VINE;
    public static final Block FENCE_GATE;
    public static final Block BRICK_STAIRS;
    public static final Block STONE_STAIRS;
    public static final BlockMycel MYCEL;
    public static final Block WATER_LILY;
    public static final Block NETHER_BRICK;
    public static final Block NETHER_FENCE;
    public static final Block NETHER_BRICK_STAIRS;
    public static final Block NETHER_WART;
    public static final Block ENCHANTMENT_TABLE;
    public static final Block BREWING_STAND;
    public static final BlockCauldron CAULDRON;
    public static final Block ENDER_PORTAL;
    public static final Block ENDER_PORTAL_FRAME;
    public static final Block WHITESTONE;
    public static final Block DRAGON_EGG;
    public static final Block REDSTONE_LAMP_OFF;
    public static final Block REDSTONE_LAMP_ON;
    public static final BlockStepAbstract WOOD_DOUBLE_STEP;
    public static final BlockStepAbstract WOOD_STEP;
    public static final Block COCOA;
    public static final Block SANDSTONE_STAIRS;
    public static final Block EMERALD_ORE;
    public static final Block ENDER_CHEST;
    public static final BlockTripwireHook TRIPWIRE_SOURCE;
    public static final Block TRIPWIRE;
    public static final Block EMERALD_BLOCK;
    public static final Block SPRUCE_WOOD_STAIRS;
    public static final Block BIRCH_WOOD_STAIRS;
    public static final Block JUNGLE_WOOD_STAIRS;
    public static final Block COMMAND;
    public static final BlockBeacon BEACON;
    public static final Block COBBLE_WALL;
    public static final Block FLOWER_POT;
    public static final Block CARROTS;
    public static final Block POTATOES;
    public static final Block WOOD_BUTTON;
    public static final Block SKULL;
    public static final Block ANVIL;
    public static final Block TRAPPED_CHEST;
    public static final Block GOLD_PLATE;
    public static final Block IRON_PLATE;
    public static final BlockRedstoneComparator REDSTONE_COMPARATOR_OFF;
    public static final BlockRedstoneComparator REDSTONE_COMPARATOR_ON;
    public static final BlockDaylightDetector DAYLIGHT_DETECTOR;
    public static final Block REDSTONE_BLOCK;
    public static final Block QUARTZ_ORE;
    public static final BlockHopper HOPPER;
    public static final Block QUARTZ_BLOCK;
    public static final Block QUARTZ_STAIRS;
    public static final Block ACTIVATOR_RAIL;
    public static final Block DROPPER;
    public final int id;
    protected float strength;
    protected float durability;
    protected boolean cC;
    protected boolean cD;
    protected boolean cE;
    protected boolean isTileEntity;
    protected double minX;
    protected double minY;
    protected double minZ;
    protected double maxX;
    protected double maxY;
    protected double maxZ;
    public StepSound stepSound;
    public float cN;
    public final Material material;
    public float frictionFactor;
    private String name;
    
    protected Block(final int i, final Material material) {
        this.cC = true;
        this.cD = true;
        this.stepSound = Block.f;
        this.cN = 1.0f;
        this.frictionFactor = 0.6f;
        if (Block.byId[i] != null) {
            throw new IllegalArgumentException("Slot " + i + " is already occupied by " + Block.byId[i] + " when adding " + this);
        }
        this.material = material;
        Block.byId[i] = this;
        this.id = i;
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        Block.s[i] = this.c();
        Block.lightBlock[i] = (this.c() ? 255 : 0);
        Block.u[i] = !material.blocksLight();
    }
    
    protected void s_() {
    }
    
    protected Block a(final StepSound stepsound) {
        this.stepSound = stepsound;
        return this;
    }
    
    protected Block k(final int i) {
        Block.lightBlock[this.id] = i;
        return this;
    }
    
    protected Block a(final float f) {
        Block.lightEmission[this.id] = (int)(15.0f * f);
        return this;
    }
    
    protected Block b(final float f) {
        this.durability = f * 3.0f;
        return this;
    }
    
    public static boolean l(final int i) {
        final Block block = Block.byId[i];
        return block != null && (block.material.k() && block.b() && !block.isPowerSource());
    }
    
    public boolean b() {
        return true;
    }
    
    public boolean b(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        return !this.material.isSolid();
    }
    
    public int d() {
        return 0;
    }
    
    protected Block c(final float f) {
        this.strength = f;
        if (this.durability < f * 5.0f) {
            this.durability = f * 5.0f;
        }
        return this;
    }
    
    protected Block r() {
        this.c(-1.0f);
        return this;
    }
    
    public float l(final World world, final int i, final int j, final int k) {
        return this.strength;
    }
    
    protected Block b(final boolean flag) {
        this.cE = flag;
        return this;
    }
    
    public boolean isTicking() {
        return this.cE;
    }
    
    public boolean t() {
        return this.isTileEntity;
    }
    
    protected final void a(final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.minX = f;
        this.minY = f1;
        this.minZ = f2;
        this.maxX = f3;
        this.maxY = f4;
        this.maxZ = f5;
    }
    
    public boolean a_(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        return iblockaccess.getMaterial(i, j, k).isBuildable();
    }
    
    public void a(final World world, final int i, final int j, final int k, final AxisAlignedBB axisalignedbb, final List list, final Entity entity) {
        final AxisAlignedBB axisalignedbb2 = this.b(world, i, j, k);
        if (axisalignedbb2 != null && axisalignedbb.a(axisalignedbb2)) {
            list.add(axisalignedbb2);
        }
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        return AxisAlignedBB.a().a(i + this.minX, j + this.minY, k + this.minZ, i + this.maxX, j + this.maxY, k + this.maxZ);
    }
    
    public boolean c() {
        return true;
    }
    
    public boolean a(final int i, final boolean flag) {
        return this.m();
    }
    
    public boolean m() {
        return true;
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
    }
    
    public void postBreak(final World world, final int i, final int j, final int k, final int l) {
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
    }
    
    public int a(final World world) {
        return 10;
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
    }
    
    public void remove(final World world, final int i, final int j, final int k, final int l, final int i1) {
    }
    
    public int a(final Random random) {
        return 1;
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return this.id;
    }
    
    public float getDamage(final EntityHuman entityhuman, final World world, final int i, final int j, final int k) {
        final float f = this.l(world, i, j, k);
        return (f < 0.0f) ? 0.0f : (entityhuman.a(this) ? (entityhuman.a(this, true) / f / 30.0f) : (entityhuman.a(this, false) / f / 100.0f));
    }
    
    public final void c(final World world, final int i, final int j, final int k, final int l, final int i1) {
        this.dropNaturally(world, i, j, k, l, 1.0f, i1);
    }
    
    public void dropNaturally(final World world, final int i, final int j, final int k, final int l, final float f, final int i1) {
        if (!world.isStatic) {
            for (int j2 = this.getDropCount(i1, world.random), k2 = 0; k2 < j2; ++k2) {
                if (world.random.nextFloat() < f) {
                    final int l2 = this.getDropType(l, world.random, i1);
                    if (l2 > 0) {
                        this.b(world, i, j, k, new ItemStack(l2, 1, this.getDropData(l)));
                    }
                }
            }
        }
    }
    
    protected void b(final World world, final int i, final int j, final int k, final ItemStack itemstack) {
        if (!world.isStatic && world.getGameRules().getBoolean("doTileDrops")) {
            final float f = 0.7f;
            final double d0 = world.random.nextFloat() * f + (1.0f - f) * 0.5;
            final double d2 = world.random.nextFloat() * f + (1.0f - f) * 0.5;
            final double d3 = world.random.nextFloat() * f + (1.0f - f) * 0.5;
            final EntityItem entityitem = new EntityItem(world, i + d0, j + d2, k + d3, itemstack);
            entityitem.pickupDelay = 10;
            world.addEntity(entityitem);
        }
    }
    
    protected void j(final World world, final int i, final int j, final int k, int l) {
        if (!world.isStatic) {
            while (l > 0) {
                final int i2 = EntityExperienceOrb.getOrbValue(l);
                l -= i2;
                world.addEntity(new EntityExperienceOrb(world, i + 0.5, j + 0.5, k + 0.5, i2));
            }
        }
    }
    
    public int getDropData(final int i) {
        return 0;
    }
    
    public float a(final Entity entity) {
        return this.durability / 5.0f;
    }
    
    public MovingObjectPosition a(final World world, final int i, final int j, final int k, Vec3D vec3d, Vec3D vec3d1) {
        this.updateShape(world, i, j, k);
        vec3d = vec3d.add(-i, -j, -k);
        vec3d1 = vec3d1.add(-i, -j, -k);
        Vec3D vec3d2 = vec3d.b(vec3d1, this.minX);
        Vec3D vec3d3 = vec3d.b(vec3d1, this.maxX);
        Vec3D vec3d4 = vec3d.c(vec3d1, this.minY);
        Vec3D vec3d5 = vec3d.c(vec3d1, this.maxY);
        Vec3D vec3d6 = vec3d.d(vec3d1, this.minZ);
        Vec3D vec3d7 = vec3d.d(vec3d1, this.maxZ);
        if (!this.a(vec3d2)) {
            vec3d2 = null;
        }
        if (!this.a(vec3d3)) {
            vec3d3 = null;
        }
        if (!this.b(vec3d4)) {
            vec3d4 = null;
        }
        if (!this.b(vec3d5)) {
            vec3d5 = null;
        }
        if (!this.c(vec3d6)) {
            vec3d6 = null;
        }
        if (!this.c(vec3d7)) {
            vec3d7 = null;
        }
        Vec3D vec3d8 = null;
        if (vec3d2 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d2) < vec3d.distanceSquared(vec3d8))) {
            vec3d8 = vec3d2;
        }
        if (vec3d3 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d3) < vec3d.distanceSquared(vec3d8))) {
            vec3d8 = vec3d3;
        }
        if (vec3d4 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d4) < vec3d.distanceSquared(vec3d8))) {
            vec3d8 = vec3d4;
        }
        if (vec3d5 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d5) < vec3d.distanceSquared(vec3d8))) {
            vec3d8 = vec3d5;
        }
        if (vec3d6 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d6) < vec3d.distanceSquared(vec3d8))) {
            vec3d8 = vec3d6;
        }
        if (vec3d7 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d7) < vec3d.distanceSquared(vec3d8))) {
            vec3d8 = vec3d7;
        }
        if (vec3d8 == null) {
            return null;
        }
        byte b0 = -1;
        if (vec3d8 == vec3d2) {
            b0 = 4;
        }
        if (vec3d8 == vec3d3) {
            b0 = 5;
        }
        if (vec3d8 == vec3d4) {
            b0 = 0;
        }
        if (vec3d8 == vec3d5) {
            b0 = 1;
        }
        if (vec3d8 == vec3d6) {
            b0 = 2;
        }
        if (vec3d8 == vec3d7) {
            b0 = 3;
        }
        return new MovingObjectPosition(i, j, k, b0, vec3d8.add(i, j, k));
    }
    
    private boolean a(final Vec3D vec3d) {
        return vec3d != null && (vec3d.d >= this.minY && vec3d.d <= this.maxY && vec3d.e >= this.minZ && vec3d.e <= this.maxZ);
    }
    
    private boolean b(final Vec3D vec3d) {
        return vec3d != null && (vec3d.c >= this.minX && vec3d.c <= this.maxX && vec3d.e >= this.minZ && vec3d.e <= this.maxZ);
    }
    
    private boolean c(final Vec3D vec3d) {
        return vec3d != null && (vec3d.c >= this.minX && vec3d.c <= this.maxX && vec3d.d >= this.minY && vec3d.d <= this.maxY);
    }
    
    public void wasExploded(final World world, final int i, final int j, final int k, final Explosion explosion) {
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k, final int l, final ItemStack itemstack) {
        return this.canPlace(world, i, j, k, l);
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k, final int l) {
        return this.canPlace(world, i, j, k);
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k) {
        final int l = world.getTypeId(i, j, k);
        return l == 0 || Block.byId[l].material.isReplaceable();
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityhuman, final int l, final float f, final float f1, final float f2) {
        return false;
    }
    
    public void b(final World world, final int i, final int j, final int k, final Entity entity) {
    }
    
    public int getPlacedData(final World world, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2, final int i1) {
        return i1;
    }
    
    public void attack(final World world, final int i, final int j, final int k, final EntityHuman entityhuman) {
    }
    
    public void a(final World world, final int i, final int j, final int k, final Entity entity, final Vec3D vec3d) {
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
    }
    
    public final double u() {
        return this.minX;
    }
    
    public final double v() {
        return this.maxX;
    }
    
    public final double w() {
        return this.minY;
    }
    
    public final double x() {
        return this.maxY;
    }
    
    public final double y() {
        return this.minZ;
    }
    
    public final double z() {
        return this.maxZ;
    }
    
    public int b(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        return 0;
    }
    
    public boolean isPowerSource() {
        return false;
    }
    
    public void a(final World world, final int i, final int j, final int k, final Entity entity) {
    }
    
    public int c(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        return 0;
    }
    
    public void g() {
    }
    
    public void a(final World world, final EntityHuman entityhuman, final int i, final int j, final int k, final int l) {
        entityhuman.a(StatisticList.C[this.id], 1);
        entityhuman.j(0.025f);
        if (this.r_() && EnchantmentManager.hasSilkTouchEnchantment(entityhuman)) {
            final ItemStack itemstack = this.c_(l);
            if (itemstack != null) {
                this.b(world, i, j, k, itemstack);
            }
        }
        else {
            final int i2 = EnchantmentManager.getBonusBlockLootEnchantmentLevel(entityhuman);
            this.c(world, i, j, k, l, i2);
        }
    }
    
    protected boolean r_() {
        return this.b() && !this.isTileEntity;
    }
    
    protected ItemStack c_(final int i) {
        int j = 0;
        if (this.id >= 0 && this.id < Item.byId.length && Item.byId[this.id].m()) {
            j = i;
        }
        return new ItemStack(this.id, 1, j);
    }
    
    public int getDropCount(final int i, final Random random) {
        return this.a(random);
    }
    
    public boolean f(final World world, final int i, final int j, final int k) {
        return true;
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final EntityLiving entityliving, final ItemStack itemstack) {
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final int l) {
    }
    
    public Block c(final String s) {
        this.name = s;
        return this;
    }
    
    public String getName() {
        return LocaleI18n.get(this.a() + ".name");
    }
    
    public String a() {
        return "tile." + this.name;
    }
    
    public boolean b(final World world, final int i, final int j, final int k, final int l, final int i1) {
        return false;
    }
    
    public boolean C() {
        return this.cD;
    }
    
    protected Block D() {
        this.cD = false;
        return this;
    }
    
    public int h() {
        return this.material.getPushReaction();
    }
    
    public void a(final World world, final int i, final int j, final int k, final Entity entity, final float f) {
    }
    
    public int getDropData(final World world, final int i, final int j, final int k) {
        return this.getDropData(world.getData(i, j, k));
    }
    
    public Block a(final CreativeModeTab creativemodetab) {
        this.creativeTab = creativemodetab;
        return this;
    }
    
    public void a(final World world, final int i, final int j, final int k, final int l, final EntityHuman entityhuman) {
    }
    
    public void l(final World world, final int i, final int j, final int k, final int l) {
    }
    
    public void g(final World world, final int i, final int j, final int k) {
    }
    
    public boolean l() {
        return true;
    }
    
    public boolean a(final Explosion explosion) {
        return true;
    }
    
    public boolean i(final int i) {
        return this.id == i;
    }
    
    public static boolean b(final int i, final int j) {
        return i == j || (i != 0 && j != 0 && Block.byId[i] != null && Block.byId[j] != null && Block.byId[i].i(j));
    }
    
    public boolean q_() {
        return false;
    }
    
    public int b_(final World world, final int i, final int j, final int k, final int l) {
        return 0;
    }
    
    public int getExpDrop(final World world, final int data, final int enchantmentLevel) {
        return 0;
    }
    
    public static float range(final float min, final float value, final float max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }
    
    static {
        f = new StepSound("stone", 1.0f, 1.0f);
        g = new StepSound("wood", 1.0f, 1.0f);
        h = new StepSound("gravel", 1.0f, 1.0f);
        i = new StepSound("grass", 1.0f, 1.0f);
        j = new StepSound("stone", 1.0f, 1.0f);
        k = new StepSound("stone", 1.0f, 1.5f);
        l = new StepSoundStone("stone", 1.0f, 1.0f);
        m = new StepSound("cloth", 1.0f, 1.0f);
        n = new StepSound("sand", 1.0f, 1.0f);
        o = new StepSound("snow", 1.0f, 1.0f);
        p = new StepSoundLadder("ladder", 1.0f, 1.0f);
        q = new StepSoundAnvil("anvil", 0.3f, 1.0f);
        byId = new Block[4096];
        s = new boolean[4096];
        lightBlock = new int[4096];
        u = new boolean[4096];
        lightEmission = new int[4096];
        Block.w = new boolean[4096];
        STONE = new BlockStone(1).c(1.5f).b(10.0f).a(Block.j).c("stone");
        GRASS = (BlockGrass)new BlockGrass(2).c(0.6f).a(Block.i).c("grass");
        DIRT = new BlockDirt(3).c(0.5f).a(Block.h).c("dirt");
        COBBLESTONE = new Block(4, Material.STONE).c(2.0f).b(10.0f).a(Block.j).c("stonebrick").a(CreativeModeTab.b);
        WOOD = new BlockWood(5).c(2.0f).b(5.0f).a(Block.g).c("wood");
        SAPLING = new BlockSapling(6).c(0.0f).a(Block.i).c("sapling");
        BEDROCK = new Block(7, Material.STONE).r().b(6000000.0f).a(Block.j).c("bedrock").D().a(CreativeModeTab.b);
        WATER = (BlockFluids)new BlockFlowing(8, Material.WATER).c(100.0f).k(3).c("water").D();
        STATIONARY_WATER = new BlockStationary(9, Material.WATER).c(100.0f).k(3).c("water").D();
        LAVA = (BlockFluids)new BlockFlowing(10, Material.LAVA).c(0.0f).a(1.0f).c("lava").D();
        STATIONARY_LAVA = new BlockStationary(11, Material.LAVA).c(100.0f).a(1.0f).c("lava").D();
        SAND = new BlockSand(12).c(0.5f).a(Block.n).c("sand");
        GRAVEL = new BlockGravel(13).c(0.6f).a(Block.h).c("gravel");
        GOLD_ORE = new BlockOre(14).c(3.0f).b(5.0f).a(Block.j).c("oreGold");
        IRON_ORE = new BlockOre(15).c(3.0f).b(5.0f).a(Block.j).c("oreIron");
        COAL_ORE = new BlockOre(16).c(3.0f).b(5.0f).a(Block.j).c("oreCoal");
        LOG = new BlockLog(17).c(2.0f).a(Block.g).c("log");
        LEAVES = (BlockLeaves)new BlockLeaves(18).c(0.2f).k(1).a(Block.i).c("leaves");
        SPONGE = new BlockSponge(19).c(0.6f).a(Block.i).c("sponge");
        GLASS = new BlockGlass(20, Material.SHATTERABLE, false).c(0.3f).a(Block.l).c("glass");
        LAPIS_ORE = new BlockOre(21).c(3.0f).b(5.0f).a(Block.j).c("oreLapis");
        LAPIS_BLOCK = new Block(22, Material.STONE).c(3.0f).b(5.0f).a(Block.j).c("blockLapis").a(CreativeModeTab.b);
        DISPENSER = new BlockDispenser(23).c(3.5f).a(Block.j).c("dispenser");
        SANDSTONE = new BlockSandStone(24).a(Block.j).c(0.8f).c("sandStone");
        NOTE_BLOCK = new BlockNote(25).c(0.8f).c("musicBlock");
        BED = new BlockBed(26).c(0.2f).c("bed").D();
        GOLDEN_RAIL = new BlockPoweredRail(27).c(0.7f).a(Block.k).c("goldenRail");
        DETECTOR_RAIL = new BlockMinecartDetector(28).c(0.7f).a(Block.k).c("detectorRail");
        PISTON_STICKY = (BlockPiston)new BlockPiston(29, true).c("pistonStickyBase");
        WEB = new BlockWeb(30).k(1).c(4.0f).c("web");
        LONG_GRASS = (BlockLongGrass)new BlockLongGrass(31).c(0.0f).a(Block.i).c("tallgrass");
        DEAD_BUSH = (BlockDeadBush)new BlockDeadBush(32).c(0.0f).a(Block.i).c("deadbush");
        PISTON = (BlockPiston)new BlockPiston(33, false).c("pistonBase");
        PISTON_EXTENSION = new BlockPistonExtension(34);
        WOOL = new BlockCloth().c(0.8f).a(Block.m).c("cloth");
        PISTON_MOVING = new BlockPistonMoving(36);
        YELLOW_FLOWER = (BlockFlower)new BlockFlower(37).c(0.0f).a(Block.i).c("flower");
        RED_ROSE = (BlockFlower)new BlockFlower(38).c(0.0f).a(Block.i).c("rose");
        BROWN_MUSHROOM = (BlockFlower)new BlockMushroom(39, "mushroom_brown").c(0.0f).a(Block.i).a(0.125f).c("mushroom");
        RED_MUSHROOM = (BlockFlower)new BlockMushroom(40, "mushroom_red").c(0.0f).a(Block.i).c("mushroom");
        GOLD_BLOCK = new BlockOreBlock(41).c(3.0f).b(10.0f).a(Block.k).c("blockGold");
        IRON_BLOCK = new BlockOreBlock(42).c(5.0f).b(10.0f).a(Block.k).c("blockIron");
        DOUBLE_STEP = (BlockStepAbstract)new BlockStep(43, true).c(2.0f).b(10.0f).a(Block.j).c("stoneSlab");
        STEP = (BlockStepAbstract)new BlockStep(44, false).c(2.0f).b(10.0f).a(Block.j).c("stoneSlab");
        BRICK = new Block(45, Material.STONE).c(2.0f).b(10.0f).a(Block.j).c("brick").a(CreativeModeTab.b);
        TNT = new BlockTNT(46).c(0.0f).a(Block.i).c("tnt");
        BOOKSHELF = new BlockBookshelf(47).c(1.5f).a(Block.g).c("bookshelf");
        MOSSY_COBBLESTONE = new Block(48, Material.STONE).c(2.0f).b(10.0f).a(Block.j).c("stoneMoss").a(CreativeModeTab.b);
        OBSIDIAN = new BlockObsidian(49).c(50.0f).b(2000.0f).a(Block.j).c("obsidian");
        TORCH = new BlockTorch(50).c(0.0f).a(0.9375f).a(Block.g).c("torch");
        FIRE = (BlockFire)new BlockFire(51).c(0.0f).a(1.0f).a(Block.g).c("fire").D();
        MOB_SPAWNER = new BlockMobSpawner(52).c(5.0f).a(Block.k).c("mobSpawner").D();
        WOOD_STAIRS = new BlockStairs(53, Block.WOOD, 0).c("stairsWood");
        CHEST = (BlockChest)new BlockChest(54, 0).c(2.5f).a(Block.g).c("chest");
        REDSTONE_WIRE = (BlockRedstoneWire)new BlockRedstoneWire(55).c(0.0f).a(Block.f).c("redstoneDust").D();
        DIAMOND_ORE = new BlockOre(56).c(3.0f).b(5.0f).a(Block.j).c("oreDiamond");
        DIAMOND_BLOCK = new BlockOreBlock(57).c(5.0f).b(10.0f).a(Block.k).c("blockDiamond");
        WORKBENCH = new BlockWorkbench(58).c(2.5f).a(Block.g).c("workbench");
        CROPS = new BlockCrops(59).c("crops");
        SOIL = new BlockSoil(60).c(0.6f).a(Block.h).c("farmland");
        FURNACE = new BlockFurnace(61, false).c(3.5f).a(Block.j).c("furnace").a(CreativeModeTab.c);
        BURNING_FURNACE = new BlockFurnace(62, true).c(3.5f).a(Block.j).a(0.875f).c("furnace");
        SIGN_POST = new BlockSign(63, TileEntitySign.class, true).c(1.0f).a(Block.g).c("sign").D();
        WOODEN_DOOR = new BlockDoor(64, Material.WOOD).c(3.0f).a(Block.g).c("doorWood").D();
        LADDER = new BlockLadder(65).c(0.4f).a(Block.p).c("ladder");
        RAILS = new BlockMinecartTrack(66).c(0.7f).a(Block.k).c("rail");
        COBBLESTONE_STAIRS = new BlockStairs(67, Block.COBBLESTONE, 0).c("stairsStone");
        WALL_SIGN = new BlockSign(68, TileEntitySign.class, false).c(1.0f).a(Block.g).c("sign").D();
        LEVER = new BlockLever(69).c(0.5f).a(Block.g).c("lever");
        STONE_PLATE = new BlockPressurePlateBinary(70, "stone", Material.STONE, EnumMobType.MOBS).c(0.5f).a(Block.j).c("pressurePlate");
        IRON_DOOR_BLOCK = new BlockDoor(71, Material.ORE).c(5.0f).a(Block.k).c("doorIron").D();
        WOOD_PLATE = new BlockPressurePlateBinary(72, "wood", Material.WOOD, EnumMobType.EVERYTHING).c(0.5f).a(Block.g).c("pressurePlate");
        REDSTONE_ORE = new BlockRedstoneOre(73, false).c(3.0f).b(5.0f).a(Block.j).c("oreRedstone").a(CreativeModeTab.b);
        GLOWING_REDSTONE_ORE = new BlockRedstoneOre(74, true).a(0.625f).c(3.0f).b(5.0f).a(Block.j).c("oreRedstone");
        REDSTONE_TORCH_OFF = new BlockRedstoneTorch(75, false).c(0.0f).a(Block.g).c("notGate");
        REDSTONE_TORCH_ON = new BlockRedstoneTorch(76, true).c(0.0f).a(0.5f).a(Block.g).c("notGate").a(CreativeModeTab.d);
        STONE_BUTTON = new BlockStoneButton(77).c(0.5f).a(Block.j).c("button");
        SNOW = new BlockSnow(78).c(0.1f).a(Block.o).c("snow").k(0);
        ICE = new BlockIce(79).c(0.5f).k(3).a(Block.l).c("ice");
        SNOW_BLOCK = new BlockSnowBlock(80).c(0.2f).a(Block.o).c("snow");
        CACTUS = new BlockCactus(81).c(0.4f).a(Block.m).c("cactus");
        CLAY = new BlockClay(82).c(0.6f).a(Block.h).c("clay");
        SUGAR_CANE_BLOCK = new BlockReed(83).c(0.0f).a(Block.i).c("reeds").D();
        JUKEBOX = new BlockJukeBox(84).c(2.0f).b(10.0f).a(Block.j).c("jukebox");
        FENCE = new BlockFence(85, "wood", Material.WOOD).c(2.0f).b(5.0f).a(Block.g).c("fence");
        PUMPKIN = new BlockPumpkin(86, false).c(1.0f).a(Block.g).c("pumpkin");
        NETHERRACK = new BlockBloodStone(87).c(0.4f).a(Block.j).c("hellrock");
        SOUL_SAND = new BlockSlowSand(88).c(0.5f).a(Block.n).c("hellsand");
        GLOWSTONE = new BlockLightStone(89, Material.SHATTERABLE).c(0.3f).a(Block.l).a(1.0f).c("lightgem");
        PORTAL = (BlockPortal)new BlockPortal(90).c(-1.0f).a(Block.l).a(0.75f).c("portal");
        JACK_O_LANTERN = new BlockPumpkin(91, true).c(1.0f).a(Block.g).a(1.0f).c("litpumpkin");
        CAKE_BLOCK = new BlockCake(92).c(0.5f).a(Block.m).c("cake").D();
        DIODE_OFF = (BlockRepeater)new BlockRepeater(93, false).c(0.0f).a(Block.g).c("diode").D();
        DIODE_ON = (BlockRepeater)new BlockRepeater(94, true).c(0.0f).a(0.625f).a(Block.g).c("diode").D();
        LOCKED_CHEST = new BlockLockedChest(95).c(0.0f).a(1.0f).a(Block.g).c("lockedchest").b(true);
        TRAP_DOOR = new BlockTrapdoor(96, Material.WOOD).c(3.0f).a(Block.g).c("trapdoor").D();
        MONSTER_EGGS = new BlockMonsterEggs(97).c(0.75f).c("monsterStoneEgg");
        SMOOTH_BRICK = new BlockSmoothBrick(98).c(1.5f).b(10.0f).a(Block.j).c("stonebricksmooth");
        BIG_MUSHROOM_1 = new BlockHugeMushroom(99, Material.WOOD, 0).c(0.2f).a(Block.g).c("mushroom");
        BIG_MUSHROOM_2 = new BlockHugeMushroom(100, Material.WOOD, 1).c(0.2f).a(Block.g).c("mushroom");
        IRON_FENCE = new BlockThinFence(101, "fenceIron", "fenceIron", Material.ORE, true).c(5.0f).b(10.0f).a(Block.k).c("fenceIron");
        THIN_GLASS = new BlockThinFence(102, "glass", "thinglass_top", Material.SHATTERABLE, false).c(0.3f).a(Block.l).c("thinGlass");
        MELON = new BlockMelon(103).c(1.0f).a(Block.g).c("melon");
        PUMPKIN_STEM = new BlockStem(104, Block.PUMPKIN).c(0.0f).a(Block.g).c("pumpkinStem");
        MELON_STEM = new BlockStem(105, Block.MELON).c(0.0f).a(Block.g).c("pumpkinStem");
        VINE = new BlockVine(106).c(0.2f).a(Block.i).c("vine");
        FENCE_GATE = new BlockFenceGate(107).c(2.0f).b(5.0f).a(Block.g).c("fenceGate");
        BRICK_STAIRS = new BlockStairs(108, Block.BRICK, 0).c("stairsBrick");
        STONE_STAIRS = new BlockStairs(109, Block.SMOOTH_BRICK, 0).c("stairsStoneBrickSmooth");
        MYCEL = (BlockMycel)new BlockMycel(110).c(0.6f).a(Block.i).c("mycel");
        WATER_LILY = new BlockWaterLily(111).c(0.0f).a(Block.i).c("waterlily");
        NETHER_BRICK = new Block(112, Material.STONE).c(2.0f).b(10.0f).a(Block.j).c("netherBrick").a(CreativeModeTab.b);
        NETHER_FENCE = new BlockFence(113, "netherBrick", Material.STONE).c(2.0f).b(10.0f).a(Block.j).c("netherFence");
        NETHER_BRICK_STAIRS = new BlockStairs(114, Block.NETHER_BRICK, 0).c("stairsNetherBrick");
        NETHER_WART = new BlockNetherWart(115).c("netherStalk");
        ENCHANTMENT_TABLE = new BlockEnchantmentTable(116).c(5.0f).b(2000.0f).c("enchantmentTable");
        BREWING_STAND = new BlockBrewingStand(117).c(0.5f).a(0.125f).c("brewingStand");
        CAULDRON = (BlockCauldron)new BlockCauldron(118).c(2.0f).c("cauldron");
        ENDER_PORTAL = new BlockEnderPortal(119, Material.PORTAL).c(-1.0f).b(6000000.0f);
        ENDER_PORTAL_FRAME = new BlockEnderPortalFrame(120).a(Block.l).a(0.125f).c(-1.0f).c("endPortalFrame").b(6000000.0f).a(CreativeModeTab.c);
        WHITESTONE = new Block(121, Material.STONE).c(3.0f).b(15.0f).a(Block.j).c("whiteStone").a(CreativeModeTab.b);
        DRAGON_EGG = new BlockDragonEgg(122).c(3.0f).b(15.0f).a(Block.j).a(0.125f).c("dragonEgg");
        REDSTONE_LAMP_OFF = new BlockRedstoneLamp(123, false).c(0.3f).a(Block.l).c("redstoneLight").a(CreativeModeTab.d);
        REDSTONE_LAMP_ON = new BlockRedstoneLamp(124, true).c(0.3f).a(Block.l).c("redstoneLight");
        WOOD_DOUBLE_STEP = (BlockStepAbstract)new BlockWoodStep(125, true).c(2.0f).b(5.0f).a(Block.g).c("woodSlab");
        WOOD_STEP = (BlockStepAbstract)new BlockWoodStep(126, false).c(2.0f).b(5.0f).a(Block.g).c("woodSlab");
        COCOA = new BlockCocoa(127).c(0.2f).b(5.0f).a(Block.g).c("cocoa");
        SANDSTONE_STAIRS = new BlockStairs(128, Block.SANDSTONE, 0).c("stairsSandStone");
        EMERALD_ORE = new BlockOre(129).c(3.0f).b(5.0f).a(Block.j).c("oreEmerald");
        ENDER_CHEST = new BlockEnderChest(130).c(22.5f).b(1000.0f).a(Block.j).c("enderChest").a(0.5f);
        TRIPWIRE_SOURCE = (BlockTripwireHook)new BlockTripwireHook(131).c("tripWireSource");
        TRIPWIRE = new BlockTripwire(132).c("tripWire");
        EMERALD_BLOCK = new BlockOreBlock(133).c(5.0f).b(10.0f).a(Block.k).c("blockEmerald");
        SPRUCE_WOOD_STAIRS = new BlockStairs(134, Block.WOOD, 1).c("stairsWoodSpruce");
        BIRCH_WOOD_STAIRS = new BlockStairs(135, Block.WOOD, 2).c("stairsWoodBirch");
        JUNGLE_WOOD_STAIRS = new BlockStairs(136, Block.WOOD, 3).c("stairsWoodJungle");
        COMMAND = new BlockCommand(137).c("commandBlock");
        BEACON = (BlockBeacon)new BlockBeacon(138).c("beacon").a(1.0f);
        COBBLE_WALL = new BlockCobbleWall(139, Block.COBBLESTONE).c("cobbleWall");
        FLOWER_POT = new BlockFlowerPot(140).c(0.0f).a(Block.f).c("flowerPot");
        CARROTS = new BlockCarrots(141).c("carrots");
        POTATOES = new BlockPotatoes(142).c("potatoes");
        WOOD_BUTTON = new BlockWoodButton(143).c(0.5f).a(Block.g).c("button");
        SKULL = new BlockSkull(144).c(1.0f).a(Block.j).c("skull");
        ANVIL = new BlockAnvil(145).c(5.0f).a(Block.q).b(2000.0f).c("anvil");
        TRAPPED_CHEST = new BlockChest(146, 1).c(2.5f).a(Block.g).c("chestTrap");
        GOLD_PLATE = new BlockPressurePlateWeighted(147, "blockGold", Material.ORE, 64).c(0.5f).a(Block.g).c("weightedPlate_light");
        IRON_PLATE = new BlockPressurePlateWeighted(148, "blockIron", Material.ORE, 640).c(0.5f).a(Block.g).c("weightedPlate_heavy");
        REDSTONE_COMPARATOR_OFF = (BlockRedstoneComparator)new BlockRedstoneComparator(149, false).c(0.0f).a(Block.g).c("comparator").D();
        REDSTONE_COMPARATOR_ON = (BlockRedstoneComparator)new BlockRedstoneComparator(150, true).c(0.0f).a(0.625f).a(Block.g).c("comparator").D();
        DAYLIGHT_DETECTOR = (BlockDaylightDetector)new BlockDaylightDetector(151).c(0.2f).a(Block.g).c("daylightDetector");
        REDSTONE_BLOCK = new BlockRedstone(152).c(5.0f).b(10.0f).a(Block.k).c("blockRedstone");
        QUARTZ_ORE = new BlockOre(153).c(3.0f).b(5.0f).a(Block.j).c("netherquartz");
        HOPPER = (BlockHopper)new BlockHopper(154).c(3.0f).b(8.0f).a(Block.g).c("hopper");
        QUARTZ_BLOCK = new BlockQuartz(155).a(Block.j).c(0.8f).c("quartzBlock");
        QUARTZ_STAIRS = new BlockStairs(156, Block.QUARTZ_BLOCK, 0).c("stairsQuartz");
        ACTIVATOR_RAIL = new BlockPoweredRail(157).c(0.7f).a(Block.k).c("activatorRail");
        DROPPER = new BlockDropper(158).c(3.5f).a(Block.j).c("dropper");
        Item.byId[Block.WOOL.id] = new ItemCloth(Block.WOOL.id - 256).b("cloth");
        Item.byId[Block.LOG.id] = new ItemMultiTexture(Block.LOG.id - 256, Block.LOG, BlockLog.a).b("log");
        Item.byId[Block.WOOD.id] = new ItemMultiTexture(Block.WOOD.id - 256, Block.WOOD, BlockWood.a).b("wood");
        Item.byId[Block.MONSTER_EGGS.id] = new ItemMultiTexture(Block.MONSTER_EGGS.id - 256, Block.MONSTER_EGGS, BlockMonsterEggs.a).b("monsterStoneEgg");
        Item.byId[Block.SMOOTH_BRICK.id] = new ItemMultiTexture(Block.SMOOTH_BRICK.id - 256, Block.SMOOTH_BRICK, BlockSmoothBrick.a).b("stonebricksmooth");
        Item.byId[Block.SANDSTONE.id] = new ItemMultiTexture(Block.SANDSTONE.id - 256, Block.SANDSTONE, BlockSandStone.a).b("sandStone");
        Item.byId[Block.QUARTZ_BLOCK.id] = new ItemMultiTexture(Block.QUARTZ_BLOCK.id - 256, Block.QUARTZ_BLOCK, BlockQuartz.a).b("quartzBlock");
        Item.byId[Block.STEP.id] = new ItemStep(Block.STEP.id - 256, Block.STEP, Block.DOUBLE_STEP, false).b("stoneSlab");
        Item.byId[Block.DOUBLE_STEP.id] = new ItemStep(Block.DOUBLE_STEP.id - 256, Block.STEP, Block.DOUBLE_STEP, true).b("stoneSlab");
        Item.byId[Block.WOOD_STEP.id] = new ItemStep(Block.WOOD_STEP.id - 256, Block.WOOD_STEP, Block.WOOD_DOUBLE_STEP, false).b("woodSlab");
        Item.byId[Block.WOOD_DOUBLE_STEP.id] = new ItemStep(Block.WOOD_DOUBLE_STEP.id - 256, Block.WOOD_STEP, Block.WOOD_DOUBLE_STEP, true).b("woodSlab");
        Item.byId[Block.SAPLING.id] = new ItemMultiTexture(Block.SAPLING.id - 256, Block.SAPLING, BlockSapling.a).b("sapling");
        Item.byId[Block.LEAVES.id] = new ItemLeaves(Block.LEAVES.id - 256).b("leaves");
        Item.byId[Block.VINE.id] = new ItemWithAuxData(Block.VINE.id - 256, false);
        Item.byId[Block.LONG_GRASS.id] = new ItemWithAuxData(Block.LONG_GRASS.id - 256, true).a(new String[] { "shrub", "grass", "fern" });
        Item.byId[Block.SNOW.id] = new ItemSnow(Block.SNOW.id - 256, Block.SNOW);
        Item.byId[Block.WATER_LILY.id] = new ItemWaterLily(Block.WATER_LILY.id - 256);
        Item.byId[Block.PISTON.id] = new ItemPiston(Block.PISTON.id - 256);
        Item.byId[Block.PISTON_STICKY.id] = new ItemPiston(Block.PISTON_STICKY.id - 256);
        Item.byId[Block.COBBLE_WALL.id] = new ItemMultiTexture(Block.COBBLE_WALL.id - 256, Block.COBBLE_WALL, BlockCobbleWall.a).b("cobbleWall");
        Item.byId[Block.ANVIL.id] = new ItemAnvil(Block.ANVIL).b("anvil");
        Item.byId[Block.BIG_MUSHROOM_1.id] = new ItemWithAuxData(Block.BIG_MUSHROOM_1.id - 256, true);
        Item.byId[Block.BIG_MUSHROOM_2.id] = new ItemWithAuxData(Block.BIG_MUSHROOM_2.id - 256, true);
        Item.byId[Block.MOB_SPAWNER.id] = new ItemWithAuxData(Block.MOB_SPAWNER.id - 256, true);
        for (int i2 = 0; i2 < 256; ++i2) {
            if (Block.byId[i2] != null) {
                if (Item.byId[i2] == null) {
                    Item.byId[i2] = new ItemBlock(i2 - 256);
                    Block.byId[i2].s_();
                }
                boolean flag = false;
                if (i2 > 0 && Block.byId[i2].d() == 10) {
                    flag = true;
                }
                if (i2 > 0 && Block.byId[i2] instanceof BlockStepAbstract) {
                    flag = true;
                }
                if (i2 == Block.SOIL.id) {
                    flag = true;
                }
                if (Block.u[i2]) {
                    flag = true;
                }
                if (Block.lightBlock[i2] == 0) {
                    flag = true;
                }
                Block.w[i2] = flag;
            }
        }
        Block.u[0] = true;
        StatisticList.b();
    }
}
