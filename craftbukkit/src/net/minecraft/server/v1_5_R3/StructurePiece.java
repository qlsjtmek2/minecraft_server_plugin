// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.Random;
import java.util.List;

public abstract class StructurePiece
{
    protected StructureBoundingBox e;
    protected int f;
    protected int g;
    
    protected StructurePiece(final int g) {
        this.g = g;
        this.f = -1;
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
    }
    
    public abstract boolean a(final World p0, final Random p1, final StructureBoundingBox p2);
    
    public StructureBoundingBox b() {
        return this.e;
    }
    
    public int c() {
        return this.g;
    }
    
    public static StructurePiece a(final List list, final StructureBoundingBox structureBoundingBox) {
        for (final StructurePiece structurePiece : list) {
            if (structurePiece.b() != null && structurePiece.b().a(structureBoundingBox)) {
                return structurePiece;
            }
        }
        return null;
    }
    
    public ChunkPosition a() {
        return new ChunkPosition(this.e.e(), this.e.f(), this.e.g());
    }
    
    protected boolean a(final World world, final StructureBoundingBox structureBoundingBox) {
        final int max = Math.max(this.e.a - 1, structureBoundingBox.a);
        final int max2 = Math.max(this.e.b - 1, structureBoundingBox.b);
        final int max3 = Math.max(this.e.c - 1, structureBoundingBox.c);
        final int min = Math.min(this.e.d + 1, structureBoundingBox.d);
        final int min2 = Math.min(this.e.e + 1, structureBoundingBox.e);
        final int min3 = Math.min(this.e.f + 1, structureBoundingBox.f);
        for (int i = max; i <= min; ++i) {
            for (int j = max3; j <= min3; ++j) {
                final int typeId = world.getTypeId(i, max2, j);
                if (typeId > 0 && Block.byId[typeId].material.isLiquid()) {
                    return true;
                }
                final int typeId2 = world.getTypeId(i, min2, j);
                if (typeId2 > 0 && Block.byId[typeId2].material.isLiquid()) {
                    return true;
                }
            }
        }
        for (int k = max; k <= min; ++k) {
            for (int l = max2; l <= min2; ++l) {
                final int typeId3 = world.getTypeId(k, l, max3);
                if (typeId3 > 0 && Block.byId[typeId3].material.isLiquid()) {
                    return true;
                }
                final int typeId4 = world.getTypeId(k, l, min3);
                if (typeId4 > 0 && Block.byId[typeId4].material.isLiquid()) {
                    return true;
                }
            }
        }
        for (int n = max3; n <= min3; ++n) {
            for (int n2 = max2; n2 <= min2; ++n2) {
                final int typeId5 = world.getTypeId(max, n2, n);
                if (typeId5 > 0 && Block.byId[typeId5].material.isLiquid()) {
                    return true;
                }
                final int typeId6 = world.getTypeId(min, n2, n);
                if (typeId6 > 0 && Block.byId[typeId6].material.isLiquid()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    protected int a(final int n, final int n2) {
        switch (this.f) {
            case 0:
            case 2: {
                return this.e.a + n;
            }
            case 1: {
                return this.e.d - n2;
            }
            case 3: {
                return this.e.a + n2;
            }
            default: {
                return n;
            }
        }
    }
    
    protected int a(final int n) {
        if (this.f == -1) {
            return n;
        }
        return n + this.e.b;
    }
    
    protected int b(final int n, final int n2) {
        switch (this.f) {
            case 2: {
                return this.e.f - n2;
            }
            case 0: {
                return this.e.c + n2;
            }
            case 1:
            case 3: {
                return this.e.c + n;
            }
            default: {
                return n2;
            }
        }
    }
    
    protected int c(final int n, final int n2) {
        if (n == Block.RAILS.id) {
            if (this.f == 1 || this.f == 3) {
                if (n2 == 1) {
                    return 0;
                }
                return 1;
            }
        }
        else if (n == Block.WOODEN_DOOR.id || n == Block.IRON_DOOR_BLOCK.id) {
            if (this.f == 0) {
                if (n2 == 0) {
                    return 2;
                }
                if (n2 == 2) {
                    return 0;
                }
            }
            else {
                if (this.f == 1) {
                    return n2 + 1 & 0x3;
                }
                if (this.f == 3) {
                    return n2 + 3 & 0x3;
                }
            }
        }
        else if (n == Block.COBBLESTONE_STAIRS.id || n == Block.WOOD_STAIRS.id || n == Block.NETHER_BRICK_STAIRS.id || n == Block.STONE_STAIRS.id || n == Block.SANDSTONE_STAIRS.id) {
            if (this.f == 0) {
                if (n2 == 2) {
                    return 3;
                }
                if (n2 == 3) {
                    return 2;
                }
            }
            else if (this.f == 1) {
                if (n2 == 0) {
                    return 2;
                }
                if (n2 == 1) {
                    return 3;
                }
                if (n2 == 2) {
                    return 0;
                }
                if (n2 == 3) {
                    return 1;
                }
            }
            else if (this.f == 3) {
                if (n2 == 0) {
                    return 2;
                }
                if (n2 == 1) {
                    return 3;
                }
                if (n2 == 2) {
                    return 1;
                }
                if (n2 == 3) {
                    return 0;
                }
            }
        }
        else if (n == Block.LADDER.id) {
            if (this.f == 0) {
                if (n2 == 2) {
                    return 3;
                }
                if (n2 == 3) {
                    return 2;
                }
            }
            else if (this.f == 1) {
                if (n2 == 2) {
                    return 4;
                }
                if (n2 == 3) {
                    return 5;
                }
                if (n2 == 4) {
                    return 2;
                }
                if (n2 == 5) {
                    return 3;
                }
            }
            else if (this.f == 3) {
                if (n2 == 2) {
                    return 5;
                }
                if (n2 == 3) {
                    return 4;
                }
                if (n2 == 4) {
                    return 2;
                }
                if (n2 == 5) {
                    return 3;
                }
            }
        }
        else if (n == Block.STONE_BUTTON.id) {
            if (this.f == 0) {
                if (n2 == 3) {
                    return 4;
                }
                if (n2 == 4) {
                    return 3;
                }
            }
            else if (this.f == 1) {
                if (n2 == 3) {
                    return 1;
                }
                if (n2 == 4) {
                    return 2;
                }
                if (n2 == 2) {
                    return 3;
                }
                if (n2 == 1) {
                    return 4;
                }
            }
            else if (this.f == 3) {
                if (n2 == 3) {
                    return 2;
                }
                if (n2 == 4) {
                    return 1;
                }
                if (n2 == 2) {
                    return 3;
                }
                if (n2 == 1) {
                    return 4;
                }
            }
        }
        else if (n == Block.TRIPWIRE_SOURCE.id || (Block.byId[n] != null && Block.byId[n] instanceof BlockDirectional)) {
            if (this.f == 0) {
                if (n2 == 0 || n2 == 2) {
                    return Direction.f[n2];
                }
            }
            else if (this.f == 1) {
                if (n2 == 2) {
                    return 1;
                }
                if (n2 == 0) {
                    return 3;
                }
                if (n2 == 1) {
                    return 2;
                }
                if (n2 == 3) {
                    return 0;
                }
            }
            else if (this.f == 3) {
                if (n2 == 2) {
                    return 3;
                }
                if (n2 == 0) {
                    return 1;
                }
                if (n2 == 1) {
                    return 2;
                }
                if (n2 == 3) {
                    return 0;
                }
            }
        }
        else if (n == Block.PISTON.id || n == Block.PISTON_STICKY.id || n == Block.LEVER.id || n == Block.DISPENSER.id) {
            if (this.f == 0) {
                if (n2 == 2 || n2 == 3) {
                    return Facing.OPPOSITE_FACING[n2];
                }
            }
            else if (this.f == 1) {
                if (n2 == 2) {
                    return 4;
                }
                if (n2 == 3) {
                    return 5;
                }
                if (n2 == 4) {
                    return 2;
                }
                if (n2 == 5) {
                    return 3;
                }
            }
            else if (this.f == 3) {
                if (n2 == 2) {
                    return 5;
                }
                if (n2 == 3) {
                    return 4;
                }
                if (n2 == 4) {
                    return 2;
                }
                if (n2 == 5) {
                    return 3;
                }
            }
        }
        return n2;
    }
    
    protected void a(final World world, final int l, final int i1, final int n, final int n2, final int n3, final StructureBoundingBox structureBoundingBox) {
        final int a = this.a(n, n3);
        final int a2 = this.a(n2);
        final int b = this.b(n, n3);
        if (!structureBoundingBox.b(a, a2, b)) {
            return;
        }
        world.setTypeIdAndData(a, a2, b, l, i1, 2);
    }
    
    protected int a(final World world, final int n, final int n2, final int n3, final StructureBoundingBox structureBoundingBox) {
        final int a = this.a(n, n3);
        final int a2 = this.a(n2);
        final int b = this.b(n, n3);
        if (!structureBoundingBox.b(a, a2, b)) {
            return 0;
        }
        return world.getTypeId(a, a2, b);
    }
    
    protected void a(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        for (int i = n2; i <= n5; ++i) {
            for (int j = n; j <= n4; ++j) {
                for (int k = n3; k <= n6; ++k) {
                    this.a(world, 0, 0, j, i, k, structureBoundingBox);
                }
            }
        }
    }
    
    protected void a(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final boolean b) {
        for (int i = n2; i <= n5; ++i) {
            for (int j = n; j <= n4; ++j) {
                for (int k = n3; k <= n6; ++k) {
                    if (!b || this.a(world, j, i, k, structureBoundingBox) != 0) {
                        if (i == n2 || i == n5 || j == n || j == n4 || k == n3 || k == n6) {
                            this.a(world, n7, 0, j, i, k, structureBoundingBox);
                        }
                        else {
                            this.a(world, n8, 0, j, i, k, structureBoundingBox);
                        }
                    }
                }
            }
        }
    }
    
    protected void a(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final boolean b) {
        for (int i = n2; i <= n5; ++i) {
            for (int j = n; j <= n4; ++j) {
                for (int k = n3; k <= n6; ++k) {
                    if (!b || this.a(world, j, i, k, structureBoundingBox) != 0) {
                        if (i == n2 || i == n5 || j == n || j == n4 || k == n3 || k == n6) {
                            this.a(world, n7, n8, j, i, k, structureBoundingBox);
                        }
                        else {
                            this.a(world, n9, n10, j, i, k, structureBoundingBox);
                        }
                    }
                }
            }
        }
    }
    
    protected void a(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b, final Random random, final StructurePieceBlockSelector structurePieceBlockSelector) {
        for (int i = n2; i <= n5; ++i) {
            for (int j = n; j <= n4; ++j) {
                for (int k = n3; k <= n6; ++k) {
                    if (!b || this.a(world, j, i, k, structureBoundingBox) != 0) {
                        structurePieceBlockSelector.a(random, j, i, k, i == n2 || i == n5 || j == n || j == n4 || k == n3 || k == n6);
                        this.a(world, structurePieceBlockSelector.a(), structurePieceBlockSelector.b(), j, i, k, structureBoundingBox);
                    }
                }
            }
        }
    }
    
    protected void a(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final float n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final boolean b) {
        for (int i = n3; i <= n6; ++i) {
            for (int j = n2; j <= n5; ++j) {
                for (int k = n4; k <= n7; ++k) {
                    if (random.nextFloat() <= n) {
                        if (!b || this.a(world, j, i, k, structureBoundingBox) != 0) {
                            if (i == n3 || i == n6 || j == n2 || j == n5 || k == n4 || k == n7) {
                                this.a(world, n8, 0, j, i, k, structureBoundingBox);
                            }
                            else {
                                this.a(world, n9, 0, j, i, k, structureBoundingBox);
                            }
                        }
                    }
                }
            }
        }
    }
    
    protected void a(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final float n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (random.nextFloat() < n) {
            this.a(world, n5, n6, n2, n3, n4, structureBoundingBox);
        }
    }
    
    protected void a(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        final float n8 = n4 - n + 1;
        final float n9 = n5 - n2 + 1;
        final float n10 = n6 - n3 + 1;
        final float n11 = n + n8 / 2.0f;
        final float n12 = n3 + n10 / 2.0f;
        for (int i = n2; i <= n5; ++i) {
            final float n13 = (i - n2) / n9;
            for (int j = n; j <= n4; ++j) {
                final float n14 = (j - n11) / (n8 * 0.5f);
                for (int k = n3; k <= n6; ++k) {
                    final float n15 = (k - n12) / (n10 * 0.5f);
                    if (!b || this.a(world, j, i, k, structureBoundingBox) != 0) {
                        if (n14 * n14 + n13 * n13 + n15 * n15 <= 1.05f) {
                            this.a(world, n7, 0, j, i, k, structureBoundingBox);
                        }
                    }
                }
            }
        }
    }
    
    protected void b(final World world, final int n, final int n2, final int n3, final StructureBoundingBox structureBoundingBox) {
        final int a = this.a(n, n3);
        int a2 = this.a(n2);
        final int b = this.b(n, n3);
        if (!structureBoundingBox.b(a, a2, b)) {
            return;
        }
        while (!world.isEmpty(a, a2, b) && a2 < 255) {
            world.setTypeIdAndData(a, a2, b, 0, 0, 2);
            ++a2;
        }
    }
    
    protected void b(final World world, final int l, final int i1, final int n, final int n2, final int n3, final StructureBoundingBox structureBoundingBox) {
        final int a = this.a(n, n3);
        int a2 = this.a(n2);
        final int b = this.b(n, n3);
        if (!structureBoundingBox.b(a, a2, b)) {
            return;
        }
        while ((world.isEmpty(a, a2, b) || world.getMaterial(a, a2, b).isLiquid()) && a2 > 1) {
            world.setTypeIdAndData(a, a2, b, l, i1, 2);
            --a2;
        }
    }
    
    protected boolean a(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final int n, final int n2, final int n3, final StructurePieceTreasure[] array, final int n4) {
        final int a = this.a(n, n3);
        final int a2 = this.a(n2);
        final int b = this.b(n, n3);
        if (structureBoundingBox.b(a, a2, b) && world.getTypeId(a, a2, b) != Block.CHEST.id) {
            world.setTypeIdAndData(a, a2, b, Block.CHEST.id, 0, 2);
            final TileEntityChest tileEntityChest = (TileEntityChest)world.getTileEntity(a, a2, b);
            if (tileEntityChest != null) {
                StructurePieceTreasure.a(random, array, tileEntityChest, n4);
            }
            return true;
        }
        return false;
    }
    
    protected boolean a(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final int n, final int n2, final int n3, final int n4, final StructurePieceTreasure[] array, final int n5) {
        final int a = this.a(n, n3);
        final int a2 = this.a(n2);
        final int b = this.b(n, n3);
        if (structureBoundingBox.b(a, a2, b) && world.getTypeId(a, a2, b) != Block.DISPENSER.id) {
            world.setTypeIdAndData(a, a2, b, Block.DISPENSER.id, this.c(Block.DISPENSER.id, n4), 2);
            final TileEntityDispenser tileEntityDispenser = (TileEntityDispenser)world.getTileEntity(a, a2, b);
            if (tileEntityDispenser != null) {
                StructurePieceTreasure.a(random, array, tileEntityDispenser, n5);
            }
            return true;
        }
        return false;
    }
    
    protected void a(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final int n, final int n2, final int n3, final int l) {
        final int a = this.a(n, n3);
        final int a2 = this.a(n2);
        final int b = this.b(n, n3);
        if (structureBoundingBox.b(a, a2, b)) {
            ItemDoor.place(world, a, a2, b, l, Block.WOODEN_DOOR);
        }
    }
}
