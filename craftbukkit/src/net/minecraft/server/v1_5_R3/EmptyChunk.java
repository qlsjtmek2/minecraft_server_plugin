// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import java.util.List;

public class EmptyChunk extends Chunk
{
    public EmptyChunk(final World world, final int i, final int j) {
        super(world, i, j);
    }
    
    public boolean a(final int n, final int n2) {
        return n == this.x && n2 == this.z;
    }
    
    public int b(final int n, final int n2) {
        return 0;
    }
    
    public void initLighting() {
    }
    
    public int getTypeId(final int n, final int n2, final int n3) {
        return 0;
    }
    
    public int b(final int n, final int n2, final int n3) {
        return 255;
    }
    
    public boolean a(final int n, final int n2, final int n3, final int n4, final int n5) {
        return true;
    }
    
    public int getData(final int n, final int n2, final int n3) {
        return 0;
    }
    
    public boolean b(final int n, final int n2, final int n3, final int n4) {
        return false;
    }
    
    public int getBrightness(final EnumSkyBlock enumSkyBlock, final int n, final int n2, final int n3) {
        return 0;
    }
    
    public void a(final EnumSkyBlock enumSkyBlock, final int n, final int n2, final int n3, final int n4) {
    }
    
    public int c(final int n, final int n2, final int n3, final int n4) {
        return 0;
    }
    
    public void a(final Entity entity) {
    }
    
    public void b(final Entity entity) {
    }
    
    public void a(final Entity entity, final int n) {
    }
    
    public boolean d(final int n, final int n2, final int n3) {
        return false;
    }
    
    public TileEntity e(final int n, final int n2, final int n3) {
        return null;
    }
    
    public void a(final TileEntity tileEntity) {
    }
    
    public void a(final int n, final int n2, final int n3, final TileEntity tileEntity) {
    }
    
    public void f(final int n, final int n2, final int n3) {
    }
    
    public void addEntities() {
    }
    
    public void removeEntities() {
    }
    
    public void e() {
    }
    
    public void a(final Entity entity, final AxisAlignedBB axisAlignedBB, final List list, final IEntitySelector entitySelector) {
    }
    
    public void a(final Class clazz, final AxisAlignedBB axisAlignedBB, final List list, final IEntitySelector entitySelector) {
    }
    
    public boolean a(final boolean b) {
        return false;
    }
    
    public Random a(final long n) {
        return new Random(this.world.getSeed() + this.x * this.x * 4987142 + this.x * 5947611 + this.z * this.z * 4392871L + this.z * 389711 ^ n);
    }
    
    public boolean isEmpty() {
        return true;
    }
    
    public boolean c(final int n, final int n2) {
        return true;
    }
}
