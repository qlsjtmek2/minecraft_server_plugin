// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ChunkCache implements IBlockAccess
{
    private int a;
    private int b;
    private Chunk[][] c;
    private boolean d;
    private World e;
    
    public ChunkCache(final World e, final int n, final int i, final int n2, final int n3, final int j, final int n4, final int n5) {
        this.e = e;
        this.a = n - n5 >> 4;
        this.b = n2 - n5 >> 4;
        final int n6 = n3 + n5 >> 4;
        final int n7 = n4 + n5 >> 4;
        this.c = new Chunk[n6 - this.a + 1][n7 - this.b + 1];
        this.d = true;
        for (int k = this.a; k <= n6; ++k) {
            for (int l = this.b; l <= n7; ++l) {
                final Chunk chunk = e.getChunkAt(k, l);
                if (chunk != null) {
                    this.c[k - this.a][l - this.b] = chunk;
                }
            }
        }
        for (int n8 = n >> 4; n8 <= n3 >> 4; ++n8) {
            for (int n9 = n2 >> 4; n9 <= n4 >> 4; ++n9) {
                final Chunk chunk2 = this.c[n8 - this.a][n9 - this.b];
                if (chunk2 != null && !chunk2.c(i, j)) {
                    this.d = false;
                }
            }
        }
    }
    
    public int getTypeId(final int n, final int j, final int n2) {
        if (j < 0) {
            return 0;
        }
        if (j >= 256) {
            return 0;
        }
        final int n3 = (n >> 4) - this.a;
        final int n4 = (n2 >> 4) - this.b;
        if (n3 < 0 || n3 >= this.c.length || n4 < 0 || n4 >= this.c[n3].length) {
            return 0;
        }
        final Chunk chunk = this.c[n3][n4];
        if (chunk == null) {
            return 0;
        }
        return chunk.getTypeId(n & 0xF, j, n2 & 0xF);
    }
    
    public TileEntity getTileEntity(final int n, final int j, final int n2) {
        return this.c[(n >> 4) - this.a][(n2 >> 4) - this.b].e(n & 0xF, j, n2 & 0xF);
    }
    
    public int getData(final int n, final int j, final int n2) {
        if (j < 0) {
            return 0;
        }
        if (j >= 256) {
            return 0;
        }
        return this.c[(n >> 4) - this.a][(n2 >> 4) - this.b].getData(n & 0xF, j, n2 & 0xF);
    }
    
    public Material getMaterial(final int n, final int n2, final int n3) {
        final int typeId = this.getTypeId(n, n2, n3);
        if (typeId == 0) {
            return Material.AIR;
        }
        return Block.byId[typeId].material;
    }
    
    public boolean u(final int n, final int n2, final int n3) {
        final Block block = Block.byId[this.getTypeId(n, n2, n3)];
        return block != null && block.material.isSolid() && block.b();
    }
    
    public Vec3DPool getVec3DPool() {
        return this.e.getVec3DPool();
    }
    
    public int getBlockPower(final int i, final int j, final int k, final int l) {
        final int typeId = this.getTypeId(i, j, k);
        if (typeId == 0) {
            return 0;
        }
        return Block.byId[typeId].c(this, i, j, k, l);
    }
}
