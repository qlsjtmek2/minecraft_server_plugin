// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class RandomPositionGenerator
{
    private static Vec3D a;
    
    public static Vec3D a(final EntityCreature entityCreature, final int n, final int n2) {
        return c(entityCreature, n, n2, null);
    }
    
    public static Vec3D a(final EntityCreature entityCreature, final int n, final int n2, final Vec3D vec3D) {
        RandomPositionGenerator.a.c = vec3D.c - entityCreature.locX;
        RandomPositionGenerator.a.d = vec3D.d - entityCreature.locY;
        RandomPositionGenerator.a.e = vec3D.e - entityCreature.locZ;
        return c(entityCreature, n, n2, RandomPositionGenerator.a);
    }
    
    public static Vec3D b(final EntityCreature entityCreature, final int n, final int n2, final Vec3D vec3D) {
        RandomPositionGenerator.a.c = entityCreature.locX - vec3D.c;
        RandomPositionGenerator.a.d = entityCreature.locY - vec3D.d;
        RandomPositionGenerator.a.e = entityCreature.locZ - vec3D.e;
        return c(entityCreature, n, n2, RandomPositionGenerator.a);
    }
    
    private static Vec3D c(final EntityCreature entityCreature, final int n, final int n2, final Vec3D vec3D) {
        final Random ae = entityCreature.aE();
        boolean b = false;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        float n6 = -99999.0f;
        boolean b2;
        if (entityCreature.aP()) {
            final double n7 = entityCreature.aM().e(MathHelper.floor(entityCreature.locX), MathHelper.floor(entityCreature.locY), MathHelper.floor(entityCreature.locZ)) + 4.0f;
            final double n8 = entityCreature.aN() + n;
            b2 = (n7 < n8 * n8);
        }
        else {
            b2 = false;
        }
        for (int i = 0; i < 10; ++i) {
            final int n9 = ae.nextInt(2 * n) - n;
            final int n10 = ae.nextInt(2 * n2) - n2;
            final int n11 = ae.nextInt(2 * n) - n;
            if (vec3D == null || n9 * vec3D.c + n11 * vec3D.e >= 0.0) {
                final int n12 = n9 + MathHelper.floor(entityCreature.locX);
                final int n13 = n10 + MathHelper.floor(entityCreature.locY);
                final int n14 = n11 + MathHelper.floor(entityCreature.locZ);
                if (!b2 || entityCreature.d(n12, n13, n14)) {
                    final float a = entityCreature.a(n12, n13, n14);
                    if (a > n6) {
                        n6 = a;
                        n3 = n12;
                        n4 = n13;
                        n5 = n14;
                        b = true;
                    }
                }
            }
        }
        if (b) {
            return entityCreature.world.getVec3DPool().create(n3, n4, n5);
        }
        return null;
    }
    
    static {
        RandomPositionGenerator.a = Vec3D.a(0.0, 0.0, 0.0);
    }
}
