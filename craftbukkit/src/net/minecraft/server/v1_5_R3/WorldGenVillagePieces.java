// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Random;

public class WorldGenVillagePieces
{
    public static ArrayList a(final Random random, final int n) {
        final ArrayList<WorldGenVillagePieceWeight> list = new ArrayList<WorldGenVillagePieceWeight>();
        list.add(new WorldGenVillagePieceWeight(WorldGenVillageHouse.class, 4, MathHelper.nextInt(random, 2 + n, 4 + n * 2)));
        list.add(new WorldGenVillagePieceWeight(WorldGenVillageTemple.class, 20, MathHelper.nextInt(random, 0 + n, 1 + n)));
        list.add(new WorldGenVillagePieceWeight(WorldGenVillageLibrary.class, 20, MathHelper.nextInt(random, 0 + n, 2 + n)));
        list.add(new WorldGenVillagePieceWeight(WorldGenVillageHut.class, 3, MathHelper.nextInt(random, 2 + n, 5 + n * 3)));
        list.add(new WorldGenVillagePieceWeight(WorldGenVillageButcher.class, 15, MathHelper.nextInt(random, 0 + n, 2 + n)));
        list.add(new WorldGenVillagePieceWeight(WorldGenVillageFarm2.class, 3, MathHelper.nextInt(random, 1 + n, 4 + n)));
        list.add(new WorldGenVillagePieceWeight(WorldGenVillageFarm.class, 3, MathHelper.nextInt(random, 2 + n, 4 + n * 2)));
        list.add(new WorldGenVillagePieceWeight(WorldGenVillageBlacksmith.class, 15, MathHelper.nextInt(random, 0, 1 + n)));
        list.add(new WorldGenVillagePieceWeight(WorldGenVillageHouse2.class, 8, MathHelper.nextInt(random, 0 + n, 3 + n * 2)));
        final Iterator<WorldGenVillagePieceWeight> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().d == 0) {
                iterator.remove();
            }
        }
        return list;
    }
    
    private static int a(final List list) {
        boolean b = false;
        int n = 0;
        for (final WorldGenVillagePieceWeight worldGenVillagePieceWeight : list) {
            if (worldGenVillagePieceWeight.d > 0 && worldGenVillagePieceWeight.c < worldGenVillagePieceWeight.d) {
                b = true;
            }
            n += worldGenVillagePieceWeight.b;
        }
        return b ? n : -1;
    }
    
    private static WorldGenVillagePiece a(final WorldGenVillageStartPiece worldGenVillageStartPiece, final WorldGenVillagePieceWeight worldGenVillagePieceWeight, final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final Class a = worldGenVillagePieceWeight.a;
        WorldGenVillagePiece worldGenVillagePiece = null;
        if (a == WorldGenVillageHouse.class) {
            worldGenVillagePiece = WorldGenVillageHouse.a(worldGenVillageStartPiece, list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenVillageTemple.class) {
            worldGenVillagePiece = WorldGenVillageTemple.a(worldGenVillageStartPiece, list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenVillageLibrary.class) {
            worldGenVillagePiece = WorldGenVillageLibrary.a(worldGenVillageStartPiece, list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenVillageHut.class) {
            worldGenVillagePiece = WorldGenVillageHut.a(worldGenVillageStartPiece, list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenVillageButcher.class) {
            worldGenVillagePiece = WorldGenVillageButcher.a(worldGenVillageStartPiece, list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenVillageFarm2.class) {
            worldGenVillagePiece = WorldGenVillageFarm2.a(worldGenVillageStartPiece, list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenVillageFarm.class) {
            worldGenVillagePiece = WorldGenVillageFarm.a(worldGenVillageStartPiece, list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenVillageBlacksmith.class) {
            worldGenVillagePiece = WorldGenVillageBlacksmith.a(worldGenVillageStartPiece, list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenVillageHouse2.class) {
            worldGenVillagePiece = WorldGenVillageHouse2.a(worldGenVillageStartPiece, list, random, n, n2, n3, n4, n5);
        }
        return worldGenVillagePiece;
    }
    
    private static WorldGenVillagePiece c(final WorldGenVillageStartPiece worldGenVillageStartPiece, final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final int a = a(worldGenVillageStartPiece.h);
        if (a <= 0) {
            return null;
        }
        int i = 0;
        while (i < 5) {
            ++i;
            int nextInt = random.nextInt(a);
            for (final WorldGenVillagePieceWeight d : worldGenVillageStartPiece.h) {
                nextInt -= d.b;
                if (nextInt < 0) {
                    if (!d.a(n5)) {
                        break;
                    }
                    if (d == worldGenVillageStartPiece.d && worldGenVillageStartPiece.h.size() > 1) {
                        break;
                    }
                    final WorldGenVillagePiece a2 = a(worldGenVillageStartPiece, d, list, random, n, n2, n3, n4, n5);
                    if (a2 != null) {
                        final WorldGenVillagePieceWeight worldGenVillagePieceWeight = d;
                        ++worldGenVillagePieceWeight.c;
                        worldGenVillageStartPiece.d = d;
                        if (!d.a()) {
                            worldGenVillageStartPiece.h.remove(d);
                        }
                        return a2;
                    }
                    continue;
                }
            }
        }
        final StructureBoundingBox a3 = WorldGenVillageLight.a(worldGenVillageStartPiece, list, random, n, n2, n3, n4);
        if (a3 != null) {
            return new WorldGenVillageLight(worldGenVillageStartPiece, n5, random, a3, n4);
        }
        return null;
    }
    
    private static StructurePiece d(final WorldGenVillageStartPiece worldGenVillageStartPiece, final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        if (n5 > 50) {
            return null;
        }
        if (Math.abs(n - worldGenVillageStartPiece.b().a) > 112 || Math.abs(n3 - worldGenVillageStartPiece.b().c) > 112) {
            return null;
        }
        final WorldGenVillagePiece c = c(worldGenVillageStartPiece, list, random, n, n2, n3, n4, n5 + 1);
        if (c != null) {
            final int n6 = (c.e.a + c.e.d) / 2;
            final int n7 = (c.e.c + c.e.f) / 2;
            final int n8 = c.e.d - c.e.a;
            final int n9 = c.e.f - c.e.c;
            if (worldGenVillageStartPiece.d().a(n6, n7, ((n8 > n9) ? n8 : n9) / 2 + 4, WorldGenVillage.e)) {
                list.add(c);
                worldGenVillageStartPiece.i.add(c);
                return c;
            }
        }
        return null;
    }
    
    private static StructurePiece e(final WorldGenVillageStartPiece worldGenVillageStartPiece, final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        if (n5 > 3 + worldGenVillageStartPiece.c) {
            return null;
        }
        if (Math.abs(n - worldGenVillageStartPiece.b().a) > 112 || Math.abs(n3 - worldGenVillageStartPiece.b().c) > 112) {
            return null;
        }
        final StructureBoundingBox a = WorldGenVillageRoad.a(worldGenVillageStartPiece, list, random, n, n2, n3, n4);
        if (a != null && a.b > 10) {
            final WorldGenVillageRoad worldGenVillageRoad = new WorldGenVillageRoad(worldGenVillageStartPiece, n5, random, a, n4);
            final int n6 = (worldGenVillageRoad.e.a + worldGenVillageRoad.e.d) / 2;
            final int n7 = (worldGenVillageRoad.e.c + worldGenVillageRoad.e.f) / 2;
            final int n8 = worldGenVillageRoad.e.d - worldGenVillageRoad.e.a;
            final int n9 = worldGenVillageRoad.e.f - worldGenVillageRoad.e.c;
            if (worldGenVillageStartPiece.d().a(n6, n7, ((n8 > n9) ? n8 : n9) / 2 + 4, WorldGenVillage.e)) {
                list.add(worldGenVillageRoad);
                worldGenVillageStartPiece.j.add(worldGenVillageRoad);
                return worldGenVillageRoad;
            }
        }
        return null;
    }
}
