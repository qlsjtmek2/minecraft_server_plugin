// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.List;

public class WorldGenMineshaftRoom extends StructurePiece
{
    private List a;
    
    public WorldGenMineshaftRoom(final int n, final Random random, final int n2, final int n3) {
        super(n);
        this.a = new LinkedList();
        this.e = new StructureBoundingBox(n2, 50, n3, n2 + 7 + random.nextInt(6), 54 + random.nextInt(6), n3 + 7 + random.nextInt(6));
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        final int c = this.c();
        int n = this.e.c() - 3 - 1;
        if (n <= 0) {
            n = 1;
        }
        for (int i = 0; i < this.e.b(); i += 4) {
            i += random.nextInt(this.e.b());
            if (i + 3 > this.e.b()) {
                break;
            }
            final StructurePiece a = b(structurePiece, list, random, this.e.a + i, this.e.b + random.nextInt(n) + 1, this.e.c - 1, 2, c);
            if (a != null) {
                final StructureBoundingBox b = a.b();
                this.a.add(new StructureBoundingBox(b.a, b.b, this.e.c, b.d, b.e, this.e.c + 1));
            }
        }
        for (int j = 0; j < this.e.b(); j += 4) {
            j += random.nextInt(this.e.b());
            if (j + 3 > this.e.b()) {
                break;
            }
            final StructurePiece a2 = b(structurePiece, list, random, this.e.a + j, this.e.b + random.nextInt(n) + 1, this.e.f + 1, 0, c);
            if (a2 != null) {
                final StructureBoundingBox b2 = a2.b();
                this.a.add(new StructureBoundingBox(b2.a, b2.b, this.e.f - 1, b2.d, b2.e, this.e.f));
            }
        }
        for (int k = 0; k < this.e.d(); k += 4) {
            k += random.nextInt(this.e.d());
            if (k + 3 > this.e.d()) {
                break;
            }
            final StructurePiece a3 = b(structurePiece, list, random, this.e.a - 1, this.e.b + random.nextInt(n) + 1, this.e.c + k, 1, c);
            if (a3 != null) {
                final StructureBoundingBox b3 = a3.b();
                this.a.add(new StructureBoundingBox(this.e.a, b3.b, b3.c, this.e.a + 1, b3.e, b3.f));
            }
        }
        for (int l = 0; l < this.e.d(); l += 4) {
            l += random.nextInt(this.e.d());
            if (l + 3 > this.e.d()) {
                break;
            }
            final StructurePiece a4 = b(structurePiece, list, random, this.e.d + 1, this.e.b + random.nextInt(n) + 1, this.e.c + l, 3, c);
            if (a4 != null) {
                final StructureBoundingBox b4 = a4.b();
                this.a.add(new StructureBoundingBox(this.e.d - 1, b4.b, b4.c, this.e.d, b4.e, b4.f));
            }
        }
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a(world, structureBoundingBox)) {
            return false;
        }
        this.a(world, structureBoundingBox, this.e.a, this.e.b, this.e.c, this.e.d, this.e.b, this.e.f, Block.DIRT.id, 0, true);
        this.a(world, structureBoundingBox, this.e.a, this.e.b + 1, this.e.c, this.e.d, Math.min(this.e.b + 3, this.e.e), this.e.f, 0, 0, false);
        for (final StructureBoundingBox structureBoundingBox2 : this.a) {
            this.a(world, structureBoundingBox, structureBoundingBox2.a, structureBoundingBox2.e - 2, structureBoundingBox2.c, structureBoundingBox2.d, structureBoundingBox2.e, structureBoundingBox2.f, 0, 0, false);
        }
        this.a(world, structureBoundingBox, this.e.a, this.e.b + 4, this.e.c, this.e.d, this.e.e, this.e.f, 0, false);
        return true;
    }
}
