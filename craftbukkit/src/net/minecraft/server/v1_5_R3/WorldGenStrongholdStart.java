// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import java.util.ArrayList;

public class WorldGenStrongholdStart extends WorldGenStrongholdStairs2
{
    public WorldGenStrongholdPieceWeight a;
    public WorldGenStrongholdPortalRoom b;
    public ArrayList c;
    
    public WorldGenStrongholdStart(final int n, final Random random, final int n2, final int n3) {
        super(0, random, n2, n3);
        this.c = new ArrayList();
    }
    
    public ChunkPosition a() {
        if (this.b != null) {
            return this.b.a();
        }
        return super.a();
    }
}
