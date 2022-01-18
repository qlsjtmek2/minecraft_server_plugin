// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.Random;
import java.util.LinkedList;

public abstract class StructureStart
{
    protected LinkedList a;
    protected StructureBoundingBox b;
    
    protected StructureStart() {
        this.a = new LinkedList();
    }
    
    public StructureBoundingBox a() {
        return this.b;
    }
    
    public LinkedList b() {
        return this.a;
    }
    
    public void a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        final Iterator iterator = this.a.iterator();
        while (iterator.hasNext()) {
            final StructurePiece structurePiece = iterator.next();
            if (structurePiece.b().a(structureBoundingBox)) {
                if (structurePiece.a(world, random, structureBoundingBox)) {
                    continue;
                }
                iterator.remove();
            }
        }
    }
    
    protected void c() {
        this.b = StructureBoundingBox.a();
        final Iterator iterator = this.a.iterator();
        while (iterator.hasNext()) {
            this.b.b(iterator.next().b());
        }
    }
    
    protected void a(final World world, final Random random, final int n) {
        final int n2 = 63 - n;
        int n3 = this.b.c() + 1;
        if (n3 < n2) {
            n3 += random.nextInt(n2 - n3);
        }
        final int n4 = n3 - this.b.e;
        this.b.a(0, n4, 0);
        final Iterator iterator = this.a.iterator();
        while (iterator.hasNext()) {
            iterator.next().b().a(0, n4, 0);
        }
    }
    
    protected void a(final World world, final Random random, final int n, final int n2) {
        final int n3 = n2 - n + 1 - this.b.c();
        int n4;
        if (n3 > 1) {
            n4 = n + random.nextInt(n3);
        }
        else {
            n4 = n;
        }
        final int n5 = n4 - this.b.b;
        this.b.a(0, n5, 0);
        final Iterator iterator = this.a.iterator();
        while (iterator.hasNext()) {
            iterator.next().b().a(0, n5, 0);
        }
    }
    
    public boolean d() {
        return true;
    }
}
