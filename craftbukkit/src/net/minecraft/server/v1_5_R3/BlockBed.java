// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import java.util.Iterator;

public class BlockBed extends BlockDirectional
{
    public static final int[][] a;
    
    public BlockBed(final int n) {
        super(n, Material.CLOTH);
        this.q();
    }
    
    public boolean interact(final World world, int n, final int j, int n2, final EntityHuman entityHuman, final int n3, final float n4, final float n5, final float n6) {
        if (world.isStatic) {
            return true;
        }
        int n7 = world.getData(n, j, n2);
        if (!e_(n7)) {
            final int i = BlockDirectional.j(n7);
            n += BlockBed.a[i][0];
            n2 += BlockBed.a[i][1];
            if (world.getTypeId(n, j, n2) != this.id) {
                return true;
            }
            n7 = world.getData(n, j, n2);
        }
        if (!world.worldProvider.e() || world.getBiome(n, n2) == BiomeBase.HELL) {
            final double n8 = n + 0.5;
            final double n9 = j + 0.5;
            final double n10 = n2 + 0.5;
            world.setAir(n, j, n2);
            final int k = BlockDirectional.j(n7);
            n += BlockBed.a[k][0];
            n2 += BlockBed.a[k][1];
            if (world.getTypeId(n, j, n2) == this.id) {
                world.setAir(n, j, n2);
                final double n11 = (n8 + n + 0.5) / 2.0;
                final double n12 = (n9 + j + 0.5) / 2.0;
                final double n13 = (n10 + n2 + 0.5) / 2.0;
            }
            world.createExplosion(null, n + 0.5f, j + 0.5f, n2 + 0.5f, 5.0f, true, true);
            return true;
        }
        if (c(n7)) {
            EntityHuman entityHuman2 = null;
            for (final EntityHuman entityHuman3 : world.players) {
                if (entityHuman3.isSleeping()) {
                    final ChunkCoordinates cb = entityHuman3.cb;
                    if (cb.x != n || cb.y != j || cb.z != n2) {
                        continue;
                    }
                    entityHuman2 = entityHuman3;
                }
            }
            if (entityHuman2 != null) {
                entityHuman.b("tile.bed.occupied");
                return true;
            }
            a(world, n, j, n2, false);
        }
        final EnumBedResult a = entityHuman.a(n, j, n2);
        if (a == EnumBedResult.OK) {
            a(world, n, j, n2, true);
            return true;
        }
        if (a == EnumBedResult.NOT_POSSIBLE_NOW) {
            entityHuman.b("tile.bed.noSleep");
        }
        else if (a == EnumBedResult.NOT_SAFE) {
            entityHuman.b("tile.bed.notSafe");
        }
        return true;
    }
    
    public int d() {
        return 14;
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean c() {
        return false;
    }
    
    public void updateShape(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        this.q();
    }
    
    public void doPhysics(final World world, final int n, final int n2, final int n3, final int n4) {
        final int data = world.getData(n, n2, n3);
        final int j = BlockDirectional.j(data);
        if (e_(data)) {
            if (world.getTypeId(n - BlockBed.a[j][0], n2, n3 - BlockBed.a[j][1]) != this.id) {
                world.setAir(n, n2, n3);
            }
        }
        else if (world.getTypeId(n + BlockBed.a[j][0], n2, n3 + BlockBed.a[j][1]) != this.id) {
            world.setAir(n, n2, n3);
            if (!world.isStatic) {
                this.c(world, n, n2, n3, data, 0);
            }
        }
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        if (e_(n)) {
            return 0;
        }
        return Item.BED.id;
    }
    
    private void q() {
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.5625f, 1.0f);
    }
    
    public static boolean e_(final int n) {
        return (n & 0x8) != 0x0;
    }
    
    public static boolean c(final int n) {
        return (n & 0x4) != 0x0;
    }
    
    public static void a(final World world, final int n, final int n2, final int n3, final boolean b) {
        final int data = world.getData(n, n2, n3);
        int l;
        if (b) {
            l = (data | 0x4);
        }
        else {
            l = (data & 0xFFFFFFFB);
        }
        world.setData(n, n2, n3, l, 4);
    }
    
    public static ChunkCoordinates b(final World world, final int i, final int n, final int k, int n2) {
        final int j = BlockDirectional.j(world.getData(i, n, k));
        for (int l = 0; l <= 1; ++l) {
            final int n3 = i - BlockBed.a[j][0] * l - 1;
            final int n4 = k - BlockBed.a[j][1] * l - 1;
            final int n5 = n3 + 2;
            final int n6 = n4 + 2;
            for (int m = n3; m <= n5; ++m) {
                for (int k2 = n4; k2 <= n6; ++k2) {
                    if (world.w(m, n - 1, k2) && world.isEmpty(m, n, k2) && world.isEmpty(m, n + 1, k2)) {
                        if (n2 <= 0) {
                            return new ChunkCoordinates(m, n, k2);
                        }
                        --n2;
                    }
                }
            }
        }
        return null;
    }
    
    public void dropNaturally(final World world, final int i, final int j, final int k, final int l, final float f, final int n) {
        if (!e_(l)) {
            super.dropNaturally(world, i, j, k, l, f, 0);
        }
    }
    
    public int h() {
        return 1;
    }
    
    public void a(final World world, int n, final int n2, int n3, final int n4, final EntityHuman entityHuman) {
        if (entityHuman.abilities.canInstantlyBuild && e_(n4)) {
            final int j = BlockDirectional.j(n4);
            n -= BlockBed.a[j][0];
            n3 -= BlockBed.a[j][1];
            if (world.getTypeId(n, n2, n3) == this.id) {
                world.setAir(n, n2, n3);
            }
        }
    }
    
    static {
        a = new int[][] { { 0, 1 }, { -1, 0 }, { 0, -1 }, { 1, 0 } };
    }
}
