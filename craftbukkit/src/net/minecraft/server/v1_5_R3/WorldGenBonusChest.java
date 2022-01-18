// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenBonusChest extends WorldGenerator
{
    private final StructurePieceTreasure[] a;
    private final int b;
    
    public WorldGenBonusChest(final StructurePieceTreasure[] a, final int b) {
        this.a = a;
        this.b = b;
    }
    
    public boolean a(final World world, final Random random, final int i, int j, final int k) {
        int typeId;
        while (((typeId = world.getTypeId(i, j, k)) == 0 || typeId == Block.LEAVES.id) && j > 1) {
            --j;
        }
        if (j < 1) {
            return false;
        }
        ++j;
        for (int l = 0; l < 4; ++l) {
            final int n = i + random.nextInt(4) - random.nextInt(4);
            final int m = j + random.nextInt(3) - random.nextInt(3);
            final int n2 = k + random.nextInt(4) - random.nextInt(4);
            if (world.isEmpty(n, m, n2) && world.w(n, m - 1, n2)) {
                world.setTypeIdAndData(n, m, n2, Block.CHEST.id, 0, 2);
                final TileEntityChest tileEntityChest = (TileEntityChest)world.getTileEntity(n, m, n2);
                if (tileEntityChest != null && tileEntityChest != null) {
                    StructurePieceTreasure.a(random, this.a, tileEntityChest, this.b);
                }
                if (world.isEmpty(n - 1, m, n2) && world.w(n - 1, m - 1, n2)) {
                    world.setTypeIdAndData(n - 1, m, n2, Block.TORCH.id, 0, 2);
                }
                if (world.isEmpty(n + 1, m, n2) && world.w(n - 1, m - 1, n2)) {
                    world.setTypeIdAndData(n + 1, m, n2, Block.TORCH.id, 0, 2);
                }
                if (world.isEmpty(n, m, n2 - 1) && world.w(n - 1, m - 1, n2)) {
                    world.setTypeIdAndData(n, m, n2 - 1, Block.TORCH.id, 0, 2);
                }
                if (world.isEmpty(n, m, n2 + 1) && world.w(n - 1, m - 1, n2)) {
                    world.setTypeIdAndData(n, m, n2 + 1, Block.TORCH.id, 0, 2);
                }
                return true;
            }
        }
        return false;
    }
}
