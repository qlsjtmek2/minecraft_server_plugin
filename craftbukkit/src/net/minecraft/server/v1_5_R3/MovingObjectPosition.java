// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class MovingObjectPosition
{
    public EnumMovingObjectType type;
    public int b;
    public int c;
    public int d;
    public int face;
    public Vec3D pos;
    public Entity entity;
    
    public MovingObjectPosition(final int b, final int c, final int d, final int face, final Vec3D vec3D) {
        this.type = EnumMovingObjectType.TILE;
        this.b = b;
        this.c = c;
        this.d = d;
        this.face = face;
        this.pos = vec3D.b.create(vec3D.c, vec3D.d, vec3D.e);
    }
    
    public MovingObjectPosition(final Entity entity) {
        this.type = EnumMovingObjectType.ENTITY;
        this.entity = entity;
        this.pos = entity.world.getVec3DPool().create(entity.locX, entity.locY, entity.locZ);
    }
}
