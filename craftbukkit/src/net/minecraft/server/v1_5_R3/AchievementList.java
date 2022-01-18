// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.List;

public class AchievementList
{
    public static int a;
    public static int b;
    public static int c;
    public static int d;
    public static List e;
    public static Achievement f;
    public static Achievement g;
    public static Achievement h;
    public static Achievement i;
    public static Achievement j;
    public static Achievement k;
    public static Achievement l;
    public static Achievement m;
    public static Achievement n;
    public static Achievement o;
    public static Achievement p;
    public static Achievement q;
    public static Achievement r;
    public static Achievement s;
    public static Achievement t;
    public static Achievement u;
    public static Achievement v;
    public static Achievement w;
    public static Achievement x;
    public static Achievement y;
    public static Achievement z;
    public static Achievement A;
    public static Achievement B;
    public static Achievement C;
    public static Achievement D;
    public static Achievement E;
    public static Achievement F;
    
    public static void a() {
    }
    
    static {
        AchievementList.e = new ArrayList();
        AchievementList.f = new Achievement(0, "openInventory", 0, 0, Item.BOOK, null).a().c();
        AchievementList.g = new Achievement(1, "mineWood", 2, 1, Block.LOG, AchievementList.f).c();
        AchievementList.h = new Achievement(2, "buildWorkBench", 4, -1, Block.WORKBENCH, AchievementList.g).c();
        AchievementList.i = new Achievement(3, "buildPickaxe", 4, 2, Item.WOOD_PICKAXE, AchievementList.h).c();
        AchievementList.j = new Achievement(4, "buildFurnace", 3, 4, Block.FURNACE, AchievementList.i).c();
        AchievementList.k = new Achievement(5, "acquireIron", 1, 4, Item.IRON_INGOT, AchievementList.j).c();
        AchievementList.l = new Achievement(6, "buildHoe", 2, -3, Item.WOOD_HOE, AchievementList.h).c();
        AchievementList.m = new Achievement(7, "makeBread", -1, -3, Item.BREAD, AchievementList.l).c();
        AchievementList.n = new Achievement(8, "bakeCake", 0, -5, Item.CAKE, AchievementList.l).c();
        AchievementList.o = new Achievement(9, "buildBetterPickaxe", 6, 2, Item.STONE_PICKAXE, AchievementList.i).c();
        AchievementList.p = new Achievement(10, "cookFish", 2, 6, Item.COOKED_FISH, AchievementList.j).c();
        AchievementList.q = new Achievement(11, "onARail", 2, 3, Block.RAILS, AchievementList.k).b().c();
        AchievementList.r = new Achievement(12, "buildSword", 6, -1, Item.WOOD_SWORD, AchievementList.h).c();
        AchievementList.s = new Achievement(13, "killEnemy", 8, -1, Item.BONE, AchievementList.r).c();
        AchievementList.t = new Achievement(14, "killCow", 7, -3, Item.LEATHER, AchievementList.r).c();
        AchievementList.u = new Achievement(15, "flyPig", 8, -4, Item.SADDLE, AchievementList.t).b().c();
        AchievementList.v = new Achievement(16, "snipeSkeleton", 7, 0, Item.BOW, AchievementList.s).b().c();
        AchievementList.w = new Achievement(17, "diamonds", -1, 5, Item.DIAMOND, AchievementList.k).c();
        AchievementList.x = new Achievement(18, "portal", -1, 7, Block.OBSIDIAN, AchievementList.w).c();
        AchievementList.y = new Achievement(19, "ghast", -4, 8, Item.GHAST_TEAR, AchievementList.x).b().c();
        AchievementList.z = new Achievement(20, "blazeRod", 0, 9, Item.BLAZE_ROD, AchievementList.x).c();
        AchievementList.A = new Achievement(21, "potion", 2, 8, Item.POTION, AchievementList.z).c();
        AchievementList.B = new Achievement(22, "theEnd", 3, 10, Item.EYE_OF_ENDER, AchievementList.z).b().c();
        AchievementList.C = new Achievement(23, "theEnd2", 4, 13, Block.DRAGON_EGG, AchievementList.B).b().c();
        AchievementList.D = new Achievement(24, "enchantments", -4, 4, Block.ENCHANTMENT_TABLE, AchievementList.w).c();
        AchievementList.E = new Achievement(25, "overkill", -4, 1, Item.DIAMOND_SWORD, AchievementList.D).b().c();
        AchievementList.F = new Achievement(26, "bookcase", -3, 6, Block.BOOKSHELF, AchievementList.D).c();
        System.out.println(AchievementList.e.size() + " achievements");
    }
}
