// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import java.util.List;

public class WorldGenNetherPieces
{
    private static final WorldGenNetherPieceWeight[] a;
    private static final WorldGenNetherPieceWeight[] b;
    
    private static WorldGenNetherPiece b(final WorldGenNetherPieceWeight worldGenNetherPieceWeight, final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final Class a = worldGenNetherPieceWeight.a;
        WorldGenNetherPiece worldGenNetherPiece = null;
        if (a == WorldGenNetherPiece3.class) {
            worldGenNetherPiece = WorldGenNetherPiece3.a(list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenNetherPiece1.class) {
            worldGenNetherPiece = WorldGenNetherPiece1.a(list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenNetherPiece13.class) {
            worldGenNetherPiece = WorldGenNetherPiece13.a(list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenNetherPiece16.class) {
            worldGenNetherPiece = WorldGenNetherPiece16.a(list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenNetherPiece12.class) {
            worldGenNetherPiece = WorldGenNetherPiece12.a(list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenNetherPiece6.class) {
            worldGenNetherPiece = WorldGenNetherPiece6.a(list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenNetherPiece9.class) {
            worldGenNetherPiece = WorldGenNetherPiece9.a(list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenNetherPiece10.class) {
            worldGenNetherPiece = WorldGenNetherPiece10.a(list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenNetherPiece8.class) {
            worldGenNetherPiece = WorldGenNetherPiece8.a(list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenNetherPiece4.class) {
            worldGenNetherPiece = WorldGenNetherPiece4.a(list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenNetherPiece5.class) {
            worldGenNetherPiece = WorldGenNetherPiece5.a(list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenNetherPiece7.class) {
            worldGenNetherPiece = WorldGenNetherPiece7.a(list, random, n, n2, n3, n4, n5);
        }
        else if (a == WorldGenNetherPiece11.class) {
            worldGenNetherPiece = WorldGenNetherPiece11.a(list, random, n, n2, n3, n4, n5);
        }
        return worldGenNetherPiece;
    }
    
    static {
        a = new WorldGenNetherPieceWeight[] { new WorldGenNetherPieceWeight(WorldGenNetherPiece3.class, 30, 0, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece1.class, 10, 4), new WorldGenNetherPieceWeight(WorldGenNetherPiece13.class, 10, 4), new WorldGenNetherPieceWeight(WorldGenNetherPiece16.class, 10, 3), new WorldGenNetherPieceWeight(WorldGenNetherPiece12.class, 5, 2), new WorldGenNetherPieceWeight(WorldGenNetherPiece6.class, 5, 1) };
        b = new WorldGenNetherPieceWeight[] { new WorldGenNetherPieceWeight(WorldGenNetherPiece9.class, 25, 0, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece7.class, 15, 5), new WorldGenNetherPieceWeight(WorldGenNetherPiece10.class, 5, 10), new WorldGenNetherPieceWeight(WorldGenNetherPiece8.class, 5, 10), new WorldGenNetherPieceWeight(WorldGenNetherPiece4.class, 10, 3, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece5.class, 7, 2), new WorldGenNetherPieceWeight(WorldGenNetherPiece11.class, 5, 2) };
    }
}
