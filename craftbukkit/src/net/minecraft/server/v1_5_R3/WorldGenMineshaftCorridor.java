// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenMineshaftCorridor extends StructurePiece
{
    private final boolean a;
    private final boolean b;
    private boolean c;
    private int d;
    
    public WorldGenMineshaftCorridor(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.e = e;
        this.a = (random.nextInt(3) == 0);
        this.b = (!this.a && random.nextInt(23) == 0);
        if (this.f == 2 || this.f == 0) {
            this.d = e.d() / 5;
        }
        else {
            this.d = e.b() / 5;
        }
    }
    
    public static StructureBoundingBox a(final List list, final Random random, final int n, final int n2, final int n3, final int n4) {
        final StructureBoundingBox structureBoundingBox = new StructureBoundingBox(n, n2, n3, n, n2 + 2, n3);
        int i;
        for (i = random.nextInt(3) + 2; i > 0; --i) {
            final int n5 = i * 5;
            switch (n4) {
                case 2: {
                    structureBoundingBox.d = n + 2;
                    structureBoundingBox.c = n3 - (n5 - 1);
                    break;
                }
                case 0: {
                    structureBoundingBox.d = n + 2;
                    structureBoundingBox.f = n3 + (n5 - 1);
                    break;
                }
                case 1: {
                    structureBoundingBox.a = n - (n5 - 1);
                    structureBoundingBox.f = n3 + 2;
                    break;
                }
                case 3: {
                    structureBoundingBox.d = n + (n5 - 1);
                    structureBoundingBox.f = n3 + 2;
                    break;
                }
            }
            if (StructurePiece.a(list, structureBoundingBox) == null) {
                break;
            }
        }
        if (i > 0) {
            return structureBoundingBox;
        }
        return null;
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        final int c = this.c();
        final int nextInt = random.nextInt(4);
        switch (this.f) {
            case 2: {
                if (nextInt <= 1) {
                    b(structurePiece, list, random, this.e.a, this.e.b - 1 + random.nextInt(3), this.e.c - 1, this.f, c);
                    break;
                }
                if (nextInt == 2) {
                    b(structurePiece, list, random, this.e.a - 1, this.e.b - 1 + random.nextInt(3), this.e.c, 1, c);
                    break;
                }
                b(structurePiece, list, random, this.e.d + 1, this.e.b - 1 + random.nextInt(3), this.e.c, 3, c);
                break;
            }
            case 0: {
                if (nextInt <= 1) {
                    b(structurePiece, list, random, this.e.a, this.e.b - 1 + random.nextInt(3), this.e.f + 1, this.f, c);
                    break;
                }
                if (nextInt == 2) {
                    b(structurePiece, list, random, this.e.a - 1, this.e.b - 1 + random.nextInt(3), this.e.f - 3, 1, c);
                    break;
                }
                b(structurePiece, list, random, this.e.d + 1, this.e.b - 1 + random.nextInt(3), this.e.f - 3, 3, c);
                break;
            }
            case 1: {
                if (nextInt <= 1) {
                    b(structurePiece, list, random, this.e.a - 1, this.e.b - 1 + random.nextInt(3), this.e.c, this.f, c);
                    break;
                }
                if (nextInt == 2) {
                    b(structurePiece, list, random, this.e.a, this.e.b - 1 + random.nextInt(3), this.e.c - 1, 2, c);
                    break;
                }
                b(structurePiece, list, random, this.e.a, this.e.b - 1 + random.nextInt(3), this.e.f + 1, 0, c);
                break;
            }
            case 3: {
                if (nextInt <= 1) {
                    b(structurePiece, list, random, this.e.d + 1, this.e.b - 1 + random.nextInt(3), this.e.c, this.f, c);
                    break;
                }
                if (nextInt == 2) {
                    b(structurePiece, list, random, this.e.d - 3, this.e.b - 1 + random.nextInt(3), this.e.c - 1, 2, c);
                    break;
                }
                b(structurePiece, list, random, this.e.d - 3, this.e.b - 1 + random.nextInt(3), this.e.f + 1, 0, c);
                break;
            }
        }
        if (c < 8) {
            if (this.f == 2 || this.f == 0) {
                for (int n = this.e.c + 3; n + 3 <= this.e.f; n += 5) {
                    final int nextInt2 = random.nextInt(5);
                    if (nextInt2 == 0) {
                        b(structurePiece, list, random, this.e.a - 1, this.e.b, n, 1, c + 1);
                    }
                    else if (nextInt2 == 1) {
                        b(structurePiece, list, random, this.e.d + 1, this.e.b, n, 3, c + 1);
                    }
                }
            }
            else {
                for (int n2 = this.e.a + 3; n2 + 3 <= this.e.d; n2 += 5) {
                    final int nextInt3 = random.nextInt(5);
                    if (nextInt3 == 0) {
                        b(structurePiece, list, random, n2, this.e.b, this.e.c - 1, 2, c + 1);
                    }
                    else if (nextInt3 == 1) {
                        b(structurePiece, list, random, n2, this.e.b, this.e.f + 1, 0, c + 1);
                    }
                }
            }
        }
    }
    
    protected boolean a(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final int n, final int n2, final int n3, final StructurePieceTreasure[] array, final int n4) {
        final int a = this.a(n, n3);
        final int a2 = this.a(n2);
        final int b = this.b(n, n3);
        if (structureBoundingBox.b(a, a2, b) && world.getTypeId(a, a2, b) == 0) {
            world.setTypeIdAndData(a, a2, b, Block.RAILS.id, this.c(Block.RAILS.id, (int)(random.nextBoolean() ? 1 : 0)), 2);
            final EntityMinecartChest entity = new EntityMinecartChest(world, a + 0.5f, a2 + 0.5f, b + 0.5f);
            StructurePieceTreasure.a(random, array, entity, n4);
            world.addEntity(entity);
            return true;
        }
        return false;
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a(world, structureBoundingBox)) {
            return false;
        }
        final int n = this.d * 5 - 1;
        this.a(world, structureBoundingBox, 0, 0, 0, 2, 1, n, 0, 0, false);
        this.a(world, structureBoundingBox, random, 0.8f, 0, 2, 0, 2, 2, n, 0, 0, false);
        if (this.b) {
            this.a(world, structureBoundingBox, random, 0.6f, 0, 0, 0, 2, 1, n, Block.WEB.id, 0, false);
        }
        for (int i = 0; i < this.d; ++i) {
            final int n2 = 2 + i * 5;
            this.a(world, structureBoundingBox, 0, 0, n2, 0, 1, n2, Block.FENCE.id, 0, false);
            this.a(world, structureBoundingBox, 2, 0, n2, 2, 1, n2, Block.FENCE.id, 0, false);
            if (random.nextInt(4) == 0) {
                this.a(world, structureBoundingBox, 0, 2, n2, 0, 2, n2, Block.WOOD.id, 0, false);
                this.a(world, structureBoundingBox, 2, 2, n2, 2, 2, n2, Block.WOOD.id, 0, false);
            }
            else {
                this.a(world, structureBoundingBox, 0, 2, n2, 2, 2, n2, Block.WOOD.id, 0, false);
            }
            this.a(world, structureBoundingBox, random, 0.1f, 0, 2, n2 - 1, Block.WEB.id, 0);
            this.a(world, structureBoundingBox, random, 0.1f, 2, 2, n2 - 1, Block.WEB.id, 0);
            this.a(world, structureBoundingBox, random, 0.1f, 0, 2, n2 + 1, Block.WEB.id, 0);
            this.a(world, structureBoundingBox, random, 0.1f, 2, 2, n2 + 1, Block.WEB.id, 0);
            this.a(world, structureBoundingBox, random, 0.05f, 0, 2, n2 - 2, Block.WEB.id, 0);
            this.a(world, structureBoundingBox, random, 0.05f, 2, 2, n2 - 2, Block.WEB.id, 0);
            this.a(world, structureBoundingBox, random, 0.05f, 0, 2, n2 + 2, Block.WEB.id, 0);
            this.a(world, structureBoundingBox, random, 0.05f, 2, 2, n2 + 2, Block.WEB.id, 0);
            this.a(world, structureBoundingBox, random, 0.05f, 1, 2, n2 - 1, Block.TORCH.id, 0);
            this.a(world, structureBoundingBox, random, 0.05f, 1, 2, n2 + 1, Block.TORCH.id, 0);
            if (random.nextInt(100) == 0) {
                this.a(world, structureBoundingBox, random, 2, 0, n2 - 1, StructurePieceTreasure.a(WorldGenMineshaftPieces.a, Item.ENCHANTED_BOOK.b(random)), 3 + random.nextInt(4));
            }
            if (random.nextInt(100) == 0) {
                this.a(world, structureBoundingBox, random, 0, 0, n2 + 1, StructurePieceTreasure.a(WorldGenMineshaftPieces.a, Item.ENCHANTED_BOOK.b(random)), 3 + random.nextInt(4));
            }
            if (this.b && !this.c) {
                final int a = this.a(0);
                final int n3 = n2 - 1 + random.nextInt(3);
                final int a2 = this.a(1, n3);
                final int b = this.b(1, n3);
                if (structureBoundingBox.b(a2, a, b)) {
                    this.c = true;
                    world.setTypeIdAndData(a2, a, b, Block.MOB_SPAWNER.id, 0, 2);
                    final TileEntityMobSpawner tileEntityMobSpawner = (TileEntityMobSpawner)world.getTileEntity(a2, a, b);
                    if (tileEntityMobSpawner != null) {
                        tileEntityMobSpawner.a().a("CaveSpider");
                    }
                }
            }
        }
        for (int j = 0; j <= 2; ++j) {
            for (int k = 0; k <= n; ++k) {
                if (this.a(world, j, -1, k, structureBoundingBox) == 0) {
                    this.a(world, Block.WOOD.id, 0, j, -1, k, structureBoundingBox);
                }
            }
        }
        if (this.a) {
            for (int l = 0; l <= n; ++l) {
                final int a3 = this.a(world, 1, -1, l, structureBoundingBox);
                if (a3 > 0 && Block.s[a3]) {
                    this.a(world, structureBoundingBox, random, 0.7f, 1, 0, l, Block.RAILS.id, this.c(Block.RAILS.id, 0));
                }
            }
        }
        return true;
    }
}
