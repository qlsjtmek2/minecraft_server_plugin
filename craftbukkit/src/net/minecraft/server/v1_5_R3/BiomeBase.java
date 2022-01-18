// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public abstract class BiomeBase
{
    public static final BiomeBase[] biomes;
    public static final BiomeBase OCEAN;
    public static final BiomeBase PLAINS;
    public static final BiomeBase DESERT;
    public static final BiomeBase EXTREME_HILLS;
    public static final BiomeBase FOREST;
    public static final BiomeBase TAIGA;
    public static final BiomeBase SWAMPLAND;
    public static final BiomeBase RIVER;
    public static final BiomeBase HELL;
    public static final BiomeBase SKY;
    public static final BiomeBase FROZEN_OCEAN;
    public static final BiomeBase FROZEN_RIVER;
    public static final BiomeBase ICE_PLAINS;
    public static final BiomeBase ICE_MOUNTAINS;
    public static final BiomeBase MUSHROOM_ISLAND;
    public static final BiomeBase MUSHROOM_SHORE;
    public static final BiomeBase BEACH;
    public static final BiomeBase DESERT_HILLS;
    public static final BiomeBase FOREST_HILLS;
    public static final BiomeBase TAIGA_HILLS;
    public static final BiomeBase SMALL_MOUNTAINS;
    public static final BiomeBase JUNGLE;
    public static final BiomeBase JUNGLE_HILLS;
    public String y;
    public int z;
    public byte A;
    public byte B;
    public int C;
    public float D;
    public float E;
    public float temperature;
    public float humidity;
    public int H;
    public BiomeDecorator I;
    protected List J;
    protected List K;
    protected List L;
    protected List M;
    private boolean S;
    private boolean T;
    public final int id;
    protected WorldGenTrees O;
    protected WorldGenBigTree P;
    protected WorldGenForest Q;
    protected WorldGenSwampTree R;
    
    protected BiomeBase(final int id) {
        this.A = (byte)Block.GRASS.id;
        this.B = (byte)Block.DIRT.id;
        this.C = 5169201;
        this.D = 0.1f;
        this.E = 0.3f;
        this.temperature = 0.5f;
        this.humidity = 0.5f;
        this.H = 16777215;
        this.J = new ArrayList();
        this.K = new ArrayList();
        this.L = new ArrayList();
        this.M = new ArrayList();
        this.T = true;
        this.O = new WorldGenTrees(false);
        this.P = new WorldGenBigTree(false);
        this.Q = new WorldGenForest(false);
        this.R = new WorldGenSwampTree();
        this.id = id;
        BiomeBase.biomes[id] = this;
        this.I = this.a();
        this.K.add(new BiomeMeta(EntitySheep.class, 12, 4, 4));
        this.K.add(new BiomeMeta(EntityPig.class, 10, 4, 4));
        this.K.add(new BiomeMeta(EntityChicken.class, 10, 4, 4));
        this.K.add(new BiomeMeta(EntityCow.class, 8, 4, 4));
        this.J.add(new BiomeMeta(EntitySpider.class, 10, 4, 4));
        this.J.add(new BiomeMeta(EntityZombie.class, 10, 4, 4));
        this.J.add(new BiomeMeta(EntitySkeleton.class, 10, 4, 4));
        this.J.add(new BiomeMeta(EntityCreeper.class, 10, 4, 4));
        this.J.add(new BiomeMeta(EntitySlime.class, 10, 4, 4));
        this.J.add(new BiomeMeta(EntityEnderman.class, 1, 1, 4));
        this.L.add(new BiomeMeta(EntitySquid.class, 10, 4, 4));
        this.M.add(new BiomeMeta(EntityBat.class, 10, 8, 8));
    }
    
    protected BiomeDecorator a() {
        return new BiomeDecorator(this);
    }
    
    private BiomeBase a(final float temperature, final float humidity) {
        if (temperature > 0.1f && temperature < 0.2f) {
            throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
        }
        this.temperature = temperature;
        this.humidity = humidity;
        return this;
    }
    
    private BiomeBase b(final float d, final float e) {
        this.D = d;
        this.E = e;
        return this;
    }
    
    private BiomeBase m() {
        this.T = false;
        return this;
    }
    
    public WorldGenerator a(final Random random) {
        if (random.nextInt(10) == 0) {
            return this.P;
        }
        return this.O;
    }
    
    public WorldGenerator b(final Random random) {
        return new WorldGenGrass(Block.LONG_GRASS.id, 1);
    }
    
    protected BiomeBase b() {
        this.S = true;
        return this;
    }
    
    protected BiomeBase a(final String y) {
        this.y = y;
        return this;
    }
    
    protected BiomeBase a(final int c) {
        this.C = c;
        return this;
    }
    
    protected BiomeBase b(final int z) {
        this.z = z;
        return this;
    }
    
    public List getMobs(final EnumCreatureType enumCreatureType) {
        if (enumCreatureType == EnumCreatureType.MONSTER) {
            return this.J;
        }
        if (enumCreatureType == EnumCreatureType.CREATURE) {
            return this.K;
        }
        if (enumCreatureType == EnumCreatureType.WATER_CREATURE) {
            return this.L;
        }
        if (enumCreatureType == EnumCreatureType.AMBIENT) {
            return this.M;
        }
        return null;
    }
    
    public boolean c() {
        return this.S;
    }
    
    public boolean d() {
        return !this.S && this.T;
    }
    
    public boolean e() {
        return this.humidity > 0.85f;
    }
    
    public float f() {
        return 0.1f;
    }
    
    public final int g() {
        return (int)(this.humidity * 65536.0f);
    }
    
    public final int h() {
        return (int)(this.temperature * 65536.0f);
    }
    
    public final float j() {
        return this.temperature;
    }
    
    public void a(final World world, final Random random, final int n, final int n2) {
        this.I.a(world, random, n, n2);
    }
    
    static {
        biomes = new BiomeBase[256];
        OCEAN = new BiomeOcean(0).b(112).a("Ocean").b(-1.0f, 0.4f);
        PLAINS = new BiomePlains(1).b(9286496).a("Plains").a(0.8f, 0.4f);
        DESERT = new BiomeDesert(2).b(16421912).a("Desert").m().a(2.0f, 0.0f).b(0.1f, 0.2f);
        EXTREME_HILLS = new BiomeBigHills(3).b(6316128).a("Extreme Hills").b(0.3f, 1.5f).a(0.2f, 0.3f);
        FOREST = new BiomeForest(4).b(353825).a("Forest").a(5159473).a(0.7f, 0.8f);
        TAIGA = new BiomeTaiga(5).b(747097).a("Taiga").a(5159473).b().a(0.05f, 0.8f).b(0.1f, 0.4f);
        SWAMPLAND = new BiomeSwamp(6).b(522674).a("Swampland").a(9154376).b(-0.2f, 0.1f).a(0.8f, 0.9f);
        RIVER = new BiomeRiver(7).b(255).a("River").b(-0.5f, 0.0f);
        HELL = new BiomeHell(8).b(16711680).a("Hell").m().a(2.0f, 0.0f);
        SKY = new BiomeTheEnd(9).b(8421631).a("Sky").m();
        FROZEN_OCEAN = new BiomeOcean(10).b(9474208).a("FrozenOcean").b().b(-1.0f, 0.5f).a(0.0f, 0.5f);
        FROZEN_RIVER = new BiomeRiver(11).b(10526975).a("FrozenRiver").b().b(-0.5f, 0.0f).a(0.0f, 0.5f);
        ICE_PLAINS = new BiomeIcePlains(12).b(16777215).a("Ice Plains").b().a(0.0f, 0.5f);
        ICE_MOUNTAINS = new BiomeIcePlains(13).b(10526880).a("Ice Mountains").b().b(0.3f, 1.3f).a(0.0f, 0.5f);
        MUSHROOM_ISLAND = new BiomeMushrooms(14).b(16711935).a("MushroomIsland").a(0.9f, 1.0f).b(0.2f, 1.0f);
        MUSHROOM_SHORE = new BiomeMushrooms(15).b(10486015).a("MushroomIslandShore").a(0.9f, 1.0f).b(-1.0f, 0.1f);
        BEACH = new BiomeBeach(16).b(16440917).a("Beach").a(0.8f, 0.4f).b(0.0f, 0.1f);
        DESERT_HILLS = new BiomeDesert(17).b(13786898).a("DesertHills").m().a(2.0f, 0.0f).b(0.3f, 0.8f);
        FOREST_HILLS = new BiomeForest(18).b(2250012).a("ForestHills").a(5159473).a(0.7f, 0.8f).b(0.3f, 0.7f);
        TAIGA_HILLS = new BiomeTaiga(19).b(1456435).a("TaigaHills").b().a(5159473).a(0.05f, 0.8f).b(0.3f, 0.8f);
        SMALL_MOUNTAINS = new BiomeBigHills(20).b(7501978).a("Extreme Hills Edge").b(0.2f, 0.8f).a(0.2f, 0.3f);
        JUNGLE = new BiomeJungle(21).b(5470985).a("Jungle").a(5470985).a(1.2f, 0.9f).b(0.2f, 0.4f);
        JUNGLE_HILLS = new BiomeJungle(22).b(2900485).a("JungleHills").a(5470985).a(1.2f, 0.9f).b(1.8f, 0.5f);
    }
}
