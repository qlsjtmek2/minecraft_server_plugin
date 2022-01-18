// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class WorldGenNetherPiece15 extends WorldGenNetherPiece1
{
    public WorldGenNetherPieceWeight a;
    public List b;
    public List c;
    public ArrayList d;
    
    public WorldGenNetherPiece15(final Random random, final int n, final int n2) {
        super(random, n, n2);
        this.d = new ArrayList();
        this.b = new ArrayList();
        for (final WorldGenNetherPieceWeight worldGenNetherPieceWeight : WorldGenNetherPieces.a) {
            worldGenNetherPieceWeight.c = 0;
            this.b.add(worldGenNetherPieceWeight);
        }
        this.c = new ArrayList();
        for (final WorldGenNetherPieceWeight worldGenNetherPieceWeight2 : WorldGenNetherPieces.b) {
            worldGenNetherPieceWeight2.c = 0;
            this.c.add(worldGenNetherPieceWeight2);
        }
    }
}
