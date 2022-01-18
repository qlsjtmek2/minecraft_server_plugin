// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class StatisticList
{
    protected static Map a;
    public static List b;
    public static List c;
    public static List d;
    public static List e;
    public static Statistic f;
    public static Statistic g;
    public static Statistic h;
    public static Statistic i;
    public static Statistic j;
    public static Statistic k;
    public static Statistic l;
    public static Statistic m;
    public static Statistic n;
    public static Statistic o;
    public static Statistic p;
    public static Statistic q;
    public static Statistic r;
    public static Statistic s;
    public static Statistic t;
    public static Statistic u;
    public static Statistic v;
    public static Statistic w;
    public static Statistic x;
    public static Statistic y;
    public static Statistic z;
    public static Statistic A;
    public static Statistic B;
    public static Statistic[] C;
    public static Statistic[] D;
    public static Statistic[] E;
    public static Statistic[] F;
    private static boolean G;
    private static boolean H;
    
    public static void a() {
    }
    
    public static void b() {
        StatisticList.E = a(StatisticList.E, "stat.useItem", 16908288, 0, 256);
        StatisticList.F = b(StatisticList.F, "stat.breakItem", 16973824, 0, 256);
        StatisticList.G = true;
        d();
    }
    
    public static void c() {
        StatisticList.E = a(StatisticList.E, "stat.useItem", 16908288, 256, 32000);
        StatisticList.F = b(StatisticList.F, "stat.breakItem", 16973824, 256, 32000);
        StatisticList.H = true;
        d();
    }
    
    public static void d() {
        if (!StatisticList.G || !StatisticList.H) {
            return;
        }
        final HashSet<Integer> set = new HashSet<Integer>();
        for (final IRecipe recipe : CraftingManager.getInstance().getRecipes()) {
            if (recipe.b() == null) {
                continue;
            }
            set.add(recipe.b().id);
        }
        final Iterator<ItemStack> iterator2 = RecipesFurnace.getInstance().getRecipes().values().iterator();
        while (iterator2.hasNext()) {
            set.add(iterator2.next().id);
        }
        StatisticList.D = new Statistic[32000];
        for (final Integer n : set) {
            if (Item.byId[n] != null) {
                StatisticList.D[n] = new CraftingStatistic(16842752 + n, LocaleI18n.get("stat.craftItem", Item.byId[n].u()), n).g();
            }
        }
        a(StatisticList.D);
    }
    
    private static Statistic[] a(final String s, final int n) {
        final Statistic[] array = new Statistic[256];
        for (int i = 0; i < 256; ++i) {
            if (Block.byId[i] != null && Block.byId[i].C()) {
                array[i] = new CraftingStatistic(n + i, LocaleI18n.get(s, Block.byId[i].getName()), i).g();
                StatisticList.e.add(array[i]);
            }
        }
        a(array);
        return array;
    }
    
    private static Statistic[] a(Statistic[] array, final String s, final int n, final int n2, final int n3) {
        if (array == null) {
            array = new Statistic[32000];
        }
        for (int i = n2; i < n3; ++i) {
            if (Item.byId[i] != null) {
                array[i] = new CraftingStatistic(n + i, LocaleI18n.get(s, Item.byId[i].u()), i).g();
                if (i >= 256) {
                    StatisticList.d.add(array[i]);
                }
            }
        }
        a(array);
        return array;
    }
    
    private static Statistic[] b(Statistic[] array, final String s, final int n, final int n2, final int n3) {
        if (array == null) {
            array = new Statistic[32000];
        }
        for (int i = n2; i < n3; ++i) {
            if (Item.byId[i] != null && Item.byId[i].usesDurability()) {
                array[i] = new CraftingStatistic(n + i, LocaleI18n.get(s, Item.byId[i].u()), i).g();
            }
        }
        a(array);
        return array;
    }
    
    private static void a(final Statistic[] array) {
        a(array, Block.STATIONARY_WATER.id, Block.WATER.id);
        a(array, Block.STATIONARY_LAVA.id, Block.STATIONARY_LAVA.id);
        a(array, Block.JACK_O_LANTERN.id, Block.PUMPKIN.id);
        a(array, Block.BURNING_FURNACE.id, Block.FURNACE.id);
        a(array, Block.GLOWING_REDSTONE_ORE.id, Block.REDSTONE_ORE.id);
        a(array, Block.DIODE_ON.id, Block.DIODE_OFF.id);
        a(array, Block.REDSTONE_TORCH_ON.id, Block.REDSTONE_TORCH_OFF.id);
        a(array, Block.RED_MUSHROOM.id, Block.BROWN_MUSHROOM.id);
        a(array, Block.DOUBLE_STEP.id, Block.STEP.id);
        a(array, Block.WOOD_DOUBLE_STEP.id, Block.WOOD_STEP.id);
        a(array, Block.GRASS.id, Block.DIRT.id);
        a(array, Block.SOIL.id, Block.DIRT.id);
    }
    
    private static void a(final Statistic[] array, final int n, final int n2) {
        if (array[n] != null && array[n2] == null) {
            array[n2] = array[n];
            return;
        }
        StatisticList.b.remove(array[n]);
        StatisticList.e.remove(array[n]);
        StatisticList.c.remove(array[n]);
        array[n] = array[n2];
    }
    
    static {
        StatisticList.a = new HashMap();
        StatisticList.b = new ArrayList();
        StatisticList.c = new ArrayList();
        StatisticList.d = new ArrayList();
        StatisticList.e = new ArrayList();
        StatisticList.f = new CounterStatistic(1000, "stat.startGame").h().g();
        StatisticList.g = new CounterStatistic(1001, "stat.createWorld").h().g();
        StatisticList.h = new CounterStatistic(1002, "stat.loadWorld").h().g();
        StatisticList.i = new CounterStatistic(1003, "stat.joinMultiplayer").h().g();
        StatisticList.j = new CounterStatistic(1004, "stat.leaveGame").h().g();
        StatisticList.k = new CounterStatistic(1100, "stat.playOneMinute", Statistic.i).h().g();
        StatisticList.l = new CounterStatistic(2000, "stat.walkOneCm", Statistic.j).h().g();
        StatisticList.m = new CounterStatistic(2001, "stat.swimOneCm", Statistic.j).h().g();
        StatisticList.n = new CounterStatistic(2002, "stat.fallOneCm", Statistic.j).h().g();
        StatisticList.o = new CounterStatistic(2003, "stat.climbOneCm", Statistic.j).h().g();
        StatisticList.p = new CounterStatistic(2004, "stat.flyOneCm", Statistic.j).h().g();
        StatisticList.q = new CounterStatistic(2005, "stat.diveOneCm", Statistic.j).h().g();
        StatisticList.r = new CounterStatistic(2006, "stat.minecartOneCm", Statistic.j).h().g();
        StatisticList.s = new CounterStatistic(2007, "stat.boatOneCm", Statistic.j).h().g();
        StatisticList.t = new CounterStatistic(2008, "stat.pigOneCm", Statistic.j).h().g();
        StatisticList.u = new CounterStatistic(2010, "stat.jump").h().g();
        StatisticList.v = new CounterStatistic(2011, "stat.drop").h().g();
        StatisticList.w = new CounterStatistic(2020, "stat.damageDealt").g();
        StatisticList.x = new CounterStatistic(2021, "stat.damageTaken").g();
        StatisticList.y = new CounterStatistic(2022, "stat.deaths").g();
        StatisticList.z = new CounterStatistic(2023, "stat.mobKills").g();
        StatisticList.A = new CounterStatistic(2024, "stat.playerKills").g();
        StatisticList.B = new CounterStatistic(2025, "stat.fishCaught").g();
        StatisticList.C = a("stat.mineBlock", 16777216);
        AchievementList.a();
        StatisticList.G = false;
        StatisticList.H = false;
    }
}
